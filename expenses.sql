USE expenses;

CREATE TABLE users(
	id	INTEGER NOT NULL AUTO_INCREMENT,
	PRIMARY KEY(id)
);

CREATE TABLE expenses(
	userId		INTEGER NOT NULL, 
	dateOfExpense 	DATE NOT NULL,
	value		DECIMAL(8, 2) NOT NULL,
	reason 		VARCHAR(256) DEFAULT '' NOT NULL,
	FOREIGN KEY (userId) REFERENCES users(id)
);
