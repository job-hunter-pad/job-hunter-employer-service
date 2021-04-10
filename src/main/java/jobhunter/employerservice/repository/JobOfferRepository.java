package jobhunter.employerservice.repository;

import jobhunter.employerservice.model.JobOffer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobOfferRepository extends MongoRepository<JobOffer, String> {

}
