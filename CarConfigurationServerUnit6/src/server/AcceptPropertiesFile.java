package server;

import java.util.Properties;

import model.Automobile;

/**
 * Interface used to declare the methods of accepting properties files.
 * @author willQian
 */
public interface AcceptPropertiesFile {
    public Automobile acceptProperties(Properties prop);
}
