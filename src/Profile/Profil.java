/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Profile;

import com.google.gson.Gson;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Miriam
 */
public class Profil {
    
    public enum profil_Type {
        GLEICHVERTEILUNG, GERADE, GAUSS, VOIP, WEB, REALMESSUNG
    }

    protected String name;
    protected profil_Type type;
    protected int[] paketArray;

    public Profil(String name, profil_Type type) {
        this.name = name;
        this.type = type;
    }

    public void saveAndWrite(Gson gson) {
        String json = gson.toJson(this);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.name + ".json"));
            writer.write(json);
            writer.close();
        } catch (IOException ex) {
        }
    }
    
    public void sendPakets() {
        
    }
    
}
