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
import static java.lang.Thread.sleep;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;
import model.PaketToSend;

/**
 *
 * @author Miriam
 */
public class Profil {

    public enum profil_Type {
        GLEICHVERTEILUNG, GAUSS, VOIP, WEB, REALMESSUNG
    }

    protected String name;
    protected profil_Type type;
    protected List<PaketToSend> pakets;

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

    public void send() throws SocketException, UnknownHostException, IOException, InterruptedException {
        DatagramSocket ds = new DatagramSocket();

        InetAddress ip = InetAddress.getByName("192.168.0.58");
        System.out.println("Start");

        DatagramPacket DpSend;
        for (int iteration = 1; iteration >= 0; iteration--) {
            for (PaketToSend paket : pakets) {
                DpSend = new DatagramPacket(paket.getContent(), paket.getLength(), ip, 1234);
                ds.send(DpSend);
                System.out.println(DpSend.getData());
                System.out.println(paket.getContent());
                sleep(paket.getWaitTime());
            }
        }
    }
}
