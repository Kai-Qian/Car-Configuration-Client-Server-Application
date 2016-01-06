package model;

import java.io.Serializable;
import java.util.ArrayList;

import exception.AutoException;

/**
 * OptionSet is the class to save the Option objects and name of property.
 * @author willQian
 *
 */
@SuppressWarnings("serial")
public class OptionSet implements Serializable {
    private ArrayList<Option> opt;
    private String name;
    private Option choice;
    /**
     * Default constructor.
     */
    protected OptionSet() {}
    /**
     * Constructor.
     * @param n
     * @param size
     * @param optionName
     * @param optionPrice
     */
    protected OptionSet(String n, int size, String[] optionName, double[] optionPrice)
    {
        opt = new ArrayList<Option>(size);
        for(int i = 0; i < size; i++)
            opt.add(new Option(optionName[i], optionPrice[i]));
        name = n;
    }
    /**
     * Set the option set name.
     * @param n
     */
    protected void setName(String n) {
        this.name = n;
    }
    /**
     * Get the option set name.
     * @return
     */
    protected String getName() {
        return this.name;
    }
    /**
     * Get the option through index.
     * @param index
     * @return
     */
    protected Option getOption(int index) {
        return this.opt.get(index);
    }
    /**
     * Get the option arraylist.
     * @return
     */
    protected ArrayList<Option> getOption() {
        return this.opt;
    }
    /**
     * Get the choice of option.
     * @return
     */
    protected Option getOptionChoice() {
        return this.choice;
  
    }
    /**
     * Set the choice of option.
     * @param optionName
     */
    protected void setOptionChoice(String optionName) {
        this.choice = this.findOption(optionName);
    }
    /**
     * Set Option according to the name.
     * @param o
     * @param n
     */
    protected void setOption(Option o, String n) {
        for (int i = 0; i < this.opt.size(); i++) {
            if (this.opt.get(i).getName() == n) {
                this.opt.set(i, o);
            }
        }
    }
    /**
     * Set Option according to the index.
     * @param o
     * @param index
     * @throws AutoException
     */
    protected void setOption(Option o, int index) throws AutoException {
        if (index >= this.opt.size()) {
            throw new AutoException("The index is out of the boundary of the Option. Wrong.");
        }
        this.opt.set(index, o);
    }
    /**
     * Set the whole Option array.
     * @param o
     * @throws AutoException
     */
    protected void setOption(Option[] o) throws AutoException {
        if (o.length != this.opt.size()) {
            throw new AutoException("These two options don't have the same length. Wrong.");
        }
        for (int i = 0; i < o.length; i++) {
            this.opt.set(i, o[i]);
        }
    }
    
    /**
     * Find the Option through name.
     * @param n
     * @return
     */
    protected Option findOption(String n) {
        for (Option i:opt)
        {
            if (i.getName().equals(n)) return i;
        }
        return null;
    }
    /**
     * Find the Option through index.
     * @param index
     * @return
     * @throws AutoException
     */
    protected Option findOption(int index) throws AutoException {
        if (index >= this.opt.size()) {
            throw new AutoException("The index is out of the boundary of the Option. Wrong.");
        }
        return this.opt.get(index);
    }
    /**
     * Delete the last element of the Option array.
     */
    protected void deleteOption() {
        int l = this.opt.size();
        this.opt.remove(l - 1);
    }
    public void deleteOption(String name) {
        for (Option i : opt) {
            if (i.getName().equals(name)) {
                opt.remove(i);
                break;
            }
        }
    }
    /**
     * Delete the element of the Option array through index.
     * @param index
     * @throws AutoException
     */
    protected void deleteOption(int index) throws AutoException {
        if (index >= this.opt.size()) {
            throw new AutoException("The index is out of the boundary of the Option. Wrong.");
        }
        this.opt.remove(index);
    }
    /**
     * Delete the whole Optiont array.
     */
    protected void deleteWholeOption() {
        for (int i = 0; i < this.opt.size(); i++) {
            this.opt.clear();
        }
    }
    /**
     * Update Option's name and price through Option's old name.
     * @param oldName
     * @param newName
     * @param pri
     * @return
     */
    protected boolean updateOption(String oldName, String newName, double pri) {
            Option temp = this.findOption(oldName);
            temp.setName(newName);
            temp.setPrice(pri);
            this.setOption(temp, oldName);
            return true;
    }
    /**
     * Update Option's name through Option's old name.
     * @param oldName
     * @param newName
     * @return
     */
    protected synchronized boolean updateOption(String oldName, String newName) {
            Option temp = this.findOption(oldName);
            temp.setName(newName);
            this.setOption(temp, oldName);
            return true;
    }
    /**
     * Update Option's price through Option's old name.
     * @param oldName
     * @param pri
     * @return
     */
    protected synchronized boolean updateOption(String oldName, double pri) {
            Option temp = this.findOption(oldName);
            temp.setPrice(pri);
            this.setOption(temp, oldName);
            return true;
    }
    /**
     * Update Option's name and price through index.
     * @param index
     * @param newName
     * @param pri
     * @return
     */
    protected boolean updateOption(int index, String newName, double pri) {
        try{
            Option temp = this.findOption(index);
            temp.setName(newName);
            temp.setPrice(pri);
            this.setOption(temp, index);
            return true;
        }
        catch (AutoException e) {
            return false;
        }
    }
    /**
     * Update Option's name through index.
     * @param index
     * @param newName
     * @return
     */
    protected boolean updateOption(int index, String newName) {
        try{
            Option temp = this.findOption(index);
            temp.setName(newName);
            this.setOption(temp, index);
            return true;
        }
        catch (AutoException e) {
            return false;
        }
    }
    /**
     * Update Option's price through index.
     * @param index
     * @param pri
     * @return
     */
    protected boolean updateOption(int index, double pri) {
        try{
            Option temp = this.findOption(index);
            temp.setPrice(pri);
            this.setOption(temp, index);
            return true;
        }
        catch (AutoException e) {
            return false;
        }
    }
    /**
     * Print the OptionSet's name and Option.
     */
    protected void print() {
        StringBuffer s1 = new StringBuffer();
        s1.append(this.getName()).append(": ");
        System.out.println(s1);
        for (int i = 0; i < opt.size(); i++) {
            StringBuffer s2 = new StringBuffer();
            s2.append("Option").append(i + 1).append(": ");
            System.out.print(s2);
            this.opt.get(i).print();
        }
    }
    
    /**
     * Option class is defined to save the option of the car configuration, like name and price of property.
     * @author willQian
     *
     */
    protected class Option  implements Serializable{
        private String name;
        private double price;
        /**
         * Constructor.
         * @param Name
         * @param Price
         */
        protected Option(String Name, double Price) {
            this.name = Name;
            this.price = Price;
        }
        /**Get option name.
         * 
         * @return
         */
        protected String getName() {
            return this.name;
        }
        /**
         * Get option price.
         * @return
         */
        protected double getPrice() {
            return this.price;
        }
        /**
         * Set option price.
         * @param pri
         */
        protected void setPrice(double pri) {
            this.price = pri;
        }
        /**
         * Set option name.
         * @param n
         */
        protected void setName(String n) {
            this.name = n;
        }
        /**
         * Print the option information.
         */
        protected void print() {
            StringBuffer s = new StringBuffer();
            s.append(this.getName()).append(" $").append(this.getPrice());
            System.out.println(s);
        }
    }
}