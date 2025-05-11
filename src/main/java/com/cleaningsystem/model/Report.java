package com.cleaningsystem.model;

import jakarta.persistence.Entity;

@Entity
public class Report {
	private int reportId;
	private String type;
    private String date;
    private int views;
    private int shortlist;
    private int no_homeowner;
    private int no_cleaner;

	public Report() {}

	public Report(String type, String date) {
		this.type = type;
        this.date= date;
	}

	// Getters
	public int getUid() { return reportId;}
	public String getType() {return type;}
    public String getDate() {return date;}
    public int getViews() {return views;}
    public int getShortlist() {return shortlist;}
    public int getNoOfHomeOwner() {return no_homeowner;}
    public int getNoOfCleaner() {return no_cleaner;}

	//Setters
	public void getUid(int new_reportId) { new_reportId = reportId;}
	public void getType(String new_type) {new_type = type;}
    public void getDate(String new_date) {new_date = date;}
    public void getViews(int new_views) {new_views = views;}
    public void getShortlist(int new_shortlist) {new_shortlist = shortlist;}
    public void getNoOfHomeOwner(int new_noHO) {new_noHO = no_homeowner;}
    public void getNoOfCleaner(int new_noC) {new_noC = no_cleaner;}
}
