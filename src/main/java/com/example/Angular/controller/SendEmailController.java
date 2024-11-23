package com.example.Angular.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Angular.dto.EmailTemplate;
import com.example.Angular.service.EmailService;

@RestController
public class SendEmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/api/v1/auth/sendEmail")
    public String sendEmail() {
        try {
            emailService.sendSimpleEmail("buccalaty@gmail.com", "Test Subject", "Negro");
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send email!";
        }

        return "Email sent!";
    }

    @PostMapping("/api/v1/auth/sendEmail")
    public String PostEmail(@RequestBody EmailTemplate emailTemplate) {
        try {
            emailService.sendSimpleEmail(emailTemplate.toEmail, emailTemplate.subject, emailTemplate.body);
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send email!";
        }

        return "Email sent!";
    }
}
