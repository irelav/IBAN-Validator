package com.stack.ibanvalidator.model;

import javax.persistence.*;

@Entity
@Table(name = "iban")
public class Iban {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "account")
    private String account;

    @Column(name = "country")
    private String country;

    @Column(name = "countryIdent")
    private String countryIdent;

    @Column(name = "valid")
    private boolean valid;

    public Iban() {

    }

    public Iban(String account) {
        this.account = account;
    }

    public Iban(String account, boolean valid) {
        this.account = account;
        this.valid = valid;
    }

    public Iban(String account, String country, String countryIdent, boolean valid) {
        this.account = account;
        this.country = country;
        this.countryIdent = countryIdent;
        this.valid = valid;
    }

    public long getId() {
        return id;
    }

    public String getAccount() {
        return account;
    }

    public String getCountry() {
        return country;
    }

    public String getCountryIdent() {
        return countryIdent;
    }

    public boolean isValid() {
        return valid;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCountryIdent(String countryIdent) {
        this.countryIdent = countryIdent;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "Iban{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", country='" + country + '\'' +
                ", countryIdent='" + countryIdent + '\'' +
                ", valid=" + valid +
                '}';
    }
}
