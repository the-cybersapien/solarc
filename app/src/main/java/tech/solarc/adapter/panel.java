package tech.solarc.adapter;

public class panel {

    Double area;
    Double efficiency;
    int id;
    String model;
    int type;

    public panel(Double area, Double efficiency, int id, String model, int type) {
        this.area = area;
        this.efficiency = efficiency;
        this.id = id;
        this.model = model;
        this.type = type;
    }

    public Double getArea() {
        return area;
    }

    public Double getEfficiency() {
        return efficiency;
    }

    public int getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public int getType() {
        return type;
    }
}
