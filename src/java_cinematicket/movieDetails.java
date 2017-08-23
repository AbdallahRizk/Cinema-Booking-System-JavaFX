package java_cinematicket;

/**
 *
 * @author abdallahrizk
 */
public class movieDetails {
    
    
    private String movieName;
    private String description;
    private String movieLocation;
    private String movieTime;
    private int movieThreator;

    public movieDetails(String movieName, String description, String movieLocation, String movieTime, int movieThreator) {
        this.movieName = movieName;
        this.description = description;
        this.movieLocation = movieLocation;
        this.movieTime = movieTime;
        this.movieThreator = movieThreator;
    }

    movieDetails(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getMovieName() {
        return movieName;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return movieLocation;
    }

    public String getTime() {
        return movieTime;
    }

    public int getThreator() {
        return movieThreator;
    }


   public void setMovieName(String movieName) {
        this.movieName=movieName;
    }

    public void setDescription(String description ) {
        this.description=description;
    }

    public void setLocation(String movieLocation) {
        this.movieLocation=movieLocation;
    }

    public void setTime(String movieTime) {
         this.movieTime=movieTime;
    }

    public void setThreator(int movieThreator) {
        this.movieThreator=movieThreator;
    }
    
    
   
    
}
