package com.dt.learning.service;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dnnt9 on 2017/4/18.
 */
public class CalculatorTest {

    Calculator calculator;

    @Before
    public void setUp() throws Exception {
        calculator = new Calculator();
    }

    @Test
    public void sum() throws Exception {
        assertEquals(6,calculator.sum(1,5),0);
    }

}