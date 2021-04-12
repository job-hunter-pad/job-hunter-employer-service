package jobhunter.employerservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class UpdateJobOfferDTO {
    String id;
    String jobName;
    String jobDescription;
    Float hourSalaryAmount;
    boolean paid;
    boolean done;
}
