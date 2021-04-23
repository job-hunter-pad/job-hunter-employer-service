# Job Hunter Employer Service

### Mappings

| Service URL | API Gateway URL | Method | Description |
| ------ | ------ | ------ | ------ |
| / | /api/jobs | GET | Get All Job Offers |
| /{jobId} | /api/jobs/{jobId} | GET | Get the Job Offer by Id |
| /getEmployerJobOffers/{employerId} | /api/jobs/getEmployerJobOffers/{employerId} | GET | Get all Job Offers made by the Employer with the id of "employerId" |
| /create | /api/jobs/create | POST | Create a new Job Offer |
| /update | /api/jobs/update | POST | Update a Job Offer |
| /getJobApplications/{jobId} | /api/jobs/getJobApplications/{jobId} | GET | Get All the Job Applications of the Job Offer with the id of "jobId" |
| /acceptApplication/{jobId}/{applicationId} | /api/jobs/acceptApplication/{jobId}/{applicationId} | POST | Application Status Will be changed to "ACCEPTED" for the Application with the id "applicationId" for the Job Offer with the id "jobId" |
| /rejectApplication/{jobId}/{applicationId} | /api/jobs/rejectApplication/{jobId}/{applicationId} | POST | Application Status Will be changed to "REJECTED" for the Application with the id "applicationId" for the Job Offer with the id "jobId" |

### Mappings Request And Responses

#### Response Job Offer

```json
{
  "id": "",
  "jobName": "",
  "jobDescription": "",
  "date": "",
  "employerId": "",
  "employerName": "",
  "hourSalaryAmount": 0.0,
  "paid": false,
  "done": false,
  "applications": []
}
```

#### Response Job Offer Application

```json
{
  "id": "",
  "jobId": "",
  "freelancerId": "",
  "freelancerName": "",
  "hourSalaryAmount": 0.0,
  "estimatedProjectCompleteTime": 0,
  "message": "",
  "status": ""
}
```

#### Request Create Job Offer

```json
{
  "jobName": "",
  "jobDescription": "",
  "date": "",
  "employerId": "",
  "employerName": "",
  "hourSalaryAmount": 0.0
}
```

#### Request Update Job Offer

```json
{
  "id": "",
  "jobName": "",
  "jobDescription": "",
  "hourSalaryAmount": 0.0,
  "paid": false,
  "done": false
}
```


