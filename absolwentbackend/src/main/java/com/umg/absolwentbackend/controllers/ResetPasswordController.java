package com.umg.absolwentbackend.controllers;

import com.umg.absolwentbackend.repositories.UniversityRepository;
import com.umg.absolwentbackend.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/password")
public class ResetPasswordController {


    @Autowired
    private EmailService emailSender;
    @Autowired
    private UniversityRepository universityRepository;

    @PostMapping("/reset")
    public ResponseEntity<Map<String,Object>> sendMail(@RequestBody Map<String, Object> universityMap) {
        String universityEmail = (String) universityMap.get("email");
        String title = "Prośba o zmiane hasła";
        String body = "Wiadomość";

        //Wysyłanie emaila
        try {
            emailSender.sendEmail(universityEmail, title, body);

            Map<String, Object> map = new HashMap<>();
            map.put("success", true);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        catch (Exception e){
            Map<String, Object> map = new HashMap<>();
            map.put("error", true);
            map.put("status", 500);
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/change")
    public ResponseEntity<Map<String,Object>> changePassword(@RequestBody Map<String, Object> universityMap){

        String newPassword = (String) universityMap.get("newPassword");

        //Zmiana hasła w bazie
        String hashedPass = BCrypt.hashpw(newPassword,BCrypt.gensalt(10));
        try {
            if (newPassword.length() < 8) {
                Map<String, Object> map = new HashMap<>();
                map.put("error", true);
                map.put("message", "Password is too short");
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            }
            universityRepository.updatePassword(hashedPass);
            Map<String, Object> map = new HashMap<>();
            map.put("success", true);
            //map.put("value", newPassword);
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        catch (Exception e){
            Map<String, Object> map = new HashMap<>();
            map.put("error", true);
            map.put("status", 500);
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
