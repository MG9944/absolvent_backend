package com.umg.absolwentbackend.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public ResponseEntity<Map<String,Object>> handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Map<String, Object> map = new HashMap<>();
        map.put("success", false);
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                map.put("status", 404);
                return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
            }
            if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
                map.put("status", 401);
                return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
            }
            if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                map.put("status", 500);
                return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        map.put("status", 400);
        return  new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

}