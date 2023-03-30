/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.utility;

import com.etzgh.xportal.model.EmiConfig;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author sunkwa-arthur
 */
public class Config {

    private static final Properties prop = new Properties();
    private static final Properties gipProp = new Properties();
    private static final Logger log = Logger.getLogger(Config.class.getName());
    private static final Map<String, EmiConfig> bankEmiConfigMap = new HashMap<>();

    InputStream is;

    static {
        try {
            InputStream is = new FileInputStream(new File("cfg" + File.separator + "xportal.properties"));
            prop.load(is);

            InputStream id = new FileInputStream(new File("cfg" + File.separator + "etzgipcodemapping.properties"));
            gipProp.load(id);

            String emi_enabled = prop.getProperty("EMI_ENABLED_BANKS", "").trim();

            if (!emi_enabled.isEmpty()) {

                List<String> bankList = Arrays.asList(emi_enabled.split("\\s*,\\s*"));
                log.info("EMI_ENABLED_BANKS::: " + bankList);

                bankList.stream().forEach(f -> {
                    EmiConfig ec = new EmiConfig();;
                    ec.setISSUER_ID((f.equals("006") ? gipProp.getProperty("ETZ_GIP_CODE") : gipProp.getProperty("BANK_" + f)));
                    ec.setCHARGING_PARTY_ID(prop.getProperty(f + "_CHARGING_PARTY_ID"));
                    ec.setAPI_KEY(prop.getProperty(ec.getCHARGING_PARTY_ID() + "_API_KEY"));
                    ec.setKEYSTORE_LOC(prop.getProperty(ec.getCHARGING_PARTY_ID() + "_KEYSTORE_LOC"));
                    ec.setKEYSTORE_PASSWORD(prop.getProperty(ec.getCHARGING_PARTY_ID() + "_KEYSTORE_PASSWORD"));
                    ec.setTRUSTSTORE_LOC(prop.getProperty(ec.getCHARGING_PARTY_ID() + "_TRUSTSTORE_LOC"));
                    ec.setTRUSTSTORE_PASSWORD(prop.getProperty(ec.getCHARGING_PARTY_ID() + "_TRUSTSTORE_PASSWORD"));

                    bankEmiConfigMap.put(f, ec);
                });

            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    public Map<String, EmiConfig> getEmiConfig() {
        return bankEmiConfigMap;
    }

    public String getProperty(String property) {
        return prop.getProperty(property, "");
    }

    public String getProperty(String property, String def) {
        return prop.getProperty(property, def);
    }

    public String getGipProperty(String property) {
        return gipProp.getProperty(property, "");
    }

    public String getGipProperty(String property, String def) {
        return gipProp.getProperty(property, def);
    }
}
