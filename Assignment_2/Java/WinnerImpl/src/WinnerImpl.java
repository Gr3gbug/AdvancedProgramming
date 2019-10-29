import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
/**
 *
 * @author gregbug
 */
public class WinnerImpl implements Winner {
    
    private int year;
    private int winnerAge;
    private String winnerName;
    private String filmTitle;

    public void setWinnerAge(int winnerAge) {
        this.winnerAge = winnerAge;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }

    public void setFilmTitle(String filmTitle) {
        this.filmTitle = filmTitle;
    }
    
    public void setYear(int year) {
        this.year = year;
    }
    
    @Override
    public int getYear() {
        return year;
    }

    @Override
    public int getWinnerAge() {
        return winnerAge;
    }

    @Override
    public String getWinnerName() {
        return winnerName;
    }

    @Override
    public String getFilmTitle() {
        return filmTitle;
    }
    
    public static Collection<Winner> loadData (String[] paths) {
        // STEP 1: I'm going to create the stream trough the Stream.builder
        ArrayList<Winner> collection = new ArrayList();

        String line;
        for (String f : paths){
            // Step 2: I'm going to READ the file 
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                br.readLine(); // NOTE: i want to jump the first header because contain the header of csv file 
                while ((line = br.readLine()) != null) {
                    // STEP 3: I'm going to split the column of file 
                    String[] fields = line.split(",");

                    // STEP 4: I'm going to create the onject of file
                    WinnerImpl w = new WinnerImpl();
                    w.setYear(Integer.parseInt(fields[1]));
                    w.setWinnerAge(Integer.parseInt(fields[2]));
                    // STEP 5: I'm going to sanitize the extra unusefull character (in this case rapresented by "")
                    w.setWinnerName(fields[3].trim().replaceAll("\"", ""));
                    w.setFilmTitle(fields[4].trim().replaceAll("\"", ""));
                    // STEP 6: Now i can insert the object inside the stream 
                    collection.add(w);
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        // STEP 7: Stream returned 
        return collection;
    }
    
}
