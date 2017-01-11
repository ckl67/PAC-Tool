 PAC-Tool
----------
Outil pour Pompe � Chaleur

 D�finition 
-------------
Outil qui permet de v�rifier les donn�es contructeur (COP) et de calculer le COP de votre pompe � chaleur � partir de vos mesures. (Programme �crit en Java)

 Chaine de D�veloppement
--------------------------
La chaine d'outil pour le d�veloppement est la suivante:

Environnement de d�veloppement int�gr� / (Integrated Development Environment)
	Eclipse IDE for Java Developers
	Version minimale: Neon.1a Release (4.6.1)
	https://eclipse.org/downloads/
	Choisir
	* Eclipse IDE for Java Developers
	Plugin
	* Installation WindowBuilder � travers Marketplace dans Eclipse

Langage de mod�lisation unifi�, Unified Modeling Language (UML) int�gr� � Eclipse
	ObjectAid
	http://www.objectaid.com/installation
	Installer uniquement les modules ne n�cessitant pas de licences.
	
Java
	http://www.oracle.com/technetwork/java/javase/downloads/index.html
	Version minimale: jre8u111

Editeur HTML
	Par exemple: kompozer-0.7.10-win32

Gestion de versions Git
	Est int�gr� �SourceTree, mais afin de pouvoir cr�er une animation montrant l'�volution du d�veloppement
	Git est n�cessaire en local sous windows
	https://git-scm.com/download/win		
	
SourceTree	
	Client graphique claire et structur�e, compatible avec Git
	Dans option Git, activer: "Update Embedded Git" afin d'avoir la version int�gr�e de Git
	https://www.sourcetreeapp.com/
	
GitHub 
	Service Web d'h�bergement et de gestion de d�veloppement de logiciels. 
	https://github.com/ckl67/PAC-Tool
	Projet sous: PAC-Tool
	
Gource
	Outil d'animation du projet
	http://gource.io/#
	Generation video: gource -s 1 -f
	
Conversion Jar vers Exe
	Launch4j est une application qui permet de cr�er des "lanceurs" (ex�cutables Windows classiques 
	pour des applications d�velopp�es en Java. 
	http://launch4j.sourceforge.net/
	Pour avoir une version standalone (Java bundled) il faut que Java soit install� dans un r�pertoire
	relatif.
	Je conseille d'utiliser JDK, et de le copier sous: ...\pac-tool\jar
	http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
	
	
wxMaxima
	Outil de calcul symbolique.
	Tr�s utile pour la simplification des expressions math�matiques
	http://andrejv.github.io/wxmaxima/index.html

Introduction au Java
--------------------
L'un des meilleurs livre gratuit que j'ai trouv� pour le Java
* http://math.hws.edu/javanotes/

Sinon OpenClassRooms est une excellente introduction 
* https://openclassrooms.com/courses/apprenez-a-programmer-en-java


