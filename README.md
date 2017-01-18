 PAC-Tool
----------
Outil pour Pompe � Chaleur

 D�finition 
-------------
Outil qui permet de v�rifier les donn�es contructeur (COP) et de calculer le COP de votre pompe � chaleur � partir de vos mesures. (Programme �crit en Java)

 Chaine de D�veloppement
--------------------------
La chaine d'outil pour le d�veloppement est la suivante:

Java
	http://www.oracle.com/technetwork/java/javase/downloads/index.html
	Version minimale: jre8u111
	Je conseille d'utiliser JDK

Environnement de d�veloppement int�gr� / (Integrated Development Environment)
	Eclipse IDE for Java Developers
	Version minimale: Neon.1a Release (4.6.1)
	https://eclipse.org/downloads/
	Choisir: Eclipse IDE for Java Developers

WindowBuilder (Eclipse)
	Installation WindowBuilder � travers Marketplace dans Eclipse
	
Langage de mod�lisation unifi�, Unified Modeling Language (UML) int�gr� � Eclipse
	ObjectAid
	Installer uniquement les modules ne n�cessitant pas de licences.
	http://www.objectaid.com/installation
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
	Generation video: gource -s 1 -f 
	Ne fonctionne pas ..
	
	Convert to file
	gource -1024x768 --stop-position 1.0 --highlight-all-users --hide-filenames --seconds-per-day 5 --output-framerate 60 --output-ppm-stream output.ppm
	ffmpeg -y -r 60 -f image2pipe -vcodec ppm -i output.ppm  -vcodec wmv1 -r 60 -qscale 0 out.wmv
	
	ffmpeg -y -r 60 -f image2pipe -vcodec ppm -i output.ppm -vcodec libvpx -fpre "C:\\Program Files\\ffmpeg\\presets\\libvpx-360p.ffpreset" gource.avi
	
	

	
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

L'un des meilleurs livre gratuit que j'ai trouv� pour le Java
* http://math.hws.edu/javanotes/
