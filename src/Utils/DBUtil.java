package Utils;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static String ip="localhost";
    private static int port=3306;
    private static String encoding="utf-8";
    private static String database="restaurant";
    private static String name="root";
    private static String password="";
    static{
        try {            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public static Connection getConnection() throws SQLException{
        String url=String.format("jdbc:mysql://%s:%d/%s?characterEncoding=%s",ip,port,database,encoding);
        url+="&serverTimezone=GMT%2B8";
        return DriverManager.getConnection(url,name,password);
    }
}
