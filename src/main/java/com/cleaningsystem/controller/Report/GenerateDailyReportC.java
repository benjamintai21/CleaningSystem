package com.cleaningsystem.controller.Report;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.Report;

@Service
public class GenerateDailyReportC {

    @Autowired
    private Report report;

    public Report generateDailyReport(){
        return report.generateDailyReport();
    }

    public Report generateDailyReport(String dateString){
        LocalDate date = LocalDate.parse(dateString);
        return report.generateDailyReport(date);
    }    
}
