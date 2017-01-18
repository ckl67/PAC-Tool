 PAC-Tool
----------
Outil pour Pompe à Chaleur

 Définition 
-------------
Outil qui permet de vérifier les données contructeur (COP) et de calculer le COP de votre pompe à  chaleur à  partir de vos mesures. (Programme écrit en Java)

 Chaine de Développement
--------------------------
La chaine d'outil pour le développement est la suivante:

Java
	http://www.oracle.com/technetwork/java/javase/downloads/index.html
	Version minimale: jre8u111
	Je conseille d'utiliser JDK

Environnement de développement intégré / (Integrated Development Environment)
	Eclipse IDE for Java Developers
	Version minimale: Neon.1a Release (4.6.1)
	https://eclipse.org/downloads/
	Choisir: Eclipse IDE for Java Developers

WindowBuilder (Eclipse)
	Installation WindowBuilder à travers Marketplace dans Eclipse
	
Langage de modélisation unifié, Unified Modeling Language (UML) intégré à  Eclipse
	ObjectAid
	Installer uniquement les modules ne nécessitant pas de licences.
	http://www.objectaid.com/installation
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
	Generation video: gource -s 1 -f 
	Ne fonctionne pas ..
	
	Convert to file
	gource -1024x768 --stop-position 1.0 --highlight-all-users --hide-filenames --seconds-per-day 5 --output-framerate 60 --output-ppm-stream output.ppm
	ffmpeg -y -r 60 -f image2pipe -vcodec ppm -i output.ppm  -vcodec wmv1 -r 60 -qscale 0 out.wmv
	
	ffmpeg -y -r 60 -f image2pipe -vcodec ppm -i output.ppm -vcodec libvpx -fpre "C:\\Program Files\\ffmpeg\\presets\\libvpx-360p.ffpreset" gource.avi
	
	

	
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

L'un des meilleurs livre gratuit que j'ai trouvé pour le Java
* http://math.hws.edu/javanotes/
