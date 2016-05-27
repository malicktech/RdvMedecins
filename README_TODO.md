## rdv medecins :

ZocDoc, the medical appointment-booking website

Zocdoc is an online medical care scheduling service, providing free of charge medical care search facility for end users by integrating information about medical practices and doctors' individual schedules in a central location.

The end user-searchable database includes specialties, range of services, office locations, photographs, personnel educational background and user-submitted reviews

appointment booking system

## module client-springmvc-thymeleaf


application.properties
==========================

# fixe le port de service de l'application web 
server.port=9000

# fixe le contexte de l'application web
server.context-path= /intro


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

DROPWIZARD ,?
http://blog.takipi.com/java-bootstrap-dropwizard-vs-spring-boot/

MapStruct ou Dozzer ?
[MapStruct] Java Bean Mappings
plus rapide que Dozzert
http://javaetmoi.com/2015/09/benchmark-frameworks-javas-mapping-objet/
http://blog.netapsys.fr/mapping-dozer-is-died-welcom-to-mapstruct/

configuration properties
http://blog.codeleak.pl/2014/09/using-configurationproperties-in-spring.html
http://www.java-allandsundry.com/2015/07/spring-boot-configurationproperties.html

test de performace : benchmark

use constant or enum : authority, profile, gender etc ...

add Suspend/restore 
http://heera.it/bootstrap-3-delete-confirm-dialog

https://www.google.fr/search?q=spring+message+properties+javascript&oq=spring+message+properties+in+js+&aqs=chrome.1.69i57j0.13021j0j1&sourceid=chrome&ie=UTF-8

http://formvalidation.io/

## L'algorithme : 
	* use map may be more easiest

## Front JSF
* prettyFace pour les url
	* richface
	Use JSF 2 new features

## Back - CORE

** gestion auditing with spring data jpa; inspire from jhipster;
** ElasticSearch
** add logtash logging from jhipster 3
spring security : persistent token / remember me
add gender : male , female (Mr, Me)
rename client to "patient"
add java 8 : optionnal, stream , etc
add status on rdv : reserved, absent, annulé, réalisé | boolean : active or not, |
* manage jsonignore and jsonfilter
* transform list to set
** use enum in entity field
** Column(name = "PRICE", precision = 9, scale = 2)
**  private BigDecimal price;
 
** gestion du cache, avec hazelcast
** gestion pool de connexion : HikariCP
changer le wording des méthode de service : use creteObjets, finAllObject, ajouter eager and easy ex: fingonewitheagerrelationship, npire to jhipter

extarnaliser pattern validation jpa présent sur les entity

apdate conf with https://github.com/jhipster/jhipster-sample-app

install graphite for metrics
ENABLE jCONSOLE WITH docker compose and logtash

## Audit - metric - Log
get stats : count login sucess, login faillure, count connected user , etc ..

## DATA MODEL
-add elastic search
-improve database : forme normal, add index
** manage - cascade type  - and fetchtype
** FK for user to client&medecins


** add " equals, hashcode and tostring " to all class object : quand 2 rv sont égaux, quand deux creneaux sont egaux ? etc
générer hascode et equals depuis l'ide depuis l'IDE
define this in abstract entity
* CSRF
** static helpers : check if it's really need element
** cors filter : http://sqli.developpez.com/tutoriels/spring/construire-backend-springboot/#LIV


## Back - ADMIN FRONT 
make design like jsipster siteweb, : comme 
i18n error message login page
* i18n, internationalisation
error page :  404
add appli monitor in backend, accee rest templet a l'api rest
chek client validation side in detail
	ORDRE dans l'affichage DES ERREURs FORMULARIE
    localize client side validation messages
use html5 and valider le format html avec : validator W3C
datatables i18n
** minifier css and js
** widget avec jauge du nbre rv 

## security 
secure technique service (web service) : authentification de type BASIC

## Front - client console
extraire la page login ans une page d'authenticatin à part avec desing perso
rebuild with javaFX
regrouper  client soncole et thymeleaf

## Test
- replace la partie console par test junit du service web
- ** test api rest | http://blog.soat.fr/2015/12/tester-une-api-rest-spring-mvc-avec-le-spring-testcontext-framework/
tst : assertj , hamcrest
- test rv même creneau , même medecin, même client
- test with spring-context and spring-test

## front - PATIENT Angularjs
send email feature to valid insciption or reset password | inspire from jhipster
bootstrap ; virer lescomposant bootstrap externe et use les natif inclu dans bootstrap : modal, select etc ...
vier lavue jubrotton
** netoyer les page unitile
* ou dans  : src/main/resources/static
** mettre un formulaire d'inscription patient
* validation form  coté client js
* modifier archtecture : passser de mvc a une archi orienté omposant comme sur jhipster



