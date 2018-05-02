Problem: 
	Item 1: Provide an interface for the user to submit expenses to a database. An expense includes a date, a value, and a reason.
	Item 2: Provide an interface for the user to view previously submitted expenses.

Solution:
	Developed a Java web app to be deployed on a tomcat server. The web app includes two pages: a form for the user to submit their expenses, and a page which requests submitted expenses from a MySQL server backend.

This solution focuses both on front-end and back-end, with emphasis on keeping both simple and cleanly separated.

I chose to build this project using Maven and Tomcat because I knew I could develop the web app very quickly. I used MySQL because Java has nice plugins for database interaction.

I would have liked to develop a more robust system for identifying users. Currently the web app has no way of knowing which user submitted which expense. This could have been solved by including a login page of some kind, and also keeping a table in the database which saves user info (id, name, password, etc.). 
