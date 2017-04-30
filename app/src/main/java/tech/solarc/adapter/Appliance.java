package tech.solarc.adapter;

import android.support.annotation.NonNull;

public class Appliance implements Comparable<Appliance>{

    private String mName;
    private int usableQuantity;
    private double dailyConsumption;
    private int priority;

    public Appliance(String name , int quantity, double dailyConsumption, int priority){
        mName = name;
        usableQuantity = quantity;
        this.dailyConsumption = dailyConsumption;
        this.priority = priority;
    }

    public String getName(){
        return mName;
    }

    public int getUsableQuantity(){
        return usableQuantity;
    }

    public double getDailyConsumption() {
        return dailyConsumption;
    }

    public int getPriority(){
        return priority;
    }

    @Override
    public int compareTo(@NonNull Appliance o) {
        if (priority == o.priority){
            return -Double.compare(dailyConsumption, o.dailyConsumption);
        } else {
            return -((Integer)priority).compareTo(o.priority);
        }
    }
}
