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
* delete cle étrangère
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
** cors filter : http://sqli.developpez.com/tutoriels/spring/construire-backend-springboot/#LIV
** add elastic search
** gestion des transaction spring data
** set errorpage with atuator or 
** minifier css and js
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
** inplément auditing with spring dat jpa; inspire from jhipster
** use enum in entity field
** add ABSENCE DU MEDECIN


** add " equals, hashcode and tostring " to all class object
http://javarevisited.blogspot.fr/2015/01/why-override-equals-hashcode-or-tostring-java.html
http://www.infoq.com/fr/articles/retour-sur-les-bases-equals-et-hashcode
voir dans les bouquins : jaa efficace,et...

** créationd'une module : reatJS

## area

Validation Form client : Jquery Validate
Validation Modele server side
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