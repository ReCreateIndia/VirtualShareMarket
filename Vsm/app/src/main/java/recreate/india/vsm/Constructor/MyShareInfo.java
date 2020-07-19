package recreate.india.vsm.Constructor;

public class MyShareInfo {
    String holding;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;


    public String getHolding() {
        return holding;
    }

    public void setHolding(String holding) {
        this.holding = holding;
    }

    public MyShareInfo(String holding, String id) {
        this.holding = holding;
        this.id = id;
    }

    public MyShareInfo (){}
}
