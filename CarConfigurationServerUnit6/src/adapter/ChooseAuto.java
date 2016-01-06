package adapter;
/**
 * Interface used to let user to make the choice of the car configuration and can get the choice and 
 * corresponding price. Also can get the total price.
 * @author willQian
 */
public interface ChooseAuto {
    /**
     * Record the choice the user has made.
     * @param Modelname
     * @param setName
     * @param optionName
     */
    public void makeChoice(String Modelname, String[] setName, String[] optionName);
    /**
     * Get the choice and corresponding price the user has chosen.
     * @param Modelname
     * @param setName
     * @return choice and corresponding price the user has chosen
     */
    public String[] getChoiceAndPrice(String Modelname, String[] setName);
    /**
     * Get the total price based on the choices the user has made.
     * @param Modelname
     * @return total price
     */
    public double getFinalPrice(String Modelname);
}
