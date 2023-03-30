package com.etzgh.xportal.dao;

import com.etzgh.xportal.utility.DbHibernate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;
import java.util.stream.Collectors;
import javax.persistence.Query;
import org.hibernate.Session;

/**
 *
 * @author sunkwa-arthur
 */
public final class MerchantCodes {

    Query q;

    private static final HashMap<String, String> map = new HashMap<String, String>();

    protected static final HashMap<String, String> merchantCodeMap = new HashMap<String, String>();

    protected static String merchantCodes = "";

    private static final Logger log = Logger.getLogger(MerchantCodes.class.getName());

    public MerchantCodes() {
        init();
    }

    public void init() {

        try {
            log.info("LOADING XPORTAL MERCHANT CODES");
            fetchMerchantCodes();

        } catch (Exception ex) {
            log.info("COULD NOT LOAD SETTINGS");
            log.error(ex.getMessage(), ex);
        }

    }

    public void destroy() {
        merchantCodeMap.clear();
    }

    public void fetchMerchantCodes() {

        List<Object[]> channelRs = new ArrayList<>();
        Query qry;
        try {
            String channelQry = "select default_card merchant_code, alias from ecarddb..e_b2bmerchant where alias is not null and default_card is not null";

            Session session = DbHibernate.getSession("40.4SYBASE");
            try {
                qry = session.createNativeQuery(channelQry);
                channelRs = qry.getResultList();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            } finally {
                if (session != null) {
                    session.close();
                }
            }

            for (Object[] record : channelRs) {
                try {
                    if (record[0] != null && record[1] != null) {
                        merchantCodeMap.put(record[0].toString(), "" + record[1].toString());
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }

            channelQry = "SELECT merchant_code, alias FROM mobiledb.m_b2b_merchants where merchant_code is not null and alias is not null";

            log.info("MerchantCodes query " + channelQry);
            session = DbHibernate.getSession("MOBILEDBMYSQL");
            try {
                qry = session.createNativeQuery(channelQry);
                channelRs = qry.getResultList();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            } finally {
                if (session != null) {
                    session.close();
                }
            }

            for (Object[] record : channelRs) {
                try {
                    if (record[0] != null && record[1] != null) {
                        merchantCodeMap.put(record[0].toString(), "" + record[1].toString());
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        } catch (Exception e) {
            log.info("MechantCodes ERROR3 ::: " + e);
            log.error(e.getMessage(), e);
        }

        log.info("MERCHANT CODES ::: " + merchantCodeMap.size());

        try {
            merchantCodes = merchantCodeMap.entrySet().stream().map(f -> "'" + f.getKey() + "'")
                    .collect((Collectors.joining(",")));
        } catch (Exception t) {
            log.error(t.getMessage(), t);
        }

        merchantCodes += ",'0067510000010000'";

        log.info("Merc Codes=> " + merchantCodes);
    }

    public static void addSettings(String key, String value) {
        log.info("PUTTING SETTINGS: " + key + " : " + value);

        map.put(key, value);
    }

    public String getSetting(String key) {

        return map.getOrDefault(key, "null");
    }

    public HashMap<String, String> getMerchantCodes() {
        return merchantCodeMap;
    }

    public String getMerchant(String merchantCode) {
        return merchantCodeMap.getOrDefault(merchantCode, "null");
    }

    public String getMerchantCodesString() {
        return merchantCodes;
    }
}
