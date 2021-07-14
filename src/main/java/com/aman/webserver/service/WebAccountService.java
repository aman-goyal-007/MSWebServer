package com.aman.webserver.service;


import com.aman.webserver.bean.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.security.auth.login.AccountNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Service
public class WebAccountService {

    @Autowired
    protected RestTemplate restTemplate;


    protected Logger logger = Logger.getLogger(WebAccountService.class.getName());

    public static final String ACCOUNT_SERVICE_URL = "http://ACCOUNT-SERVICE";



    /**
     * The RestTemplate works because it uses a custom request-factory that uses
     * Ribbon to look-up the service to use. This method simply exists to show this.
     */
    @PostConstruct
    public void demoOnly() {
        // Can't do this in the constructor because the RestTemplate injection
        // happens afterwards.
        logger.warning("The RestTemplate request factory is " + restTemplate.getRequestFactory().getClass());
    }

    public Account findByNumber(String accountNumber){
        logger.info("findByNumber invoked for account "+accountNumber);
        try{
            return restTemplate.getForObject(ACCOUNT_SERVICE_URL+"/accounts/{number}",Account.class,accountNumber);
        }catch(Exception e){
            logger.severe(e.getLocalizedMessage());
            return null;
        }
    }

    public List<Account> byOwnerContains(String name) {
        logger.info("byOwnerContains() invoked:  for " + name);
        Account[] accounts = null;

        try {
            accounts = restTemplate.getForObject(ACCOUNT_SERVICE_URL + "/accounts/owner/{name}", Account[].class, name);
        } catch (HttpClientErrorException e) {
        }

        if (accounts == null || accounts.length == 0)
            return null;
        else
            return Arrays.asList(accounts);
    }



}
