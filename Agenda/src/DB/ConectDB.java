package DB;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConectDB {

    final static String DATABASE = "agenda";
    public static Connection connect(){

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost/" + DATABASE;
            return DriverManager.getConnection(url,"root","root");

        } catch (Exception e) {
            return null;
        }
    }

}
