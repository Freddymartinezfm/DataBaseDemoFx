package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sample.model.Datasource;


public class Main extends Application {
    public static final Logger logger = LogManager.getLogger(Main.class);


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
//        GridPane root = new GridPane();
//        root.setAlignment(Pos.CENTER);
//        root.setVgap(10);
//        root.setHgap(10);
//
//        Label greeting = new Label("Welcome to JavaFX!");
//        greeting.setTextFill(Color.GREEN);
//        root.getChildren().add(greeting);
//
        primaryStage.setTitle("Hello JavaFX!");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
       if (!Datasource.getInstance().open()){
           logger.fatal("Couldn't connect to database");
           System.out.println("Couldn't connect to database");
       }

       logger.info("Database open successfully");
       logger.debug("debug");
       logger.info("info");
       logger.trace("trace");
       logger.fatal("fatal");
        System.out.println("Database open successfully");

    }

    @Override
    public void stop() throws Exception {
        Datasource.getInstance().close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
