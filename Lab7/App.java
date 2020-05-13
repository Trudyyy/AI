import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class App {
    public static ArrayList<ArrayList<Double>>trainIn=new ArrayList<>();
    public static ArrayList<ArrayList<Double>>testIn=new ArrayList<>();
    public static ArrayList<Double>trainOut=new ArrayList<>();
    public static ArrayList<Double>testOut=new ArrayList<>();
    public static ArrayList<ArrayList<Double>>inputs=new ArrayList<>();
    public static ArrayList<Double>outputs=new ArrayList<>();
    public static void do_ml(){
        ArrayList<String> columns=new ArrayList<>();
        columns.add("Economy..GDP.per.Capita.");
        columns.add("Freedom");
        String need="Happiness.Score";
        read("C:\\Users\\tvasi\\Desktop\\Lab7-IA\\src\\world-happiness-report-2017.csv",columns,need);
        splitData();
        LinearRegression r=new LinearRegression(trainIn,trainOut);
        ArrayList<Double> compuredOutputs=new ArrayList<>();
        for (int i=0;i<testIn.get(0).size();i++){
            compuredOutputs.add(func(r.getB0(),r.getB1(),r.getB2(),testIn.get(0).get(i),testIn.get(1).get(i)));
        }
        Regression err=new Regression(testOut,compuredOutputs,testOut.size());
        System.out.println("Eroare absoluta "+ err.error_abs());
        System.out.println("Eroare patrata: "+err.error_sqr());
    }
    public static void main(String[] args){
        do_ml();
    }
    public static double func(double b0,double b1,double b2,double x1,double x2){
        return (double)b0+b1*x1+b2*x2;
    }
    public static void splitData(){
        Random r=new Random();
        ArrayList<Integer>indexes=new ArrayList<>();
        for(int i=0;i<inputs.get(0).size();i++){
            indexes.add(i);
        }
        ArrayList<Integer> trainSample=new ArrayList<>();
        for(int i=0;i<indexes.size();i++){
            Random c=new Random();
            if(c.nextFloat()<=0.8) {
                int a = indexes.get(r.nextInt(inputs.get(0).size()));
                while (trainSample.contains(a)) {
                    a = indexes.get(r.nextInt(inputs.get(0).size()));
                }
                trainSample.add(a);
            }
        }
        ArrayList<Integer> testSample=new ArrayList<>();
        for (int index : indexes) {
            if (!trainSample.contains(index))
                testSample.add(index);
        }
        for(int i=0;i<trainSample.size();i++){
            trainOut.add(outputs.get(i));
        }
        for(int i=0;i<testSample.size();i++){
            testOut.add(outputs.get(i));
        }
        ArrayList<Double> testInputs=new ArrayList<>();
        ArrayList<Double> trainInputs=new ArrayList<>();
        for (ArrayList<Double> input : inputs) {
            trainInputs.clear();
            testInputs.clear();
            for (int j = 0; j < trainSample.size(); j++) {
                trainInputs.add(input.get(j));
            }
            trainIn.add(trainInputs);
            for (int j = 0; j < testSample.size(); j++) {
                testInputs.add(input.get(j));
            }
            testIn.add(testInputs);
        }
    }
    public static void read(String fileName,ArrayList<String> inputNames,String outputVariabName){
        ArrayList<String[]> data=new ArrayList<>();
        ArrayList<String> dataNames=new ArrayList<>();
        String line;
        int count=0;
        try(BufferedReader br=new BufferedReader(new FileReader(fileName))){
            while ((line=br.readLine())!=null) {
                String[] date = line.split(",");
                if(count==0){
                    dataNames.addAll(Arrays.asList(date));
                }
                else data.add(date);
                count++;
            }
            for (String inputVariabName : inputNames) {
                int selectedVariable = dataNames.indexOf(inputVariabName);
                ArrayList<Double> inp = new ArrayList<>();
                for (String[] datum : data) {
                    inp.add(Double.parseDouble(datum[selectedVariable]));
                }
                inputs.add(inp);
            }
            int selectedOutput=dataNames.indexOf(outputVariabName);

            for (String[] datum : data) {
                outputs.add(Double.parseDouble(datum[selectedOutput]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Regression {
    private ArrayList<Double> realOutputs;
    private ArrayList<Double> computedOutputs;
    private int noOfSamples;

    public Regression(ArrayList<Double> realOutputs, ArrayList<Double> computedOutputs, int noOfSamples) {
        this.realOutputs = realOutputs;
        this.computedOutputs = computedOutputs;
        this.noOfSamples = noOfSamples;
    }


    public double error_abs() {
        double sum = 0.0;
        int noOfSamples1 = 0;
        for (int i = 0; i < noOfSamples; i++) {
            double realOutput = (double) realOutputs.get(i);
            double computedOutput = (double) computedOutputs.get(i);
            noOfSamples1++;
            sum += Math.abs(realOutput - computedOutput);
        }
        return (double) sum / (double) noOfSamples1;
    }

    public double error_sqr() {
        double sum = 0.0;
        int noOfSamples1 = 0;
        for (int i = 0; i < noOfSamples; i++) {
            double realOutput = (double) realOutputs.get(i);
            double computedOutput = (double) computedOutputs.get(i);
            noOfSamples1++;
            sum += Math.pow(realOutput - computedOutput, 2);
        }
        return Math.sqrt(sum / (double) noOfSamples1);
    }
}
class LinearRegression{
    private ArrayList<ArrayList<Double>> ins;
    private ArrayList<Double> outs;
    private double b0;
    private double b1;
    private double b2;

    public double getB0() {
        return b0;
    }

    public void setB0(double b0) {
        this.b0 = b0;
    }

    public double getB1() {
        return b1;
    }

    public void setB1(double b1) {
        this.b1 = b1;
    }

    public double getB2() {
        return b2;
    }

    public void setB2(double b2) {
        this.b2 = b2;
    }

    public LinearRegression(ArrayList<ArrayList<Double>> ins, ArrayList<Double> outs) {
        this.ins = ins;
        this.outs = outs;
        double[][] d=new double[ins.get(0).size()][ins.size()+1];
        for(int i=0;i<ins.size();i++){
            for(int j=0;j<ins.get(0).size();j++){
                d[j][i+1]=ins.get(i).get(j);
            }
        }
        for (int i=0;i<ins.get(0).size();i++){
            d[i][0]=1;
        }

        double[]f=new double[outs.size()];
        for(int i=0;i<outs.size();i++){
            f[i]=outs.get(i);
        }
        MultipleLinearRegression regression = new MultipleLinearRegression(d, f);
        this.setB0(regression.beta(0));
        this.setB1(regression.beta(1));
        this.setB2(regression.beta(2));
        System.out.printf("model invatat: f(x)= %.2f + %.2f *X1 + %.2f *X2  (R^2 = %.2f)\n",
                regression.beta(0), regression.beta(1), regression.beta(2), regression.R2());
    }
}
