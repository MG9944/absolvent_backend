package com.umg.absolwentbackend.controllers;



import com.umg.absolwentbackend.exceptions.AuthenticationException;
import com.umg.absolwentbackend.models.Group;
import com.umg.absolwentbackend.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/pool")
public class GroupController {

    @Autowired
    GroupService groupService;

  /*
    @PostMapping("")
    public ResponseEntity<Map<String, Object>> getAll(HttpServletRequest request, @RequestBody Map<String, Object> paramMap, @RequestParam(value = "force", required = false) String forceParam) {

        int permissions = (Integer) request.getAttribute("permissions");
        String group_name = (String) paramMap.get("group_name");
        Integer questionnaireId = (Integer) paramMap.get("questionnaireId");
        Integer questionnaireFrequency = (Integer) paramMap.get("questionnaireFrequency");
        if(forceParam==null)
            forceParam="false";
        Boolean force = forceParam.compareTo("true")==0 ? true : false;
        if (permissions > 1) {
            List<Map<String, Object>> user;
            var success = groupService.addGroup(group_name,questionnaireId, questionnaireFrequency);
            if (success == 0) {
                Map<String, Object> map = new HashMap<>();
                var options = new HashMap<String, String>();
                options.put("force", "forcePost");
                map.put("success", true);
                map.put("message", "Pool exist");
                map.put("options", options);
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
            if (success == -1) {
                Map<String, Object> map = new HashMap<>();
                map.put("success", false);
                map.put("message", "Error");
                return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            }
            Map<String, Object> map = new HashMap<>();
            map.put("success", true);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("success", false);
            map.put("message", "Unauthorized");
            return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
        }


    }
    */

}
