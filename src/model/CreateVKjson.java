/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


//this big mess of a class includes verkehrsprofile in c-code with adjustments to make it runnable in java

public class CreateVKjson {

    private final int[] POWERS_OF_10 = {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000};
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final List<Verkehrsprofil> profile = new ArrayList<>();
    private final Random random = new Random();

    public boolean parseVKToJson() {
        esoffice_udp();
        excel();
        gaming();
        lasttest();
        outlook_start_udp();
        print();
        rauschen();
        remote_desktop_udp();
        skypesc();
        voip_multi();
        voip_profil();
        web_profil();
        youtube2160p();
        youtube720p();
        youtube240p();

        boolean result = writeProfiles();
        
        return result;
    }

    public void esoffice_udp() {
        List<Paket> pakets = new ArrayList<>();
        int i;
        int j = 0;

        int[] len = new int[1200];

        for (i = 0; i < 1200; i = i + 12) {
            len[i] = 24;
        }
        for (i = 1; i < 1200; i = i + 12) {
            len[i] = 24;
        }
        for (i = 2; i < 1200; i = i + 12) {
            len[i] = 24;
        }
        for (i = 3; i < 1200; i = i + 12) {
            len[i] = 0;
        }
        for (i = 4; i < 1200; i = i + 12) {
            len[i] = 1472;
        }
        for (i = 5; i < 1200; i = i + 12) {
            len[i] = 1472;
        }
        for (i = 6; i < 1200; i = i + 12) {
            len[i] = 1472;
        }
        for (i = 7; i < 1200; i = i + 12) {
            len[i] = 1472;
        }
        for (i = 8; i < 1200; i = i + 12) {
            len[i] = 1472;
        }
        for (i = 9; i < 1200; i = i + 12) {
            len[i] = 1472;
        }
        for (i = 10; i < 1200; i = i + 12) {
            len[i] = 1472;
        }
        for (i = 11; i < 1200; i = i + 12) {
            len[i] = 1472;
        }

        int[] len2 = new int[3000];

        for (i = 0; i < 3000; i++) {
            j++;
            len2[i] = 1 + j;
            if (j > 1472) {
                j = 0;
            }
        }
        int n;
        int k;

        for (n = 0; n < 1; n++) {
            /*Pro Schleifendurchlauf 1 Minute Youtube-Video*/

            for (i = 0; i < 3; i++) {
                /*ersten 3 Schleifen mit 66 Byte werden mit 3 * 3000 = 9000 Pakete gesendet zu ta1 = 500µs*/
                for (k = 0; k < 3000; k++) {
                    if (!pakets.isEmpty()) {
                        double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + 100;
                        pakets.add(new Paket(timestamp / POWERS_OF_10[6], len[i], Paket.Protocol.UNIDENTIFIED));
                    } else {
                        pakets.add(new Paket(0.0, len[i], Paket.Protocol.UNIDENTIFIED));
                    }
                }
            }

            for (i = 4; i < 12; i++) {
                /*ersten 8 Schleifen mit 1514 werden mit 8 * 3000 = 24000 Pakete gesendet zu ta1 = 1600µs*/
                for (k = 0; k < 3000; k++) {
                    double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + 125;
                    pakets.add(new Paket(timestamp / POWERS_OF_10[6], len[i], Paket.Protocol.UNIDENTIFIED));
                }
            }

            for (i = 0; i < 3000; i++) {
                /*3000 Schleifen von 66 bis 1514 Byte hochgezählt werden mit 3000* 1 = 3000 Pakete gesendet zu ta1 = 1500µs*/
                double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + 125;
                pakets.add(new Paket(timestamp / POWERS_OF_10[6], len2[i], Paket.Protocol.UNIDENTIFIED));
            }
        }
        createVerkehrsprofil(pakets, "esoffice_udp");
    }

    public void excel() {
        List<Paket> pakets = new ArrayList<>();
        int i;
        int[] len = new int[1000];

        for (i = 0; i < 1000; i = i + 50) {
            len[i] = 222;
        }
        for (i = 1; i < 1000; i = i + 50) {
            len[i] = 13;
        }
        for (i = 2; i < 1000; i = i + 50) {
            len[i] = 294;
        }
        for (i = 3; i < 1000; i = i + 50) {
            len[i] = 13;
        }
        for (i = 4; i < 1000; i = i + 50) {
            len[i] = 18;
        }
        for (i = 5; i < 1000; i = i + 50) {
            len[i] = 24;
        }
        for (i = 6; i < 1000; i = i + 50) {
            len[i] = 30;
        }
        for (i = 7; i < 1000; i = i + 50) {
            len[i] = 366;
        }
        for (i = 8; i < 1000; i = i + 50) {
            len[i] = 36;
        }
        for (i = 9; i < 1000; i = i + 50) {
            len[i] = 42;
        }
        for (i = 10; i < 1000; i = i + 50) {
            len[i] = 48;
        }
        for (i = 11; i < 1000; i = i + 50) {
            len[i] = 54;
        }
        for (i = 12; i < 1000; i = i + 50) {
            len[i] = 50;
        }
        for (i = 13; i < 1000; i = i + 50) {
            len[i] = 438;
        }
        for (i = 14; i < 1000; i = i + 50) {
            len[i] = 66;
        }
        for (i = 15; i < 1000; i = i + 50) {
            len[i] = 510;
        }
        for (i = 16; i < 1000; i = i + 50) {
            len[i] = 13;
        }
        for (i = 17; i < 1000; i = i + 50) {
            len[i] = 582;
        }
        for (i = 18; i < 1000; i = i + 50) {
            len[i] = 13;
        }
        for (i = 19; i < 1000; i = i + 50) {
            len[i] = 72;
        }
        for (i = 20; i < 1000; i = i + 50) {
            len[i] = 654;
        }
        for (i = 21; i < 1000; i = i + 50) {
            len[i] = 13;
        }
        for (i = 22; i < 1000; i = i + 50) {
            len[i] = 726;
        }
        for (i = 23; i < 1000; i = i + 50) {
            len[i] = 13;
        }
        for (i = 24; i < 1000; i = i + 50) {
            len[i] = 78;
        }
        for (i = 25; i < 1000; i = i + 50) {
            len[i] = 84;
        }
        for (i = 26; i < 1000; i = i + 50) {
            len[i] = 90;
        }
        for (i = 27; i < 1000; i = i + 50) {
            len[i] = 96;
        }
        for (i = 28; i < 1000; i = i + 50) {
            len[i] = 102;
        }
        for (i = 29; i < 1000; i = i + 50) {
            len[i] = 108;
        }
        for (i = 30; i < 1000; i = i + 50) {
            len[i] = 114;
        }
        for (i = 31; i < 1000; i = i + 50) {
            len[i] = 120;
        }
        for (i = 32; i < 1000; i = i + 50) {
            len[i] = 126;
        }
        for (i = 33; i < 1000; i = i + 50) {
            len[i] = 798;
        }
        for (i = 34; i < 1000; i = i + 50) {
            len[i] = 132;
        }
        for (i = 35; i < 1000; i = i + 50) {
            len[i] = 870;
        }
        for (i = 36; i < 1000; i = i + 50) {
            len[i] = 13;
        }
        for (i = 37; i < 1000; i = i + 50) {
            len[i] = 942;
        }
        for (i = 38; i < 1000; i = i + 50) {
            len[i] = 13;
        }
        for (i = 39; i < 1000; i = i + 50) {
            len[i] = 138;
        }
        for (i = 40; i < 1000; i = i + 50) {
            len[i] = 1032;
        }
        for (i = 41; i < 1000; i = i + 50) {
            len[i] = 13;
        }
        for (i = 42; i < 1000; i = i + 50) {
            len[i] = 1086;
        }
        for (i = 43; i < 1000; i = i + 50) {
            len[i] = 13;
        }
        for (i = 44; i < 1000; i = i + 50) {
            len[i] = 144;
        }
        for (i = 45; i < 1000; i = i + 50) {
            len[i] = 150;
        }
        for (i = 46; i < 1000; i = i + 50) {
            len[i] = 1374;
        }
        for (i = 47; i < 1000; i = i + 50) {
            len[i] = 1158;
        }
        for (i = 48; i < 1000; i = i + 50) {
            len[i] = 1302;
        }
        for (i = 49; i < 1000; i = i + 50) {
            len[i] = 1240;
        }
        int n;
        int k;

        for (n = 0; n < 1; n++) {
            /*Pro Schleifendurchlauf 1 Minute Youtube-Video*/
            for (i = 0; i < 1000; i++) {
                /*Erste Sekunde besitzt 10 * 245 = 2450 Pakete zu ta1 = 408ys*/
                if (!pakets.isEmpty()) {
                    double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + 2000;
                    pakets.add(new Paket(timestamp / POWERS_OF_10[6], len[i], Paket.Protocol.UNIDENTIFIED));
                } else {
                    pakets.add(new Paket(0.0, len[i], Paket.Protocol.UNIDENTIFIED));
                }
            }
        }
        createVerkehrsprofil(pakets, "excel");
    }

