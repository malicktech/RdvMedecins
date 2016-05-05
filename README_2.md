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

Template Engine : thymeleaf

## TODO : 

thymeleaf eclipse plugin 
http://www.thymeleaf.org/doc/tutorials/2.1/usingthymeleaf.html
thymeleaf test

One Delete Confirm Dialog For All

possible to read from messages.properties: var msg = [[#{msg}]]


mettre msg : 
En poursuivant votre navigation, vous acceptez l'utilisation de cookies afin de réaliser des statistiques d'audiences et vous proposer des services ou des offres adaptés à vos centres d'intérêts

This website uses cookies so that we can provide you the best user experience possible.
By continuing to browse the site you are agreeing to our use of cookies


add Suspend/restore 
http://heera.it/bootstrap-3-delete-confirm-dialog

https://www.google.fr/search?q=spring+message+properties+javascript&oq=spring+message+properties+in+js+&aqs=chrome.1.69i57j0.13021j0j1&sourceid=chrome&ie=UTF-8

http://formvalidation.io/

* faire module admin back thymeleaf : acce direct service business core + front thymeleaf  accee rest templet a l'api rest
* Add send email feature tovalid insciption or reset password
* regrouper  client soncole et thymeleaf
* pass element from table to modal : http://stackoverflow.com/questions/22833490/how-to-pass-spring-model-attributes-as-parameters-in-thymeleaf-fragments
* chek validation side in detail

*  bootstrap ; virer lescomposant bootstrap externe et use les natif inclu dans bootstrap : modal, select etc ...
* vier lavue jubrotton
* rename client to "patient"
* popup de confirmation pour les suppressions
** ORDRE dans l'affichage DES ERREURs FORMULARIE
** centraliser tous les lien url path dans un fichier enum, y accéder depuis les controlleur et les vue thymeleaf
** factorise form register client and medecins, form edit ad register
** datatables i18n
** alert save , edit, update : factorise in one html file : 
** use html5 and valider le format html avec : validator W3C
* change creanau <-> medecins to one to one | delete id externe
* add table and form with responsible 
* add java 8 : optionnal, stream , etc
* add status on rdv : reserved, absent, annulé, réalisé | boolean : active or not, | 
* extraire la page login ans une page d'authenticatin à part avec desing perso
** netoyer les page unitile
* rename field : firstname, lastname
* form add, virer tableau , use real form
* use partie javascript validation in admin
* Respect de la norme pour les api rest
* adapter le chemin mapping pour les controlleurs pour les 
* use popin to add and edit object medecins and autres
* changer le wording des méthode de service : use creteObjets, finAllObject, ajouter eager and easy ex: fingonewitheagerrelationship, npire to jhipter

* manage jsonignore and jsonfilter
* transform list to set
* replace jquery.validate.unobtrusive.js by jquery validate native
* qhow client validation i18n
* change repository to jpa repo
** filterjson p403 p402, se passer des mapper,utiliser  annottion json inspired from ebank youssfi,  
* adopter REST HATEOAS   | https://zestedesavoir.com/tutoriels/299/la-theorie-rest-restful-et-hateoas/
* add : js (jquery , ajax) et bootstrapts à la page web
* replace la partie console par test junit
** Spring MVC
		layout with thymeleaf : http://www.thymeleaf.org/doc/articles/layouts.html
		redirection
		ajax, js
* CSRF
* mettre la partie angular dans un dossier app ,à part, dans le dossier maven 
* ou dans  : src/main/resources/static
* i18n, internationalisation
** add spring mvc example in spring demo.
** deploy Heroku or Amazon Elastic Bean Stalk
** static helpers : check if it's really need element
** cors filter : http://sqli.developpez.com/tutoriels/spring/construire-backend-springboot/#LIV
** add elastic search
** gestion des transaction spring data
** set errorpage with atuator or 
** minifier css and js
** manage date time, check jhipster
** factorise html en layout au max, use replace et include
** Conf : environnement DEV, STG, PROD
** add liquibase | http://blog.soat.fr/2015/10/liquibase-et-le-versioning-de-base-de-donnees/
** test api rest | http://blog.soat.fr/2015/12/tester-une-api-rest-spring-mvc-avec-le-spring-testcontext-framework/
** add lets' Enrypt https
** s'inpirer de  jhipster, ajouter tous les parties : actuator , etc ....
** gestion timout RestTemplate
** gestion des cookie
** spring MVC : interceptor
** ajputer "Les cookies nous permettent de fournir, protéger et améliorer les services de Facebook. En continuant à utiliser notre site, vous acceptez notre Politique d’utilisation des cookies."
** voir quelle est la bonnepratique pour les sessions spring security , utiliser ou non ? p492 
** gestion du sessions du user : partir de lexemplle et s'inspirer de mcdo
** gestion du cache, avec hazel cache
** gestion pool de connexion : HikariCP
** gestion des exclusion spring boot , to only keep needed depndency
** gestion des messages d'erreur , code et autres ... personnalisation, 
** gestion page erreur et exception avec spring ; OptimisticLockException; 
** gestion des log
** add envoi de mail :  http://www.thymeleaf.org/doc/articles/springmail.html   & Apache Velocity Template & javaMail http://sivalabs.in/2011/05/sending-email-with-attachments-using-javamail/
** vérifier la validité des données avec Hibernate validator et javax validator
* validation form  coté client js
** improve database : table personne , medecins, ..... add index, ...
** mettre un formulaire d'inscription patient
** choix du medecins favoris / MEDECINS TRAINTANTS
** présentation des medecins
** java doc est présente
** use set in place of list or collection
** manage - cascade type  - and fetchtype
** inplément auditing with spring dat jpa; inspire from jhipster
** use enum in entity field
** add ABSENCE DU MEDECIN
** code barre
** echange de mal entre medein et patient
** monitoring with actuator | https://dzone.com/articles/disable-spring-boot-production
** api rest : expose hypermedia link | Hypermedia-Driven REST services
** add spring loader pour le rechargement à chaud
http://javarevisited.blogspot.fr/2015/01/why-override-equals-hashcode-or-tostring-java.html
http://www.infoq.com/fr/articles/retour-sur-les-bases-equals-et-hashcode
voir dans les bouquins : jaa efficace,et...

** add " equals, hashcode and tostring " to all class object : quand 2 rv sont égaux, quand deux creneaux sont egaux ? etc
générer hascode et equals depuis l'ide depuis l'IDE
test rv même creneau , même medecin, même client
define this in abstract entity

** créationd'une module : reatJS

## area

internationalisation(i18n) de jQuery : globalise
Validation client side : Jquery.Validation plugin
Validation server side : Bean Validation (JSR 303) + Hibernate Validator, ResourceBundle pour chaque langue  
POST/Redirect/GET pattern with Flash Attributes 
thymeleaf layout

## LINK

<p align="center">
  <a href="https://angularclass.com" target="_blank">
    <img src="https://cloud.githubusercontent.com/assets/1016365/9863762/a84fed4a-5af7-11e5-9dde-d5da01e797e7.png" alt="Webpack and Angular 2" width="500" height="320"/>
  </a>
</p>

http://www.jmdoudoux.fr/java/dej/chap-validation_donnees.htm



##
Column(name = "PRICE", precision = 9, scale = 2)
  private BigDecimal price;


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



[schemas workbench]

