/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java_cinematicket;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java_cinematicket.reportDetails;
import javafx.collections.ObservableList;

/**
 *
 * @author abdallahrizk
 */
public class CSVFileImplemenation {
    
    private final String COMMA_DELIMITER = ",";
    private final String NEW_LINE_SEPARATOR = "\n";
    
    
        private final String FILE_HEADER = "Customer Name,Uniqe ID,Movie Title,Location,Time,Threator,Seat";

            public void writeCsvFile(ObservableList list) {
                
                ObservableList<reportDetails> reportDetailsList = list;

                File reportFile = new File("Report.csv");
                FileWriter fileWriter = null;
                
                try{
                
                    fileWriter = new FileWriter(reportFile);
                    
                    fileWriter.append(FILE_HEADER);
                    fileWriter.append(NEW_LINE_SEPARATOR);

                    
                  for(reportDetails movie : reportDetailsList) {
                    
                   String customerName = movie.getCustomerName();
                   String uniqID = movie.getUniqID();
                   String movieTitle = movie.getMovieTitle();
                   String location = movie.getLocation();
                   String time = movie.getTime();
                   int threator = movie.getThreator();
                   int seat = movie.getSeat();
                   
                fileWriter.append(customerName);
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(uniqID);
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(movieTitle);
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(location);
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(time);
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(threator));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(seat));
                fileWriter.append(NEW_LINE_SEPARATOR);

                   
                   
                }   
                  
                   System.out.println("CSV file was created successfully !!!");
                   fileWriter.flush();
                   fileWriter.close();
                    
                
                
                } catch (IOException ex) {
            Logger.getLogger(CSVFileImplemenation.class.getName()).log(Level.SEVERE, null, ex);
                }
    
        }
            
            
            public ArrayList uniqIDChecher(String filePath) throws IOException{

                ArrayList<String> uniqID = new ArrayList<>();
                
                    File file = new File(filePath);
                                List<String> lines = Files.readAllLines(file.toPath(), 
                                StandardCharsets.UTF_8);
                        for (String line : lines) {
                              String[] array = line.split(",");
                               uniqID.add(array[1]);
    }
                                
                                return uniqID;
        }
            
            
            
}