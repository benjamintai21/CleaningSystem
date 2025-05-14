package com.cleaningsystem.controller.Report;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.Report;

@Service
public class GenerateWeeklyReportC {

    @Autowired
    private Report report;

    public Report generateWeeklyReport(){
        return report.generateWeeklyReport();
    }

    public Report generateWeeklyReport(String dateString){
        LocalDate date = LocalDate.parse(dateString);
        return report.generateWeeklyReport(date);
    }
}
