/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Admin
 */
public class Paket {
    private Double timestamp;
    private Long timestamp_absolute;
    private int paketlength;
    private Protocol protocol;
    
    public enum Protocol {
        UDP,TCP,OTHER,TOTAL,UNIDENTIFIED
    }
    
    public Paket(Double timestamp, int paketlength, Protocol protocol) {
        this.timestamp = timestamp;
        this.paketlength = paketlength;
        this.protocol = protocol;
    }
    
    public Paket(Double timestamp, int paketlength, Protocol protocol, Long timestamp_absolute) {
        this.timestamp = timestamp;
        this.paketlength = paketlength;
        this.protocol = protocol;
        this.timestamp_absolute = timestamp_absolute;
    }

    /**
     * @return the timestamp
     */
    public Double getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Double timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the paketlength
     */
    public int getPaketlength() {
        return paketlength;
    }

    /**
     * @param paketlength the paketlength to set
     */
    public void setPaketlength(int paketlength) {
        this.paketlength = paketlength;
    }

    /**
     * @return the protocol
     */
    public Protocol getProtocol() {
        return protocol;
    }

    /**
     * @param protocol the protocol to set
     */
    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public Long getTimestamp_absolute() {
        return timestamp_absolute;
    }
    
    
    
}
