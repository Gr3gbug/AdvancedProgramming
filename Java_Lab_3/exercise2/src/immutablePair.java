/**
 *
 * @author gregbug
 */
public class immutablePair<T1, T2> {
    private final T1 fst;
    private final T2 snd;

    public T1 getFst() { return fst; }
    
    public T2 getSnd() { return snd; }
    
    public immutablePair(T1 fst, T2 snd) {
        this.fst = fst;
        this.snd = snd;
    }
    
    @Override
    public String toString() {
        return "(" + fst.toString() + ", " + snd.toString() + ")";
    }
}
