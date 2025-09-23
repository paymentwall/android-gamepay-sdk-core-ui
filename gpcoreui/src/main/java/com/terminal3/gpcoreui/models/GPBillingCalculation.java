package com.terminal3.gpcoreui.models;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class GPBillingCalculation {
    private BigDecimal subtotal;
    private BigDecimal tax;
    private double taxRate;
    private BigDecimal discount;
    private BigDecimal total;
    private String taxMessage;

    public GPBillingCalculation() {
        this.subtotal = BigDecimal.ZERO;
        this.tax = BigDecimal.ZERO;
        this.taxRate = 0.0;
        this.discount = BigDecimal.ZERO;
        this.total = BigDecimal.ZERO;
        this.taxMessage = "";
    }

    public GPBillingCalculation(BigDecimal subtotal, BigDecimal tax, BigDecimal discount, BigDecimal total, double taxRate) {
        this.subtotal = subtotal;
        this.tax = tax;
        this.discount = discount;
        this.total = total;
        this.taxMessage = "";
        this.taxRate = taxRate;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public String getTaxMessage() {
        return taxMessage;
    }

    public void setTaxMessage(String taxMessage) {
        this.taxMessage = taxMessage;
    }

    public String getFormattedSubtotal() {
        return formatAmount(subtotal);
    }

    public String getFormattedTax() {
        return formatAmount(tax);
    }

    public String getFormattedDiscount() {
        return formatAmount(discount);
    }

    public String getFormattedTotal() {
        return formatAmount(total);
    }

    public String getDisplayTaxLabel() {
        if (taxRate > 0) {
            return formatTaxWithRate(taxRate);
        }
        return "Tax";
    }

    public String formatTaxWithRate(double rate) {
        // Create DecimalFormat with comma as decimal separator
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setDecimalSeparator('.');

        // Format to maximum 2 decimal places, removing trailing zeros
        DecimalFormat df = new DecimalFormat("#.##", symbols);
        String formattedRate = df.format(rate);

        return "Tax (" + formattedRate + " %)";
    }

    private String formatAmount(BigDecimal amount) {
        if (amount == null) {
            return "$0.00";
        }
        return String.format("$%.2f", amount);
    }
}