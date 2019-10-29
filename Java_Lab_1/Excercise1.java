package exercise1;

import java.util.Arrays;
/**
 *
 * @author gregbug
 */
public class Exercise1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // code application logic here
        Arrays.sort(args);
        for(String s : args) {
            if(s.length() % 2 == 0)
                System.out.println(s);
        }
    }  
}
