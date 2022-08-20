package edu.eci.arsw.primefinder;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

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
        Scanner scan = new Scanner(System.in);
        for(int i= 0;i<threads;i++){
            pft[i].start();
        }
        while(!pft[pft.length-1].getWait()){
            synchronized(this.primes){
                if(isFinish()){
                    break;
                }
                if(pft[pft.length-1].getWait()){
                    System.out.println("Se han encontrado " + primes.size() + " hasta el momento");
                    System.out.println("Pulse ENTER para continuar");
                    String next = scan.nextLine();
                    if(next.isEmpty()){
                        System.out.println("Hilos corriendo nuevamente");
                        rRun();
                        primes.notifyAll();
                        System.out.println("Se han encontrado " + primes.size() + " hasta el momento");

                    }
                }
            }
        }
        System.out.println("Total de primos: " + primes.size());

    }

    public void rRun(){
        for(PrimeFinderThread thread : pft){
            thread.setWait(false);
            thread.setInicio(System.currentTimeMillis());    
        }
    }

    public boolean isFinish(){
        boolean finish = false;
        for(PrimeFinderThread thread : pft){
            finish |= thread.isAlive();
        }
        return !finish;
    }
}
