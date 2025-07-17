import java.sql.Connection;
import java.sql.Statement;
import java.sql.Types;
import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.io.*;  

public class after11 {

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
    final static String QUERY_TEMPLATE_3_a = "INSERT INTO fit " + 
											"VALUES (?, ?);";
    final static String QUERY_TEMPLATE_3_b = "INSERT INTO paint " + 
			"VALUES (?, ?, ?);";
    final static String QUERY_TEMPLATE_3_c = "INSERT INTO cut " + 
			"VALUES (?, ?, ?);";
    final static String QUERY_TEMPLATE_3_d ="INSERT into supervise " +
											"VALUES (?, ?, ?);"; 
    final static String QUERY_TEMPLATE_4_a ="INSERT into assembly " +
			"VALUES (?, ?, ?);"; 
    final static String QUERY_TEMPLATE_4_b ="INSERT into order1 " +
			"VALUES (?, ?);"; 
    final static String QUERY_TEMPLATE_4_c ="INSERT into passthrugh " +
			"VALUES (?, ?);"; 
    final static String QUERY_TEMPLATE_5_a ="INSERT into account " +
			"VALUES (?, ?);"; 
    final static String QUERY_TEMPLATE_5_b ="INSERT into departmentaccount " +
			"VALUES (?, ?);"; 
    final static String QUERY_TEMPLATE_5_c ="INSERT into assemblyaccount " +
			"VALUES (?, ?);"; 
    final static String QUERY_TEMPLATE_5_d ="INSERT into processaccount " +
			"VALUES (?, ?);"; 
    final static String QUERY_TEMPLATE_5_e ="INSERT into record_dept_cost " +
			"VALUES (?, ?);"; 
    final static String QUERY_TEMPLATE_5_f ="INSERT into record_assembly_cost " +
			"VALUES (?, ?);"; 
    final static String QUERY_TEMPLATE_5_g ="INSERT into record_process_cost " +
			"VALUES (?, ?);"; 
    final static String QUERY_TEMPLATE_6_a ="INSERT into job  " +
			"VALUES (?, ?, ?, ?, ?);";
    final static String QUERY_TEMPLATE_6_b ="INSERT into assign  " +
			"VALUES (?, ?, ?);";
    
    final static String QUERY_TEMPLATE_7_a ="UPDATE job " +
			"SET dateofjobcompleted = (?), labortime = (?), job_type = (?)" +
			"Where jobnomber = (?);";
    final static String QUERY_TEMPLATE_7_b ="INSERT into cutjob  " +
			"VALUES (?, ?, ?, ?, ?);";
    final static String QUERY_TEMPLATE_7_c ="INSERT into painjob  " +
			"VALUES (?, ?, ?, ?);";
    final static String QUERY_TEMPLATE_7_d ="INSERT into fitjob  " +
			"VALUES (?, ?);";
    final static String QUERY_TEMPLATE_8_a ="INSERT into transaction1  " +
			"VALUES (?, ?);";
    final static String QUERY_TEMPLATE_8_b ="INSERT into record  " +
			"VALUES (?, ?);";
    final static String QUERY_TEMPLATE_8_c ="SELECT Assemblyid, processId from assign  " +
			" WHERE jobnomber = (?);";
    final static String QUERY_TEMPLATE_8_d ="SELECT departmentnumber FROM supervise " +
			"WHERE processId =(?);";
    final static String QUERY_TEMPLATE_8_e ="SELECT account_num FROM record_dept_cost " +
			"WHERE dept_num =(?);";
    final static String QUERY_TEMPLATE_8_f ="SELECT account_num FROM record_assembly_cost " +
			"WHERE assembly_id =(?);";
    final static String QUERY_TEMPLATE_8_g ="SELECT account_num FROM record_process_cost " +
			"WHERE process_id =(?);";
    final static String QUERY_TEMPLATE_8_h ="SELECT sup_cost FROM departmentaccount " +
			"WHERE accountnumber =(?);";
    final static String QUERY_TEMPLATE_8_i ="SELECT sup_cost FROM assemblyaccount " +
			"WHERE accountnumber =(?);";
    final static String QUERY_TEMPLATE_8_j ="SELECT sup_cost FROM processaccount " +
			"WHERE accountnumber =(?);";
    final static String QUERY_TEMPLATE_8_K = "UPDATE departmentaccount " +
			"SET sup_cost = (?)" +
			"Where accountnumber = (?);";
    final static String QUERY_TEMPLATE_8_L = "UPDATE assemblyaccount " +
			"SET sup_cost = (?)" +
			"Where accountnumber = (?);";
    final static String QUERY_TEMPLATE_8_M = "UPDATE processaccount " +
			"SET sup_cost = (?)" +
			"Where accountnumber = (?);";
    
    final static String QUERY_TEMPLATE_10_a ="SELECT DISTINCT processId " + 
			"FROM supervise WHERE departmentnumber =  (?); ";
    final static String QUERY_TEMPLATE_10_b ="SELECT DISTINCT jobnomber " + 
			"FROM assign WHERE processId =  (?); ";
    final static String QUERY_TEMPLATE_10_c ="SELECT labortime, dateofjobcompleted " + 
			"FROM Job WHERE jobNomber =  (?); ";
    final static String QUERY_TEMPLATE_11 = "select supervise.processId, departmentnumber "
    		+ "from Assembly, process, supervise "
    		+ "where supervise.processId = process.processId "
    		+ "and Assembly.Assemblyid = (?) "
    		+ "order by dateofordered ASC";
    final static String QUERY_TEMPLATE_12_a = "select processId from supervise "+
    		"where departmentnumber = (?)";
    final static String QUERY_TEMPLATE_12_b = "select jobNomber, Assemblyid from assign " +
    		"where processId = (?)";
    final static String QUERY_TEMPLATE_12_c = "select job_type from job "+
    		"where jobnomber = (?) and dateofjobcompleted = (?)";
    final static String QUERY_TEMPLATE_13 = "SELECT * FROM customer WHERE category >= (?) and category < (?) "   ;
    
