package tech.solarc.adapter;

import android.widget.Button;
import android.widget.EditText;

/**
 * Created by sagar on 30/4/17.
 */

public class EditApplication {

    String name;
    EditText quantity;
    Button plus;
    Button minus;

    public EditApplication(String name, EditText quantity, Button plus, Button minus) {
        this.name = name;
        this.quantity = quantity;
        this.plus = plus;
        this.minus = minus;
    }

    public String getName() {
        return name;
    }

    public EditText getQuantity() {
        return quantity;
    }

    public Button getPlus() {
        return plus;
    }

    public Button getMinus() {
        return minus;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(EditText quantity) {
        this.quantity = quantity;
    }

    public void setPlus(Button plus) {
        this.plus = plus;
    }

    public void setMinus(Button minus) {
        this.minus = minus;
    }
}
