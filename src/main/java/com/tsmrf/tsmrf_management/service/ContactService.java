package com.tsmrf.tsmrf_management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tsmrf.tsmrf_management.model.Contact;
import com.tsmrf.tsmrf_management.repository.ContactRepository;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public Contact createContact(Contact contact) {
        return contactRepository.save(contact);
    }

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }
}