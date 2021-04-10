package jobhunter.employerservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class JobApplication {
    @Id
    private String id;

    private String freelancerId;
    private String freelancerName;
    private float hourSalaryAmount;
    private int estimatedProjectCompleteTime;
    private String message;
    private JobApplicationStatus status;
}
