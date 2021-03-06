package ru.progwards.java2.lessons.synchro.bankomat.model;

import java.util.Date;

public class Account extends ru.progwards.java2.lessons.http.bankomat.model.Account {

private String id;
private String holder;
private Date date;
private double amount;
private int pin;

public String getId() {
        return id;
        }

public void setId(String id) {
        this.id = id;
        }

public String getHolder() {
        return holder;
        }

public void setHolder(String holder) {
        this.holder = holder;
        }

public Date getDate() {
        return date;
        }

public void setDate(Date date) {
        this.date = date;
        }

public double getAmount() {
        return amount;
        }

public void setAmount(double amount) {
        this.amount = amount;
        }

public int getPin() {
        return pin;
        }

public void setPin(int pin) {
        this.pin = pin;
        }
        }