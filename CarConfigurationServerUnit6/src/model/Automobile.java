package model;

import java.io.Serializable;
import java.util.ArrayList;

import exception.AutoException;
import model.OptionSet.Option;
/**
 * Automotive is defined to be the class of car configuration. It can store OptionSet array, which is the diffeent
 * options of the car. Also, it can store car's name and base price.
 * @author willQian
 *
 */
@SuppressWarnings("serial")
public class Automobile implements Serializable{ //This class will represent the Model.
    private String name;
    private double baseprice;
    private String make;
    private String model;
    private ArrayList<OptionSet> opset;
    /**
     * Default constructor.
     */
    public Automobile() {}
    /**
     * Constructor.
     * @param OptionSetSize
     * @param Name
     * @param basePrice
     * @param OptionSize
     * @param optionSetName
     * @param optionName
     * @param optionPrice
     */
    public Automobile(String cmake, String cmodel, int OptionSetSize, String Name, double basePrice,int[] OptionSize, String[] optionSetName, String[][] optionName, double[][] optionPrice)
    {
        this.opset = new ArrayList<OptionSet>(OptionSetSize);
        for(int i = 0; i < OptionSetSize; i++) {
            OptionSet temp = new OptionSet(optionSetName[i], OptionSize[i], optionName[i], optionPrice[i]);
            this.opset.add(temp);
        }
        name = Name;
        baseprice = basePrice;
        make = cmake;
        model = cmodel;
    }
    /**
     * Get the car's name.
     * @return
     */
    public String getName() {
        return this.name;
    }
    /**
     * Get the base price.
     * @return
     */
    public double getBasePrice() {
        return this.baseprice;
    }
    /**
     * Get the car's make.
     * @return
     */
    public String getMake() {
        return this.make;
    }
    /**
     * Get the car's model.
     * @return
     */
    public String getModel() {
        return this.model;
    }
    /**
     * Get the option set through index.
     * @param index
     * @return
     */
    public OptionSet getOptionSet(int index) {
        return this.opset.get(index);
    }
    /**
     * Get the option set name.
     * @param index
     * @return
     */
    public String getOptionSetName(int index) {
        return this.opset.get(index).getName();
    }
    /**
     * Get the option name.
     * @param index
     * @return
     */
    public ArrayList<String> getOptionName(int index) {
        ArrayList<String> optionName = new ArrayList<String>();
        ArrayList<Option> o = this.opset.get(index).getOption();
        for (Option i : o) {
            optionName.add(i.getName());
        }
        return optionName;
    }
    /**
     * Get the option price.
     * @param index
     * @return
     */
    public ArrayList<Double> getOptionPrice(int index) {
        ArrayList<Double> optionPrice = new ArrayList<Double>();
        ArrayList<Option> o = this.opset.get(index).getOption();
        for (Option i : o) {
            optionPrice.add(i.getPrice());
        }
        return optionPrice;
    }
    /**
     * Get the whole option set arraylist.
     * @return
     */
    public ArrayList<OptionSet> getOptionSet() {
        return this.opset;
    }
    /**
     * Get the option choice.
     * @param setName
     * @return
     */
    public String getOptionChoice(String setName) {
        OptionSet o = this.findOptionSet(setName);
        return o.getOptionChoice().getName();
    }
    /**
     * Get the price of the option choice.
     * @param setName
     * @return
     */
    public double getOptionChoicePrice(String setName) {
        OptionSet o = this.findOptionSet(setName);
        return o.getOptionChoice().getPrice();
    }
    /**
     * Get the total price of the car.
     * @return
     */
    public double getTotalPrice() {
        double totalPrice = this.baseprice;
        for (int i = 0; i < this.opset.size(); i++) {
            totalPrice += getOptionChoicePrice(this.opset.get(i).getName());
        }
        return totalPrice;
    }
    /**
     * Set the car's name.
     * @param n
     */
    public void setName(String n) {
        this.name = n;
    }
    /**
     * Set the base price.
     * @param p
     */
    public void setBasePrice(double p) {
        this.baseprice = p;
    }
    /**
     * Set the car's make.
     * @param ma
     */
    public void setMake(String ma) {
        this.make = ma;
    }
    /**
     * Set the car's model.
     * @param mo
     */
    public void setModel(String mo) {
        this.model = mo;
    }
    /**
     * Set the option choice.
     * @param setName
     * @param optionName
     */
    public void setOptionChoice(String setName, String optionName) {
        OptionSet o = this.findOptionSet(setName);
        o.setOptionChoice(optionName);
    }
    /**
     * Set the OptionSet through name.
     * @param o
     * @param n
     */
    public void setOptionSet(OptionSet o, String n) {
        for (int i = 0; i < this.opset.size(); i++) {
            if (opset.get(i).getName() == n) {
                opset.set(i, o);
            }
        }
    }
    /**
     * Set the OptionSet through index.
     * @param o
     * @param index
     * @throws AutoException
     */
    public void setOptionSet(OptionSet o, int index) throws AutoException {
        if (index >= this.opset.size()) {
            throw new AutoException("The index is out of the boundary of the OptionSet. Wrong.");
        }
        this.opset.set(index, o);
    }
    /**
     * Set the whole OptionSet.
     * @param o
     * @throws AutoException
     */
    public void setOptionSet(OptionSet[] o) throws AutoException {
        if (o.length != this.opset.size()) {
            throw new AutoException("These two optionsets don't have the same length. Wrong.");
        }
        for (int i = 0; i < o.length; i++) {
            this.opset.set(i, o[i]);
        }
    }
    /**
     * Find OptionSet through name
     * @param n
     * @return
     */
    public OptionSet findOptionSet(String n) {
        for (OptionSet i:opset)
        {
            if (i.getName().equals(n)) return i;
        }
        return null;
    }
    /**
     * Find OptionSet through index
     * @param n
     * @return
     */
    public OptionSet findOptionSet(int index) throws AutoException {
        if (index >= this.opset.size()) {
            throw new AutoException("The index is out of the boundary of the OptionSet. Wrong.");
        }
        return this.opset.get(index);
    }
    /**
     * Update the option price.
     * @param OptionSetname
     * @param Option
     * @param newprice
     */
    public void updateOptionPrice(String OptionSetname, String Option, double newprice) {
        findOptionSet(OptionSetname).updateOption(Option, newprice);
    }
    /**
     * Update the option name.
     * @param OptionSetname
     * @param oldOption
     * @param newOption
     */
    public void updateOptionName(String OptionSetname, String oldOption, String newOption) {
        findOptionSet(OptionSetname).updateOption(oldOption, newOption);
    }
    /**
     * Update both option name and price.
     * @param OptionSetname
     * @param oldOption
     * @param newOption
     * @param newprice
     */
    public synchronized void updateOptionNameAndPrice(String OptionSetname, String oldOption, String newOption, double newprice) {
        findOptionSet(OptionSetname).updateOption(oldOption, newprice);
        findOptionSet(OptionSetname).updateOption(oldOption, newOption);
    }
    /**
     * Delete one option.
     * @param OptionSetName
     * @param Option
     */
    public void deleteOption(String OptionSetName, String Option) {
        findOptionSet(OptionSetName).deleteOption(Option);
    }
    /**
     * Delete the last element of the OptionSet array.
     */
    public void deleteOptionSet() {
        int l = this.opset.size();
        this.opset.remove(l - 1);
    }
    /**
     * Delete one option set.
     * @param name
     */
    public void deleteOptionSet(String name) {
        for (OptionSet i : opset) {
            if (i.getName().equals(name)) {
                opset.remove(i);
                break;
            }
        }
    }
    /**
     * Delete the element of OptionSet array according to the index.
     * @param index
     * @throws AutoException
     */
    public void deleteOptionSet(int index) throws AutoException {
        if (index >= this.opset.size()) {
            throw new AutoException("The index is out of the boundary of the OptionSet. Wrong.");
        }
        this.opset.remove(index);
    }
    /**
     * Delete the whole OptionSet array.
     */
    public void deleteWholeOptionSet() {
        for (int i = 0; i < this.opset.size(); i++) {
            this.opset.clear();
        }
    }
    /**
     * Update the OptionSet's name and Option array through old name.
     * @param oldName
     * @param newName
     * @param newOption
     * @return
     */
    public boolean updateOptionSet(String oldName, String newName, Option[] newOption) {
        try{
            OptionSet temp = this.findOptionSet(oldName);
            temp.setName(newName);
            temp.setOption(newOption);
            this.setOptionSet(temp, oldName);
            return true;
        }
        catch (AutoException e) {
            return false;
        }
    }
    /**
     * Update the OptionSet's name through old name.
     * @param oldName
     * @param newName
     * @return
     */
    public synchronized boolean updateOptionSet(String oldName, String newName) {
            OptionSet temp = this.findOptionSet(oldName);
            temp.setName(newName);
            this.setOptionSet(temp, oldName);
            return true;
    }
    /**
     * Update the OptionSet's Option array through old name.
     * @param oldName
     * @param newOption
     * @return
     */
    public boolean updateOptionSet(String oldName, Option[] newOption) {
        try{
            OptionSet temp = this.findOptionSet(oldName);
            temp.setOption(newOption);
            this.setOptionSet(temp, oldName);
            return true;
        }
        catch (AutoException e) {
            return false;
        }
    }
    /**
     * Update the OptionSet's name and Option array through index.
     * @param index
     * @param newName
     * @param newOption
     * @return
     */
    public boolean updateOptionSet(int index, String newName, Option[] newOption) {
        try{
            OptionSet temp = this.findOptionSet(index);
            temp.setName(newName);
            temp.setOption(newOption);
            this.setOptionSet(temp, index);
            return true;
        }
        catch (AutoException e) {
            return false;
        }
    }
    /**
     * Update the OptionSet's name through index.
     * @param index
     * @param newName
     * @return
     */
    public boolean updateOptionSet(int index, String newName) {
        try{
            OptionSet temp = this.findOptionSet(index);
            temp.setName(newName);
            this.setOptionSet(temp, index);
            return true;
        }
        catch (AutoException e) {
            return false;
        }
    }
    /**
     * Update the OptionSet's Option array through index.
     * @param index
     * @param newOption
     * @return
     */
    public boolean updateOptionSet(int index, Option[] newOption) {
        try{
            OptionSet temp = this.findOptionSet(index);
            temp.setOption(newOption);
            this.setOptionSet(temp, index);
            return true;
        }
        catch (AutoException e) {
            return false;
        }
    }
    /**
     * Print the Automotive's name, base price and OptionSet.
     */
    public void print() {
        StringBuffer s1 = new StringBuffer();
        s1.append(this.getName()).append("\n").append("Base price: $").append(this.baseprice);
        System.out.println(s1);
        for (int i = 0; i < opset.size(); i++) {
            this.opset.get(i).print();
        }
    }
    
}
