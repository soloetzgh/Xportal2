package com.etzgh.xportal.controller;

import com.etzgh.xportal.jobs.BulkReversalJob;
import com.etzgh.xportal.jobs.ChangePasswordJob;
import com.etzgh.xportal.jobs.EmiProcessJob;
import com.etzgh.xportal.jobs.ExpireOtpJob;
import com.etzgh.xportal.jobs.InactiveUserJob;
import com.etzgh.xportal.utility.Config;
import com.etzgh.xportal.utility.DbHibernate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author sunkwa-arthur
 */
public class PortalSettings {

    private static HashMap<String, String> map = new HashMap<>();
    private static final Logger log = Logger.getLogger(PortalSettings.class.getName());

    static {
        init();
    }

    public PortalSettings() {
    }

    public static void init() {

        try {
            log.info("LOADING PORTAL SETTINGS");
            Config config = new Config();
            List<Object[]> resp = loadPortalSettings();

            resp.forEach((record) -> {

                addSettings(record[0].toString(), record[1].toString());
            });

            JobKey changePasswordKey = new JobKey("changePasswordJob", "portalJob");
            JobDetail changePasswordJob = JobBuilder.newJob(ChangePasswordJob.class)
                    .withIdentity(changePasswordKey).build();

            JobKey inactiveUserKey = new JobKey("inactiveUserJob", "portalJob");
            JobDetail inactiveUserJob = JobBuilder.newJob(InactiveUserJob.class)
                    .withIdentity(inactiveUserKey).build();

            JobKey expirePasswordKey = new JobKey("expireOtpJob", "portalJob");
            JobDetail expireOtpJob = JobBuilder.newJob(ExpireOtpJob.class)
                    .withIdentity(expirePasswordKey).build();

            JobKey bulkReversalKey = new JobKey("bulkReversalJob", "portalJob");
            JobDetail bulkReversalJob = JobBuilder.newJob(BulkReversalJob.class)
                    .withIdentity(bulkReversalKey).build();

            JobKey emiProcessKey = new JobKey("emiProcessKeyJob", "portalJob");
            JobDetail emiProcessJob = JobBuilder.newJob(EmiProcessJob.class)
                    .withIdentity(emiProcessKey).build();

            Trigger changePasswordTrigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("changePasswordTrigger", "portalJob")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 */1 * ? * *"))
                    .build();
            Trigger inactiveUserTrigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("inactiveUserTrigger", "portalJob")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 */1 * ? * *"))
                    .build();

            Trigger expireOtpTrigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("expireOtpTrigger", "portalJob")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 */1 * ? * *"))
                    .build();
            Trigger bulkReversalTrigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("bulkReversalTrigger", "portalJob")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * ? * *"))
                    .build();

            Trigger emiProcessTrigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("emiProcessTrigger", "portalJob")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * ? * *"))
                    .build();

            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(changePasswordJob, changePasswordTrigger);
            scheduler.scheduleJob(inactiveUserJob, inactiveUserTrigger);
            scheduler.scheduleJob(expireOtpJob, expireOtpTrigger);

            if (config.getProperty("REVERSAL_JOB").equals("1")) {
                scheduler.scheduleJob(bulkReversalJob, bulkReversalTrigger);
            }
            if (config.getProperty("EMI_PROCESS_JOB").equals("1")) {
                scheduler.scheduleJob(emiProcessJob, emiProcessTrigger);
            }

        } catch (Exception ex) {

            log.error("error: ", ex);
        }

    }

    public static void refreshPortalSettings() {
        HashMap<String, String> refMap = new HashMap<>();

        try {
            log.info("REFRESHING PORTAL SETTINGS");

            List<Object[]> resp = loadPortalSettings();

            resp.forEach((record) -> {

                refMap.put(record[0].toString(), record[1].toString());
            });
            map = refMap;
        } catch (Exception ex) {

            log.error("error: ", ex);
        }
    }

    public static void addSettings(String key, String value) {

        map.put(key, value);
    }

    public String getSetting(String key) {

        return map.getOrDefault(key, "null");
    }

    public String getSetting(String key, String defaultValue) {

        return map.getOrDefault(key, defaultValue);
    }

    public static List<Object[]> loadPortalSettings() {
        List<Object[]> resp = new ArrayList<>();
        Session session = DbHibernate.getSession("APP_DB");
        try {

            String qry = "select key_name, concat(current_value,(case when type_2 !='' then concat('-',type_2) else '' end), "
                    + "(concat(':',status))) from telcodb.support_portal_settings where status = 1";

            Query q = session.createNativeQuery(qry);

            resp = q.getResultList();
        } catch (Exception we) {
            log.error("error: ", we);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return resp;
    }

}
