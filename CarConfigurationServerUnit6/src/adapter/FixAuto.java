package adapter;

import model.Automobile;
/**
 * To deal with the exception.
 * @author willQian
 */
public interface FixAuto {
    /**
     * Fix different kinds of exception to make sure the program can still work.
     * @param filename
     * @return the automobile object.
     */
    public Automobile fix(String filename);
}
