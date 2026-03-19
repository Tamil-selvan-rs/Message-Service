package com.ESOP.MessageService.Repository;

import com.ESOP.MessageService.Entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface MessageRepository extends JpaRepository<Template, BigInteger> {
}
