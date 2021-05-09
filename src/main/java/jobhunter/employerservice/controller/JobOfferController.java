package jobhunter.employerservice.controller;

import jobhunter.employerservice.controller.dto.CreateJobOfferDTO;
import jobhunter.employerservice.controller.dto.UpdateJobOfferDTO;
import jobhunter.employerservice.kafka.producer.JobApplicationsProducer;
import jobhunter.employerservice.kafka.producer.JobOfferProducer;
import jobhunter.employerservice.model.JobApplication;
import jobhunter.employerservice.model.JobApplicationStatus;
import jobhunter.employerservice.model.JobOffer;
import jobhunter.employerservice.model.JobOfferStatus;
import jobhunter.employerservice.repository.JobOfferRepository;
import jobhunter.employerservice.service.JobApplicationNotCompletedException;
import jobhunter.employerservice.service.JobOfferService;
import jobhunter.employerservice.utils.StringValidation;
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

    public JobOfferController(JobOfferRepository jobOfferRepository,
                              JobOfferService jobOfferService, JobOfferProducer jobOfferProducer,
                              JobApplicationsProducer jobApplicationsProducer) {
        this.jobOfferRepository = jobOfferRepository;
        this.jobOfferService = jobOfferService;
        this.jobOfferProducer = jobOfferProducer;
        this.jobApplicationsProducer = jobApplicationsProducer;
    }

    @GetMapping("/")
    public List<JobOffer> getAllJobOffers() {
        return jobOfferRepository.findAll();
    }

    @GetMapping("/{jobId}")
    public JobOffer getJobOffer(@PathVariable String jobId) {
        return jobOfferRepository.findById(jobId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/getJobOffers/{employerId}")
    public List<JobOffer> getJobOffersByEmployer(@PathVariable String employerId) {
        return jobOfferRepository.findAllByEmployerId(employerId);
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
    public JobOffer createJob(@RequestBody CreateJobOfferDTO jobOfferDTO) {
        if (jobOfferDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        JobOffer jobOffer = jobOfferRepository.save(jobOfferDTO.createJobOffer());

        jobOfferProducer.postJobOffer(jobOffer);

        return jobOffer;
    }

    @PostMapping("/update")
    public JobOffer updateJob(@RequestBody UpdateJobOfferDTO jobOfferDTO) {
        if (jobOfferDTO == null || StringValidation.IsStringEmpty(jobOfferDTO.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Optional<JobOffer> jobOfferOptional = jobOfferRepository.findById(jobOfferDTO.getId());
        if (jobOfferOptional.isPresent()) {
            JobOffer jobOffer = jobOfferOptional.get();

            if (StringValidation.IsStringNotEmpty(jobOfferDTO.getJobName())) {
                jobOffer.setJobName(jobOfferDTO.getJobName());
            }

            if (StringValidation.IsStringNotEmpty(jobOfferDTO.getJobDescription())) {
                jobOffer.setJobDescription(jobOfferDTO.getJobDescription());
            }

            if (jobOfferDTO.getHourSalaryAmount() != null) {
                jobOffer.setHourSalaryAmount(jobOfferDTO.getHourSalaryAmount());
            }

            JobOffer updatedJobOffer = jobOfferRepository.save(jobOffer);

            jobOfferProducer.postJobOffer(updatedJobOffer);

            return updatedJobOffer;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getJobApplications/{jobId}")
    public List<JobApplication> getJobApplications(@PathVariable String jobId) {
        JobOffer jobOffer = jobOfferRepository.findById(jobId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return jobOffer.getApplications();
    }

    @PostMapping("/acceptApplication/{jobId}/{applicationId}")
    public JobApplication acceptApplication(@PathVariable String jobId, @PathVariable String applicationId) {
        JobOffer jobOffer = jobOfferRepository.findById(jobId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
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
    public JobApplication rejectApplication(@PathVariable String jobId, @PathVariable String applicationId) {
        JobOffer jobOffer = jobOfferRepository.findById(jobId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
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
    public JobOffer updateJob(@PathVariable String jobId) {
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
