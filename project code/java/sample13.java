import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class sample13 {

    // Database credentials
    final static String HOSTNAME = "shah0037-sql-server.database.windows.net";
    final static String DBNAME = "cs-dsa-4513-sql-db";
    final static String USERNAME = "shah0037";
    final static String PASSWORD = "Lenovoe460";

    // Database connection string
    final static String URL = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;",
            HOSTNAME, DBNAME, USERNAME, PASSWORD);

    // Query templates
    final static String QUERY_TEMPLATE_1 = "INSERT INTO customer " + 
                                           "VALUES (?, ?, ?);";

    final static String QUERY_TEMPLATE_2 = "INSERT INTO department " + 
            								"VALUES (?, ?);";
    final static String QUERY_TEMPLATE_3 = "INSERT INTO process " + 
											"VALUES (?, ?);";
    
    final static String QUERY_TEMPLATE_13 = "SELECT cname FROM customer WHERE category <= 4"   ;
    // User input prompt// 
    final static String PROMPT = 
            "\nPlease select one of the options below: \n" +
            "1) Insert new customer; \n" + 
            "2) Enter a new department; \n" + 
            "3) Enter a new Process ; \n" +
            "13)Retrieve the customers (in name order) whose category is in a given range: \n" +
            "4) Exit!";

    public static void main(String[] args) throws SQLException {

        System.out.println("Welcome to the sample application!");

        final Scanner sc = new Scanner(System.in); // Scanner is used to collect the user input
        String option = ""; // Initialize user option selection as nothing
        while (!option.equals("3")) { // As user for options until option 3 is selected
            System.out.println(PROMPT); // Print the available options
            option = sc.next(); // Read in the user option selection

            switch (option) { // Switch between different options
                case "1": // Insert a new student option
                    // Collect the new student data from the user
                    System.out.println("Please enter customer cname:");
                    final String cname = sc.next(); // Read in the user input of student ID
                    //System.out.print(cname);
                    sc.nextLine();
                    System.out.println("Please enter customer address:");
                    // Preceding nextInt, nextFloar, etc. do not consume new line characters from the user input.
                    // We call nextLine to consume that newline character, so that subsequent nextLine doesn't return nothing.
                    //sc.nextLine();
                    final String caddress = sc.nextLine(); // Read in user input of student First Name (white-spaces allowed).

                    System.out.println("Please enter customer category:");
                    // No need to call nextLine extra time here, because the preceding nextLine consumed the newline character.
                    final float category = sc.nextFloat(); // Read in user input of student Last Name (white-spaces allowed).
                   	
                    System.out.println("Connecting to the database...");
                    // Get a database connection and prepare a query statement
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        try (
                            final PreparedStatement statement = connection.prepareStatement(QUERY_TEMPLATE_1)) {
                            // Populate the query template with the data collected from the user
                        	System.out.print(cname);
                            statement.setString(1, cname);
                            statement.setString(2, caddress);
                            statement.setFloat(3, category);

                            System.out.println("Dispatching the query...");
                            // Actually execute the populated query
                            final int rows_inserted = statement.executeUpdate();
                            System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
                        }
                    }

                    break;
                case "2":
                	System.out.println("Please enter department number");
                    final int departmentnumber = sc.nextInt(); // Read in the user input of student ID
                    //System.out.print(departmentnumber);
                    //sc.nextLine();
                    System.out.println("Please enter department data:");
                    // Preceding nextInt, nextFloar, etc. do not consume new line characters from the user input.
                    // We call nextLine to consume that newline character, so that subsequent nextLine doesn't return nothing.
                    sc.nextLine();
                    final String departmentdata = sc.nextLine(); // Read in user input of student First Name (white-spaces allowed).

                    
                    System.out.println("Connecting to the database...");
                    // Get a database connection and prepare a query statement
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        try (
                            final PreparedStatement statement = connection.prepareStatement(QUERY_TEMPLATE_2)) {
                            // Populate the query template with the data collected from the user
                        	//System.out.print(cname);
                            statement.setInt(1, departmentnumber);
                            statement.setString(2, departmentdata);
                            

                            System.out.println("Dispatching the query...");
                            // Actually execute the populated query
                            final int rows_inserted = statement.executeUpdate();
                            System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
                        }
                    }

                    break;
                case "13":
                    System.out.println("Connecting to the database...");
                    // Get the database connection, create statement and execute it right away, as no user input need be collected
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        System.out.println("Dispatching the query...");
                        try (
                            final Statement statement = connection.createStatement();
                            final ResultSet resultSet = statement.executeQuery(QUERY_TEMPLATE_13)) {

                                System.out.println("Contents of the Student table:");
                                System.out.println("Cname | Caddress | Category");

                                // Unpack the tuples returned by the database and print them out to the user
                                while (resultSet.next()) {
                                    System.out.println(String.format("%s",
                                        resultSet.getString(1)
                                        //resultSet.getString(2),
                                        //resultSet.getString(3)
                                        ));
                                }
                        }
                    }
                    break;
                case "3": // Insert a new student option
                    // Collect the new student data from the user
                    System.out.println("Please enter processId:");
                    final int processId = sc.nextInt(); // Read in the user input of student ID
                    //System.out.print(cname);
                    sc.nextLine();
                    System.out.println("Please enter department data:");
                    // Preceding nextInt, nextFloar, etc. do not consume new line characters from the user input.
                    // We call nextLine to consume that newline character, so that subsequent nextLine doesn't return nothing.
                    //sc.nextLine();
                    final String processData = sc.nextLine(); // Read in user input of student First Name (white-spaces allowed).

               
                   	
                    System.out.println("Connecting to the database...");
                    // Get a database connection and prepare a query statement
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        try (
                            final PreparedStatement statement = connection.prepareStatement(QUERY_TEMPLATE_3)) {
                            // Populate the query template with the data collected from the user
                        	//System.out.print(cname);
                            statement.setInt(1, processId);
                            statement.setString(2, processData);
                           
                            System.out.println("Dispatching the query...");
                            // Actually execute the populated query
                            final int rows_inserted = statement.executeUpdate();
                            System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
                        }
                    }
                    break;
                case "4": // Do nothing, the while loop will terminate upon the next iteration
                    System.out.println("Exiting! Good-buy!");
                    break;
                default: // Unrecognized option, re-prompt the user for the correct one
                    System.out.println(String.format(
                        "Unrecognized option: %s\n" + 
                        "Please try again!", 
                        option));
                    break;
            }
        }

        sc.close(); // Close the scanner before exiting the application
    }
}
