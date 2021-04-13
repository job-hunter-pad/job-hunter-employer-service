package jobhunter.employerservice.kafka.listener;

import jobhunter.employerservice.model.JobApplication;
import jobhunter.employerservice.model.JobOffer;
import jobhunter.employerservice.repository.JobOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class Consumer {

    @Autowired
    private JobOfferRepository jobOfferRepository;

    @KafkaListener(topics = "job_application", groupId = "group_job_application", containerFactory = "kafkaListenerContainerFactory")
    public void consumeJobApplication(JobApplication jobApplication) {
        System.out.println("Consumed JSON Message: " + jobApplication);

        JobOffer jobOffer = jobOfferRepository.findById(jobApplication.getJobId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        jobOffer.getApplications().add(jobApplication);

        System.out.println("Job Application added");
    }

}
