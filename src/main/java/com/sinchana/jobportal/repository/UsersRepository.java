package com.sinchana.jobportal.repository;

import com.sinchana.jobportal.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users,Integer> {

   Optional<Users> findByEmail(String email);

}
