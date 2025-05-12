package com.cleaningsystem.model;

import jakarta.persistence.Entity;

@Entity
public class Report {
	private int reportId;
	private String type;
    private String date;
    private int new_homeowners;
    private int total_homeowners;
    private int new_cleaners;
    private int total_cleaners;
    private int no_shortlists;
    private int no_bookings;

	public Report() {}

	public Report(String type, String date) {
		this.type = type;
        this.date= date;
	}

	// Getters
	public int getReportId() { return reportId;}
	public String getType() {return type;}
    public String getDate() {return date;}
    
    public int getNewHomeOwners() {return new_homeowners;}
    public int getTotalHomeOwners() {return total_homeowners;}
    public int getNewCleaners() {return new_cleaners;}
    public int getTotalCleaners() {return total_cleaners;}
    public int getNoOfShortlists() {return no_shortlists;}
    public int getNoOfBookings() {return no_bookings;}

	//Setters
	public void setReportId(int new_reportId) { new_reportId = reportId;}
	public void setType(String new_type) {new_type = type;}
    public void setDate(String new_date) {new_date = date;}

    public void setNewOwners(int new_new_homeowners) {new_new_homeowners = new_homeowners;}
    public void setTotalOwners(int new_total_homeowners) {new_total_homeowners = total_homeowners;} 
    public void setNewCleaner(int new_new_cleaners) {new_new_cleaners = new_cleaners;} 
    public void setTotalCleaner(int new_total_cleaners) {new_total_cleaners = total_cleaners;} 
    public void setNoOfShortlists(int new_total_shortlists) {new_total_shortlists = no_shortlists;} 
    public void setNoOfBookings(int new_total_bookings) {new_total_bookings = no_bookings;}
}
