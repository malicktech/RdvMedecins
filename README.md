# RdvMedecins

## Learning project
Projet d'étude dans le cadre de mon cursus à l'UTBM
Application de prise de rendez-vous pour un cabinet medical. 


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
* [Actuator]
* [JUnit 4] et assert j : TU
*  
* [Mysql] - Pour le SGBD
* [Angular ou JSF] - pour le front


[HikariCP] Pool de connection JDBC

[Hibernate Envers] Audit et Suivi de version des entités / tracer les modifications sur les objets métiers

[liquibase] database schema versioning and upgrades 

[EHCache] JSR-107 (JCache) provider, used for hibernate second level cache


[dropwizard.metrics] Metrics
[graphite] metric's stockage 

[Actuator] Monitor Health & Audit 

[Hibernate envers] entity auditing

[Elastic Search] search engine

[snakeyml] configuration expressed in YAML syntax, in a hierarchical format. YAML support is specific to Spring Boot

[Spring DevTools] set of tools to improve productivity : provide additional development-time features : livereload and Automatic restart

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

- internationalization i18n  : FR & EN

###  Database - data modele

Les rendez-vous sont gérés par les tables suivantes :
• [medecins] : contient la liste des médecins du cabinet ;
• [clients] : contient la liste des patienst du cabinet ;
• [creneaux] : contient les créneaux horaires de chacun des médecins ;
• [rv] : contient la liste des rendez-vous des médecins.
• Les tables [roles], [users] et [users_roles] sont des tables liées à l'authentification. 

Les relations entre les tables gérant les rendez-vous sont les suivantes :

• un créneau horaire appartient à un médecin – un médecin a 0 ou plusieurs créneaux horaires : 1:p bidirectionellle
• un rendez-vous réunit à la fois un client et un médecin via un créneau horaire de ce dernier ;
• un client a 0 ou plusieurs rendez-vous : 1:p bidirectionellle
• à un créneau horaire est associé 0 ou plusieurs rendez-vous (à des jours différents)

 contrainte d'unicité sur les valeurs des colonnes jointes (JOUR, ID_CRENEAU) de la table rv, pour empêcher que que deux RV ont été pris au même moment pour le même médecin

[schemas workbench]

	