package com.stack.ibanvalidator.repository;

import com.stack.ibanvalidator.model.IbanFormat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IbanFormatRepository extends JpaRepository<IbanFormat, Long> {
    IbanFormat findByCountryIdent(String indent);
    boolean existsByCountryIdent(String indent);
}
