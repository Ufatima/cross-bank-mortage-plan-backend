package com.crosskey.codingTest.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Prospect {
    @NotNull
    @Size(min = 3)
    private final String customerName;
    @NotNull
    private final double totalLoan;
    @NotNull
    private final double interest;
    @NotNull
    private final Integer years;

    private double result;

    public Prospect(String customerName, double totalLoan, double interest, Integer years) {
        this.customerName = customerName;
        this.totalLoan = totalLoan;
        this.interest = interest;
        this.years = years;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getTotalLoan() {
        return totalLoan;
    }

    public double getInterest() {
        return interest;
    }

    public Integer getYears() {
        return years;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return customerName + " wants to borrow " + totalLoan +
                " € for a period of " + years + " years and pay " + result + " € each month";
    }
}
