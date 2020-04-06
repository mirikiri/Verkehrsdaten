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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.Paket;
import model.PaketToSend;

/**
 *
 * @author Miriam
 */
public class Profil {

    public enum profil_Type {
        Gleichverteilung, Gauss, VoIP, Web, Stresstest, IoT, Signal_Generator, Spotify, Backbone_Rauschen, Allegro_Office; 
    }

    protected String name;
    protected profil_Type type;
    protected List<PaketToSend> pakets;
    protected List<PaketToSend> packetToSend;
    protected List<PaketToSend> packetslist = new ArrayList<>();
    protected int sleeptime;
    
    
    protected double timestamp_old = 0;                     // für Signal_Generator
    protected double timestamp_current = 0;
    protected double timestamp_difference = 0;
    protected double timestamp_difference_all = 0;
    protected int package_counter = 0;
    protected int package_duration = 0;
    protected List<Paket> packages = new ArrayList<>();

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
    
    
     public void send1() throws SocketException, UnknownHostException, IOException, InterruptedException {
        DatagramSocket ds = new DatagramSocket();

        InetAddress ip = InetAddress.getByName("192.168.0.58");
        System.out.println("Start");

        DatagramPacket DpSend;
           for (PaketToSend packetToSend : packetslist) {            
                DpSend = new DatagramPacket(packetToSend.getContent(), packetToSend.getLength(), ip, 1234);
        
                sleep(sleeptime);              //sleeps for 30 milliseconds, evtl. für die vielen icmps verantwortlich?
                ds.send(DpSend);
                System.out.println(DpSend.getData());
                System.out.println(packetToSend.getContent());
            
        }
    }
     
    public void send2() throws SocketException, UnknownHostException, IOException, InterruptedException {
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
    
    
    
     public void send3() throws SocketException, UnknownHostException, IOException, InterruptedException {
        DatagramSocket ds = new DatagramSocket();

        InetAddress ip = InetAddress.getByName("192.168.0.58");
        System.out.println("Start");

        DatagramPacket DpSend;
        timestamp_old = 0;
         for (Paket paket: packages) {
                byte[] packageContent = new byte[paket.getPaketlength()];
                Arrays.fill(packageContent, (byte) 1 );
                
                timestamp_current = paket.getTimestamp();
                timestamp_difference = timestamp_current - timestamp_old;
                timestamp_old = timestamp_current;
                int sleep_value = (int) timestamp_difference *1000; 
               
                DpSend = new DatagramPacket(packageContent, paket.getPaketlength(), ip, 1234);      
                sleep(sleep_value);              //sleeps for 3 milliseconds, evtl. für die vielen icmps verantwortlich?
                ds.send(DpSend);
                
                System.out.println(DpSend.getData());            
        
            }
    }
}

