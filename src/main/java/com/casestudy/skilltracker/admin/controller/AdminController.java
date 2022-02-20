package com.casestudy.skilltracker.admin.controller;

import com.casestudy.skilltracker.admin.dto.SearchResponse;
import com.casestudy.skilltracker.admin.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1")
@Slf4j
public class AdminController {

    @Autowired
    SearchService serviceService;

    @GetMapping(value = "/admin/{criterion}/{value}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<SearchResponse> find(@Valid @PathVariable final String criterion,
                                               @PathVariable final String value,
                                               @RequestParam(required = false,defaultValue = "0", name = "page") int page,
                                               @RequestParam(required = false,defaultValue = "5", name = "size") int size
                                               ) {
        log.info("Search request received for criterion: {} and  Value:{}",criterion,value);
        SearchResponse response=  serviceService.find(criterion,value,page,size);
        log.debug("Search response :{}",response.toString());
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
