package tech.solarc.adapter;

public class Appliance {

    private String mName;
    private int usableQuantity;
    private double dailyConsumption;

    public Appliance(String name , int quantity, double dailyConsumption){
        mName = name;
        usableQuantity = quantity;
        this.dailyConsumption = dailyConsumption;
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
}
