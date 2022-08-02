package com.example.ordernode.repository;

import com.example.ordernode.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {

    Type findTypeByType(String type);

}
