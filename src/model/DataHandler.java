package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DataHandler {

    public void writeData(Object object) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("resources", "data/data.txt").getPath()));
            oos.writeObject(object);
            oos.close();
        } 
        catch (IOException e) {
            System.out.println();
        }
    }
    
    public Object readData() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("resources", "data/data.txt").getPath()));
            Object object = ois.readObject();
            ois.close();
            return object;
        } 
        catch (IOException e) {
            throw new RuntimeException(e);
        } 
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    
}
