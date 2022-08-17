package edu.eci.arsw.primefinder;
import java.util.LinkedList;
import java.util.List;

public class Control extends Thread{

    private static final int threads = 3;
    private static final int max = 10000000;
    private final int data = max / threads;
    private PrimeFinderThread pft[];
    private List<Integer> primes = new LinkedList<>();

    public Control(){
        super();
        this.pft = new PrimeFinderThread[threads];
        int i;
        for(i=0;i<threads-1;i++){
            pft[i] = new PrimeFinderThread(i*data,(i+1)*data,this.primes);
        }
        pft[i] = new PrimeFinderThread(i*data,max + 1,this.primes);
    }

    @Override
    public void run(){
        for(int i= 0;i<threads;i++){
            pft[i].start();
        }
    }
}
