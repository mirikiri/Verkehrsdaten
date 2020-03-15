/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Profile;

import static datennetz_simulation.Datennetz_Simulation.targetsystem;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Miriam
 */
public class ProfileController {

    
    private List<String> piOrder = new ArrayList<String>();
    private String aufruf; //Variable um den Ausführbefehl für das Zielsystem (Linux oder Windows) anzupassen
    
    public List<String> gsetOrderList(String signalType,String duration, String minSize, String maxSize, String rauschen_skalierung, String web_anzahlSeiten, String web_wartezeit) {
        if (targetsystem.equals("Linux"))
            aufruf = "./";
        else
            aufruf = "";
        
        piOrder.clear();
        switch (signalType) {
            case "Skype-Shared-Screen":
                piOrder.add(aufruf + "skypesc " + duration);
                break;
            case "Web":
                piOrder.add(aufruf + "web " + web_anzahlSeiten + " " + web_wartezeit);
                break;
            case "VoIP":
                piOrder.add(aufruf + "voip " + minSize + " " + maxSize + " " + duration);
                break;
            case "YouTube 240p":
                piOrder.add(aufruf + "youtube240p " + duration);
                break;
            case "YouTube 720p":
                piOrder.add(aufruf + "youtube720p " + duration);
                break;
            case "YouTube 2160p":
                piOrder.add(aufruf + "youtube2160p " + duration);
                break;
            case "Excel":
                piOrder.add(aufruf + "excel " + duration);
                break;
            case "ES Office":
                piOrder.add(aufruf + "esoffice_udp " + duration);
                break;
            case "Lasttest":
                piOrder.add(aufruf + "lasttest_udp " + duration);
                break;
            case "Outlook Start":
                piOrder.add(aufruf + "outlook_start_udp " + duration);
                break;
            case "Print":
                piOrder.add(aufruf + "print " + duration);
                break;
            case "Rauschen":
                piOrder.add(aufruf + "rauschen " + duration + " " + rauschen_skalierung);
                break;
            case "Remote Desktop":
                piOrder.add(aufruf + "remote_desktop_udp " + duration);
                break;
            case "Gaming":
                piOrder.add(aufruf + "gaming " + duration);
                break;
            case "VoIP_Multi":
                piOrder.add(aufruf + "voip_multi " + duration);
                break;
            case "Industrie 1":
                piOrder.add(aufruf + "rauschen " + duration + " " + rauschen_skalierung);
                piOrder.add(aufruf + "excel " + duration);
                piOrder.add(aufruf + "outlook_start_udp " + duration);
                piOrder.add(aufruf + "print " + duration);
                piOrder.add(aufruf + "skypesc " + duration);
                piOrder.add(aufruf + "voip_profil " + duration);
                piOrder.add(aufruf + "web_profil " + duration);
                break;
            case "Industrie 2":
                piOrder.add(aufruf + "rauschen " + duration + " " + rauschen_skalierung);
                piOrder.add(aufruf + "esoffice_udp " + duration);
                piOrder.add(aufruf + "esoffice_udp " + duration);
                piOrder.add(aufruf + "esoffice_udp " + duration);
                piOrder.add(aufruf + "voip_multi " + duration);
                piOrder.add(aufruf + "voip_multi " + duration);
                break;
            case "Industrie 3":
                piOrder.add(aufruf + "rauschen " + duration + " " + rauschen_skalierung);
                piOrder.add(aufruf + "esoffice_udp " + duration);
                piOrder.add(aufruf + "excel " + duration);
                piOrder.add(aufruf + "outlook_start_udp " + duration);
                piOrder.add(aufruf + "print " + duration);
                piOrder.add(aufruf + "remote_desktop_udp " + duration);
                piOrder.add(aufruf + "voip_multi " + duration);
                piOrder.add(aufruf + "web_profil " + duration);
                break;
            case "Privat":
                piOrder.add(aufruf + "rauschen " + duration + " " + rauschen_skalierung);
                piOrder.add(aufruf + "outlook_start_udp " + duration);
                piOrder.add(aufruf + "print " + duration);
                piOrder.add(aufruf + "skypesc " + duration);
                piOrder.add(aufruf + "web_profil " + duration);
                piOrder.add(aufruf + "youtube2160p " + duration);
                break;
            case "Multimedia 1":
                piOrder.add(aufruf + "rauschen " + duration + " " + rauschen_skalierung);
                piOrder.add(aufruf + "gaming " + duration);
                piOrder.add(aufruf + "gaming " + duration);
                piOrder.add(aufruf + "voip_profil " + duration);
                piOrder.add(aufruf + "voip_profil " + duration);
                piOrder.add(aufruf + "youtube720p " + duration);
                break;
            case "Multimedia 2":
                piOrder.add(aufruf + "rauschen " + duration + " " + rauschen_skalierung);
                piOrder.add(aufruf + "gaming " + duration);
                piOrder.add(aufruf + "web_profil " + duration);
                piOrder.add(aufruf + "web_profil " + duration);
                piOrder.add(aufruf + "web_profil " + duration);
                piOrder.add(aufruf + "youtube240p " + duration);
                piOrder.add(aufruf + "youtube720p " + duration);
                piOrder.add(aufruf + "youtube2160p " + duration);
                break;
            default:
                System.out.println("Kein Signaltyp ausgewählt!");
                break;
        }
        return piOrder;
    }
}
