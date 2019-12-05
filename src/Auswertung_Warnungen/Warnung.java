/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Auswertung_Warnungen;

import model.Messung;

/**
 *
 * @author Florian
 */
public abstract class Warnung {
    protected String titel = "nicht_Initialisiert";
    protected String triggerText = "nicht_Initialisiert";
    protected String beschreibung = "nicht_Initialisiert";
    protected Boolean triggered = Boolean.FALSE;
    protected Boolean expanded = Boolean.TRUE;

    public Warnung(String titel, String beschreibung) {
        this.titel = titel;
        this.beschreibung = beschreibung;
    }
    
    public abstract void check_Trigger(Messung messung);
    
    
    public String getTitel() {
        return titel;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public String getTriggerText() {
        return triggerText;
    }

    public Boolean getTriggered() {
        return triggered;
    }

    public Boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }
    
    
    
}
