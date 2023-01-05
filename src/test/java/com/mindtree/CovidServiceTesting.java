package com.mindtree;

import com.mindtree.dao.CovidDao;
import com.mindtree.entity.CovidData;
import com.mindtree.service.CovidService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import java.sql.Date;
import java.util.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.Silent.class)
public class CovidServiceTesting {
    @InjectMocks
    private CovidService service;

    @Mock
    private CovidDao covidDao;
    //1
    @Test
    public void testFunctionalityStateValidData(){

        List<String> ref= Arrays.asList("st1","st2","st3");
        Mockito.when(service.findAllByState()).thenReturn(ref);
    }
    //3
    @Test
    public void testFunctionalityDateRangeValidData(){
        String res="2020-08-07";
        Date resdate=Date.valueOf(res);
        String str="2020-08-06";
        String str1="2020-08-08";
        Date start=Date.valueOf(str);
        Date end=Date.valueOf(str1);

        List<CovidData> co=new ArrayList<>();
        CovidData covid1=new CovidData();
        covid1.setDate(start);
        covid1.setState("JK");
        covid1.setConfirmed("2");
        covid1.setDistrict("Andhra");
        co.add(covid1);

        CovidData covid2=new CovidData();
        covid2.setDate(end);
        covid2.setState("JKk");
        covid2.setConfirmed("1");
        covid2.setDistrict("Andhra pradesh");

        co.add(covid2);
        Mockito.when(covidDao.findAllByDates(start,end)).thenReturn(co);
        List<Object> ref=service.getDetailsByDates(start,end);
        Assert.assertNotSame(2,ref.size());
    }
    //2
    @Test
    public void testFunctionalityDistrictValidData() {

        List<CovidData> lst = new ArrayList<>();
        CovidData clst = new CovidData();
        clst.setState("JK");
        clst.setDistrict("An");
        lst.add(clst);
        CovidData clst1 = new CovidData();
        clst1.setState("JK");
        clst1.setDistrict("Ann");
        lst.add(clst1);
        String st = "JK";
        Mockito.when(this.covidDao.findByState(st)).thenReturn(lst);
        Mockito.when(covidDao.findAll()).thenReturn(lst);
        List<String> ref22 = service.getDistrictByState(st);
        Assert.assertEquals(2, ref22.size());
    }
    //4
    @Test
    public void testFunctionalityDateAndStateRangeValidData(){

        List<Object> ref2=Arrays.asList("2020-08-04","JK",390,"DN",43);
        String strr="2020-08-06";
        String strr1="2020-08-08";
        Date start1=Date.valueOf(strr);
        Date end1= Date.valueOf(strr1);
        String startstate="JK";
        String endstate="DN";
        Mockito.when(service.getDetailsByDatesAndStates(start1,end1,startstate,endstate)).thenReturn(ref2);
    }

//Invalid Test Cases
    //1
    @Test()
    public void testFunctionalityDistrictInvalidStateCode() {
        List<CovidData> lst = new ArrayList<>();
        CovidData clst = new CovidData();
        clst.setState("JK");
        clst.setDistrict("An");
        lst.add(clst);
        CovidData clst1 = new CovidData();
        clst1.setState("JK");
        clst1.setDistrict("Ann");
        lst.add(clst1);
        String st = "JK";
        List<String> ref22 = service.getDistrictByState(st);
    }
    @Test
    public void testFunctionalityDateAndStateRangeNoRecords(){
        String str="2020-08-06";
        String str1="2020-08-08";
        Date start=Date.valueOf(str);
        Date end=Date.valueOf(str1);

        List<CovidData> co=new ArrayList<>();
        CovidData covid1=new CovidData();
        covid1.setDate(start);
        covid1.setState("JK");
        covid1.setConfirmed("2");
        covid1.setDistrict("Andhra");
        co.add(covid1);

        CovidData covid2=new CovidData();
        covid2.setDate(end);
        covid2.setState("JKk");
        covid2.setConfirmed("1");
        covid2.setDistrict("Andhra pradesh");

        co.add(covid2);
        List<Object> ref=service.getDetailsByDates(start,end);

    }
    @Test
    public void testFunctionalityDateAndStateRangeEndDateIsBeforeStartDate() {
        String str = "2020-08-06";
        String str1 = "2020-08-08";
        Date start = Date.valueOf(str);
        Date end = Date.valueOf(str1);

        List<CovidData> co = new ArrayList<>();
        CovidData covid1 = new CovidData();
        covid1.setDate(start);
        covid1.setState("JK");
        covid1.setConfirmed("2");
        covid1.setDistrict("Andhra");
        co.add(covid1);

        CovidData covid2 = new CovidData();
        covid2.setDate(end);
        covid2.setState("JKk");
        covid2.setConfirmed("1");
        covid2.setDistrict("Andhra pradesh");

        co.add(covid2);

        List<Object> ref1 = service.getDetailsByDates(end, start);

    }
    @Test
    public void testFunctionalityDateAndStateRangeSecondInvalidStartDate() {
        String str = "2020-08-03";
        String str1 = "2020-08-07";
        Date start = Date.valueOf(str);
        Date end = Date.valueOf(str1);
        String strr = "1990-08-03";
        String strr1 = "1995-08-08";
        Date start1 = Date.valueOf(strr);
        Date end1 = Date.valueOf(strr1);
        List<CovidData> co = new ArrayList<>();
        CovidData covid1 = new CovidData();

        covid1.setDate(end);
        covid1.setState("JK");
        covid1.setConfirmed("2");
        covid1.setDistrict("Andhra");
        co.add(covid1);

        CovidData covid2 = new CovidData();
        covid2.setDate(start1);
        covid2.setState("JKk");
        covid2.setConfirmed("1");
        covid2.setDistrict("Andhra pradesh");

        co.add(covid2);
        CovidData covid3 = new CovidData();
        covid3.setDate(end1);
        covid3.setState("DK");
        covid3.setConfirmed("3");
        covid3.setDistrict("Andhra pradesh");
        co.add(covid3);

        Mockito.when(covidDao.findAll()).thenReturn(co);
        List<Object> ref2 = service.getDetailsByDates(start, end);

    }
    @Test
    public void testFunctionalityDateAndStateRangeThirdInvalidEndDate() {
        String str = "2020-08-03";
        String str1 = "2020-08-07";
        Date start = Date.valueOf(str);
        Date end = Date.valueOf(str1);
        String strr = "1990-08-03";
        String strr1 = "1995-08-08";
        Date start1 = Date.valueOf(strr);
        Date end1 = Date.valueOf(strr1);
        List<CovidData> co = new ArrayList<>();
        CovidData covid1 = new CovidData();

        covid1.setDate(start);
        covid1.setState("JK");
        covid1.setConfirmed("2");
        covid1.setDistrict("Andhra");
        co.add(covid1);

        CovidData covid2 = new CovidData();
        covid2.setDate(start1);
        covid2.setState("JKk");
        covid2.setConfirmed("1");
        covid2.setDistrict("Andhra pradesh");

        co.add(covid2);
        CovidData covid3 = new CovidData();
        covid3.setDate(end1);
        covid3.setState("DK");
        covid3.setConfirmed("3");
        covid3.setDistrict("Andhra pradesh");
        co.add(covid3);

        Mockito.when(covidDao.findAll()).thenReturn(co);
        List<Object> ref2 = service.getDetailsByDates(start, end);

    }}