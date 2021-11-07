package com.stack.ibanvalidator.controller;

import com.stack.ibanvalidator.model.Iban;
import com.stack.ibanvalidator.service.IbanFormatService;
import com.stack.ibanvalidator.service.IbanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class IbanController {

    @Autowired
    IbanService ibanService;

    @Autowired
    IbanFormatService ibanFormatService;

    @GetMapping("/ibans/{account}")
    public ResponseEntity<Iban> getIbanByAccount(@PathVariable("account") String account) {
        return ibanService.fetchIbanByAccount(account);
    }

    //list of ibans based on valid type
    @GetMapping("/ibans")
    public ResponseEntity<List<Iban>> findByValid(@RequestParam(value="valid") String valid) {
        return ibanService.fetchByValidationType(valid);
    }

    //add an iban
    @PostMapping("/ibans")
    public ResponseEntity<Iban> createIban(@RequestBody Iban iban) {
        return ibanFormatService.createIban(iban);
    }

    @PostMapping("/ibansmultiple")
    public ResponseEntity<List<Iban>> createIbans(@RequestBody List<Iban> ibans) {
        return ibanFormatService.createIbans(ibans);
    }

    @DeleteMapping("/ibans/{account}")
    public ResponseEntity<HttpStatus> deleteIban(@PathVariable("account") String account) {
        return ibanService.deleteIbanFromDatabase(account);
    }

    //remove all
    @DeleteMapping("/ibans")
    public ResponseEntity<HttpStatus> deleteAllIbans() {
        return ibanService.deleteAllIbansFromDatabase();
    }
}
