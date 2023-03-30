package com.etzgh.xportal.dao;

import com.etzgh.xportal.model.ApiPostData;
import com.etzgh.xportal.model.BOGswitch;
import com.etzgh.xportal.model.KYCswitch;
import com.etzgh.xportal.utility.DbHibernate;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author sunkwa-arthur
 */
public class BogDao {

    private static final Logger log = Logger.getLogger(BogDao.class.getName());
    private static final String ERROR = "ERROR";
    Query q;

    public List<BOGswitch> getBOGswitchTransactions(String jsonString) {

        Gson j = new Gson();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
        List<BOGswitch> switchRecords = new ArrayList<>();

        String start_dt = apiData.getStartDate();

        String end_dt = apiData.getEndDate();

        String type_id = apiData.getType_id();

        if (!(type_id.contains("[0]") || type_id.contains("[1]"))) {
            return switchRecords;
        }

        String qry = "select title, amount from "
                + "(select 'TOTAL REGISTRED E-MONEY' title , count(*) amount  from ECARDDB.e_CARDHOLDER  "
                + "where online_balance > 0 "
                + "and (company not like 'SusuTxT' OR company is null) and prime_status is null and card_account not like '%1111111111%' Union all "
                + "select 'VALUE OF  REGISTRED E-MONEY CUSTOMERS' title, sum(online_balance) amount from ECARDDB.e_CARDHOLDER where FIRSTNAME is not null and  FIRSTNAME <> '' "
                + "and  online_balance >= 0 "
                + "and (company not like 'SusuTxT' OR company is null) and prime_status is null and card_account not like '%1111111111%' Union all "
                + "select 'ACTIVE E-MONEY CUSTOMERS' title , count(distinct(card_num)) amount from ECARDDB.e_transaction  "
                + "where trans_date between  :start_dt and :end_dt "
                + "and card_num not in (select card_num from ecarddb.e_cardholder where prime_status ='7' and (company like 'SusuTxT' OR company is not null) and card_account like '%1111111111%') Union all "
                + "select 'TOTAL VOLUME OF E-MONEY TRANSACTION FOR THE MONTH' title, count(*) amount from ECARDDB.e_transaction  "
                + "where trans_date between :start_dt and :end_dt "
                + "and card_num not in (select card_num from ecarddb.e_cardholder where prime_status ='7' and (company like 'SusuTxT' OR company is not null) and card_account like '%1111111111%') Union all "
                + "select 'VALUE OF E-MONEY FOR THE MONTH' title, "
                + "sum(trans_amount) amount from ecarddb.e_transaction where trans_date between :start_dt and :end_dt  "
                + "and card_num not in (select card_num from ecarddb.e_cardholder where prime_status ='7' and (company like 'SusuTxT' OR company is not null) and card_account like '%1111111111%') Union all "
                + "select 'TOTAL REGISTERED CUSTOMERS HAVING MINIMUM KYC CUMULATIVE' title, count(*) amount from "
                + "ecarddb.e_cardholder a where  online_balance >  0 and  online_balance <= 2000 "
                + "and (company not like 'SusuTxT' OR company is null) and prime_status is null and card_account not like '%1111111111%' Union all "
                + "select 'VALUE OF REGISTERED CUSTOMERS HAVING MINIMUM KYC CUMULATIVE' title, sum(online_balance) amount "
                + "from ecarddb.e_cardholder a where  online_balance > 0 and  online_balance <= 2000 "
                + "and (company not like 'SusuTxT' OR company is null) and prime_status is null and card_account not like '%1111111111%' Union all "
                + "select 'TOTAL REGISTERED CUSTOMERS HAVING MEDIUM KYC CUMULATIVE ' title, "
                + "count(*) amount from ecarddb.e_cardholder a where online_balance >  2000 and  online_balance <= 15000 "
                + "and (company not like 'SusuTxT' OR company is null) and prime_status is null and card_account not like '%1111111111%' Union all "
                + "select 'VALUE OF REGISTERED CUSTOMERS HAVING MEDIUM KYC CUMULATIVE ' title, "
                + "sum(online_balance) amount from ecarddb.e_cardholder a where online_balance >  2000 and  online_balance <= 15000 "
                + "and (company not like 'SusuTxT' OR company is null) and prime_status is null and card_account not like '%1111111111%' Union all "
                + "select 'TOTAL REGISTERED CUSTOMERS HAVING ENHANCED KYC CUMULATIVE ' title, "
                + "count(*) amount from ecarddb.e_cardholder a where online_balance >  15000 "
                + "and (company not like 'SusuTxT' OR company is null) and prime_status is null and card_account not like '%1111111111%' Union all "
                + "select 'VALUE OF REGISTERED CUSTOMERS HAVING ENHANCED KYC CUMULATIVE ' title, sum(online_balance) amount "
                + "from ecarddb.e_cardholder a  where online_balance >= 15000 "
                + "and (company not like 'SusuTxT' OR company is null) and prime_status is null and card_account not like '%1111111111%' Union all "
                + "select 'TOTAL REGISTERED ACTIVE CUSTOMERS HAVING MINIMUM KYC' title,count(distinct(a.card_num)) amount "
                + "from ecarddb.e_cardholder a , ecarddb.e_transaction b  where   a.card_num = b.card_num  and  a.online_balance > 0  "
                + "and a.online_balance <= 2000 and  b.trans_date  between  :start_dt and :end_dt "
                + "and (a.company not like 'SusuTxT' OR a.company is null) and a.prime_status is null and a.card_account not like '%1111111111%' Union all "
                + "select 'VALUE OF REGISTERED ACTIVE CUSTOMERS HAVING MINIMUM KYC' title, sum(b.trans_amount) amount  from "
                + "ecarddb.e_cardholder a , ecarddb.e_transaction b   where   a.card_num = b.card_num  and  a.online_balance > 0  "
                + "and a.online_balance <= 2000 and  b.trans_date  between  :start_dt and :end_dt  "
                + "and (a.company not like 'SusuTxT' OR a.company is null) and a.prime_status is null and a.card_account not like '%1111111111%' Union all "
                + "select 'TOTAL REGISTERED ACTIVE CUSTOMERS HAVING MEDIUM KYC' title, count(distinct(a.card_num)) amount from "
                + "ecarddb.e_cardholder a , ecarddb.e_transaction b where  a.card_num = b.card_num  and  a.online_balance > 2000  and a.online_balance <= 15000 "
                + "and b.trans_date  between :start_dt and :end_dt "
                + "and (a.company not like 'SusuTxT' OR a.company is null) and a.prime_status is null and a.card_account not like '%1111111111%' Union all "
                + "select 'VALUE OF REGISTERED ACTIVE CUSTOMERS HAVING MEDIUM KYC' title, "
                + "sum(b.trans_amount ) from ecarddb.e_cardholder a , ecarddb.e_transaction b where  a.card_num = b.card_num  and  a.online_balance > 2000  "
                + "and a.online_balance <= 15000 and  b.trans_date  between :start_dt and :end_dt "
                + "and (a.company not like 'SusuTxT' OR a.company is null) and a.prime_status is null and a.card_account not like '%1111111111%' Union all "
                + "select 'TOTAL REGISTERED ACTIVE CUSTOMERS HAVING ENHANCED KYC' title,count(distinct(a.card_num)) amount from ecarddb.e_cardholder a , "
                + "ecarddb.e_transaction b where  a.card_num = b.card_num  and  a.online_balance > 15000 and  "
                + "b.trans_date between :start_dt and :end_dt "
                + "and (a.company not like 'SusuTxT' OR a.company is null) and a.prime_status is null and a.card_account not like '%1111111111%' Union all "
                + "select 'VALUE OF REGISTERED ACTIVE CUSTOMERS HAVING ENHANCED KYC' title, sum(b.trans_amount) amount from "
                + "ecarddb.e_cardholder a , ecarddb.e_transaction b  where  a.card_num = b.card_num "
                + "and a.online_balance > 15000 and b.trans_date between :start_dt and :end_dt "
                + "and (a.company not like 'SusuTxT' OR a.company is null) and a.prime_status is null and a.card_account not like '%1111111111%' Union all "
                + "select 'NUMBER  DORMANT CUSTOMERS HAVING MINIMUM KYC' title, count(card_num) amount from "
                + "e_cardholder where DATE_SUB(online_date, INTERVAL 6 MONTH) < DATE_SUB(now(),INTERVAL DAYOFMONTH(now())-1 DAY)  and online_balance > 0 "
                + "and online_balance <=2000 "
                + "and (company not like 'SusuTxT' OR company is null) and prime_status is null and card_account not like '%1111111111%' Union all "
                + "select 'VALUE DORMANT CUSTOMERS HAVING MINIMUM KYC' title, "
                + "sum(online_balance ) amount from ecarddb.e_cardholder where  DATE_SUB(online_date, INTERVAL 6 MONTH) < DATE_SUB(now(),INTERVAL DAYOFMONTH(now())-1 DAY) and online_balance > 0  "
                + "and online_balance <= 2000 "
                + "and (company not like 'SusuTxT' OR company is null) and prime_status is null and card_account not like '%1111111111%' Union all "
                + "select 'NUMBER  DORMANT CUSTOMERS HAVING MEDIUM KYC' title, "
                + "count(card_num) amount from ecarddb.e_cardholder where  DATE_SUB(online_date, INTERVAL 6 MONTH) < DATE_SUB(now(),INTERVAL DAYOFMONTH(now())-1 DAY) and online_balance > 2000  "
                + "and online_balance <= 15000 "
                + "and (company not like 'SusuTxT' OR company is null) and prime_status is null and card_account not like '%1111111111%' Union all "
                + "select 'VALUE DORMANT CUSTOMERS HAVING MEDIUM KYC' title, "
                + "sum(online_balance ) amount from ecarddb.e_cardholder where DATE_SUB(online_date, INTERVAL 6 MONTH) < DATE_SUB(now(),INTERVAL DAYOFMONTH(now())-1 DAY) and online_balance > 2000  "
                + "and online_balance <= 15000 "
                + "and (company not like 'SusuTxT' OR company is null) and prime_status is null and card_account not like '%1111111111%' Union all "
                + "select 'NUMBER DORMANT CUSTOMERS HAVING ENHANCED KYC' title, "
                + "count(card_num) amount from ecarddb.e_cardholder where  DATE_SUB(online_date, INTERVAL 6 MONTH) < DATE_SUB(now(),INTERVAL DAYOFMONTH(now())-1 DAY) and online_balance > 10001  "
                + "and online_balance > 15000 "
                + "and (company not like 'SusuTxT' OR company is null) and prime_status is null and card_account not like '%1111111111%' Union all "
                + "select 'VALUE DORMANT CUSTOMERS HAVING ENHANCED KYC' title, "
                + "sum(online_balance) amount from ecarddb.e_cardholder where DATE_SUB(online_date, INTERVAL 6 MONTH) < DATE_SUB(now(),INTERVAL DAYOFMONTH(now())-1 DAY) and online_balance > 15000 "
                + "and (company not like 'SusuTxT' OR company is null) and prime_status is null and card_account not like '%1111111111%' Union all "
                + "select 'TOTAL BALANCES ON ALL CARDS' title, "
                + "sum(online_balance) amount from ecarddb.e_cardholder where   online_balance > 0 "
                + "and (company not like 'SusuTxT' OR company is null) and prime_status is null and card_account not like '%1111111111%' Union all "
                + "select  'VOLUME CASH IN ' title, count(*) amount from ecarddb.e_transaction where  trans_code ='D' "
                + "and TRANS_DATE between :start_dt and :end_dt "
                + "and card_num not in (select card_num from ecarddb.e_cardholder where (prime_status ='7' or card_account like '%1111111111%' or company  like 'SusuTxT')) Union all "
                + "select  'AMOUNT CASH IN' title,  sum(trans_amount) amount from "
                + "ecarddb.e_transaction where trans_code ='D' and TRANS_DATE between :start_dt and :end_dt "
                + "and card_num not in (select card_num from ecarddb.e_cardholder where (prime_status ='7' or card_account like '%1111111111%' or company  like 'SusuTxT')) Union all "
                + "select 'VOLUME CASH OUT' title,  count(*) amount from ecarddb.e_transaction where  trans_code ='W' "
                + "and  TRANS_DATE between :start_dt and :end_dt "
                + "and card_num not in (select card_num from ecarddb.e_cardholder where (prime_status ='7' or card_account like '%1111111111%' or company  like 'SusuTxT')) Union all "
                + "select 'AMOUNT CASH OUT ' title, sum(trans_amount) amount from ecarddb.e_transaction where trans_code ='W' and "
                + "TRANS_DATE between :start_dt and :end_dt "
                + "and card_num not in (select card_num from ecarddb.e_cardholder where (prime_status ='7' or card_account like '%1111111111%' or company  like 'SusuTxT')) Union all "
                + "select 'VOLUME WALLET TO BANK' title, count(*) amount from "
                + "ecarddb.e_transaction where trans_code ='T' and  TRANS_DATE between :start_dt and :end_dt  and length(merchant_code) > 16 "
                + "and card_num not in (select card_num from ecarddb.e_cardholder where (prime_status ='7' or card_account like '%1111111111%' or company  like 'SusuTxT')) Union all "
                + "select 'AMOUNT WALLET TO BANK' title, sum(trans_amount) amount from e_transaction  where   trans_code ='T' and  "
                + "TRANS_DATE between :start_dt and :end_dt and length(merchant_code) >16 "
                + "and card_num not in (select card_num from ecarddb.e_cardholder where (prime_status ='7' or card_account like '%1111111111%' or company  like 'SusuTxT')) Union all "
                + "select 'TOTAL AMOUNT BANK TO WALLET' title, "
                + "sum(trans_amount) amount from e_transaction where trans_code ='T' and  TRANS_DATE BETWEEN :start_dt and :end_dt  and "
                + "substring(card_num,1,6) not in (select issuer_branch from ecarddb.e_exfrontendinterface) "
                + "and card_num not in (select card_num from ecarddb.e_cardholder where (prime_status ='7' or card_account like '%1111111111%' or company  like 'SusuTxT')) Union all "
                + "select 'VOLUME BANK TO WALLET' title, "
                + "count(*) amount from ecarddb.e_transaction  where  trans_code ='T' and  TRANS_DATE BETWEEN :start_dt and :end_dt  and "
                + "substring(card_num,1,6) not in (select issuer_branch from ecarddb.e_exfrontendinterface) "
                + "and card_num not in (select card_num from ecarddb.e_cardholder where (prime_status ='7' or card_account like '%1111111111%' or company  like 'SusuTxT')) Union all "
                + "select 'TOTAL AMOUNT PERSON TO PERSON ' title, sum(trans_amount) amount from ecarddb.e_transaction "
                + "where  trans_code ='T' and  TRANS_DATE BETWEEN :start_dt and :end_dt  and substring(card_num,1,6) in "
                + "(select issuer_branch from ecarddb.e_exfrontendinterface) "
                + "and card_num not in (select card_num from ecarddb.e_cardholder where (prime_status ='7' or card_account like '%1111111111%' or company  like 'SusuTxT')) Union all "
                + "select 'VOLUME PERSON TO PERSON ' title, count(*) amount "
                + "from ecarddb.e_transaction where trans_code ='T' and  TRANS_DATE BETWEEN :start_dt and :end_dt  and substring(card_num,1,6) "
                + "in (select issuer_branch from ecarddb.e_exfrontendinterface) "
                + "and card_num not in (select card_num from ecarddb.e_cardholder where (prime_status ='7' or card_account like '%1111111111%' or company  like 'SusuTxT')) Union all "
                + "select 'AMOUNT PERSON TO BUSINESS ' title, "
                + "sum(trans_amount) amount from ecarddb.e_transaction where trans_code ='P' and trans_date between :start_dt and :end_dt  "
                + "and card_num not in (select card_num from ecarddb.e_cardholder where (prime_status ='7' or card_account like '%1111111111%' or company  like 'SusuTxT')) Union all "
                + "select 'VOLUME PERSON TO BUSINESS' title, count(*) amount from ecarddb.e_transaction where trans_code ='P' and "
                + "trans_date between :start_dt and :end_dt "
                + "and card_num not in (select card_num from ecarddb.e_cardholder where (prime_status ='7' or card_account like '%1111111111%' or company  like 'SusuTxT')) Union all "
                + "select 'OVER THE COUNTER (LOADING) PERSON TO PERSON' title, "
                + "count(*) amount from ecarddb.e_transaction where trans_code ='D' and trans_date between :start_dt and :end_dt "
                + "and card_num not in (select card_num from ecarddb.e_cardholder where (prime_status ='7' or card_account like '%1111111111%' or company  like 'SusuTxT')) "
                + ") as t1 ";

        Session session = DbHibernate.getSession("40.17MYSQL");
        System.out.println("QUERY TO RUN: " + qry);
        List<Object[]> records = new ArrayList<>();
        try {

            q = session.createNativeQuery(qry);
            q.setParameter("start_dt", start_dt);
            q.setParameter("end_dt", end_dt);
            records = q.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (Exception e) {
                    log.error(ERROR, e);
                }
            }
        }

