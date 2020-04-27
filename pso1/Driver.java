import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Driver {

    static final int NUMBER_OF_ANTS = 80;
    static final double PROCESSING_CYCLE_PROBABILITY = 0.8;
    static ArrayList<City> initialRoute=new ArrayList<>();
    static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    static ExecutorCompletionService<Ant> executorCompletionService = new ExecutorCompletionService<>(executorService);
    private Route shortestRoute = null;
    private int activeAnts = 0;

    public static void main(String[] args) throws IOException {
        Driver driver = new Driver();
        read();
        driver.printHeading();
        ACO aco = new ACO();
        IntStream.range(1, NUMBER_OF_ANTS).forEach(x -> {
            executorCompletionService.submit(new Ant(aco, x));
            driver.activeAnts++;
            if (Math.random() > PROCESSING_CYCLE_PROBABILITY)
                driver.processAnts();
        });
        driver.processAnts();
        executorService.shutdownNow();
        System.out.println("Optimal route: " + Arrays.toString(driver.shortestRoute.getCities().stream().mapToInt(x->Integer.parseInt(x.getName())).toArray()));
        System.out.println("Distance: " + driver.shortestRoute.getDistance());
    }

    private void processAnts() {
        while (activeAnts > 0) {
            try {
                Ant ant = executorCompletionService.take().get();
                Route currentRoute = ant.getRoute();
                if (shortestRoute == null || currentRoute.getDistance() < shortestRoute.getDistance()) {
                    shortestRoute = currentRoute;
                    StringBuffer distance = new StringBuffer("    " + String.format("%.2f", currentRoute.getDistance()));
                    IntStream.range(0, 21 - distance.length()).forEach(k -> distance.append(""));
                    System.out.println(Arrays.toString(shortestRoute.getCities().stream().mapToInt(x->Integer.parseInt(x.getName())).toArray()) + " |" + distance + " |" + ant.getAntNumb());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            activeAnts--;
        }
    }

    private void printHeading() {
        String headingColumn1 = "Route";
        String remainingHeadingColumns = "Distance | ant #";
        int cityNameLength = 0;
        for (int x=0;x<initialRoute.size();x++) cityNameLength += initialRoute.get(x).getName().length();
        int arrayLength = cityNameLength + initialRoute.size() * 2;
        int partialLength = (arrayLength - headingColumn1.length()) / 2;
        for (int x = 0; x < partialLength; x++) System.out.print(" ");
        System.out.print(headingColumn1);
        for (int x = 0; x < partialLength; x++) System.out.print(" ");
        if ((arrayLength % 2) == 0) System.out.print(" ");
        System.out.print(" | " + remainingHeadingColumns);
        cityNameLength += remainingHeadingColumns.length() + 3;
        for (int x = 0; x < cityNameLength + initialRoute.size() * 2; x++) System.out.print(" ");
        System.out.println();
    }
    private static void read() throws FileNotFoundException {
        try(BufferedReader bufferedReader=new BufferedReader(new FileReader(new File("C:\\Users\\tvasi\\Desktop\\PSO\\src\\input.txt")))){
            String line="";
            int dim=0;
            int nrLine=0;
            while((line=bufferedReader.readLine())!=null) {
                if(nrLine==0){
                    dim=Integer.parseInt(line);
                }
                else if(dim>=nrLine){
                    String[] distances = line.split(",");
                    if(distances.length!=dim)throw new IOException("corupt");
                    ArrayList<Double> dist = new ArrayList<>();
                    for (String s : distances) {
                        dist.add(Double.valueOf(s));
                    }
                    initialRoute.add(new City(dist,String.valueOf(nrLine)));
                }
                else throw new IOException("Corupt");
                nrLine++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
