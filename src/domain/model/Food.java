package domain.model;

import java.sql.Date;

public class Food extends Product {

    private Date mFG, eXP;
    private String supplier;

    public Food(String iD, String name, int amount, int price, Date mFG, Date eXP, String supplier) {
        super(iD, name, amount, price);
        this.mFG = mFG;
        this.eXP = eXP;
        this.supplier = supplier;
    }

    public Date getmFG() {
        return mFG;
    }

    public Date geteXP() {
        return eXP;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setmFG(Date mFG) {
        this.mFG = mFG;
        notifySubscribers();
    }

    public void seteXP(Date eXP) {
        this.eXP = eXP;
        notifySubscribers();
    }

    @Override
    public int calVAT() {
        return getPrice() / 20;
    }

    @Override
    public String saleGrading() {
        // Còn hàng hết hạn => Auto khó bán
        if (getAmount() > 0 && geteXP().getTime() - System.currentTimeMillis() < 0)
            return "Obsolete";
        // Còn 1/10 thời gian từ NSX đến HSD mà vẫn còn 15 => hết hạn
        if (getAmount() >= 15 && (System.currentTimeMillis() - getmFG().getTime()) / 9 > geteXP().getTime()
                - System.currentTimeMillis())
            return "Obsolete";
        // Mới 1/2 thời gian từ NSX đến HSD mà còn 15=> bán chạy
        if ((geteXP().getTime() - System.currentTimeMillis() >= System.currentTimeMillis() - getmFG().getTime())
                && getAmount() <= 15) {
            return "Best-Seller";
        }
        return "Average";
    }

}
