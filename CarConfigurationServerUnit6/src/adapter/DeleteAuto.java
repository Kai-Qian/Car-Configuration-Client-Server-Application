package adapter;
/**
 * Interface which has the methods of deleting.
 * @author willQian
 */
public interface DeleteAuto {
    /**
     * Delete the option set name.
     * @param carName
     * @param OptionSetName
     */
    public void deleteOptionSet(String carName, String OptionSetName);
    /**
     * Delete the option price.
     * @param carName
     * @param OptionSetName
     * @param Option
     */
    public void deleteOption(String carName, String OptionSetName, String Option);
    /**
     * Delete the car.
     * @param carName
     */
    public void deleteCar(String carName);
}
