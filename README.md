 PAC-Tool
----------
Outil pour Pompe à Chaleur

 Définition 
-------------
Outil qui permet de vérifier les données contructeur (COP) et de calculer le COP de votre pompe à  chaleur à  partir de vos mesures. 
(Programme écrit en Java)

Pour le test, préférer la méthode en ligne de commande
java -jar PAC-Tool.jar

 Chaine de Développement
--------------------------
La chaine d'outil pour le développement est la suivante:

Java
	http://www.oracle.com/technetwork/java/javase/downloads/index.html
	Version minimale: jre8u111
	Je conseille d'utiliser JDK

Fuite mémoire
	Afin de trouver les fuites mémoires, il est conseillé d'activer les paramètres de vérification code au niveau Eclipse
	Par la suite, un outil très intéressant est: visualvm
	https://visualvm.github.io/download.html
	Introduction:
	https://developers.redhat.com/blog/2014/08/14/find-fix-memory-leaks-java-application/
	
Environnement de développement intégré / (Integrated Development Environment)
	Eclipse IDE for Java Developers
	Version minimale: Neon.1a Release (4.6.1)
	https://eclipse.org/downloads/
	Choisir: Eclipse IDE for Java Developers

WindowBuilder (Eclipse)
	Installation WindowBuilder à travers Marketplace dans Eclipse

log4j Logger
	Log4j Plugin pour le logging  
		Download : https://logging.apache.org/log4j/2.x/download.html 
	Utilisation:
	http://logging.apache.org/log4j/2.x/manual/api.html
		
Langage de modélisation unifié, Unified Modeling Language (UML) intégré à  Eclipse
	ObjectAid
	Installer uniquement les modules ne nécessitant pas de licences.
	Au niveau Eclipse dans Install New Software
	Name: ObjectAid UML Explorer
	URL: http://www.objectaid.com/update
	
Editeur HTML
	Par exemple: kompozer-0.7.10-win32

Gestion de versions Git
	Git est intégré à Eclipse, mais je préfère utiliser SourceTree.
	Git est donc intégré à SourceTree, mais afin de pouvoir créer une animation montrant l'évolution du développement
	Git est nécessaire en local sous windows
	https://git-scm.com/download/win		
	
SourceTree	
	Client graphique claire et structurée, compatible avec Git
	Dans option Git, activer: "Update Embedded Git" afin d'avoir la version intégrée de Git
	https://www.sourcetreeapp.com/
	
GitHub 
	Service Web d'hébergement et de gestion de développement de logiciels. 
	https://github.com/ckl67/PAC-Tool
	Projet sous: PAC-Tool

ffmpeg
	converteur video
	Après installation modifier variable d'environnement pour pointer sur ffmpeg
	https://ffmpeg.zeranoe.com/builds/
	
Gource
	Outil d'animation du projet
	http://gource.io/#
	Generation video: 
	gource -920x691 -s 1 
	gource -920x691 -s 2 
	ou autre taille selon écraon
	
	Utiliser un logiciel de capture écran.
	comme:
	* Free Screen To Video V2.0
	
Conversion Jar vers Exe
	Launch4j est une application qui permet de créer des "lanceurs" (exécutables Windows classiques 
	pour des applications développées en Java. 
	http://launch4j.sourceforge.net/
	Pour avoir une version standalone (Java bundled) il faut que Java soit installé dans un répertoire
	relatif.
	Je conseille d'utiliser JDK, et de le copier sous: ...\pac-tool\jar

Creation d'un installateur avec Inno.
	Inno permet de créer un installeur Window
	http://www.jrsoftware.org/isinfo.php
	
wxMaxima
	Outil de calcul symbolique.
	Très utile pour la simplification des expressions mathématiques
	http://andrejv.github.io/wxmaxima/index.html

Introduction au Java
--------------------
OpenClassRooms est une excellente introduction au Java
* https://openclassrooms.com/courses/apprenez-a-programmer-en-java

D'autres références sont données au niveau du répertoire: 
..\pac-tool\dev_help
