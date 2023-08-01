package domain.model;

public class Appliance extends Product {

    private int warrantyMonths;
    private double capacity;

    public Appliance(String iD, String name, int amount, int price, int warrantyMonths, double capacity) {
        super(iD, name, amount, price);
        this.warrantyMonths = warrantyMonths;
        this.capacity = capacity;
    }

    public int getWarrantyMonths() {
        return warrantyMonths;
    }

    public double getCapacity() {
        return capacity;
    }

    @Override
    public int calVAT() {
        return getPrice() / 10;
    }

    @Override
    public String grade() {
        String grade = new String();
        return grade;
    }
}
