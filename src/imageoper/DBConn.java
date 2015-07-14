package imageoper;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBConn {
	
	 
     Statement st;
     ResultSet rs;
     
     public Connection getConn()
     {
    	 Connection con = null;
     	try{
     		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

     	    con=DriverManager.getConnection("jdbc:odbc:DB1");
     	    
            }
         catch(Exception e)
         {
    	    e.printStackTrace();
     		System.out.println(e);
          }
       return con;
    
    }
     
    
     
    
     
//     public static void main(String[]args)
//     {
//    	 Air air = new Air();
//    	 air.setCategory("Environmentally sensitive areas");
//    	 air.setSpm(900);
//    	 air.setSo2(200);
//    	 air.setCo(2000);
//    	 air.setNox(10);
//    	 air.setRspm(200);
//    	 DBConn db= new DBConn();
//    	 String str = db.getAirQuality(air);
//    	 
//    	 System.out.println(str);
//     }
     
    
	
}
