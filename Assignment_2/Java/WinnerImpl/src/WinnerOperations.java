
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author gregbug
 */
public class WinnerOperations {

    /**
     * method oldWinners that given a Stream<Winner> returns a new
     * Stream<String>
     * containing the names of the winners that are older than 35 sorted
     * alphabetically.
     *
     * @param input
     * @return
     */
    public static Stream<String> oldWinners(Stream<Winner> input) {
        /**
         * Through the 'filter' methods i can get a stream composed by of
         * distinct elements whose match with a given predicate (which, in my
         * case it is an anonymous function which take the properties
         * 'getWinnerAge' of a winner and check if is >= 35 or not), then
         * through a map i can obtain a Stream consisting on the results of
         * applying given function to the element of stream and finally i
         * ordered the element with 'sorted() method which will return a
         * Stream<String> as required
         */
        return input.filter(w -> w.getWinnerAge() >= 35).map(w -> w.getWinnerName()).sorted();
        
    }

    /**
     * method extremeWinners that given a Stream<Winner> returns a
     * Stream<String>
     * containing the names of all the youngest and of all the oldest winners,
     * sorted in inverse alphabetical ordering
     *
     * @param input
     * @return
     */
    public static Stream<String> extremeWinners(Stream<Winner> input) {
        /**
         * In this method i have decided to operate in the following way: First
         * of all i declared a Map structure, because i want to split the
         * youngest candidate from the older. Then i'll go to fill this
         * structure with a key (represented by Boolean value, so i'll get >>
         * true: youngest, and false: oldest) and a List of winner whose satisfy
         * the given condition. To do this inside 'collect()' method i have used
         * the 'Collectors' implementation of 'collector' interface, which, in
         * turns, allow us to summarizing elements according to various criteria
         * (in this case i use it, according to the groupingBy to obtain two
         * different group: the first rapresented by the youngest and the second
         * rapresented by the oldest)
         *
         * Torno prima tutti i giovani ( < 35) e li ordino al contrario, e poi faccio lo
         * stesso per i vecchi ( > 35)
         */
        Map<Boolean, List<Winner>> tmp = input
                .collect(Collectors
                        .groupingBy(w -> w.getWinnerAge() <= 35));
        
        Stream<String> youngStream = tmp
                .get(true)
                .stream()
                .map(w -> w.getWinnerName())
                .sorted((s1, s2) -> s2.compareTo(s1)); // NOTE: the 'compareTo' it has been used to revert the order of both list
        
        Stream<String> oldeStream = tmp
                .get(false)
                .stream()
                .map(w -> w.getWinnerName())
                .sorted((s1, s2) -> s2.compareTo(s1));
        
        return Stream.concat(youngStream, oldeStream);
    }
    
    public static Stream<String> extremeWinners_2(Stream<Winner> input) {
        /**
         * The following operation retrieves the names of each member in the collection winner and groups them by WinnerAge
         * The groupingBy operation in this example takes two parameters, a classification function and an instance of Collector. 
         * The Collector parameter is called a downstream collector. This is a collector that the Java runtime applies to 
         * the results of another collector. Consequently, this groupingBy operation enables me to apply a collect method 
         * to the List values created by the groupingBy operator. How does it work > 
         * At this point i apply the collector mapping, which applies the mapping function Winner::getWinnerAge to each element of the stream.
         * Consequently, the resulting stream consists of only the Age of winners.
         */
        Map<Integer, List<String>> tmpMap = input
                .collect(Collectors
                        .groupingBy(Winner::getWinnerAge, Collectors
                                .mapping(Winner::getWinnerName, Collectors.toList())));

        /**
         * Here from the tmpMap, i'm going to retrieve a 'set' view of the keys contained in the latter (the Map). Then we stream the retrieved
         * source and through the mapToInt function we are going to returns an 'IntStream' consisting of the results of applying 
         * the given function (which says to the 'stream().min()' function that i'll go to work on Integers) to the elements of this stream.
         * In this way i can get it from, both youngest and oldest too, the min value of the sorted stream and the max.
         * NOTE: Why the function getAsInt() ? --> Because the given object cannot contain instances of OptionalInt
         * it expected an Integer value, so i'll go to use the getAsInt() function to solve the problem
         */
        Stream<String> youngests = tmpMap.get(tmpMap.keySet().stream().mapToInt(i -> i).min().getAsInt()).stream().sorted();
        Stream<String> oldests = tmpMap.get(tmpMap.keySet().stream().mapToInt(i -> i).max().getAsInt()).stream().sorted(); 
        
        return Stream.concat(youngests, oldests);
    }
    
