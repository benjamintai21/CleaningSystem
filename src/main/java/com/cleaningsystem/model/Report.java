package com.cleaningsystem.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;

@Entity
public class Report {
	private int reportId;
	private String type;
    private LocalDate date;
    private int new_homeOwners;
    private int total_homeOwners;
    private int new_cleaners;
    private int total_cleaners;
    private int no_shortlists;
    private int no_bookings;

	public Report() {}

	public Report(String type, LocalDate date, int new_homeOwners, int total_homeOwners, int new_cleaners, int total_cleaners, int no_shortlists, int no_bookings) {
		this.type = type;
        this.date = date;
        this.new_homeOwners = new_homeOwners;
        this.total_homeOwners = total_homeOwners;
        this.new_cleaners = new_cleaners;
        this.total_cleaners = total_cleaners;
        this.no_shortlists = no_shortlists;
        this.no_bookings = no_bookings;
	}

	// Getters
	public int getReportId() { return reportId;}
	public String getType() {return type;}
    public LocalDate getDate() {return date;}
    
    public int getNewHomeOwners() {return new_homeOwners;}
    public int getTotalHomeOwners() {return total_homeOwners;}
    public int getNewCleaners() {return new_cleaners;}
    public int getTotalCleaners() {return total_cleaners;}
    public int getNoOfShortlists() {return no_shortlists;}
    public int getNoOfBookings() {return no_bookings;}

	//Setters
	public void setReportId(int new_reportId) { new_reportId = reportId;}
	public void setType(String new_type) {new_type = type;}
    public void setDate(LocalDate new_date) {new_date = date;}

    public void setNewHomeOwners(int new_new_homeOwners) {new_new_homeOwners = new_homeOwners;}
    public void setTotalHomeOwners(int new_total_homeOwners) {new_total_homeOwners = total_homeOwners;} 
    public void setNewCleaner(int new_new_cleaners) {new_new_cleaners = new_cleaners;} 
    public void setTotalCleaner(int new_total_cleaners) {new_total_cleaners = total_cleaners;} 
    public void setNoOfShortlists(int new_total_shortlists) {new_total_shortlists = no_shortlists;} 
    public void setNoOfBookings(int new_total_bookings) {new_total_bookings = no_bookings;}
}
