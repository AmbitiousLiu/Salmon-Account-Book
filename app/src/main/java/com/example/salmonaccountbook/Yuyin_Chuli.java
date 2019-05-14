package com.example.salmonaccountbook;

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
        put("表","服装");

        put("吃","餐饮");
        put("菜","餐饮");
        put("饭","餐饮");
        put("食","餐饮");
        put("餐","餐饮");
        put("鸡","餐饮");
        put("鸭","餐饮");
        put("鱼","餐饮");
        put("肉","餐饮");
        put("米","餐饮");
        put("面","餐饮");
        put("螺","餐饮");
        put("奶","餐饮");
        put("茶","餐饮");
        put("粽","餐饮");
        put("蕉","餐饮");
        put("瓜","餐饮");
        put("果","餐饮");
        put("橘","餐饮");
        put("桃","餐饮");
        put("梨","餐饮");
        put("饼","餐饮");
        put("豆","餐饮");
        put("肠","餐饮");
        put("牛","餐饮");
        put("蛋","餐饮");
        put("菇","餐饮");
        put("肚","餐饮");

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
        ArrayList<String> s1 = new ArrayList<String>(){{
            add("0");
            add("0");
        }};
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(str);
        int i = 0;
        while (matcher.find()) {
            s1.set(i,matcher.group(0));
            i++;
            if(i==2) {
                break;
            }
        }
        s = s1.get(0)+"."+s1.get(1);
        if(s.equals("0.0")){
            return 0;
        }else{
            return Double.parseDouble(s);
        }

    }
}