    final static String QUERY_TEMPLATE_14_a = "delete from cutjob Where jobnomber > (?) and jobnomber < (?) " ;
    final static String QUERY_TEMPLATE_14_b = "delete from assign Where jobnomber > (?) and jobnomber < (?) " ;
    final static String QUERY_TEMPLATE_14_c = "delete from job Where jobnomber > (?) and jobnomber < (?) " ;
    final static String QUERY_TEMPLATE_15 ="UPDATE painjob " +
			"SET color = (?)" +
			"Where jobnomber = (?);";
    // User input prompt// 
    final static String PROMPT = 
            "\nPlease select one of the options below: \n" +
            "1) Enter new customer; \n" + 
            "2) Enter a new department; \n" + 
            "3) Enter a new Process ; \n" +
            "4) Enter a new Assembly: \n" +
            "5) Enter a new Account Number:\n" + 
            "6) Enter a new Job:\n"+
            "7) Enter the Completion date of a Job: \n " +
            "8) Enter a new Transaction Number: \n" + 
            "9) Retrieve the total cost incurred on an assembly-id \n " +
            "10)Retrieve total labor time within a Department for a given day: \n " +
            "11)Retrieve the process through which a given assembly-id has passed: \n" +
            "12)Retrieve the jobs completed during given date in a given department: \n"+
            "13)Retrieve the customers (in name order) whose category is in a given range: \n" +
            "14)Delete all cut-jobs with a Job Number: \n"+
            "15)Change the color of a Paint Job: \n" +
            "16)Import: enter new customers from a data file: \n"+
            "17)Export Customer Data \n" + 
            "20) Exit!";

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

