 PAC-Tool
----------
Outil pour Pompe à Chaleur

 Définition 
-------------
Outil qui permet de vérifier les données contructeur (COP) et de calculer le COP de votre pompe à  chaleur à  partir de vos mesures. (Programme écrit en Java)

 Chaine de Développement
--------------------------
La chaine d'outil pour le développement est la suivante:

Environnement de développement intégré / (Integrated Development Environment)
	Eclipse IDE for Java Developers
	Version minimale: Neon.1a Release (4.6.1)
	https://eclipse.org/downloads/
	Choisir
	* Eclipse IDE for Java Developers
	Plugin
	* Installation WindowBuilder à travers Marketplace dans Eclipse

Langage de modélisation unifié, Unified Modeling Language (UML) intégré à  Eclipse
	ObjectAid
	http://www.objectaid.com/installation
	Installer uniquement les modules ne nécessitant pas de licences.
	
Java
	http://www.oracle.com/technetwork/java/javase/downloads/index.html
	Version minimale: jre8u111

Editeur HTML
	Par exemple: kompozer-0.7.10-win32

Gestion de versions Git
	Est intégré à SourceTree, mais afin de pouvoir créer une animation montrant l'évolution du développement
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
	
Gource
	Outil d'animation du projet
	http://gource.io/#
	Generation video: gource -s 1 -f
	
Conversion Jar vers Exe
	Launch4j est une application qui permet de créer des "lanceurs" (exécutables Windows classiques 
	pour des applications développées en Java. 
	http://launch4j.sourceforge.net/
	Pour avoir une version standalone (Java bundled) il faut que Java soit installé dans un répertoire
	relatif.
	Je conseille d'utiliser JDK, et de le copier sous: ...\pac-tool\jar
	http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
	
	
wxMaxima
	Outil de calcul symbolique.
	Très utile pour la simplification des expressions mathématiques
	http://andrejv.github.io/wxmaxima/index.html

Introduction au Java
--------------------
L'un des meilleurs livre gratuit que j'ai trouvé pour le Java
* http://math.hws.edu/javanotes/

Sinon OpenClassRooms est une excellente introduction 
* https://openclassrooms.com/courses/apprenez-a-programmer-en-java


