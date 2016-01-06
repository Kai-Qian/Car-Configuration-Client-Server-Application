package database;
/**
 * An abstract class which has the general abstract methods
 * @author willQian
 */
public abstract class Adapter {
    public abstract void createCarTable();
    public abstract int insertCar(String carName, String carMake, String carModel, double basePrice);
    public abstract int insertOptionSet(String optionSetName, int carID);
    public abstract boolean insertOption(String optionName, double optionPrice, int optionSetID);
}
