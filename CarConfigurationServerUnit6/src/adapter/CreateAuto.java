package adapter;
/**
 * Interface used to build auto and print the information of the configuration of the auto.
 * @author willQian
 */
public interface CreateAuto {
    /**
     * Build the auto through reading text file.
     * @param filename
     */
    public void BuildAuto(String filename);
    /**
     * Print the car configuration information of the auto.
     * @param Modelname
     */
    public void printAuto(String Modelname);

}
