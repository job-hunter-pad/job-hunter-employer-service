package jobhunter.employerservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@AllArgsConstructor
@Value
public class UpdateJobOfferDTO {
    String id;
    String jobName;
    String jobDescription;
    Float hourSalaryAmount;
    List<String> skills;
}
