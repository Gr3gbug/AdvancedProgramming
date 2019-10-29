package helloworld;
/**
 *
 * @author gregbu
 */
public class HelloWorld {
    
    public void testReturn(String s) {
        String result;
        
        if(s.length()>10){
            result = "The string is greather than 10";
        }
        else {
            result = "String is lower than 10";
        }
        
        System.out.println(result);
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String myString = "Ciaociaociaociao";
        HelloWorld test = new  HelloWorld();
        test.testReturn(myString);
    }
    
}