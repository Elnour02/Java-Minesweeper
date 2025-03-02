package model;

public class Mine implements Board {

    private boolean flag;

    public Mine() {
        flag = false;
    }

    @Override
    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public boolean getFlag() {
        return flag;
    }

}
