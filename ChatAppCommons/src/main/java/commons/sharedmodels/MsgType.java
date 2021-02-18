package commons.sharedmodels;

public enum MsgType{FILE(1), TEXT(0);
    private final int value;
    MsgType(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
    @Override
    public String toString(){
        return  ""+value;
    }
};