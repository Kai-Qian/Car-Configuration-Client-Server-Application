package server;

import java.net.*;

import java.util.Properties;
import java.io.*;

import model.Automobile;

/**
 * Server to handle communication with client.
 * @author willQian
 */
public class DefaultSocketServer extends Thread implements SocketServerInterface, SocketServerConstants{
    private BuildCarModelOptions buildCarOption = new BuildCarModelOptions();
    private PrintWriter out2 = null;
    private BufferedReader in2 = null;
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;
    private Socket serSocket = null;
    private int iPort;
    /**
     * Constructor.
     * @param serSocket
     */
    public DefaultSocketServer(Socket serSocket) {       
        this.serSocket = serSocket;
    }
    /**
     * Constructor.
     * @param iPort
     */
    public DefaultSocketServer(int iPort) {       
            setPort (iPort);
    }
    /**
     * Run function.
     */
    public void run() {
       if (openConnection()) {
          handleSession();
          closeSession();
       }
    }
    /**
     * Open the session connection.
     */
    public boolean openConnection() {
        try {
          out = new ObjectOutputStream(serSocket.getOutputStream());
          in = new ObjectInputStream(serSocket.getInputStream());
          out2 = new PrintWriter(serSocket.getOutputStream(), true);
          in2 = new BufferedReader(new InputStreamReader(serSocket.getInputStream()));
          } catch (IOException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
              return false;
          }
       return true;
    }
    /**
     * Handle the information sent and received.
     */
    public void handleSession() {
          String input = null;
          String outputLine;
        
          Automobile auto = null;
          Properties prop =null;
          int i = 1;
          while (!((input = handleStringInput()).equals("3"))) {
              if (input.equals("1")) {
                  outputLine = "Hello, client. Ready to accpet the properties files.";
                  sendStringOutput(outputLine);
                  while ((prop = handleObjectInput()) != null) {
                      auto = buildCarOption.acceptProperties(prop);
                      buildCarOption.addAutomobileObject(buildCarOption.getFileNameTemp(), auto);
                      outputLine = "Accept the " + i + " properties file. Created the Automobile.";
                      sendStringOutput(outputLine);
                    i++;
                  }
              } else if (input.equals("2")) {
                  buildCarOption.provideAutoList(out2);
                  buildCarOption.sendAuto(out, in2);
              }
          }
          
      }       
      /**
       * Send string through socket.
       * @param strOutput
       */
      public void sendStringOutput(String strOutput){
        out2.println(strOutput);
      }
      /**
       * Receive string through socket.
       * @return
       */
      public String handleStringInput() {
          String strInput = null;
        try {
            strInput = in2.readLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
          return strInput;
      }    
      /**
       * Send object through socket.
       * @param objOutput
       */
      public void sendObjectOutput(Automobile objOutput){
          try {
            out.writeObject(objOutput);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        }
      /**
       * Receive object through socket.
       * @return
       */
        public Properties handleObjectInput() {
            Properties props = null;
          try {
              props = (Properties) in.readObject();
          } catch (IOException | ClassNotFoundException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }
          return props;
        } 
      /**
       * Close the server client session.
       */
      public void closeSession() {
         try {
            in = null;
            in2 = null;
            out = null;
            out2 = null;
            serSocket.close();
         }
         catch (IOException e){
         }       
      }
      /**
       * Set the port number.
       * @param iPort
       */
      public void setPort(int iPort){
              this.iPort = iPort;
      }
}