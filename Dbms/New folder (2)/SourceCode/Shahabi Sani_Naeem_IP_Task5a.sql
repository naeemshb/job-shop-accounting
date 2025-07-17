Attention: I talked to the TA  (Taras Basiuk) and he said that showing the implementation of the queries in the
 Java program is enough for this exercise. And it does not need to be implemented in azure SQL. 

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