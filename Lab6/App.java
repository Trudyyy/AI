import java.util.ArrayList;
import java.util.Arrays;

public class App {
    public static ArrayList<ArrayList<Integer>> matricea(ArrayList<String> realList, ArrayList<String> computedList, ArrayList<String> lableList) {
        ArrayList<ArrayList<Integer>> m = new ArrayList<>();
        for (int i = 0; i < lableList.size(); i++) {
            ArrayList<Integer> p = new ArrayList<>();
            for (int j = 0; j < lableList.size(); j++) {
                p.add(0);
            }
            m.add(p);
        }
        for (int i = 0; i < realList.size(); i++) {
            int real = 0;
            int computed = 0;
            for (int j = 0; j < lableList.size(); j++) {
                if (realList.get(i).equals(lableList.get(j))) real = j;
                if (computedList.get(i).equals(lableList.get(j))) computed = j;
            }
            ArrayList<Integer> c = m.get(real);
            c.set(computed, c.get(computed) + 1);
            m.set(real, c);
        }
        return m;
    }

    public static void multiClasificare(ArrayList<String> realList, ArrayList<String> computedList, ArrayList<String> lableList) {
        ArrayList<ArrayList<Integer>> m = matricea(realList, computedList, lableList);
        System.out.println("Matricea este:");
        for (ArrayList<Integer> integers : m) {
            for (int j = 0; j < m.get(0).size(); j++) {
                System.out.print(integers.get(j)+" ");
            }
            System.out.println();
        }
        System.out.println();
        //acuratete
        int pereche = 0;
        for (int i = 0; i < m.size(); i++) {
            pereche += m.get(i).get(i);
        }
        System.out.println("Acuratetea:" + (double) pereche / (double) realList.size());
        System.out.println();
        //precizie si rapel
        for (int i = 0; i < lableList.size(); i++) {
            int totalLable = 0;
            int compTotal = 0;
            for (int j = 0; j < m.get(0).size(); j++)
                totalLable += m.get(i).get(j);//linia

            for (int j = 0; j < lableList.size(); j++)
                compTotal += m.get(j).get(i);//coloana

            if (compTotal == 0) System.out.println("Nu se poate calcula precizia pt " + lableList.get(i));
            else
                System.out.println("Precizia pentru " + m.get(i) + " este " + (double) m.get(i).get(i) / (double) compTotal);

            if (totalLable == 0) System.out.println("Nu se poate calcula rapelul pentru " + lableList.get(i));
            else
                System.out.println("Rapelul pentru " + m.get(i) + " este " + (double) m.get(i).get(i) / (double) totalLable);
            System.out.println();
        }
    }

