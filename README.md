# IBAN-Validator

Description: <br>
This is REST application for IBAN validation. <br>
All IBAN numbers are stored to database and later listed by valid type (valid / invalid / all). <br>
In React application *Add an IBAN* submit IBAN number, then in *List of IBANs* all IBAN numbers are showed. By making an input of valid type, you can specify which account numbers to show. By clicking on an account number, the information about that IBAN number is showed: 
1. account
2. country (if empty then it is an invalid type)
3. country identification (first two letters of IBAN)
4. status (valid or invalid)

More information about validation of IBAN: https://en.wikipedia.org/wiki/International_Bank_Account_Number

Total number of different national check digits: 79
 
Stack used:
1. Java 11, Spring Boot v2.5.6, Maven
2. React, Bootstarp 5, Axios
3. PostgresSQL 13

Installation

1. */ibanValidator/src/main/resources/application.properties* <br>
Set up database by specifying username & password 
properties which are the same as your database installation. <br>
All tables (iban - to store added accounts, ibanFormat - to store all national ) will be created when running application.
2. */ibanValidator* <br>
In (first) terminal run: **./mvnw spring-boot:run**
3. */react-iban-validator* <br>
In (second) terminal run two commands: 1) **npm install** 2) **npm start**
