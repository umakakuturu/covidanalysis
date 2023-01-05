package com.mindtree.dao;

import com.mindtree.entity.CovidData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface CovidDao extends JpaRepository<CovidData,Integer> {

    public List<CovidData> findByState(String state);


    @Query("select c from CovidData c where (c.date >:startdate  and c.date <:enddate)")
    public List<CovidData> findAllByDates(Date startdate, Date enddate);


    @Query(value = "select f.date as 'Date' , f.state as 'First state' , sum(f.confirmed) as 'first state confirmed total',s.state as 'Second State',s.sec as 'Second state confirmed total' FROM covid_analysis.covid_data f LEFT JOIN(SELECT  date,state , sum(confirmed) as 'sec' FROM covid_analysis.covid_data WHERE (date >:startdate  and date <:enddate) and state =:secondstate group by date order by date) as s ON (f.date = s.date) WHERE (f.date >:startdate  and f.date <:enddate) and f.state =:firststate group by f.date ORDER BY f.date;", nativeQuery = true)
    public List<Object> findConfirmedDetails(Date startdate, Date enddate, String firststate, String secondstate);

}
