CREATE TABLE Assembly(
    Assemblyid int,
    address VARCHAR(32),
    dateofordered INT,
    AssemblyDeatails VARCHAR(80)
    PRIMARY key(Assemblyid)
    FOREIGN KEY (processId) REFERENCES process(processId)

);