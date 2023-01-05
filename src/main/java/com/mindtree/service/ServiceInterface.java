package com.mindtree.service;

import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public interface ServiceInterface {

     List<String> findAllByState();
     List<String> getDistrictByState(String state);
     List<Object> getDetailsByDates(Date startdate, Date enddate);
     List<Object> getDetailsByDatesAndStates(Date startdate, Date enddate, String firststate, String secondstate);

}
