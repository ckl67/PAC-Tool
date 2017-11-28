 PAC-Tool
----------
Outil pour Pompe � Chaleur

 D�finition 
-------------
Outil qui permet de vérifier les données contructeur (COP) et de calculer le COP de votre pompe à chaleur à partir de vos mesures. 
<i>(Programme écrit en Java)</i>


 Chaine de Développement
--------------------------
La chaine d'outil pour le développement est la suivante:<br>
<i>Je donne ci-dessous l'ordre dans lequel je conseille de procéder à l'installation de la chaîne de développement.</i>

Création Compte Github
----------------------
La première chose va consister à créer un compte sur https://github.com/ afin que je puisse vous ajouter au projet.
Une fois cette opération faite, il faudra me contacter afin que je vous autorise à participer au projet.

Java
----
Installation de Java
Java Platform, Standard Edition<br>
http://www.oracle.com/technetwork/java/javase/downloads/index.html
Version minimale: jre8u111<br>
Utiliser JDK<br>

SourceTree
-----------
Client graphique clair et structuré, compatible avec Git <br>
https://www.sourcetreeapp.com/
Lors de l'installation de SourceTree, il faudra

	Créer un compte Altassian, ou utiliser un compte Google
	Choisir GitHub (L'association devrait se faire automatiquement)
	L'outil va 
		télécharger automatiquement la version embarquée de Git 
			(Dans le cas contraire ne pas pointer vers une version installée GIT voir plus loin)
		télécharger la version embarquée de Mercudial<br>
	Au niveau du clone (cad version locale) je vous invite à choisir un réprtoire sous workspace
	\Users\<nom>\workspace\pac-tool
	Par la suite, sourcetree demandera les informations utilisateurs que vous voulez associer.
	Il sagira ici de rentrer les informations de votre compte Github
	
	
Eclipse: Environnement de développement intégré
-----------------------------------------------
Eclipse IDE <i>(Integrated Development Environment)</i> for Java Developers<br>
Version minimale: Neon Release<br>
https://www.eclipse.org/downloads/eclipse-packages/<br>
Choisir: Eclipse Installer<br>
Lors de l'installation choisir: Eclipse IDE for Java Developers<br>

	Une fois installer, faire pointer sur le répertoire : \Users\<nom>\workspace\pac-tool

Add-ons intégrés à Eclipse (IDE)
--------------------------------
WindowBuilder
-------------
	Installation WindowBuilder à travers Marketplace dans Eclipse

Editeur HTML
	Par exemple: kompozer-0.7.10-win32
	Int�gr� � Eclipse
	HTML Editor WPT 
	
Langage de mod�lisation unifi�, Unified Modeling Language (UML) int�gr� � Eclipse
	ObjectAid
	Installer uniquement les modules ne n�cessitant pas de licences.
	Au niveau Eclipse dans Install New Software
	Name: ObjectAid UML Explorer
	URL: http://www.objectaid.com/update/current

log4j Logger
<<<<<<< HEAD
	Log4j Plugin pour le logging  
	Doit �tre t�l�charg� sur le site officiel
		Download : https://logging.apache.org/log4j/2.x/download.html 
	Utilisation:
	http://logging.apache.org/log4j/2.x/manual/api.html
	
=======
------------
	Log4j Plugin pour le logging  <br>
	Doit être téléchargé sur le site officiel<br>
		Download : https://logging.apache.org/log4j/2.x/download.html <br>
	Utilisation:<br>
	http://logging.apache.org/log4j/2.x/manual/api.html<br>
		
Langage de modélisation unifié, Unified Modeling Language (UML) intégré à  Eclipse
	ObjectAid<br>
	Installer uniquement les modules ne nécessitant pas de licences.<br>
	Au niveau Eclipse dans Install New Software<br>
	Name: ObjectAid UML Explorer<br>
	URL: http://www.objectaid.com/update<br>
	
Editeur HTML
	Par exemple: kompozer-0.7.10-win32<br>
	Intégré à Eclipse<br>


Fuite mémoire
	Afin de trouver les fuites mémoires, il est conseillé d'activer les paramètres de vérification code au niveau Eclipse<br>
	Par la suite, un outil très intéressant est: visualvm<br>
	https://visualvm.github.io/download.html<br>
	Introduction:<br>
	https://developers.redhat.com/blog/2014/08/14/find-fix-memory-leaks-java-application/<br>
	

	<br>
Pour le test final, préférer la méthode en ligne de commande<br>
java -jar PAC-Tool.jar



>>>>>>> a9baa606100f8c7b12d179c6b4bd0fd0262bf888

Gestion de versions Git
	Git est intégré à Eclipse, mais je préfère utiliser SourceTree.<br>
	Git est donc intégré à SourceTree, mais afin de pouvoir créer une animation montrant l'évolution du développement<br>
	Git est nécessaire en local sous windows<br>
	https://git-scm.com/download/win<br>
	

	
GitHub 
	Service Web d'hébergement et de gestion de développement de logiciels. <br>
	https://github.com/ckl67/PAC-Tool<br>
	Projet sous: PAC-Tool<br>

ffmpeg
	converteur video
	Après installation modifier variable d'environnement pour pointer sur ffmpeg<br>
	https://ffmpeg.zeranoe.com/builds/<br>
	
Gource
	Outil d'animation du projet<br>
	http://gource.io/#<br>
	Generation video: (durant la génération la fenêtre doit resté ouverte )<br>
	https://github.com/acaudwell/Gource/wiki/Videos<br>
	
Conversion Jar vers Exe
	Launch4j est une application qui permet de créer des "lanceurs" (exécutables Windows classiques 
	pour des applications développées en Java. <br>
	http://launch4j.sourceforge.net/<br>
	Pour avoir une version standalone (Java bundled) il faut que Java soit installé dans un répertoire
	relatif.<br>
	Je conseille d'utiliser JDK, et de le copier sous: ...\pac-tool\jar<br>

Creation d'un installateur avec Inno.
	Inno permet de créer un installeur Window<br>
	http://www.jrsoftware.org/isinfo.php<br>
	
wxMaxima
	Outil de calcul symbolique.<br>
	Très utile pour la simplification des expressions mathématiques<br>
	http://andrejv.github.io/wxmaxima/index.html<br>

Introduction au Java
--------------------
OpenClassRooms est une excellente introduction au Java<br>
* https://openclassrooms.com/courses/apprenez-a-programmer-en-java

D'autres références sont données au niveau du répertoire: <br>
..\pac-tool\dev_help

 Autre
-------
La chaine de diffusion Youtube est sous le compte: christian.klugesherz@geosolterm.fr
