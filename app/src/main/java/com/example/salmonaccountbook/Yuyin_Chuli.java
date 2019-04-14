package com.example.salmonaccountbook;

import android.content.Intent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Yuyin_Chuli {

    public String str;

    public ArrayList<String> iestr = new ArrayList<String>(){{
        add("收");
        add("赚");
        add("得");
    }};
    public HashMap<String,String> typemap1 = new HashMap<String,String>(){{
        put("衣","服装");
        put("裤","服装");
        put("鞋","服装");
        put("袜","服装");
        put("吃","餐饮");
        put("菜","餐饮");
        put("饭","餐饮");
        put("食","餐饮");
        put("餐","餐饮");
        put("车","出行");
        put("船","出行");
    }};

    Yuyin_Chuli(String str){
        this.str = str;
    }

    public boolean isincome(){
        for (String c:iestr) {
            if (str.contains(c)){
                return true;
            }
        }
        return false;
    }

    public String rtnType(){
        String rtn = "其他";
        for (String c:str.split("")) {
            if (typemap1.get(c)!=null){
                rtn = typemap1.get(c);
            }
        }
        return rtn;
    }

    public double rtnMoney() {
        String s = "";
        Pattern pattern = Pattern.compile("(\\d+\\.\\d+)");
        Matcher matcher = pattern.matcher(str);
        matcher.find();
        s = matcher.group();
        if(s.equals("")==false){
            return Double.parseDouble(s);
        }else{
            return 0;
        }

    }
}
