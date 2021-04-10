package jobhunter.employerservice.controller;

import jobhunter.employerservice.controller.dto.CreateJobOfferDTO;
import jobhunter.employerservice.model.JobOffer;
import jobhunter.employerservice.repository.JobOfferRepository;
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
        Optional<JobOffer> jobOffer = jobOfferRepository.findById(jobId);

        if (jobOffer.isPresent()) {
            return jobOffer.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public JobOffer createJob(@RequestBody CreateJobOfferDTO jobOfferDTO) {
        return jobOfferRepository.save(new JobOffer(jobOfferDTO));
    }
}
