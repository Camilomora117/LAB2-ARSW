package edu.eci.arsw.primefinder;

import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Scanner scanner;
		String in;
		Date startDate, endDate;
		int seconds=0;

		LinkedList<PrimeFinderThread> primeFinderThreads = new LinkedList<>();
		startDate = new Date();

		PrimeFinderThread pft1=new PrimeFinderThread(0, 10000000, "Thread 1");
		primeFinderThreads.add(pft1);

		PrimeFinderThread pft2=new PrimeFinderThread(10000000, 20000000, "Thread 2");
		primeFinderThreads.add(pft2);

		PrimeFinderThread pft3= new PrimeFinderThread(20000000, 30000000, "Thread 3");
		primeFinderThreads.add(pft3);

		for(PrimeFinderThread thread:primeFinderThreads){
			thread.start();
		}

		//Contamos los 5 segundos con la fecha actual(enDate) y la fecha de inicio del programa(startDate)
		while(seconds!=5) {
			endDate = new Date();
			seconds = (int)((endDate.getTime()-startDate.getTime())/1000);
		}

		//Suspendemos los threads
		for(PrimeFinderThread thread:primeFinderThreads) {
			thread.setSuspend(true);
		}

		System.out.println("Press Enter to Continue.");

		//Escaneamos el input del Usuario
		scanner = new Scanner(System.in);
		in = scanner.nextLine();

		//Continuamos el programa
		if(in.equals("")) {
			for(PrimeFinderThread thread:primeFinderThreads) {
				thread.resumeThread();
			}
		}

	}
	
}
