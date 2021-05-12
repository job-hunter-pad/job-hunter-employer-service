package jobhunter.employerservice.controller.authorization;

public interface AuthTokenValidator {
    boolean authorize(String id, String token);
}
