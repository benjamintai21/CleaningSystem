package com.cleaningsystem.controller.Report;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleaningsystem.entity.Report;

@Service
public class GenerateMonthlyReportC {

    @Autowired
    private Report report;

    public Report generateMonthlyReport(){
        return report.generateMonthlyReport();
    }

    public Report generateMonthlyReport(String dateString){
        LocalDate date = LocalDate.parse(dateString);
        return report.generateMonthlyReport(date);
    }
}
