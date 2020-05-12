package com.tt.concurrent.callable;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ComplexMath {

	private double[][] matrix;
	
	public ComplexMath(int noRows, int noColumns) {
		//Random r = new Random();
		Random r = ThreadLocalRandom.current();
		matrix = new double[noColumns][noRows];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				matrix[i][j] = (r.nextDouble() * 10);
			}
		}
	}
	
	public double[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(double[][] matrix) {
		this.matrix = matrix;
	}

	public int getNoColumns() {
		return matrix.length;
	}
	
	public double calculate() {
		double result = 0.;
		for(int i = 0; i < matrix.length; i++) {
			result += (doComplexMathForColumn(i));
		}
		return result;
	}

	public double doComplexMathForColumn(int i) {
		double result = 1.;
		for (int j = 0; j < matrix[i].length; j++) {
			double log = Math.log(matrix[i][j]);
			double cos = Math.cos(log);
			result += Math.sqrt(Math.abs(cos));
		}
		return result;
	}
}
