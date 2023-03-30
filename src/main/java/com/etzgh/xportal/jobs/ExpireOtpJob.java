/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.jobs;

import com.etzgh.xportal.service.PortalSettingService;
import org.apache.log4j.Logger;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;

/**
 *
 * @author sunkwa-arthur
 */
public class ExpireOtpJob implements InterruptableJob {

    private static final Logger log = Logger.getLogger(ExpireOtpJob.class.getName());

    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {

        try {
            new PortalSettingService().runExpireOtpSchedule();
        } catch (Exception es) {

            log.error(" runExpireOtpSchedule error: ", es);

        }
    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
