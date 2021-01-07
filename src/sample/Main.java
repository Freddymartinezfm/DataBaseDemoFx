package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        controller.listArtists();

        primaryStage.setTitle("Music Database");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        super.init();
       if (!Datasource.getInstance().open()){
           logger.fatal("Couldn't connect to database");
           Platform.exit();
       } else {
           // TODO create a pop up confirmation dialog
            System.out.println("Database open successfully");
       }

//       logger.info("Database open successfully");
//       logger.debug("debug");
//       logger.info("info");
//       logger.trace("trace");
//       logger.fatal("fatal");

    }

    @Override
    public void stop() throws Exception {
        super.stop();
        Datasource.getInstance().close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
