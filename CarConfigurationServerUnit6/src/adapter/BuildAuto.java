package adapter;

import scale.EditAuto;
import server.AutoServer;

/**
 * The API which has nobody, but can be used directly, since it implements the interface and extends the class.
 * @author willQian
 *
 */
public class BuildAuto extends ProxyAutomobile implements CreateAuto, UpdateAuto, DeleteAuto, ChooseAuto, AutoServer, EditAuto{
    

}
