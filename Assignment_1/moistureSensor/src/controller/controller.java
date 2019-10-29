package controller;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * @author gregbug
 */
public class controller {

    /*
    Declaration variable
     */
    private int localHumidity;
    private boolean on = false;

    /*
    Getter Method
     */
    public boolean isOn() {
        return on;
    }
    
    public void setOn(boolean newOn) {
        this.on = newOn; //To change body of generated methods, choose Tools | Templates.
    }

    public int getLocalHumidity() {
        return localHumidity;
    }

    /*
     Instantiate a PropertyChangeSupport object
     */
    private PropertyChangeSupport change
            = new PropertyChangeSupport(this);

    /*
     Implement methods to maintain the property change listener list, respectively:
     - addPropertyChange
     - removePropertyChange
     */
    public void addPropertyChangeListener(PropertyChangeListener l) {
        change.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        change.removePropertyChangeListener(l);
    }

    /*
      Check continuosly if the 'localHumidity' range works properly in the range
      required. Each time which property changes, we invoke the firePropertyChange
      to advertise each registered listener of property change, in this case 'on' 
      value
     */
    public void setLocalHumidity(int localHumidity) {
        this.localHumidity = localHumidity;
        if (localHumidity < 30) {
            on = true;
            change.firePropertyChange("on", false, true);
        } else if (localHumidity >= 90) {
            on = false;
        }
        change.firePropertyChange("on", true, false);
    }

}
