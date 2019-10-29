import java.util.Arrays;
import java.util.List;

/**
 *
 * @author gregbug
 */
public class Exercise2 {

    public static immutablePair<Integer, Double> someCalc(List<Double> list)  {
        // Variable Declaration
        int numFirstRange = 0;
        int numSecondRange = 0;
        double sumSecondRange = 0;

        for (Double x : list) {
            if (x >= 0.2 && x <= Math.PI) {
                ++numFirstRange;
            }
            if (x >= 10.0 && x <= 100.0) {
                ++numSecondRange;
                sumSecondRange += x;
            }
        }
        
        double averageSecondRange = sumSecondRange / numSecondRange;
        return new immutablePair<>(numFirstRange, averageSecondRange);
    }
        
    public static immutablePair<Integer, Double> someCalcStreamed(List<Double> list) {
        int numFirstRange = (int)list.stream()
                                  .filter(x -> x >= 0.2 && x <= Math.PI)
                                  .count();
        double averageSecondRange = list.stream()
                                     .filter(x -> x >= 10.0 && x <= 100.0)
                                     .mapToDouble(e -> e)
                                     .average()
                                     .getAsDouble();

        return new immutablePair<>(numFirstRange, averageSecondRange);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List<Double> l = Arrays.asList(1.0, 2.6, 3.1, 10.0, 20.7, 29.3);
        immutablePair<Integer, Double> p1 = someCalc(l);
        immutablePair<Integer, Double> p2 = someCalcStreamed(l);
        System.out.println("Iterative calculation = " + p1);
        System.out.println("Streamed calculation = " + p2);
    }
    
}
