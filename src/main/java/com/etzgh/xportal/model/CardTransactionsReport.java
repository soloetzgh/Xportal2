/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.model;

/**
 *
 * @author sunkwa-arthur
 */
import com.google.gson.Gson;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class CardTransactionsReport implements Serializable {

    List<CardTransReport> CardTransactionsReport = new ArrayList<>();
    BigDecimal totalCreditAmount = new BigDecimal(BigInteger.ZERO);
    BigDecimal totalDebitAmount = new BigDecimal(BigInteger.ZERO);
    String cardName = "";
    String cardNumber = "";

    public List<CardTransReport> getCardTransactionsReport() {
        return CardTransactionsReport;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCardTransactionsReport(List<CardTransReport> CardTransactionsReport) {
        this.CardTransactionsReport = CardTransactionsReport;
    }

    public String getTotalCreditAmount() {
        this.totalCreditAmount = this.CardTransactionsReport.stream().map(f -> f.getCR()).reduce(BigDecimal.ZERO, BigDecimal::add);
        return  currencyFormat(this.totalCreditAmount);
    }

    public void setTotalCreditAmount(BigDecimal totalCreditAmount) {
        this.totalCreditAmount = totalCreditAmount;
    }

    public String getTotalDebitAmount() {
        this.totalDebitAmount = this.CardTransactionsReport.stream().map(f -> f.getDR()).reduce(BigDecimal.ZERO, BigDecimal::add);
        return currencyFormat(this.totalDebitAmount);
    }

    public void setTotalDebitAmount(BigDecimal totalDebitAmount) {
        this.totalDebitAmount = totalDebitAmount;
    }

    public String paginate(int pageNumber, int rowsPerPage) {
        int offset = 0;

        if (pageNumber < 1) {
            pageNumber = 1;
        }

        if (pageNumber > 1) {
            offset = (pageNumber - 1) * rowsPerPage;
        }

        int toIndex = offset + rowsPerPage;
        if (toIndex > this.CardTransactionsReport.size()) {
            toIndex = this.CardTransactionsReport.size();
        }

        return new Gson().toJson(this.CardTransactionsReport.subList(offset, toIndex));
    }

    public String getTotalCount() {
        return new Gson().toJson(this.CardTransactionsReport.size());
    }

    public static String currencyFormat(BigDecimal n) {

        return NumberFormat.getNumberInstance().format(n);
    }
}
