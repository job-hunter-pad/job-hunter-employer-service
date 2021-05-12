package jobhunter.employerservice.controller.authorization;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthTokenValidatorImpl implements AuthTokenValidator {
    @Override
    public boolean authorize(String id, String token) {

        String auth_url = System.getenv("AUTH_URL");
        final String validateIdUrl = auth_url + "/validateId";

        RestTemplate restTemplate = new RestTemplate();
        ValidateIdRequest validateIdRequest = new ValidateIdRequest(id, token);

        ValidateIdResponse result;
        try {
            result = restTemplate.postForObject(validateIdUrl, validateIdRequest, ValidateIdResponse.class);
        } catch (Exception ignored) {
            return false;
        }
        if (result == null) {
            return false;
        }

        return result.isValid();
    }
}
