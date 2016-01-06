package server;

import java.util.ArrayList;

import model.Automobile;
/**
 * Interface used to declare methods.
 * @author willQian
 */
public interface AutoServer {
    public void addAutomobileObject(String filename, Automobile auto);
    public ArrayList<String> provideAutoList();
    public Automobile getAuto(String filename);
}
