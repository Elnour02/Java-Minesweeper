package model;

public class Tile implements Board {

    private boolean checked;
    private boolean flag;
    
    public Tile() {
        checked = false;
        flag = false;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean getChecked() {
        return checked;
    }

    @Override
    public boolean getFlag() {
        return flag;
    }

}
