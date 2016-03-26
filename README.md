# Electoral systems Application for Proportional representation

Application de prise de rendez-vous pour un cabinet medical avec plusieurs medecins. 

# Architecture

Client / serveur -> 4 Modules  :

	* rdvmedecins-api-core				(DAO, métier / Spring Data JPA Hibernate , Spring Securité)
	* rdvmedecins-api-ws					(REST web service / Spring MVC)
	* rdvmedecins-api-web-springmvc		(ui web / Spring MVC & thymeleaf)
	* rdvmedecins-api-web-jsf			(ui web / JSF 2 et primeface)
	
	* rdvmedecins-api-web-angularjs		(ui web / AngularJS 1)
		(projet angular à part , généré avec yeoman, importer dans un projet maven web)
	
# Stack Technique

* Plateform : JDK 1.8
* Object Lifecycle & IOC : Spring 4
* Test : JUnit 4
* DAO : Spring Data JPA / Hibernate
* Web Service : Spring MVC
* Security	: Spring Security
* UI : Spring MVC (web) / Angular JS / JSF 2

# Outils utilisés

* IDE : Eclipse Mars (+ Spring Tools Suite)
* Application Server : Tomcat 7
* SCM : Git
* Build : Maven 3
* SGBD : WampServer (MySQL 5) - Workbench

# Link

Tutoriel :
	
	* http://tahe.developpez.com/java/spring/serge-tahe-spring-mvc-rest-security-cors-angularjs-tutoriel-debutant/
		

# accée :
api				:  localhost:8080
ui-client-cors	:  localhost:8081/client.html
	