package adapter;
/**
 * Update the car configuration information.
 * @author willQian
 */
public interface UpdateAuto {
    /**
     * Update the option set name.
     * @param Modelname
     * @param OptionSetname
     * @param newName
     */
    public void updateOptionSetName(String Modelname, String OptionSetname, String newName);
    /**
     * Update the option price.
     * @param Modelname
     * @param Optionname
     * @param Option
     * @param newprice
     */
    public void updateOptionPrice(String Modelname, String Optionname, String Option, double newprice);

}
