package com.example.transferrapidjavy;

import java.util.Date;

public class DataModel {
    String sender;
    String receiver;
    String date;
    Integer ammount;

    public DataModel(){}

    public DataModel(String sender, String receiver, String date, Integer ammount) {
        this.sender = sender;
        this.receiver = receiver;
        this.date = date;
        this.ammount = ammount;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getDate() {
        return date;
    }

    public Integer getAmmount() {
        return ammount;
    }
}
