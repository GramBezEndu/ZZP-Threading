package com.tt.concurrent.callable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 1. Stwórz pulę wątków
 * 	a) Wykorzystaj Runtime.getRuntime().availableProcessors() aby pobrać ilość dostępnych procesów
 * 	b) pulę stwórz wykorzystując Executors.newFixedThreadPool(numberOfThreads);
 * 2. Stwórz zadania ComplexMathCallable w zależności od ilości wątków
 * 3. Zgłoś zadania do wykonania (metoda submit);
 * 4. Odłóż zwracane obiekty typu Future na listę - przydadzą się przy pobieraniu wyników
 * 5. Przeiteruj po liście i wywołaj metodę get() na obiektach Future
 * 	a) pamiętaj, że poszczególne wyniki należy zsumować
 * 6. Pamiętaj o zamknięciu ExecutorService'u - executor.shutdown();
 * 7. Zaprezentuj wyniki i zinterpretuj czas wykonania
 * 
 */
public class ComplexMathRunner {
	private final static int jobSize = 16000;
	public static void main(String[] args) {
		System.out.println("Program start...");
		MultipleThreads();
		OneThread();
	}

	private static void MultipleThreads() {
		//1 a
		int numberOfThreads = Runtime.getRuntime().availableProcessors();
		System.out.format("%d thread/s:\n", numberOfThreads);
		//1 b
		ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
		//2 Stwórz zadania ComplexMathCallable
		List<ComplexMathCallable> complexMathCallables = new ArrayList<ComplexMathCallable>();
		for (int i = 0; i < numberOfThreads; i++) {
			complexMathCallables.add(new ComplexMathCallable(jobSize, jobSize / numberOfThreads));
		}
		//3 submit && 4 Odłóż zwracane obiekty typu Future na listę
		long startTime = System.currentTimeMillis();
		List<Future<Double>> futureList = null;
		try {
			futureList = executor.invokeAll(complexMathCallables);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//5 Przeiteruj po liście i zsumuj
		double result = 0;
		for (var f : futureList) {
			try {
				result += f.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		System.out.println(result);
		//6
		executor.shutdown();
		//7
		long endTime = System.currentTimeMillis();
		System.out.println("Execution time: " + ((endTime - startTime) / 1000d) + " seconds.");
	}

	private static void OneThread() {
		System.out.println("1 thread:");
		ComplexMath cm = new ComplexMath(jobSize, jobSize);
		long startTime = System.currentTimeMillis();
		System.out.println(cm.calculate());
		long endTime = System.currentTimeMillis();
		System.out.println("Execution time: " + ((endTime - startTime) / 1000d) + " seconds.");
	}
}
