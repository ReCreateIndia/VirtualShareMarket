package startup.carvaan.Constructor;

public class CashFreeToken {
    private String message,status,cftoken;

    public CashFreeToken() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCftoken() {
        return cftoken;
    }

    public void setCftoken(String cftoken) {
        this.cftoken = cftoken;
    }

    public CashFreeToken(String message, String status, String cftoken) {
        this.message = message;
        this.status = status;
        this.cftoken = cftoken;
    }
}
