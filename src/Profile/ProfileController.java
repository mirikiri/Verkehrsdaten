/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Profile;

import static Profile.Profil.profil_Type.Allegro_Office;
import static Profile.Profil.profil_Type.Backbone_Rauschen;
import static Profile.Profil.profil_Type.Gauss;
import static Profile.Profil.profil_Type.Gleichverteilung;
import static Profile.Profil.profil_Type.IoT;
import static Profile.Profil.profil_Type.Signal_Generator;
import static Profile.Profil.profil_Type.Spotify;
import static Profile.Profil.profil_Type.Stresstest;
import static Profile.Profil.profil_Type.VoIP;
import static Profile.Profil.profil_Type.Web;
import static datennetz_simulation.Datennetz_Simulation.targetsystem;
import static datennetz_simulation.Datennetz_Simulation.sendType;
import static datennetz_simulation.Datennetz_Simulation.profileMap;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Miriam
 */
public class ProfileController {

    private List<String> piOrder = new ArrayList<String>();
    private Profil profil;
    private String order;

    
    public List<String> gsetOrderList(String signalType, String duration, String minSize, String maxSize, String rauschen_skalierung, String web_anzahlSeiten, String web_wartezeit) {
        //Profile für Raspberry Pis
        piOrder = profileMap.get(signalType);
        for(int i =0; i<piOrder.size(); i++){
            if(piOrder.get(i).equals("./web ")){
                order = "./web " + web_anzahlSeiten + " " + web_wartezeit;// + duration;
                piOrder.set(i, order);
            }
            else if(piOrder.get(i).equals("./voip ")){
                order = "./voip " + minSize + " " + maxSize + " " + duration;
                piOrder.set(i, order);
            }
            else if(piOrder.get(i).equals("./rauschen ")){
                order = "./rauschen " + duration + " " + rauschen_skalierung;
                piOrder.set(i, order);
            }
            else{
                order = piOrder.get(i) + duration;
                piOrder.set(i, order);
            }
        }
        return piOrder;
    }

    public Profil getProfilOrder(String signalType) {
        //Profile für Windows
        
        switch (signalType) {
            case "Skype-Shared-Screen":
                profil = new Profil("skypesc ", Backbone_Rauschen);
                sendType = "send2";
                break;
            case "Web":
                profil = new Profil("web " , Web);
                break;
            case "VoIP":
                profil = new Profil("voip ", VoIP);
                break;
            case "YouTube 240p":
                profil = new Profil("youtube240p ", Backbone_Rauschen);
                break;
            case "YouTube 720p":
                profil = new Profil("youtube720p ", Backbone_Rauschen);
                break;
            case "YouTube 2160p":
                profil = new Profil("youtube2160p ", Backbone_Rauschen);
                break;
            case "Excel":
                profil = new Profil("excel ", Backbone_Rauschen);
                break;
            case "ES Office":
                profil = new Profil("esoffice_udp ", Backbone_Rauschen);
                break;
            case "Lasttest":
                profil = new Profil("lasttest_udp ", Stresstest);
                break;
            case "Outlook Start":
                profil = new Profil("outlook_start_udp ", Backbone_Rauschen);
                break;
            case "Print":
                profil = new Profil("print ", Backbone_Rauschen);
                break;
            case "Rauschen":
                profil = new Profil("rauschen ", Backbone_Rauschen);
                break;
            case "Remote Desktop":
                profil = new Profil("remote_desktop_udp ", Backbone_Rauschen);
                break;
            case "Gaming":
                profil = new Profil("gaming ", Backbone_Rauschen);
                break;
            case "VoIP_Multi":
                profil = new Profil("voip_multi ", Backbone_Rauschen);
                break;
            case "Gleichverteilung":
                profil = new Profil("Gleichverteilung ", Gleichverteilung);
                break;
            case "Gauss":
                profil = new Profil("Gauss ", Gauss);
                break;
            case "IoT":
                profil = new Profil("IoT ", IoT);
                break;
            case "Signal_Generator":
                profil = new Profil("Signal_Generator ", Signal_Generator);
                break;
            case "Spotify":
                profil = new Profil("Spotify ", Spotify);
                break;
            case "Allegro_Office":
                profil = new Profil("Allegro_Office ", Allegro_Office);
                break;
            default:
                System.out.println("Kein Signaltyp ausgewählt!");
                break;
        }
        return profil;
    }
}