## Back - API Web service
** monitoring with actuator | https://dzone.com/articles/disable-spring-boot-production
** api rest : expose hypermedia link | Hypermedia-Driven REST services
** filterjson p403 p402, se passer des mapper,utiliser  annottion json inspired from ebank youssfi,  
* adopter REST HATEOAS   | https://zestedesavoir.com/tutoriels/299/la-theorie-rest-restful-et-hateoas/
** netoyer les page unitile
* rename field : firstname, lastname
* form add, virer tableau , use real form
* use partie javascript validation in admin
* Respect de la norme pour les api rest
* adapter le chemin mapping pour les controlleurs pour les 





** add lets' Enrypt https

** gestion timout RestTemplate

** gestion des cookies 
mettre msg : 
> En poursuivant votre navigation, vous acceptez l'utilisation de cookies afin de réaliser des statistiques d'audiences et vous proposer des services ou des offres adaptés à vos centres d'intérêts
This website uses cookies so that we can provide you the best user experience possible. By continuing to browse the site you are agreeing to our use of cookies.
ajouter "Les cookies nous permettent de fournir, protéger et améliorer les services de Facebook. En continuant à utiliser notre site, vous acceptez notre Politique d’utilisation des cookies.

** gestion du sessions du user : 
partir de lexemplle et s'inspirer de mcdo
voir quelle est la bonnepratique pour les sessions spring security , utiliser ou non ? p492 

** gestion erreurs
messages d'erreur , code et autres ... personnalisation, 
code error standar
page erreur et exception avec spring ; OptimisticLockException; 

** spring MVC : interceptor


** gestion des log : fichier log and console : slf4j + logback
Une interface pour rapidement faire une recherche dans les logs

** add envoi de mail :  
http://www.thymeleaf.org/doc/articles/springmail.html  & Apache Velocity Template & javaMail http://sivalabs.in/2011/05/sending-email-with-attachments-using-javamail/


** java doc est présente
** use set in place of list or collection
** add ajax, refreash layout bloc by ajax

## Spring boot
- gestion des exclusion spring boot , to only keep needed depndency

### Load Balancing : Répartition de charge ?

### Backup : Sauvegarde DB ?

### serveur cache proxy ? Varnish ?

### dashbord monitoring : like dashing , datadog ...

## MAVEN 
** ientifier les dependences communes et factoriser 
* mettre la partie angular dans un dossier app ,à part, dans le dossier maven 
maven report plugin / sonarqube etc ... jhipter
maven ; avoid all duplicat pom coe : profil conf, plugin cong, etc

-- factoriser la confg DB dans le core module :
	make the domain module and the higher level application properties to be loaded by Spring.
	http://stackoverflow.com/questions/23138494/spring-boot-application-properties-maven-multi-module-projects


### ADDITIONAL FEATURES
expore excel , spring batch
** localize doctor : map google
http://www.challenges.fr/entreprise/20140221.CHA0767/la-guerre-des-applis-qui-gerent-les-rdv-chez-le-medecin.html
** doctor speciality : 
https://www.zocdoc.com/
http://www.mondocteur.fr/
https://www.doctolib.fr/
https://www.direct-rdv.com/
** choix du medecins favoris / MEDECINS TRAINTANTS
** présentation des medecins
** add ABSENCE DU MEDECIN
** protection contre des attaques (XSS, CSRF, injections…).
** code barre
** echange de mal entre medein et patient
** deploy Heroku or Amazon Elastic Bean Stalk, cloudfoundery

** add spring loader pour le rechargement à chaud
http://javarevisited.blogspot.fr/2015/01/why-override-equals-hashcode-or-tostring-java.html
http://www.infoq.com/fr/articles/retour-sur-les-bases-equals-et-hashcode
voir dans les bouquins : jaa efficace,et...
** créationd'une module : reatJS


## area

internationalisation(i18n) de jQuery : globalise
Validation client side : Jquery.Validation plugin
Validation server side : Bean Validation (JSR 303) + Hibernate Validator, ResourceBundle pour chaque langue  
POST/Redirect/GET pattern with Flash Attributes 
thymeleaf layout

Support Date and Time API with jpa2.1 : use of attribute converter   

## LINK

<p align="center">
  <a href="https://angularclass.com" target="_blank">
    <img src="https://cloud.githubusercontent.com/assets/1016365/9863762/a84fed4a-5af7-11e5-9dde-d5da01e797e7.png" alt="Webpack and Angular 2" width="500" height="320"/>
  </a>
</p>




