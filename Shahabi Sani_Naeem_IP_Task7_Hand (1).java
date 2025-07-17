package jsp_azure_test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Hand {

    private Connection conn;

    // Azure SQL connection credentials
    private String server = "shah0037-sql-server.database.windows.net";
    private String database = "cs-dsa-4513-sql-db";
    private String username = "shah0037";
    private String password = "Lenovoe460";

    // Resulting connection string
    final private String url =
            String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;",
                    server, database, username, password);

    // Initialize and save the database connection
    private void getDBConnection() throws SQLException {
        if (conn != null) {
            return;
        }

        this.conn = DriverManager.getConnection(url);
    }

    // Return the result of selecting everything from the movie_night table 
    public ResultSet selectcustomer() throws SQLException {
        getDBConnection();
        
        final String sqlQuery = "SELECT * FROM customer WHERE category > (6) and category < (9); ";
        final PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        return stmt.executeQuery();
    }

    // Inserts a record into the movie_night table with the given attribute values
  


public boolean addMovie(
        String cname, String caddress, String category) throws SQLException {

    getDBConnection(); // Prepare the database connection

    // Prepare the SQL statement
    final String sqlQuery =
            "INSERT INTO customer " + 
                "(cname, caddress, category) " + 
            "VALUES " + 
            "(?, ?, ?) ";
    final PreparedStatement stmt = conn.prepareStatement(sqlQuery);

    // Replace the '?' in the above statement with the given attribute values
    stmt.setString(1, cname);
    stmt.setString(2, caddress);
    stmt.setString(3, category);
    
    // Execute the query, if only one record is updated, then we indicate success by returning true
    return stmt.executeUpdate() == 1;
}
}

