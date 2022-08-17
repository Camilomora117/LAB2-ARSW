package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;

public class PrimeFinderThread extends Thread{

	
	int a,b;

	String name;

	boolean suspend;
	
	private List<Integer> primes=new LinkedList<Integer>();

	/**
	 * Constructor del Threads
	 * @param a rango inicial del Thread
	 * @param b rango final del Thread
	 * @param name nombre del Thread
	 */
	public PrimeFinderThread(int a, int b, String name) {
		super();
		this.a = a;
		this.b = b;
		this.name = name;
	}

	/**
	 * Metodo overraid run
	 */
	public void run(){
		for (int i=a;i<=b;i++){						
			if (isPrime(i)){
				primes.add(i);
				System.out.println(i + " " + name);
			}

			//Si suspend es True pone todos los Threads en Espera
			synchronized(this) {
				while(suspend) {
					try {
						this.wait();
					} catch(InterruptedException e) {
						System.out.println("Erro in the Process");
					}
				}
			}
		}
	}

	/**
	 * Metodo que verifica si un numero es primo
	 * @param n numero a verificar
	 * @return True si es primo, false de lo contrario
	 */
	boolean isPrime(int n) {
	    if (n%2==0) return false;
	    for(int i=3;i*i<=n;i+=2) {
	        if(n%i==0)
	            return false;
	    }
	    return true;
	}

	/**
	 * Retorna la lista de los numeros primos encontrados
	 * @return lista de numeros primos
	 */
	public List<Integer> getPrimes() {
		return primes;
	}

	/**
	 * Cambia el valor de suspend
	 * @param suspend valor nuevo de suspend
	 */
	public void setSuspend(boolean suspend) {
		this.suspend = suspend;
	}

	/**
	 * Metodo para reanudar el Thread
	 */
	synchronized void resumeThread() {
		suspend = false;
		notify();
	}

}
