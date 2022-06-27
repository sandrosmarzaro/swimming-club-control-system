# 🏊🏻 **Swimming Club Control System** 🏊🏻

## 📖 **Summary**
1. <a href="#-What is SCCS">What is SCCS?</a>
2. <a href="#-Technologies Used">Technologies Used</a>
3. <a href="#-How to Use">How to Use</a>

</br>

## 🤔 **What is SCCS?** <a id="-What is SCCS"></a>

</br>

* SCCS is a desktop application for enrollment for a swimming club or school, thus registering employees, teachers and students, in addition to the swimming pools of the establishment.

* This application was developed as a work in the discipline of Object Oriented Programming II in the course of the Bachelor of Information Systems at IFES Campus Cachoeiro

</br>

## 💻 **Technologies Used** <a id="-Technologies Used"></a>

</br>

The technologies used for the construction of the project were the following:

* [Java](https://www.java.com/pt-BR/) as the main language

* [PostgreSQL](https://www.postgresql.org) for data persistence

* [JavaFX](https://openjfx.io) for graphical interface

 * [Jfoenix](http://www.jfoenix.com) used to manipulate more elaborate interface elements

 * [Jasypt](http://www.jasypt.org) used to encrypt user passwords

 * [JasperReport](https://community.jaspersoft.com) for creating reports

 </br>

## 👩‍💻 **How to use?** <a id="-How to Use"></a>

</br>

1. Download project or cloning repository with git command: </br>
&nbsp; `git clone https://github.com/sandrosmarzaro/swimming-club-control-system.git`

2. Create PostgreSQL database with script *database.sql* in sub-directory `/src/com/sccs/script/` inside the project project, having the username and password as *"postgres"*

3. Compile project with Java 8 or superior, using the dependencies jars found in the `/lib/` sub directory

4. Enjoy the system using the default username and password as *"admin"*