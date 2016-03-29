
# TODO LIST
	
	* spring CONIG : move fro mxml to JAVCONFIG 
			JSF & Spring Securtity
			Use JSF 2 new features
			
	* prettyFace pour les url
	* richface
	
	* faire projet a part , nnamed : SpringBootPrimeFaces  
	
	* Implement les accées CORS inter domaine	
	* json conversion
don’t need to do this conversion manually. Because Jackson is on the classpath, Spring’s MappingJackson2HttpMessageConverter is automatically chosen to convert the Greeting instance to JSON. 
		

# module API : 
	* virer le mapping JOSN qui est fait automatiquement si on renvoi les entités
	* faile le mapping si on renvois un reponse autre


# L'algorithme : 
	* use map may be more easiest
	

# code 
	* variable, comment and method in english
	* change primitive type to wrapper
	* définir les niveau de log spring sur fihcier properties
	* use : classe exceptions, enum, ClasseTest

# les cas spécfiques a gérer : 


# module CORE
	* changre repository to jpareposirtory
	* 
	* configure with bean globalConfig in securityConfig.class
	* configure access to list medecins and agenda to all & restric access only for reservation
	* link user, role and client/Medecins tables
	* rename  rename transfert entites to ...dto



# Generale

get out table, use list

change package name : net.webapp.rdvmedecins.api.core

improve ui with simple bootstrat theme and angular ui

add logging : voir intro-spring-java

USE JAVA 8 / Stream sur les list / Sort /
delete guava

regrouper git ingore dans la racine

complete unit test

update spring boot version

pom : spring-boot-starter-tomcat	
	rename file of package models -> xxxxDto
	rename package controller -> service	
	rename controller to xxxRestService

add test module angular :
update js plugin : footable, Jtables etc ...

features :
	ajout bouton "add new user" + form to subscribe new client  + search client (implement elastic search)
	partie admin ou le medecins peut configurer ses créneaux
	build ionic app inspire from angulr ui
	add partie visiteur	
	build android app
	
	
# wording : 



