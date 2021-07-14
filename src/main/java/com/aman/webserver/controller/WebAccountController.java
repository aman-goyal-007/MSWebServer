package com.aman.webserver.controller;


import com.aman.webserver.bean.Account;
import com.aman.webserver.service.WebAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class WebAccountController {
    @Autowired
    protected WebAccountService accountService;

    protected Logger logger = Logger.getLogger(WebAccountController.class.getName());

    public WebAccountController(WebAccountService accountsService) {
        this.accountService = accountsService;
    }

    @GetMapping
    public String home(){
        return "WebAccount Service";
    }

    @GetMapping("/accounts/{accountNumber}")
    public Account byNumber(@PathVariable("accountNumber") String accountNumber) {

        logger.info("web-service byNumber() invoked: " + accountNumber);

        Account account = accountService.findByNumber(accountNumber);

        if (account == null) { // no such account
            return null;
        }

        logger.info("web-service byNumber() found: " + account);
        return account;
    }
}
