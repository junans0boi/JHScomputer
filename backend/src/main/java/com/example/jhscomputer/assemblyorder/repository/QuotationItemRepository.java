package com.example.jhscomputer.assemblyorder.repository;

import com.example.jhscomputer.assemblyorder.entity.QuotationItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuotationItemRepository extends JpaRepository<QuotationItem, Long> {
}