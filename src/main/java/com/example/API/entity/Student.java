package com.example.API.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Student {

    @Id
    private String uid;
    private String classname;
    private String name;
    private String Email;
    private String Contact;
    private String Batch_No;

    // Default constructor (required by Hibernate)
    public Student() {
    }

    // Parameterized constructor (optional)
    public Student(String uid, String classname, String name, String email, String contact, String batch_No) {
        this.uid = uid;
        this.classname = classname;
        this.name = name;
        this.Email = email;
        this.Contact = contact;
        this.Batch_No = batch_No;
    }

    // Getters and setters
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        this.Contact = contact;
    }

    public String getBatch_No() {
        return Batch_No;
    }

    public void setBatch_No(String batch_No) {
        this.Batch_No = batch_No;
    }
}
