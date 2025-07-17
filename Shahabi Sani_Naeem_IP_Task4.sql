CREATE TABLE process()
 processId   int,
 processData VARCHAR(32),

 PRIMARY key (processId)
);

CREATE TABLE fit (
fittype varchar(32),
processId int,
primary key (processId),
foreign KEY (processId) references process(processId)
);

CREATE TABLE paint (
painttype varchar(32),
paintingmethod varchar(32),
processId INT,
primary key (processId),
foreign KEY (processId) references process(processId)
);

CREATE TABLE cut (
cuttingtype varchar(32),
machinetype varchar(32),
processId INT,
primary key (processId),
foreign KEY (processId) references process(processId)
);

create table Job(
jobNomber int,
dateofjobcommenced int,
dateofjobcompleted int,
primary key(jobNomber)
);

create table fitjob(
jobNomber int,
labortime int,
primary key(jobNomber),
foreign key (jobNomber) references Job(jobNomber)
);


create table painjob(
jobNomber int,
labortime int,
color varchar(15),
amountoftime int,
primary key(jobNomber),
foreign key (jobNomber) references Job(jobNomber)
);

create table cutjob(
jobNomber int,
labortime int,
color varchar(15),
amountoftime int,
typeofmachine varchar(32),
primary key(jobNomber),
foreign key (jobNomber) references Job(jobNomber)
);

CREATE TABLE passthrugh(
    Assemblyid int,
    processId int,
    PRIMARY key(Assemblyid, processId),
    FOREIGN KEY (Assemblyid) REFERENCES Assembly(Assemblyid),
    FOREIGN KEY (processId) REFERENCES process(processId)
    );

CREATE TABLE account (
 accountnumber int,
 dateofaccount int,
 primary key (accountnumber)
 );

create TABLE departmentaccount(
    accountnumber INT,
    sup_cost REAL,
    PRIMARY KEY(accountnumber)
    FOREIGN KEY(accountnumber) REFERENCES account(accountnumber)
);

create TABLE assemblyaccount(
    accountnumber INT,
    sup_cost REAL,
    PRIMARY KEY(accountnumber),
    FOREIGN KEY(accountnumber) REFERENCES account(accountnumber)
    );
create TABLE processaccount(
    accountnumber INT,
    sup_cost REAL,
    PRIMARY KEY(accountnumber),
    FOREIGN KEY(accountnumber) REFERENCES account(accountnumber)
    );

CREATE TABLE Transaction1(
    transactionnumber ,
    sup_cost REAL,
    PRIMARY KEY(transactionnumber)
);
CREATE TABLE Assembly (
Assemblyid int,
address varchar(32),
dateofordered INT,
AssemblyDeatails varchar(80),
primary key(Assemblyid)
);

CREATE TABLE assign(
    Assemblyid int,
    processId int,
    jobnomber INT
    PRIMARY key(Assemblyid, processId, jobnomber),
    FOREIGN KEY (Assemblyid) REFERENCES Assembly(Assemblyid),
    FOREIGN KEY (processId) REFERENCES process(processId),
    FOREIGN KEY (jobnomber) REFERENCES Job(jobnomber)
    );

CREATE TABLE record(
    transactionnumber int,
    jobnomber INT
    PRIMARY key(transactionnumber, jobnomber)
    );

CREATE TABLE order1(
    Assemblyid int,
    cname int,
    PRIMARY key(Assemblyid, cname),
    FOREIGN KEY (cname) REFERENCES customer(cname),
    FOREIGN KEY (Assemblyid) REFERENCES Assembly(Assemblyid)
    );

CREATE TABLE department(
    departmentnumber int,
    departmentdata VARCHAR(70),
    PRIMARY KEY(departmentnumber,departmentdata),
);

CREATE TABLE supervise(
    processId int,
    departmentnumber int,
    departmentdata VARCHAR(32),
    PRIMARY key(processId, departmentnumber, departmentdata),
    FOREIGN KEY (processId) REFERENCES process(processId),
    FOREIGN KEY (departmentnumber) REFERENCES department(departmentnumber),
    
    );
create table record_dept_cost(
    account_num INT,
    dept_num INT,
    PRIMARY KEY(account_num, dept_num),
    FOREIGN KEY(account_num) REFERENCES departmentaccount(accountnumber),
    FOREIGN KEY(dept_num) REFERENCES department(departmentnumber),
);
create table record_assembly_cost(
    account_num INT,
    assembly_id INT,
    PRIMARY KEY(account_num, assembly_id),
    FOREIGN KEY(account_num) REFERENCES assemblyaccount(accountnumber),
    FOREIGN KEY(assembly_id) REFERENCES assembly(Assemblyid),
);
create table record_process_cost(
    account_num INT,
    process_id INT,
    PRIMARY KEY(account_num, process_id),
    FOREIGN KEY(account_num) REFERENCES processaccount(accountnumber),
    FOREIGN KEY(process_id) REFERENCES process(processId),
);
create table record_cost(
    account_num INT,
    process_id INT,
    PRIMARY KEY(account_num, process_id),
    FOREIGN KEY(account_num) REFERENCES account(accountnumber),
    FOREIGN KEY(process_id) REFERENCES process(processId)
);


CREATE TABLE customer(
    cname int,
    caddress VARCHAR(32),
    category INT,
    PRIMARY KEY(cname),
    CONSTRAINT CHK_category CHECK (category IN ('1','2','3','4','5','6','7','8','9','10'))
);

CREATE TABLE update1(
    transactionnumber INT,
    FOREIGN KEY (transactionnumber) REFERENCES transaction1(transactionnumber),
    FOREIGN KEY (accountnumber) REFERENCES account(accountnumber)
    );