    public void gaming() {
        List<Paket> pakets = new ArrayList<>();
        int[] len = new int[4720];
        int i = 0;

        /*Gauß-Normalverteilung*/
        double f;   //Anzahl Pakete*/
        double mittel = 175 - 42;       //Erwartungswert*/
        double stdabw = 25;             //Standartabweichung*/
        double x;                       //Momentanwert
        double Normierungsfaktor = 787; //für 10sec skype*/
        double wurzprod = Math.sqrt(2 * Math.PI);
        double Multiplikator = (1 / (stdabw * wurzprod)) * Normierungsfaktor;	//Vorkalkulation zur Reduzierung der Rechenlast

        int k;
        int z = 0;

        //Gearbeitet wird innerhalb der Grenzen von 6 Sigma ausgehend vom Mittelwert
        for (x = 100 - 42; x <= 250 - 42; x++) {
            i = i + z;

            double hoch, p;
            p = ((x - mittel) / stdabw);
            hoch = p * p;
            /*quadratfunktion für gauß*/

            double a = (-0.5 * hoch);
            /*Argument für exponentailfunktion für gauß*/

            f = (Math.exp(a) * Multiplikator);
            /*Dichtefunktion von Gauß multipliziert mit Normierungsfaktor*/
            z = (int) Math.round(f);
            /*Ergebnis von f Runden*/

            //Füllen des Längenarrays len mit den Datenlängen, die später versendet werden sollen
            for (k = 0; k < z; k++) {
                len[i + k] = (int) x;
            }

        }

        int[] len2 = new int[2696];
        int i2 = 0;

        /*Gauß-Normalverteilung*/
        double f2;
        /*Anzahl Pakete*/
        double mittel2 = 420 - 42;
        /*Erwartungswert*/
        double stdabw2 = 30;
        /*Standartabweichung*/
        double x2;							//Momentanwert
        double Normierungsfaktor2 = 449;
        /*für 10sec skype*/
        double wurzprod2 = Math.sqrt(2 * Math.PI);
        double Multiplikator2 = (1 / (stdabw2 * wurzprod2)) * Normierungsfaktor2;	//Vorkalkulation zur Reduzierung der Rechenlast

        int k2;
        int z2 = 0;

        //Gearbeitet wird innerhalb der Grenzen von 6 Sigma ausgehend vom Mittelwert
        for (x2 = 330 - 42; x2 <= 510 - 42; x2++) {
            i2 = i2 + z2;

            double hoch2, p2;
            p2 = ((x2 - mittel2) / stdabw2);
            hoch2 = p2 * p2;
            /*quadratfunktion für gauß*/

            double a2 = (-0.5 * hoch2);
            /*Argument für exponentailfunktion für gauß*/

            f2 = (Math.exp(a2) * Multiplikator2);
            /*Dichtefunktion von Gauß multipliziert mit Normierungsfaktor*/
            z2 = (int) Math.round(f2);
            /*Ergebnis von f Runden*/

            //Füllen des Längenarrays len mit den Datenlängen, die später versendet werden sollen
            for (k2 = 0; k2 < z2; k2++) {
                len2[i2 + k2] = (int) x2;
            }

        }

        int k3;
        int[] len3 = new int[3965];

        /*Restliche Sendegrößen in das Längenarray eintragen*/
        for (k3 = 0; k3 < 159; k3++) {
            len3[k3] = 60 - 42;
        }
        for (k3 = 159; k3 < 320; k3++) {
            len3[k3] = 107 - 42;
        }
        for (k3 = 320; k3 < 489; k3++) {
            len3[k3] = 130 - 42;
        }
        for (k3 = 489; k3 < 760; k3++) {
            len3[k3] = 138 - 42;
        }
        for (k3 = 760; k3 < 1091; k3++) {
            len3[k3] = 146 - 42;
        }
        for (k3 = 1091; k3 < 1518; k3++) {
            len3[k3] = 154 - 42;
        }
        for (k3 = 1518; k3 < 2059; k3++) {
            len3[k3] = 162 - 42;
        }
        for (k3 = 2059; k3 < 2616; k3++) {
            len3[k3] = 170 - 42;
        }
        for (k3 = 2616; k3 < 3144; k3++) {
            len3[k3] = 178 - 42;
        }
        for (k3 = 3144; k3 < 3516; k3++) {
            len3[k3] = 186 - 42;
        }
        for (k3 = 3516; k3 < 3763; k3++) {
            len3[k3] = 194 - 42;
        }
        for (k3 = 3763; k3 < 3965; k3++) {
            len3[k3] = 202 - 42;
        }

        int[] len4 = new int[627];
        int k4;
        int j = 0;

        for (k4 = 0; k4 < 627; k4++) {
            j++;
            len4[k4] = (int) (1 + Math.round(2.3 * j));
            if (j > 1454 / 3) {
                j = 0;
            }
        }
        int n;
        int l;

        for (l = 0; l < 1; l++) {
            /*Jeder Schleifendurchlauf entspricht 1 min skype-Video-shared screen*/
            for (n = 0; n < 6; n++) {
                /*Jeder Schleifendurchlauf entspricht 10 sec skype-Video-shared screen*/

                for (i = 0; i < 786; i++) {
                    /*Vorsicht i darf nur bis 2934 laufen, sonst Überschreitung der Arrays*/
                    if (!pakets.isEmpty()) {
                        double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + 5000;
                        pakets.add(new Paket(timestamp / POWERS_OF_10[6], len[i], Paket.Protocol.UNIDENTIFIED));
                    } else {
                        pakets.add(new Paket(0.0, len[i], Paket.Protocol.UNIDENTIFIED));
                    }
                }
            }

            for (n = 0; n < 6; n++) {
                /*Jeder Schleifendurchlauf entspricht 10 sec skype-Video-shared screen*/
                for (i = 0; i < 449; i++) {
                    /*Vorsicht i darf nur bis 2934 laufen, sonst Überschreitung der Arrays*/
                    double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + 5000;
                    pakets.add(new Paket(timestamp / POWERS_OF_10[6], len2[i], Paket.Protocol.UNIDENTIFIED));
                }
            }

            for (n = 0; n < 1; n++) {
                /*Jeder Schleifendurchlauf entspricht 10 sec skype-Video-shared screen*/
                for (k3 = 0; k3 < 3965; k3++) {
                    /*Vorsicht i darf nur bis 2934 laufen, sonst Überschreitung der Arrays*/
                    double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + 5000;
                    pakets.add(new Paket(timestamp / POWERS_OF_10[6], len3[k3], Paket.Protocol.UNIDENTIFIED));

                }
            }

            for (n = 0; n < 1; n++) {
                /*Jeder Schleifendurchlauf entspricht 10 sec skype-Video-shared screen*/
                for (k4 = 0; k4 < 627; k4++) {
                    /*Vorsicht i darf nur bis 2934 laufen, sonst Überschreitung der Arrays*/
                    double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + 5000;
                    pakets.add(new Paket(timestamp / POWERS_OF_10[6], len4[k4], Paket.Protocol.UNIDENTIFIED));
                }
            }
        }
        createVerkehrsprofil(pakets, "gaming");
    }

