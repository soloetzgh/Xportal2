package com.etz.gh.sw.utils;

import com.etzgh.xportal.controller.PortalSettings;
import org.apache.commons.lang.StringUtils;

public class Utils {

    public static final String BASE_URL = StringUtils.substringBeforeLast(new PortalSettings().getSetting("sw_endpoint"), ":");

    public static final String DEBIT_PW_URL = BASE_URL + "/api/h/debit";

    public static final String REFUND_URL = BASE_URL + "/api/h/refund";

    public static final String FUND_URL = BASE_URL + "/api/h/fund";

    public static final String CSUSER_URL = BASE_URL + "/api/h/csuser";

    public static final String CPWALLET_URL = BASE_URL + "/api/h/cpwallet";

    public static final String CSKEY_URL = BASE_URL + "/api/h/cskey";

    public static final String GETSTKNS_URL = BASE_URL + "/api/h/getstkns";

    public static final String APRVSUSR_URL = BASE_URL + "/api/h/aprvsusr";

    public static final String PINAUTH_URL = BASE_URL + "/api/h/pin";

    public static final String CWALLETPIN_URL = BASE_URL + "/api/h/cppin";

    public static final String FETCHOTP_URL = BASE_URL + "/api/h/fetchotp";

    public static final String ACCT_BAL_URL = BASE_URL + "/api/h/balance";

    public static final String TXNSTATUS_URL = BASE_URL + "/api/h/txnstatus";

    public static final String P2P_TRANSFER_URL = BASE_URL + "/api/h/p2p";

    public static final String ACCT_HISTORY_URL = BASE_URL + "/api/h/history";

    public static final String CTRLVIEW_URL = BASE_URL + "/api/h/viewcontrol";

    public static final String CTRLUPDT_URL = BASE_URL + "/api/h/updatecontrol";

    public static final String RDM_INCTV_PTS_URL = BASE_URL + "/api/h/redeemIctvPoints";

    public static final String HOTLIST_URL = BASE_URL + "/api/h/hotlist";

    public static final String OPEN_REWARD_URL = BASE_URL + "/api/h/rewardAdvise";

    public static final String TXNSQUERY_URL = BASE_URL + "/api/h/txnquery";

}
