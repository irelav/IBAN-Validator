package com.stack.ibanvalidator.repository;

import com.stack.ibanvalidator.model.Iban;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IbanRepository extends JpaRepository<Iban, Long> {
    List<Iban> findByValid(boolean valid);

    Optional<Iban> findByAccount(String account);

    List<Iban> deleteByAccount(String account);
}