    public void lasttest() {
        List<Paket> pakets = new ArrayList<>();
        int i;
        int[] len = new int[1000];
        for (i = 0; i < len.length; i++) {
            len[i] = 1;
        }

        /*Senden von Daten*/
        int n;
        int k;

        for (n = 0; n < 1; n++) {
            /*Pro Schleifendurchlauf 1 Minute Youtube-Video*/
            for (i = 0; i < 100; i++) {
                /*Erste Sekunde besitzt 10 * 245 = 2450 Pakete zu ta1 = 408ys*/
                for (k = 0; k < 1000; k++) {
                    if (!pakets.isEmpty()) {
                        double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + 1;
                        pakets.add(new Paket(timestamp / POWERS_OF_10[6], len[k], Paket.Protocol.UNIDENTIFIED));
                    } else {
                        pakets.add(new Paket(0.0, len[k], Paket.Protocol.UNIDENTIFIED));
                    }
                }
            }
        }
        createVerkehrsprofil(pakets, "lasttest");
    }

    public void outlook_start_udp() {
        List<Paket> pakets = new ArrayList<>();
        int i;
        int j = 0;
        int x = 0;
        int y = 0;

        /*Array für Sendegröße Initialisieren */
        int[] len = new int[1500];

        for (i = 0; i < 1500; i = i + 5) {
            len[i] = 13;
        }
        for (i = 1; i < 1500; i = i + 5) {
            len[i] = 13;
        }
        for (i = 2; i < 1500; i = i + 5) {
            j++;
            len[i] = 14 + j;
            if (j > 183) {
                j = 0;
            }
        }
        for (i = 3; i < 1500; i = i + 5) {
            x++;
            len[i] = 196 + x;
            if (x > 70) {
                x = 0;
            }
        }
        for (i = 4; i < 1500; i = i + 5) {
            y++;
            len[i] = 276 + 4 * y;
            if (y > 1193) {
                y = 0;
            }
        }

        /*Senden von Daten*/
        int n;
        int k;

        for (n = 0; n < 1; n++) {
            /*Pro Schleifendurchlauf 1 Minute Youtube-Video*/

            for (i = 0; i < 750; i++) {
                /*Erste Sekunde besitzt 750 * 1 = 750 Pakete zu ta1 = 1346µs*/
                if (!pakets.isEmpty()) {
                    double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + 1346;
                    pakets.add(new Paket(timestamp / POWERS_OF_10[6], len[i], Paket.Protocol.UNIDENTIFIED));
                } else {
                    pakets.add(new Paket(0.0, len[i], Paket.Protocol.UNIDENTIFIED));
                }
            }

            for (i = 750; i < 1500; i++) {
                /*Erste Sekunde besitzt 750 * 1 = 750 Pakete zu ta1 = 408µs*/
                for (k = 0; k < 1; k++) {
                    double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + 3311;
                    pakets.add(new Paket(timestamp / POWERS_OF_10[6], len[i], Paket.Protocol.UNIDENTIFIED));
                    /*302 Pakete/s*/
                }
            }
        }
        createVerkehrsprofil(pakets, "outlook_start_udp");
    }

    public void print() {
        List<Paket> pakets = new ArrayList<>();
        int i;
        int[] len = new int[42];

        for (i = 0; i < 42; i++) {
            len[i] = 135;
        }

        int[] len2 = new int[18];

        for (i = 0; i < 18; i++) {
            len2[i] = 163;
        }

        int[] len3 = new int[54];

        for (i = 0; i < 54; i++) {
            len3[i] = 168;
        }

        int[] len4 = new int[34];

        for (i = 0; i < 34; i++) {
            len4[i] = 178;
        }

        int[] len5 = new int[18];

        for (i = 0; i < 18; i++) {
            len5[i] = 180;
        }

        /*Senden von Daten*/
        int n;
        int k;

        for (n = 0; n < 1; n++) {
            /*Pro Schleifendurchlauf 1 Minute*/
            for (i = 0; i < 42; i++) {
                if (!pakets.isEmpty()) {
                    double timestamp;
                    if (i % 10 == 0) {
                        timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + (500000 + (random.nextInt() % 26) * 100000);
                    } else {
                        timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6];
                    }
                    pakets.add(new Paket(timestamp / POWERS_OF_10[6], len[0], Paket.Protocol.UNIDENTIFIED));
                } else {
                    pakets.add(new Paket(0.0, len[0], Paket.Protocol.UNIDENTIFIED));
                }
            }

            for (i = 0; i < 18; i++) {
                double timestamp;
                if (i % 10 == 0) {
                    timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + (500000 + (random.nextInt() % 26) * 100000);
                } else {
                    timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6];
                }
                pakets.add(new Paket(timestamp / POWERS_OF_10[6], len2[0], Paket.Protocol.UNIDENTIFIED));
            }

