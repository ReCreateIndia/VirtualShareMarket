package recreate.india.vsm.Constructor;

public class Sharequantity {
    String total,available;

    public Sharequantity(){

    }
    public String getTotal() {
        return total;
    }
    public void setTotal(String total) {
        this.total = total;
    }
    public String getAvailable() {
        return available;
    }
    public void setAvailable(String available) {
        this.available = available;
    }
    public Sharequantity(String total, String available) {
        this.total = total;
        this.available = available;
    }
}
