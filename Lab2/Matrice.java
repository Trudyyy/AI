import java.io.*;
import java.util.Arrays;

public class Matrice {
    private String path;
    public int[][] m;
    public int dim;
    public int sursa=Integer.MAX_VALUE;
    public int destinatie;
    public Matrice(String path){
        this.path=path;
        citeste();
    }
    public void citeste(){
        try(BufferedReader bufferedReader=new BufferedReader(new FileReader(new File(this.path)))){
            String line;
            int l=0;
            while ((line=bufferedReader.readLine())!=null){
                if (l==0){
                    dim=Integer.parseInt(line);
                    m=new int[dim+1][dim+1];
                }
                else if(l<=dim){
                    String[] distante=line.split(",");
                    int i=1;
                    for(String s:distante){
                        this.m[l][i]=Integer.parseInt(s);
                        i++;
                    }
                }
                else if(l<=dim+2){
                    if (l==dim+1)
                        sursa = Integer.parseInt(line);

                    if(l==dim+2) destinatie=Integer.parseInt(line);
                }
                else {
                    System.out.println("Fisier corupt");
                }
                l++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void scrie(String Path,String orase1,int suma1,String orase2,int suma2){
        try(BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(new File(Path)))){
            bufferedWriter.append(String.valueOf(this.dim));
            bufferedWriter.newLine();
            bufferedWriter.append(orase1);
            bufferedWriter.newLine();
            bufferedWriter.append(String.valueOf(suma1));
            bufferedWriter.newLine();
            bufferedWriter.append(String.valueOf(Arrays.stream(orase2.split(",")).count()));
            bufferedWriter.newLine();
            bufferedWriter.append(orase2);
            bufferedWriter.newLine();
            bufferedWriter.append(String.valueOf(suma2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
