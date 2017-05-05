package tech.solarc.adapter;

import android.support.annotation.NonNull;

public class Appliance implements Comparable<Appliance>{

    private String mName;
    private int usableQuantity;
    private int totalQuantity;
    private double dailyConsumption;
    private int priority;
    private int mId;

    public Appliance(int id, String name , int quantity, double dailyConsumption, int priority){
        mId = id;
        mName = name;
        totalQuantity = quantity;
        this.dailyConsumption = dailyConsumption;
        this.priority = priority;
    }

    public String getName(){
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public int getUsableQuantity(){
        return usableQuantity;
    }

    public void setUsableQuantity(int usableQuantity) {
        this.usableQuantity = usableQuantity;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public double getDailyConsumption() {
        return dailyConsumption;
    }

    public void setDailyConsumption(double dailyConsumption) {
        this.dailyConsumption = dailyConsumption;
    }

    public int getPriority(){
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
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
