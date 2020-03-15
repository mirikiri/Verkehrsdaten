/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datennetz_simulation;

import Menu.MenuController;
import Profile.ProfileController;
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

    public static Stage parentWindow;
    public static MenuController menucontrol = new MenuController();
    public static String targetsystem = "Linux";
    public static ProfileController profilcontrol = new ProfileController();
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        parentWindow = primaryStage;
        checkForVerkehrsprofileJSON();

        Parent root = FXMLLoader.load(getClass().getResource("/View/MainWindow.fxml"));

        Scene scene = new Scene(root, 700, 600);

<<<<<<< HEAD
        primaryStage.setTitle("Willkommen");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(700);
        primaryStage.setMinHeight(600);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/Pictures/Cloud_Icon_bottom.png")));
=======
        primaryStage.setTitle("Welcome");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(700);
        primaryStage.setMinHeight(600);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/Pictures/PC_Icon.png")));
>>>>>>> master
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                ConcurrencyUtils.shutdownThreadPoolAndAwaitTermination();   //needed because of something strange in FFT library. This needs to be called once and only once
                //there mustn't be any FFT calls after this line is executed. Therefore it is now at closing of main window
            }
        });

        primaryStage.show();

//        final int contentLength[] = {100, 300, 500};
//        final int packageAmount[] = {500, 100, 1000};
//        List<PaketToSend> paketslist = new ArrayList<>();
//        
//        for (int i = 0; i < packageAmount.length; i++) {
//            for (int j = 0; j < packageAmount[i]; j++) {
//                paketslist.add(new PaketToSend(contentLength[i]));
//            }
//        }
//        
//        Collections.shuffle(paketslist);
//        
//        for (PaketToSend paketToSend : paketslist) {
//            DatagramPacket DpSend = new DatagramPacket(paketToSend.getContent(), paketToSend.getLength(), ip, 1234);
//
//            sleep(3);
//            ds.send(DpSend);
//            System.out.println(DpSend.getData());
//            System.out.println(buf);
//        }
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
    
    //    public void something() {
//        boolean check = true;
//        List<Paket> pakets = new ArrayList<>();
//
//        HashMap<Double, Double> arrival = new HashMap();
//        double timestamp_old = 0;
//        double timestamp_current = 0;
//        double timestamp_difference = 0;
//        double timestamp_difference_all = 0;
//
//        if (check) {    //hier ein check ob mehr als 1 paket pro ms geschickt werden muss
//            
//            //code zum erstellen von struktur zum senden von mehr als 1 paket pro ms hier
//            
//        } else { // hier landen wir wenn die ankunftsrate nicht kritisch hoch ist
//            
//            for (int i = 0; i < pakets.size() - 1; i++) {
//                int waitTime = (int)Math.round(pakets.get(i+1).getTimestamp() - pakets.get(i).getTimestamp());
//                if (waitTime == 0) {
//                    waitTime = 1; //falls 2 pakete sehr nah aufeinander folgen, werden sie hiermit auf 1 ms getrennt. Sollte nicht großartig bemerkbar sein
//                }
//                //jetzt hast die im endeffekt die wartezeit vom aktuellen paket aufs nächste. Die Wartezeit kannst beim Senden benutzen: Thread.sleep(waitTime)
//                //wie du die waitTime speicherst, musst du schauen. Mit der HashMap hab ich noch nie gearbeitet, mit der kann ich dir online nicht helfen.
//                //Da müssten wir uns wenn dann mal zusammensetzen und du erklärst mir wie du rangehen möchtest.
//                //Ich hätte es anders gelöst, aber will dir da jetzt nicht zu sehr reinreden ;)
//            }
//        }
//
//    }

}
