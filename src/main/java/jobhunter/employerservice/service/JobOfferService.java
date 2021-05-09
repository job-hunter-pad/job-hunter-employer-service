package jobhunter.employerservice.service;

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
}
