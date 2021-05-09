package jobhunter.employerservice.service;

import jobhunter.employerservice.model.JobApplication;
import jobhunter.employerservice.model.JobApplicationStatus;
import jobhunter.employerservice.model.JobOffer;
import jobhunter.employerservice.model.JobOfferStatus;
import jobhunter.employerservice.repository.JobOfferRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobOfferServiceImpl implements JobOfferService {
    private final JobOfferRepository jobOfferRepository;

    public JobOfferServiceImpl(JobOfferRepository jobOfferRepository) {
        this.jobOfferRepository = jobOfferRepository;
    }

    @Override
    public void addJobApplication(JobApplication jobApplication) {
        Optional<JobOffer> jobOfferOptional = jobOfferRepository.findById(jobApplication.getJobId());
        if (jobOfferOptional.isEmpty()) {
            return;
        }

        JobOffer jobOffer = jobOfferOptional.get();
        jobOffer.getApplications().add(jobApplication);
        jobOfferRepository.save(jobOffer);
    }

    @Override
    public Optional<JobOffer> completeJob(String jobId) throws JobApplicationNotCompletedException {

        Optional<JobOffer> optionalJobOffer = jobOfferRepository.findById(jobId);
        if (optionalJobOffer.isEmpty()) {
            return Optional.empty();
        }
        JobOffer jobOffer = optionalJobOffer.get();

        if (jobOffer.getApplications().stream().noneMatch(jobApplication -> jobApplication.getStatus().equals(JobApplicationStatus.COMPLETED))) {
            throw new JobApplicationNotCompletedException();
        }

        jobOffer.setStatus(JobOfferStatus.COMPLETED);

        return Optional.of(jobOfferRepository.save(jobOffer));
    }

    @Override
    public Optional<JobOffer> completeApplication(JobApplication jobApplication) {
        Optional<JobOffer> optionalJobOffer = jobOfferRepository.findById(jobApplication.getJobId());
        if (optionalJobOffer.isEmpty()) {
            return Optional.empty();
        }

        JobOffer jobOffer = optionalJobOffer.get();

        for (int i = 0; i < jobOffer.getApplications().size(); i++) {
            if (jobOffer.getApplications().get(i).getJobId().equals(jobApplication.getJobId())) {
                jobOffer.getApplications().set(i, jobApplication);
                jobOfferRepository.save(jobOffer);
                break;
            }
        }

        return Optional.of(jobOffer);
    }

    @Override
    public List<JobOffer> getAllCompletedJobOffersOfEmployer(String employerId) {
        return jobOfferRepository.findAllByEmployerId(employerId).stream()
                .filter(jobOffer -> jobOffer.getStatus() == JobOfferStatus.COMPLETED)
                .collect(Collectors.toList());
    }

    @Override
    public List<JobOffer> getAllNotCompletedJobOffersOfEmployer(String employerId) {
        return jobOfferRepository.findAllByEmployerId(employerId).stream()
                .filter(jobOffer -> jobOffer.getStatus() != JobOfferStatus.COMPLETED)
                .collect(Collectors.toList());
    }
}
