package database;

import java.sql.*;

/**
 * This class is used to manage the database. It can create tables and insert, update and delete car models.
 * @author willQian
 */
public class JDBCAdapter extends Adapter {
    private Connection connection;
    private Statement statement;
    /**
     * Constructor makes the connection and create tables.
     * @param url
     * @param driverName
     * @param user
     * @param passwd
     */
    public JDBCAdapter(String url, String driverName, String user, String passwd) {
        try {
            System.out.println("Opening db connection");
            Class.forName(driverName);

            connection = DriverManager.getConnection(url, user, passwd);
            statement = connection.createStatement();
            createCarTable();
        }
        catch (ClassNotFoundException ex) {
            System.err.println("Cannot find the database driver classes.");
            System.err.println(ex);
        }
        catch (SQLException ex) {
            System.err.println("Cannot connect to this database.");
            System.err.println(ex);
        }
     }
    /**
     * Create tables, which are car's table, option set's table and option's table
     */
    public void createCarTable() {
        String creatCarConfiguration = "create table CarConfiguration (CarID int not null AUTO_INCREMENT," + 
                                                                       "CarName varchar(20) not null," + 
                                                                       "CarMake varchar(20) not null," + 
                                                                       "CarModel varchar(20) not null," + 
                                                                       "CarBasePrice float(2)," + 
                                                                       "PRIMARY KEY(CarID))";
        try {
            statement.execute(creatCarConfiguration);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String creatOptionSet = "create table CarOptionSet (OptionSetID int not null AUTO_INCREMENT," + 
                                                         "OptionSetName varchar(30) not null," + 
                                                         "CarID int not null," + 
                                                         "PRIMARY KEY(OptionSetID)," +
                                                         "FOREIGN KEY(CarID) REFERENCES CarConfiguration(CarID))";
        try {
            statement.execute(creatOptionSet);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String creatOption = "create table CarOption (OptionName varchar(60) not null," + 
                                                   "OptionPrice float(2) not null," + 
                                                   "OptionSetID int not null," + 
                                                   "FOREIGN KEY(OptionSetID) REFERENCES CarOptionSet(OptionSetID))";
        try {
            statement.execute(creatOption);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * insert one car model into the database.
     * @param carName
     * @param carMake
     * @param carModel
     * @param basePrice
     */
    public int insertCar(String carName, String carMake, String carModel, double basePrice) {
        String insertCar = "insert into CarConfiguration(CarName, CarMake, CarModel, CarBasePrice) values('" + carName + "', '" + carMake + "', '" + carModel + "', '" + basePrice + "')";
        try {
            statement.executeUpdate(insertCar, statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }
    /**
     * insert the option set into the database
     * @param optionSetName
     * @param carID
     */
    public int insertOptionSet(String optionSetName, int carID) {
        String insertOptionSet = "insert into CarOptionSet(OptionSetName, CarID) values('" + optionSetName + "', '" + carID + "')";
        try {
            statement.executeUpdate(insertOptionSet, statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }
    /**
     * insert one option
     * @param optionName
     * @param optionPrice
     * @param optionSetID
     */
    public boolean insertOption(String optionName, double optionPrice, int optionSetID) {
        String insertOption = "insert into CarOption(OptionName, OptionPrice, OptionSetID) values('" + optionName + "', '" + optionPrice + "', '" + optionSetID + "')";
        try {
            statement.executeUpdate(insertOption);
            return true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
    /**
     * update one option
     * @param carName
     * @param OptionSetname
     * @param oldOption
     * @param newOption
     * @param newprice
     * @return
     */
    public boolean updateOptionNameAndPrice(String carName, String OptionSetname, String oldOption, String newOption, double newprice) {
        String updateOption = "update CarOption set OptionName = " + "'" + newOption + "', OptionPrice = " + "'" + newprice + 
                "' where OptionName =" + "'" + oldOption + "' and OptionSetID in "
                        + "(select OptionSetID from CarOptionSet where OptionSetName =" + "'" + OptionSetname + "' and CarID in "
                                + "(select CarID from CarConfiguration where CarName =" + "'" + carName + "'));";
        try {
            statement.executeUpdate(updateOption);
            return true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
    /**
     * update one option set
     * @param carName
     * @param oldName
     * @param newName
     * @return
     */
    public boolean updateOptionSet(String carName, String oldName, String newName) {
        String updateOptionSet = "update CarOptionSet set OptionSetName = " + "'" + newName + 
                "' where OptionSetName =" + "'" + oldName + "' and CarID in "
                                + "(select CarID from CarConfiguration where CarName =" + "'" + carName + "');";
        try {
            statement.executeUpdate(updateOptionSet);
            return true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
    /**
     * delete one option set
     * @param carName
     * @param OptionSetName
     * @return
     */
    public boolean deleteOptionSet(String carName, String OptionSetName) {
        String a = "SET FOREIGN_KEY_CHECKS = 0;";
        String b = "SET FOREIGN_KEY_CHECKS = 1;";
        String deleteOptionSet = "delete from CarOptionSet where OptionSetName =" + "'" + OptionSetName + "' and CarID in "
                                + "(select CarID from CarConfiguration where CarName =" + "'" + carName + "');";
        String deleteOption = "delete from CarOption where OptionSetID in "
                + "(select OptionSetID from CarOptionSet where OptionSetName =" + "'" + OptionSetName + "' and CarID in "
                                + "(select CarID from CarConfiguration where CarName =" + "'" + carName + "'));";
        try {
            statement.execute(a);
            statement.execute(deleteOption);
            statement.execute(deleteOptionSet);
            statement.execute(b);
            return true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
    /**
     * delete one option
     * @param carName
     * @param OptionSetName
     * @param Option
     * @return
     */
    public boolean deleteOption(String carName, String OptionSetName, String Option) {
        String a = "SET FOREIGN_KEY_CHECKS = 0;";
        String b = "SET FOREIGN_KEY_CHECKS = 1;";
        String deleteOption = "delete from CarOption where OptionName = " + "'" + Option + "' and OptionSetID in "
                + "(select OptionSetID from CarOptionSet where OptionSetName =" + "'" + OptionSetName + "' and CarID in "
                        + "(select CarID from CarConfiguration where CarName =" + "'" + carName + "'));";
        try {
            statement.execute(a);
            statement.execute(deleteOption);
            statement.execute(b);
            return true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
    /**
     * delete a car model
     * @param carName
     * @return
     */
    public boolean deleteCar(String carName) {
        String a = "SET FOREIGN_KEY_CHECKS = 0;";
        String b = "SET FOREIGN_KEY_CHECKS = 1;";
        String deleteCar = "delete from CarConfiguration where CarName = " + "'" + carName + "';";
        String deleteOptionSet = "delete from CarOptionSet where CarID in "
                + "(select CarID from CarConfiguration where CarName =" + "'" + carName + "');";
        String deleteOption = "delete from CarOption where OptionSetID in "
                + "(select OptionSetID from CarOptionSet where CarID in "
                        + "(select CarID from CarConfiguration where CarName =" + "'" + carName + "'));";
        try {
            statement.execute(a);
            statement.execute(deleteOption);
            statement.execute(deleteOptionSet);
            statement.execute(deleteCar);
            statement.execute(b);
            return true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
}
