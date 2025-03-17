package com.project.customerapi.repository;

import com.project.customerapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;



public interface UserRepository  extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username); // kullanıcı adını bulmak için jpa sorgu extendi

}
