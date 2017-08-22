package com.home.pses.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

@Service
@ManagedResource(objectName = "bean:name=dataSyncController", description = "Data Sync MBean.")
public class DataSyncController implements ApplicationContextAware {
    private DataSynchronizer dataSync;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @ManagedOperation
    public void getNewDataSync() {
        this.dataSync = applicationContext.getBean(DataSynchronizer.class);
    }

    @ManagedOperation
    public void startDataSync() {
        if (dataSync == null || dataSync.getState() == Thread.State.TERMINATED) {
            getNewDataSync();
        }
        this.dataSync.start();
    }

    @ManagedOperation
    public void stopDataSync() {
        this.dataSync.terminate();
        this.dataSync = null;
    }

    @ManagedOperation
    public Thread.State getDataSyncThreadState() {
        return this.dataSync.getState();
    }

    @ManagedOperation
    public void setTimeout(int seconds) {
        this.dataSync.setTimeout(seconds);
    }
}
