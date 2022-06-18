package com.umg.absolwentbackend.controllers;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/public")
public class PublicController implements ErrorController {
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.BAD_REQUEST.value()) {

                map.put("status", 400);
                map.put("message", "Bad request");
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);

            }
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                map.put("status", 404);
                map.put("message", "Not found");
                return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
            }
            if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
                map.put("status", 401);
                map.put("message", "Unauthorized");
                return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
            }
            if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                map.put("status", 500);
                map.put("message", "Internal Server Error");
                return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        map.put("status", 200);
        map.put("message", "Ok");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/statistics")
    private Map<String, Object> dataModeling(){
        Map<String, Object> map = new HashMap<>();



        return map; //To jest porażka
    }

}
