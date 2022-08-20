package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;

public class PrimeFinderThread extends Thread{

	
	int a,b;	
	private List<Integer> primes=new LinkedList<Integer>();
	private final static int mSegundos = 5000;
	private boolean wait;
	private long inicio,fin;
	
	/**
	 * Constructor de la clase
	 * @param a 
	 * @param b
	 * @param primes
	 */
	public PrimeFinderThread(int a, int b, List<Integer> primes) {
		super();
		this.a = a;
		this.b = b;
		this.primes = primes;
	}

	/**
	 * Funcion con el fin de ejecutar el hilo y se detenga una vez haya transcurrido 5 segundos
	 */
	@Override
	public void run(){
		this.inicio = System.currentTimeMillis();
		this.fin = System.currentTimeMillis();
		synchronized(this.primes){
			for (int i=a;i<=b;i++){						
				if(fin - inicio <= mSegundos){
					wait = false;
					if (isPrime(i)){
						primes.add(i);
						System.out.println(i);
						fin = System.currentTimeMillis();
					}
				}
				else{
					wait = true;
					System.out.println("Limite de tiempo");
					try {
						primes.wait();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	boolean isPrime(int n) {
	    if (n%2==0) return false;
	    for(int i=3;i*i<=n;i+=2) {
	        if(n%i==0)
	            return false;
	    }
	    return true;
	}

	public List<Integer> getPrimes() {
		return primes;
	}

	public boolean getWait(){
		return wait;
	}

	public void setWait(boolean b){
		this.wait = b;
	}

	public void setInicio(long inicio){
		this.inicio = inicio;
	}
	
}
