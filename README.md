 PAC-Tool
----------
Outil pour Pompe � Chaleur

 D�finition 
-------------
Outil qui permet de v�rifier les donn�es contructeur (COP) et de calculer le COP de votre pompe � chaleur � partir de vos mesures. 
(Programme �crit en Java)

Pour le test, pr�f�rer la m�thode en ligne de commande
java -jar PAC-Tool.jar

 Chaine de D�veloppement
--------------------------
La chaine d'outil pour le d�veloppement est la suivante:

Java
	http://www.oracle.com/technetwork/java/javase/downloads/index.html
	Version minimale: jre8u111
	Je conseille d'utiliser JDK

Fuite m�moire
	Afin de trouver les fuites m�moires, il est conseill� d'activer les param�tres de v�rification code au niveau Eclipse
	Par la suite, un outil tr�s int�ressant est: visualvm
	https://visualvm.github.io/download.html
	Introduction:
	https://developers.redhat.com/blog/2014/08/14/find-fix-memory-leaks-java-application/
	
Environnement de d�veloppement int�gr� / (Integrated Development Environment)
	Eclipse IDE for Java Developers
	Version minimale: Neon.1a Release (4.6.1)
	https://eclipse.org/downloads/
	Choisir: Eclipse IDE for Java Developers

WindowBuilder (Eclipse)
	Installation WindowBuilder � travers Marketplace dans Eclipse

log4j Logger
	Log4j Plugin pour le logging  
		Download : https://logging.apache.org/log4j/2.x/download.html 
	Utilisation:
	http://logging.apache.org/log4j/2.x/manual/api.html
		
Langage de mod�lisation unifi�, Unified Modeling Language (UML) int�gr� � Eclipse
	ObjectAid
	Installer uniquement les modules ne n�cessitant pas de licences.
	Au niveau Eclipse dans Install New Software
	Name: ObjectAid UML Explorer
	URL: http://www.objectaid.com/update
	
Editeur HTML
	Par exemple: kompozer-0.7.10-win32

Gestion de versions Git
	Git est int�gr� � Eclipse, mais je pr�f�re utiliser SourceTree.
	Git est donc int�gr� �SourceTree, mais afin de pouvoir cr�er une animation montrant l'�volution du d�veloppement
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

ffmpeg
	converteur video
	Apr�s installation modifier variable d'environnement pour pointer sur ffmpeg
	https://ffmpeg.zeranoe.com/builds/
	
Gource
	Outil d'animation du projet
	http://gource.io/#
	Generation video: 
	gource -920x691 -s 1 
	gource -920x691 -s 2 
	ou autre taille selon �craon
	
	Utiliser un logiciel de capture �cran.
	comme:
	* Free Screen To Video V2.0
	
Conversion Jar vers Exe
	Launch4j est une application qui permet de cr�er des "lanceurs" (ex�cutables Windows classiques 
	pour des applications d�velopp�es en Java. 
	http://launch4j.sourceforge.net/
	Pour avoir une version standalone (Java bundled) il faut que Java soit install� dans un r�pertoire
	relatif.
	Je conseille d'utiliser JDK, et de le copier sous: ...\pac-tool\jar

Creation d'un installateur avec Inno.
	Inno permet de cr�er un installeur Window
	http://www.jrsoftware.org/isinfo.php
	
wxMaxima
	Outil de calcul symbolique.
	Tr�s utile pour la simplification des expressions math�matiques
	http://andrejv.github.io/wxmaxima/index.html

Introduction au Java
--------------------
OpenClassRooms est une excellente introduction au Java
* https://openclassrooms.com/courses/apprenez-a-programmer-en-java

D'autres r�f�rences sont donn�es au niveau du r�pertoire: 
..\pac-tool\dev_help
