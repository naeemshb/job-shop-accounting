import java.sql.Connection;
import java.sql.Statement;
import java.sql.Types;
import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.io.*;  

public class Final1 {

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
    final static String QUERY_TEMPLATE_3_1 = "INSERT INTO fit " + 
											"VALUES (?, ?);";
    final static String QUERY_TEMPLATE_3_2 = "INSERT INTO paint " + 
			"VALUES (?, ?, ?);";
    final static String QUERY_TEMPLATE_3_3 = "INSERT INTO cut " + 
			"VALUES (?, ?, ?);";
    final static String QUERY_TEMPLATE_3_4 ="INSERT into supervise " +
											"VALUES (?, ?, ?);"; 
    final static String QUERY_TEMPLATE_4_1 ="INSERT into assembly " +
			"VALUES (?, ?, ?);"; 
    final static String QUERY_TEMPLATE_4_2 ="INSERT into order1 " +
			"VALUES (?, ?);"; 
    final static String QUERY_TEMPLATE_4_3 ="INSERT into passthrugh " +
			"VALUES (?, ?);"; 
    final static String QUERY_TEMPLATE_5_1 ="INSERT into account " +
			"VALUES (?, ?);"; 
    final static String QUERY_TEMPLATE_5_2 ="INSERT into departmentaccount " +
			"VALUES (?, ?);"; 
    final static String QUERY_TEMPLATE_5_3 ="INSERT into assemblyaccount " +
			"VALUES (?, ?);"; 
    final static String QUERY_TEMPLATE_5_4 ="INSERT into processaccount " +
			"VALUES (?, ?);"; 
    final static String QUERY_TEMPLATE_5_5 ="INSERT into record_dept_cost " +
			"VALUES (?, ?);"; 
    final static String QUERY_TEMPLATE_5_6 ="INSERT into record_assembly_cost " +
			"VALUES (?, ?);"; 
    final static String QUERY_TEMPLATE_5_7 ="INSERT into record_process_cost " +
			"VALUES (?, ?);"; 
    final static String QUERY_TEMPLATE_6_1 ="INSERT into job  " +
			"VALUES (?, ?, ?, ?, ?);";
    final static String QUERY_TEMPLATE_6_2 ="INSERT into assign  " +
			"VALUES (?, ?, ?);";
    
    final static String QUERY_TEMPLATE_7_1 ="UPDATE job " +
			"SET dateofjobcompleted = (?), labortime = (?), job_type = (?)" +
			"Where jobnomber = (?);";
    final static String QUERY_TEMPLATE_7_2 ="INSERT into cutjob  " +
			"VALUES (?, ?, ?, ?, ?);";
    final static String QUERY_TEMPLATE_7_3 ="INSERT into painjob  " +
			"VALUES (?, ?, ?, ?);";
    final static String QUERY_TEMPLATE_7_4 ="INSERT into fitjob  " +
			"VALUES (?, ?);";
    final static String QUERY_TEMPLATE_8_1 ="INSERT into transaction1  " +
			"VALUES (?, ?);";
    final static String QUERY_TEMPLATE_8_2 ="INSERT into record  " +
			"VALUES (?, ?);";
    final static String QUERY_TEMPLATE_8_3 ="SELECT Assemblyid, processId from assign  " +
			" WHERE jobnomber = (?);";
    final static String QUERY_TEMPLATE_8_4 ="SELECT departmentnumber FROM supervise " +
			"WHERE processId =(?);";
    final static String QUERY_TEMPLATE_8_5 ="SELECT account_num FROM record_dept_cost " +
			"WHERE dept_num =(?);";
    final static String QUERY_TEMPLATE_8_6 ="SELECT account_num FROM record_assembly_cost " +
			"WHERE assembly_id =(?);";
    final static String QUERY_TEMPLATE_8_7 ="SELECT account_num FROM record_process_cost " +
			"WHERE process_id =(?);";
    final static String QUERY_TEMPLATE_8_8 ="SELECT sup_cost FROM departmentaccount " +
			"WHERE accountnumber =(?);";
    final static String QUERY_TEMPLATE_8_9 ="SELECT sup_cost FROM assemblyaccount " +
			"WHERE accountnumber =(?);";
    final static String QUERY_TEMPLATE_8_10 ="SELECT sup_cost FROM processaccount " +
			"WHERE accountnumber =(?);";
    final static String QUERY_TEMPLATE_8_11 = "UPDATE departmentaccount " +
			"SET sup_cost = (?)" +
			"Where accountnumber = (?);";
    final static String QUERY_TEMPLATE_8_12 = "UPDATE assemblyaccount " +
			"SET sup_cost = (?)" +
			"Where accountnumber = (?);";
    final static String QUERY_TEMPLATE_8_13 = "UPDATE processaccount " +
			"SET sup_cost = (?)" +
			"Where accountnumber = (?);";
    
