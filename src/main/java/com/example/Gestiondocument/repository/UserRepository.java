package com.example.Gestiondocument.repository;
import com.example.Gestiondocument.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
   User findByUsername(String username);
   //Optional<User> findByUsername(String username);
}






