package com.home.pses.util;

import com.home.pses.entity.Company;
import com.home.pses.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope("prototype")
public class DataSynchronizer extends Thread {

    private volatile boolean running = true;

    @Autowired
    private CompanyService companyService;

    private static int timeout = 10;

    @Override
    public void run() {
        while (running) {
            try {
                List<Company> all = companyService.findAll();
                for (Company company : all) {
                    companyService.saveToEs(company);
                }
                sleep(timeout * 1000L);
            } catch (InterruptedException e) {
                System.out.println("Handling Interrupt Signal...");
                running = false;
            }
        }
    }

    public void setTimeout(int timeout) {
        DataSynchronizer.timeout = timeout;
    }

    public void terminate() {
        running = false;
    }
}
