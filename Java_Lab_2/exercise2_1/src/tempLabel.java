import java.io.Serializable;
import javax.swing.JLabel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gregbug
 */

// Class to define a new object of type tempLabel 
public class tempLabel extends JLabel implements Serializable {
    
// Method to calculate the Celsius temperature to farheneit 
    private String celsusiusToFarheneit(String t){
        if(t==null){
            return "Empty";
        }
        if(t.equals("")){
            return "Empty";
        }
        
        int celsius = Integer.parseInt(t);
        float farheneit = celsius * 9/5 + 32;
        
        return String.valueOf(farheneit);
    }
    
// Method to override the setText into the cel_to_farh frame of java beans
    @Override
    public void setText(String t){
        super.setText(celsusiusToFarheneit(t));
    }
    
    public static void main(String[] args) {
        tempLabel label = new tempLabel();
        String[] random_int = {"100", "0", "93", "32"};
        
            for(String value : random_int) {
                label.setText(value);
                System.out.println("Celsius" + value + "==>" + label.getText());
            }
        }
    }