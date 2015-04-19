package com.example.inga.mcash;

/**
 * Created by Inga on 21.03.2015.
 */
public class Cashier {

    private String firstName;
    private String lastName;
    private String pw;
    private int id;

    public Cashier(){

    }

    public void setFirstName(String firstName){
        this.firstName=firstName;
    }

    public void setLastName(String lastName){
        this.lastName=lastName;
    }

    public void setPw(String pw){
        this.pw=pw;
    }

    public void setId(int id){
        this.id=id;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getPw(){
        return pw;
    }

    public int getId(){
        return id;
    }
}
