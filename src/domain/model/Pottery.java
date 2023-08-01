package domain.model;

import java.sql.Date;

public class Pottery extends Product {

    private Date importDate;
    private String supplier;

    public Pottery(String iD, String name, int amount, int price, Date importDate, String supplier) {
        super(iD, name, amount, price);
        this.importDate = importDate;
        this.supplier = supplier;
    }

    public Date getImportDate() {
        return importDate;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
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
