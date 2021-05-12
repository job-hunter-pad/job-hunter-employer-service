package jobhunter.employerservice.interceptor;

public interface BearerExtractor {
    String extract(String header);
}