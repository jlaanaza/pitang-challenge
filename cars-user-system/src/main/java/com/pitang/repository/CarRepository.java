package com.pitang.repository;

import com.pitang.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
	
	Optional<Car> findById(Long id);
	
}
