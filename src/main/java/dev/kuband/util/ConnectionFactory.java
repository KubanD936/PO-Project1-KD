package dev.kuband.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public static Connection getConnection(){
        //Set up environmental variable
        String url = System.getenv("POSTGRES_SQL_DB");
        String username = System.getenv("DB_USERNAME");
        String password = System.getenv("DB_PASSWORD");
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
        //Version 2
        /*try{
            Connection connection = DriverManager.getConnection(System.getenv("POSTGRES_SQL_DB"));
            return connection;

        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
*/
        //Version 1
        /*try{
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
        //Need to hide the postgres username and password within String url for best practice, to do that edit environment variables
        //For tutorial on how to do that go to Week 2 Thursday, around 1:50 into the video
        String url = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=password&currentSchema=project1";

        //PostgreSQL Username
        String username = "postgres";
        //PostgreSQL Password
        String password = "password";
        return DriverManager.getConnection(url, username, password);*/

