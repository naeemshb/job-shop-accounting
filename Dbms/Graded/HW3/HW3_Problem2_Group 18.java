import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class HW3_Problem2_Group18 {

    // Database credentials
    final static String HOSTNAME = "roth0025-sql-server.database.windows.net";
    final static String DBNAME = "cs-dsa-4513-sql-db";
    final static String USERNAME = "roth0025";
    final static String PASSWORD = "1poggerdoggerlittlefrogger!";

    // Database connection string
    final static String URL = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;",
            HOSTNAME, DBNAME, USERNAME, PASSWORD);

    // Query templates
    final static String QUERY_TEMPLATE_1 = "INSERT INTO Performer " + 
                                           "VALUES (?, ?, ?, ?);";

    final static String QUERY_TEMPLATE_2 = "SELECT * FROM Performer;";
    
    final static String QUERY_TEMPLATE_3 = "SELECT CASE \r\n"
    		+ "    WHEN AVG(years_of_experience) is NULL AND ? - 18 > 0 THEN ? - 18 --age, age\r\n"
    		+ "    WHEN AVG(years_of_experience) is NULL AND ? - 18 <= 0 THEN 0 --age\r\n"
    		+ "    WHEN AVG(years_of_experience) > ? THEN ? --age, age\r\n"
    		+ "    ELSE\r\n"
    		+ "        AVG(years_of_experience)\r\n"
    		+ "    END AS years_of_experience\r\n"
    		+ "FROM Performer \r\n"
    		+ "WHERE age < ? + 10 and age > ? - 10; --age, age";
    
   final static String QUERY_TEMPLATE_4 = "SELECT CASE \r\n"
   		+ "    WHEN AVG(years_of_experience) is NULL AND ? - 18 > 0 THEN ? - 18 --age, age\r\n"
   		+ "    WHEN AVG(years_of_experience) is NULL AND ? - 18 <= 0 THEN 0 --age\r\n"
   		+ "    WHEN AVG(years_of_experience) > ? THEN ? --age, age\r\n"
   		+ "    ELSE\r\n"
   		+ "        AVG(years_of_experience)\r\n"
   		+ "    END AS years_of_expeience\r\n"
   		+ "FROM Performer \r\n"
   		+ "INNER JOIN Acted on Acted.pid = Performer.pid\r\n"
   		+ "INNER JOIN Movie on Movie.mname = Acted.mname\r\n"
   		+ "INNER JOIN Director on Movie.did = Director.did\r\n"
   		+ "WHERE Director.did = ?; -- did";

    // User input prompt//
    final static String PROMPT = 
            "\nPlease select one of the options below: \n" +
            "1) Insert a new Performer (estimate experience by age); \n" + 
            "2) Insert a new Performer (estimage experience by director); \n" + 
            "3) Display all Performers; \n" +
            "4) Quit!";

    public static void main(String[] args) throws SQLException {

        System.out.println("Welcome to the sample application!");

        final Scanner sc = new Scanner(System.in); // Scanner is used to collect the user input
        String option = ""; // Initialize user option selection as nothing
        while (!option.equals("4")) { // As user for options until option 3 is selected
            System.out.println(PROMPT); // Print the available options
            option = sc.next(); // Read in the user option selection

            switch (option) { // Switch between different options
                case "1": // Insert a new performer option
                    // Collect the new performer data from the user
                    System.out.println("Please enter integer performer ID:");
                    final int id = sc.nextInt(); // Read in the user input of performer ID

                    System.out.println("Please enter Performer last name:");
                    // Preceding nextInt, nextFloat, etc. do not consume new line characters from the user input.
                    // We call nextLine to consume that newline character, so that subsequent nextLine doesn't return nothing.
                    sc.nextLine();
                    final String pname = sc.nextLine(); // Read in user input of actor last name (white-spaces allowed).

                    System.out.println("Please enter integer performer age:");
                    final int age = sc.nextInt(); // Read in the user input of performer age
                    
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        try (
                            final PreparedStatement statement = connection.prepareStatement(QUERY_TEMPLATE_3)) {
                            // Populate the query template with the data collected from the user
                            statement.setInt(1, age);
                            statement.setInt(2, age);
                            statement.setInt(3, age);
                            statement.setInt(4, age);
                            statement.setInt(5, age);
                            statement.setInt(6, age);
                            statement.setInt(7, age);

                            System.out.println("Estimating years of experience...");
                            // Actually execute the populated query
                            final ResultSet resultSet = statement.executeQuery();
                            resultSet.next();
                            final int years_of_experience = (int) resultSet.getDouble(1);
                       
                            try (
                                    final PreparedStatement statement1 = connection.prepareStatement(QUERY_TEMPLATE_1)) {
                                    // Populate the query template with the data collected from the user
                                    statement1.setInt(1, id);
                                    statement1.setString(2, pname);
                                    statement1.setInt(3, years_of_experience);
                                    statement1.setFloat(4, age);

                                    System.out.println("Dispatching the query...");
                                    // Actually execute the populated query
                                    final int rows_inserted = statement1.executeUpdate();
                                    System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
                                }
                        }
                        
                    }
                    

                    break;
                case "2":
                    // Collect the new performer data from the user
                    System.out.println("Please enter integer performer ID:");
                    final int pid = sc.nextInt(); // Read in the user input of performer ID
                    
                    System.out.println("Please enter integer director ID:");
                    final int did = sc.nextInt(); // Read in the user input of performer ID

                    System.out.println("Please enter Performer last name:");
                    // Preceding nextInt, nextFloat, etc. do not consume new line characters from the user input.
                    // We call nextLine to consume that newline character, so that subsequent nextLine doesn't return nothing.
                    sc.nextLine();
                    final String name = sc.nextLine(); // Read in user input of actor last name (white-spaces allowed).

                    System.out.println("Please enter integer performer age:");
                    final int page = sc.nextInt(); // Read in the user input of performer age
                    
                    System.out.println("Connecting to the database...");
                    
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        try (
                            final PreparedStatement statement = connection.prepareStatement(QUERY_TEMPLATE_4)) {
                            // Populate the query template with the data collected from the user
                            statement.setInt(1, page);
                            statement.setInt(2, page);
                            statement.setInt(3, page);
                            statement.setInt(4, page);
                            statement.setInt(5, page);
                            statement.setInt(6, did);

                            System.out.println("Estimating years of experience...");
                            // Actually execute the populated query
                            final ResultSet resultSet = statement.executeQuery();
                            resultSet.next();
                            final int years_of_experience = (int) resultSet.getDouble(1);
                       
                            try (
                                    final PreparedStatement statement1 = connection.prepareStatement(QUERY_TEMPLATE_1)) {
                                    // Populate the query template with the data collected from the user
                                    statement1.setInt(1, pid);
                                    statement1.setString(2, name);
                                    statement1.setInt(3, years_of_experience);
                                    statement1.setFloat(4, page);

                                    System.out.println("Dispatching the query...");
                                    // Actually execute the populated query
                                    final int rows_inserted = statement1.executeUpdate();
                                    System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
                                }
                        }
                        
                    }
                    
                    break;
                case "3": // Do nothing, the while loop will terminate upon the next iteration
                    System.out.println("Connecting to the database...");
                    // Get the database connection, create statement and execute it right away, as no user input need be collected
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        System.out.println("Dispatching the query...");
                        try (
                            final Statement statement = connection.createStatement();
                            final ResultSet resultSet = statement.executeQuery(QUERY_TEMPLATE_2)) {

                                System.out.println("Contents of the Student table:");
                                System.out.println("pid | pname | years_of_experience | age ");

                                // Unpack the tuples returned by the database and print them out to the user
                                while (resultSet.next()) {
                                    System.out.println(String.format("%s | %s | %s | %s",
                                        resultSet.getString(1),
                                        resultSet.getString(2),
                                        resultSet.getString(3),
                                        resultSet.getString(4)));
                                }
                        }
                    }
                    break;

                case "4": // Do nothing, the while loop will terminate upon the next iteration
                    System.out.println("Exiting! Goodbye!");
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