    final static String QUERY_TEMPLATE_10_1 ="SELECT DISTINCT processId " + 
			"FROM supervise WHERE departmentnumber =  (?); ";
    final static String QUERY_TEMPLATE_10_2 ="SELECT DISTINCT jobnomber " + 
			"FROM assign WHERE processId =  (?); ";
    final static String QUERY_TEMPLATE_10_3 ="SELECT labortime, dateofjobcompleted " + 
			"FROM Job WHERE jobNomber =  (?); ";
    final static String QUERY_TEMPLATE_11 = "select supervise.processId, departmentnumber "
    		+ "from Assembly, process, supervise "
    		+ "where supervise.processId = process.processId "
    		+ "and Assembly.Assemblyid = (?) "
    		+ "order by dateofordered ASC";
    final static String QUERY_TEMPLATE_12_1 = "select processId from supervise "+
    		"where departmentnumber = (?)";
    final static String QUERY_TEMPLATE_12_2 = "select jobNomber, Assemblyid from assign " +
    		"where processId = (?)";
    final static String QUERY_TEMPLATE_12_3 = "select job_type from job "+
    		"where jobnomber = (?) and dateofjobcompleted = (?)";
    final static String QUERY_TEMPLATE_13 = "SELECT * FROM customer WHERE category >= (?) and category < (?) "   ;
    
    final static String QUERY_TEMPLATE_14_1 = "delete from cutjob Where jobnomber > (?) and jobnomber < (?) " ;
    final static String QUERY_TEMPLATE_14_2 = "delete from assign Where jobnomber > (?) and jobnomber < (?) " ;
    final static String QUERY_TEMPLATE_14_3 = "delete from job Where jobnomber > (?) and jobnomber < (?) " ;
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
    		            "7) At the completion of a job, enter the date it completed: \n " +
    		            "8) Enter a transaction-no and its sup-cost and update all the costs: \n" + 
    		            "9) Retrieve the total cost incurred on an assembly-id \n " +
    		            "10)Retrieve total labor time within a Department for a given day: \n " +
    		            "11)Retrieve the process through which a given assembly-id has passed: \n" +
    		            "12)Retrieve the jobs completed during given date in a given department: \n"+
    		            "13)Retrieve the customers (in name order) whose category is in a given range: \n" +
    		            "14)Delete all cut-jobs whose job-no is in a given range: \n"+
    		            "15)Change the color of a given paint job\n" +
    		            "16)Import: enter new customers from a data file: \n"+
    		            "17)Export: Retrieve the customers \n" + 
    		            "18) Quit!";

