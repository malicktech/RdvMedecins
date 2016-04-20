## rdv medecins :


## module client-springmvc-thymeleaf



application.properties
==========================

# fixe le port de service de l'application web 
server.port=9000

# fixe le contexte de l'application web
server.context-path= /intro



link :
http://localhost:9000/intro/exemple-01.html


## archi 

Il comprend les chapitres suivants*:

Étude de cas (260 p)*: gestion de rendez-vous avec une architecture 3tier (client jS, serveur1, serveur2), chaque tier étant lui-même structuré en couches*:
le service web / jSON (serveur2) (Spring Data, Spring MVC, Spring security, CORS),
client programmé du service web / jSON (RestTemplate),
le serveur web / jSON (serveur1) Spring / Thymeleaf,
le client JS (jQuery),
création d'une application Android native*;


## TODO : 
* adopter REST HATEOAS   | https://zestedesavoir.com/tutoriels/299/la-theorie-rest-restful-et-hateoas/
* add : js (jquery , ajax) et bootstrapts à la page web
** Spring MVC
		layout with thymeleaf : http://www.thymeleaf.org/doc/articles/layouts.html
		redirection
		ajax, js

* mettre la partie angular dans un dossier app ,à part, dans le dossier maven 
* ou dans  : src/main/resources/static
* i18n, internationalisation
** add spring mvc example in spring demo.
** cors filter : http://sqli.developpez.com/tutoriels/spring/construire-backend-springboot/#LIV
** add elastic search
** add liquibase | http://blog.soat.fr/2015/10/liquibase-et-le-versioning-de-base-de-donnees/
** test api rest | http://blog.soat.fr/2015/12/tester-une-api-rest-spring-mvc-avec-le-spring-testcontext-framework/
** add letsrypt https
** s'inpirer de  jhipster, ajouter tous les parties : actuator , etc ....
** gestion des cookie
** gestion du sessions du user : partir de lex et s'inspirer de mcdo
** gestion du cache
** gestion pool de connexion : HikariCP
** gestion des exclusion spring boot , to only keep needed depndency
** gestion des messages d'erreur , code et autres ... personnalisation, 
** gestion page eerreur et exeption avec spring
** add envoi de mail :  http://www.thymeleaf.org/doc/articles/springmail.html   & Apache Velocity Template
** vérifier la validité des données avec Hibernate validator et javax validator
* validation form  coté client js
** improve database : table personne , medecins, ..... add index, ...
** mettre un formulaire d'inscription patient
** choix du medecins favoris / MEDECINS TRAINTANTS
** présentation des medecins

** créationd'une module : reatJS

## LINK


http://www.jmdoudoux.fr/java/dej/chap-validation_donnees.htm