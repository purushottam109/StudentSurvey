# StudentSurvey

A Student Survey web application that allows the user to fill in the survey form and store it in a remote database. And, allows to retrieve the survey data for any particular student form that database.

>The application uses JSF 2.0, Primefaces 5.1, Javascript at the front end providing very convenient and polished webpages.

>EJB3, JPA, JQuery at the back end and database

>REST and SOAP services also attached to the provide additional facilities

The application has:

a)Student Survey EJBs.
b)Two Entity beans (one for student data and the other for emergency contact info)
c)Implemented logical relationships between entities.
d)I have not used any SQL code or JDBC connection settings in the code. But have used JPQL for creating the search query.
e)Also the search page works fine and searches for records that starts with the given word.
f)I have also added the Delete functionality for the search page.


To Run:
download the A5EAR.ear file and deploy on your application server
* You will also need make necessary changes in the code for connection to the remote server and the database.