package drive;

import adapter.BuildAuto;


/**
 * Drive class is used to test whether the operation on database is right, like create, insert, update and delete.
 * @author willQian
 */
public class Drive {
    public static void main(String [] args) {
        BuildAuto ca = new BuildAuto();
        ca.BuildAuto("FordZTW");
        ca.BuildAuto("NissanAltima");
        ca.BuildAuto("HondaAccord");
        //update option set
        ca.updateOptionSet("FordZTW", "Color", "New Color");
        ca.updateOptionSet("HondaAccord", "Brakes", "New Brakes");
        //update option
        ca.updateOptionNameAndPrice("FordZTW", "New Color", "Liquid Grey Clearcoat Metallic", "Liquid Grey", 10);
        ca.updateOptionNameAndPrice("HondaAccord", "Transmission", "Automatic", "Automatic and Manual", 219);
        //delete option set
        ca.deleteOptionSet("FordZTW", "Transmission");
        ca.deleteOptionSet("HondaAccord", "Power Moonroof");
        //delete option
        ca.deleteOption("FordZTW", "New Color", "Fort Knox Gold Clearcoat Metallic");
        ca.deleteOption("HondaAccord", "Side Impact Air Bags", "not present");
        //delete car
        ca.deleteCar("NissanAltima");
        //print out the result
        ca.printAuto("FordZTW");
        System.out.println();
        ca.printAuto("NissanAltima");
        System.out.println();
        ca.printAuto("HondaAccord");
    }
}
