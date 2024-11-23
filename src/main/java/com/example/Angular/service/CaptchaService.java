package com.example.Angular.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

@Service
public class CaptchaService {

    private static final String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
    private static final String SECRET_KEY = "6LfEC4UqAAAAAMRhrNFOEFYgOMtdJ5XWKgqvBuE-"; // Remplacez par votre clé
                                                                                         // secrète

    public boolean validateCaptcha(String captchaToken) {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("secret", SECRET_KEY);
        requestParams.add("response", captchaToken);

        ResponseEntity<Map> response = restTemplate.postForEntity(RECAPTCHA_VERIFY_URL, requestParams, Map.class);

        if (response.getBody() != null) {
            System.out.println("Response from reCAPTCHA: " + response.getBody()); // Ajoutez cette ligne
            Boolean success = (Boolean) response.getBody().get("success");
            return success != null && success;
        }
        return false;
    }

}
