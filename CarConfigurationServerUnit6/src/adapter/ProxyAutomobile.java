package adapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;

import database.JDBCAdapter;
import exception.AutoException;
import util.AutoUtil;
import model.Automobile;
import model.OptionSet;
/**
 * The class which implements all the methods in other interfaces.
 * @author willQian
 */
public class ProxyAutomobile {
    /**
     * @var Store all the car model in the same map.
     */
    private static LinkedHashMap<String, Automobile> a1 = new LinkedHashMap<String, Automobile>();
    private JDBCAdapter jdbc = new JDBCAdapter("jdbc:mysql://127.0.0.1:3306/sample", "com.mysql.jdbc.Driver", "kaiq", "kaiq");
    /**
     * Build the auto through reading text file.
     * @param filename
     */
    public void BuildAuto(String filename) {
        int carID;
        int OptionSetID;
        try {
            AutoUtil auto = new AutoUtil();
            Automobile car = auto.readFileAndBuildAutoObject(filename);
            a1.put(filename, car);
            carID = jdbc.insertCar(car.getName(), car.getMake(), car.getModel(), car.getBasePrice());
            for (int i = 0; i < car.getOptionSet().size(); i++) {
                OptionSetID = jdbc.insertOptionSet(car.getOptionSetName(i), carID);
                for (int j = 0; j < car.getOptionName(i).size(); j++) {
                    jdbc.insertOption(car.getOptionName(i).get(j), car.getOptionPrice(i).get(j), OptionSetID);
                }
            }
        }catch(AutoException e) {
            a1.put(filename, e.fix(filename)); 
        }
    }
    /**
     * Print the car configuration information of the auto.
     * @param Modelname
     */
    public void printAuto(String Modelname) {
        if (a1.get(Modelname) == null) {
            System.out.println("There is no " + Modelname + "in the database. It is either not created or deleted.");
        } else {
            a1.get(Modelname).print();
        }
    }
    /**
     * Uodate the option set name.
     * @param Modelname
     * @param OptionSetname
     * @param newName
     */
    public void updateOptionSetName(String Modelname, String OptionSetname, String newName) {
        a1.get(Modelname).updateOptionSet(OptionSetname, newName);
    }
    /**
     * Update the option price.
     * @param Modelname
     * @param Optionname
     * @param Option
     * @param newprice
     */
    public void updateOptionPrice(String Modelname, String OptionSetname, String Option, double newprice) {
        a1.get(Modelname).updateOptionPrice(OptionSetname, Option, newprice);
    }
    /**
     * Record the choice the user has made.
     * @param Modelname
     * @param setName
     * @param optionName
     */
    public void makeChoice(String Modelname, String[] setName, String[] optionName) {
        for (int i = 0; i < setName.length; i++) {
            a1.get(Modelname).setOptionChoice(setName[i], optionName[i]);
        }
    }
    /**
     * Get the choice and corresponding price the user has chosen.
     * @param Modelname
     * @param setName
     * @return choice and corresponding price the user has chosen
     */
    public String[] getChoiceAndPrice(String Modelname, String[] setName) {
        String[] temp = new String[setName.length];
        for (int i = 0; i < setName.length; i++) {
            temp[i] = a1.get(Modelname).getOptionChoice(setName[i]) + " $" + a1.get(Modelname).getOptionChoicePrice(setName[i]);
        }
        return temp;
    }
    /**
     * Get the total price based on the choices the user has made.
     * @param Modelname
     * @return total price
     */
    public double getFinalPrice(String Modelname) {
        return a1.get(Modelname).getTotalPrice();
    }
    /**
     * Get the auto list.
     * @return the auto list.
     */
    public LinkedHashMap<String, Automobile> getAutoList() {
        return this.a1;
    }
    /**
     * Update the option set and do the same thing in database.
     * @param carName
     * @param oldName
     * @param newName
     */
    public synchronized void updateOptionSet(String carName, String oldName, String newName) {
        getAutoList().get(carName).updateOptionSet(oldName, newName);
        jdbc.updateOptionSet(carName, oldName, newName);
    }
    /**
     * Update the option name and price and do the same thing in database.
     * @param carName
     * @param OptionSetname
     * @param oldOption
     * @param newOption
     * @param newprice
     */
    public synchronized void updateOptionNameAndPrice(String carName, String OptionSetname, String oldOption, String newOption, double newprice) {
        getAutoList().get(carName).updateOptionNameAndPrice(OptionSetname, oldOption, newOption, newprice);
        jdbc.updateOptionNameAndPrice(carName, OptionSetname, oldOption, newOption, newprice);
    }
    /**
     * Add one car model into the list.
     * @param filename
     * @param auto
     */
    public void addAutomobileObject(String filename, Automobile auto) {
        a1.put(filename, auto);
    }
    /**
     * Get the car model list.
     * @return
     */
    public ArrayList<String> provideAutoList() {
        Set<String> s = a1.keySet();
        ArrayList<String> al = new ArrayList<String>();
        for (String i : s) {
            al.add(i);
        }
        return al;
    }
    /**
     * Get the car model.
     * @param filename
     * @return
     */
    public Automobile getAuto(String filename) {
        return a1.get(filename);
    }
    /**
     * Delete the option set name and do the same thing in database.
     * @param Modelname
     * @param OptionSetname
     */
    public void deleteOptionSet(String carName, String OptionSetName) {
        getAutoList().get(carName).deleteOptionSet(OptionSetName);
        jdbc.deleteOptionSet(carName, OptionSetName);
    }
    /**
     * Delete the option price and do the same thing in database.
     * @param Modelname
     * @param Optionname
     * @param Option
     */
    public void deleteOption(String carName, String OptionSetName, String Option) {
        getAutoList().get(carName).deleteOption(OptionSetName, Option);
        jdbc.deleteOption(carName, OptionSetName, Option);
    }
    /**
     * Delete one car model and do the same thing in database.
     * @param carName
     */
    public void deleteCar(String carName) {
        getAutoList().remove(carName);
        jdbc.deleteCar(carName);
    }
}
