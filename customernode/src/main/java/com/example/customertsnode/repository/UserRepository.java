package com.example.customertsnode.repository;

import com.example.customertsnode.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}

//order id_user id_offer
//product id title description
//offer -> product + price
//product -> type_of_payment
//user -> type_of_payment
//

