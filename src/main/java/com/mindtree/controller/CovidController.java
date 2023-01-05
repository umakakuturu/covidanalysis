package com.mindtree.controller;

import com.mindtree.service.ServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/coviddata")
public class CovidController {

    @Autowired
    private ServiceInterface covidService;
    @GetMapping("/states")
    public List<String> findAllByState() {
        System.out.println("contoller");
        return covidService.findAllByState();
    }

    @GetMapping("/statecode")
    public List<String> findAllByStateCode(@RequestParam("state") String state) {
        return covidService.getDistrictByState(state);
    }

    @GetMapping("/date")
    public List<Object> findAllByDates(@RequestParam("startdate") Date startdate, @RequestParam("enddate") Date enddate) {
        return covidService.getDetailsByDates(startdate, enddate);
    }

    @GetMapping("/daterange")
    public List<Object> findAllConfirmedCases(@RequestParam("startdate") Date startdate, @RequestParam("enddate") Date enddate, @RequestParam("firststate") String firststate, @RequestParam("secondstate") String secondstate) {
        return covidService.getDetailsByDatesAndStates(startdate, enddate, firststate, secondstate);
    }
}
