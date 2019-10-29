import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.io.Serializable;
import javax.swing.JLabel;
import java.lang.String;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gregbug
 */
public class constrainedProperty
        extends JLabel
        implements Serializable, VetoableChangeListener {

    private static final long serialVersionUID = 1L;
    private VetoableChangeSupport vetos;

// Constructor
    public constrainedProperty() {
        vetos = new VetoableChangeSupport(this);
        addVetoableChangeListener(this);
    }

// methods to maintain the property change listener list
    public void addVetoableChangeListener(VetoableChangeListener l) {
        vetos.addVetoableChangeListener(l);
    }

    public void removeVetoableChangeListener(VetoableChangeListener l) {
        vetos.removeVetoableChangeListener(l);
    }

// Method to calculate the Celsius temperature to farheneit 
    private String celsusiusToFarheneit(String t) {
        if (t == null) {
            return "Empty";
        }
        if (t.equals("")) {
            return "Empty";
        }

        int celsius = Integer.parseInt(t);
        float farheneit = celsius * 9 / 5 + 32;

        return String.valueOf(farheneit);
    }

    /**
     * Method to override the setText into the cel_to_farh frame of java beans
     * @param newValue
     */
    @Override
    public void setText(String newValue) {
        /**
         * If anyone objects, we don't catch the exception but just let if pass
         * on to our caller
         */
        if (vetos == null) {
            super.setText(celsusiusToFarheneit(newValue));
        }
        
        String oldValue = getText();
        try {
            vetos.fireVetoableChange("Value in Celsius", oldValue, newValue);
        } catch (PropertyVetoException e) {
            Logger.getLogger(constrainedProperty.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
        try {
            String Celsius = (String) evt.getNewValue();
            float farenheit = Float.parseFloat(celsusiusToFarheneit(Celsius));
            if (farenheit < 0) {
                throw new PropertyVetoException("The value must be over absolute 0", evt);
            }
        } 
        catch (NumberFormatException e) {
            throw new PropertyVetoException("The value is not an integer", evt);
        }
    }

    public static void main(String[] args) {
        tempLabel label = new tempLabel();
        String[] random_int = {"100", "0", "93", "32"};

        for (String value : random_int) {
            label.setText(value);
            System.out.println("Celsius" + value + "==>" + label.getText());
        }
    }

}
