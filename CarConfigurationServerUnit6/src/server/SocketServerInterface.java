package server;
/**
 * Interface used to declare methods.
 * @author willQian
 */
public interface SocketServerInterface
{
    boolean openConnection();
    void handleSession();
    void closeSession();
}