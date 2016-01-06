package util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;

import model.Automobile;
import exception.AutoException;

/**
 * AutoUtil class is defined to read car configuration from text file and generate the Automotive object.
 * Also, it is used to do the serialization, both object output and object input.
 * And it can fix the exceptions.
 * @author willQian
 *
 */
public class AutoUtil 
{
    /**
     * Change from property files to txt file so that can be used in the future.
     * @param prop
     * @return
     */
    public String changeFromPropertiesFileToTxtFile(Properties prop) {
        String CarMake = prop.getProperty("CarMake"); //this is how you read a property. It is like gettting a value from HashTable.
        String filename = null;
        if(!CarMake.equals(null))
        {
            String CarModel = prop.getProperty("CarModel");
            File myFile = new File(CarMake + CarModel + ".txt");
            filename = CarMake + CarModel;
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(myFile));
                writer.write(CarMake + "\r\n");
                writer.write(CarModel + "\r\n");
                String basePrice = prop.getProperty("BasePrice");
                writer.write("$" + basePrice + "\r\n");
                for (int i = 1; i <= 5; i++) {
                    Integer count = 0;
                    ArrayList<String> optionValue = new ArrayList<String>();
                    ArrayList<String> optionPrice = new ArrayList<String>();
                    int s = 97;
                    char ct = (char) s;
                    while (prop.containsKey("OptionValue" + i + ct)) {
                        optionValue.add(prop.getProperty("OptionValue" + i + ct));
                        optionPrice.add(prop.getProperty("OptionPrice" + i + ct));
                        count++;
                        s++;
                        ct = (char) s;
                    }
                    writer.write(count.toString());
                    for (int j = 0; j < optionValue.size(); j++) {
                        writer.write("," + optionValue.get(j) + " " + "$");
                        writer.write(optionPrice.get(j));
                    }
                    writer.write("\r\n");
                }
                writer.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return filename;
    }
    /**
     * Read car configuration from text file and generate the Automotive object.
     * @param autoName
     * @return
     * @throws AutoException
     */
    public Automobile readFileAndBuildAutoObject(String autoName) throws AutoException
    {
        Automobile motor = null;
        String[][] optionName = new String[5][];
        double[][] optionPrice = new double[5][];
        try {
                FileReader file = new FileReader(autoName + ".txt");
                BufferedReader buff = new BufferedReader(file);
                boolean eof = false;
                int opsetSize = 5, flag = 1, count = 0, num;
                int[] optionSize = new int[5];
                double baseprice = 0;
                String carMake = null;
                String carModel = null;
                String[] optionSetName = {"Color", "Transmission", "Brakes", "Side Impact Air Bags", "Power Moonroof"};
                String s = null;
                
                while (!eof) 
                {
                    String temp = buff.readLine();
                    if ((temp == null) && (count == 5)) {
                        eof = true;
                    }
                    else if(((temp == null) || (temp.equals(""))) && (count < 5)) {
                        buff.close();
                        throw new AutoException(2);
                    }
                    else if (flag == 1) {
                        carMake = temp;
                        flag++;
                    }
                    else if (flag == 2) {
                        carModel = temp;
                        flag++;
                    }
                    else if (flag == 3) {
                        if (temp.equals("$")) {
                            buff.close();
                            throw new AutoException(1);
                        }
                        else {
                            baseprice = Double.parseDouble(temp.substring(1));
                            flag++;
                        }
                    }
                    else {
                        StringTokenizer st = new StringTokenizer(temp,",");
                        num = Integer.parseInt(st.nextToken());
                        optionSize[count] = num;
                        optionName[count] = new String[num];
                        optionPrice[count] = new double[num];
                        
                        int num2 = 0;
                        while (st.hasMoreTokens()) 
                        {
                            s = st.nextToken();
                            int indx = s.indexOf("$");
                            optionName[count][num2] = s.substring(0, indx - 1);
                            String p = s.substring(indx);
                            if (p.equals("$")) {
                                buff.close();
                                throw new AutoException(5);
                            }
                            else {
                                optionPrice[count][num2] = Double.parseDouble(s.substring(indx + 1));
                            }
                            num2++;
                        }
                        if (num2 != num) {
                            buff.close();
                            throw new AutoException(3);
                        }
                        count++;
                    }
                }
                motor = new Automobile(carMake, carModel, opsetSize, autoName, baseprice, optionSize, optionSetName, optionName, optionPrice);
                buff.close();
        }
        catch (FileNotFoundException e) {
            throw new AutoException(4);
        }
        catch (IOException e) 
        {
            System.out.println("Error ­­ " + e.toString());
        }
            return motor;  
    }
    /**
     * Fix exception 4.
     * @return
     */
    public Automobile fixError4()
    {
        Automobile auto = null;
        try {
            AutoException ex4 = new AutoException(4);
            ex4.logException();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Missing filename or wrong filename. Please enter the file name again:");
            String s = br.readLine();
            auto = fixErrors(s);
            return auto;
        }
        catch (IOException e) {
            System.out.println("Error ­­ " + e.toString());
        }
        return auto;
    }
    /**
     * Fix all the five exceptions exception exception 4.
     * @param autoName
     * @return
     */
    public Automobile fixErrors(String autoName)
    {
        Automobile motor = null;
        String[][] optionName = new String[5][];
        double[][] optionPrice = new double[5][];
        try {
                FileReader file = new FileReader(autoName + ".txt");
                BufferedReader buff = new BufferedReader(file);
                boolean eof = false;
                int opsetSize = 5, flag = 1, count = 0, num;
                int[] optionSize = new int[5];
                double baseprice = 0;
                String carMake = null;
                String carModel = null;
                String[] optionSetName = {"Color", "Transmission", "Brakes", "Side Impact Air Bags", "Power Moonroof"};
                String s = null;
                
                while (!eof) 
                {
                    String temp = buff.readLine();
                    if ((temp == null) && (count == 5)) {
                        eof = true;
                    }
                    else if(((temp == null) || (temp.equals(""))) && (count < 5)) {
                      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                      AutoException ex2 = new AutoException(2);
                      ex2.logException();
                      System.out.println("Missing the optionset data. Please enter the optionset data:");
                      temp = br.readLine();
                      StringTokenizer st = new StringTokenizer(temp,",");
                      num = Integer.parseInt(st.nextToken());
                      optionSize[count] = num;
                      optionName[count] = new String[num];
                      optionPrice[count] = new double[num];
                      
                      int num2 = 0;
                      while (st.hasMoreTokens()) 
                      {
                          s = st.nextToken();
                          int indx = s.indexOf("$");
                          optionName[count][num2] = s.substring(0, indx - 1);
                          String p = s.substring(indx);
                          if (p.equals("$")) {
                              AutoException ex5 = new AutoException(5);
                              ex5.logException();
                              BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
                              System.out.println("Missing price for option data. Please enter the price:");
                              String str = br2.readLine();
                              optionPrice[count][num2] = Double.parseDouble(str);
                          }
                          else {
                              optionPrice[count][num2] = Double.parseDouble(s.substring(indx + 1));
                          }
                          num2++;
                      }
                      count++;
                    }
                    else if (flag == 1) {
                        carMake = temp;
                        flag++;
                    }
                    else if (flag == 2) {
                        carModel = temp;
                        flag++;
                    }
                    else if (flag == 3) {
                        if (temp.equals("$")) {
                            AutoException ex1 = new AutoException(1);
                            ex1.logException();
                            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                            System.out.println("Missing the price of the car. Please enter the price:");
                            String str = br.readLine();
                            baseprice = Double.parseDouble(str);
                        }
                        else {
                            baseprice = Double.parseDouble(temp.substring(1));
                        }
                        flag++;
                    }
                    else {
                        StringTokenizer st = new StringTokenizer(temp,",");
                        num = Integer.parseInt(st.nextToken());
                        optionSize[count] = num;
                        optionName[count] = new String[num];
                        optionPrice[count] = new double[num];
                        
                        int num2 = 0;
                        while (st.hasMoreTokens()) 
                        {
                            s = st.nextToken();
                            int indx = s.indexOf("$");
                            optionName[count][num2] = s.substring(0, indx - 1);
                            String p = s.substring(indx);
                            if (p.equals("$")) {
                                AutoException ex5 = new AutoException(5);
                                ex5.logException();
                                BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
                                System.out.println("Missing price for option data. Please enter the price:");
                                String str = br2.readLine();
                                optionPrice[count][num2] = Double.parseDouble(str);
                            }
                            else {
                                optionPrice[count][num2] = Double.parseDouble(s.substring(indx + 1));
                            }
                            num2++;
                        }
                        if (num2 != num) {
                          BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                          AutoException ex3 = new AutoException(3);
                          ex3.logException();
                          System.out.println("Missing " + (num - num2) + " option data. Please enter the option data:");
                          String str = br.readLine();
                          StringTokenizer st2 = new StringTokenizer(str,",");
                          while (st2.hasMoreTokens()) 
                          {
                              s = st2.nextToken();
                              int indx = s.indexOf("$");
                              optionName[count][num2] = s.substring(0, indx - 1);
                              String p = s.substring(indx);
                              if (p.equals("$")) {
                                  AutoException ex5 = new AutoException(5);
                                  ex5.logException();
                                  BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
                                  System.out.println("Missing price for option data. Please enter the price:");
                                  String str2 = br2.readLine();
                                  optionPrice[count][num2] = Double.parseDouble(str2);
                              }
                              else {
                                  optionPrice[count][num2] = Double.parseDouble(s.substring(indx + 1));
                              }
                              num2++;
                          }
                        }
                        count++;
                    }
                }
                motor = new Automobile(carMake, carModel, opsetSize, autoName, baseprice, optionSize, optionSetName, optionName, optionPrice);
                buff.close();
        }
        catch (IOException e) 
        {
            System.out.println("Error ­­ " + e.toString());
        }
            return motor;
    }
    
    /**
     * Do the serialization.
     * @param auto
     * @param name
     */
    public void serializeAuto(Automobile auto, String name) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(name + ".ser"));
            out.writeObject(auto);
            out.close();
        }catch(Exception e) {
            System.out.print("Error: " + e);
            System.exit(1);
        }
    }
    
    /**
     * Do the deserialization.
     * @param name
     * @return
     */
    public Automobile DeserializeAuto(String name) {
        Automobile auto =null;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(name));
            auto = (Automobile) in.readObject();
            in.close();
            return auto;
        }catch(Exception e) {
            System.out.print("Error: " + e);
            System.exit(1);
        }
        return auto;
    }
    
    public Properties readPropertiesFile(String fileName, String fileType) {
        Properties props= new Properties();
        FileInputStream in;
        try {
            in = new FileInputStream(fileName + "." + fileType);
            props.load(in);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } //This loads the entire file in memory.
        return props;
    }
}
