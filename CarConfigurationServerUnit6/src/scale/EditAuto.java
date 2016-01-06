package scale;

import java.util.LinkedHashMap;

import model.Automobile;
/**
 * Interface used to expose the static list in ProxyAutomobile
 * @author willQian
 */
public interface EditAuto {
    /**
     * Get the auto list.
     * @return the auto list.
     */
    public LinkedHashMap<String, Automobile> getAutoList();
    public void updateOptionSet(String carName, String oldName, String newName);
    public void updateOptionNameAndPrice(String carName, String OptionSetname, String oldOption, String newOption, double newprice);
}
