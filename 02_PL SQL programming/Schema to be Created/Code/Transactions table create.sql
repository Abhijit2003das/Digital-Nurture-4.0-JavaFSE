CREATE TABLE TRANSACTIONS (
    TRANSACTIONID   NUMBER PRIMARY KEY,
    ACCOUNTID       NUMBER,
    TRANSACTIONDATE DATE,
    AMOUNT          NUMBER,
    TRANSACTIONTYPE VARCHAR2(10),
    FOREIGN KEY ( ACCOUNTID )
        REFERENCES ACCOUNTS ( ACCOUNTID )
);