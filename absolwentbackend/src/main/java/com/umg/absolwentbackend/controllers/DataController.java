package com.umg.absolwentbackend.controllers;
import com.umg.absolwentbackend.models.Data;
import com.umg.absolwentbackend.services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/s_api/data")
public class DataController
{
    @Autowired
    DataService dataService;

    @GetMapping("/")
    public ResponseEntity<Iterable<Data>> findAll()
    {
        return new ResponseEntity<>(dataService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/")
    public boolean sendData(@RequestBody Data data)
    {
        return dataService.sendData(data.getEnding_date(), data.getGender(), data.getEarnings(), data.getCompany_size(), data.getTown_size(), data.getCompany_category(), data.getJob_search_time(), data.getPeriod_of_employment(), data.getQuestionnarie_id(), data.isLocation(), data.isProffesional_activity(), data.isLocation(), data.isTraining());
    }

    @GetMapping("/test")
    public String testConnection()
    {
        return "Data controller is working !";
    }

    @GetMapping("/allByGenderAndYear")
    public ResponseEntity<Iterable<Data>> getAllByGenderAndYear(@RequestParam("gender") String gender, @RequestParam("year") Integer year)
    {
        System.out.println("Get data-----]\nGender: "+gender+"\nYear: "+year+"\nGet data-----]");
        return new ResponseEntity<>(dataService.getAllByGenderAndYear(gender, year), HttpStatus.OK);
    }

    @GetMapping("/periodOfEmployementByGenderAndYear")
    public ResponseEntity<Iterable<String>> getPeriodOfEmployementByGenderAndYear (@RequestParam("gender") String gender, @RequestParam("year") Integer year)
    {
        System.out.println("Get data-----]\nGender: "+gender+"\nYear: "+year+"\nGet data-----]");
        return new ResponseEntity<>(dataService.getPeriodOfEmployementByGenderAndYear(gender, year), HttpStatus.OK);
    }

    @GetMapping("/companyCategoryByGenderAndYear")
    public ResponseEntity<Iterable<String>> getCompanyCategoryByGenderAndYear (@RequestParam("gender") String gender, @RequestParam("year") Integer year)
    {
        System.out.println("Get data-----]\nGender: "+gender+"\nYear: "+year+"\nGet data-----]");
        return new ResponseEntity<>(dataService.getCompanyCategoryByGenderAndYear(gender, year), HttpStatus.OK);
    }

    @GetMapping("/companySizeByGenderAndYear")
    public ResponseEntity<Iterable<String>> getCompanySizeByGenderAndYear (@RequestParam("gender") String gender, @RequestParam("year") Integer year)
    {
        System.out.println("Get data-----]\nGender: "+gender+"\nYear: "+year+"\nGet data-----]");
        return new ResponseEntity<>(dataService.getCompanySizeByGenderAndYear(gender, year), HttpStatus.OK);
    }

    @GetMapping("/jobSearchTimeByGenderAndYear")
    public ResponseEntity<Iterable<String>> getSearchTimeByGenderAndYear (@RequestParam("gender") String gender, @RequestParam("year") Integer year)
    {
        System.out.println("Get data-----]\nGender: "+gender+"\nYear: "+year+"\nGet data-----]");
        return new ResponseEntity<>(dataService.getJobSearchTimeByGenderAndYear(gender, year), HttpStatus.OK);
    }
}
