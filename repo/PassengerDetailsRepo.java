package com.passenger.repo;

import com.passenger.entity.PassengerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassengerDetailsRepo extends JpaRepository<PassengerDetails, Integer> {

    public List<PassengerDetails> findByMobileNoOrEmail(String mobileNo, String email);

}
