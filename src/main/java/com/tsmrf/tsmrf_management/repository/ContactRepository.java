package com.tsmrf.tsmrf_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tsmrf.tsmrf_management.model.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {

}