    public static Stream<String> multiAwardedFilm(Stream<Winner> inputStream) {       
        // TODO
        Map<String, Long> tmpMap = inputStream
                .collect(Collectors
                        .groupingBy(w -> w.getFilmTitle(), Collectors.counting()));              
        /**
         * Finally i can retrieve the name of Films whose have win more than one price, 
         * and generate the stream from the initial Map
         */
        Stream<String> finalString = tmpMap
                .entrySet()
                .stream()
                .filter(el -> el.getValue() >= 2 )
                .map(el -> el.getKey()).sorted();
        
        return finalString;
    }

    /**
     * method runJobs that given a Stream<Function<Stream<T>,Stream<U>>> of jobs
     * and a Collection<T> coll returns a Stream<U> obtained by concatenating
     * the results of the execution of all the jobs on the data contained in
     * coll
     *
     * @param <T>
     * @param <U>
     * @param job
     * @param coll
     * @return
     */
    public static <T, U> Stream<U> runJobs(Stream<Function<Stream<T>, Stream<U>>> job, Collection<T> coll) {

        // 2 Per ogni jobs prendo una collection e la trasformo in stream<t>
        // 3 Applico ad ogni stream il job opportuno e lo trasformo in stream<u>
        // 4 Concateno i stream ottenuti (con flatmap)
        /**
         * How does it work > For each job, i'm going to run the method
         * 'flatMap' which returns a stream consisting of the results of
         * replacing each element of this stream with the contents of a mapped
         * stream produced by applying the provided mapping function
         * (coll.stram() which, in turn, is in charge to convert the
         * Collection<T> in a Stream<T> ) to each element. In this way, finally
         * i'll get a flatMap which take a Stream<Function<Stream<T>>> and
         * returns a Stream<U>
         */
        return job.flatMap(j -> j
                .apply(coll.stream()));
        
    }
    
    public static void main(String[] args) {

        // Database path to take both files from destination folder
        String[] databasePaths = {"oscar_age_male.csv", "oscar_age_female.csv"};

        // Implementing a Collection<Winner> where i ='ll insert datas taken from DB
        // path
        Collection<Winner> collection = WinnerImpl.loadData(databasePaths);

        /**
         * To make the standard collection of winner a Stream<Winner> i'm going
         * to use the 'builder', which allows us to create a Stream by
         * generating elements individually and adding them to the Builder. How
         * does it work > A stream builder has a lifecycle, which starts in a
         * building phase (see below for details *) during which elements can be
         * added, and then followed by a transition to a built phase. The built
         * phase begins when the build() method is called (see below for details
         * **), which creates an ordered Stream whose elements are the elements
         * that were added to the stream builder
         */
        Stream.Builder<Function<Stream<Winner>, Stream<String>>> streamBuild = Stream.builder();
        // * stream builder lifecycle
        streamBuild.accept(WinnerOperations::oldWinners);
        streamBuild.accept(WinnerOperations::extremeWinners);
        streamBuild.accept(WinnerOperations::extremeWinners_2);
        streamBuild.accept(WinnerOperations::multiAwardedFilm);
        // ** Built phase
        Stream<Function<Stream<Winner>, Stream<String>>> jobsStream = streamBuild.build();
        
        runJobs(jobsStream, collection).forEach(System.out::println);
    }
    
}
