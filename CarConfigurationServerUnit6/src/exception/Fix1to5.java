package exception;

import util.AutoUtil;
import model.Automobile;

public class Fix1to5 {
    private String file;
    /**
     * Constructor.
     * @param filename
     */
    public Fix1to5(String filename) {
        this.file = filename;
    }
    /**
     * Fix exception 1.
     * @param e
     * @return
     */
    protected Automobile fix1(int e) {
        AutoUtil auto = new AutoUtil();
        return auto.fixErrors(this.file);
    }
    /**
     * Fix exception 2.
     * @param e
     * @return
     */
    protected Automobile fix2(int e) {
        AutoUtil auto = new AutoUtil();
        return auto.fixErrors(this.file);
    }
    /**
     * Fix exception 3.
     * @param e
     * @return
     */
    protected Automobile fix3(int e) {
        AutoUtil auto = new AutoUtil();
        return auto.fixErrors(this.file);
    }
    /**
     * Fix exception 4.
     * @param e
     * @return
     */
    protected Automobile fix4(int e) {
        AutoUtil auto = new AutoUtil();
        return auto.fixError4();
    }
    /**
     * Fix exception 5.
     * @param e
     * @return
     */
    protected Automobile fix5(int e) {
        AutoUtil auto = new AutoUtil();
        return auto.fixErrors(this.file);
    }
    
}
