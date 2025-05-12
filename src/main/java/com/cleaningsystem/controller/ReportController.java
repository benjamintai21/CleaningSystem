package com.cleaningsystem.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.model.Report;
import com.cleaningsystem.dao.ReportDAO;


@Service
public class ReportController {

    @Autowired
    private ReportDAO reportDAO;

    public Report generateDailyReport(){
        return reportDAO.generateDailyReport();
    }

    public Report generateWeeklyReport(LocalDate date){
        return reportDAO.generateWeeklyReport(date);
    }

    public Report generateMonthlyReport(LocalDate date){
        return reportDAO.generateMonthlyReport(date);
    }
    
}
