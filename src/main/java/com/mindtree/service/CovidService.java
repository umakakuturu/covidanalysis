package com.mindtree.service;

import com.mindtree.dao.CovidDao;
import com.mindtree.entity.CovidData;
import com.mindtree.exceptions.InvalidDateException;
import com.mindtree.exceptions.InvalidDateRangeException;
import com.mindtree.exceptions.InvalidStateCodeException;
import com.mindtree.exceptions.NoDataFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.sql.Date;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CovidService implements ServiceInterface{

    @Autowired
    private CovidDao coviddao;

    private static Pattern DATE_PATTERN = Pattern.compile("^[0-9]{4}-[0-9]{2}-[0-9]{2}$");

    List<CovidData> covidData;
    List<CovidData> covidData1;
    @Override
    public List<String> findAllByState() {
        covidData = coviddao.findAll();
        return covidData.stream().map(covid->covid.getState()).distinct().collect(Collectors.toList());
    }

    @Override
    public List<String> getDistrictByState(String state) {
        try {
            if (coviddao.findByState(state).isEmpty()){
                throw new InvalidStateCodeException("Invalid State code,please check your input");
            }
          List<CovidData> data=coviddao.findAll();
            return data.stream().filter(s->s.getState().equals(state)).map(covidData1 -> covidData1.getDistrict()).sorted().collect(Collectors.toList());
    }
        catch (InvalidStateCodeException e) {
            return Collections.singletonList(e.getMessage());
        }

    }

    @Override
    public List<Object> getDetailsByDates(Date startdate, Date enddate) {
        List<CovidData> covidData =coviddao.findAll();
        List<Date> start1=covidData.stream().filter(date->date.getDate().equals(startdate)).map(i->i.getDate()).collect(Collectors.toList());
        List<Date> end1=covidData.stream().filter(date->date.getDate().equals(enddate)).map(i->i.getDate()).collect(Collectors.toList());
        try {
            boolean var= enddate.before(startdate);
        if(var==true){
            throw new InvalidDateRangeException("Invalid Date Range, Please check your input");
        }
        else if(start1.isEmpty() && end1.contains(enddate)){
            throw new InvalidDateException("Invalid Start date, please check your input");
        }
        else if(end1.isEmpty() && start1.contains(startdate)){
            throw new InvalidDateException("Invalid End date, please check your input");
        }
        else if(coviddao.findAllByDates(startdate, enddate).isEmpty()){
            throw new NoDataFoundException("No data present");
        }
        covidData1 = coviddao.findAllByDates(startdate,enddate);
            return covidData1.stream().sorted(Comparator.comparing(CovidData::getDate)).flatMap(p-> Stream.of(p.getDate(),p.getState(),p.getConfirmed())).collect(Collectors.toList());
    }

        catch (InvalidDateException e) {
            return Collections.singletonList(e.getMessage());

        }
        catch (InvalidDateRangeException e) {
            return Collections.singletonList(e.getMessage());
        }
        catch (NoDataFoundException e) {
            return Collections.singletonList(e.getMessage());
        }

    }

    @Override
    public List<Object> getDetailsByDatesAndStates(Date startdate, Date enddate, String firststate, String secondstate) {
        List<Object> last = coviddao.findConfirmedDetails(startdate, enddate, firststate, secondstate);
        return last.stream().collect(Collectors.toList());

    }}