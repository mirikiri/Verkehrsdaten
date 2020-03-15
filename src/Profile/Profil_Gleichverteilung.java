/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Profile;

import java.util.Arrays;

/**
 *
 * @author Admin
 */
public class Profil_Gleichverteilung extends Profil {
    
    public Profil_Gleichverteilung(String name, Profil.profil_Type type, int dauer, int ankunftsrate) {
       super(name, type);
       int[] pakets = new int[dauer];
       Arrays.fill(pakets, ankunftsrate);
       this.paketArray = pakets;
    }
}
