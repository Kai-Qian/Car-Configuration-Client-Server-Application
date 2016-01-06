package scale;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import model.Automobile;
import model.OptionSet;
import adapter.BuildAuto;

public class EditOptions extends Thread {
    private EditAuto ea = new BuildAuto();
    private ArrayList<String> oldOptionsetName = new ArrayList<String>();
    private ArrayList<String> newOptionsetName = new ArrayList<String>();
    private String optionsetName = new String();
    private ArrayList<String> oldOptionName = new ArrayList<String>();
    private ArrayList<String> newOptionName = new ArrayList<String>();
    private ArrayList<Double> newOptionPrice = new ArrayList<Double>();
    private String carName;
    /**
     * Constructor.
     * @param temp
     * @param Name
     * @param oldOpstNm
     * @param newOpstNm
     * @param opsetName
     * @param oldOptNm
     * @param newOptNm
     * @param newOptPr
     */
    public EditOptions(BuildAuto temp, String Name, String[] oldOpstNm, String[] newOpstNm, String opsetName, String[] oldOptNm, String[] newOptNm, double[] newOptPr) {
        this.ea = temp;
        this.carName = Name;
        this.optionsetName = opsetName;
        for (int i = 0; i < oldOpstNm.length; i++) {
            this.oldOptionsetName.add(oldOpstNm[i]);
            this.newOptionsetName.add(newOpstNm[i]);
        }
        for (int i = 0; i < oldOptNm.length; i++) {
            this.oldOptionName.add(oldOptNm[i]);
            this.newOptionName.add(newOptNm[i]);
            this.newOptionPrice.add(newOptPr[i]);
        }
         
    }
    /**
     * Get old option set name.
     * @return
     */
    public ArrayList<String> getOldOptionsetName() {
        return oldOptionsetName;
    }
    /**
     * Set old option set name.
     * @param oldOptionsetName
     */
    public void setOldOptionsetName(String[] oldOptionsetName) {
        for (int i = 0; i < oldOptionsetName.length; i++) {
            this.oldOptionsetName.add(oldOptionsetName[i]);
        }
    }
    /**
     * Get new option set name.
     * @return
     */
    public ArrayList<String> getNewOptionsetName() {
        return newOptionsetName;
    }
    /**
     * Set new option set name.
     * @param newOptionsetName
     */
    public void setNewOptionsetName(String[] newOptionsetName) {
        for (int i = 0; i < newOptionsetName.length; i++) {
            this.newOptionsetName.add(newOptionsetName[i]);
        }
    }
    /**
     * Get new option set name.
     * @return
     */
    public String getOptionsetName() {
        return optionsetName;
    }
    /**
     * Set option set name.
     * @param optionsetName
     */
    public void setOptionsetName(String optionsetName) {
        this.optionsetName = optionsetName;
    }
    /**
     * Get old option name.
     * @return
     */
    public ArrayList<String> getOldOptionName() {
        return oldOptionName;
    }
    /**
     * Set old option set name.
     * @param oldOptionName
     */
    public void setOldOptionName(String[] oldOptionName) {
        for (int i = 0; i < oldOptionName.length; i++) {
            this.oldOptionName.add(oldOptionName[i]);
        }
    }
    /**
     * Get new option name.
     * @return
     */
    public ArrayList<String> getNewOptionName() {
        return newOptionName;
    }
    /**
     * Set new option name.
     * @param newOptionName
     */
    public void setNewOptionName(String[] newOptionName) {
        for (int i = 0; i < newOptionName.length; i++) {
            this.newOptionName.add(newOptionName[i]);
        }
    }
    /**
     * Get old option price.
     * @return
     */
    public ArrayList<Double> getOldOptionPrice() {
        return newOptionPrice;
    }
    /**
     * Set the 
     * @param newOptionPrice
     */
    public void setOldOptionPrice(Double[] newOptionPrice) {
        for (int i = 0; i < newOptionPrice.length; i++) {
            this.newOptionPrice.add(newOptionPrice[i]);
        }
    }
    /**
     * Get car name.
     * @return
     */
    public String getCarName() {
        return carName;
    }
    /**
     * Set car name.
     * @param name
     */
    public void setCarName(String name) {
        this.carName = name;
    }
    /**
     * The run function which has the task in it to do when the thread is runing.
     */
    public void run() {
        for (int i = 0; i < this.oldOptionsetName.size(); i++) {
            ea.updateOptionSet(this.carName, this.oldOptionsetName.get(i), this.newOptionsetName.get(i));
        }
        for (int i = 0; i < this.oldOptionName.size(); i++) {
            ea.updateOptionNameAndPrice(this.carName, this.optionsetName, this.oldOptionName.get(i), this.newOptionName.get(i), this.newOptionPrice.get(i));
        }
    }

}
