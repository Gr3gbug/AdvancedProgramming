package exercise2.pkg3;
import java.lang.reflect.Method;
import java.util.LinkedList;
/**
 *
 * @author gregbug
 */

public class Exercise23 {
    
    public void beanInspector (String s) throws ClassNotFoundException {
        
        // Temporary variable
        LinkedList<String> tmp_Get = new LinkedList<>();
        LinkedList<String> tmp_Set = new LinkedList<>();
        String not_fall = "Empty";
        // Check for beans name and get it
        Class c = Class.forName(s);
        Method[] methods = c.getMethods();
        
        // Retrieve each method for that Java Beans Class
        for(Method method : methods){
            String tmp_S = method.getName();
                if(tmp_S.indexOf("get")==0){
                    tmp_Get.add(tmp_S.substring("get".length()));
                }
                else if(tmp_S.indexOf("set")==0){
                     tmp_Set.add(tmp_S.substring("set".length()));
                }
        }
        
        if(!tmp_Get.isEmpty()){
            System.out.println(tmp_Get);
        } else if(!tmp_Set.isEmpty()){
            System.out.println(tmp_Set);
        } else {
            System.out.println("Not found!");
        }
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main (String[] args) throws ClassNotFoundException {
        // TODO code application logic here
        String s = "javax.swing.JFrame";
        Exercise23 test = new Exercise23();
        test.beanInspector(s);
    }   
}