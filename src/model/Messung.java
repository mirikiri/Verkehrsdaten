/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import HurstBerechnung.HurstBerechung;
import model.Paket.Protocol;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class Messung {

    private final String name;
    private final String path;
    private final List<Paket> pakets;
    private final List<Zeitintervall> zeitintervalle;
    private final HurstBerechnung.HurstBerechung schaetzer = new HurstBerechung();
    private final PaketLengthHistogram paketlengthHisto_udp;
    private final PaketLengthHistogram paketlengthHisto_tcp;
    private final PaketLengthHistogram paketlengthHisto_other;
    private final PaketLengthHistogram paketlengthHisto_total;
    private final CDF cdf_udp;
    private final CDF cdf_tcp;
    private final CDF cdf_other;
    private final CDF cdf_total;

    public Messung(String path, List<Paket> pakets) {

        this.path = path;
        name = new File(path).getName();
        this.pakets = pakets;
        zeitintervalle = new ArrayList<>();

        List<Integer> firstInterval = sumUpTimeStamps(pakets);

        zeitintervalle.add(new Zeitintervall("0.01", firstInterval, 1, Boolean.FALSE));
        zeitintervalle.add(new Zeitintervall("0.1", zeitintervalle.get(zeitintervalle.size() - 1).getPaketsPerInterval(), 10, Boolean.TRUE));
        zeitintervalle.add(new Zeitintervall("1", zeitintervalle.get(zeitintervalle.size() - 1).getPaketsPerInterval(), 10, Boolean.TRUE));
        zeitintervalle.add(new Zeitintervall("10", zeitintervalle.get(zeitintervalle.size() - 1).getPaketsPerInterval(), 10, Boolean.TRUE));
        zeitintervalle.add(new Zeitintervall("60", zeitintervalle.get(zeitintervalle.size() - 1).getPaketsPerInterval(), 6, Boolean.TRUE));
        paketlengthHisto_udp = new PaketLengthHistogram(pakets, Protocol.UDP);
        paketlengthHisto_tcp = new PaketLengthHistogram(pakets, Protocol.TCP);
        paketlengthHisto_other = new PaketLengthHistogram(pakets, Protocol.OTHER);
        paketlengthHisto_total = new PaketLengthHistogram(pakets, Protocol.TOTAL);
        cdf_udp = new CDF(pakets, Protocol.UDP);
        cdf_tcp = new CDF(pakets, Protocol.TCP);
        cdf_other = new CDF(pakets, Protocol.OTHER);
        cdf_total = new CDF(pakets, Protocol.TOTAL);

        schaetzer.berechneHurst(zeitintervalle.get(0));
        schaetzer.berechneHurst(zeitintervalle.get(1));
        schaetzer.berechneHurst(zeitintervalle.get(2));
        schaetzer.berechneHurst(zeitintervalle.get(3));
        schaetzer.berechneHurst(zeitintervalle.get(4));
    }

    //sums up pakets according to first timeInterval being 0.01 seconds
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

    /**
     * @return the _name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the _path
     */
    public String getPath() {
        return path;
    }

    public List<Paket> getPakets() {
        return pakets;
    }
    
    

    /**
     * @return the zeitintervalle
     */
    public List<Zeitintervall> getZeitintervalle() {
        return zeitintervalle;
    }
    
        /**
     * @return the paketlengthHisto_udp
     */
    public PaketLengthHistogram getPaketlengthHisto_udp() {
        return paketlengthHisto_udp;
    }

    /**
     * @return the paketlengthHisto_tcp
     */
    public PaketLengthHistogram getPaketlengthHisto_tcp() {
        return paketlengthHisto_tcp;
    }
    
    /**
     * @return the paketlengthHisto_other
     */
    public PaketLengthHistogram getPaketlengthHisto_other() {
        return paketlengthHisto_other;
    }

    /**
     * @return the paketlengthHisto_total
     */
    public PaketLengthHistogram getPaketlengthHisto_total() {
        return paketlengthHisto_total;
    }

    /**
     * @return the cdf_udp
     */
    public CDF getCdf_udp() {
        return cdf_udp;
    }

    /**
     * @return the cdf_tcp
     */
    public CDF getCdf_tcp() {
        return cdf_tcp;
    }

    public CDF getCdf_other() {
        return cdf_other;
    }
    
    

    /**
     * @return the cdf_total
     */
    public CDF getCdf_total() {
        return cdf_total;
    }

    /**
     * @return the schaetzer
     */
    public HurstBerechnung.HurstBerechung getSchaetzer() {
        return schaetzer;
    }

    //Getter Functions für Statistik Allgemein Daten
    public Double getDuration() {
        return Math.round(pakets.get(pakets.size() - 1).getTimestamp() * 1000d) / 1000d;
    }

    public int getNumberOfPakets() {
        return pakets.size();
    }

    public int getTypeTotalPakets(Protocol protocol) {
        int counter = 0;
        for (Paket paket : pakets) {
            if (paket.getProtocol() == protocol) {
                counter++;
            }
        }
        return counter;
    }

    //Getter Functions für Statistik Paketlänge Daten
    public PaketLength getMinPaketLength(Protocol protocol) {
        List<PaketLength> lst = null;
        lst = switchProtocol(protocol);
        PaketLength pl = null;
        for (PaketLength paketLength : lst) {
            if (paketLength.getCount() > 0) {
                pl = paketLength;
                break;
            }
        }
        if (pl == null) {
            pl = new PaketLength(-1);
        }
        return pl;
    }

    public PaketLength getMaxPaketLength(Protocol protocol) {
        List<PaketLength> lst = null;
        lst = switchProtocol(protocol);
        PaketLength pl = null;
        for (int i = lst.size()-1; i >= 0; i--) {
            if (lst.get(i).getCount() > 0) {
                pl = lst.get(i);
                break;
            }
        }
        if (pl == null) {
            pl = new PaketLength(-1);
        }
        return pl;
    }
    
    public double getMeanPaketLength(Protocol protocol) {
        List<PaketLength> lst = null;
        lst = switchProtocol(protocol);
        int summe = 0;
        for (int i = 0; i < lst.size(); i++) {
            summe += lst.get(i).getCount() * lst.get(i).getLength();
        }
        summe -= lst.get(1561).getCount() * lst.get(1561).getLength(); //an stelle 1561 liegen pakete >1560 Byte gesammelt
        if (protocol == Protocol.TOTAL) {
            return Math.round((double)summe / getNumberOfPakets()*100d)/100d;
        } else {
            return Math.round((double)summe / getTypeTotalPakets(protocol)*100d)/100d;
        }     
    }
    
    public PaketLength getMostUsedPaket(Protocol protocol) {
        List<PaketLength> lst = null;
        lst = switchProtocol(protocol);
        PaketLength mostUsed = new PaketLength(-1);
        for (PaketLength paketLength : lst) {
            if (paketLength.getCount() > mostUsed.getCount()) {
                if (paketLength.getLength() != 1561) {
                    mostUsed.setLength(paketLength.getLength());
                    mostUsed.setCount(paketLength.getCount());
                }
            }
        }
        return mostUsed;
    }
    
    public int getNumberPaketsBigger1560Byte(Protocol protocol) {
        List<PaketLength> lst = null;
        lst = switchProtocol(protocol);
        return lst.get(1561).getCount();
    }

    public List<PaketLength> switchProtocol(Protocol protocol) {
        List<PaketLength> lst = null;
        switch (protocol) {
            case UDP:
                lst = paketlengthHisto_udp.getPaketlengths();
                break;
            case TCP:
                lst = paketlengthHisto_tcp.getPaketlengths();
                break;
            case OTHER:
                lst = paketlengthHisto_other.getPaketlengths();
                break;
            case TOTAL:
                lst = paketlengthHisto_total.getPaketlengths();
                break;
        }
        return lst;
    }

    //Getter Functions für Statistik Byte Daten
    public int getTotalBytes(Protocol protocol) {
        int totalBytes = 0;
        if (protocol == Protocol.TOTAL) {
            for (Paket paket : pakets) {
                totalBytes += paket.getPaketlength();
            }
        } else {
            for (Paket paket : pakets) {
                if (paket.getProtocol() == protocol) {
                    totalBytes += paket.getPaketlength();
                }
            }
        }
        return totalBytes;
    }

    public double getAverageKBytePerSecond(int totalBytes) {
        return Math.round(((double) totalBytes / getDuration() / 1000) * 100d) / 100d;
    }

    public double getAverageKBitPerSecond(int totalBytes) {
        return Math.round(((double) (totalBytes * 8) / getDuration() / 1000) * 100d) / 100d;
    }

}
