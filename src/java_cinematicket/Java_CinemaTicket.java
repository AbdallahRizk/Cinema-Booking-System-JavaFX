/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java_cinematicket;


import java.io.BufferedReader;
import java.lang.NullPointerException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author abdallahrizk
 */
public class Java_CinemaTicket extends Application {
    
 // All JavaFX controls Which Being Used:
    //Login Scene:
    TextField user_name;
    PasswordField user_password;
    RadioButton admin_radioButton;
    RadioButton customer_radioButton;
    ToggleGroup radioButton_select;
    Button login_button;
    // CUSTOMER choose Movie Scene:
    ComboBox movie_comboBox;
    TextArea movieDescription_textArea;
    ComboBox cinema_comboBox;
    ComboBox movieTime_comboBox;
    ComboBox movieHall_comboBox;
    Label customerName_label;
    Button nextToSeatsChoice_button;
    ImageView moviePoster_imageView;
    
   // CUSTOMER Summary Scene:
    Label movieName_labelSummary;
    Label movieLocation_labelSummary;
    Label movieTime_labelSummar;
    Label movieSeats_labelSummary;
    Label movieHall_labelSummary;
    Label customerUniqeOrder_labelSummary;
    Button confirm_printTheRecipt;
    
    // Admin Validation Ticket Secne
    TextField ticketPath_textField=new TextField();
    TextField reportPath_textField=new TextField();
    
    
    Label movieName_label =new Label();
    
    // seats which have been selected by user
    
    
    JavaMongoDBConnection db = new JavaMongoDBConnection();
    CSVFileImplemenation csvFile = new CSVFileImplemenation();
    
    
  
