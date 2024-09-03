package com.passenger.service;

import com.passenger.dto.PassengerDetailsCreateRequest;
import com.passenger.dto.PassengerDetailsFindAllRequest;
import com.passenger.dto.PassengerDetailsUpdateRequest;
import com.passenger.entity.PassengerDetails;
import com.passenger.repo.PassengerDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class
PassengerDetailsService {

    @Autowired
    private PassengerDetailsRepo passengerDetailsRepo;

    public PassengerDetails save(PassengerDetailsCreateRequest passengerDetailsCreateRequest){
        PassengerDetails passengerDetails = getEntityFromCreateDTO(passengerDetailsCreateRequest);

        return passengerDetailsRepo.save(passengerDetails);
    }

    public PassengerDetails getEntityFromCreateDTO(PassengerDetailsCreateRequest passengerDetailsCreateRequest){
        PassengerDetails passengerDetails = new PassengerDetails();
        passengerDetails.setName(passengerDetailsCreateRequest.getName());
        passengerDetails.setEmail(passengerDetailsCreateRequest.getEmail());
        passengerDetails.setAddress(passengerDetailsCreateRequest.getAddress());
        passengerDetails.setMobileNo(passengerDetailsCreateRequest.getMobileNo());
        return passengerDetails;
    }

    public List<PassengerDetails> findByMobileNoOrEmail(String mobileNo, String email) {
        return passengerDetailsRepo.findByMobileNoOrEmail(mobileNo, email);
    }

    public PassengerDetails update(PassengerDetailsUpdateRequest passengerDetailsUpdateRequest) {
        PassengerDetails passengerDetails = getEntityFromUpdateDTO(passengerDetailsUpdateRequest);
        return passengerDetailsRepo.save(passengerDetails);
    }
    public PassengerDetails find(Integer id) {
        Optional<PassengerDetails> optional = passengerDetailsRepo.findById(id);
        if(optional.isPresent()){
            return passengerDetailsRepo.findById(id).get();
        }
        return null;
    }
    public int delete(Integer id) {
        Optional<PassengerDetails> optional = passengerDetailsRepo.findById(id);
        if (optional.isPresent()) {
            passengerDetailsRepo.deleteById(id);
            return 1;
        }
        else {
            return 0;
        }
    }
    public List<PassengerDetails> findAll() {
//        PassengerDetails passengerDetails = getEntityFromUpdateDTO(passengerDetailsFindAllRequest);
        return passengerDetailsRepo.findAll();
    }
    public PassengerDetails getEntityFromUpdateDTO(PassengerDetailsUpdateRequest passengerDetailsUpdateRequest){
        PassengerDetails passengerDetails = new PassengerDetails();
        passengerDetails.setId(passengerDetailsUpdateRequest.getId());
        passengerDetails.setName(passengerDetailsUpdateRequest.getName());
        passengerDetails.setEmail(passengerDetailsUpdateRequest.getEmail());
        passengerDetails.setAddress(passengerDetailsUpdateRequest.getAddress());
        passengerDetails.setMobileNo(passengerDetailsUpdateRequest.getMobileNo());
        return passengerDetails;
    }
    public List<PassengerDetails> sort(String field) {
        return passengerDetailsRepo.findAll(Sort.by(Sort.Direction.DESC,field));
    }

    public Page<PassengerDetails> pagination(Integer pageNumber,Integer pageSize){
      Page<PassengerDetails> page =passengerDetailsRepo.findAll(PageRequest.of(pageNumber,pageSize));
      return page;
    }
    public Page<PassengerDetails> sortAndPagination(Integer pageNumber,Integer pageSize,String field) {
        Page<PassengerDetails> passengerDetails = passengerDetailsRepo.findAll(PageRequest.of(pageNumber,pageSize).withSort(Sort.by(Sort.Direction.DESC,field)));
        return passengerDetails;
    }
}

