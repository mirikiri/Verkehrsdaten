/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Arrays;

/**
 *
 * @author Admin
 */
public class PaketToSend {
    int length;
    int waitTime;
    byte content[];
    
    public PaketToSend(int length) {
        this.length = length;
        content = new byte[length];
        Arrays.fill(content, (byte) 1);
    }
    
    public PaketToSend(int length, int waitTime) {
        this.length = length;
        this.waitTime = waitTime;
        content = new byte[length];
        Arrays.fill(content, (byte) 1);
    }

    public int getLength() {
        return length;
    }
    
    public byte[] getContent() {
        return content;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }
    
}
