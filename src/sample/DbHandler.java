package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbHandler
{
    private Connection connection;
    private static DbHandler dbHandler;

    private DbHandler() {}

    public Connection getConnection() {
        final String connectionString = "jdbc:mysql://localhost:3306/courseprojectschema";
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            Logger.getLogger(DbHandler.class.getName()).log(Level.SEVERE, null, e);
        }
        try
        {
            connection = DriverManager.getConnection(connectionString, "root", "root");
        }
        catch (SQLException throwables)
        {
            Logger.getLogger(DbHandler.class.getName()).log(Level.SEVERE, null, throwables);
        }
        return connection;
    }

    public static DbHandler getDbHandler() {
        if(dbHandler == null){
            dbHandler = new DbHandler();
        }
        return dbHandler;
    }

//    public Connection getConnection()
//    {
//        final String connectionString = "jdbc:mysql://localhost:3306/courseprojectschema";
//        try
//        {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        }
//        catch (ClassNotFoundException e)
//        {
//            Logger.getLogger(DbHandler.class.getName()).log(Level.SEVERE, null, e);
//        }
//        try
//        {
//            connection = DriverManager.getConnection(connectionString, "root", "root");
//        } catch (SQLException throwables)
//        {
//            Logger.getLogger(DbHandler.class.getName()).log(Level.SEVERE, null, throwables);
//        }
//        return connection;
//    }
}
