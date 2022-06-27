<h1 align="center">🏊🏻 <b> Swimming Club Control System </b> 🏊🏻 

</br>

<h1 align="center">
    <img alt="SCCS Logo" src="https://github.com/sandrosmarzaro/swimming-club-control-system/blob/main/src/com/sccs/view/images/logo.png" />
</h1>

<div align="left"> 

![GitHub search hit counter](https://img.shields.io/github/search/sandrosmarzaro/swimming-club-control-system/count?label=views)
![GitHub language count](https://img.shields.io/github/languages/count/sandrosmarzaro/swimming-club-control-system)
![GitHub repo size](https://img.shields.io/github/repo-size/sandrosmarzaro/swimming-club-control-system?label=size)
![GitHub issues](https://img.shields.io/github/issues/sandrosmarzaro/swimming-club-control-system?color=blue)
![GitHub pull requests](https://img.shields.io/github/issues-pr/sandrosmarzaro/swimming-club-control-system?color=blue)
![GitHub](https://img.shields.io/github/license/sandrosmarzaro/swimming-club-control-system?color=blue)

![GitHub all releases](https://img.shields.io/github/downloads/sandrosmarzaro/swimming-club-control-system/total?color=blue)
![GitHub release (latest by date)](https://img.shields.io/github/v/release/sandrosmarzaro/swimming-club-control-system?color=blue)
![GitHub tag (latest by date)](https://img.shields.io/github/v/tag/sandrosmarzaro/swimming-club-control-system?color=blue&logoColor=blue)

![GitHub Sponsors](https://img.shields.io/github/sponsors/sandrosmarzaro)
![GitHub contributors](https://img.shields.io/github/contributors/sandrosmarzaro/swimming-club-control-system?color=blue)
![GitHub commit activity](https://img.shields.io/github/commit-activity/y/sandrosmarzaro/swimming-club-control-system?color=blue&logoColor=blue)
![GitHub last commit](https://img.shields.io/github/last-commit/sandrosmarzaro/swimming-club-control-system?color=blue)
</div>
</h1>

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

* [Java](https://www.java.com/pt-BR/) as the main language with [Lombok](https://projectlombok.org) to to assist in code generation

* [PostgreSQL](https://www.postgresql.org) with [JDBC](https://docs.oracle.com/javase/8/docs/technotes/guides/jdbc/) for data persistence

* [JavaFX](https://openjfx.io) for graphical interface with [Jfoenix](http://www.jfoenix.com) to manipulate more elaborate interface elements

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