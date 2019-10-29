package exercise3;
/**
 *
 * @author gregbug
 */
public class Exercise3 {
    
    // Object Declaration
    public class Object{
        private String objName;
        private int objIndex;

        @Override
        public String toString(){
            return objName + " has " + objIndex + " index.";
        }
    
    }
    
    private static Object[] replObj;
    
    // Method to replicate object N times 
    public static Object[] repl(Object[] xs, int n){
        while(n>=0){
            for(Object obj : xs){
                replObj = xs.clone();
            }
        }
        return replObj;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
            
    }
    
}