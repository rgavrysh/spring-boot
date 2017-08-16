package com.home.springboot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

import javax.management.*;
import java.lang.management.ManagementFactory;

@Service
@ManagedResource(objectName = "bean:name=appStatistics", description = "My managed Bean")
public class AppStatistics implements AppStatisticsMBean {

    @Autowired
    private ClubService clubService;

    @Autowired
    private PlayerService playerService;

    /* If you don't want to use Spring jmx export support annotations (@ManagedResources, @ManagedOperation)
       you can simply use standard ManagementFactory library to register you object (in constructor) as an MBean.

    public AppStatistics() {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName objectName = null;
        try {
            objectName = new ObjectName("bean", "name", "appStatisticImpl");
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        }
        try {
            mBeanServer.registerMBean(this, objectName);
        } catch (InstanceAlreadyExistsException e) {
            e.printStackTrace();
        } catch (MBeanRegistrationException e) {
            e.printStackTrace();
        } catch (NotCompliantMBeanException e) {
            e.printStackTrace();
        }
    }
    */

    @Override
    @ManagedOperation
    public long getTotalNumberOfPlayers() {
        return this.playerService.getTotalNumberOfPlayers();
    }

    @Override
    @ManagedOperation
    public long getTotalNumberOfClubs() {
        return this.clubService.getTotalNumberOfClubs();
    }
}
