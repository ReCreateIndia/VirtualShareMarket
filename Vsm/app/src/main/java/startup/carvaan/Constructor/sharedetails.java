package startup.carvaan.Constructor;

public class sharedetails {
    String buyingprice,sellingprice;

    public String getBuyingprice() {
        return buyingprice;
    }

    public void setBuyingprice(String buyingprice) {
        this.buyingprice = buyingprice;
    }

    public String getSellingprice() {
        return sellingprice;
    }

    public void setSellingprice(String sellingprice) {
        this.sellingprice = sellingprice;
    }

    public sharedetails(String buyingprice, String sellingprice) {
        this.buyingprice = buyingprice;
        this.sellingprice = sellingprice;
    }
    public sharedetails(){}
}
