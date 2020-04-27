import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class Ant implements Callable<Ant> {
    public static final double ALPHA = 0.01;
    public static final double BETA = 9.5;
    public static final double Q = 0.0005;
    public static final double RHO = 0.2;
    static int invalidCityIndex = -1;
    static int numberOfCities = Driver.initialRoute.size();
    private Route route = null;
    private ACO aco;
    private int antNumb;

    public Ant(ACO aco, int antNumb) {
        this.aco = aco;
        this.antNumb = antNumb;
    }

    public Route getRoute() {
        return route;
    }

    public int getAntNumb() {
        return antNumb;
    }

    @Override
    public Ant call() throws Exception {
        int originalCityIndex = ThreadLocalRandom.current().nextInt(numberOfCities);
        ArrayList<City> routeCities = new ArrayList<>(numberOfCities);
        HashMap<String, Boolean> visitedCities = new HashMap<>(numberOfCities);
        IntStream.range(0, numberOfCities).forEach(x -> visitedCities.put(Driver.initialRoute.get(x).getName(), false));
        int numerOfVizitedCities = 0;
        visitedCities.put(Driver.initialRoute.get(originalCityIndex).getName(), true);
        double routeDistance = 0.0;
        int x = originalCityIndex;
        int y = invalidCityIndex;
        if (numerOfVizitedCities != numberOfCities) y = getY(x, visitedCities);
        while (y != invalidCityIndex) {
            routeCities.add(numerOfVizitedCities++, Driver.initialRoute.get(x));
            routeDistance += aco.getDistancesMatrix()[x][y];
            adjustPheromoneLevel(x, y, routeDistance);
            visitedCities.put(Driver.initialRoute.get(y).getName(), true);
            x = y;
            if (numerOfVizitedCities != numberOfCities) y = getY(x, visitedCities);
            else y = invalidCityIndex;
        }
        routeDistance += aco.getDistancesMatrix()[x][originalCityIndex];
        routeCities.add(numerOfVizitedCities, Driver.initialRoute.get(x));
        route = new Route(routeCities, routeDistance);
        return this;
    }

    private void adjustPheromoneLevel(int x, int y, double distance) {
        boolean flag = false;
        while (!flag) {
            double currentPheromoneLevel = aco.getPheromoneLevelMatrix()[x][y].doubleValue();
            double updatePheromoneLevel = (1 - RHO) * currentPheromoneLevel + Q / distance;
            if (updatePheromoneLevel < 0.00) flag = aco.getPheromoneLevelMatrix()[x][y].compareAndSet(0);
            else flag = aco.getPheromoneLevelMatrix()[x][y].compareAndSet(updatePheromoneLevel);
        }
    }

    private int getY(int x, HashMap<String, Boolean> visitedCities) {
        int returnY = invalidCityIndex;
        double random = ThreadLocalRandom.current().nextDouble();
        ArrayList<Double> transitionProbability = getTransitionProbabilities(x, visitedCities);
        for (int y = 0; y < numberOfCities; y++) {
            if (transitionProbability.get(y) > random) {
                returnY = y;
                break;
            } else random -= transitionProbability.get(y);
        }
        return returnY;
    }

    private ArrayList<Double> getTransitionProbabilities(int x, HashMap<String, Boolean> visitedCities) {
        ArrayList<Double> transitionProbabilities = new ArrayList<>(numberOfCities);
        IntStream.range(0, numberOfCities).forEach(i -> transitionProbabilities.add(0.0));
        double dominator = getTPDenominator(transitionProbabilities, x, visitedCities);
        IntStream.range(0, numberOfCities).forEach(y -> transitionProbabilities.set(y, transitionProbabilities.get(y) / dominator));

        return transitionProbabilities;
    }

    private double getTPDenominator(ArrayList<Double> transitionProbabilities, int x, HashMap<String, Boolean> visitedCities) {
        double dominator = 0.0;
        for (int y = 0; y < numberOfCities; y++) {
            if (!visitedCities.get(Driver.initialRoute.get(y).getName())) {
                if (x == y) transitionProbabilities.set(y, 0.0);
                else transitionProbabilities.set(y, getTPNumerator(x, y));
                dominator += transitionProbabilities.get(y);
            }
        }
        return dominator;
    }

    private double getTPNumerator(int x, int y) {
        double numerator = 0.0;
        double pheromoneLevel = aco.getPheromoneLevelMatrix()[y][x].doubleValue();
        if (pheromoneLevel != 0.0)
            numerator = Math.pow(pheromoneLevel, ALPHA) * Math.pow(1 / aco.getDistancesMatrix()[x][y], BETA);
        return numerator;
    }
}
