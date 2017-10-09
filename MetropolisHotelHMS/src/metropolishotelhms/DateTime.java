/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metropolishotelhms;

/**
 *
 * @author Moishin
 */

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
public class DateTime {

   public String getDate() {
       DateFormat df = new SimpleDateFormat("dd/MM/yy ");
       Date dateobj = new Date();
       String date = df.format(dateobj);
       return date;
    }
   
      public String getDate2() {
       DateFormat df = new SimpleDateFormat("yy/MM/dd ");
       Date dateobj = new Date();
       String date = df.format(dateobj);
       return date;
    }
   
   public String getTime(){
       DateFormat df = new SimpleDateFormat("hh:mm:ss ");
       Date dateobj = new Date();
       String time = df.format(dateobj);
       return time;
   }
   
      public String getDateTime(){
       DateFormat df = new SimpleDateFormat("dd/MM/yy hh:mm:ss ");
       Date dateobj = new Date();
       String time = df.format(dateobj);
       return time;
   }
}
    
