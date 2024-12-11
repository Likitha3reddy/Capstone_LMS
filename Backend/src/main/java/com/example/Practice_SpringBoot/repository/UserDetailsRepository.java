package com.example.Practice_SpringBoot.repository;

import com.example.Practice_SpringBoot.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails,Long> {

    UserDetails findByUserName(String userName);
//    UserDetails findByUserNameAndRole(String userName, String role);

}
