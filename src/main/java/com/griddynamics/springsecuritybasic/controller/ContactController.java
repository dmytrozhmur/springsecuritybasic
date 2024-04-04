package com.griddynamics.springsecuritybasic.controller;

import com.griddynamics.springsecuritybasic.model.Contact;
import com.griddynamics.springsecuritybasic.repo.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@RestController
public class ContactController {
    @Autowired
    private ContactRepository contactRepository;

    @PostMapping("/contact")
    @PostFilter("filterObject.contactName != 'Test'")
    public List<Contact> saveContactInquiryDetails(@RequestBody Contact contact) {
        contact.setContactId(getServiceReqNumber());
        contact.setCreateDt(new Date(System.currentTimeMillis()));
        Contact saved = contactRepository.save(contact);
        List<Contact> contacts = new ArrayList<>();
        contacts.add(saved);
        return contacts;
    }

    public String getServiceReqNumber() {
        Random random = new Random();
        int ranNum = random.nextInt(999999999 - 9999) + 9999;
        return "SR"+ranNum;
    }
}