                    System.out.println("Please enter customer category between 1 and 10:");
                    // No need to call nextLine extra time here, because the preceding nextLine consumed the newline character.
                    final float category = sc.nextFloat(); // 
                   	
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
                	System.out.println("Please input the minimum category:");
                	final int mincat = sc.nextInt();
                	sc.nextLine();
                	System.out.println("Please input the maximum category:");
                	final int maxcat = sc.nextInt();
                	sc.nextLine();
                    System.out.println("Connecting to the database...");
                    // Get the database connection, create statement and execute it right away, as no user input need be collected
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        System.out.println("Dispatching the query...");
                        try (
                        		final PreparedStatement statement2 = connection.prepareStatement(QUERY_TEMPLATE_13)) {
                        		
                        		statement2.setInt(1, mincat);
                        		statement2.setInt(2, maxcat);
                        		System.out.println("Dispatching the query 13...");
                        		ResultSet rs1 = statement2.executeQuery();
                                System.out.println("Contents of the Student table:");
                                System.out.println("Cname | Caddress | Category");

                                // Unpack the tuples returned by the database and print them out to the user
                                while (rs1.next()) {
                                    
                                        String name = rs1.getString("cname");
                                        String address = rs1.getString("caddress");
                                        int cat = rs1.getInt("category");
                                        System.out.println(name + "\t" + address + "\t\t" + cat);
                                        //resultSet.getString(2),
                                        //resultSet.getString(3)
                                        
                                }
                        }
                        
                    }
                    break;
                case "3": // Insert a new student option
                    // Collect the new student data from the user
                    System.out.println("Please enter processId:");
                    final int processId = sc.nextInt(); // Read in the user input of student ID
                    sc.nextLine();
                    System.out.println("Please enter the process Data:");
                    final String processData = sc.nextLine();

          
                    //System.out.print(cname);
                    sc.nextLine();
                    System.out.println("Please enter the Supervising Department ID:" );
                    final int deptId = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Please enter the Department data:");
                    final String deptData = sc.nextLine();
                    //System.out.println("Please enter process data: 1 For Fit Process, 2 For Paint Process & 3 for Cut Process:");
                    // Preceding nextInt, nextFloar, etc. do not consume new line characters from the user input.
                    // We call nextLine to consume that newline character, so that subsequent nextLine doesn't return nothing.
                    //sc.nextLine();
                    //final String processData = sc.nextLine(); // Read in user input of student First Name (white-spaces allowed).
                    System.out.println("Please enter process data: 1 For Fit Process, 2 For Paint Process & 3 for Cut Process:");
                    final int processType = sc.nextInt();
                    sc.nextLine();
                    if (processType == 1) {
                    	//Fit Process
                    	System.out.println("Please enter Fit type:");
                    	final String fitType = sc.nextLine();
                    	sc.nextLine();
                    	System.out.println("Connecting to the database...");
                        // Get a database connection and prepare a query statement
                        try (final Connection connection = DriverManager.getConnection(URL)) {
                        	try (
                                    final PreparedStatement statement1 = connection.prepareStatement(QUERY_TEMPLATE_3)) {
                                    // Populate the query template with the data collected from the user
                                	//System.out.print(cname);
                                    statement1.setInt(1, processId);
                                    statement1.setString(2, processData);
                                   
                                    System.out.println("Dispatching the query...");
                                    // Actually execute the populated query
                                    final int rows_inserted = statement1.executeUpdate();
                                    System.out.println(String.format("Done. %d rows inserted in \"process\" table.", rows_inserted));
                                   
                                }
                            try (
                                final PreparedStatement statement2 = connection.prepareStatement(QUERY_TEMPLATE_3_a)) {
                                // Populate the query template with the data collected from the user
                            	//System.out.print(cname);
                                statement2.setInt(2, processId);
                                statement2.setString(1, fitType);
                               
                                System.out.println("Dispatching the query...");
                                // Actually execute the populated query
                                final int rows_inserted = statement2.executeUpdate();
                                System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
                               
                            }
                            
                        }
                        
                    	
                    }
                    else if (processType == 2) {
                    	//information for Paint Process
                    	System.out.println("Please enter Paint type:");
                    	final String paintType = sc.nextLine();
                    	//sc.nextLine();
                    	System.out.println("Please enter Paint method:");
                    	final String paintMethod = sc.nextLine();
                    	//sc.nextLine();
                    	
                    	System.out.println("Connecting to the database...");
                        // Get a database connection and prepare a query statement
                        try (final Connection connection = DriverManager.getConnection(URL)) {
                        	
                        	 try (
                                     final PreparedStatement statement1 = connection.prepareStatement(QUERY_TEMPLATE_3)) {
                                     // Populate the query template with the data collected from the user
                                 	//System.out.print(cname);
                                     statement1.setInt(1, processId);
                                     statement1.setString(2, processData);
                                    
                                     System.out.println("Dispatching the query...");
                                     // Actually execute the populated query
                                     final int rows_inserted = statement1.executeUpdate();
                                     System.out.println(String.format("Done. %d rows inserted in \"process\" table.", rows_inserted));
                                    
                                 }
                            try (
                                final PreparedStatement statement2 = connection.prepareStatement(QUERY_TEMPLATE_3_b)) {
                                // Populate the query template with the data collected from the user
                            	//System.out.print(cname);
                                statement2.setInt(3, processId);
                                statement2.setString(1, paintType);
                                statement2.setString(2, paintMethod);
                                System.out.println("Dispatching the query...");
                                // Actually execute the populated query
                                final int rows_inserted = statement2.executeUpdate();
                                System.out.println("Executing Statement 3(b)...");
                                System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
                                
                               
                            }
                           
                        }
                        
                    }
                    else {
                    	// information for Cut Process
                    	System.out.println("Please enter the Cutting Type:");
                    	final String cutType = sc.nextLine();
                    	System.out.println("Please enter the Machine Type:");
                    	final String machineType = sc.nextLine();
                    	System.out.println("Connecting to the database...");
                        // Get a database connection and prepare a query statement
                        try (final Connection connection = DriverManager.getConnection(URL)) {
                        	
                        	try (
                                    final PreparedStatement statement1 = connection.prepareStatement(QUERY_TEMPLATE_3)) {
                                    // Populate the query template with the data collected from the user
                                	//System.out.print(cname);
                                    statement1.setInt(1, processId);
                                    statement1.setString(2, processData);
                                   
                                    System.out.println("Dispatching the query...");
                                    // Actually execute the populated query
                                    final int rows_inserted = statement1.executeUpdate();
                                    System.out.println(String.format("Done. %d rows inserted in \"process\" table.", rows_inserted));
                                   
                                }
                     
                            try (
                                final PreparedStatement statement3 = connection.prepareStatement(QUERY_TEMPLATE_3_c)) {
                                // Populate the query template with the data collected from the user
                            	//System.out.print(cname);
                                statement3.setInt(3, processId);
                                statement3.setString(1, cutType);
                                statement3.setString(2, machineType);
                               
                                System.out.println("Dispatching the query...");
                                // Actually execute the populated query
                                final int rows_inserted = statement3.executeUpdate();
                                System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
                                
                            }
                            
                        }
                        
                    }
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        try (
                            final PreparedStatement statement4 = connection.prepareStatement(QUERY_TEMPLATE_3_d)) {
                            // Populate the query template with the data collected from the user
                        	//System.out.print(cname);
                            statement4.setInt(1, processId);
                            statement4.setInt(2, deptId);
                            statement4.setString(3, deptData);
                           
                            System.out.println("Dispatching the query 3(d)...");
                            // Actually execute the populated query
                            final int rows_inserted = statement4.executeUpdate();
                            System.out.println(String.format("Done. %d rows inserted in \"supervise\" table.", rows_inserted));
                            
                        }
                    }
                    break;
                    
                case "4":
                	System.out.println("Please enter a new Assembly ID:");
                	final int assembly_id = sc.nextInt();
                	sc.nextLine();
                	System.out.println("Please enter the Date ordered for the assembly:");
                	final int date_ordered = sc.nextInt();
                	sc.nextLine();
                	System.out.println("Please enter the Assembly details:");
                	final String assembly_details = sc.nextLine();
                	System.out.println("Please enter the associated Customer Name:");
                	final String assembly_customer = sc.nextLine();
                	System.out.println("Please enter the number of associated process IDs with this assembly");
                	final int numAssemblies = sc.nextInt();
                	sc.nextLine();
                	int[] assemblyArray = new int[numAssemblies];
                	for (int i = 0; i < numAssemblies; i++)
                	{
                		System.out.println("Please enter %dth process ID:");
                		assemblyArray[i] = sc.nextInt();
                		sc.nextLine();
                	}
                	
                	try (final Connection connection = DriverManager.getConnection(URL)) {
                		try (
                                final PreparedStatement statement4 = connection.prepareStatement(QUERY_TEMPLATE_4_a)) {
                                // Populate the query template with the data collected from the user
                            	//System.out.print(cname);
                                statement4.setInt(1, assembly_id);
                                statement4.setInt(2, date_ordered);
                                statement4.setString(3, assembly_details);
                               
                                System.out.println("Dispatching the query 4(a)...");
                                // Actually execute the populated query
                                final int rows_inserted = statement4.executeUpdate();
                                System.out.println(String.format("Done. %d rows inserted in \"Assembly\" table.", rows_inserted));
                                
                            }
                		try (
                                final PreparedStatement statement5 = connection.prepareStatement(QUERY_TEMPLATE_4_b)) {
                                // Populate the query template with the data collected from the user
                            	//System.out.print(cname);
                                statement5.setInt(1, assembly_id);
                                statement5.setString(2, assembly_customer);
                               
                               
                                System.out.println("Dispatching the query 4(b)...");
                                // Actually execute the populated query
                                final int rows_inserted = statement5.executeUpdate();
                                System.out.println(String.format("Done. %d rows inserted in \"order1\" table.", rows_inserted));
                                
                            }
                		try (
                                final PreparedStatement statement6 = connection.prepareStatement(QUERY_TEMPLATE_4_c)) {
                                // Populate the query template with the data collected from the user
                            	//System.out.print(cname);
                				for (int i = 0; i< numAssemblies; i++)
                				{
                					statement6.setInt(1, assembly_id);
                                    statement6.setInt(2, assemblyArray[i]);
                                    System.out.println("Dispatching the query 4(b)...");
                                    // Actually execute the populated query
                                    final int rows_inserted = statement6.executeUpdate();
                                    System.out.println(String.format("Done. %d rows inserted in \"passthrugh\" table.", rows_inserted));
                				}
                         
                            }
                	}
                	break;
                   	
                case "5":
                	System.out.println("Please enter a new Account number");
                    final int accNum = sc.nextInt(); // Read in the user input of student ID
                    sc.nextLine();
                    System.out.println("Please enter the date of account establishment:");
                    final int accDate = sc.nextInt();
          
          
                    System.out.println("Please enter the account type: 1 For Dept Acc, 2 For Assembly Acc & 3 for Process Acc:");
                    final int accType = sc.nextInt();
                    sc.nextLine();
                    if (accType == 1) {
                    	System.out.println("Please enter the department number associated with the account:");
                    	final int deptNo = sc.nextInt();
                    	sc.nextLine();
                    	System.out.println("Connecting to the database...");
                        // Get a database connection and prepare a query statement
                        try (final Connection connection = DriverManager.getConnection(URL)) {
                        	try (
                                    final PreparedStatement statement1 = connection.prepareStatement(QUERY_TEMPLATE_5_a)) {
                                    // Populate the query template with the data collected from the user
                                	//System.out.print(cname);
                                    statement1.setInt(1, accNum);
                                    statement1.setInt(2, accDate);
                                   
                                    System.out.println("Dispatching the query 5(a)...");
                                    // Actually execute the populated query
                                    final int rows_inserted = statement1.executeUpdate();
                                    System.out.println(String.format("Done. %d rows inserted in \"account\" table.", rows_inserted));
                                   
                                }
                            try (
                                final PreparedStatement statement2 = connection.prepareStatement(QUERY_TEMPLATE_5_b)) {
                                // Populate the query template with the data collected from the user
                            	//System.out.print(cname);
                                statement2.setInt(1, accNum);
                                statement2.setInt(2, 0);
                               
                                System.out.println("Dispatching the query 5(b)...");
                                // Actually execute the populated query
                                final int rows_inserted = statement2.executeUpdate();
                                System.out.println(String.format("Done. %d rows inserted in \"departmentaccount\" table .", rows_inserted));
                               
                            }
                            try (
                                    final PreparedStatement statement3 = connection.prepareStatement(QUERY_TEMPLATE_5_e)) {
                                    // Populate the query template with the data collected from the user
                                	//System.out.print(cname);
                                    statement3.setInt(1, accNum );
                                    statement3.setInt(2, deptNo);
                                   
                                    System.out.println("Dispatching the query 5(e)...");
                                    // Actually execute the populated query
                                    final int rows_inserted = statement3.executeUpdate();
                                    System.out.println(String.format("Done. %d rows inserted in \"record_dept_cost\" table .", rows_inserted));
                                   
                                }
                            
                        }
                        
                    	
                    }
                    else if (accType == 2) {
                    	//information for Paint Process
                    	System.out.println("Please enter the associated Assembly ID: ");
                    	final int assemblyID = sc.nextInt();
                    	sc.nextLine();
                    	
                    	System.out.println("Connecting to the database...");
                        // Get a database connection and prepare a query statement
                        try (final Connection connection = DriverManager.getConnection(URL)) {
                        	
                        	 try (
                                     final PreparedStatement statement1 = connection.prepareStatement(QUERY_TEMPLATE_5_a)) {
                                     // Populate the query template with the data collected from the user
                                 	//System.out.print(cname);
                        		 statement1.setInt(1, accNum);
                                 statement1.setInt(2, accDate);
                                
                                 System.out.println("Dispatching the query 5(a)...");
                                 // Actually execute the populated query
                                 final int rows_inserted = statement1.executeUpdate();
                                 System.out.println(String.format("Done. %d rows inserted in \"account\" table.", rows_inserted));
                                    
                                 }
                            try (
                                final PreparedStatement statement9 = connection.prepareStatement(QUERY_TEMPLATE_5_c)) {
                                // Populate the query template with the data collected from the user
                            	//System.out.print(cname);
                                statement9.setInt(1, accNum);
                                statement9.setInt(2, 0);
                                System.out.println("Dispatching the query 5(c)...");
                                // Actually execute the populated query
                                final int rows_inserted = statement9.executeUpdate();
                                System.out.println("Executing Statement 5(c)...");
                                System.out.println(String.format("Done. %d rows inserted in \"assemblyaccount\" table.", rows_inserted));
                                
                               
                            }
                            try (
                                    final PreparedStatement statement3 = connection.prepareStatement(QUERY_TEMPLATE_5_f)) {
                                    // Populate the query template with the data collected from the user
                                	//System.out.print(cname);
                                    statement3.setInt(1, accNum);
                                    statement3.setInt(2, assemblyID);
                                    System.out.println("Dispatching the query 5(f)...");
                                    // Actually execute the populated query
                                    final int rows_inserted = statement3.executeUpdate();
                                    System.out.println("Executing Statement 5(f)...");
                                    System.out.println(String.format("Done. %d rows inserted in \"record_assembly_cost\" table.", rows_inserted));
                                   
                                }
                           
                        }
                        
                    }
                    else {
                    	// information for Cut Process
                    	System.out.println("Please enter the associated process ID:");
                    	final int processID = sc.nextInt();
                    	sc.nextLine();
                    	
                    	System.out.println("Connecting to the database...");
                        // Get a database connection and prepare a query statement
                        try (final Connection connection = DriverManager.getConnection(URL)) {
                        	
                        	try (
                                    final PreparedStatement statement1 = connection.prepareStatement(QUERY_TEMPLATE_5_a)) {
                                    // Populate the query template with the data collected from the user
                                	//System.out.print(cname);
                                    statement1.setInt(1, accNum);
                                    statement1.setInt(2, accDate);
                                   
                                    System.out.println("Dispatching the query 5(a)...");
                                    // Actually execute the populated query
                                    final int rows_inserted = statement1.executeUpdate();
                                    System.out.println(String.format("Done. %d rows inserted in \"account\" table.", rows_inserted));
                                   
                                }
                     
                            try (
                                final PreparedStatement statement3 = connection.prepareStatement(QUERY_TEMPLATE_5_d)) {
                                // Populate the query template with the data collected from the user
                            	//System.out.print(cname);
                                statement3.setInt(1, accNum);
                                statement3.setInt(2, 0);
                                System.out.println("Dispatching the query 5(d)...");
                                // Actually execute the populated query
                                final int rows_inserted = statement3.executeUpdate();
                                System.out.println(String.format("Done. %d rows inserted in \"processaccount\" table..", rows_inserted));
                                
                            }
                            
                            try (
                                    final PreparedStatement statement3 = connection.prepareStatement(QUERY_TEMPLATE_5_g)) {
                                    // Populate the query template with the data collected from the user
                                	//System.out.print(cname);
                                    statement3.setInt(1, accNum);
                                    statement3.setInt(2, processID);
                                    System.out.println("Dispatching the query 5(g)...");
                                    // Actually execute the populated query
                                    final int rows_inserted = statement3.executeUpdate();
                                    System.out.println(String.format("Done. %d rows inserted in \"record_process_cost\" table..", rows_inserted));
                                    
                                }
                            
                        }
                        
                    }
                   break; 
                case "6":
                	System.out.println("Please enter the Job Number:");
                	final int jobNo = sc.nextInt();
                	sc.nextLine();
                	
                    System.out.println("Please enter the date of job commenced:");
                    final int dateCommenced = sc.nextInt();
                    sc.nextLine();
                    
                    System.out.println("Please enter the Process ID assigned to this job:");
                    final int processID = sc.nextInt();
                    sc.nextLine();
                    
                    System.out.println("Please enter the Assembly ID assigned to the process for this job:");
                    final int assemblyID = sc.nextInt();
                    sc.nextLine();
                    
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                    	try (
                                final PreparedStatement statement1 = connection.prepareStatement(QUERY_TEMPLATE_6_a)) {
                                // Populate the query template with the data collected from the user
                            	//System.out.print(cname);
                                statement1.setInt(1, jobNo);
                                statement1.setInt(3, dateCommenced);
                                statement1.setNull(2, Types.NULL);
                                statement1.setNull(4, Types.NULL);
                                statement1.setNull(5, Types.NULL);
                                System.out.println("Dispatching the query 6(a)...");
                                // Actually execute the populated query
                                final int rows_inserted = statement1.executeUpdate();
                                System.out.println(String.format("Done. %d rows inserted in \"Job\" table..", rows_inserted));
                                
                            }
                    	try (
                                final PreparedStatement statement2 = connection.prepareStatement(QUERY_TEMPLATE_6_b)) {
                                // Populate the query template with the data collected from the user
                            	//System.out.print(cname);
                                statement2.setInt(1, assemblyID);
                                statement2.setInt(3, jobNo);
                                statement2.setInt(2, processID);
                                System.out.println("Dispatching the query 6(b)...");
                                // Actually execute the populated query
                                final int rows_inserted = statement2.executeUpdate();
                                System.out.println(String.format("Done. %d rows inserted in \"assign\" table..", rows_inserted));
                                
                            }
                    	
                    }
                    break;
                case "7":
                	System.out.println("Please enter the Job Number:");
                	final int job_no = sc.nextInt();
                	sc.nextLine();
                	
                	System.out.println("Please enter the date the job completed:");
                	final int jobCompleted = sc.nextInt();
                	sc.nextLine();
                	
                	System.out.println("Please enter the Kind of Job: 1. for Cut Job, 2 for Paint Job and 3 for Fit Job");
                	final int job_type = sc.nextInt();
                	sc.nextLine();
                    
                	if (job_type == 1)
                    {
                    	System.out.println("Please enter the type of Machine used:");
                    	final String machineType = sc.nextLine();
                    	System.out.println("Please enter the amount of time required in hours:");
                    	final int timeReqd = sc.nextInt();
                    	sc.nextLine();
                    	System.out.println("Please enter the labor time required in hours:");
                    	final int laborTime = sc.nextInt();
                    	sc.nextLine();
                    	System.out.println("Please enter the material used for the job:");
                    	final String material = sc.nextLine();
                    	
                    	System.out.println("Connecting to the database...");
                        // Get a database connection and prepare a query statement
                        try (final Connection connection = DriverManager.getConnection(URL)) {
                        	try (
                                    final PreparedStatement statement1 = connection.prepareStatement(QUERY_TEMPLATE_7_a)) {
                                    // Populate the query template with the data collected from the user
                                	//System.out.print(cname);
                                    statement1.setInt(1, jobCompleted);
                                    statement1.setInt(2, laborTime);
                                    statement1.setInt(3, job_type);
                                    statement1.setInt(4, job_no);
                                    System.out.println("Dispatching the query 7(a)...");
                                    // Actually execute the populated query
                                    final int rows_inserted = statement1.executeUpdate();
                                    System.out.println(String.format("Done. %d rows inserted in \"Job\" table..", rows_inserted));
                                    
                                }
                        	
                        	try (
                                    final PreparedStatement statement3 = connection.prepareStatement(QUERY_TEMPLATE_7_b)) {
                                    // Populate the query template with the data collected from the user
                                	//System.out.print(cname);
                                    statement3.setString(1, machineType);
                                    statement3.setString(2, material);
                                    statement3.setInt(3, job_no);
                                    statement3.setInt(4, timeReqd);
                                    statement3.setInt(5, laborTime);
                                    System.out.println("Dispatching the query 6(c)...");
                                    // Actually execute the populated query
                                    final int rows_inserted = statement3.executeUpdate();
                                    System.out.println(String.format("Done. %d rows inserted in \"cutjob\" table..", rows_inserted));
                                    
                                }
                        }
                    }
                    else if (job_type == 2)
                    {
                    	System.out.println("Please enter the color of the paint used:");
                    	final String color = sc.nextLine();
                    	
                    	System.out.println("Please enter the volume of paint used:");
                    	final int volume = sc.nextInt();
                    	sc.nextLine();
                    	
                    	System.out.println("Please enter the labor time in number of hours:");
                    	final int laborTime = sc.nextInt();
                    	sc.nextLine();
                    	
                    	System.out.println("Connecting to the database...");
                        // Get a database connection and prepare a query statement
                    	 try (final Connection connection = DriverManager.getConnection(URL)) {
                         	try (
                                     final PreparedStatement statement1 = connection.prepareStatement(QUERY_TEMPLATE_7_a)) {
                                     // Populate the query template with the data collected from the user
                                 	//System.out.print(cname);
		                         		statement1.setInt(1, jobCompleted);
		                                statement1.setInt(2, laborTime);
		                                statement1.setInt(3, job_type);
		                                statement1.setInt(4, job_no);
                                     System.out.println("Dispatching the query 7(a)...");
                                     // Actually execute the populated query
                                     final int rows_inserted = statement1.executeUpdate();
                                     System.out.println(String.format("Done. %d rows inserted in \"Job\" table..", rows_inserted));
                                     
                                 }
                         	
                         	try (
                                     final PreparedStatement statement3 = connection.prepareStatement(QUERY_TEMPLATE_7_c)) {
                                     // Populate the query template with the data collected from the user
                                 	//System.out.print(cname);
                                     statement3.setString(1, color);
                                     statement3.setInt(2, volume);
                                     statement3.setInt(3, job_no);
                                     statement3.setInt(4, laborTime);
                                     
                                     System.out.println("Dispatching the query 7(c)...");
                                     // Actually execute the populated query
                                     final int rows_inserted = statement3.executeUpdate();
                                     System.out.println(String.format("Done. %d rows inserted in \"painjob\" table..", rows_inserted));
                                     
                                 }
                         }
                    }
                    else
                    {
                    	System.out.println("Please enter the labor time in number of hours:");
                    	final int laborTime = sc.nextInt();
                    	sc.nextLine();
                    	System.out.println("Connecting to the database...");
                        // Get a database connection and prepare a query statement
                    	 try (final Connection connection = DriverManager.getConnection(URL)) {
                         	try (
                                     final PreparedStatement statement1 = connection.prepareStatement(QUERY_TEMPLATE_7_a)) {
                                     // Populate the query template with the data collected from the user
                                 	//System.out.print(cname);
	                         		statement1.setInt(1, jobCompleted);
	                                statement1.setInt(2, laborTime);
	                                statement1.setInt(3, job_type);
	                                statement1.setInt(4, job_no);
                                     System.out.println("Dispatching the query 7(a)...");
                                     // Actually execute the populated query
                                     final int rows_inserted = statement1.executeUpdate();
                                     System.out.println(String.format("Done. %d rows inserted in \"Job\" table..", rows_inserted));
                                     
                                 }
                         	
                         	try (
                                     final PreparedStatement statement3 = connection.prepareStatement(QUERY_TEMPLATE_7_d)) {
                                     // Populate the query template with the data collected from the user
                                 	//System.out.print(cname);
                                     
                                     statement3.setInt(1, job_no);
                                     statement3.setInt(2, laborTime);
                                     
                                     System.out.println("Dispatching the query 7(d)...");
                                     // Actually execute the populated query
                                     final int rows_inserted = statement3.executeUpdate();
                                     System.out.println(String.format("Done. %d rows inserted in \"fitjob\" table..", rows_inserted));
                                     
                                 }
                         }
                    }
                	break;
                	
                case "8":
                	int assAcc, procAcc, deptAcc;
                	System.out.println("Please enter a new Transaction Number:");
                	final int tNo = sc.nextInt();
                	sc.nextLine();
                	System.out.println("Please enter the cost of the transaction:");
                	final int tcost = sc.nextInt();
                	sc.nextLine();
                	System.out.println("Please enter the corresponding Job Number:");
                	final int jNo = sc.nextInt();
                	sc.nextLine();
                	System.out.println("Connecting to the database...");
                    // Get a database connection and prepare a query statement
                	 try (final Connection connection = DriverManager.getConnection(URL)) {
                		 try (
                                 final PreparedStatement statement3 = connection.prepareStatement(QUERY_TEMPLATE_8_a)) {
                                 // Populate the query template with the data collected from the user
                             	//System.out.print(cname);
                                 
                                 statement3.setInt(1, tNo);
                                 statement3.setInt(2, tcost);
                                 
                                 System.out.println("Dispatching the query 8(a)...");
                                 // Actually execute the populated query
                                 final int rows_inserted = statement3.executeUpdate();
                                 System.out.println(String.format("Done. %d rows inserted in \"transaction1\" table..", rows_inserted));
                                 
                             }
                		 try (
                                 final PreparedStatement statement4 = connection.prepareStatement(QUERY_TEMPLATE_8_b)) {
                                 // Populate the query template with the data collected from the user
                             	//System.out.print(cname);
                                 
                                 statement4.setInt(1, tNo);
                                 statement4.setInt(2, jNo);
                                 
                                 System.out.println("Dispatching the query 8(b)...");
                                 // Actually execute the populated query
                                 final int rows_inserted = statement4.executeUpdate();
                                 System.out.println(String.format("Done. %d rows inserted in \"record\" table..", rows_inserted));
                                 
                             }
                		 try (
                                 final PreparedStatement statement3 = connection.prepareStatement(QUERY_TEMPLATE_8_c)) {
                                 // Populate the query template with the data collected from the user
                             	//System.out.print(cname);
                                 
                                 statement3.setInt(1, jNo);
                                 System.out.println("Dispatching the query 8(c)...");
                                 // Actually execute the populated query
                                 ResultSet rs = statement3.executeQuery();
                                 
                                 while (rs.next()) {
                                 	int assId = rs.getInt("Assemblyid");
                                 	
                                 	int pId = rs.getInt("processiD");
                                 	
                                 	try (
                                            final PreparedStatement statement5 = connection.prepareStatement(QUERY_TEMPLATE_8_d)) {
                                            // Populate the query template with the data collected from the user
                                        	//System.out.print(cname);
                                            
                                            statement5.setInt(1, pId);
                                            System.out.println("Dispatching the query 8(d)...");
                                            // Actually execute the populated query
                                            ResultSet rs1 = statement5.executeQuery();
                                            while (rs1.next()) {
                                            	int deptNo = rs1.getInt("departmentnumber");
                                            	
                                            	try (
                                                        final PreparedStatement statement10 = connection.prepareStatement(QUERY_TEMPLATE_8_e)) {
                                                        // Populate the query template with the data collected from the user
                                                    	//System.out.print(cname);
                                                        
                                                        statement10.setInt(1, deptNo);
                                                        System.out.println("Dispatching the query 8(e)...");
                                                        // Actually execute the populated query
                                                        ResultSet rs2 = statement10.executeQuery();
                                                        while (rs2.next()) {
                                                        	deptAcc = rs1.getInt(1);
                                                        	try (
                                                                    final PreparedStatement statement11 = connection.prepareStatement(QUERY_TEMPLATE_8_h)) {
                                                        			statement11.setInt(1, deptAcc);
                                                        			System.out.println("Dispatching query 8(h)...");
                                                        			ResultSet rs3 = statement11.executeQuery();
                                                        			float sup_cost = rs3.getFloat("sup_cost");
                                                        			sup_cost = sup_cost + tcost;
                                                        			try (
                                                                            final PreparedStatement statement12 = connection.prepareStatement(QUERY_TEMPLATE_8_K)) {
                                                        				statement12.setFloat(1, sup_cost);
                                                        				System.out.println("Dispatching query 8(K)....");
                                                        				final int rows_inserted = statement12.executeUpdate();
                                                                        System.out.println(String.format("Done. %d rows inserted in \"departmentaccount\" table..", rows_inserted));
                                                        			}
                                                        	}
                                                        }
                                                        
                                                    }
                                            	
                                            }
                                            
                                        }
                                 }
                                 
                             }
                	 }
                	
                	
                case "16":
                	System.out.println("Please Enter the path of the file for importing data:");
                	sc.nextLine();
                	final String filePath = sc.nextLine();
                	BufferedReader br; 
                	String line = "";  
                	String splitBy = ",";
				try {
					br = new BufferedReader(new FileReader(filePath));
					try (final Connection connection = DriverManager.getConnection(URL)) {
						while ((line = br.readLine()) != null)   //returns a Boolean value  
						{  
							String[] customer = line.split(splitBy);    // use comma as separator  
							//System.out.println("Customer Name= " + customer[0] + "\nAddress=" + customer[1] + "\nCategory=" + customer[2]);
							try (
                                    final PreparedStatement statement1 = connection.prepareStatement(QUERY_TEMPLATE_1)) {
                                    // Populate the query template with the data collected from the user
                                	//System.out.print(cname);
                                    statement1.setString(1, customer[0]);
                                    statement1.setString(2, customer[1]);
                                    statement1.setInt(3, Integer.parseInt(customer[2]));
                                    
                                    System.out.println("Dispatching the query 1...");
                                    // Actually execute the populated query
                                    final int rows_inserted = statement1.executeUpdate();
                                    System.out.println(String.format("Done. %d rows inserted in \"customer\" table..", rows_inserted));
                                    
                                }
						}
						
					br.close();
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					System.out.println("Could not find the file specified. Please try again!");
					e.printStackTrace();
					break;
				} catch (IOException e) {
					System.out.println("IO Exception Occured! Please Try Again!");
					e.printStackTrace();
					break;
				}
				break;
				
                case "10":
                	System.out.println("Please enter the Department number for determining the labor cost:");
                	final int deptNo = sc.nextInt();
                	sc.nextLine();
                	
                	System.out.println("Please enter the date of completion for the jobs:");
                	final int dateCompleted = sc.nextInt();
                	sc.nextLine();
                	// Determining the Processes supervised by the given Department:
                	try (final Connection connection = DriverManager.getConnection(URL)) {
                		try (
                                final PreparedStatement statement1 = connection.prepareStatement(QUERY_TEMPLATE_10_a)) {
                                // Populate the query template with the data collected from the user
                            	//System.out.print(cname);
                                statement1.setInt(1, deptNo);
                                System.out.println("Dispatching the query 1...");
                                // Actually execute the populated query
                                ResultSet rs = statement1.executeQuery();
                                int psize = 0;
                                int jsize = 0;
                                int[] pArray = new int[100];
                                int[] jArray = new int[100];
                                int pindex = 0;
                                int jindex = 0;
                                while (rs.next()) {
                                	int pid = rs.getInt("processId");
                                	pArray[pindex] = pid;
                                	pindex++;
                                	psize++;
                                }
                                for (int i = 0; i < psize; i++)
                                {
                                	System.out.println(pArray[i]);
                                	try (
                                            final PreparedStatement statement2 = connection.prepareStatement(QUERY_TEMPLATE_10_b)) {
	                                		statement2.setInt(1, pArray[i]);
	                                        System.out.println("Dispatching the query 10(b)...");
	                                        ResultSet rs1 = statement2.executeQuery();
	                                        int numJobs = 0;
	                                        while(rs1.next()) {
	                                        	int jno = rs1.getInt("jobnomber");
	                                        	jArray[jindex] = jno;
	                                        	jindex++;
	                                        	
	                                        }
	                                        
                                	}
                                	
                                }
                                int[] dcArray = new int[jindex];
                                int[] ltArray = new int[jindex];
                                for(int k = 0; k < jindex; k++)
                                {
                                	System.out.println(jArray[k]);
                                	try (
                                            final PreparedStatement statement4 = connection.prepareStatement(QUERY_TEMPLATE_10_c)) {
	                                		statement4.setInt(1, jArray[k]);
	                                        System.out.println("Dispatching the query 10(c)...");
	                                        ResultSet rs2 = statement4.executeQuery();
	                                        
	                                        while(rs2.next()) {
	                                        	ltArray[k] = rs2.getInt("labortime");
	                                        	dcArray[k] = rs2.getInt("dateofjobcompleted");
	                                        }     
                                	}
                                }
                                int totalLaborTime = 0;
                                for (int l = 0; l < jindex; l++) {
                                	if(dcArray[l] == dateCompleted)
                                		totalLaborTime += ltArray[l];
                                }
                                System.out.println("The total labor time within the given " + deptNo + " departmen for jobs completed during the " + 
                                		dateCompleted +  " date is: " + totalLaborTime + " hours.");
                            		
                                
                                //System.out.println(String.format("Done. %d rows inserted in \"customer\" table..", rows_inserted));
                               
                            }
                	}
                	break;
                	
                case "11":
                	System.out.println("Please enter the assembly ID through which the passed processes have to be determined:");
                	final int aID = sc.nextInt();
                	sc.nextLine();
                	
                	try (final Connection connection = DriverManager.getConnection(URL)) {
                		try (
                                final PreparedStatement statement1 = connection.prepareStatement(QUERY_TEMPLATE_11)) {
                				statement1.setInt(1, aID);
                				System.out.println("Dispatching the query 11...");
                                ResultSet rs1 = statement1.executeQuery();
                                System.out.println("Process ID  Department Number");
                                while(rs1.next()) {
                                	System.out.println(rs1.getInt(1) + "\t" + rs1.getInt(2)); 
                                }
                		}
                	}
                	break;
                	
                case "12":
                	System.out.println("Please enter the date of completion of the job:");
                	final int jdate = sc.nextInt();
                	sc.nextLine();
                	System.out.println("Please enter the Department for which the jobs are to be determined:");
                	final int dNo = sc.nextInt();
                	sc.nextLine();
                	System.out.println("Connecting to the database...");
                	try (final Connection connection = DriverManager.getConnection(URL)) {
                		try (final PreparedStatement statement1 = connection.prepareStatement(QUERY_TEMPLATE_12_a)) {
                			statement1.setInt(1, dNo);
                			System.out.println("Dispatching the query 12(a)...");
                			ResultSet rs1 = statement1.executeQuery();
                			while(rs1.next()) {
                				int pID = rs1.getInt(1);
                				try (final PreparedStatement statement2 = connection.prepareStatement(QUERY_TEMPLATE_12_b)) {
                					statement2.setInt(1, pID);
                					System.out.println("Dispatching the query 12(b)");
                					ResultSet rs2 = statement2.executeQuery();
                					while (rs2.next()) {
                						int jID = rs2.getInt(1);
                						int assID = rs2.getInt(2);
                						try (final PreparedStatement statement3 = connection.prepareStatement(QUERY_TEMPLATE_12_c)) {
                							statement3.setInt(1, jID);
                							statement3.setInt(2, jdate);
                							System.out.println("Dispatching query 12(c)");
                							ResultSet rs3 = statement3.executeQuery();
                							while(rs3.next()) {
                								int jType = rs3.getInt(1);
                								if(jType == 1) {
                									System.out.println(jID + "\t" + assID + "\t" + "Cut Job");
                								}
                								else if(jType == 2) {
                									System.out.println(jID + "\t" + assID + "\t" + "Paint Job");
                								}
                								else
                									System.out.println(jID + "\t" + assID + "\t" + "Fit Job");
                							}
                						}
                					}
                				}
                			}
                		}
                               
                	}
                	break;
                case "17":
                	System.out.println("Please input the minimum category:");
                	final int catmin = sc.nextInt();
                	sc.nextLine();
                	System.out.println("Please input the maximum category:");
                	final int catmax = sc.nextInt();
                	sc.nextLine();
                	System.out.println("Please enter the output file name (please don't give the extension)");
                	final String fileName = sc.nextLine();
                    System.out.println("Connecting to the database...");
                    // Get the database connection, create statement and execute it right away, as no user input need be collected
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        System.out.println("Dispatching the query...");
                        try (
                        		final PreparedStatement statement2 = connection.prepareStatement(QUERY_TEMPLATE_13)) {
                        		
                        		statement2.setInt(1, catmin);
                        		statement2.setInt(2, catmax);
                        		System.out.println("Dispatching the query 13...");
                        		ResultSet rs1 = statement2.executeQuery();
                                // Unpack the tuples returned by the database and print them out to the user
                        		try (PrintWriter writer = new PrintWriter(fileName + ".csv")) {
                                while (rs1.next()) {
                                	StringBuilder sb = new StringBuilder();
                                        String name = rs1.getString("cname");
                                        sb.append(name);
                                        sb.append(',');
                                        String address = rs1.getString("caddress");
                                        sb.append(address);
                                        sb.append(',');
                                        int cat = rs1.getInt("category");
                                        sb.append(cat);
                                        sb.append('\n');
                                        writer.write(sb.toString());
                                        //System.out.println(name + "\t" + address + "\t\t" + cat);
                                        //resultSet.getString(2),
                                        //resultSet.getString(3)
                                        
                                }
                        		}catch (FileNotFoundException e) {
                        		      System.out.println(e.getMessage());
                        	    }
                        }
                        
                    }
                    break;
                case "14":
                	System.out.println("Please enter the mininum Job Number:");
                	final int minjNo = sc.nextInt();
                	sc.nextLine();
                	System.out.println("Please enter the maximum Job Number:");
                	final int maxjNo = sc.nextInt();
                	sc.nextLine();
                    System.out.println("Connecting to the database...");
                    // Get the database connection, create statement and execute it right away, as no user input need be collected
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                    	try (
                                final PreparedStatement statement1 = connection.prepareStatement(QUERY_TEMPLATE_14_a)) {
                                // Populate the query template with the data collected from the user
                            	//System.out.print(cname);
                    			statement1.setInt(1, minjNo);
                    			statement1.setInt(2, maxjNo);
                                System.out.println("Dispatching the query 14(a)...");
                                // Actually execute the populated query
                                final int rows_inserted = statement1.executeUpdate();
                                System.out.println(String.format("Done. %d rows deleted in \"cutjob\" table..", rows_inserted));
                            }
                    	
                    	try (
                                final PreparedStatement statement2 = connection.prepareStatement(QUERY_TEMPLATE_14_b)) {
                                // Populate the query template with the data collected from the user
                            	//System.out.print(cname);
                                statement2.setInt(1, minjNo);
                                statement2.setInt(2, maxjNo);
                                System.out.println("Dispatching the query 14(b)...");
                                // Actually execute the populated query
                                final int rows_inserted = statement2.executeUpdate();
                                System.out.println(String.format("Done. %d rows deleted in \"assign\" table..", rows_inserted));
                            }
                    	try (
                                final PreparedStatement statement3 = connection.prepareStatement(QUERY_TEMPLATE_14_c)) {
                                // Populate the query template with the data collected from the user
                            	//System.out.print(cname);
                                statement3.setInt(1, minjNo);
                                statement3.setInt(2, maxjNo);
                                System.out.println("Dispatching the query 14(c)...");
                                // Actually execute the populated query
                                final int rows_inserted = statement3.executeUpdate();
                                System.out.println(String.format("Done. %d rows deleted in \"job\" table..", rows_inserted));
                            }
                    }
                	  break;
                	  
                case "15":
                	System.out.println("Please enter the Job Number for the color change:");
                	final int jobno = sc.nextInt();
                	sc.nextLine();
                	System.out.println("Please enter the new color for the job:");
                	final String color = sc.nextLine();
                	System.out.println("Connecting to the database...");
                    // Get the database connection, create statement and execute it right away, as no user input need be collected
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                    	try (
                                final PreparedStatement statement1 = connection.prepareStatement(QUERY_TEMPLATE_15)) {
                                // Populate the query template with the data collected from the user
                            	//System.out.print(cname);
                    			statement1.setString(1, color);
                    			statement1.setInt(2, jobno);
                                System.out.println("Dispatching the query 15...");
                                // Actually execute the populated query
                                final int rows_inserted = statement1.executeUpdate();
                                System.out.println(String.format("Done. %d rows updated in \"painjob\" table..", rows_inserted));
                            }
                    }
                	
                case "20": // Do nothing, the while loop will terminate upon the next iteration
                    System.out.println("Exiting! Good-buy!");
                    System.exit(0);
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
