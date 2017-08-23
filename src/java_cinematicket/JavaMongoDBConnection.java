/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java_cinematicket;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class JavaMongoDBConnection{
    
            Connection conn =null;
            String dbuser = "root";
            String dbpassw = "0557724289";
            String databasename = "java_cinemaTickets";
            String url = "jdbc:mysql://127.0.0.1:3306/java_cinemaTickets";
            String driver = "com.mysql.jdbc.Driver";
            PreparedStatement ps;
            




    public boolean customer_auth(String user, String pass) throws SQLException, ClassNotFoundException{
            boolean login;
            Class.forName(driver);
            conn = DriverManager.getConnection(url,dbuser,dbpassw);
            Statement st = conn.createStatement();
            
            ps = conn.prepareStatement("SELECT `customer_name`, `customer_password` FROM `customer` WHERE `customer_name` = ? AND `customer_password` = ?");
           
           ps.setString(1, user);
           ps.setString(2, pass);
           
           ResultSet result = ps.executeQuery();
            if(result.next()){
                 login = true;
                 
                 conn.close();
        } 
            else{login = false;}
                
            return login;
            

    }
    
    
    public boolean admin_auth(String user, String pass) throws SQLException, ClassNotFoundException{
            boolean login;
            Class.forName(driver);
            conn = DriverManager.getConnection(url,dbuser,dbpassw);
            Statement st = conn.createStatement();
            
            ps = conn.prepareStatement("SELECT `admin_name`, `admin_password` FROM `admin` WHERE `admin_name` = ? AND `admin_password` = ?");
           
           ps.setString(1, user);
           ps.setString(2, pass);
           
           
           ResultSet result = ps.executeQuery();
            if(result.next()){
                 login = true;
                 
                 conn.close();
        } 
            else{login = false;}
                
            return login;

    }
    
    
 
    public void addMovie_insetIntoDB(String title,String description,String cinema,String time,int threator,String image,String timeID,String threatorID,String combinAll) throws ClassNotFoundException, SQLException{

         Class.forName(driver);
            conn = DriverManager.getConnection(url,dbuser,dbpassw);
            Statement st = conn.createStatement();
            
            ps = conn.prepareStatement("insert into movie(movie_name,description,cinema_location,time,threator,movie_poster,movieTimeID,movieCinema,movieAllCombin)"+"values(?,?,?,?,?,?,?,?,?)");
            
           ps.setString(1, title);
           ps.setString(2, description);
           ps.setString(3, cinema);
           ps.setString(4, time);
           ps.setInt(5, threator);
           ps.setString(6, image);
           ps.setString(7, timeID);
           ps.setString(8, threatorID);
           ps.setString(9, combinAll);


           ps.executeUpdate();
           
           conn.close();
           
    }
    
    public ObservableList movieName() throws ClassNotFoundException, SQLException{
        
        HashSet<String> movieName_Hashlist = new HashSet<>();
        ObservableList<String>  movieName_list = FXCollections.observableArrayList();
        
        Class.forName(driver);
            conn = DriverManager.getConnection(url,dbuser,dbpassw);
            ResultSet rs = conn.createStatement().executeQuery("SELECT movie_name FROM movie");
            
            while(rs.next()){
            movieName_Hashlist.add(rs.getString("movie_name"));                                      
        }
            
            movieName_list.addAll(movieName_Hashlist);
            
         conn.close();

            
          return movieName_list;  
          

    }
        

    
    public ObservableList movieLocation(String movie) throws ClassNotFoundException, SQLException{
        
        HashSet<String> movieName_Hashlist = new HashSet<>();
        ObservableList<String>  movieName_list = FXCollections.observableArrayList(); 
        
        Class.forName(driver);
            conn = DriverManager.getConnection(url,dbuser,dbpassw);
            ResultSet rs = conn.createStatement().executeQuery("SELECT cinema_location FROM movie where movie_name = '"+movie+"'");
            
            while(rs.next()){
            movieName_Hashlist.add(rs.getString("cinema_location"));                                    
        }
            
            movieName_list.addAll(movieName_Hashlist);
            
          return movieName_list;  
    }
    
    
    
    public ObservableList movieTime(String movie) throws ClassNotFoundException, SQLException{
        
        HashSet<String> movieName_Hashlist = new HashSet<>();
        ObservableList<String>  movieName_list = FXCollections.observableArrayList();        
        Class.forName(driver);
            conn = DriverManager.getConnection(url,dbuser,dbpassw);
            ResultSet rs = conn.createStatement().executeQuery("SELECT time FROM movie where movieTimeID = '"+movie+"'");
            
            while(rs.next()){
            movieName_Hashlist.add(rs.getString("time"));                                    
        }
                conn.close();

            movieName_list.addAll(movieName_Hashlist);
            
          return movieName_list;  
    }
    
    
    public ObservableList movieHall(String movie) throws ClassNotFoundException, SQLException{
        
        HashSet<String> movieName_Hashlist = new HashSet<>();
        ObservableList<String>  movieName_list = FXCollections.observableArrayList();   
        
        Class.forName(driver);
            conn = DriverManager.getConnection(url,dbuser,dbpassw);
            ResultSet rs = conn.createStatement().executeQuery("SELECT threator FROM movie where movieCinema = '"+movie+"'");
            
            while(rs.next()){
            movieName_Hashlist.add(rs.getString("threator"));                                    
        }
                conn.close();

            movieName_list.addAll(movieName_Hashlist);
            
          return movieName_list;  
    }
    
    
    public String moviePoster(String movie) throws ClassNotFoundException, SQLException{
                
            String moviePoster_string="";
                
        Class.forName(driver);
            conn = DriverManager.getConnection(url,dbuser,dbpassw);
            ResultSet rs = conn.createStatement().executeQuery("SELECT movie_poster FROM movie where movie_name = '"+movie+"'");
            
            while(rs.next()){
            moviePoster_string = rs.getString("movie_poster");                                    
        }
                conn.close();

          return moviePoster_string;  
    }

    public String movieDesription(String movie) throws ClassNotFoundException, SQLException{
                
            String movieDesription="";
                
        Class.forName(driver);
            conn = DriverManager.getConnection(url,dbuser,dbpassw);
            ResultSet rs = conn.createStatement().executeQuery("SELECT description FROM movie where movie_name = '"+movie+"'");
            
            while(rs.next()){
            movieDesription = rs.getString("description");                                    
        }
                conn.close();

          return movieDesription;  
    }
    
    
    
    public void addCustomer_insetIntoDB(String title,String description,String cinema,String time,String threator,int image,int timeID) throws ClassNotFoundException, SQLException{

         Class.forName(driver);
            conn = DriverManager.getConnection(url,dbuser,dbpassw);
            Statement st = conn.createStatement();
            
            ps = conn.prepareStatement("insert into report(customerWhoPurchase,uniqeID,movieWhichPurchased,cinema,time,threator,seats)"+"values(?,?,?,?,?,?,?)");
            
           ps.setString(1, title);
           ps.setString(2, description);
           ps.setString(3, cinema);
           ps.setString(4, time);
           ps.setString(5, threator);
           ps.setInt(6, image);
           ps.setInt(7, timeID);

           ps.executeUpdate();
           
           conn.close();
           
    }
 
   



public ArrayList bookedSeats(String movie) throws ClassNotFoundException, SQLException{
        
        ArrayList<Integer> selectedSeats = new ArrayList<>();
        
        Class.forName(driver);
            conn = DriverManager.getConnection(url,dbuser,dbpassw);
            ResultSet rs = conn.createStatement().executeQuery("SELECT seats FROM report where uniqeID = '"+movie+"'");
            
            while(rs.next()){
            selectedSeats.add(rs.getInt("seats"));                                    
        }
                conn.close();

            
          return selectedSeats;  
    }


public ObservableList movies_tableView() throws ClassNotFoundException, SQLException{
        
        ObservableList<movieDetails>  movieDetails_Table = FXCollections.observableArrayList();   
        
        Class.forName(driver);
            conn = DriverManager.getConnection(url,dbuser,dbpassw);
            ResultSet rs = conn.createStatement().executeQuery("SELECT movie_name,description,cinema_location,time,threator FROM movie");
            
            while(rs.next()){
            movieDetails_Table.add(new movieDetails(
            
                                rs.getString("movie_name"),
                                rs.getString("description"),
                                rs.getString("cinema_location"),
                                rs.getString("time"),
                                rs.getInt("threator")

            
                                 ));                                   
        }
                conn.close();

            
          return movieDetails_Table;  
    }


public ObservableList moviesBasedOnMovie_tableView(String movie) throws ClassNotFoundException, SQLException{
        
        ObservableList<movieDetails>  movieDetails_Table = FXCollections.observableArrayList();   
        
        
        Class.forName(driver);
            conn = DriverManager.getConnection(url,dbuser,dbpassw);
            ResultSet rs = conn.createStatement().executeQuery("SELECT movie_name,description,cinema_location,time,threator FROM movie where movie_name = '"+movie+"'");
            
            while(rs.next()){
            movieDetails_Table.add(new movieDetails(
            
                                rs.getString("movie_name"),
                                rs.getString("description"),
                                rs.getString("cinema_location"),
                                rs.getString("time"),
                                rs.getInt("threator")

            
                                 ));                                   
        }
                conn.close();

            
          return movieDetails_Table;  
    }


public void deleteMovies(String cominedID) throws ClassNotFoundException, SQLException{
        
        Class.forName(driver);
            conn = DriverManager.getConnection(url,dbuser,dbpassw);
            
            PreparedStatement st = conn.prepareStatement("DELETE FROM movie WHERE movieAllCombin = ?");
            
            st.setString(1,cominedID);
            st.executeUpdate(); 

                conn.close();   
            
    }



public ObservableList reportDetails() throws ClassNotFoundException, SQLException{
        
        ObservableList<reportDetails>  reportInfoList = FXCollections.observableArrayList();   
        
        
        Class.forName(driver);
            conn = DriverManager.getConnection(url,dbuser,dbpassw);
            ResultSet rs = conn.createStatement().executeQuery("SELECT customerWhoPurchase,uniqeID,movieWhichPurchased,cinema,time,threator,seats FROM report");
            
            while(rs.next()){
            reportInfoList.add(new reportDetails(
            
                                rs.getString("customerWhoPurchase"),
                                rs.getString("uniqeID"),
                                rs.getString("movieWhichPurchased"),
                                rs.getString("cinema"),
                                rs.getString("time"),
                                rs.getInt("threator"),
                                rs.getInt("seats")
                                 ));                                   
        }
                conn.close();

            
          return reportInfoList;  
    }



public boolean validateTicket(String uniQIDforValidate) throws SQLException, ClassNotFoundException{
            boolean found;
            Class.forName(driver);
            conn = DriverManager.getConnection(url,dbuser,dbpassw);
            Statement st = conn.createStatement();
            
            ps = conn.prepareStatement("SELECT 'uniqeID' FROM `report` WHERE `uniqeID` = ?");
           
           ps.setString(1, uniQIDforValidate);
           
           ResultSet result = ps.executeQuery();
            if(result.next()){
                 found = true;
                 
                 conn.close();
        } 
            else{found = false;}
                
            return found;
            

    }
}