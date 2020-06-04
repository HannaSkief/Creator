package com.example.ic2.model;

import android.content.Context;
import android.content.SharedPreferences;

public class User {
    private long id;
    private String token;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private String status;
    private String address;
    private String phone;
    private long company_id;
    private long parent_id;
    private int role;
    private String company_name;
    private String company_reference;
    private String company_phone;
    private String company_email;
    private String img;

    private  static User currentUser;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getCompany_id() {
        return company_id;
    }

    public void setCompany_id(long company_id) {
        this.company_id = company_id;
    }

    public long getParent_id() {
        return parent_id;
    }

    public void setParent_id(long parent_id) {
        this.parent_id = parent_id;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_reference() {
        return company_reference;
    }

    public void setCompany_reference(String company_reference) {
        this.company_reference = company_reference;
    }

    public String getCompany_phone() {
        return company_phone;
    }

    public void setCompany_phone(String company_phone) {
        this.company_phone = company_phone;
    }

    public String getCompany_email() {
        return company_email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }





    public void setCompany_email(String company_email) {
        this.company_email = company_email;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public static void initCurrentUser(SharedPreferences sharedPreferences){
        currentUser=new User();
        currentUser.setId(sharedPreferences.getLong("userId",0));
        currentUser.setToken(sharedPreferences.getString("token",""));
        currentUser.setFirst_name(sharedPreferences.getString("firstName",""));
        currentUser.setLast_name(sharedPreferences.getString("lastName",""));
        currentUser.setEmail(sharedPreferences.getString("email",""));
        currentUser.setCompany_email(sharedPreferences.getString("companyEmail",""));
        currentUser.setCompany_id(sharedPreferences.getLong("companyId",0));
        currentUser.setImg(sharedPreferences.getString("img",""));

    }

    public static void saveCurrentUserInfo(User user,SharedPreferences sharedPreferences){
        currentUser=user;
        sharedPreferences.edit()
                .putBoolean("isLoggedIn",true)
                .putLong("userId",user.getId())
                .putLong("companyId",user.getCompany_id())
                .putString("firstName",user.getFirst_name())
                .putString("lastName",user.getLast_name())
                .putString("email",user.getEmail())
                .putString("companyEmail",user.getCompany_email())
                .putString("img",user.getImg())
                .putString("token","Bearer "+user.getToken())
                .apply();
    }
    public static User getCurrentUser() {
        return currentUser;
    }

}
