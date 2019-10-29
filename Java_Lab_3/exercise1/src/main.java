import java.util.stream.IntStream;
/**
 *
 * @author gregbug
 * 
 * Main
 */
public class main {
    
    public static void main(String[] args) {
        int sumodd=IntStream.range(0,10).filter(p -> p%2==0).sum();
        System.out.println(sumodd);
    }
    
}