    public static void main(String[] args) {
        //realOutput=['lalea','narcisa','narcisa','zambila','lalea','lalea','narcisa','ghiocel','zambila','ghiocel','ghiocel','zambila','zambila']
        //computedOutput=['narcisa','narcisa','narcisa','zambila','ghiocel','lalea','narcisa','lalea','lalea','zambila','ghiocel','zambila','zambila']
        //labelsList=['lalea','narcisa','zambila','ghiocel']
        //realOutput1=[[2,9.8],[3,7.2],[12,5.1],[6,10],[9,2.1],[7,7],[1,13.4],[2,2.1],[4,6],[14,9.3]]
        //computedOutput1=[[4,5.6],[2.9,7],[16,2.8],[6.3,10],[5,1.3],[9.3,8.1],[3,12.6],[4.8,8],[5.7,3],[14.2,9.3]]
        ArrayList<String> realOutput = new ArrayList<>();
        realOutput.add("lalea");
        realOutput.add("narcisa");
        realOutput.add("narcisa");
        realOutput.add("zambila");
        realOutput.add("lalea");
        realOutput.add("lalea");
        realOutput.add("narcisa");
        realOutput.add("ghiocel");
        realOutput.add("zambila");
        realOutput.add("ghiocel");
        realOutput.add("ghiocel");
        realOutput.add("zambila");
        realOutput.add("zambila");

        ArrayList<String> computedOutput = new ArrayList<>();
        computedOutput.add("narcisa");
        computedOutput.add("narcisa");
        computedOutput.add("narcisa");
        computedOutput.add("zambila");
        computedOutput.add("ghiocel");
        computedOutput.add("lalea");
        computedOutput.add("narcisa");
        computedOutput.add("lalea");
        computedOutput.add("lalea");
        computedOutput.add("zambila");
        computedOutput.add("ghiocel");
        computedOutput.add("zambila");
        computedOutput.add("zambila");

        ArrayList<String> lableList = new ArrayList<>();
        lableList.add("lalea");
        lableList.add("narcisa");
        lableList.add("zambila");
        lableList.add("ghiocel");

        ArrayList<ArrayList<Double>> realOutput1 = new ArrayList<>();
        realOutput1.add(new ArrayList<>(Arrays.asList(2.0, 9.8)));
        realOutput1.add(new ArrayList<>(Arrays.asList(3.0, 7.2)));
        realOutput1.add(new ArrayList<>(Arrays.asList(12.0, 5.1)));
        realOutput1.add(new ArrayList<>(Arrays.asList(6.0, 10.0)));
        realOutput1.add(new ArrayList<>(Arrays.asList(9.0, 2.1)));
        realOutput1.add(new ArrayList<>(Arrays.asList(7.0, 7.0)));
        realOutput1.add(new ArrayList<>(Arrays.asList(1.0, 13.4)));
        realOutput1.add(new ArrayList<>(Arrays.asList(2.0, 2.1)));
        realOutput1.add(new ArrayList<>(Arrays.asList(4.0, 6.0)));
        realOutput1.add(new ArrayList<>(Arrays.asList(14.0, 9.3)));

        ArrayList<ArrayList<Double>> computedOutput1 = new ArrayList<>();
        computedOutput1.add(new ArrayList<>(Arrays.asList(4.5, 6.0)));
        computedOutput1.add(new ArrayList<>(Arrays.asList(2.9, 7.0)));
        computedOutput1.add(new ArrayList<>(Arrays.asList(16.0, 2.8)));
        computedOutput1.add(new ArrayList<>(Arrays.asList(6.3, 10.0)));
        computedOutput1.add(new ArrayList<>(Arrays.asList(5.0, 1.3)));
        computedOutput1.add(new ArrayList<>(Arrays.asList(9.3, 8.1)));
        computedOutput1.add(new ArrayList<>(Arrays.asList(3.0, 12.6)));
        computedOutput1.add(new ArrayList<>(Arrays.asList(4.8, 8.0)));
        computedOutput1.add(new ArrayList<>(Arrays.asList(5.7, 3.0)));
        computedOutput1.add(new ArrayList<>(Arrays.asList(14.2, 9.3)));


        multiClasificare(realOutput, computedOutput, lableList);
        System.out.println();
        errMultiTargetRegre(realOutput1,computedOutput1);
    }

    public static double distantaEuclidiana(ArrayList<Double> x, ArrayList<Double> y) {
        double s = 0;
        for (int i = 0; i < x.size(); i++) {
            s += Math.pow((x.get(i) - y.get(i)), 2);
        }
        return Math.sqrt(s);
    }

    public static void errMultiTargetRegre(ArrayList<ArrayList<Double>> RealList, ArrayList<ArrayList<Double>> computedList) {
        double error1 = 0.0;
        double error2 = 0.0;
        for (int i = 0; i < RealList.size(); i++) {
            error1 += Math.abs(distantaEuclidiana(RealList.get(i), computedList.get(i)));
            error2 += Math.pow(distantaEuclidiana(RealList.get(i), computedList.get(i)), 2);
        }
        System.out.println("Eroarea cu diferenta absoluta: " + error1 / (double) RealList.size());
        System.out.println("Eroarea cu patratele: " + Math.sqrt(error2 / (double) RealList.size()));
    }
}
