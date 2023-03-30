package com.etz.vasgate.dao;

public interface VasgateDAO {

    public String doTopup(String vastype, String destacct, double amount, String reference);

    public String payBill(String vastype, String destacct, double amount, String reference, String otherInfo);

    public void verifyBiller(String vastype, String account, String otherInfo, String reference);
}