            for (i = 0; i < 54; i++) {
                double timestamp;
                if (i % 10 == 0) {
                    timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + (500000 + (random.nextInt() % 26) * 100000);
                } else {
                    timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6];
                }
                pakets.add(new Paket(timestamp / POWERS_OF_10[6], len3[0], Paket.Protocol.UNIDENTIFIED));
            }

            for (i = 0; i < 34; i++) {
                double timestamp;
                if (i % 10 == 0) {
                    timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + (500000 + (random.nextInt() % 26) * 100000);
                } else {
                    timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6];
                }
                pakets.add(new Paket(timestamp / POWERS_OF_10[6], len4[0], Paket.Protocol.UNIDENTIFIED));
            }

            for (i = 0; i < 18; i++) {
                double timestamp;
                if (i % 10 == 0) {
                    timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + (500000 + (random.nextInt() % 26) * 100000);
                } else {
                    timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6];
                }
                pakets.add(new Paket(timestamp / POWERS_OF_10[6], len5[0], Paket.Protocol.UNIDENTIFIED));
            }
        }
        createVerkehrsprofil(pakets, "print");
    }

    public void rauschen() {
        List<Paket> pakets = new ArrayList<>();
        int Skalierung = 4;
        /*Arrays für Zwischenankunftszeiten Initialisieren */
        int i;
        int j = 0;
        /*Array für Sendegröße Initialisieren */

        int[] len = new int[2000];

        for (i = 0; i < 2000; i = i + 20) {
            len[i] = 18;
        }
        for (i = 1; i < 2000; i = i + 20) {
            len[i] = 18;
        }
        for (i = 2; i < 2000; i = i + 20) {
            len[i] = 18;
        }
        for (i = 3; i < 2000; i = i + 20) {
            len[i] = 18;
        }
        for (i = 4; i < 2000; i = i + 20) {
            len[i] = 18;
        }
        for (i = 5; i < 2000; i = i + 20) {
            len[i] = 18;
        }
        for (i = 6; i < 2000; i = i + 20) {
            len[i] = 18;
        }
        for (i = 7; i < 2000; i = i + 20) {
            len[i] = 22;
        }
        for (i = 8; i < 2000; i = i + 20) {
            len[i] = 22;
        }
        for (i = 9; i < 2000; i = i + 20) {
            len[i] = 22;
        }
        for (i = 10; i < 2000; i = i + 20) {
            len[i] = 22;
        }
        for (i = 11; i < 2000; i = i + 20) {
            len[i] = 1464;
        }
        for (i = 12; i < 2000; i = i + 20) {
            len[i] = 1468;
        }
        for (i = 13; i < 2000; i = i + 20) {
            len[i] = 1468;
        }
        for (i = 14; i < 2000; i = i + 20) {
            len[i] = 0;
        }
        for (i = 15; i < 2000; i = i + 20) {
            len[i] = 0;
        }
        for (i = 16; i < 2000; i = i + 20) {
            len[i] = 0;
        }
        for (i = 17; i < 2000; i = i + 20) {
            len[i] = 0;
        }
        for (i = 18; i < 2000; i = i + 20) {
            len[i] = 0;
        }
        for (i = 19; i < 2000; i = i + 20) {
            len[i] = 0;
        }

        int[] len2 = new int[3000];

        for (i = 0; i < 3000; i++) {
            j++;
            len2[i] = 22 + Math.round(8 / Skalierung) * j;
            if (j > 1472) {
                j = 0;
            }
        }

        int n;
        int k;

        for (n = 0; n < 1; n++) {
            /*Pro Schleifendurchlauf 1 Minute Youtube-Video*/
            for (i = 0; i < 14; i++) {
                /*ersten 14 Schleifen werden mit 14 * 30 * Skalierung = 420 Pakete * Skalierung gesendet zu ta1 = 100000µs*/
                for (k = 0; k < 30 * Skalierung; k++) {
                    /*pro Skalierung 30 Pakete */
                    if (!pakets.isEmpty()) {
                        double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + (100000 / Skalierung);
                        pakets.add(new Paket(timestamp / POWERS_OF_10[6], len[i], Paket.Protocol.UNIDENTIFIED));
                    } else {
                        pakets.add(new Paket(0.0, len[i], Paket.Protocol.UNIDENTIFIED));
                    }
                }
            }

            for (i = 0; i < 180 * Skalierung; i++) {
                /*180 Schleifen von 64 bis 1506 Byte hochgezählt werden mit 180* 1* Skalierung = 180 Pakete * Skalierung gesendet zu ta1 = 100000µs*/
                double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + (100000 / Skalierung);
                pakets.add(new Paket(timestamp / POWERS_OF_10[6], len2[i], Paket.Protocol.UNIDENTIFIED));
            }
        }
        createVerkehrsprofil(pakets, "rauschen");
    }

    public void remote_desktop_udp() {
        List<Paket> pakets = new ArrayList<>();
        int i;
        int j = 0;
        int x = 0;
        int y = 0;

        int[] len = new int[2262];
        for (i = 0; i < 2262; i++) {
            len[i] = 18;
        }

        int[] len2 = new int[2527];
        for (i = 0; i < 2527; i++) {
            len2[i] = 22;
        }

        int[] len3 = new int[326];
        for (i = 0; i < 326; i++) {
            len3[i] = 24;
        }

        int[] len4 = new int[730];
        for (i = 0; i < 730; i++) {
            j++;
            len4[i] = 21 + j;
            if (j > 33) {
                j = 0;
            }
        }

        int[] len5 = new int[326];
        for (i = 0; i < 326; i++) {
            len5[i] = 25;
        }

        int[] len6 = new int[320];
        for (i = 0; i < 320; i++) {
            len6[i] = 39;
        }

        int[] len7 = new int[333];
        for (i = 0; i < 333; i++) {
            len7[i] = 45;
        }

        int[] len8 = new int[318];
        for (i = 0; i < 318; i++) {
            len8[i] = 112;
        }

        int[] len9 = new int[321];
        for (i = 0; i < 321; i++) {
            len9[i] = 131;
        }

        int[] len10 = new int[320];
        for (i = 0; i < 320; i++) {
            len10[i] = 235;
        }

        int[] len11 = new int[326];
        for (i = 0; i < 326; i++) {
            len11[i] = 270;
        }

        int[] len12 = new int[591];
        for (i = 0; i < 591; i++) {
            x++;
            len12[i] = (int) (52 + Math.round(2.2 * x)); 		// 1289/591 = 2.2 ; 1391-102 = 1289
            if (x > 1289) {
                x = 0;
            }
        }

        int[] len13 = new int[321];
        for (i = 0; i < 321; i++) {
            len13[i] = 1343;
        }

        int[] len14 = new int[97];
        for (i = 0; i < 97; i++) {
            y++;
            len14[i] = (int) (1343 + Math.round(1.15 * y)); // 111/97 = 1.15; 1503-1392 = 111 mit 1503 = letztes Paket			
            if (y > 111) {
                y = 0;
            }
        }

        int n;
        int k;

        for (n = 0; n < 1; n++) {
            /*Pro Schleifendurchlauf 1 Minute*/
            for (i = 0; i < 754; i++) {
                /*ersten 754 Schleifen mit 67 Byte werden mit 754 * 1 = 754 Pakete gesendet zu ta1 = 3333µs*/
                if (!pakets.isEmpty()) {
                    double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + 3333;
                    pakets.add(new Paket(timestamp / POWERS_OF_10[6], len[i], Paket.Protocol.UNIDENTIFIED));
                } else {
                    pakets.add(new Paket(0.0, len[i], Paket.Protocol.UNIDENTIFIED));
                }
            }

            for (i = 0; i < 754; i++) {
                /*ersten 754 Schleifen mit 67 Byte werden mit 754 * 1 = 754 Pakete gesendet zu ta1 = 10000µs*/
                double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + 10000;
                pakets.add(new Paket(timestamp / POWERS_OF_10[6], len[i], Paket.Protocol.UNIDENTIFIED));
            }

            for (i = 0; i < 754; i++) {
                /*ersten 754 Schleifen mit 67 Byte werden mit 754 * 1 = 754 Pakete gesendet zu ta1 = 3333µs*/
                double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + 3333;
                pakets.add(new Paket(timestamp / POWERS_OF_10[6], len[i], Paket.Protocol.UNIDENTIFIED));
            }

            for (i = 0; i < 800; i++) {
                /*ersten 800 Schleifen mit 70 Byte werden mit 800 * 1 = 800 Pakete gesendet zu ta1 = 10000µs*/
                double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + 10000;
                pakets.add(new Paket(timestamp / POWERS_OF_10[6], len2[i], Paket.Protocol.UNIDENTIFIED));
            }

            for (i = 0; i < 1127; i++) {
                /*ersten 1127 Schleifen mit 70 Byte werden mit 1127 * 1 = 1127 Pakete gesendet zu ta1 = 3333µs*/
                double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + 3333;
                pakets.add(new Paket(timestamp / POWERS_OF_10[6], len2[i], Paket.Protocol.UNIDENTIFIED));
            }

            for (i = 0; i < 600; i++) {
                /*ersten 600 Schleifen mit 70 Byte werden mit 600 * 1 = 600 Pakete gesendet zu ta1 = 10000µs*/
                double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + 10000;
                pakets.add(new Paket(timestamp / POWERS_OF_10[6], len2[i], Paket.Protocol.UNIDENTIFIED));
            }

            for (i = 0; i < 326; i++) {
                /*ersten 326 Schleifen mit 72 Byte werden mit 326 * 1 = 326 Pakete gesendet zu ta1 = 3333µs*/
                double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + 3333;
                pakets.add(new Paket(timestamp / POWERS_OF_10[6], len3[i], Paket.Protocol.UNIDENTIFIED));
            }

            for (i = 0; i < 730; i++) {
                /*ersten 730 Schleifen mit 70 bis 101 Byte werden mit 730 * 1 = 730 Pakete gesendet zu ta1 = 10000µs*/
                double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + 10000;
                pakets.add(new Paket(timestamp / POWERS_OF_10[6], len4[i], Paket.Protocol.UNIDENTIFIED));
            }

            for (i = 0; i < 326; i++) {
                /*ersten 326 Schleifen mit 74 Byte werden mit 326 * 1 = 326 Pakete gesendet zu ta1 = 3333µs*/
                double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + 3333;
                pakets.add(new Paket(timestamp / POWERS_OF_10[6], len5[i], Paket.Protocol.UNIDENTIFIED));
            }

            for (i = 0; i < 320; i++) {
                /*ersten 320 Schleifen mit 88 Byte werden mit 320 * 1 = 320 Pakete gesendet zu ta1 = 10000µs*/
                double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + 10000;
                pakets.add(new Paket(timestamp / POWERS_OF_10[6], len6[i], Paket.Protocol.UNIDENTIFIED));
            }

            for (i = 0; i < 333; i++) {
                /*ersten 333 Schleifen mit 94 Byte werden mit 333 * 1 = 333 Pakete gesendet zu ta1 = 3333µs*/
                double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + 3333;
                pakets.add(new Paket(timestamp / POWERS_OF_10[6], len7[i], Paket.Protocol.UNIDENTIFIED));
            }

            for (i = 0; i < 318; i++) {
                /*ersten 318 Schleifen mit 161 Byte werden mit 318 * 1 = 318 Pakete gesendet zu ta1 = 10000µs*/
                double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + 10000;
                pakets.add(new Paket(timestamp / POWERS_OF_10[6], len8[i], Paket.Protocol.UNIDENTIFIED));
            }

            for (i = 0; i < 321; i++) {
                /*ersten 321 Schleifen mit 180 Byte werden mit 321 * 1 = 321 Pakete gesendet zu ta1 = 3333µs*/
                double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + 3333;
                pakets.add(new Paket(timestamp / POWERS_OF_10[6], len9[i], Paket.Protocol.UNIDENTIFIED));
            }

            for (i = 0; i < 320; i++) {
                /*ersten 320 Schleifen mit 284 Byte werden mit 320 * 1 = 320 Pakete gesendet zu ta1 = 10000µs*/
                double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + 10000;
                pakets.add(new Paket(timestamp / POWERS_OF_10[6], len10[i], Paket.Protocol.UNIDENTIFIED));
            }

            for (i = 0; i < 326; i++) {
                /*ersten 326 Schleifen mit 319 Byte werden mit 326 * 1 = 326 Pakete gesendet zu ta1 = 3333µs*/
                double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + 3333;
                pakets.add(new Paket(timestamp / POWERS_OF_10[6], len11[i], Paket.Protocol.UNIDENTIFIED));
            }

            for (i = 0; i < 591; i++) {
                /*ersten 591 Schleifen mit 102 bis 1391 Byte werden mit 591 * 1 = 591 Pakete gesendet zu ta1 = 10000µs*/
                double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + 10000;
                pakets.add(new Paket(timestamp / POWERS_OF_10[6], len12[i], Paket.Protocol.UNIDENTIFIED));
            }

            for (i = 0; i < 321; i++) {
                /*ersten 321 Schleifen mit 1392 Byte werden mit 321 * 1 = 321 Pakete gesendet zu ta1 = 50000µs, da bei kleinerer ta1 fragmentiert wird*/
                double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + 50000;
                pakets.add(new Paket(timestamp / POWERS_OF_10[6], len13[i], Paket.Protocol.UNIDENTIFIED));
            }

            for (i = 0; i < 97; i++) {
                /*ersten 97 Schleifen mit 1392 bis 1503 Byte werden mit 97 * 1 = 97 Pakete gesendet zu ta1 = 10000µs*/
                double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + 10000;
                pakets.add(new Paket(timestamp / POWERS_OF_10[6], len14[i], Paket.Protocol.UNIDENTIFIED));
            }
        }
        createVerkehrsprofil(pakets, "remote_desktop_udp");
    }

    public void skypesc() {
        List<Paket> pakets = new ArrayList<>();
        int[] len = new int[2936];
        int i;

        /*Anzahl Pakete*/
        double mittel = 1058;
        /*Erwartungswert*/
        double stdabw = 67;
        /*Standartabweichung*/
        int x;				//Momentanwert
        double Normierungsfaktor = 1848;
        /*für 10sec skype*/
        double wurzprod = Math.sqrt(2 * Math.PI);
        double Multiplikator = ((double) 1 / (stdabw * wurzprod)) * Normierungsfaktor;	//Vorkalkulation zur Reduzierung der Rechenlast

        i = 0;
        int k;
        int z = 0;

        for (x = 858; x <= 1258; x++) {
            i = i + z;

            double hoch, p;
            p = ((x - mittel) / stdabw);
            hoch = p * p;
            /*quadratfunktion für gauß*/

            double a = (-0.5 * hoch);
            /*Argument für exponentailfunktion für gauß*/

            z = (int) Math.round(Math.exp(a) * Multiplikator);
            /*Dichtefunktion von Gauß multipliziert mit Normierungsfaktor*/

            //Füllen des Längenarrays len mit den Datenlängen, die später versendet werden sollen
            for (k = 0; k < z; k++) {
                len[i + k] = x;
            }
        }

        /*Restliche Sendegrößen in das Längenarray eintragen*/
        for (k = 1835; k < 1855; k++) {
            len[k] = 60;
        }
        for (k = 1855; k < 1859; k++) {
            len[k] = 61;
        }
        for (k = 1859; k < 1866; k++) {
            len[k] = 62;
        }
        for (k = 1866; k < 1878; k++) {
            len[k] = 63;
        }
        for (k = 1878; k < 1897; k++) {
            len[k] = 64;
        }
        for (k = 1897; k < 1921; k++) {
            len[k] = 65;
        }
        for (k = 1921; k < 1958; k++) {
            len[k] = 66;
        }
        for (k = 1958; k < 2021; k++) {
            len[k] = 67;
        }
        for (k = 2021; k < 2262; k++) {
            len[k] = 68;
        }
        for (k = 2262; k < 2339; k++) {
            len[k] = 69;
        }
        for (k = 2339; k < 2408; k++) {
            len[k] = 70;
        }
        for (k = 2408; k < 2467; k++) {
            len[k] = 71;
        }
        for (k = 2467; k < 2520; k++) {
            len[k] = 72;
        }
        for (k = 2520; k < 2607; k++) {
            len[k] = 73;
        }
        for (k = 2520; k < 2662; k++) {
            len[k] = 74;
        }
        for (k = 2662; k < 2716; k++) {
            len[k] = 75;
        }
        for (k = 2716; k < 2763; k++) {
            len[k] = 76;
        }
        for (k = 2763; k < 2807; k++) {
            len[k] = 77;
        }
        for (k = 2807; k < 2853; k++) {
            len[k] = 78;
        }
        for (k = 2853; k < 2890; k++) {
            len[k] = 79;
        }
        for (k = 2890; k < 2916; k++) {
            len[k] = 80;
        }
        for (k = 2916; k <= 2935; k++) {
            len[k] = 81;
        }

        /*Array für Zwischenankunftszeit Initialisieren */
        int[] ta = new int[2935];
        int iT;
        /*Rückgabewert für Gauß bzw. Anzahl Pakete*/
        double mittelT = 3472;
        /*Erwartungswert*/
        double stdabwT = 174;
        /*Standartabweichung*/
        int xT;
        /*Wert für Zwischenankunftszeit bei xT*/
        double NormierungsfaktorT = 2960;
        /*für 10sec skype*/
        double wurzprodT = Math.sqrt(2 * Math.PI);
        /*Wurzelprodukt*/
        double MultiplikatorT = ((double) 1 / (stdabwT * wurzprodT)) * NormierungsfaktorT;	//Vorkalkulation zur Reduzierung der Rechenlast

        iT = 0;
        int kT;
        int zT = 0;

        //Gearbeitet wird innerhalb der Grenzen von 6 Sigma ausgehend vom Mittelwert
        for (xT = 2950; xT <= 3994; xT++) {
            iT = iT + zT;

            double hochT, pT;
            pT = ((xT - mittelT) / stdabwT);
            hochT = pT * pT;
            /*quadratfunktion für gauß*/

            double aT = (-0.5 * hochT);
            /*Argument für exponentailfunktion für gauß*/

            zT = (int) Math.round(Math.exp(aT) * MultiplikatorT);
            /*Dichtefunktion von Gauß multipliziert mit Normierungsfaktor*/ /*Ergebnis von fT Runden*/

            //Füllen des Arrays ta für die Zwischenankunftszeiten
            for (kT = 0; kT < zT; kT++) {
                ta[iT + kT] = xT;
            }
        }

        int n;
        int l;

        for (l = 0; l < 1; l++) {
            /*Jeder Schleifendurchlauf entspricht 1 min skype-Video-shared screen*/
            for (n = 0; n < 6; n++) {
                /*Jeder Schleifendurchlauf entspricht 10 sec skype-Video-shared screen*/

                for (i = 0; i < 2935; i++) {
                    /*Vorsicht i darf nur bis 2934 laufen, sonst Überschreitung der Arrays*/
                    if (!pakets.isEmpty()) {
                        double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + ta[i];
                        pakets.add(new Paket(timestamp / POWERS_OF_10[6], len[i], Paket.Protocol.UNIDENTIFIED));
                    } else {
                        pakets.add(new Paket(0.0, len[i], Paket.Protocol.UNIDENTIFIED));
                    }
                }
            }
        }
        createVerkehrsprofil(pakets, "skypesc");
    }

    public void voip_multi() {
        List<Paket> pakets = new ArrayList<>();
        int min = 200;
        int max = 220;
        int o = min - 42;
        int i;
        int k = (max - min + 1);
        int[] len = new int[k];

        for (i = 0; i < k; i++) {
            len[i] = (o + i);
        }

        int p = Math.round(60 * 1000000 / (k * 200));
        int n;
        int m;

        for (m = 0; m < 1; m++) {
            /*Hier als Übergabewert die Übertragungszeit von VoIP in Minuten */
            for (i = 0; i < p; i++) {

                for (n = 0; n < k; n++) {
                    if (!pakets.isEmpty()) {
                        double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + 200;
                        pakets.add(new Paket(timestamp / POWERS_OF_10[6], len[n], Paket.Protocol.UNIDENTIFIED));
                    } else {
                        pakets.add(new Paket(0.0, len[n], Paket.Protocol.UNIDENTIFIED));
                    }
                }
            }
        }
        createVerkehrsprofil(pakets, "voip_multi");
    }

    public void voip_profil() {
        List<Paket> pakets = new ArrayList<>();
        int min = 200;
        int max = 220;
        int o = min - 42;
        int i;
        int k = (max - min + 1);
        int[] len = new int[k];

        for (i = 0; i < k; i++) {
            len[i] = (o + i);
        }

        int ta = 10000;

        int p = Math.round(60 * 1000000 / (k * ta));
        int n;
        int m;

        for (m = 0; m < 1; m++) {
            /*Hier als Übergabewert die Übertragungszeit von VoIP in Minuten */
            for (i = 0; i < p; i++) {

                for (n = 0; n < k; n++) {
                    if (!pakets.isEmpty()) {
                        double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + ta;
                        pakets.add(new Paket(timestamp / POWERS_OF_10[6], len[n], Paket.Protocol.UNIDENTIFIED));
                    } else {
                        pakets.add(new Paket(0.0, len[n], Paket.Protocol.UNIDENTIFIED));
                    }
                }
            }
        }
        createVerkehrsprofil(pakets, "voip_profil");
    }

    public void web_profil() {
        List<Paket> pakets = new ArrayList<>();
        int[] ta = new int[1046];
        int i;

        for (i = 0; i < 34; i++) {
            ta[i] = 294;
        }
        for (i = 34; i < 35; i++) {
            ta[i] = 30000;
        }
        for (i = 35; i < 72; i++) {
            ta[i] = 270;
        }
        for (i = 72; i < 80; i++) {
            ta[i] = 1250;
        }
        for (i = 80; i < 81; i++) {
            ta[i] = 10000;
        }
        for (i = 81; i < 135; i++) {
            ta[i] = 185;
        }
        for (i = 135; i < 160; i++) {
            ta[i] = 400;
        }
        for (i = 160; i < 161; i++) {
            ta[i] = 10000;
        }
        for (i = 161; i < 194; i++) {
            ta[i] = 303;
        }
        for (i = 194; i < 205; i++) {
            ta[i] = 909;
        }
        for (i = 205; i < 268; i++) {
            ta[i] = 159;
        }
        for (i = 268; i < 316; i++) {
            ta[i] = 208;
        }
        for (i = 316; i < 336; i++) {
            ta[i] = 500;
        }
        for (i = 336; i < 358; i++) {
            ta[i] = 455;
        }
        for (i = 358; i < 362; i++) {
            ta[i] = 2500;
        }
        for (i = 362; i < 363; i++) {
            ta[i] = 20000;
        }
        for (i = 363; i < 382; i++) {
            ta[i] = 526;
        }
        for (i = 382; i < 432; i++) {
            ta[i] = 200;
        }
        for (i = 432; i < 467; i++) {
            ta[i] = 285;
        }
        for (i = 467; i < 468; i++) {
            ta[i] = 10000;
        }
        for (i = 468; i < 515; i++) {
            ta[i] = 213;
        }
        for (i = 515; i < 575; i++) {
            ta[i] = 167;
        }
        for (i = 575; i < 627; i++) {
            ta[i] = 192;
        }
        for (i = 627; i < 636; i++) {
            ta[i] = 1111;
        }
        for (i = 636; i < 654; i++) {
            ta[i] = 556;
        }
        for (i = 654; i < 673; i++) {
            ta[i] = 526;
        }
        for (i = 673; i < 699; i++) {
            ta[i] = 385;
        }
        for (i = 699; i < 722; i++) {
            ta[i] = 435;
        }
        for (i = 722; i < 754; i++) {
            ta[i] = 313;
        }
        for (i = 754; i < 765; i++) {
            ta[i] = 909;
        }
        for (i = 765; i < 799; i++) {
            ta[i] = 294;
        }
        for (i = 799; i < 817; i++) {
            ta[i] = 556;
        }
        for (i = 817; i < 830; i++) {
            ta[i] = 769;
        }
        for (i = 830; i < 848; i++) {
            ta[i] = 556;
        }
        for (i = 848; i < 873; i++) {
            ta[i] = 400;
        }
        for (i = 873; i < 908; i++) {
            ta[i] = 286;
        }
        for (i = 908; i < 926; i++) {
            ta[i] = 556;
        }
        for (i = 926; i < 944; i++) {
            ta[i] = 556;
        }
        for (i = 944; i < 963; i++) {
            ta[i] = 526;
        }
        for (i = 963; i < 1004; i++) {
            ta[i] = 244;
        }
        for (i = 1004; i < 1023; i++) {
            ta[i] = 526;
        }
        for (i = 1023; i < 1037; i++) {
            ta[i] = 714;
        }
        for (i = 1037; i <= 1045; i++) {
            ta[i] = 909;
        }

        /*Array für Sendegröße Initialisieren */
        int[] len = new int[1046];
        for (i = 0; i < 1046; i = i + 4) {
            len[i] = 20;
        }
        for (i = 1; i < 1046; i = i + 4) {
            len[i] = 1464;
        }
        for (i = 2; i < 1046; i = i + 4) {
            len[i] = 1464;
        }
        for (i = 3; i <= 1045; i = i + 4) {
            len[i] = 1464;
        }

        int n;
        for (n = 0; n < 1; n++) {
            for (i = 0; i < 1046; i++) {
                if (!pakets.isEmpty()) {
                    double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + ta[i];
                    pakets.add(new Paket(timestamp / POWERS_OF_10[6], len[i], Paket.Protocol.UNIDENTIFIED));
                } else {
                    pakets.add(new Paket(0.0, len[i], Paket.Protocol.UNIDENTIFIED));
                }
            }
        }
        createVerkehrsprofil(pakets, "web_profil");
    }

    public void youtube2160p() {
        List<Paket> pakets = new ArrayList<>();
        int i;

        int ta1 = 408;

        int[] ta2 = new int[16];
        for (i = 0; i < 16; i++) {
            ta2[i] = 461 + i;
        }

        int ta3 = 3125;

        int[] len = new int[330];

        for (i = 0; i < 330; i = i + 3) {
            len[i] = 41;
        }
        for (i = 1; i < 330; i = i + 3) {
            len[i] = 1350;
        }
        for (i = 2; i < 330; i = i + 3) {
            len[i] = 1350;
        }

        int n;
        int k;
        int x;
        int l;

        for (n = 0; n < 1; n++) {
            /*Übergabevariable Minuten: Pro Schleifendurchlauf 1 Minute Youtube-Video*/
            for (i = 0; i < 10; i++) {
                /*Paketanzahl in 10 * 1/10 Sekunden entspricht Paketrate für erste Sekunde*/
                for (k = 0; k < 245; k++) {
                    /*Paketübertragung für 1/10 Sekunden*/
                    if (!pakets.isEmpty()) {
                        double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + ta1;
                        pakets.add(new Paket(timestamp / POWERS_OF_10[6], len[k], Paket.Protocol.UNIDENTIFIED));
                    } else {
                        pakets.add(new Paket(0.0, len[k], Paket.Protocol.UNIDENTIFIED));
                    }
                }
            }

            for (l = 0; l < 637; l++) {
                /*Mittelteil hat 637 * 15 *16 = 152880 Pakete in 73 Sekunden zu verschiedenen ta[]*/
                for (x = 0; x < 15; x++) {
                    /*15 Schleifendurchläufe ergeben 15 * 16 = 240 Pakete*/
                    k = x * 16;
                    /*Hiermit fängt len[] in "send" immer 16 Werte weiter in Array an zu zählen*/
                    for (i = 0; i < 16; i++) {
                        /*16 schleifendurchläufe ergeben 16 Pakete zu verschiedenen ta[]*/
                        double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + ta2[i];
                        pakets.add(new Paket(timestamp / POWERS_OF_10[6], len[k + i], Paket.Protocol.UNIDENTIFIED));
                    }
                }
            }

            for (i = 0; i < 320; i++) {
                /*Dritte Sekunde mit 320 Pakete zu ta3 = 3125ys */
                double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + ta3;
                pakets.add(new Paket(timestamp / POWERS_OF_10[6], len[i], Paket.Protocol.UNIDENTIFIED));
                /*Korrekturfaktor von 92ys wegen Dauer der Sleep-Funktion und Schleifendurchlaufzeit*/
            }
        }
        createVerkehrsprofil(pakets, "youtube2160p");
    }

    public void youtube720p() {
        List<Paket> pakets = new ArrayList<>();
        int[] len = new int[250];
        int i;

        int ta1 = 408;

        int[] ta2 = new int[27];
        for (i = 0; i < 27; i++) {
            ta2[i] = 469 + i;
        }

        int ta3 = 595;

        for (i = 0; i < 250; i = i + 4) {
            len[i] = 41;
        }
        for (i = 1; i < 250; i = i + 4) {
            len[i] = 1350;
        }
        for (i = 2; i < 250; i = i + 4) {
            len[i] = 1350;
        }
        for (i = 3; i < 250; i = i + 4) {
            len[i] = 1350;
        }

        int n;
        int k;
        int x;
        int l;

        for (n = 0; n < 1; n++) {
            /*Pro Schleifendurchlauf 1 Minute Youtube-Video*/
            for (i = 0; i < 10; i++) {
                /*Erste Sekunde besitzt 10 * 245 = 2450 Pakete zu ta1 = 408ys*/
                for (k = 0; k < 245; k++) {
                    if (!pakets.isEmpty()) {
                        double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + ta1;
                        pakets.add(new Paket(timestamp / POWERS_OF_10[6], len[k], Paket.Protocol.UNIDENTIFIED));
                    } else {
                        pakets.add(new Paket(0.0, len[k], Paket.Protocol.UNIDENTIFIED));
                    }
                }
            }
            for (l = 0; l < 43; l++) {
                /*Mittelteil hat 43 * 9 * 27 = 10449 Pakete in 5 Sekunden zu verschiedenen ta[]*/
                for (x = 0; x < 9; x++) {
                    /*15 Schleifendurchläufe ergeben 9 * 27 = 243 Pakete*/
                    k = x * 27;
                    /*Hiermit fängt len[] in "send" immer 27 Werte weiter in Array an zu zählen*/
                    for (i = 0; i < 27; i++) {
                        /*27 schleifendurchläufe ergeben 27 Pakete zu verschiedenen ta[]*/
                        double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + ta2[i];
                        pakets.add(new Paket(timestamp / POWERS_OF_10[6], len[k + i], Paket.Protocol.UNIDENTIFIED));
                    }
                }
            }
            for (i = 0; i < 7; i++) {
                /* Siebte Sekunde besitzt 7 * 240 = 1680 Pakete zu ta = 595ys */
                for (k = 0; k < 240; k++) {
                    double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + ta3;
                    pakets.add(new Paket(timestamp / POWERS_OF_10[6], len[k], Paket.Protocol.UNIDENTIFIED));
                }
            }
        }
        createVerkehrsprofil(pakets, "youtube720p");
    }

    public void youtube240p() {
        List<Paket> pakets = new ArrayList<>();

        int[] len = new int[340];
//        int[] helper = {41, 1350, 1350, 1350, 1350};
//        
//        for (int i = 0; i < len.length; i++) {
//            len[i] = helper[i%helper.length-1];
//        }

        for (int i = 0; i < len.length; i += 5) {
            len[i] = 41;
        }
        for (int i = 1; i < 340; i += 5) {
            len[i] = 1350;
        }
        for (int i = 2; i < 340; i += 5) {
            len[i] = 1350;
        }
        for (int i = 3; i < 340; i += 5) {
            len[i] = 1350;
        }
        for (int i = 4; i < 340; i += 5) {
            len[i] = 1350;
        }

        int ta1 = 3030;
        int ta2 = 428;
        int ta3 = 1389;

        int i;
        int k;

        for (int n = 0; n < 1; n++) {
            /* Pro Schleifendurchlauf 1 Minute Youtube-Video*/
            pakets.add(new Paket(0.0, len[0], Paket.Protocol.UNIDENTIFIED));
            for (i = 1; i < 330; i++) {
                /*Erste Sekunde: es werden 330 Pakete mit ta2 = 3030ys abgeschickt*/
                double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + ta1;
                pakets.add(new Paket(timestamp / POWERS_OF_10[6], len[i], Paket.Protocol.UNIDENTIFIED));
            }

            for (i = 0; i < 7; i++) {
                /*Zweite Sekunde: es werden 7 * 334 = 2338 Pakete mit ta2 = 428ys abgeschickt*/
                for (k = 0; k < 334; k++) {
                    double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + ta2;
                    pakets.add(new Paket(timestamp / POWERS_OF_10[6], len[k], Paket.Protocol.UNIDENTIFIED));
                }
            }
//
            for (i = 0; i < 3; i++) {
                /*Dritte Sekunde: es werden 3 * 240 = 720 Pakete mit ta3 = 1389ys abgeschickt*/
                for (k = 0; k < 240; k++) {
                    double timestamp = pakets.get(pakets.size() - 1).getTimestamp() * POWERS_OF_10[6] + ta3;
                    pakets.add(new Paket(timestamp / POWERS_OF_10[6], len[k], Paket.Protocol.UNIDENTIFIED));
                }
            }
        }
        createVerkehrsprofil(pakets, "youtube240p");
    }

    public void createVerkehrsprofil(List<Paket> pakets, String name) {
        List<Integer> ppI = sumUpTimeStamps(pakets);
        Verkehrsprofil profil = new Verkehrsprofil(name, ppI);
        profile.add(profil);
    }

    private List<Integer> sumUpTimeStamps(List<Paket> pakets) {
        int paketCounter = 0;
        int timeCounter = 1;
        List<Integer> summedUpPakets = new ArrayList<>();

        for (int i = 0; i < pakets.size(); i++) {
            Double var = pakets.get(i).getTimestamp() * 100.0;
            Integer intVar = var.intValue();
            if (intVar < timeCounter) {
                paketCounter++;
            } else {
                summedUpPakets.add(paketCounter);
                paketCounter = 0;
                for (int j = 0; j < intVar - timeCounter; j++) {
                    summedUpPakets.add(paketCounter);
                }
                timeCounter = intVar + 1;
                paketCounter++;
            }
        }
        summedUpPakets.add(paketCounter); //add pakets of last timestep
        return summedUpPakets;
    }

    public boolean writeProfiles() {
        Boolean result;
        String json = gson.toJson(profile);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("verkehrsprofile.json"));
            writer.write(json);
            writer.close();
            result = true;

        } catch (IOException ex) {
            result = false;
        }
        return result;
    }

}
