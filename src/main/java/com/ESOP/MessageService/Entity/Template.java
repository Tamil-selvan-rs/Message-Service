package com.ESOP.MessageService.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigInteger;

@Entity
@Data
@Table(name="template_master")
public class Template {
    @Id
    @Column(name = "alt_key")
    private BigInteger altKey;
    @Column(name = "subject")
    private String subject;
    @Column(name="body")
    private String body;
    @Column(name = "entity_name")
    private String entityName;
    @Column(name = "place_holder")
    private String placeHolder;
}
