package com.stack.ibanvalidator.model;

import javax.persistence.*;

@Entity
@Table(name = "ibanformat")
public class IbanFormat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "country")
    private String country;

    @Column(name = "countryIdent")
    private String countryIdent;

    @Column(name = "accountNumberLength")
    private int accountNumberLength;

    public IbanFormat() {
    }

    public IbanFormat(String country, String countryIdent, int accountNumberLength) {
        this.country = country;
        this.countryIdent = countryIdent;
        this.accountNumberLength = accountNumberLength;
    }

    public long getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public String getCountryIdent() {
        return countryIdent;
    }

    public int getAccountNumberLength() {
        return accountNumberLength;
    }
}
