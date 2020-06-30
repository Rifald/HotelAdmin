package com.ribal.hoteladmin;

import java.io.Serializable;

public class User implements Serializable {
    private String email;
    private String fName;
    private String phone;

    public User(){

    }

    public String getEmail() {
        return email;
    }

    public String getfName() {
        return fName;
    }

    public String getPhone() {
        return phone;
    }
    @Override
    public String toString() {
        return " "+email+"\n" +
                " "+fName+"\n" +
                " "+phone +"\n";
    }
    public User( String email,String fName, String phone){
        this.email = email;
        this.fName = fName;
        this.phone = phone;

    }
}
