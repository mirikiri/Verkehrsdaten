/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datennetz_simulation;

import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.CreateVKjson;
import pl.edu.icm.jlargearrays.ConcurrencyUtils;

/**
 *
 * @author Admin
 */
//just soime random comment
public class Datennetz_Simulation extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        checkForVerkehrsprofileJSON();

        Parent root = FXMLLoader.load(getClass().getResource("/View/MainWindow.fxml"));

        Scene scene = new Scene(root, 300, 275);

        primaryStage.setTitle("FXML Welcome");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/Pictures/icon.jpg")));
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(330);
        primaryStage.setMinHeight(275);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                ConcurrencyUtils.shutdownThreadPoolAndAwaitTermination();   //needed because of something strange in FFT library. This needs to be called once and only once
                //there mustn't be any FFT calls after this line is executed. Therefore it is now at closing of main window
            }
        });

        primaryStage.show();
    }

    public void checkForVerkehrsprofileJSON() {
        //check if verkehrsprofile.json exists. if not, create it
        File tempFile = new File("verkehrsprofile.json");
        if (!tempFile.exists()) {
            CreateVKjson vk = new CreateVKjson();
            vk.parseVKToJson();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
