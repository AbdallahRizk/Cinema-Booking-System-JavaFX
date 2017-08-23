/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java_cinematicket;

/**
 *
 * @author abdallahrizk
 */
public class reportDetails {
    
    
    
    private String customerName;
    private String uniqID;
    private String movieTitle;
    private String location;
    private String time;
    private int threator;
    private int seat;

    public reportDetails(String customerName, String uniqID, String movieTitle, String location, String time, int threator, int seat) {
        this.customerName = customerName;
        this.uniqID = uniqID;
        this.movieTitle = movieTitle;
        this.location = location;
        this.time = time;
        this.threator = threator;
        this.seat = seat;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getUniqID() {
        return uniqID;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getLocation() {
        return location;
    }

    public String getTime() {
        return time;
    }

    public int getThreator() {
        return threator;
    }

    public int getSeat() {
        return seat;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setUniqID(String uniqID) {
        this.uniqID = uniqID;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setThreator(int threator) {
        this.threator = threator;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }
    
}
