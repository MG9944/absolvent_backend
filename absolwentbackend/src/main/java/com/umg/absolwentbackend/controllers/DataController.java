package com.umg.absolwentbackend.controllers;
import com.umg.absolwentbackend.models.Data;
import com.umg.absolwentbackend.services.DataService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/public/statistics") // TODO: Must be change to: /api/data -> Filter settings must be set.
public class DataController
{
    /*

    Połączenie z bazą nie działało.
    Pozdro.

     */

    @Autowired
    DataService dataService;

    // URL: localhost:6362/api/statistics/
    @GetMapping("/")
    public ResponseEntity<Iterable<Data>> findAll()
    {
        List<Data> all = dataService.findAll();
        System.out.println(all);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    // URL: localhost:6362/api/statistics/test
    @GetMapping("/test_json_object")
    public ResponseEntity<JSONObject> test0()
    {
        JSONObject obj = new JSONObject();
        obj.put("Test0", "Value - test 0");
        obj.put("Test1", "Value - test 1");
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    // URL: localhost:6362/api/statistics/test
    @GetMapping("/test_string")
    public String testConnection()
    {
        return "Data controller is working !";
    }

    // URL: localhost:6362/api/statistics/all/2020?gender=mezczyzna
    @GetMapping("/all/{year}")
    public ResponseEntity<Iterable<Data>> getAllByGenderAndYear(@PathVariable("year") Integer year, @RequestParam("gender") String gender)
    {
        System.out.println("Get data-----]\nGender: "+gender+"\nYear: "+year+"\nGet data-----]");
        return new ResponseEntity<Iterable<Data>>(dataService.getAllByGenderAndYear(gender, year), HttpStatus.OK);
    }

    // URL: localhost:6362/api/public/statistics/period/2020?gender=mezczyzna
    @GetMapping("/period/{year}")
    public ResponseEntity<JSONObject> getPeriodOfEmployementByGenderAndYear (@PathVariable("year") Integer year, @RequestParam("gender") String gender)
    {
        /*
        System.out.println("Get data-----]\nGender: "+gender+"\nYear: "+year+"\nGet data-----]");
        return new ResponseEntity<>(dataService.getPeriodOfEmployementByGenderAndYear(gender, year), HttpStatus.OK);
         */

        JSONArray jsonArray = new JSONArray();
        String[]data={"2015", "2019", "2014", "2012", "2011", "2000", "2022", "2001"};
        int[]count={154, 52, 63, 169, 38, 90, 146, 9};
        for(int i=1; i<=8; i++)
        {
            JSONObject obj = new JSONObject();
            obj.put("id", i);
            obj.put("name", data[i-1]);
            obj.put("count", count[i-1]);
            jsonArray.add(obj);
        }
        JSONObject data_object = new JSONObject();
        data_object.put("answersCount", 721);
        data_object.put("answers", jsonArray);
        data_object.put("name", "Rok zatrudnienia");
        return new ResponseEntity<>(data_object, HttpStatus.OK);
    }

    // URL: localhost:6362/api/public/statistics/company/2020?gender=mezczyzna
    @GetMapping("/company/{year}")
    public ResponseEntity<JSONObject> getCompanyCategoryByGenderAndYear (@PathVariable("year") Integer year, @RequestParam("gender") String gender)
    {
        /*
        System.out.println("Get data-----]\nGender: "+gender+"\nYear: "+year+"\nGet data-----]");
        return new ResponseEntity<>(dataService.getCompanyCategoryByGenderAndYear(gender, year), HttpStatus.OK);
         */
        JSONArray jsonArray = new JSONArray();
        String[]data={"AMD", "Intel", "Sii", "Nvidia", "Boeing", "WP", "NASA", "Meta"};
        int[]count={154, 52, 63, 169, 38, 90, 146, 9};
        for(int i=1; i<=8; i++)
        {
            JSONObject obj = new JSONObject();
            obj.put("id", i);
            obj.put("name", data[i-1]);
            obj.put("count", count[i-1]);
            jsonArray.add(obj);
        }
        JSONObject data_object = new JSONObject();
        data_object.put("answersCount", 721);
        data_object.put("answers", jsonArray);
        data_object.put("name", "Firmy");
        return new ResponseEntity<>(data_object, HttpStatus.OK);
    }

    // URL: localhost:6362/api/public/statistics/companySize/2020?gender=mezczyzna
    @GetMapping("/companySize/{year}")
    public ResponseEntity<JSONObject> getCompanySizeByGenderAndYear (@PathVariable("year") Integer year, @RequestParam("gender") String gender)
    {
        /*
        System.out.println("Get data-----]\nGender: "+gender+"\nYear: "+year+"\nGet data-----]");
        return new ResponseEntity<>(dataService.getCompanySizeByGenderAndYear(gender, year), HttpStatus.OK);
         */
        JSONArray jsonArray = new JSONArray();
        String[]data={"30-44", "44-59", "59-74", "74-89", "89-104", "104-119", "119-149", "149+"};
        int[]count={154, 52, 63, 169, 38, 90, 146, 9};
        for(int i=1; i<=8; i++)
        {
            JSONObject obj = new JSONObject();
            obj.put("id", i);
            obj.put("name", data[i-1]);
            obj.put("count", count[i-1]);
            jsonArray.add(obj);
        }
        JSONObject data_object = new JSONObject();
        data_object.put("answersCount", 721);
        data_object.put("answers", jsonArray);
        data_object.put("name", "Wielkość firmy");
        return new ResponseEntity<>(data_object, HttpStatus.OK);
    }

    // URL: localhost:6362/api/public/statistics/jobSearchTime/2020?gender=mezczyzna
    @GetMapping(value="/jobSearchTime/{year}")
    public @ResponseBody ResponseEntity<JSONObject>  getSearchTimeByGenderAndYear (@PathVariable("year") Integer year, @RequestParam("gender") String gender)
    {
        /*
        System.out.println("Get data-----]\nGender: "+gender+"\nYear: "+year+"\nGet data-----]");
        return new ResponseEntity<>(dataService.getJobSearchTimeByGenderAndYear(gender, year), HttpStatus.OK);
         */

        JSONArray jsonArray = new JSONArray();
        String[]data={"30-44", "44-59", "59-74", "74-89", "89-104", "104-119", "119-149", "149+"};
        int[]count={154, 52, 63, 169, 38, 90, 146, 9};
        for(int i=1; i<=8; i++)
        {
            JSONObject obj = new JSONObject();
            obj.put("id", i);
            obj.put("name", data[i-1]);
            obj.put("count", count[i-1]);
            jsonArray.add(obj);
        }
        JSONObject data_object = new JSONObject();
        data_object.put("answersCount", 721);
        data_object.put("answers", jsonArray);
        data_object.put("name", "Czas szukania pracy");
        return new ResponseEntity<>(data_object, HttpStatus.OK);
    }

    // URL: localhost:6362/api/public/statistics/salary/2020?gender=mezczyzna
    @GetMapping(value="/salary/{year}")
    public @ResponseBody ResponseEntity<JSONObject> getSalaryByGenderAndYear(@PathVariable("year") Integer year, @RequestParam("gender") String gender) {
        System.out.println("Get data-----]\nGender: "+gender+"\nYear: "+year+"\nGet data-----]");

        JSONArray jsonArray = new JSONArray();
        String[]data={"3010-4400", "4401-5900", "5901-7400", "7401-8900", "8901-10400", "10401-11900", "11901-14900", "14900+"};
        int[]count={154, 52, 63, 169, 38, 90, 146, 9};
        for(int i=1; i<=8; i++)
        {
            JSONObject obj = new JSONObject();
            obj.put("id", i);
            obj.put("name", data[i-1]);
            obj.put("count", count[i-1]);
            jsonArray.add(obj);
        }
        JSONObject data_object = new JSONObject();
        data_object.put("answersCount", 721);
        data_object.put("answers", jsonArray);
        data_object.put("name", "Przedział zarobkowy brutto");

        return new ResponseEntity<>(data_object, HttpStatus.OK);
    }
}
