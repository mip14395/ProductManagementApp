package domain.model;

import java.io.Serializable;

public abstract class Product implements Serializable {
    private String iD, name;
    private int amount;
    private int price;

    public Product(String iD, String name, int amount, int price) {
        this.iD = iD;
        this.name = name;
        this.amount = amount;
        this.price = price;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getID() {
        return iD;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public int getPrice() {
        return price;
    }

    public abstract int calVAT();

    public abstract String grade();
}
