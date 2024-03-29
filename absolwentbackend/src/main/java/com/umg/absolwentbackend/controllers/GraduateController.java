package com.umg.absolwentbackend.controllers;

import com.umg.absolwentbackend.Constants;
import com.umg.absolwentbackend.exceptions.AuthenticationException;
import com.umg.absolwentbackend.models.Graduate;
import com.umg.absolwentbackend.models.Group;
import com.umg.absolwentbackend.repositories.GraduateRepository;
import com.umg.absolwentbackend.services.DataService;
import com.umg.absolwentbackend.services.EmailService;
import com.umg.absolwentbackend.services.GraduateService;
import com.umg.absolwentbackend.services.GroupService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.crypto.SecretKey;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class GraduateController {

    @Autowired
    GraduateService graduateService;
    @Autowired
    private DataService dataService;
    @Autowired
    private EmailService emailSender;
    @Autowired
    private GraduateRepository graduateRepository;
    @Autowired
    private GroupService groupService;

   @DeleteMapping("/graduate/delete")
   public ResponseEntity<Map<String,Object>> delete(HttpServletRequest request, @RequestBody Map<String, Object> paramMap) {
       int graduate_id = (Integer) paramMap.get("graduateId");
           var success = graduateService.delete(graduate_id);
           if (!success) {
               Map<String, Object> map = new HashMap<>();
               map.put("success", false);
               map.put("message", "No student specified");
               return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
           }
           Map<String, Object> map = new HashMap<>();
           map.put("success", true);
           return new ResponseEntity<>(map, HttpStatus.OK);
   }

    @GetMapping("/graduate/list")
    public ResponseEntity<Map<String,Object>> getAll(HttpServletRequest request) {
            List<Graduate> success = graduateRepository.getAll();
            if (success == null) {
                Map<String, Object> map = new HashMap<>();
                map.put("success", false);
                map.put("message", "No student data available");
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            }
            Map<String, Object> map = new HashMap<>();
            map.put("success", true);
            map.put("graduates", success);
            return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/graduate")
    public ResponseEntity<Map<String,Object>> addGraduate(@RequestBody Map<String, Object> graduateMap)
    {
         String name = (String) graduateMap.get("name");
         String lastname = (String) graduateMap.get("lastName");
         //Date date_of_birth = Date.valueOf(LocalDate.parse((CharSequence) graduateMap.get("dateOfBirth")));
         String email = (String) graduateMap.get("email");
         int graduation_year = Integer.parseInt(graduateMap.get("graduationYear").toString());
         String field = (String) graduateMap.get("field");
         String title = (String) graduateMap.get("title");
         String faculty = (String) graduateMap.get("faculty");
         String group = (String) graduateMap.get("group");
         String gender = (String) graduateMap.get("gender");
         Graduate graduate = null;
         try{
             graduate = graduateService.addGraduate(name,lastname,email,graduation_year,faculty,field,title,group,gender);
         } catch (AuthenticationException e) {
             Map<String, Object> map = new HashMap<>();
             map.put("success", false);
             map.put("message", e.getMessage());
             return new ResponseEntity<>(map, HttpStatus.CONFLICT);
         }
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("value", graduate);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @PostMapping("/survey")
    public ResponseEntity<Map<String,Object>> sendMail(@RequestBody Map<String, Object> graduateMap) {
        String groupName = (String) graduateMap.get("group_name");
        Integer validDays=(Integer) graduateMap.get("valid_days");
        List<Map<String, Object>> graduateEmails = graduateRepository.findGroupEmails(groupName);
        Group group = null;
        try{
            group = groupService.validateGroup(groupName);
        }catch (Exception e){
            Map<String,Object> map = new HashMap<>();
            map.put("success", false);
            map.put("message", e.getMessage());
            return  new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }


        if(groupName.isEmpty() || validDays==null) {
            Map<String, Object> map = new HashMap<>();
            map.put("success", false);
            map.put("status", 500);
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //Wysyłanie emaila
        new sendMails(graduateEmails,validDays,emailSender).start();
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("status", 200);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/group/survey")
    public ResponseEntity<Map<String,Object>> sendMailToGraduatesInGroup(@RequestBody Map<String, Object> graduateMap) {
        String groupName = (String)graduateMap.get("group_name");
        List<Map<String, Object>> graduateEmails = graduateRepository.findGroupEmails(groupName);
        Group group = null;
        try{
            group = groupService.validateGroup(groupName);
        }catch (Exception e){
            Map<String,Object> map = new HashMap<>();
            map.put("success", false);
            map.put("message", e.getMessage());
            return  new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }


        if(groupName.isEmpty()) {
            Map<String, Object> map = new HashMap<>();
            map.put("success", false);
            map.put("status", 500);
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //Wysyłanie emaila
        new sendMails(graduateEmails,1,emailSender).start();
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("status", 200);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }




}
