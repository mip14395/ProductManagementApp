package domain.model;

import java.sql.Date;
import java.util.concurrent.TimeUnit;

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
    public String saleGrading() {
        long milisDay = TimeUnit.DAYS.toMillis(1);
        // chỉ còn 10 trong kho trong vòng 90 ngày thì là hàng bán chạy
        if (milisDay * 90 > System.currentTimeMillis() - getImportDate().getTime() && getAmount() <= 10)
            return "Best-Seller";
        // Sau 1 năm, trong kho vẫn còn 15 thì là hàng khó bán
        if (milisDay * 365 <= System.currentTimeMillis() - getImportDate().getTime() && getAmount() >= 15)
            return "Obsolete";
        return "Average";
    }

}
