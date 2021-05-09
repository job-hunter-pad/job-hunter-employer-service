package jobhunter.employerservice.service;

import jobhunter.employerservice.controller.dto.CreateJobOfferDTO;
import jobhunter.employerservice.controller.dto.UpdateJobOfferDTO;
import jobhunter.employerservice.model.JobApplication;
import jobhunter.employerservice.model.JobOffer;

import java.util.List;
import java.util.Optional;

public interface JobOfferService {
    void addJobApplication(JobApplication jobApplication);

    Optional<JobOffer> completeJob(String jobId) throws JobApplicationNotCompletedException;

    Optional<JobOffer> completeApplication(JobApplication jobApplication);

    List<JobOffer> getAllCompletedJobOffersOfEmployer(String employerId);

    List<JobOffer> getAllNotCompletedJobOffersOfEmployer(String employerId);

    List<JobOffer> getAllJobOffers();

    Optional<JobOffer> getJobOffer(String jobId);

    List<JobOffer> getJobOfferByEmployer(String employerId);

    JobOffer createJob(CreateJobOfferDTO jobOfferDTO);

    Optional<JobOffer> updateJobOffer(UpdateJobOfferDTO jobOfferDTO);
}
