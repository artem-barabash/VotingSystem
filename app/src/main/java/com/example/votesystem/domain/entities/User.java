package com.example.votesystem.domain.entities;

import java.util.ArrayList;

public class User {
    String numberKey;
    String firstName;
    String lastName;
    String email;
    String phone;
    String password;
    String birthday;
    String gender;
    String homeAddress;
    ArrayList<Answer> answerArrayList;


    public User(String numberKey, String firstName, String lastName, String email, String phone,
                String password, String birthday, String gender, String homeAddress,
                ArrayList<Answer> answerArrayList) {
        this.numberKey = numberKey;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.birthday = birthday;
        this.gender = gender;
        this.homeAddress = homeAddress;
        this.answerArrayList = answerArrayList;
    }

    public String getNumberKey() {
        return numberKey;
    }

    public void setNumberKey(String numberKey) {
        this.numberKey = numberKey;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public ArrayList<Answer> getAnswerArrayList() {
        return answerArrayList;
    }

    public void setAnswerArrayList(ArrayList<Answer> answerArrayList) {
        this.answerArrayList = answerArrayList;
    }

    @Override
    public String toString() {
        return "User{" +
                "numberKey='" + numberKey + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", birthday='" + birthday + '\'' +
                ", gender='" + gender + '\'' +
                ", homeAddress='" + homeAddress + '\'' +
                ", answerArrayList=" + answerArrayList +
                '}';
    }
}
