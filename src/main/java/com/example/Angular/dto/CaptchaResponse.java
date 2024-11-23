package com.example.Angular.dto;

import lombok.Data;

@Data
public class CaptchaResponse {
    Boolean success;
    String challenge_ts;
    String hostname;

}
