# RdvMedecins
## UTBM Learning project

Projet d'étude dans le cadre de mon cursus à l'UTBM
Application de prise de rendez-vous pour un cabinet medical. 


### Architecture

Client / serveur -> maven multi-mod  :

* rdvmedecins-back-core					(DAO & Business Layer | Spring Data JPA & Hibernate & Spring Security)
* rdvmedecins-back-ws					(API REST Services | Spring MVC)
* rdvmedecins-back-admin-springmvcthymeleaf	(gui web | Spring MVC & thymeleaf & jquery & Twitter Bootstrap)
* rdvmedecins-front-jsf2				(gui web | JSF 2 + primeface 5)
* rdvmedecins-front-javascript			(gui web | Javascript) 	
* rdvmedecins-front-angularjs			(gui web | AngularJS 1 & yeoman & grunt & bower )
	
### Stack Technique

* [Spring Boot] [Spring Data] [Spring Security] [Spring MVC];
* [Actuator]
* [JUnit 4]
*  
* [Mysql] - Pour le SGBD
* [Angular ou JSF] - pour le front


### Outils utilisés

* Plateform : JDK 1.8
* IDE : Eclipse Mars (+ Spring Tools Suite)
* AS : Tomcat 7
* SCM : Git
* Build : Maven 3
* SGBD : WampServer (MySQL 5) - Workbench

### Features

- l'utilisateur s'inscrit
- l'utilisateur se connecte et peut ensuite choisir le médecin avec lequel il veut un rendez-vous et le jour de celui-ci 
- il peut voir l'agenda du médecin choisi pour le jour choisi ;
- une fois obtenu l'agenda du médecin, on peut réserver un créneau
- Une fois le rendez-vous validé, il est ramené automatiquement à l'agenda où le nouveau rendez-vous est désormais inscrit. 
- Ce rendez-vous pourra être ultérieurement supprimé

i18n  : FR & EN

	