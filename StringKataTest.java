package com.hasid.tddkata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class StringCalculatorTest 
{
    private void testAdd(final String input, final int expectedOutput) {
        final StringCalculator calculator = new StringCalculator();
        final int actualOutput = calculator.add(input);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void emptyStringShouldReturnZero()
    {
        testAdd("", 0);
    }

    @Test
    public void singleNumberShouldReturnTheNumberItself()
    {
        testAdd("5", 5);
    }

    @Test
    public void twoNumbersShouldReturnTheirSum() {
        testAdd("1,2", 3);
    }

    @Test
    public void onlySpacesShouldReturnZero() {
        testAdd("   ", 0);
    }

    @Test
    public void singleNumberWithExtraSpacesShouldReturnTheNumber() {
        testAdd("  1 ", 1);
    }

    @Test
    public void twoNumbersWithSpacesShouldReturnTheirSum() {
        testAdd(" 1 , 2 ", 3);
    }

    @Test
    public void moreThanTwoNumbersShouldReturnTheirSum() {
        testAdd("1,1,1", 3);
        testAdd("1,2,3", 6);
        testAdd("1,1,1,1", 4);
    }

    @Test
    public void numbersSeparatedByNewLineShouldReturnTheirSum() {
        testAdd("1\n2\n3", 6);
        testAdd("1\n2,3", 6);
    }

    @Test
    public void customDelimiterInFirstLineShouldBeHonoured() {
        testAdd("//;\n1;2", 3);
        testAdd("//;\n1;2;3", 6);
        testAdd("//*\n1*2", 3);
        testAdd("//*\n1*2*3", 6);
    }

    @Test
    public void negativeNumbersShouldThrowException() {
        final StringCalculator calculator = new StringCalculator();
        try {
            calculator.add("1,-2,3,-4");
            assertTrue(false);
        } catch(Exception e) {
            // expected to fail
            assertTrue(e.getMessage().contains("-2") && e.getMessage().contains("-4"));
        }
    }

    @Test
    public void numbersGreaterThan1000ShouldBeIgnored() {
        testAdd("1001,2", 2);
        testAdd("//*\n1002*5", 5);
    }

    @Test
    public void anyLengthSeparatorShouldBeAllowed() {
        testAdd("//[***]\n1***2***3", 6);
        testAdd("//[...]\n1...2...3", 6);
    }

    @Test
    public void multipleSeparatorsShouldBeAllowed() {
        testAdd("//[*][%]\n1*2%3", 6);
        testAdd("//[**][%%]\n1**2%%3", 6);
    }

}