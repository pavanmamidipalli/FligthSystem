package com.passenger.controller;

import com.passenger.dto.PassengerDetailsCreateRequest;
import com.passenger.dto.PassengerDetailsUpdateRequest;
import com.passenger.dto.PassengerDetailsFindRequest;
import com.passenger.dto.ResponseDTO;
import com.passenger.entity.PassengerDetails;
import com.passenger.service.PassengerDetailsService;
import com.passenger.util.CustomValidator;
import jakarta.validation.*;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "passenger-details")
@Validated
public class PassengerDetailsController {

    @Autowired
    private PassengerDetailsService passengerDetailsService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody PassengerDetailsCreateRequest passengerDetailsCreateRequest){
        ResponseEntity<ResponseDTO> response = CustomValidator.validate(passengerDetailsCreateRequest);
        if (response != null) return response;
        List<PassengerDetails> passengerDetailsList = passengerDetailsService.findByMobileNoOrEmail(passengerDetailsCreateRequest.getMobileNo(), passengerDetailsCreateRequest.getEmail());
        response = passengerDetailsDuplicateCheck(passengerDetailsList);
        if (response != null) return response;
        PassengerDetails passengerDetails = passengerDetailsService.save(passengerDetailsCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(passengerDetails);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody PassengerDetailsUpdateRequest passengerDetailsUpdateRequest){
        ResponseEntity<ResponseDTO> errorDTO = CustomValidator.validate(passengerDetailsUpdateRequest);
        if (errorDTO != null) return errorDTO;
        PassengerDetails passengerDetails = passengerDetailsService.update(passengerDetailsUpdateRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(passengerDetails);
    }
    @GetMapping("/find-by-id/{id}")
    public ResponseEntity<?> find(@PathVariable int id){
        PassengerDetails passengerDetails = passengerDetailsService.find(id);
        if(passengerDetails == null){
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setErrorMessage("Passenger details with given id does not exist.");
            responseDTO.setErrorType("Record not found");
            return ResponseEntity.badRequest().body(responseDTO);
        }
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Fetched results successfully");
        responseDTO.setNoOfRecords(1);
        responseDTO.setResults(passengerDetails);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<?> delete(@PathVariable int id){
//        ResponseEntity<ErrorDTO> errorDTO = CustomValidator.validate(passengerDetailsFindRequest);
//        if (errorDTO != null) return errorDTO;
        int isDeleted = passengerDetailsService.delete(id);
        if(isDeleted==1){
            return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully.");
        }else{
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setErrorMessage("Passenger details with given id does not exist.");
            responseDTO.setErrorType("Record not found");
            return ResponseEntity.badRequest().body(responseDTO);
        }

    }
    @GetMapping("/find-all")
    public ResponseEntity<?> findAll(){
        List<PassengerDetails> passengerDetails = passengerDetailsService.findAll();
        if(passengerDetails.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body("No record found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(passengerDetails);
    }
    @GetMapping("/find-all/{field}")
    public ResponseEntity<?> sortAll(@PathVariable String field){
        List<PassengerDetails> passengerDetails = passengerDetailsService.sort(field);
        if(passengerDetails.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body("No record found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(passengerDetails);
    }
    @GetMapping("/find-all/{pageNumber}/{pageSize}")
    public ResponseEntity<?> paginationn(@PathVariable Integer pageNumber,@PathVariable Integer pageSize){
        Page<PassengerDetails> passengerDetails = passengerDetailsService.pagination(pageNumber,pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(passengerDetails);
    }
    @GetMapping("/find-all/{pageNumber}/{pageSize}/{field}")
    public ResponseEntity<?> paginationAndSorting(@PathVariable Integer pageNumber,@PathVariable Integer pageSize,@PathVariable String field){
        Page<PassengerDetails> passengerDetails = passengerDetailsService.sortAndPagination(pageNumber,pageSize,field);
        return ResponseEntity.status(HttpStatus.OK).body(passengerDetails);
    }

    private ResponseEntity<ResponseDTO> passengerDetailsDuplicateCheck(List<PassengerDetails> passengerDetailsList) {
        if(!passengerDetailsList.isEmpty()){
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setErrorMessage("Mobile number or email already exists.");
            responseDTO.setErrorType("Duplicate");
            return ResponseEntity.badRequest().body(responseDTO);
        }
        return null;
    }

    @GetMapping("/test")
    public String test(){
            return "test";
        }
}
