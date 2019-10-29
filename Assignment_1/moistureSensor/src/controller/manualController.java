package controller;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;

/**
 *
 * @author gregbug
 */
public final class manualController extends controller implements VetoableChangeListener {
    
    private final VetoableChangeSupport vetos;
    
    public manualController() {
        vetos = new VetoableChangeSupport(this);
        addPropertyChangeListener(this);
    }
    
    public void addPropertyChangeListener(VetoableChangeListener l) {
        vetos.addVetoableChangeListener(l);
    }
    
    public void removePropertyChangeListener(VetoableChangeListener l) {
        vetos.removeVetoableChangeListener(l);
    }
    
    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
       if (isOn() && getLocalHumidity() <= 50) {
            throw new PropertyVetoException("Attention!!! Humidity seems to be too low to stop irrigation.", evt);
        } else if (!isOn() && getLocalHumidity() >= 60) {
            throw new PropertyVetoException("Attention!!! Humidity seems to be too high to start irrigation.", evt);
        }
    }
    
    @Override
    public void setOn(boolean newOn) {
        if (newOn == isOn()) {
            return;
        }
        try {
            vetos.fireVetoableChange("on", !newOn, newOn);
            super.setOn(newOn);
        } catch (PropertyVetoException e) {}
    }
    
    
    
}
