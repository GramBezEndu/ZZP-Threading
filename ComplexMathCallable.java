package com.tt.concurrent.callable;

import jdk.jshell.spi.ExecutionControl;

import java.util.concurrent.Callable;

public class ComplexMathCallable extends ComplexMath implements Callable<Double> {
    public ComplexMathCallable(int noRows, int noColumns) {
        super(noRows, noColumns);
    }

    @Override
    public Double call() throws Exception {
        return calculate();
    }
}
