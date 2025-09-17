package com.terminal3.gpcoreui.models;

import java.math.BigDecimal;

public class GPBillingCalculation {
    private BigDecimal subtotal;
    private BigDecimal tax;
    private BigDecimal discount;
    private BigDecimal total;
    private String taxMessage;

    public GPBillingCalculation() {
        this.subtotal = BigDecimal.ZERO;
        this.tax = BigDecimal.ZERO;
        this.discount = BigDecimal.ZERO;
        this.total = BigDecimal.ZERO;
        this.taxMessage = "";
    }

    public GPBillingCalculation(BigDecimal subtotal, BigDecimal tax, BigDecimal discount, BigDecimal total) {
        this.subtotal = subtotal;
        this.tax = tax;
        this.discount = discount;
        this.total = total;
        this.taxMessage = "";
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

    private String formatAmount(BigDecimal amount) {
        if (amount == null) {
            return "$0.00";
        }
        return String.format("$%.2f", amount);
    }
}