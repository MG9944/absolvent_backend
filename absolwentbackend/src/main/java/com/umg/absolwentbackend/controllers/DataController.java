package com.umg.absolwentbackend.controllers;
import com.umg.absolwentbackend.models.Results;
import com.umg.absolwentbackend.repositories.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/data")
public class DataController
{
    @Autowired
    DataRepository dataRepository;
    @GetMapping("/{version}")
    public ResponseEntity<Map<String,Object>> getResults(@PathVariable("version") String version)
    {
        Map<String, Object> map = new HashMap<>();


        switch(version){
            //TODO - DONE!!!
            case "year":
            {
                map.put("success", true);
                map.put("status", 200);
                Map<String, Object> data = new HashMap<>();
                ArrayList<Integer> years = new ArrayList<>();
                ArrayList<Map<String,Object>> paMaps = new ArrayList<>();
                ArrayList<Map<String,Object>> poeMaps = new ArrayList<>();
                ArrayList<Map<String,Object>> jsMaps = new ArrayList<>();
                ArrayList<Map<String,Object>> tMaps = new ArrayList<>();
                ArrayList<Map<String,Object>> eMaps = new ArrayList<>();
                List<Results> List = dataRepository.getPaCountByYear();
                for (Results R:List) {
                    if(years.indexOf(R.getFilling_year()) == -1) {
                        years.add(R.getFilling_year());
                        paMaps.add(new HashMap<>());
                        poeMaps.add(new HashMap<>());
                        jsMaps.add(new HashMap<>());
                        tMaps.add(new HashMap<>());
                        eMaps.add(new HashMap<>());
                    }
                    paMaps.get(years.indexOf(R.getFilling_year())).put(R.getStringValue().toString(), R.getCount());
                }
                List = dataRepository.getPoeCountByYear();
                for (Results R:List) {
                    poeMaps.get(years.indexOf(R.getFilling_year())).put(R.getStringValue(), R.getCount());
                }
                List = dataRepository.getJsCountByYear();
                for (Results R:List) {
                    jsMaps.get(years.indexOf(R.getFilling_year())).put(R.getStringValue(), R.getCount());
                }
                List = dataRepository.getTCountByYear();
                for (Results R:List) {
                    tMaps.get(years.indexOf(R.getFilling_year())).put(R.getStringValue(), R.getCount());
                }
                List = dataRepository.getECountByYear();
                for (Results R:List) {
                   eMaps.get(years.indexOf(R.getFilling_year())).put(R.getStringValue(), R.getCount());
                }
                Map<String, Object> year = new HashMap<>();
                for(int i = 0;i<years.size();i++)
                {
                    year.put("proffesional_activity",paMaps.get(i));
                    year.put("period_of_employment",poeMaps.get(i));
                    year.put("job_satisfaction",jsMaps.get(i));
                    year.put("title",tMaps.get(i));
                    year.put("earnings",eMaps.get(i));
                    data.put(years.get(i).toString(),year);
                    year = new HashMap<>();
                }
                map.put("data",data);
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
            case "sex":
            {
                map.put("success", true);
                map.put("status", 200);
                Map<String, Object> data = new HashMap<>();
                ArrayList<String> sexes = new ArrayList<>();
                ArrayList<Map<String,Object>> eMaps = new ArrayList<>();
                ArrayList<Map<String,Object>> jstMaps = new ArrayList<>();
                ArrayList<Map<String,Object>> csMaps = new ArrayList<>();
                ArrayList<Map<String,Object>> tMaps = new ArrayList<>();
                ArrayList<Map<String,Object>> tsMaps = new ArrayList<>();
                List<Results> List = dataRepository.getECountBySex();
                for (Results R:List) {
                    if(sexes.indexOf(R.getStringKey()) == -1) {
                        sexes.add(R.getStringKey());
                        eMaps.add(new HashMap<>());
                        jstMaps.add(new HashMap<>());
                        csMaps.add(new HashMap<>());
                        tMaps.add(new HashMap<>());
                        tsMaps.add(new HashMap<>());
                    }
                    eMaps.get(sexes.indexOf(R.getStringKey())).put(R.getStringValue().toString(), R.getCount());
                }
                List = dataRepository.getJstCountBySex();
                for (Results R:List) {
                    jstMaps.get(sexes.indexOf(R.getStringKey())).put(R.getStringValue(), R.getCount());
                }
                List = dataRepository.getCsCountBySex();
                for (Results R:List) {
                    csMaps.get(sexes.indexOf(R.getStringKey())).put(R.getStringValue(), R.getCount());
                }
                List = dataRepository.getTCountBySex();
                for (Results R:List) {
                    tMaps.get(sexes.indexOf(R.getStringKey())).put(R.getStringValue(), R.getCount());
                }
                List = dataRepository.getTsCountBySex();
                for (Results R:List) {
                    tsMaps.get(sexes.indexOf(R.getStringKey())).put(R.getStringValue(), R.getCount());
                }
                Map<String, Object> sex = new HashMap<>();
                for(int i = 0;i<sexes.size();i++)
                {
                    sex.put("earnings",eMaps.get(i));
                    sex.put("job_search_time",jstMaps.get(i));
                    sex.put("company_size",csMaps.get(i));
                    sex.put("training",tMaps.get(i));
                    sex.put("town_size",tsMaps.get(i));
                    data.put(sexes.get(i).toString(),sex);
                    sex = new HashMap<>();
                }
                map.put("data",data);
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
            case "earnings":
            {
                map.put("success", true);
                map.put("status", 200);
                Map<String, Object> data = new HashMap<>();
                ArrayList<String> earnings = new ArrayList<>();
                ArrayList<Map<String,Object>> csMaps = new ArrayList<>();
                ArrayList<Map<String,Object>> tsMaps = new ArrayList<>();
                ArrayList<Map<String,Object>> ccMaps = new ArrayList<>();
                ArrayList<Map<String,Object>> facultyMaps = new ArrayList<>();
                ArrayList<Map<String,Object>> tMaps = new ArrayList<>();
                ArrayList<Map<String,Object>> fieldMaps = new ArrayList<>();
                List<Results> List = dataRepository.getCsCountByEarnings();
                for (Results R:List) {
                    if(earnings.indexOf(R.getStringKey()) == -1) {
                        earnings.add(R.getStringKey());
                        csMaps.add(new HashMap<>());
                        tsMaps.add(new HashMap<>());
                        ccMaps.add(new HashMap<>());
                        facultyMaps.add(new HashMap<>());
                        tMaps.add(new HashMap<>());
                        fieldMaps.add(new HashMap<>());
                    }
                    csMaps.get(earnings.indexOf(R.getStringKey())).put(R.getStringValue().toString(), R.getCount());
                }
                List = dataRepository.getTsCountByEarnings();
                for (Results R:List) {
                    tsMaps.get(earnings.indexOf(R.getStringKey())).put(R.getStringValue(), R.getCount());
                }
                List = dataRepository.getCcCountByEarnings();
                for (Results R:List) {
                    ccMaps.get(earnings.indexOf(R.getStringKey())).put(R.getStringValue(), R.getCount());
                }
                List = dataRepository.getFacultyCountByEarnings();
                for (Results R:List) {
                    facultyMaps.get(earnings.indexOf(R.getStringKey())).put(R.getStringValue(), R.getCount());
                }
                List = dataRepository.getTCountByEarnings();
                for (Results R:List) {
                    tMaps.get(earnings.indexOf(R.getStringKey())).put(R.getStringValue(), R.getCount());
                }
                List = dataRepository.getFieldCountByEarnings();
                for (Results R:List) {
                    fieldMaps.get(earnings.indexOf(R.getStringKey())).put(R.getStringValue(), R.getCount());
                }
                Map<String, Object> earning = new HashMap<>();
                for(int i = 0;i<earnings.size();i++)
                {
                    earning.put("company_size",csMaps.get(i));
                    earning.put("town_size",tsMaps.get(i));
                    earning.put("company_category",ccMaps.get(i));
                    earning.put("faculty",facultyMaps.get(i));
                    earning.put("title",tMaps.get(i));
                    earning.put("field",fieldMaps.get(i));
                    data.put(earnings.get(i).toString(),earning);
                    earning = new HashMap<>();
                }
                map.put("data",data);
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
            case "faculty":
            {
                map.put("success", true);
                map.put("status", 200);
                Map<String, Object> data = new HashMap<>();
                ArrayList<String> facultys = new ArrayList<>();
                ArrayList<Map<String,Object>> ccMaps = new ArrayList<>();
                ArrayList<Map<String,Object>> jstMaps = new ArrayList<>();
                ArrayList<Map<String,Object>> jsMaps = new ArrayList<>();
                List<Results> List = dataRepository.getCcCountByFaculty();
                for (Results R:List) {
                    if(facultys.indexOf(R.getStringKey()) == -1) {
                        facultys.add(R.getStringKey());
                        ccMaps.add(new HashMap<>());
                        jstMaps.add(new HashMap<>());
                        jsMaps.add(new HashMap<>());
                    }
                    ccMaps.get(facultys.indexOf(R.getStringKey())).put(R.getStringValue().toString(), R.getCount());
                }
                List = dataRepository.getJstCountByFaculty();
                for (Results R:List) {
                    jstMaps.get(facultys.indexOf(R.getStringKey())).put(R.getStringValue(), R.getCount());
                }
                List = dataRepository.getJsCountByFaculty();
                for (Results R:List) {
                    jsMaps.get(facultys.indexOf(R.getStringKey())).put(R.getStringValue(), R.getCount());
                }
                Map<String, Object> faculty = new HashMap<>();
                for(int i = 0;i<facultys.size();i++)
                {
                    faculty.put("company_category",ccMaps.get(i));
                    faculty.put("job_search_time",jstMaps.get(i));
                    faculty.put("job_satisfaction",jsMaps.get(i));
                    data.put(facultys.get(i).toString(),faculty);
                    faculty = new HashMap<>();
                }
                map.put("data",data);
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
            default:
            {
                map.put("success", false);
                map.put("status", 404);
                break;
            }
        }
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
    }



}
