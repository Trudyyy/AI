class Ruta{
    String parcurgere;
    int distanta;

    public Ruta(String parcurgere, int distanta) {
        this.parcurgere = parcurgere;
        this.distanta = distanta;
    }

    public Ruta() {}

    public String getParcurgere() {
        return parcurgere;
    }

    public void setParcurgere(String parcurgere) {
        this.parcurgere = parcurgere;
    }

    public int getDistanta() {
        return distanta;
    }

    public void setDistanta(int distanta) {
        this.distanta = distanta;
    }
}

public class Main {
    public static void main(String[] args) {
        Matrice mat = new Matrice("D:\\Facultate\\Anul ii\\Sem 2\\AI\\Lab2\\src\\Input.txt");
        int[] distante=new int[mat.dim+1];
        String[] parcurgeri=new String[mat.dim+1];
        Ruta p=new Ruta();
        for(int i=1;i<=mat.dim;i++){
            p.setParcurgere("");
            p.setDistanta(0);
            calculeaza(mat,i,i,p);
            distante[i]=p.getDistanta();
            parcurgeri[i]=p.getParcurgere();
        }
        p.setDistanta(0);
        p.setParcurgere("");
        calculeaza(mat, mat.sursa, mat.destinatie,p);
        int minim=distante[1];
        int poz=1;
        for(int i=2;i<=mat.dim;i++) {
            if (minim > distante[i]) {
                minim = distante[i];
                poz=i;
            }
        }
        mat.scrie("D:\\Facultate\\Anul ii\\Sem 2\\AI\\Lab2\\src\\Output.txt",parcurgeri[poz],distante[poz],p.parcurgere,p.distanta);
    }

    public static void calculeaza(Matrice m,int sursa,int destinatie,Ruta p) {
        p.setParcurgere(String.valueOf(sursa));
        int actual = sursa;
        boolean continua;
        do{
            continua=false;
            int posibil=0;
            int d = Integer.MAX_VALUE;
            for (int i = 1; i <= m.dim; i++) {
                if ((m.m[i][actual] < d) && (!p.getParcurgere().contains(String.valueOf(i)))) {
                    d = m.m[i][actual];
                    posibil = i;
                }
            }
            p.setParcurgere(p.getParcurgere().concat("," + (posibil)));
            p.setDistanta(p.getDistanta()+d);
            actual = posibil;
            for(int i=1;i<=m.dim;i++){
                if(!p.getParcurgere().contains(String.valueOf(i))){
                    continua=true;
                    break;
                }
            }
        }while(continua&&destinatie!=actual);
        if(destinatie==sursa){
            p.setDistanta(p.getDistanta()+m.m[sursa][actual]);
        }
    }
}
