package exception;

import adapter.FixAuto;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import util.AutoUtil;
import model.Automobile;


/**
 * CustomException class is defined to make programmer's define their own exceptions.
 * @author willQian
 *
 */
@SuppressWarnings("serial")
public class AutoException extends Exception implements FixAuto {
        private int errorno;
        private String errormsg;
        private File writename = new File("ExceptionLog.txt"); 
        /**
         * Enumation of all exception.
         * @author willQian
         */
        private enum errnoNumAndMsg {
            error1("Missing price for Automobile in text file.",1), 
            error2("Missing OptionSet data (or part of it).",2), 
            error3("Missing Option data.",3), 
            error4("Missing filename or wrong filename.",4), 
            error5("Missing price for option data.",5);
            private String errorMessage;
            private int errorNum;
            /**
             * Constructor.
             * @param msg
             * @param num
             */
            private errnoNumAndMsg(String msg, int num) {
                this.errorMessage = msg;
                this.errorNum = num;
            }
            /**
             * Get the error message through error number.
             * @param num
             * @return
             */
            private static String getErrorMessage(int num) {
                for (errnoNumAndMsg c : errnoNumAndMsg.values()) {
                    if (c.getErrorNum() == num) {
                        return c.errorMessage;
                    }
                }
                return null;
            }
            /**
             * Get the error number.
             * @param msg
             * @return
             */
            private static int getErrorNum(String msg) {
                for (errnoNumAndMsg c : errnoNumAndMsg.values()) {
                    if (c.getErrorMessage() == msg) {
                        return c.errorNum;
                    }
                }
                return 0;
            }
            /**
             * Get the error message.
             * @return
             */
            private String getErrorMessage() {
                return errorMessage;
            }
            /**
             * Set the error message.
             * @param msg
             */
            private void setErrorMessage(String msg) {
                this.errorMessage = msg;
            }
            /**
             * Get the error number.
             * @return
             */
            private int getErrorNum() {
                return errorNum;
            }
            /**
             * Set the error number.
             * @param num
             */
            private void setErrorNum(int num) {
                this.errorNum = num;
            }
        }
        /**
         * Default construction.
         */
        public AutoException() {
            super();
            try {
                writename.createNewFile(); 
            }catch (IOException e2) {
                e2.printStackTrace();
            } 
        }
        /**
         * Constructor.
         * @param errormsg
         */
        public AutoException(String errormsg) {
            super();
            this.errormsg = errormsg;
            printmyproblem();
        }
        /**
         * Constructor.
         * @param errorno
         */
        public AutoException(int errorno) {
            super();
            this.errorno = errorno;
            this.errormsg = errnoNumAndMsg.getErrorMessage(errorno);
        }
        /**
         * Constructor.
         * @param errorno
         * @param errormsg
         */
        public AutoException(int errorno, String errormsg) {
            super();
            this.errorno = errorno;
            this.errormsg = errormsg;
        }
        /**
         * Get the error number.
         * @return
         */
        public int getErrorno() {
            return errorno;
        }
        /**
         * Set the error number.
         * @param errorno
         */
        public void setErrorno(int errorno) {
            this.errorno = errorno;
        }
        /**
         * Get the error message.
         * @return
         */
        public String getErrormsg() {
            return errormsg;
        }
        /**
         * Set the error message.
         * @param errormsg
         */
        public void setErrormsg(String errormsg) {
            this.errormsg = errormsg;
        }
        /**
         * Log all the exception information since first time running the function.
         */
        public void logException() {
                // 1) create a java calendar instance
                Calendar calendar = Calendar.getInstance();
                // 2) get a java.util.Date from the calendar instance.
                Date now = calendar.getTime();
                String s = "Error #" + this.getErrorno() + ": " + this.getErrormsg();
                String time = now.toString();
                try {
                    BufferedWriter out = new BufferedWriter(new FileWriter(writename, true));  
                    out.write(time + "\r\n");
                    out.write(s + "\r\n");
                    out.newLine();
                    out.flush();
                    out.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                } 
        }
        /**
         * Print the error number and error message.
         */
        public void printmyproblem() {
//            System.out.println("AutoException [errorno=" + errorno + ", errormsg=" + errormsg + "]"); 
            System.out.println("AutoException [errormsg=" + errormsg + "]");
        }
        
        /**
         * Fix all the exceptions.
         */
        @Override
        public Automobile fix(String filename) {
            // TODO Auto-generated method stub 
            Fix1to5 f = new Fix1to5(filename);
            switch(this.errorno) { 
                case 1: return f.fix1(1);
                case 2: return f.fix2(2);
                case 3: return f.fix3(3);
                case 4: return f.fix4(4);
                case 5: return f.fix5(5);
                default: return null;
            }
         }
    }

