package jobhunter.employerservice.repository;

import jobhunter.employerservice.model.JobOffer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface JobOfferRepository extends MongoRepository<JobOffer, String> {
    List<JobOffer> findAllByEmployerId(String employerId);
}
