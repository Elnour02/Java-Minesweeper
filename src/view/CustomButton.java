package view;

import javax.swing.DefaultButtonModel;

public class CustomButton extends DefaultButtonModel {

    @Override
        public boolean isPressed() {
            return false;
        }
    
        @Override
        public boolean isRollover() {
            return false;
        }
    
        @Override
        public void setRollover(boolean b) {}
        
}
