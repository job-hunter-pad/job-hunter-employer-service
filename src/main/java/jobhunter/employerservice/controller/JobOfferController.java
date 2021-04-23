package jobhunter.employerservice.controller;

import jobhunter.employerservice.controller.dto.CreateJobOfferDTO;
import jobhunter.employerservice.controller.dto.UpdateJobOfferDTO;
import jobhunter.employerservice.model.JobApplication;
import jobhunter.employerservice.model.JobApplicationStatus;
import jobhunter.employerservice.model.JobOffer;
import jobhunter.employerservice.repository.JobOfferRepository;
import jobhunter.employerservice.utils.StringValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class JobOfferController {
    @Autowired
    private JobOfferRepository jobOfferRepository;

    @GetMapping("/")
    public List<JobOffer> getAllJobOffers() {
        return jobOfferRepository.findAll();
    }

    @GetMapping("/{jobId}")
    public JobOffer getJobOffer(@PathVariable String jobId) {
        return jobOfferRepository.findById(jobId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/getEmployerJobOffers/{employerId}")
    public List<JobOffer> getJobOffersByEmployer(@PathVariable String employerId) {
        return jobOfferRepository.findAllByEmployerId(employerId);
    }

    @PostMapping("/create")
    public JobOffer createJob(@RequestBody CreateJobOfferDTO jobOfferDTO) {
        if (jobOfferDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return jobOfferRepository.save(jobOfferDTO.createJobOffer());
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

            if (!jobOffer.isPaid()) {
                jobOffer.setPaid(jobOfferDTO.isPaid());
            }

            if (!jobOffer.isDone()) {
                jobOffer.setDone(jobOfferDTO.isDone());
            }

            return jobOfferRepository.save(jobOffer);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getJobApplications/{jobId}")
    public List<JobApplication> getJobApplications(@PathVariable String jobId) {
        JobOffer jobOffer = jobOfferRepository.findById(jobId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return jobOffer.getApplications();
    }

    @PostMapping("/acceptApplication/{jobId}/{applicationId}")
    public void acceptApplication(@PathVariable String jobId, @PathVariable String applicationId) {
        JobOffer jobOffer = jobOfferRepository.findById(jobId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Optional<JobApplication> jobApplication = jobOffer.getApplications()
                .stream()
                .filter(application -> application.getId().equals(applicationId))
                .findFirst();

        if (jobApplication.isPresent()) {
            jobApplication.get().setStatus(JobApplicationStatus.ACCEPTED);
        }
    }

    @PostMapping("/rejectApplication/{jobId}/{applicationId}")
    public void rejectApplication(@PathVariable String jobId, @PathVariable String applicationId) {
        JobOffer jobOffer = jobOfferRepository.findById(jobId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Optional<JobApplication> jobApplication = jobOffer.getApplications()
                .stream()
                .filter(application -> application.getId().equals(applicationId))
                .findFirst();

        if (jobApplication.isPresent()) {
            jobApplication.get().setStatus(JobApplicationStatus.REJECTED);
        }
    }

}