        for (Object[] record : records) {
            BOGswitch bog = new BOGswitch();
            try {
                bog.setTitle(checkNull(record[0]));
                bog.setAmount(checkNull(record[1]));

            } catch (Exception e) {
                log.error(ERROR, e);
            }
            switchRecords.add(bog);
        }

        return switchRecords;
    }

    public List<KYCswitch> getKYCswitchRecords(String jsonString) {

        Gson j = new Gson();
        ApiPostData apiData = j.fromJson(jsonString, ApiPostData.class);
        List<KYCswitch> kycswitchRecords = new ArrayList<>();
        String transType = apiData.getTransType();

        String type_id = apiData.getType_id();

        if (!(type_id.contains("[0]") || type_id.contains("[1]"))) {
            return kycswitchRecords;
        }

        String qry = "";

        switch (transType.toLowerCase()) {

            case "minimum":
                qry = "select card_num ,phone ,firstname ,lastname ,online_balance from ecarddb.e_cardholder where 1=1 "
                        + " and online_balance > 0.00 and online_balance <= 2000.00 "
                        + " and control_id <> '002' and firstname not like '%holding%' and lastname not like '%fundgate%' "
                        + " and card_num not like '%___88888%' and card_num not in (select card_num from ecarddb.e_cardholder where prime_status ='7') order by online_balance desc";
                break;
            case "medium":
                qry = "select card_num ,phone ,firstname ,lastname ,online_balance from ecarddb.e_cardholder where 1=1 "
                        + " and online_balance > 2000.00 and online_balance <= 15000.00 "
                        + " and control_id <> '002' and firstname not like '%holding%' and lastname not like '%fundgate%' "
                        + " and card_num not like '%___88888%' and card_num not in (select card_num from ecarddb.e_cardholder where prime_status ='7') order by online_balance desc";
                break;
            case "enhanced":

                qry = "select card_num ,phone ,firstname ,lastname ,online_balance from ecarddb.e_cardholder where 1=1 "
                        + " and online_balance > 15000.00 "
                        + " and control_id <> '002' and firstname not like '%holding%' and lastname not like '%fundgate%' "
                        + " and card_num not like '%___88888%' and card_num not in (select card_num from ecarddb.e_cardholder where prime_status ='7') order by online_balance desc";
                break;
            default:
                break;
        }

        if (!qry.isEmpty()) {
            Session session = DbHibernate.getSession("40.17MYSQL");
            List<Object[]> records = new ArrayList<>();

            q = session.createNativeQuery(qry);
            records = q.getResultList();

            for (Object[] record : records) {
                KYCswitch ks = new KYCswitch();

                try {
                    ks.setCard_num(checkNull(record[0]));
                    ks.setPhone(checkNull(record[1]));
                    ks.setFirstname(checkNull(record[2]));
                    ks.setLastname(checkNull(record[3]));
                    ks.setOnline_balance(checkNull(record[4]));

                } catch (Exception e) {
                    log.error(ERROR, e);
                }
                kycswitchRecords.add(ks);
            }
        }

        return kycswitchRecords;
    }

    protected String checkNull(Object Data) {
        if (Data != null && !Data.equals("")) {
            return Data.toString();
        }
        return "0.00";
    }

    public static String rightPadding(String str, int num) {
        for (int i = 0; i < num; i++) {
            str += ",";
        }
        return str;

    }
}
