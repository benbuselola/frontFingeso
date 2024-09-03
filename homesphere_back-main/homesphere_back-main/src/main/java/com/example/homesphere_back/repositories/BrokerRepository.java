package com.example.homesphere_back.repositories;

import com.example.homesphere_back.models.Users;
import com.example.homesphere_back.models.Brokers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;


public interface BrokerRepository extends JpaRepository<Brokers, Long> {
    Brokers findBrokerById(Long id);
}
