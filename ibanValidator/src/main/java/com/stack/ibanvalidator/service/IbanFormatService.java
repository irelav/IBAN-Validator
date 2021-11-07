package com.stack.ibanvalidator.service;

import com.stack.ibanvalidator.model.Iban;
import com.stack.ibanvalidator.model.IbanFormat;
import com.stack.ibanvalidator.repository.IbanFormatRepository;
import com.stack.ibanvalidator.repository.IbanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;

@Service
public class IbanFormatService {

    @Autowired
    IbanFormatRepository ibanFormatRepository;

    @Autowired
    IbanRepository ibanRepository;

    public ResponseEntity<Iban> createIban(Iban iban) {
        List<IbanFormat> ibanFormatData = ibanFormatRepository.findAll();
        String country;
        boolean valid;
        try {
            if (ibanFormatData.isEmpty()) {
                saveIbanValidationRepository();
            }
            String account = iban.getAccount();
            String countryIdent = iban.getAccount().substring(0,2);
            if (ibanFormatRepository.existsByCountryIdent(countryIdent)) {
                country = ibanFormatRepository.findByCountryIdent(countryIdent).getCountry();
                valid = checkValidation(iban.getAccount());
            } else {
                country = null;
                valid = false;
            }
            Iban _iban = ibanRepository
                    .save(new Iban(account, country, countryIdent, valid));
            System.out.println("Saved to database: " + _iban);
            return new ResponseEntity<>(_iban, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Iban>> createIbans(List<Iban> iban) {
        List<IbanFormat> ibanFormatData = ibanFormatRepository.findAll();
        List<Iban> _ibanData = new ArrayList<>(iban);
        try {
            if (ibanFormatData.isEmpty()) {
                saveIbanValidationRepository();
            }
            for (int i = 0; i < iban.size(); i++) {
                ResponseEntity<Iban> _iban = createIban(_ibanData.get(i));
                _ibanData.get(i).setId(Objects.requireNonNull(_iban.getBody()).getId());
                _ibanData.get(i).setAccount(Objects.requireNonNull(_iban.getBody()).getAccount());
                _ibanData.get(i).setValid(Objects.requireNonNull(_iban.getBody()).isValid());
                if (_iban.getBody().getCountry() != null) {
                    _ibanData.get(i).setCountry(ibanFormatRepository.findByCountryIdent(_iban.getBody().getCountryIdent()).getCountry());
                } else {
                    _ibanData.get(i).setCountry(null);
                }
                _ibanData.get(i).setCountryIdent(_iban.getBody().getCountryIdent());
            }
            return new ResponseEntity<>(_ibanData, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean checkValidation(String account) {
        boolean isLengthValid = checkLength(account.substring(0, 2), account);
        String afterFirstFormat = firstFormat(account);
        BigInteger afterSecondFormat = secondFormat(afterFirstFormat);
        return checkCalculation(afterSecondFormat) && isLengthValid;
    }

    public boolean checkLength(String indent, String account) {
        IbanFormat ibanFormatData = ibanFormatRepository.findByCountryIdent(indent);
        if (ibanFormatData.getAccountNumberLength() == account.length()) {
            return true;
        }
        return false;
    }

    public String firstFormat(String account) {
        String firstFourLetters = account.substring(0, 4);
        StringBuilder sb = new StringBuilder(account);
        sb.delete(0, 4);
        sb.append(firstFourLetters);
        return sb.toString();
    }

    public BigInteger secondFormat(String account) {
        StringBuilder sb = new StringBuilder();
        HashMap<Character, Integer> result = charValues();
        for (int i = 0; i < account.length(); i++) {
            if (result.containsKey(account.charAt(i))) {
                sb.append(result.get(account.charAt(i)));
            } else {
                sb.append(account.charAt(i));
            }
        }
        return new BigInteger(sb.toString());
    }

    public boolean checkCalculation(BigInteger account) {
        BigInteger checker = new BigInteger("97");
        int result = account.mod(checker).intValue();
        return result == 1;
    }

    public HashMap<Character, Integer> charValues() {
        HashMap<Character, Integer> result = new HashMap<>();
        result.put('A', 10);
        result.put('B', 11);
        result.put('C', 12);
        result.put('D', 13);
        result.put('E', 14);
        result.put('F', 15);
        result.put('G', 16);
        result.put('H', 17);
        result.put('I', 18);
        result.put('J', 19);
        result.put('K', 20);
        result.put('L', 21);
        result.put('M', 22);
        result.put('N', 23);
        result.put('O', 24);
        result.put('P', 25);
        result.put('Q', 26);
        result.put('R', 27);
        result.put('S', 28);
        result.put('T', 29);
        result.put('U', 30);
        result.put('V', 31);
        result.put('W', 32);
        result.put('X', 33);
        result.put('Y', 34);
        result.put('Z', 35);
        return result;
    }

    public void saveIbanValidationRepository() {
        ibanFormatRepository.saveAll(Arrays.asList(
                new IbanFormat("Albania", "AL", 28),
                new IbanFormat("Andorra", "AD", 24),
                new IbanFormat("Austria", "AT", 20),
                new IbanFormat("Azerbaijan", "AZ", 28),
                new IbanFormat("Bahrain", "BH", 22),
                new IbanFormat("Belarus", "BY", 28),
                new IbanFormat("Belgium", "BE", 16),
                new IbanFormat("Bosnia and Herzegovina", "BA", 20),
                new IbanFormat("Brazil", "BR", 29),
                new IbanFormat("Bulgaria", "BG", 22),
                new IbanFormat("Costa Rica", "CR", 22),
                new IbanFormat("Croatia", "HR", 21),
                new IbanFormat("Cyprus", "CY", 28),
                new IbanFormat("Czech Republic", "CZ", 24),
                new IbanFormat("Denmark", "DK", 18),
                new IbanFormat("Dominican Republic", "DO", 28),
                new IbanFormat("East Timor", "TL", 23),
                new IbanFormat("Egypt", "EG", 29),
                new IbanFormat("El Salvador", "SV", 28),
                new IbanFormat("Estonia", "EE", 20),
                new IbanFormat("Faroe Islands", "FO", 18),
                new IbanFormat("Finland", "FI", 18),
                new IbanFormat("France", "FR", 27),
                new IbanFormat("Georgia", "GE", 22),
                new IbanFormat("Germany", "DE", 22),
                new IbanFormat("Gibraltar", "GI", 23),
                new IbanFormat("Greece", "GR", 27),
                new IbanFormat("Greenland", "GL", 18),
                new IbanFormat("Guatemala", "GT", 28),
                new IbanFormat("Hungary", "HU", 28),
                new IbanFormat("Iceland", "IS", 26),
                new IbanFormat("Iraq", "IQ", 23),
                new IbanFormat("Ireland", "IE", 22),
                new IbanFormat("Israel", "IL", 23),
                new IbanFormat("Italy", "IT", 27),
                new IbanFormat("Jordan", "JO", 30),
                new IbanFormat("Kazakhstan", "KZ", 20),
                new IbanFormat("Kosovo", "XK", 20),
                new IbanFormat("Kuwait", "KW", 30),
                new IbanFormat("Latvia", "LV", 21),
                new IbanFormat("Lebanon", "LB", 28),
                new IbanFormat("Libya", "LY", 25),
                new IbanFormat("Liechtenstein", "LI", 21),
                new IbanFormat("Lithuania", "LT", 20),
                new IbanFormat("Luxembourg", "LU", 20),
                new IbanFormat("North Macedonia", "MK", 19),
                new IbanFormat("Malta", "MT", 31),
                new IbanFormat("Mauritania", "MR", 27),
                new IbanFormat("Mauritius", "MU", 30),
                new IbanFormat("Monaco", "MC", 27),
                new IbanFormat("Moldova", "MD", 24),
                new IbanFormat("Montenegro", "ME", 22),
                new IbanFormat("Netherlands", "NL", 18),
                new IbanFormat("Norway", "NO", 15),
                new IbanFormat("Pakistan", "PK", 24),
                new IbanFormat("Palestinian territories", "PS", 29),
                new IbanFormat("Poland", "PL", 28),
                new IbanFormat("Portugal", "PT", 25),
                new IbanFormat("Qatar", "QA", 29),
                new IbanFormat("Romania", "RO", 24),
                new IbanFormat("Saint Lucia", "LC", 32),
                new IbanFormat("San Marino", "SM", 27),
                new IbanFormat("Sao Tome and Principe", "ST", 25),
                new IbanFormat("Saudi Arabia", "SA", 24),
                new IbanFormat("Serbia", "RS", 22),
                new IbanFormat("Seychelles", "SC", 31),
                new IbanFormat("Slovakia", "SK", 24),
                new IbanFormat("Slovenia", "SI", 19),
                new IbanFormat("Spain", "ES", 24),
                new IbanFormat("Sudan", "SD", 18),
                new IbanFormat("Sweden", "SE", 24),
                new IbanFormat("Switzerland", "CH", 21),
                new IbanFormat("Tunisia", "TN", 24),
                new IbanFormat("Turkey", "TR", 26),
                new IbanFormat("Ukraine", "UA", 29),
                new IbanFormat("United Arab Emirates", "AE", 23),
                new IbanFormat("United Kingdom", "GB", 22),
                new IbanFormat("Vatican City", "VA", 22),
                new IbanFormat("Virgin Islands, British", "VG", 24)
        ));
    }
}
