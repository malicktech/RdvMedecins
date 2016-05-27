# RdvMedecins
Application de prise de rendez-vous pour un cabinet medical.

## Learning project
Projet d'étude réalisé dans le cadre de mon cursus à l'UTBM
Que je continue de faire évoluer pour tester des techno 

### TODO 
improve it like the zocdoc.com app

### Architecture
Client / serveur -> maven multi-mod  :

* rdvmedecins-back-core					(DAO & Business Layer | Spring Data JPA & Hibernate & Spring Security) Domain access module
* rdvmedecins-back-ws					(API REST Services | Spring MVC)
* rdvmedecins-back-admin-springmvcthymeleaf	(gui web | Spring MVC & thymeleaf & jquery & Twitter Bootstrap)
* rdvmedecins-front-jsf2				(gui web | JSF 2 + primeface 5)
* rdvmedecins-front-javascript			(gui web | Javascript) 	
* rdvmedecins-front-angularjs			(gui web | AngularJS 1 & yeoman & grunt & bower )
	
### Stack Technique

* [Spring Boot] [Spring Data] [Spring Security] [Spring MVC];
* [Spring Test] [JUnit 4] [assert j] : TU
* [Mysql] - Pour le SGBD
* [Angular ou JSF] - pour le front
* [HikariCP] Pool de connection JDBC
* [EHCache] JSR-107 (JCache) provider, used for hibernate second level cache
* [Hibernate Envers] entity auditing, Audit et Suivi de version des entités / tracer les modifications sur les objets métiers
* [liquibase] database schema versioning and upgrades 
* [dropwizard.metrics] Metrics
* [graphite] metric's stockage 
* [Actuator] Monitor Health & Audit 
* [Elastic Search] search engine
* [snakeyml] configuration expressed in YAML syntax (support is specific to Spring Boot) format.
* [Spring DevTools] set of tools to improve productivity : provide additional development-time features : livereload and Automatic restart

### Outils utilisés

* Plateform : JDK 1.8
* IDE : Eclipse Mars (+ Spring Tools Suite)
* AS : Tomcat 7
* SCM : Git
* Build : Maven 3
* SGBD : WampServer (MySQL 5) - Workbench

### Features

- l'utilisateur s'inscrit
- l'utilisateur se connecte
- il peut voir l'agenda du médecin choisi pour le jour choisi ;
- une fois obtenu l'agenda du médecin, on peut réserver un créneau
- Une fois le rendez-vous validé, il est ramené automatiquement à l'agenda où le nouveau rendez-vous est désormais inscrit. 
- Ce rendez-vous pourra être ultérieurement supprimé

- internationalization i18n  : FR & EN



	