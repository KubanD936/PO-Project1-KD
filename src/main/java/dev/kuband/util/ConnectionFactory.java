package dev.kuband.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public static Connection getConnection() throws SQLException{
        try{
            //Indicates that we're using PostgreSQL for DB
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException e){
            //This message will appear if class is not found
            System.out.println("Class not found");
            //This prints exception message
            e.printStackTrace();
        }
        //url of the schema in DB
        //5432 is port, postgres is the name of the DB, project1 is the name of schema
        String url = "jdbc:postgresql://localhost:5432/postgres?currentSchema=project1";

        //PostgreSQL Username
        String username = "postgres";
        //PostgreSQL Password
        String password = "password";
        return DriverManager.getConnection(url, username, password);
    }
}
