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
    }

    public void seteXP(Date eXP) {
        this.eXP = eXP;
    }

    @Override
    public int calVAT() {
        return getPrice() / 20;
    }

    @Override
    public String grade() {
        String grade = new String();
        return grade;
    }

}
