CREATE TABLE assign(
    Assemblyid int,
    processId int,
    jobnomber INT
    PRIMARY key(Assemblyid, processId, jobnomber),
    FOREIGN KEY (Assemblyid) REFERENCES Assembly(Assemblyid),
    FOREIGN KEY (processId) REFERENCES process(processId),
    FOREIGN KEY (jobnomber) REFERENCES Job(jobnomber)
    );