    @Override
    @SuppressWarnings("element-type-mismatch")
    public void start(Stage primaryStage) throws FileNotFoundException, ClassNotFoundException, SQLException {
        
       //____________________________________________________________________//
       // Login Scene Start
       //____________________________________________________________________//

        
        VBox login_pane = new VBox(15);
        login_pane.setPadding(new Insets(10,10,10,10));
        GridPane login_gridPane = new GridPane();
        login_gridPane.setHgap(5);
        login_gridPane.setVgap(5);

        
        user_name = new TextField();
        user_password = new PasswordField();
        
        login_gridPane.add(new Label("User Name:"), 0, 0);
        login_gridPane.add(user_name, 1, 0);
        login_gridPane.add(new Label("User Password:"), 0, 1);
        login_gridPane.add(user_password, 1, 1);

        admin_radioButton = new RadioButton("Admin");
        customer_radioButton = new RadioButton("Customer");
        
        radioButton_select = new ToggleGroup();
        admin_radioButton.setToggleGroup(radioButton_select);
        customer_radioButton.setToggleGroup(radioButton_select);
        customer_radioButton.setSelected(true);
        
        HBox type_raw = new HBox(30);
        
        type_raw.getChildren().addAll(new Label("Type:"), admin_radioButton,customer_radioButton);
        
       login_button = new Button("Login");
        HBox centrlize_loginButton = new HBox();
        centrlize_loginButton.getChildren().add(login_button);
        centrlize_loginButton.setAlignment(Pos.CENTER);
        
        login_pane.getChildren().addAll(login_gridPane,type_raw,centrlize_loginButton);
        
        
        
        Scene scene = new Scene(login_pane, 300, 160);
        
        primaryStage.setTitle("Cinema Ticket!");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
        // ________________________________________________________________//
        // CUSTOMER:  Choose Movie Scene
        // ________________________________________________________________//

        // Left Pane Of the borderPane
        VBox left_section = new VBox(10);
        left_section.setPadding(new Insets(10,10,10,10));
        // To be Modify to retrive data from the database
        
        movie_comboBox = new ComboBox(); 
        movie_comboBox.getSelectionModel().selectFirst();
        movie_comboBox.setPromptText("Please choose here");
        movie_comboBox.setItems(db.movieName());

        
        HBox movieChoice_raw = new HBox(5);
        movieChoice_raw.getChildren().addAll(new Label("Select Movie: "), movie_comboBox);
        
        
        
        // To be modify to retrive the description from the database
        movieDescription_textArea = new TextArea();
        movieDescription_textArea.setMaxSize(300, 150);
        movieDescription_textArea.setEditable(false);
        
        
        
        moviePoster_imageView = new ImageView();
                
        movie_comboBox.setOnAction(e->{
         
            try {
                cinema_comboBox.setItems(db.movieLocation(movie_comboBox.getValue().toString()));
                movieDescription_textArea.clear();
                movieDescription_textArea.appendText(db.movieDesription(movie_comboBox.getValue().toString()));
                
            FileInputStream moviePoster_fileInputStream = new FileInputStream(db.moviePoster(movie_comboBox.getValue().toString()));
            Image moviePoster_image = new Image(moviePoster_fileInputStream,300,250,false,false);
             moviePoster_imageView.setImage(moviePoster_image);


            } catch (ClassNotFoundException | SQLException | FileNotFoundException ex) {
                Logger.getLogger(Java_CinemaTicket.class.getName()).log(Level.SEVERE, null, ex);
            }
       
       
       });
        
        left_section.getChildren().addAll(movieChoice_raw,moviePoster_imageView,movieDescription_textArea);
        
        // Right Pane Of the BorderPane
        
        GridPane right_section = new GridPane();
        right_section.setPadding(new Insets(10,10,10,10));
        right_section.setHgap(5);
        right_section.setVgap(50);
        
        cinema_comboBox = new ComboBox();
        cinema_comboBox.setPromptText("Please choose here");

        
        movieTime_comboBox = new ComboBox();
        movieTime_comboBox.setPromptText("Please choose here");

        
        movieHall_comboBox = new ComboBox();
        movieHall_comboBox.setPromptText("Please choose here");

        
        right_section.add(new Label("Select the cinema: "), 0, 0);
        right_section.add(cinema_comboBox, 1, 0);
        right_section.add(new Label("Select time"), 0, 1);
        right_section.add(movieTime_comboBox, 1, 1);
        right_section.add(new Label("Select the movie hall"), 0, 2);
        right_section.add(movieHall_comboBox, 1, 2);
        
        // the string to be change based on the login user
        
        String userName_string = "Abdallah";
        customerName_label = new Label("Welcome  "+userName_string);
        
        nextToSeatsChoice_button = new Button("Next");
        HBox locateNextToSeatsLeft_Button = new HBox();
        locateNextToSeatsLeft_Button.setAlignment(Pos.CENTER_RIGHT);
        locateNextToSeatsLeft_Button.getChildren().add(nextToSeatsChoice_button);
        
        BorderPane customer_pane = new BorderPane();
        customer_pane.setPadding(new Insets(10,10,10,10));
        customer_pane.setTop(customerName_label);
        customer_pane.setBottom(locateNextToSeatsLeft_Button);
        customer_pane.setLeft(left_section);
        customer_pane.setRight(right_section);
        
        Scene customer = new Scene(customer_pane, 670, 440);

        // ________________________________________________________________//
        // CUSTOMER:  Choose Seats Scene
        // ________________________________________________________________//
        
        FileInputStream seats_fileInputStream = new FileInputStream("seat.png");
        Image seats_image = new Image(seats_fileInputStream,50,50,false,false);
        
        FileInputStream AvailabeSeats_fileInputStream = new FileInputStream("seat.png");
        Image AvelableSeats_image = new Image(AvailabeSeats_fileInputStream,50,50,false,false);
        ImageView AvailableSeatsScreen = new ImageView(AvelableSeats_image);

        // add lighting to modify the unavailable seats
        
        Lighting lighting_red = new Lighting();
        lighting_red.setDiffuseConstant(1.0);
        lighting_red.setSpecularConstant(0.0);
        lighting_red.setSpecularExponent(0.0);
        lighting_red.setSurfaceScale(0.0);
        lighting_red.setLight(new Light.Distant(45, 45, Color.RED));
        
        FileInputStream UnavlibleSeats_fileInputStream = new FileInputStream("seat.png");
        Image UnavelableSeats_image = new Image(UnavlibleSeats_fileInputStream,50,50,false,false);
        ImageView UnavailableSeatsScreen = new ImageView(UnavelableSeats_image);
        UnavailableSeatsScreen.setEffect(lighting_red);

      
        HBox seatsImage_availbilty = new HBox(20);
        HBox seatsNote_availbilty = new HBox(20);
        seatsImage_availbilty.setAlignment(Pos.CENTER);
        seatsNote_availbilty.setAlignment(Pos.CENTER);
        seatsImage_availbilty.getChildren().addAll(AvailableSeatsScreen,UnavailableSeatsScreen);
        seatsNote_availbilty.getChildren().addAll(new Label("Available Seats"),new Label("UnAvailable Seats"));

        
        
        FileInputStream seatsScreen_fileInputStream = new FileInputStream("screen.png");
        Image seatsScreen_image = new Image(seatsScreen_fileInputStream,270,30,false,false);
        
        ImageView seatsScreen = new ImageView(seatsScreen_image);
        ImageView[] seats = new ImageView[30];
        

        Lighting orginalLighting = new Lighting();
        orginalLighting.setDiffuseConstant(1.0);
        orginalLighting.setSpecularConstant(0.0);
        orginalLighting.setSpecularExponent(0.0);
        orginalLighting.setSurfaceScale(0.0);
        orginalLighting.setLight(new Light.Distant(85, 85, Color.LIGHTGREY));
        
        //Intilizing all the imageview to the seat image
        for(int i = 0;i<30;i++){
        seats[i] = new ImageView(seats_image); 
        seats[i].setEffect(orginalLighting);
        }
          
        
        HBox seatsRaw_hbox[] = new HBox[5];
        VBox seatsLine_vbox = new VBox(5);
        seatsLine_vbox.setAlignment(Pos.CENTER);
        seatsLine_vbox.setPadding(new Insets(10,10,10,10));
        
            seatsLine_vbox.getChildren().addAll(seatsImage_availbilty,seatsNote_availbilty,seatsScreen);
        
        int seatsCount = 0;
        for(int i=0;i<5;i++){
          seatsRaw_hbox[i]= new HBox();
          seatsRaw_hbox[i].setAlignment(Pos.CENTER);
             for(int j=0;j<6;j++){       
           seatsRaw_hbox[i].getChildren().addAll(seats[seatsCount]);
           seatsCount++;
             }
           seatsLine_vbox.getChildren().add(seatsRaw_hbox[i]);

    } 
                
        
        StackPane seatsDetails_pane = new StackPane();
        seatsDetails_pane.setPadding(new Insets(10,10,10,10));
        seatsDetails_pane.getChildren().add(movieName_label);
        
        
        Button nextToSummaryPage_button = new Button("Next");
        Button backToChooseMovie_button = new Button("Back");
        HBox nextToSummaryPage_pane = new HBox();
        HBox rightButtons = new HBox(nextToSummaryPage_button);
        rightButtons.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(rightButtons, Priority.ALWAYS);
        nextToSummaryPage_pane.getChildren().addAll(backToChooseMovie_button,rightButtons);
        nextToSummaryPage_pane.setPadding(new Insets(9));
        
        
        

        BorderPane seats_pane = new BorderPane();
        seats_pane.setPadding(new Insets(10,10,10,10));
        seats_pane.setCenter(seatsLine_vbox);
        seats_pane.setLeft(seatsDetails_pane);
        seats_pane.setBottom(nextToSummaryPage_pane);
        seats_pane.setTop(customerName_label);

        Scene seats_scene = new Scene(seats_pane, 500, 500);

        // The Image Event Handler:
        
        
        Lighting lighting = new Lighting();
        lighting.setDiffuseConstant(1.0);
        lighting.setSpecularConstant(0.0);
        lighting.setSpecularExponent(0.0);
        lighting.setSurfaceScale(0.0);
        lighting.setLight(new Light.Distant(45, 45, Color.GREEN));
        

        // Array to hold the seats which will be chosed by the customer
      ArrayList<Integer> selectedseats = new ArrayList<>();


     
        for(int i=0;i<30;i++){
            Node seat = seats[i];
        seat.setOnMouseClicked(e->{
            
          if(seat.getEffect() == lighting) {

           seat.setEffect(orginalLighting);

}         else if (seat.getEffect() == orginalLighting) {

            seat.setEffect(lighting);    

}
            
        });     
        }
        
        
        // ________________________________________________________________//
        // CUSTOMER:  Summary Scene
        // ________________________________________________________________//
        
        // These All to be change according to the database
        GridPane summary_gridPane = new GridPane();
        summary_gridPane.setPadding(new Insets(60,10,10,10));
        summary_gridPane.setVgap(35);
        
        movieName_labelSummary = new Label();
        movieLocation_labelSummary = new Label();
        movieTime_labelSummar = new Label();
        movieSeats_labelSummary = new Label();
        customerUniqeOrder_labelSummary = new Label();
        movieHall_labelSummary = new Label();
        
        summary_gridPane.add(new Label("Movie Title: "),0,0);
        summary_gridPane.add(movieName_labelSummary,1,0);
        
        summary_gridPane.add(new Label("Movie Location: "),0,1);
        summary_gridPane.add(movieLocation_labelSummary,1,1);
        
        summary_gridPane.add(new Label("Movie Time: "),0,2);
        summary_gridPane.add(movieTime_labelSummar,1,2);
        
        summary_gridPane.add(new Label("Movie Threator: "),0,3);
        summary_gridPane.add(movieHall_labelSummary,1,3);
        
        summary_gridPane.add(new Label("Seats: "),0,4);
        summary_gridPane.add(movieSeats_labelSummary,1,4);
        
        summary_gridPane.add(new Label("Uniqe ID: "),0,5);
        summary_gridPane.add(customerUniqeOrder_labelSummary,1,5);
        
        confirm_printTheRecipt = new Button("Confirm");
        HBox confirmPrintTheRecipt_Hbox = new HBox();
        confirmPrintTheRecipt_Hbox.setAlignment(Pos.CENTER);
        confirmPrintTheRecipt_Hbox.getChildren().add(confirm_printTheRecipt);
        
        BorderPane summar_borderPane = new BorderPane();
        summar_borderPane.setPadding(new Insets(10,10,10,10));
        
        summar_borderPane.setCenter(summary_gridPane);
        summar_borderPane.setTop(customerName_label);
        summar_borderPane.setBottom(confirmPrintTheRecipt_Hbox);
        
        Scene summary_scene = new Scene(summar_borderPane, 500, 500);

        
        // ________________________________________________________________//
        // CUSTOMER:  Thanks User Scene
        // ________________________________________________________________//
        
        
        VBox thanks_pane = new VBox(10);
        thanks_pane.setAlignment(Pos.CENTER);
        Label thanksMessage = new Label("Your tickets being generated");
        Label thanks_Message = new Label("Thanks");
        
        thanksMessage.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
        thanks_Message.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 22));
        
        Button summaryLogin_button = new Button("Login Again");
        Button summaryExit_button = new Button("Exit");
        HBox thanks_HBoxpane = new HBox(10);
        thanks_HBoxpane.setAlignment(Pos.CENTER);
        thanks_HBoxpane.getChildren().addAll(summaryLogin_button,summaryExit_button);
        

        thanks_pane.getChildren().addAll(thanksMessage,thanks_Message,thanks_HBoxpane);
        thanks_pane.setPadding(new Insets(60,10,10,10));
        
        Scene thanks_scene = new Scene(thanks_pane, 320, 250);

        
        
        // ________________________________________________________________//
        // Admin:  Menu Scene
        // ________________________________________________________________//
        
        VBox adminPane = new VBox(25);
        adminPane.setPadding(new Insets(10,10,10,10));
        adminPane.setAlignment(Pos.CENTER);
        
        Button addMovie_button = new Button("Add Movie");
        addMovie_button.setPrefWidth(130);
        Button deleteMovie_button = new Button("Delete Movie");
        deleteMovie_button.setPrefWidth(130);
        Button report_button = new Button("Report");
        report_button.setPrefWidth(130);
        Button toValidateScene_button = new Button("Ticket Validation");
        toValidateScene_button.setPrefWidth(130);


        
        adminPane.getChildren().addAll(addMovie_button,deleteMovie_button,report_button,toValidateScene_button);
        
        Scene admin_Scene = new Scene(adminPane,150,250);
       
        // ________________________________________________________________//
        // Admin:  Add Movie Scene
        // ________________________________________________________________//
        
        TextField movieNameToAdd_textField = new TextField();
        movieNameToAdd_textField.setPrefColumnCount(9);
        TextArea MovieDescriptionToAdd_textArea = new TextArea();
        MovieDescriptionToAdd_textArea.setMaxSize(150,60);
        
        // The Avalible cinema for the admin to choose once he want to create ne movie:
        ComboBox cinemaToAdd_comboBox = new ComboBox();
        cinemaToAdd_comboBox.getItems().add("Sunway");
        cinemaToAdd_comboBox.getItems().add("MidVally");
        cinemaToAdd_comboBox.getItems().add("KLCC");
        cinemaToAdd_comboBox.getSelectionModel().selectFirst();
        
        ComboBox timeToAdd_comboBox = new ComboBox();
        timeToAdd_comboBox.getItems().add("5PM-7PM");
        timeToAdd_comboBox.getItems().add("7PM-9PM");
        timeToAdd_comboBox.getItems().add("9PM-11PM");
        timeToAdd_comboBox.getItems().add("11PM-1PM");
        timeToAdd_comboBox.getSelectionModel().selectFirst();
        
        ComboBox threatorToAdd_comboBox = new ComboBox();
        threatorToAdd_comboBox.getItems().add("1");
        threatorToAdd_comboBox.getItems().add("2");
        threatorToAdd_comboBox.getItems().add("3");
        threatorToAdd_comboBox.getItems().add("3");
        threatorToAdd_comboBox.getItems().add("3");

        threatorToAdd_comboBox.getSelectionModel().selectFirst();
        
        Button choosePoster_Button = new Button("Browse");
        
        GridPane adminAdd_gridPane = new GridPane();
        adminAdd_gridPane.setHgap(10);
        adminAdd_gridPane.setVgap(10);
        
        adminAdd_gridPane.add(new Label("Movie Title: "),0,0);
        adminAdd_gridPane.add(movieNameToAdd_textField,1,0);
        adminAdd_gridPane.add(new Label("Movie Description: "),0,1);
        adminAdd_gridPane.add(MovieDescriptionToAdd_textArea,1,1);
        adminAdd_gridPane.add(new Label("Cinema: "),0,2);
        adminAdd_gridPane.add(cinemaToAdd_comboBox,1,2);
        adminAdd_gridPane.add(new Label("Time: "),0,3);
        adminAdd_gridPane.add(timeToAdd_comboBox,1,3);
        adminAdd_gridPane.add(new Label("Threator: "),0,4);
        adminAdd_gridPane.add(threatorToAdd_comboBox,1,4);
        adminAdd_gridPane.add(new Label("Choose Poster: "),0,5);
        adminAdd_gridPane.add(choosePoster_Button,1,5);

        Button adminAddMovie_button = new Button("Add Movie");
        HBox adminAddMovieButton_Hbox = new HBox();
        adminAddMovieButton_Hbox.getChildren().add(adminAddMovie_button);
        adminAddMovieButton_Hbox.setAlignment(Pos.CENTER_RIGHT);
        
        BorderPane adminAddMovie_pane = new BorderPane();
        adminAddMovie_pane.setPadding(new Insets(10,10,10,10));
        adminAddMovie_pane.setCenter(adminAdd_gridPane);
        adminAddMovie_pane.setBottom(adminAddMovieButton_Hbox);
        
        Scene adminAddMovie_scene = new Scene(adminAddMovie_pane,300,300);
        
        TextField moviePoster_textField = new TextField();
        
        
        // ________________________________________________________________//
        // Admin:  Delete Movie Scene
        // ________________________________________________________________//
        
        BorderPane deleteMovie_pane = new BorderPane();
        deleteMovie_pane.setPadding(new Insets(20));
        ListView<String> movieName_listView = new ListView<>();
       // movieName_listView.setPadding(new Insets(20));
        movieName_listView.setPrefSize(200, 350);
        TableView<movieDetails> movieDetails_table = new TableView<>();
       // movieDetails_table.setPadding(new Insets(0,0,20,20));
        movieDetails_table.setPrefSize(620, 90);
        
        Button delete_button = new Button("Delete");
        Button showAll_button = new Button("Show All");
        Button back_button = new Button("Back");
   
       
        HBox hbox = new HBox();

        HBox rightSideButtons = new HBox(delete_button, showAll_button);
        rightSideButtons.setAlignment(Pos.CENTER_RIGHT);

        HBox.setHgrow(rightSideButtons, Priority.ALWAYS);

        hbox.getChildren().addAll(back_button, rightSideButtons);
        hbox.setPadding(new Insets(9));


        VBox deleteMovie_vbox= new VBox();
        deleteMovie_vbox.getChildren().addAll(new Label("Movie Titles"),movieName_listView);
        HBox deleteMovie_hbox= new HBox(20);
        deleteMovie_hbox.getChildren().addAll(deleteMovie_vbox,movieDetails_table);
        
        TableColumn<movieDetails,String> movieNameCal=new TableColumn<>("Movie Name");
        TableColumn<movieDetails,String> movieLocCal=new TableColumn<>("Movie Location");
        TableColumn<movieDetails,String> movieTimCal=new TableColumn<>("Movie Time");
        TableColumn<movieDetails,Integer> movieThreCal=new TableColumn<>("Movie Threator");
        TableColumn<movieDetails,String> movieDesCal=new TableColumn<>("Description");

        
        
        movieNameCal.setCellValueFactory(new PropertyValueFactory<>("MovieName"));
        movieLocCal.setCellValueFactory(new PropertyValueFactory<>("Location"));
        movieTimCal.setCellValueFactory(new PropertyValueFactory<>("Time"));
        movieThreCal.setCellValueFactory(new PropertyValueFactory<>("Threator"));
        movieDesCal.setCellValueFactory(new PropertyValueFactory<>("Description"));

        
        
        movieDetails_table.getColumns().add(movieNameCal);
        movieDetails_table.getColumns().add(movieLocCal);
        movieDetails_table.getColumns().add(movieTimCal);
        movieDetails_table.getColumns().add(movieThreCal);
        movieDetails_table.getColumns().add(movieDesCal);


        

        //deleteMovie_pane.setLeft(movieName_listView);
        deleteMovie_pane.setCenter(deleteMovie_hbox);
        deleteMovie_pane.setBottom(hbox);


        Scene deleteMovie_scene = new Scene(deleteMovie_pane,900,450);

        
        // ________________________________________________________________//
        // Admin:  Validate Ticket Scene
        // ________________________________________________________________//
        
        Button validateFromMySql_button = new Button("Validate From Current Database");
        validateFromMySql_button.setPrefWidth(250);
        Button validateFromReport_button = new Button("Validate From Previews Report");
        validateFromReport_button.setPrefWidth(250);
        
        Button ChooseTicket_button = new Button("Browse");
        Button ChooseReport_button = new Button("Browse");
        
        Label validateFromReport_label = new Label();
        Label validateFromMysql_label = new Label();
        
        
        GridPane validate_pane = new GridPane();
        validate_pane.setPadding(new Insets(20));
        validate_pane.setVgap(5);
        validate_pane.setHgap(5);
        validate_pane.add(new Label("Choose Ticket To Validate"),0,0);
        validate_pane.add(ChooseTicket_button,1,0);
        validate_pane.add(new Label("To validate from Previous Report Please Specify the report"),0,1);
        validate_pane.add(ChooseReport_button,1,1);
        validate_pane.add(validateFromReport_button,0,2);
        validate_pane.add(validateFromMySql_button,0,3);
        validate_pane.add(validateFromReport_label,1,2);
        validate_pane.add(validateFromMysql_label,1,3);
        
        HBox backValidateScene_hbox = new HBox();
        Button backValidate_button = new Button("Back");
        backValidateScene_hbox.getChildren().add(backValidate_button);
        backValidateScene_hbox.setAlignment(Pos.BOTTOM_LEFT);
        
        BorderPane validateBorder_pane = new BorderPane();
        validateBorder_pane.setPadding(new Insets(20));
        validateBorder_pane.setCenter(validate_pane);
        validateBorder_pane.setBottom(backValidateScene_hbox);
        
        Scene validate_scene = new Scene(validateBorder_pane,550,230);
        

   
        // ________________________________________________________________//
        //  All Even Handler
        // ________________________________________________________________//   
        
        // ________________________________________________________________//
        // Event Handler Section: add Movie Button On the admin Menu to display add movie scene
        // ________________________________________________________________//
        
        
        backValidate_button.setOnAction(e->{
                     primaryStage.setTitle("Admin GUI");
                     primaryStage.setScene(admin_Scene);
                     primaryStage.show();});
        
        addMovie_button.setOnAction(e->{
        primaryStage.setTitle("Add Movie GUI");
        primaryStage.setScene(adminAddMovie_scene);
        primaryStage.show();
        
        });
        
        // ________________________________________________________________//
        // Event Handler Section: add Movie Button On the add movie scene to insert the movied deltails to the database
        // ________________________________________________________________//
        adminAddMovie_button.setOnAction(e->{
            
        String movieName_String = movieNameToAdd_textField.getText();
        String movieDescription_String = MovieDescriptionToAdd_textArea.getText();
        String moviePoster_string= moviePoster_textField.getText();
        String cinemaLocation_string = cinemaToAdd_comboBox.getSelectionModel().getSelectedItem().toString();
        String movieTime_string = timeToAdd_comboBox.getSelectionModel().getSelectedItem().toString();
        int  movieThretor_integer = Integer.parseInt(threatorToAdd_comboBox.getSelectionModel().getSelectedItem().toString());
        String movieTimeID_string = movieName_String.concat(cinemaLocation_string);
        String movieHallID_string = movieTimeID_string.concat(movieTime_string);
        String combinAll_string = movieName_String.concat(cinemaLocation_string).concat(movieTime_string)+movieThretor_integer;
            
        
            try {
                db.addMovie_insetIntoDB(movieName_String,movieDescription_String,cinemaLocation_string,movieTime_string,movieThretor_integer,moviePoster_string,movieTimeID_string,movieHallID_string,combinAll_string);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(Java_CinemaTicket.class.getName()).log(Level.SEVERE, null, ex);
            }

        primaryStage.setTitle("Admin GUI");
        primaryStage.setScene(admin_Scene);
        primaryStage.show();
        
        });

        
        choosePoster_Button.setOnAction(e->{    
        FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*.jpg","*.png"));                                           
             File file = fileChooser.showOpenDialog(primaryStage);
             String movie = file.getPath();
             moviePoster_textField.setText(movie);
        });
        
        
        login_button.setOnAction(e->{
         String userNamestring = user_name.getText();
         String userstring = user_password.getText();
        if(customer_radioButton.isSelected()==true){
             try {
                 if(db.customer_auth(userNamestring, userstring) == true){
                     primaryStage.setTitle("Customer GUI");
                     primaryStage.setScene(customer);
                     primaryStage.show();
                 }} catch (SQLException | ClassNotFoundException ex) {
                 Logger.getLogger(Java_CinemaTicket.class.getName()).log(Level.SEVERE, null, ex);
             }
        }
        
        else if(admin_radioButton.isSelected()==true){
             try {
                 if(db.admin_auth(userNamestring, userstring) == true){
                     primaryStage.setTitle("Admin GUI");
                     primaryStage.setScene(admin_Scene);
                     primaryStage.show();
                     
                 }    } catch (SQLException | ClassNotFoundException ex) {
                 Logger.getLogger(Java_CinemaTicket.class.getName()).log(Level.SEVERE, null, ex);
             }
        
        }});
      
        
        nextToSeatsChoice_button.setOnAction(e->{
            
            try {
                 String uniqID =movie_comboBox.getValue().toString().concat(movieTime_comboBox.getValue().toString().concat(cinema_comboBox.getValue().toString().concat(user_name.getText())));
        for(int i = 0;i<30;i++){
                if(db.bookedSeats(uniqID).contains(i)){
                    seats[i].setEffect(lighting_red);
                }
        }} catch (ClassNotFoundException | SQLException ex) {
                
                Logger.getLogger(Java_CinemaTicket.class.getName()).log(Level.SEVERE, null, ex);
                
            } catch(NullPointerException u){  
              
                Stage error= new Stage();
                VBox error_vbox = new VBox(8);
                error_vbox.setPadding(new Insets(40));
                error_vbox.setAlignment(Pos.CENTER);
                Button ok_button = new Button("OK");
                error_vbox.getChildren().addAll(new Label("Wrong Please Choose Everything"),ok_button);
                Scene error_scene =new Scene(error_vbox,350,100);
                error.setScene(error_scene);
                error.show();
                ok_button.setOnAction(o->{error.close();});
        }    

        
          movieName_label.setText(String.format("Movie Title: %s",movie_comboBox.getValue().toString()));
   
        primaryStage.setTitle("Seats Pane");
        primaryStage.setScene(seats_scene);
        primaryStage.show();
        
                    });
    
       nextToSummaryPage_button.setOnAction(e->{
         
           for(int i=0;i<30;i++){
           if(seats[i].getEffect()==lighting){
                     selectedseats.add(i);
        }
           }
 
           
        String[] seatsName_array = new String[30];  
     int s=0; int j=0; int g=0; int f=0; int d=0; 
      for(int i=0;i<30;i++){
          while(i<6){
            seatsName_array[i]=String.format("A-%d", j+1);i++;j++;}
          while(i<12){
            seatsName_array[i]=String.format("B-%d", g+1);i++;g++;}
          while(i<18){
            seatsName_array[i]=String.format("C-%d", f+1);i++;f++;}
          while(i<24){
            seatsName_array[i]=String.format("D-%d", d+1);i++;d++;}
          while(i<30){
            seatsName_array[i]=String.format("E-%d", s+1);i++;s++;}
        }
      
      
      ArrayList<String>seatsActualName = new ArrayList<>();
      for(int i=0;i<selectedseats.size();i++){
      seatsActualName.add(seatsName_array[selectedseats.get(i)]);
      }
          
      
        movieName_labelSummary.setText(movie_comboBox.getValue().toString());
        movieLocation_labelSummary.setText(cinema_comboBox.getValue().toString());
        movieTime_labelSummar.setText(movieTime_comboBox.getValue().toString());
        movieHall_labelSummary.setText(movieHall_comboBox.getValue().toString());                  
        movieSeats_labelSummary.setText(seatsActualName.toString());
        customerUniqeOrder_labelSummary.setText(movie_comboBox.getValue().toString().concat(movieTime_comboBox.getValue().toString().concat(cinema_comboBox.getValue().toString())));
          
        primaryStage.setTitle("Summary Pane");
        primaryStage.setScene(summary_scene);
        primaryStage.show();
        
                    });
       
       confirm_printTheRecipt.setOnAction(e->{
        
            try {
                
                File ticketsFile = new File(user_name.getText()+" "+movie_comboBox.getValue().toString()+".txt");
                PrintWriter output = new PrintWriter(ticketsFile);
                    output.println("This Ticket Being Generated as You Confirm");
                    output.println("\nName    : "+user_name.getText());
                    output.println("\nMovie   : "+movie_comboBox.getValue().toString());
                    output.println("\nLocation: "+cinema_comboBox.getValue().toString());
                    output.println("\nTime    : "+movieTime_comboBox.getValue().toString());
                    output.println("\nThreator    : "+movieHall_comboBox.getValue().toString());
                    output.println("\nSeats   : "+movieSeats_labelSummary.getText());
                    output.println("\nUniqueID: "+movie_comboBox.getValue().toString().concat(movieTime_comboBox.getValue().toString().concat(cinema_comboBox.getValue().toString().concat(user_name.getText()))));
                    output.flush();
                    
                
                
         
                  String uniqID =movie_comboBox.getValue().toString().concat(movieTime_comboBox.getValue().toString().concat(cinema_comboBox.getValue().toString().concat(user_name.getText())));
  
                for(int i=0;i<selectedseats.size();i++){
                    try {
                        db.addCustomer_insetIntoDB(user_name.getText(), uniqID, movie_comboBox.getValue().toString(), cinema_comboBox.getValue().toString(), movieTime_comboBox.getValue().toString(), Integer.parseInt(movieHall_comboBox.getValue().toString()), selectedseats.get(i));
                    } catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(Java_CinemaTicket.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }  selectedseats.clear();
                
                
                primaryStage.setTitle("Thanks Pane");
                primaryStage.setScene(thanks_scene);
                primaryStage.show();
                
                FadeTransition ft = new FadeTransition(Duration.millis(3500), thanks_pane);
                ft.setFromValue(0.97);
                ft.setToValue(0.0);
                ft.play();
                ft.setOnFinished(r->{
                    
                    primaryStage.setTitle("Cinema Tickets");
                    primaryStage.setScene(scene);
                    primaryStage.show();
                });
                
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Java_CinemaTicket.class.getName()).log(Level.SEVERE, null, ex);
            }
        
 
        });
       summaryLogin_button.setOnAction(e->{
           
        primaryStage.setTitle("Cinema Tickets");
        primaryStage.setScene(scene);
        primaryStage.show();
       });
       
       summaryExit_button.setOnAction(e->{
         primaryStage.close();
       });
       
       
       
     
       
       cinema_comboBox.setOnAction(e->{
            try {
                movieTime_comboBox.setItems(db.movieTime(movie_comboBox.getValue().toString().concat(cinema_comboBox.getValue().toString())));
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(Java_CinemaTicket.class.getName()).log(Level.SEVERE, null, ex);
            }
           
       
       });
       
       
       movieTime_comboBox.setOnAction(e->{
       
            try {
                movieHall_comboBox.setItems(db.movieHall(movie_comboBox.getValue().toString().concat(cinema_comboBox.getValue().toString().concat(movieTime_comboBox.getValue().toString()))));
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(Java_CinemaTicket.class.getName()).log(Level.SEVERE, null, ex);
            }
       
       });
       
       
       deleteMovie_button.setOnAction(e->{
       
            try {
                
        
                movieName_listView.setItems(db.movieName());
                movieDetails_table.setItems(db.movies_tableView());
                primaryStage.setTitle("Delete Movie Scene");
                primaryStage.setScene(deleteMovie_scene);
                primaryStage.show();
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(Java_CinemaTicket.class.getName()).log(Level.SEVERE, null, ex);
            }
       
       });
       
       
       movieName_listView.setOnMouseClicked(e->{     
            try {
                movieDetails_table.setItems(db.moviesBasedOnMovie_tableView(movieName_listView.getSelectionModel().getSelectedItem()));
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(Java_CinemaTicket.class.getName()).log(Level.SEVERE, null, ex);
            }
       });
       
       
       showAll_button.setOnAction(e->{
            try {
                movieDetails_table.setItems(db.movies_tableView());
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(Java_CinemaTicket.class.getName()).log(Level.SEVERE, null, ex);
            }
       
       });
       
       back_button.setOnAction(e->{
       
           primaryStage.setTitle("Admin GUI");
           primaryStage.setScene(admin_Scene);
           primaryStage.show();
       
       });
       
       delete_button.setOnAction(e->{
           
            try {
                ObservableList<movieDetails>  selectedMovies;
                
                selectedMovies= movieDetails_table.getSelectionModel().getSelectedItems();
                
 
                for(movieDetails movie : selectedMovies) {
                   String movieName = movie.getMovieName();
                   String movieLocation = movie.getLocation();
                   String movieTime = movie.getTime();
                   int movieThreator = movie.getThreator();
                   
                   String combinAll = movieName.concat(movieLocation.concat(movieTime)+movieThreator);
                   db.deleteMovies(combinAll);

                }           
                
                    movieDetails_table.setItems(db.movies_tableView());

            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(Java_CinemaTicket.class.getName()).log(Level.SEVERE, null, ex);
            }
       });
       
       
       
       
       report_button.setOnAction(e->{
           
            try {
                ObservableList<reportDetails>  reportDetailsList;
                
                
                
                reportDetailsList= db.reportDetails();
                csvFile.writeCsvFile(reportDetailsList);
                
                Stage error= new Stage();
                VBox error_vbox = new VBox(8);
                error_vbox.setPadding(new Insets(40));
                error_vbox.setAlignment(Pos.CENTER);
                Button ok_button = new Button("OK");
                error_vbox.getChildren().addAll(new Label("The Report Already Generated"),ok_button);
                Scene error_scene =new Scene(error_vbox,350,100);
                error.setScene(error_scene);
                error.show();
                ok_button.setOnAction(o->{error.close();});
  
                
               //___________________________________
                
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(Java_CinemaTicket.class.getName()).log(Level.SEVERE, null, ex);
            }

       });
       
       toValidateScene_button.setOnAction(e->{
    
        primaryStage.setTitle("Admin GUI");
        primaryStage.setScene(validate_scene);
        primaryStage.show();
    
    });
       
       
       ChooseTicket_button.setOnAction(e->{
      
           FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Choose Ticket", "*.txt"));                                           
             File file = fileChooser.showOpenDialog(primaryStage);
             String ticketPath = file.getPath();
                ticketPath_textField.setText(ticketPath); // To extract the string path outside lembda
             
       });
       
       
       ChooseReport_button.setOnAction(e->{
      
           FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Choose Report", "*.csv"));                                           
             File file = fileChooser.showOpenDialog(primaryStage);
             String reportPath = file.getPath();
                reportPath_textField.setText(reportPath); // To extract the string path outside lembda

             
       });
       
       validateFromReport_button.setOnAction(e->{
       
                FileReader fr = null;
            try {
                // Try To get the uniqID
                fr = new FileReader(ticketPath_textField.getText());
                BufferedReader br = new BufferedReader(fr);
                String unique="";
                String line;
                while((line =  br.readLine()) != null){
                    if (line.contains("UniqueID"))
                    {
                        unique = line.split(":")[1].trim();
                        break;
                    }
                }
                
                if(csvFile.uniqIDChecher(reportPath_textField.getText()).contains(unique)==true){
                validateFromReport_label.setText("Valid Ticket");              
                }else{validateFromReport_label.setText("Not Valid Ticket");}
                
                
                System.out.println(unique);
                
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Java_CinemaTicket.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Java_CinemaTicket.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fr.close();
                } catch (IOException ex) {
                    Logger.getLogger(Java_CinemaTicket.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
       
       
       });
       
       
       validateFromMySql_button.setOnAction(e->{
       
            try {
                FileReader fr = null;
                
                // Try To get the uniqID
                fr = new FileReader(ticketPath_textField.getText());
                BufferedReader br = new BufferedReader(fr);
                String unique="";
                String line;
                while((line =  br.readLine()) != null){
                    if (line.contains("UniqueID"))
                    {
                        unique = line.split(":")[1].trim();
                        break;
                    }
                }
                
                if(db.validateTicket(unique)==true){
                validateFromMysql_label.setText("Valid Ticket");              
                }else{validateFromMysql_label.setText("Not Valid Ticket");}
                
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Java_CinemaTicket.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Java_CinemaTicket.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Java_CinemaTicket.class.getName()).log(Level.SEVERE, null, ex);
            }
       
       
       });
       
       
       backToChooseMovie_button.setOnAction(e->{
       
        primaryStage.setTitle("Customer GUI");
        primaryStage.setScene(customer);
        primaryStage.show();
       });
       
       }  
       


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
     
        launch(args);
    }
    
}
