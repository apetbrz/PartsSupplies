-- Create Suppliers Table

CREATE TABLE suppliers (

  sid CHAR(10) PRIMARY KEY,

  sname CHAR(30) NOT NULL,

  phone CHAR(15) UNIQUE NOT NULL

);



-- Create Parts Table

CREATE TABLE parts (

  pid CHAR(10) PRIMARY KEY,

  pname CHAR(30) NOT NULL,

  manufacturer CHAR(30) NOT NULL

);



-- Create Catalog Table
-- NOTE FROM STUDENT: had to change NUMBER() to FLOAT() for MySQL

CREATE TABLE catalog (

  sid CHAR(10),

  pid CHAR(10),

  cost FLOAT(10,2) NOT NULL,

  PRIMARY KEY (sid, pid),

  CONSTRAINT fk_catalog_supplier FOREIGN KEY (sid) REFERENCES suppliers(sid) ON DELETE CASCADE,

  CONSTRAINT fk_catalog_part FOREIGN KEY (pid) REFERENCES parts(pid) ON DELETE CASCADE

);



-- Insert Suppliers

INSERT INTO suppliers (sid, sname, phone) VALUES

('S001','Tech Supplies Inc.','123-456-7890');

INSERT INTO suppliers (sid, sname, phone) VALUES

('S002','Auto Parts Ltd.','987-654-3210');

INSERT INTO suppliers (sid, sname, phone) VALUES

('S003','Global Machinery','555-123-4567');



-- Insert Parts

INSERT INTO parts (pid, pname, manufacturer) VALUES

('P100','Engine Oil Filter','Bosch');

INSERT INTO parts (pid, pname, manufacturer) VALUES

('P101','Brake Pads','Brembo');

INSERT INTO parts (pid, pname, manufacturer) VALUES

('P102','Spark Plug','NGK');

INSERT INTO parts (pid, pname, manufacturer) VALUES

('P103','Alternator','Denso');



-- Insert Catalog Entries (Supplier-Part-Cost)

INSERT INTO catalog (sid, pid, cost) VALUES

('S001','P100',12.50);

INSERT INTO catalog (sid, pid, cost) VALUES

('S001','P101',25.75);

INSERT INTO catalog (sid, pid, cost) VALUES

('S002','P100',11.99);

INSERT INTO catalog (sid, pid, cost) VALUES

('S002','P102',7.99);

INSERT INTO catalog (sid, pid, cost) VALUES

('S003','P103',149.99);

INSERT INTO catalog (sid, pid, cost) VALUES

('S003','P101',24.50);

INSERT INTO catalog (sid, pid, cost) VALUES

('S001','P103',155.00);



COMMIT;