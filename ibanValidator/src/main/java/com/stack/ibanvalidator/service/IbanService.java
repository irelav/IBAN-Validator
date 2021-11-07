package com.stack.ibanvalidator.service;

import com.stack.ibanvalidator.model.Iban;
import com.stack.ibanvalidator.model.IbanFormat;
import com.stack.ibanvalidator.repository.IbanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class IbanService {

    @Autowired
    IbanRepository ibanRepository;

    public ResponseEntity<Iban> fetchIbanByAccount(String account) {
        Optional<Iban> ibanData = ibanRepository.findByAccount(account);

        if (ibanData.isPresent()) {
            return new ResponseEntity<>(ibanData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<Iban>> fetchByValidationType(String validType) {
        List<Iban> ibans = new ArrayList<>();
        System.out.println("Searching by type: " + validType.toLowerCase(Locale.ROOT));
        try {
            if (validType.toLowerCase(Locale.ROOT).equals("valid")) {
                ibans = ibanRepository.findByValid(true);
            } else if (validType.toLowerCase(Locale.ROOT).equals("invalid")) {
                ibans = ibanRepository.findByValid(false);
            } else if (validType.toLowerCase(Locale.ROOT).equals("all")) {
                ibans = ibanRepository.findAll();
            } else {
                System.out.print(" => didn't find match for validType");
            }
            if (ibans.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(ibans, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<HttpStatus> deleteIbanFromDatabase(String account) {
        try {
            ibanRepository.deleteByAccount(account);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<HttpStatus> deleteAllIbansFromDatabase() {
        try {
            ibanRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
