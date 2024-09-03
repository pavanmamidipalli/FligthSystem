package com.passenger.util;

import com.passenger.dto.ResponseDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public class CustomValidator {

    public static ResponseEntity<ResponseDTO> validate(Object object) {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<Object>> violations = validator.validate(object);
            List<String> violationMessages = violations.stream().map(ConstraintViolation::getMessage).toList();
            if(!violationMessages.isEmpty()) {
                ResponseDTO responseDTO = new ResponseDTO();
                responseDTO.setErrorType("Validation");
                responseDTO.setErrorMessage(violationMessages);
                return ResponseEntity.badRequest().body(responseDTO);
            }
        }
        return null;
    }

}
