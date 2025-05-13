package com.cleaningsystem.controller;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.Report;

@Service
public class ReportController {

    @Autowired
    private Report report;

    public Report generateDailyReport(){
        return report.generateDailyReport();
    }

    public Report generateWeeklyReport(LocalDate date){
        return report.generateWeeklyReport(date);
    }

    public Report generateMonthlyReport(LocalDate date){
        return report.generateMonthlyReport(date);
    }
    
}
