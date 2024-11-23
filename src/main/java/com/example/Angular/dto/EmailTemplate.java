package com.example.Angular.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class EmailTemplate {
    public String toEmail;
    public String subject;
    public String body;
}
