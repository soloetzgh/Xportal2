package com.etzgh.xportal.cdi;

import com.etzgh.xportal.model.CorporatePay;
import com.google.gson.Gson;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import javax.enterprise.context.SessionScoped;

/**
 * @author Eugene
 */
@SessionScoped
public class CorporatePayTransactions implements Serializable {

    List<CorporatePay> corporateTransactions;
    BigDecimal totalAmount;
    BigDecimal totalSuccessfulAmount;
    BigDecimal totalFailedAmount;
    BigDecimal totalReversedAmount;

    public List<CorporatePay> getCorporateTransactions() {
        return corporateTransactions;
    }

    public void setCorporateTransactions(List<CorporatePay> corporateTransactions) {
        this.corporateTransactions = corporateTransactions;
    }

    public String getTotalAmount() {
        this.totalAmount = corporateTransactions.stream().map(f -> f.getAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
        return new Gson().toJson(currencyFormat(this.totalAmount));
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalSuccessfulAmount() {
        this.totalSuccessfulAmount = corporateTransactions.stream().filter(f -> f.getStatus().equalsIgnoreCase("successful")).map(f -> f.getAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
        return currencyFormat(this.totalSuccessfulAmount);

    }

    public void setTotalSuccessfulAmount(BigDecimal totalSuccessfulAmount) {
        this.totalSuccessfulAmount = totalSuccessfulAmount;
    }

    public String getTotalFailedAmount() {
        this.totalFailedAmount = corporateTransactions.stream().filter(f -> f.getStatus().equalsIgnoreCase("failed")).map(f -> f.getAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
        return currencyFormat(this.totalFailedAmount);

    }

    public void setTotalFailedAmount(BigDecimal totalFailedAmount) {
        this.totalFailedAmount = totalFailedAmount;
    }

    public String getTotalReversedAmount() {
        this.totalReversedAmount = corporateTransactions.stream().filter(f -> f.getStatus().equalsIgnoreCase("reversed")).map(f -> f.getAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
        return currencyFormat(this.totalReversedAmount);

    }

    public void setTotalReversedAmount(BigDecimal totalReversedAmount) {
        this.totalReversedAmount = totalReversedAmount;
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
        if (toIndex > this.corporateTransactions.size()) {
            toIndex = this.corporateTransactions.size();
        }

        return new Gson().toJson(this.corporateTransactions.subList(offset, toIndex));
    }

    public String getTotalCount() {
        return new Gson().toJson(this.corporateTransactions.size());
    }

    public String currencyFormat(BigDecimal n) {

        return NumberFormat.getNumberInstance().format(n);
    }
}
