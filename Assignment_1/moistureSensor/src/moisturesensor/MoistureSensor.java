package moisturesensor;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Timer;
import java.util.TimerTask;
/**
 *
 * @author gregbug
 */
public class MoistureSensor {

    /*
    Variable declaration
     */
    private int currentHumidity;
    private boolean decreasing = true;

    /*
    Setter Method
    IMPORTANT NOTE: Here i'm going to mdify the property's setter method to 
    fire a property change event when the property 'decreasing' is changed
     */
    public void setDecreasing(boolean decreasing) {
        boolean tmp = this.decreasing;
        this.decreasing = decreasing;
        change.firePropertyChange("decreasing", tmp, decreasing);
    }

    /*
    Getter Methods
     */
    public boolean isDecreasing() {
        return decreasing;
    }

    public int getCurrentHumidity() {
        return currentHumidity;
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
      Method start() which is responsible for:
      - reads current currentHumidity and update value in the 'currentHumidity' var
      - increasing or decreasing about the value of boolean variable 'decrease'
     */
    public void start() {
        /*
        - Generator of random numbers to simulate behaviour of sensor
        - Use of TimerTask to call each 'n' millisecond the function 'start()'
        */
        currentHumidity = (int) (Math.random() * 101);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                int prevHumidity = currentHumidity;
                int delta;
                delta = (int) (Math.random() * 5) + 1;
                if (decreasing) {
                    currentHumidity -= delta;
                    if (currentHumidity <= 0) {
                        currentHumidity = 0;
                        setDecreasing(false);
                    }
                } else {
                    currentHumidity += delta;
                    if (currentHumidity >= 100) {
                        currentHumidity = 100;
                        setDecreasing(true);
                    }
                }
                /*
                Call of 'firePropertyChange' method to fire a property change 
                event when the property is changed. 
                */
                change.firePropertyChange("humidity", prevHumidity, currentHumidity);
            }
        }, 0, 1000);
    }

    // Main function
    public static void main(String[] args) {
        new MoistureSensor().start();
    }
}