    public static void main(String[] args) throws SQLException {

        System.out.println("WELCOME TO THE JOB-SHOP ACCOUNTING DATABASE SYSTEM");

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
                        	
                            statement.setInt(1, departmentnumber);
                            statement.setString(2, departmentdata);
                            

                            System.out.println("Dispatching the query...");
                            // Actually execute the populated query
                            final int rows_inserted = statement.executeUpdate();
                            System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
                        }
                    }

                    break;
                case "3": // Insert a new process option
                    // Collect the new process data from the user
                    System.out.println("Please enter process Id:");
                    final int processId = sc.nextInt(); // Read in the user input of student ID
                    sc.nextLine();
                    System.out.println("Please enter the process Data:");
                    final String processData = sc.nextLine();
                           
                    sc.nextLine();
                    System.out.println("Please enter the Supervising Department ID:" );
                    final int deptnum = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Please enter the Department data:");
                    final String deptData = sc.nextLine();
                    
                    System.out.println("Please enter : 1. For Fit , 2. For Paint  , 3. for Cut Process:");
                    final int processmodel = sc.nextInt();
                    sc.nextLine();
                    if (processmodel == 1) {
                    	//Fit Process
                    	System.out.println("Please enter Fit type:");
                    	final String fittype = sc.nextLine();
                    	sc.nextLine();
                    	System.out.println("Connecting to the database...");
                        // Get a database connection and prepare a query statement
                        try (final Connection connection = DriverManager.getConnection(URL)) {
                        	try (
                                    final PreparedStatement statement1 = connection.prepareStatement(QUERY_TEMPLATE_3)) {
                                    // Populate the query template with the data collected from the user
                                	
                                    statement1.setInt(1, processId);
                                    statement1.setString(2, processData);
                                   
                                    System.out.println("Dispatching the query...");
                                    // Actually execute the populated query
                                    final int rows_inserted = statement1.executeUpdate();
                                    System.out.println(String.format("Done. %d rows inserted in \"process\" table.", rows_inserted));
                                   
                                }
                            try (
                                final PreparedStatement statement2 = connection.prepareStatement(QUERY_TEMPLATE_3_1)) {
                                // Populate the query template with the data collected from the user
                            
                                statement2.setInt(2, processId);
                                statement2.setString(1, fittype);
                               
                                System.out.println("Dispatching the query...");
                                // Actually execute the populated query
                                final int rows_inserted = statement2.executeUpdate();
                                System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
                               
                            }
                            
                        }
                        
                    	
                    }
                    else if (processmodel == 2) {
                    	//information for Paint Process
                    	System.out.println("Please enter Paint type:");
                    	final String painttype = sc.nextLine();
                    	//sc.nextLine();
                    	System.out.println("Please enter Paint method:");
                    	final String paintingmethod = sc.nextLine();
                    	//sc.nextLine();
                    	
                    	System.out.println("Connecting to the database...");
                        // Get a database connection and prepare a query statement
                        try (final Connection connection = DriverManager.getConnection(URL)) {
                        	
                        	 try (
                                     final PreparedStatement statement1 = connection.prepareStatement(QUERY_TEMPLATE_3)) {
                                     // Populate the query template with the data collected from the user
                                 	
                                     statement1.setInt(1, processId);
                                     statement1.setString(2, processData);
                                    
                                     System.out.println("Dispatching the query...");
                                     // Actually execute the populated query
                                     final int rows_inserted = statement1.executeUpdate();
                                     System.out.println(String.format("Done. %d rows inserted in the process table.", rows_inserted));
                                    
                                 }
                            try (
                                final PreparedStatement statement2 = connection.prepareStatement(QUERY_TEMPLATE_3_2)) {
                                // Populate the query template with the data collected from the user
                            	
                                statement2.setInt(3, processId);
                                statement2.setString(1, painttype);
                                statement2.setString(2, paintingmethod);
                                System.out.println("Dispatching the query...");
                                // Actually execute the populated query
                                final int rows_inserted = statement2.executeUpdate();
                                System.out.println("Executing Statement 3(2)...");
                                System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
                                
                               
                            }
                           
                        }
                        
                    }
                    else {
                    	// information for Cutting
                    	System.out.println("Please enter the Cutting Type:");
                    	final String cuttingType = sc.nextLine();
                    	System.out.println("Please enter the Machine Type:");
                    	final String machineType = sc.nextLine();
                    	System.out.println("Connecting to the database...");
                        // Get a database connection and prepare a query statement
                        try (final Connection connection = DriverManager.getConnection(URL)) {
                        	
                        	try (
                                    final PreparedStatement statement1 = connection.prepareStatement(QUERY_TEMPLATE_3)) {
                                    // Populate the query template with the data collected from the user
                                	
                                    statement1.setInt(1, processId);
                                    statement1.setString(2, processData);
                                   
                                    System.out.println("Dispatching the query...");
                                    // Actually execute the populated query
                                    final int rows_inserted = statement1.executeUpdate();
                                    System.out.println(String.format("Done. %d rows inserted in process table.", rows_inserted));
                                   
                                }
                     
                            try (
                                final PreparedStatement statement3 = connection.prepareStatement(QUERY_TEMPLATE_3_3)) {
                                // Populate the query template with the data collected from the user                            	
                                statement3.setInt(3, processId);
                                statement3.setString(1, cuttingType);
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
                            final PreparedStatement statement4 = connection.prepareStatement(QUERY_TEMPLATE_3_4)) {
                            // Populate the query template with the data collected from the user
                        	
                            statement4.setInt(1, processId);
                            statement4.setInt(2, deptnum);
                            statement4.setString(3, deptData);
                           
                            System.out.println("Dispatching the query 3(4)...");
                            // Actually execute the populated query
                            final int rows_inserted = statement4.executeUpdate();
                            System.out.println(String.format("Done. %d rows inserted in supervise table.", rows_inserted));
                            
                        }
                    }
                    break;
                case "4":
                	System.out.println(" enter a new Assembly ID:");
                	final int assemblyid = sc.nextInt();
                	sc.nextLine();
                	System.out.println("Please enter the Date of ordered of assembly:");
                	final int dateof_ordered = sc.nextInt();
                	sc.nextLine();
                	System.out.println("Please enter the Assembly details:");
                	final String assemblyDetails = sc.nextLine();
                	System.out.println("Please enter the associated Customer Name:");
                	final String assembly_customername = sc.nextLine();
                	System.out.println("Please enter the number of associated process IDs with this assembly");
                	final int numassemblies = sc.nextInt();
                	sc.nextLine();
                	int[] assemblyrep = new int[numassemblies];
                	for (int j = 0; j < numassemblies; j++)
                	{
                		System.out.println(" enter %dth process ID:");
                		assemblyrep[j] = sc.nextInt();
                		sc.nextLine();
                	}
                	
                	try (final Connection connection = DriverManager.getConnection(URL)) {
                		try (
                                final PreparedStatement statement4 = connection.prepareStatement(QUERY_TEMPLATE_4_1)) {
                                // Populate the query template with the data collected from the user
                            	
                                statement4.setInt(1, assemblyid);
                                statement4.setInt(2, dateof_ordered);
                                statement4.setString(3, assemblyDetails);
                               
                                System.out.println("Dispatching the query 4(1)...");
                                // Actually execute the populated query
                                final int rows_inserted = statement4.executeUpdate();
                                System.out.println(String.format("Done. %d rows inserted in Assembly table.", rows_inserted));
                                
                            }
                		try (
                                final PreparedStatement statement5 = connection.prepareStatement(QUERY_TEMPLATE_4_2)) {
                                
                                statement5.setInt(1, assemblyid);
                                statement5.setString(2, assembly_customername);
                               
                               
                                System.out.println("Dispatching the query 4(2)...");
                                // Actually execute the populated query
                                final int rows_inserted = statement5.executeUpdate();
                                System.out.println(String.format("Done. %d rows inserted in order1 table.", rows_inserted));
                                
                            }
                		try (
                                final PreparedStatement statement6 = connection.prepareStatement(QUERY_TEMPLATE_4_3)) {
                                // Populate the query template with the data collected from the user
                            	
                				for (int i = 0; i< numassemblies; i++)
                				{
                					statement6.setInt(1, assemblyid);
                                    statement6.setInt(2, assemblyrep[i]);
                                    System.out.println("Dispatching the query 4(3)...");
                                    // Actually execute the populated query
                                    final int rows_inserted = statement6.executeUpdate();
                                    System.out.println(String.format("Done. %d rows inserted in passthrugh table.", rows_inserted));
                				}
                         
                            }
                	}
                	break;
                case "5":
                	System.out.println(" enter a new Account number");
                    final int accountnumb = sc.nextInt(); // Read in the user input of student ID
                    sc.nextLine();
                    System.out.println(" enter the date of account commenced please:");
                    final int accountdate = sc.nextInt();
          
          
                    System.out.println(" enter the account type (1,2,3): 1.Departmentt Account, 2.Assembly Account, 3.Process Account:");
                    final int Type = sc.nextInt();
                    sc.nextLine();
                    if (Type == 1) {
                    	System.out.println(" enter the department number which is associated with the account:");
                    	final int deptNo = sc.nextInt();
                    	sc.nextLine();
                    	System.out.println("Connecting to the database...");
                        // Get a database connection and prepare a query statement
                        try (final Connection connection = DriverManager.getConnection(URL)) {
                        	try (
                                    final PreparedStatement statement1 = connection.prepareStatement(QUERY_TEMPLATE_5_1)) {
                                    // Populate the query template with the data collected from the user
                                	
                                    statement1.setInt(1, accountnumb);
                                    statement1.setInt(2, accountdate);
                                   
                                    System.out.println("Dispatching the query 5(1)...");
                                    // Actually execute the populated query
                                    final int rows_inserted = statement1.executeUpdate();
                                    System.out.println(String.format("Done. %d rows inserted in account table.", rows_inserted));
                                   
                                }
                            try (
                                final PreparedStatement statement2 = connection.prepareStatement(QUERY_TEMPLATE_5_2)) {
                                // Populate the query template with the data collected from the user
                            	
                                statement2.setInt(1, accountnumb);
                                statement2.setInt(2, 0);
                               
                                System.out.println("Dispatching the query 5(2)...");
                                // Actually execute the populated query
                                final int rows_inserted = statement2.executeUpdate();
                                System.out.println(String.format("Done. %d rows inserted in department account table .", rows_inserted));
                               
                            }
                            try (
                                    final PreparedStatement statement3 = connection.prepareStatement(QUERY_TEMPLATE_5_5)) {
                                    // Populate the query template with the data collected from the user
                                	
                                    statement3.setInt(1, accountnumb );
                                    statement3.setInt(2, deptNo);
                                   
                                    System.out.println("Dispatching the query 5(5)...");
                                    // Actually execute the populated query
                                    final int rows_inserted = statement3.executeUpdate();
                                    System.out.println(String.format("Done. %d rows inserted in record dept cost table .", rows_inserted));
                                   
                                }
                            
                        }
                        
                    	
                    }
                    else if (Type == 2) {
                    	//information for Paint Process
                    	System.out.println("Please enter the associated Assembly ID: ");
                    	final int AssemblyID = sc.nextInt();
                    	sc.nextLine();
                    	
                    	System.out.println("Connecting to the database...");
                        // Get a database connection and prepare a query statement
                        try (final Connection connection = DriverManager.getConnection(URL)) {
                        	
                        	 try (
                                     final PreparedStatement statement1 = connection.prepareStatement(QUERY_TEMPLATE_5_1)) {
                                     // Populate the query template with the data collected from the user
                                 	
                        		 statement1.setInt(1, accountnumb);
                                 statement1.setInt(2, accountdate);
                                
                                 System.out.println("Dispatching the query 5(a)...");
                                 // Actually execute the populated query
                                 final int rows_inserted = statement1.executeUpdate();
                                 System.out.println(String.format("Done. %d rows inserted in account table.", rows_inserted));
                                    
                                 }
                            try (
                                final PreparedStatement statement9 = connection.prepareStatement(QUERY_TEMPLATE_5_3)) {
                                // Populate the query template with the data collected from the user
                            	
                                statement9.setInt(1, accountnumb);
                                statement9.setInt(2, 0);
                                System.out.println("Dispatching the query 5(3)...");
                                // Actually execute the populated query
                                final int rows_inserted = statement9.executeUpdate();
                                System.out.println("Executing Statement 5(c)...");
                                System.out.println(String.format("Done. %d rows inserted in assemblyaccount table.", rows_inserted));
                                
                               
                            }
                            try (
                                    final PreparedStatement statement3 = connection.prepareStatement(QUERY_TEMPLATE_5_6)) {
                                    // Populate the query template with the data collected from the user
                                	//System.out.print(cname);
                                    statement3.setInt(1, accountnumb);
                                    statement3.setInt(2, AssemblyID);
                                    System.out.println("Dispatching the query 5(6)...");
                                    // Actually execute the populated query
                                    final int rows_inserted = statement3.executeUpdate();
                                    System.out.println("Executing Statement 5(6)...");
                                    System.out.println(String.format("Done. %d rows inserted in record assembly cost table.", rows_inserted));
                                   
                                }
                           
                        }
                        
                    }
                    else {
                    	// information for Cut Process
                    	System.out.println(" enter the associated process ID:");
                    	final int processID = sc.nextInt();
                    	sc.nextLine();
                    	
                    	System.out.println("Connecting to the database...");
                        // Get a database connection and prepare a query statement
                        try (final Connection connection = DriverManager.getConnection(URL)) {
                        	
                        	try (
                                    final PreparedStatement statement1 = connection.prepareStatement(QUERY_TEMPLATE_5_1)) {
                                    // Populate the query template with the data collected from the user
                                	//System.out.print(cname);
                                    statement1.setInt(1, accountnumb);
                                    statement1.setInt(2, accountdate);
                                   
                                    System.out.println("Dispatching the query 5(1)...");
                                    // Actually execute the populated query
                                    final int rows_inserted = statement1.executeUpdate();
                                    System.out.println(String.format("Done. %d rows inserted in account table.", rows_inserted));
                                   
                                }
                     
                            try (
                                final PreparedStatement statement3 = connection.prepareStatement(QUERY_TEMPLATE_5_4)) {
                                // Populate the query template with the data collected from the user
                            	//System.out.print(cname);
                                statement3.setInt(1, accountnumb);
                                statement3.setInt(2, 0);
                                System.out.println("Dispatching the query 5(4)...");
                                // Actually execute the populated query
                                final int rows_inserted = statement3.executeUpdate();
                                System.out.println(String.format("Done. %d rows inserted in process account table..", rows_inserted));
                                
                            }
                            
                            try (
                                    final PreparedStatement statement3 = connection.prepareStatement(QUERY_TEMPLATE_5_7)) {
                                    // Populate the query template with the data collected from the user
                                	
                                    statement3.setInt(1, accountnumb);
                                    statement3.setInt(2, processID);
                                    System.out.println("Dispatching the query 5(7)...");
                                    // Actually execute the populated query
                                    final int rows_inserted = statement3.executeUpdate();
                                    System.out.println(String.format("Done. %d rows inserted in record process cost table..", rows_inserted));
                                    
                                }
                            
                        }
                        
                    }
                    break;
                case "6":
                	System.out.println("Please enter new Job Number:");
                	final int jobNum = sc.nextInt();
                	sc.nextLine();
                	
                    System.out.println("Please enter the date of job commenced:");
                    final int datejobCommenced = sc.nextInt();
                    sc.nextLine();
                    
                    System.out.println("Please enter the Process ID of to this job:");
                    final int processID = sc.nextInt();
                    sc.nextLine();
                    
                    System.out.println(" enter the Assembly ID of process for this job:");
                    final int assemblyID = sc.nextInt();
                    sc.nextLine();
                    
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                    	try (
                                final PreparedStatement statement1 = connection.prepareStatement(QUERY_TEMPLATE_6_1)) {
                                // Populate the query template with the data collected from the user
                            	
                                statement1.setInt(1, jobNum);
                                statement1.setInt(3, datejobCommenced);
                                statement1.setNull(2, Types.NULL);
                                statement1.setNull(4, Types.NULL);
                                statement1.setNull(5, Types.NULL);
                                System.out.println("Dispatching the query 6(1)...");
                                // Actually execute the populated query
                                final int rows_inserted = statement1.executeUpdate();
                                System.out.println(String.format("Done. %d rows inserted in Job table..", rows_inserted));
                                
                            }
                    	try (
                                final PreparedStatement statement2 = connection.prepareStatement(QUERY_TEMPLATE_6_2)) {
                                // Populate the query template with the data collected from the user
                            	
                                statement2.setInt(1, assemblyID);
                                statement2.setInt(3, jobNum);
                                statement2.setInt(2, processID);
                                System.out.println("Dispatching the query 6(2)...");
                                // Actually execute the populated query
                                final int rows_inserted = statement2.executeUpdate();
                                System.out.println(String.format("Done. %d rows inserted in assign table..", rows_inserted));
                                
                            }
                    	
                    }
                    break;
                case "7":
                	System.out.println("Please enter the Job Number which is in case 6:");
                	final int jobnum = sc.nextInt();
                	sc.nextLine();
                	
                	System.out.println("Please enter the date the job completed:");
                	final int datejobCompleted = sc.nextInt();
                	sc.nextLine();
                	
                	System.out.println("Please enter type of Job(1,2,3): 1.Cut Job, 2. Paint Job , 3.Fit Job");
                	final int type = sc.nextInt();
                	sc.nextLine();
                    
                	if (type == 1)
                    {
                    	System.out.println("Please enter the type of Machine :");
                    	final String machinetype = sc.nextLine();
                    	System.out.println(" enter the amount of time in hours:");
                    	final int time = sc.nextInt();
                    	sc.nextLine();
                    	System.out.println("Please enter the material :");
                    	final String material = sc.nextLine();
                    	System.out.println("Please enter the labor time  in hours:");
                    	final int laborTime = sc.nextInt();
                    	sc.nextLine();
                    	
                    	
                    	System.out.println("Connecting to the database...");
                        // Get a database connection and prepare a query statement
                        try (final Connection connection = DriverManager.getConnection(URL)) {
                        	try (
                                    final PreparedStatement statement1 = connection.prepareStatement(QUERY_TEMPLATE_7_1)) {
                                    // Populate the query template with the data collected from the user
                                	//System.out.print(cname);
                                    statement1.setInt(1, datejobCompleted);
                                    statement1.setInt(2, laborTime);
                                    statement1.setInt(3, type);
                                    statement1.setInt(4, jobnum);
                                    System.out.println("Dispatching the query 7(1)...");
                                    // Actually execute the populated query
                                    final int rows_inserted = statement1.executeUpdate();
                                    System.out.println(String.format("Done. %d rows inserted in Job table..", rows_inserted));
                                    
                                }
                        	
                        	try (
                                    final PreparedStatement statement3 = connection.prepareStatement(QUERY_TEMPLATE_7_2)) {
                                    // Populate the query template with the data collected from the user
                                	//System.out.print(cname);
                                    statement3.setString(1, machinetype);
                                    statement3.setString(2, material);
                                    statement3.setInt(3, jobnum);
                                    statement3.setInt(4, time);
                                    statement3.setInt(5, laborTime);
                                    System.out.println("Dispatching the query 6(3)...");
                                    // Actually execute the populated query
                                    final int rows_inserted = statement3.executeUpdate();
                                    System.out.println(String.format("Done. %d rows inserted in cutjob table..", rows_inserted));
                                    
                                }
                        }
                    }
                    else if (type == 2)
                    {
                    	System.out.println("Please enter the color:");
                    	final String color = sc.nextLine();
                    	
                    	System.out.println("Please enter the volume of paint :");
                    	final int volume = sc.nextInt();
                    	sc.nextLine();
                    	
                    	System.out.println("Please enter the labor time in hours:");
                    	final int laborTime = sc.nextInt();
                    	sc.nextLine();
                    	
                    	System.out.println("Connecting to the database...");
                        // Get a database connection and prepare a query statement
                    	 try (final Connection connection = DriverManager.getConnection(URL)) {
                         	try (
                                     final PreparedStatement statement1 = connection.prepareStatement(QUERY_TEMPLATE_7_1)) {
                                     // Populate the query template with the data collected from the user
                                 	//System.out.print(cname);
		                         		statement1.setInt(1, datejobCompleted);
		                                statement1.setInt(2, laborTime);
		                                statement1.setInt(3, type);
		                                statement1.setInt(4, jobnum);
                                     System.out.println("Dispatching the query 7(1)...");
                                     // Actually execute the populated query
                                     final int rows_inserted = statement1.executeUpdate();
                                     System.out.println(String.format("Done. %d rows inserted in Job table..", rows_inserted));
                                     
                                 }
                         	
                         	try (
                                     final PreparedStatement statement3 = connection.prepareStatement(QUERY_TEMPLATE_7_3)) {
                                     // Populate the query template with the data collected from the user
                                 	//System.out.print(cname);
                                     statement3.setString(1, color);
                                     statement3.setInt(2, volume);
                                     statement3.setInt(3, jobnum);
                                     statement3.setInt(4, laborTime);
                                     
                                     System.out.println("Dispatching the query 7(3)...");
                                     // Actually execute the populated query
                                     final int rows_inserted = statement3.executeUpdate();
                                     System.out.println(String.format("Done. %d rows inserted in painjob table..", rows_inserted));
                                     
                                 }
                         }
                    }
                    else
                    {
                    	System.out.println("Please enter the labor time in hours:");
                    	final int laborTime = sc.nextInt();
                    	sc.nextLine();
                    	System.out.println("Connecting to the database...");
                        // Get a database connection and prepare a query statement
                    	 try (final Connection connection = DriverManager.getConnection(URL)) {
                         	try (
                                     final PreparedStatement statement1 = connection.prepareStatement(QUERY_TEMPLATE_7_1)) {
                                     // Populate the query template with the data collected from the user
                                 	
	                         		statement1.setInt(1, datejobCompleted);
	                                statement1.setInt(2, laborTime);
	                                statement1.setInt(3, type);
	                                statement1.setInt(4, jobnum);
                                     System.out.println("Dispatching the query 7(1)...");
                                     // Actually execute the populated query
                                     final int rows_inserted = statement1.executeUpdate();
                                     System.out.println(String.format("Done. %d rows inserted in Job table..", rows_inserted));
                                     
                                 }
                         	
                         	try (
                                     final PreparedStatement statement3 = connection.prepareStatement(QUERY_TEMPLATE_7_4)) {
                                     // Populate the query template with the data collected from the user
                                 	//System.out.print(cname);
                                     
                                     statement3.setInt(1, jobnum);
                                     statement3.setInt(2, laborTime);
                                     
                                     System.out.println("Dispatching the query 7(4)...");
                                     // Actually execute the populated query
                                     final int rows_inserted = statement3.executeUpdate();
                                     System.out.println(String.format("Done. %d rows inserted in fitjob table..", rows_inserted));
                                     
                                 }
                         }
                    }
                	break;
                case "11":
                	System.out.println("Please enter the assembly ID which has passed so far :");
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
                	System.out.println("Please enter the date that job completed:");
                	final int jobcomp = sc.nextInt();
                	sc.nextLine();
                	System.out.println("Please enter the Department for the job:");
                	final int depjob = sc.nextInt();
                	sc.nextLine();
                	System.out.println("Connecting to the database...");
                	try (final Connection connection = DriverManager.getConnection(URL)) {
                		try (final PreparedStatement statement1 = connection.prepareStatement(QUERY_TEMPLATE_12_1)) {
                			statement1.setInt(1, depjob);
                			System.out.println("Dispatching the query 12(1)...");
                			ResultSet rs1 = statement1.executeQuery();
                			while(rs1.next()) {
                				int processidd = rs1.getInt(1);
                				try (final PreparedStatement statement2 = connection.prepareStatement(QUERY_TEMPLATE_12_2)) {
                					statement2.setInt(1, processidd);
                					System.out.println("Dispatching the query 12(2)");
                					ResultSet rs2 = statement2.executeQuery();
                					while (rs2.next()) {
                						int jobid = rs2.getInt(1);
                						int assemblyiID = rs2.getInt(2);
                						try (final PreparedStatement statement3 = connection.prepareStatement(QUERY_TEMPLATE_12_3)) {
                							statement3.setInt(1, jobid);
                							statement3.setInt(2, jobcomp);
                							System.out.println("Dispatching query 12(3)");
                							ResultSet rs3 = statement3.executeQuery();
                							while(rs3.next()) {
                								int Type1 = rs3.getInt(1);
                								if(Type1 == 1) {
                									System.out.println(jobid + "\t" + assemblyiID + "\t" + "Cut Job");
                								}
                								else if(Type1 == 2) {
                									System.out.println(jobid + "\t" + assemblyiID + "\t" + "Paint Job");
                								}
                								else
                									System.out.println(jobid + "\t" + assemblyiID + "\t" + "Fit Job");
                							}
                						}
                					}
                				}
                			}
                		}
                               
                	}
                	break;
                case "10":
                	System.out.println("Please enter the Department number that you want find the labor cost:");
                	final int department = sc.nextInt();
                	sc.nextLine();
                	
                	System.out.println("Please enter the date of completion for the jobs:");
                	final int comdate = sc.nextInt();
                	sc.nextLine();
                	// Determining the Processes supervised by the given Department:
                	try (final Connection connection = DriverManager.getConnection(URL)) {
                		try (
                                final PreparedStatement statement1 = connection.prepareStatement(QUERY_TEMPLATE_10_1)) {
                                // Populate the query template with the data collected from the user
                            	//System.out.print(cname);
                                statement1.setInt(1, department);
                                System.out.println("Dispatching the query 1...");
                                // Actually execute the populated query
                                ResultSet rs = statement1.executeQuery();
                                int psize = 0;
                                int jsize = 0;
                                int[] iarr = new int[77];
                                int[] jjj = new int[77];
                                int pindex = 0;
                                int jindex = 0;
                                while (rs.next()) {
                                	int prcid = rs.getInt("processId");
                                	iarr[pindex] = prcid;
                                	pindex++;
                                	psize++;
                                }
                                for (int i = 0; i < psize; i++)
                                {
                                	System.out.println(iarr[i]);
                                	try (
                                            final PreparedStatement statement2 = connection.prepareStatement(QUERY_TEMPLATE_10_2)) {
	                                		statement2.setInt(1, iarr[i]);
	                                        System.out.println("Dispatching the query 10(2)...");
	                                        ResultSet rs1 = statement2.executeQuery();
	                                        int jobN = 0;
	                                        while(rs1.next()) {
	                                        	int jobno = rs1.getInt("jobnomber");
	                                        	jjj[jindex] = jobno;
	                                        	jindex++;	                                        
	                                        }	                                        
                                	}                                	
                                }
                                int[] rrr = new int[jindex];
                                int[] trtr = new int[jindex];
                                for(int k = 0; k < jindex; k++)
                                {
                                	System.out.println(jjj[k]);
                                	try (
                                            final PreparedStatement statement4 = connection.prepareStatement(QUERY_TEMPLATE_10_3)) {
	                                		statement4.setInt(1, jjj[k]);
	                                        System.out.println("Dispatching the query 10(c)...");
	                                        ResultSet rs2 = statement4.executeQuery();
	                                        
	                                        while(rs2.next()) {
	                                        	trtr[k] = rs2.getInt("labortime");
	                                        	rrr[k] = rs2.getInt("dateofjobcompleted");
	                                        }     
                                	}
                                }
                                int totalLaborTime = 0;
                                for (int l = 0; l < jindex; l++) {
                                	if(rrr[l] == comdate)
                                		totalLaborTime += trtr[l];
                                }
                                System.out.println("The total labor time for " + department + " departmen for jobs completed during the " + 
                                		comdate +  " date is: " + totalLaborTime + " hours.");
                              
                            }
                	}
                	break;
                case "13":
                	System.out.println("enter min for category:");
                	final int minimum = sc.nextInt();
                	sc.nextLine();
                	System.out.println("enter max for category::");
                	final int maximum = sc.nextInt();
                	sc.nextLine();
                    System.out.println("Connecting to the database...");
                    // Get the database connection, create statement and execute it right away, as no user input need be collected
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        System.out.println("Dispatching the query...");
                        try (
                        		final PreparedStatement statement2 = connection.prepareStatement(QUERY_TEMPLATE_13)) {
                        		
                        		statement2.setInt(1, minimum);
                        		statement2.setInt(2, maximum);
                        		System.out.println("Dispatching the query 13...");
                        		ResultSet rs1 = statement2.executeQuery();
                                System.out.println("Contents of the Student table:");
                                System.out.println("name | address | Category");

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
                case "14":
                	System.out.println("minimum for job number:");
                	final int minijobnum = sc.nextInt();
                	sc.nextLine();
                	System.out.println("maximum for job number:");
                	final int maxjobnum = sc.nextInt();
                	sc.nextLine();
                    System.out.println("Connecting to the database...");
                    // Get the database connection, create statement and execute it right away, as no user input need be collected
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                    	try (
                                final PreparedStatement statement1 = connection.prepareStatement(QUERY_TEMPLATE_14_1)) {
                                // Populate the query template with the data collected from the user
                            	
                    			statement1.setInt(1, minijobnum);
                    			statement1.setInt(2, maxjobnum);
                                System.out.println("Dispatching the query 14(1)...");
                                // Actually execute the populated query
                                final int rows_inserted = statement1.executeUpdate();
                                System.out.println(String.format("Done. %d rows deleted in cutjob table..", rows_inserted));
                            }
                    	
                    	try (
                                final PreparedStatement statement2 = connection.prepareStatement(QUERY_TEMPLATE_14_2)) {
                                // Populate the query template with the data collected from the user
                            	
                                statement2.setInt(1, minijobnum);
                                statement2.setInt(2, maxjobnum);
                                System.out.println("Dispatching the query 14(2)...");
                                // Actually execute the populated query
                                final int rows_inserted = statement2.executeUpdate();
                                System.out.println(String.format("Done. %d rows deleted in assign table..", rows_inserted));
                            }
                    	try (
                                final PreparedStatement statement3 = connection.prepareStatement(QUERY_TEMPLATE_14_3)) {
                                // Populate the query template with the data collected from the user
                            	
                                statement3.setInt(1, minijobnum);
                                statement3.setInt(2, maxjobnum);
                                System.out.println("Dispatching the query 14(3)...");
                                // Actually execute the populated query
                                final int rows_inserted = statement3.executeUpdate();
                                System.out.println(String.format("Done. %d rows deleted in job table..", rows_inserted));
                            }
                    }
                	  break;
                case "15":
                	System.out.println("enter job number that you want change color:");
                	final int jobnumm = sc.nextInt();
                	sc.nextLine();
                	System.out.println("enter new color:");
                	final String color = sc.nextLine();
                	System.out.println("Connecting to the database...");
                    // Get the database connection, create statement and execute it right away, as no user input need be collected
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                    	try (
                                final PreparedStatement statement1 = connection.prepareStatement(QUERY_TEMPLATE_15)) {
                                // Populate the query template with the data collected from the user                            
                    			statement1.setString(1, color);
                    			statement1.setInt(2, jobnumm);
                                System.out.println("Dispatching the query 15...");
                                // Actually execute the populated query
                                final int rows_inserted = statement1.executeUpdate();
                                System.out.println(String.format("Done. %d rows updated in painjob table..", rows_inserted));
                            }
                    }
                    break;
                case "16":
                	System.out.println("Where is path of file you want to enter?:");
                	sc.nextLine();
                	final String file = sc.nextLine();
                	BufferedReader br; 
                	String line = "";  
                	String split = ",";
				try {
					br = new BufferedReader(new FileReader(file));
					try (final Connection connection = DriverManager.getConnection(URL)) {
						while ((line = br.readLine()) != null)   
						{  
							String[] customer = line.split(split);      
							
							try (
                                    final PreparedStatement statement1 = connection.prepareStatement(QUERY_TEMPLATE_1)) {
                                    // Populate the query template with the data collected from the user                                	
                                    statement1.setString(1, customer[0]);
                                    statement1.setString(2, customer[1]);
                                    statement1.setInt(3, Integer.parseInt(customer[2]));
                                    
                                    System.out.println("Dispatching the query 1...");
                                    
                                    final int rows_inserted = statement1.executeUpdate();
                                    System.out.println(String.format("Done. %d rows inserted in customer table..", rows_inserted));
                                    
                                }
						}
						
					br.close();
					}
				} catch (FileNotFoundException e)
				{
					
					System.out.println("not found!");
					e.printStackTrace();
					break;
				} catch (IOException e) {
					System.out.println("eroooooor");
					e.printStackTrace();
					break;
				}
				break;
                case "17":
                	System.out.println("enter min for category:");
                	final int mincategory = sc.nextInt();
                	sc.nextLine();
                	System.out.println("enter max for category:");
                	final int maxcategory = sc.nextInt();
                	sc.nextLine();
                	System.out.println("enter output file name");
                	final String fileName = sc.nextLine();
                    System.out.println("Connecting to the database...");               
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        System.out.println("Dispatching the query...");
                        try (
                        		final PreparedStatement statement2 = connection.prepareStatement(QUERY_TEMPLATE_13)) {
                        		
                        		statement2.setInt(1, mincategory);
                        		statement2.setInt(2, maxcategory);
                        		System.out.println("Dispatching the query 13...");
                        		ResultSet tre = statement2.executeQuery();
                                
                        		try (PrintWriter rrr = new PrintWriter(fileName + ".csv")) {
                                while (tre.next()) {
                                	StringBuilder sub = new StringBuilder();
                                        String name = tre.getString("cname");
                                        sub.append(name);
                                        sub.append(',');
                                        String address = tre.getString("caddress");
                                        sub.append(address);
                                        sub.append(',');
                                        int cat = tre.getInt("category");
                                        sub.append(cat);
                                        sub.append('\n');
                                        rrr.write(sub.toString());                                      
                                }
                        		}catch (FileNotFoundException e) 
                        		
                        		{
                        		      System.out.println(e.getMessage());
                        	    }
                        }
                        
                    }
                    break;
                case "18": 
                    System.out.println("Exiting! bye!");
                    System.exit(0);
                default: 
                    System.out.println(String.format(
                        "Unrecognized option: %s\n" + 
                        "Please try again!", 
                        option));
                    break;
                	  
            }
        }
        sc.close(); 
    }
}
