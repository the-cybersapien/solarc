package tech.solarc.adapter;

public class Appliance {

    String mName;
    int usableQuantity;

    public Appliance(String name , int quantity){
        mName = name;
        usableQuantity = quantity;
    }

    public String getName(){return mName;}
    public int getUsableQuantity(){return usableQuantity;}

}
