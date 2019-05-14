package com.example.salmonaccountbook;

import org.litepal.crud.DataSupport;

public class Person extends DataSupport {
    private String username;
    private String password;
    private String birthday;
    private String beijing;
    private String denglu;
    private String plan;
    private String remind;

    public String getRemind() {
        return remind;
    }

    public void setRemind(String remind) {
        this.remind = remind;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setBeijing(String beijing){
        this.beijing = beijing;
    }

    public String getBeijing(){
        return beijing;
    }

    public String getDenglu(){
        return denglu;
    }

    public void setDenglu(String denglu){
        this.denglu = denglu;
    }
}
