package com.umg.absolwentbackend.controllers;
import com.umg.absolwentbackend.models.Data;
import com.umg.absolwentbackend.services.DataService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/s_api/data") // TODO: Must be change to: /api/data -> Filter settings must be set.
public class DataController
{
    @Autowired
    DataService dataService;

    // URL: localhost:6362/s_api/data/
    @GetMapping("/")
    public ResponseEntity<Iterable<Data>> findAll()
    {
        return new ResponseEntity<>(dataService.findAll(), HttpStatus.OK);
    }

    // URL: localhost:6362/s_api/data/test
    @GetMapping("/test")
    public String testConnection()
    {
        return "Data controller is working !";
    }

    // URL: localhost:6362/s_api/data/all?gender=mezczyzna&year=2022
    @GetMapping("/all")
    public ResponseEntity<Iterable<Data>> getAllByGenderAndYear(@RequestParam("gender") String gender, @RequestParam("year") Integer year)
    {
        System.out.println("Get data-----]\nGender: "+gender+"\nYear: "+year+"\nGet data-----]");
        return new ResponseEntity<Iterable<Data>>(dataService.getAllByGenderAndYear(gender, year), HttpStatus.OK);
    }

    // URL: localhost:6362/s_api/data/period?gender=mezczyzna&year=2022
    @GetMapping("/period")
    public ResponseEntity<Iterable<JSONObject>> getPeriodOfEmployementByGenderAndYear (@RequestParam("gender") String gender, @RequestParam("year") Integer year)
    {
        System.out.println("Get data-----]\nGender: "+gender+"\nYear: "+year+"\nGet data-----]");
        return new ResponseEntity<>(dataService.getPeriodOfEmployementByGenderAndYear(gender, year), HttpStatus.OK);
    }

    // URL: localhost:6362/s_api/data/company?gender=mezczyzna&year=2022
    @GetMapping("/company")
    public ResponseEntity<Iterable<JSONObject>> getCompanyCategoryByGenderAndYear (@RequestParam("gender") String gender, @RequestParam("year") Integer year)
    {
        System.out.println("Get data-----]\nGender: "+gender+"\nYear: "+year+"\nGet data-----]");
        return new ResponseEntity<>(dataService.getCompanyCategoryByGenderAndYear(gender, year), HttpStatus.OK);
    }

    // URL: localhost:6362/s_api/data/companySize?gender=mezczyzna&year=2022
    @GetMapping("/companySize")
    public ResponseEntity<Iterable<JSONObject>> getCompanySizeByGenderAndYear (@RequestParam("gender") String gender, @RequestParam("year") Integer year)
    {
        System.out.println("Get data-----]\nGender: "+gender+"\nYear: "+year+"\nGet data-----]");
        return new ResponseEntity<>(dataService.getCompanySizeByGenderAndYear(gender, year), HttpStatus.OK);
    }

    // URL: localhost:6362/s_api/data/jobSearchTime?gender=mezczyzna&year=2022
    @GetMapping("/jobSearchTime")
    public ResponseEntity<Iterable<JSONObject>> getSearchTimeByGenderAndYear (@RequestParam("gender") String gender, @RequestParam("year") Integer year)
    {
        System.out.println("Get data-----]\nGender: "+gender+"\nYear: "+year+"\nGet data-----]");
        return new ResponseEntity<>(dataService.getJobSearchTimeByGenderAndYear(gender, year), HttpStatus.OK);
    }

    // URL: localhost:6362/s_api/data/salary?gender=mezczyzna&year=2022
    @GetMapping("/salary")
    public ResponseEntity<Iterable<JSONObject>> getSalaryByGenderAndYear (@RequestParam("gender") String gender, @RequestParam("year") Integer year)
    {
        System.out.println("Get data-----]\nGender: "+gender+"\nYear: "+year+"\nGet data-----]");
        return new ResponseEntity<>(dataService.getSalaryByGenderAndYear(gender, year), HttpStatus.OK);
    }
}
