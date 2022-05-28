package com.chnu.phone.repository;

import com.chnu.phone.entity.Bill;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {

    @Override
    default List<Bill> findAll() {
        return findAll(Sort.by(Sort.Direction.ASC, "payedAt"));
    }

    List<Bill> findByPayedAtIsNull();
    List<Bill> findByPayedAtIsNotNull();
}
