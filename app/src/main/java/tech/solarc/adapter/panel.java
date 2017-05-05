package tech.solarc.adapter;

public class panel {

    private Double area;
    private Double efficiency;
    private int id;
    private String model;
    private int type;

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

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(Double efficiency) {
        this.efficiency = efficiency;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
