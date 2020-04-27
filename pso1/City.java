import java.util.ArrayList;

public class City {
    private ArrayList<Double> distante;
    private String name;

    public City(ArrayList<Double>distante, String name) {
        this.distante = distante;
        this.name = name;
    }

    public ArrayList<Double> getDistante() {
        return distante;
    }

    public void setDistante(ArrayList< Double> distante) {
        this.distante = distante;
    }
    public double measureDistance(City city){
        return distante.get(Integer.parseInt(city.getName())-1);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
