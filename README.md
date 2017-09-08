 PAC-Tool
----------
Outil pour Pompe à Chaleur

 Définition 
-------------
Outil qui permet de vérifier les données contructeur (COP) et de calculer le COP de votre pompe à  chaleur à  partir de vos mesures. 
(Programme écrit en Java)
<br>
Pour le test final, préférer la méthode en ligne de commande<br>
java -jar PAC-Tool.jar

 Chaine de Développement
--------------------------
La chaine d'outil pour le développement est la suivante:

Java
	http://www.oracle.com/technetwork/java/javase/downloads/index.html
	Version minimale: jre8u111<br>
	Je conseille d'utiliser JDK<br>

Fuite mémoire
	Afin de trouver les fuites mémoires, il est conseillé d'activer les paramètres de vérification code au niveau Eclipse<br>
	Par la suite, un outil très intéressant est: visualvm<br>
	https://visualvm.github.io/download.html<br>
	Introduction:<br>
	https://developers.redhat.com/blog/2014/08/14/find-fix-memory-leaks-java-application/<br>
	
Environnement de développement intégré / (Integrated Development Environment)
	Eclipse IDE for Java Developers<br>
	Version minimale: Neon.1a Release (4.6.1)<br>
	https://eclipse.org/downloads/<br>
	Choisir: Eclipse IDE for Java Developers<br>

WindowBuilder (Eclipse)
	Installation WindowBuilder à travers Marketplace dans Eclipse

log4j Logger
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

Gestion de versions Git
	Git est intégré à Eclipse, mais je préfère utiliser SourceTree.<br>
	Git est donc intégré à SourceTree, mais afin de pouvoir créer une animation montrant l'évolution du développement<br>
	Git est nécessaire en local sous windows<br>
	https://git-scm.com/download/win<br>
	
SourceTree	
	Client graphique claire et structurée, compatible avec Git <br>
	Lors de l'installation de SourceTree, il faudra <br>
	télécharger la version embarquée de Git (ne pas pointer vers la version installée voir ci-dessus), <br>
	télécharger une version embarquée de Mercudial<br>
	Dans option Git, activer: "Update Embedded Git" afin d'avoir la version intégrée de Git<br>
	https://www.sourcetreeapp.com/
	
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
