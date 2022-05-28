package com.chnu.phone.repository;

import com.chnu.phone.entity.Bill;
import com.chnu.phone.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    default List<User> findAll() {
        return findAll(Sort.by(Sort.Direction.ASC, "fullName"));
    }

}
