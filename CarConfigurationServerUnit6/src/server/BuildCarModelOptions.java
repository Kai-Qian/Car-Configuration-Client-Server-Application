package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;

import model.Automobile;
import exception.AutoException;
import adapter.BuildAuto;
import scale.EditAuto;
import util.AutoUtil;

/**
 * Class to build car model and options.
 * @author willQian
 */
public class BuildCarModelOptions implements AcceptPropertiesFile{
    private AutoServer auto = new BuildAuto();
    private AutoUtil autoUtil = new AutoUtil();
    private String fileNameTemp = null;
    private ServerSocket serverSocket = null;
    private int port;
    /**
     * Constructor.
     * @param p
     */
    public BuildCarModelOptions (int p) {
        this.port = p;
    }
    /**
     * Constructor.
     */
    public BuildCarModelOptions () {
    }
    /**
     * Get file name.
     * @return
     */
    public String getFileNameTemp() {
        return fileNameTemp;
    }
    /**
     * Provide car list.
     * @param out2
     */
    public void provideAutoList(PrintWriter out2) {
        // TODO Auto-generated method stub
        String outputLine;
        ArrayList<String> al = this.auto.provideAutoList();
        outputLine = "There are " + al.size() + " Automobiles. Which one do you want to configure?";
        out2.println(outputLine);
        for (int j = 0; j < al.size(); j++) {
            outputLine = (j+1) + ". " + al.get(j);
            out2.println(outputLine);
        }
        outputLine = "Please choose the number.";
        out2.println(outputLine);
    }
    /**
     * Send the auto object to client.
     * @param out
     * @param in2
     */
    public void sendAuto(ObjectOutputStream out, BufferedReader in2) {
        // TODO Auto-generated method stub
        String inputLine;
        ArrayList<String> al = this.auto.provideAutoList();
        try {
            if ((inputLine = in2.readLine()) != null) {
                int i = Integer.parseInt(inputLine);
                if ( i <= al.size()) {
                    out.writeObject(auto.getAuto(al.get(i - 1)));
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * Add car model to the list.
     * @param filename
     * @param automob
     */
    public void addAutomobileObject(String filename, Automobile automob) {
        auto.addAutomobileObject(filename, automob);
    }
    /**
     * Open socket.
     */
    public void open() {
        try {
            serverSocket = new ServerSocket(this.port);  
        }
        catch(IOException socketError) {
            System.err.println("Could not listen on port: " + port);
        }
    }
    /**
     * Start the thread.
     */
    public void acceptObject() {
        DefaultSocketServer d1;
        try {
            d1 = new DefaultSocketServer(serverSocket.accept());
            Thread t = new Thread(d1);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Accept the properties files.
     * @param prop
     */
    @Override
    public Automobile acceptProperties(Properties prop) {    
    // TODO Auto-generated method stub
        Automobile automobile = null;
        String fileName = autoUtil.changeFromPropertiesFileToTxtFile(prop);
        this.fileNameTemp = fileName;
        try {
            automobile = autoUtil.readFileAndBuildAutoObject(fileName);
        } catch (AutoException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return automobile;
    }

}
