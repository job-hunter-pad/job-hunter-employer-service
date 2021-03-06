package jobhunter.employerservice.controller;

import jobhunter.employerservice.controller.authorization.AuthTokenValidator;
import jobhunter.employerservice.controller.dto.CreateJobOfferDTO;
import jobhunter.employerservice.controller.dto.UpdateJobOfferDTO;
import jobhunter.employerservice.interceptor.AuthTokenHTTPInterceptor;
import jobhunter.employerservice.interceptor.BearerExtractor;
import jobhunter.employerservice.kafka.producer.JobApplicationsProducer;
import jobhunter.employerservice.kafka.producer.JobOfferProducer;
import jobhunter.employerservice.model.JobApplication;
import jobhunter.employerservice.model.JobApplicationStatus;
import jobhunter.employerservice.model.JobOffer;
import jobhunter.employerservice.model.JobOfferStatus;
import jobhunter.employerservice.repository.JobOfferRepository;
import jobhunter.employerservice.service.JobApplicationNotCompletedException;
import jobhunter.employerservice.service.JobOfferService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class JobOfferController {
    private final JobOfferRepository jobOfferRepository;
    private final JobOfferService jobOfferService;

    private final JobOfferProducer jobOfferProducer;
    private final JobApplicationsProducer jobApplicationsProducer;

    private final AuthTokenValidator authTokenValidator;
    private final BearerExtractor bearerExtractor;

    public JobOfferController(JobOfferRepository jobOfferRepository,
                              JobOfferService jobOfferService,
                              JobOfferProducer jobOfferProducer,
                              JobApplicationsProducer jobApplicationsProducer,
                              AuthTokenValidator authTokenValidator,
                              BearerExtractor bearerExtractor) {
        this.jobOfferRepository = jobOfferRepository;
        this.jobOfferService = jobOfferService;
        this.jobOfferProducer = jobOfferProducer;
        this.jobApplicationsProducer = jobApplicationsProducer;
        this.authTokenValidator = authTokenValidator;
        this.bearerExtractor = bearerExtractor;
    }

    @GetMapping("/")
    public List<JobOffer> getAllJobOffers() {
        return jobOfferService.getAllJobOffers();
    }

    @GetMapping("/{jobId}")
    public JobOffer getJobOffer(@PathVariable String jobId) {
        return jobOfferService.getJobOffer(jobId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/getJobOffers/{employerId}")
    public List<JobOffer> getJobOffersByEmployer(@PathVariable String employerId) {
        return jobOfferService.getJobOfferByEmployer(employerId);
    }


    @GetMapping("/getNotCompletedJobOffers/{employerId}")
    public List<JobOffer> getEmployerNotCompletedJobOffers(@PathVariable String employerId) {
        return jobOfferService.getAllNotCompletedJobOffersOfEmployer(employerId);
    }

    @GetMapping("/getCompletedJobOffers/{employerId}")
    public List<JobOffer> getEmployerCompletedJobOffers(@PathVariable String employerId) {
        return jobOfferService.getAllCompletedJobOffersOfEmployer(employerId);
    }

    @PostMapping("/create")
    public JobOffer createJob(@RequestBody CreateJobOfferDTO jobOfferDTO, @RequestHeader(AuthTokenHTTPInterceptor.AUTHORIZATION_HEADER) String header) {
        if (jobOfferDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (!authTokenValidator.authorize(jobOfferDTO.getEmployerId(), bearerExtractor.extract(header))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        JobOffer jobOffer = jobOfferService.createJob(jobOfferDTO);

        jobOfferProducer.postJobOffer(jobOffer);

        return jobOffer;
    }

    @PostMapping("/update")
    public JobOffer updateJob(@RequestBody UpdateJobOfferDTO jobOfferDTO, @RequestHeader(AuthTokenHTTPInterceptor.AUTHORIZATION_HEADER) String header) {

        Optional<JobOffer> optional = jobOfferService.getJobOffer(jobOfferDTO.getId());

        if (optional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (!authTokenValidator.authorize(optional.get().getEmployerId(), bearerExtractor.extract(header))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Optional<JobOffer> jobOfferOptional = jobOfferService.updateJobOffer(jobOfferDTO);
        if (jobOfferOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        JobOffer jobOffer = jobOfferOptional.get();
        jobOfferProducer.postJobOffer(jobOffer);

        return jobOffer;
    }

    @GetMapping("/getJobApplications/{jobId}")
    public List<JobApplication> getJobApplications(@PathVariable String jobId) {
        Optional<JobOffer> jobOfferOptional = jobOfferService.getJobOffer(jobId);
        if (jobOfferOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return jobOfferOptional.get().getApplications();
    }

    @PostMapping("/acceptApplication/{jobId}/{applicationId}")
    public JobApplication acceptApplication(@PathVariable String jobId, @PathVariable String applicationId, @RequestHeader(AuthTokenHTTPInterceptor.AUTHORIZATION_HEADER) String header) {
        JobOffer jobOffer = jobOfferRepository.findById(jobId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!authTokenValidator.authorize(jobOffer.getEmployerId(), bearerExtractor.extract(header))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Optional<JobApplication> jobApplicationOptional = jobOffer.getApplications()
                .stream()
                .filter(application -> application.getId().equals(applicationId))
                .findFirst();

        jobOffer.setStatus(JobOfferStatus.IN_PROGRESS);

        if (jobApplicationOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        JobApplication jobApplication = jobApplicationOptional.get();

        jobApplication.setStatus(JobApplicationStatus.ACCEPTED);
        jobApplicationsProducer.postJobApplication(jobApplication);

        for (JobApplication application : jobOffer.getApplications()) {
            if (application.getId().equals(jobApplication.getId())) {
                continue;
            }

            application.setStatus(JobApplicationStatus.REJECTED);
            jobApplicationsProducer.postJobApplication(application);
        }

        jobOfferRepository.save(jobOffer);
        jobOfferProducer.postJobOffer(jobOffer);

        return jobApplication;
    }

    @PostMapping("/rejectApplication/{jobId}/{applicationId}")
    public JobApplication rejectApplication(@PathVariable String jobId, @PathVariable String applicationId, @RequestHeader(AuthTokenHTTPInterceptor.AUTHORIZATION_HEADER) String header) {
        JobOffer jobOffer = jobOfferRepository.findById(jobId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!authTokenValidator.authorize(jobOffer.getEmployerId(), bearerExtractor.extract(header))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Optional<JobApplication> jobApplicationOptional = jobOffer.getApplications()
                .stream()
                .filter(application -> application.getId().equals(applicationId))
                .findFirst();
        if (jobApplicationOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        JobApplication jobApplication = jobApplicationOptional.get();
        jobApplication.setStatus(JobApplicationStatus.REJECTED);

        jobApplicationsProducer.postJobApplication(jobApplication);

        jobOfferRepository.save(jobOffer);
        jobOfferProducer.postJobOffer(jobOffer);

        return jobApplication;
    }

    @PostMapping("/completeJob/{jobId}")
    public JobOffer updateJob(@PathVariable String jobId, @RequestHeader(AuthTokenHTTPInterceptor.AUTHORIZATION_HEADER) String header) {
        Optional<JobOffer> optional = jobOfferService.getJobOffer(jobId);
        if (optional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (!authTokenValidator.authorize(optional.get().getEmployerId(), bearerExtractor.extract(header))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Optional<JobOffer> jobOfferOptional;
        try {
            jobOfferOptional = jobOfferService.completeJob(jobId);
        } catch (JobApplicationNotCompletedException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }
        if (jobOfferOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        JobOffer jobOffer = jobOfferOptional.get();
        jobOfferProducer.postJobOffer(jobOffer);
        return jobOffer;
    }
}
