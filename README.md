# PAC-Tool
Outil pour Pompe � Chaleur

# D�finition 
Outil qui permet de v�rifier les donn�es contructeur (COP) et de calculer le COP de votre pompe �chaleur � partir de vos mesures.  
*(Programme �crit en Java)*

# Chaine de D�veloppement
La chaine d'outil pour le d�veloppement est la suivante:  
*Je donne ci-dessous l'ordre dans lequel je conseille de proc�der � l'installation de la cha�ne de d�veloppement.*

# GitHub
Service Web d'h�bergement et de gestion de d�veloppement de logiciels : **https://github.com/ckl67/PAC-Tool**
Projet sous: PAC-Tool

## Cr�ation Compte Github
La premi�re chose va consister � cr�er un compte sur : **https://github.com/** afin que je puisse vous ajouter au projet.  
Une fois cette op�ration faite, il faudra me contacter afin que je vous autorise � participer au projet.

# Java
Installation de Java : **http://www.oracle.com/technetwork/java/javase/downloads/index.html**  
Choisir:   
* Java Platform, Standard Edition
* Version minimale: jre8u111
* Utiliser JDK

# SourceTree
Client graphique clair et structur�, compatible avec Git : **https://www.sourcetreeapp.com/**  
Lors de l'installation de SourceTree, il faudra  
* Cr�er un compte Altassian, ou utiliser un compte Google
* Choisir GitHub (L'association devrait se faire automatiquement)
* L'outil va 
* t�l�charger automatiquement la version embarqu�e de Git
* t�l�charger la version embarqu�e de Mercudial
* Au niveau du clone *(cad version locale)*, je vous invite � choisir un r�pertoire sous workspace : \Users\<votre compte windows>\workspace
	
# Eclipse: Environnement de d�veloppement int�gr�
Eclipse IDE *(Integrated Development Environment)* for Java Developers : **https://www.eclipse.org/downloads/eclipse-packages/**
* Version minimale: Oxygene Release
* Choisir: Eclipse Installer
* Lors de l'installation choisir: Eclipse IDE for Java Developers
* Une fois installer, faire pointer sur le r�pertoire : \Users\<nom>\workspace

## Add-ons int�gr�s � Eclipse (IDE)
### WindowBuilder
Permet de cr�er des interface graphique  
A travers Marketplace dans Eclipse : **WindowBuilder**   

### Editeur HTML
Editeur HTML, *(non wysiwyg)*  
A travers Marketplace dans Eclipse : **HTML Editor WPT** 
	
### Langage de mod�lisation unifi� (UML)
Unified Modeling Language (UML) int�gr� � Eclipse, permet de voir l'arborescence des classes  
ObjectAid
* Installer uniquement les modules ne n�cessitant pas de licences.
* Au niveau Eclipse dans Install New Software
* Work with: **http://www.objectaid.com/update/current**
* Name: ObjectAid UML Explorer

### log4j Logger
Log4j est un Plugin pour le logging 
Il n'y a rien � faire, car les jar files font d�j� partis du projets.
C'est uniquement dans le cas de mise � jour qu'il faudra copier les fichiers au niveau du r�pertoirelib du projet    
Principe  
Doit �tre t�l�charg� sur le site officiel : **https://logging.apache.org/log4j/2.x/download.html** 
* Apache "Log4j 2 binary (zip)" --> choisir : log4j-api-2.x.y.jar et log4j-core-2.x.y.jar)
* Utilisation: http://logging.apache.org/log4j/2.x/manual/api.html

### JSON
Afin de pouvoir travailler avec du JSON, nous utilisons le fichier jar: json-simple  
Ici encore une fois, cette op�ration n'est n�cessaire que dans le cas d'une mise � jour de json-simple  
Pour plus d'explication voir : **https://www.tutorialspoint.com/json/json_java_example.htm**	

# Fuite m�moiree
Afin de trouver les fuites m�moires, il est conseill� d'activer les param�tres de v�rification code au niveau Eclipse  
Par la suite, un outil tr�s int�ressant est: visualv : **https://visualvm.github.io/download.html**  
Introduction : https://developers.redhat.com/blog/2014/08/14/find-fix-memory-leaks-java-application/

# Gestion de versions Git
Git est int�gr� � Eclipse, mais je pr�f�re utiliser SourceTree.
Git est donc int�gr� �SourceTree, mais afin de pouvoir cr�er une animation montrant l'�volution du d�veloppement
Git est n�cessaire en local sous windows
https://git-scm.com/download/win

# ffmpeg
converteur vid�oo
Apr�s installation modifier variable d'environnement pour pointer sur ffmpeg : 	**https://ffmpeg.zeranoe.com/builds**/

# Gource
Outil d'animation du projet : **http://gource.io/**
Generation video: (durant la g�n�ration la fen�tre doit rest� ouverte) 
https://github.com/acaudwell/Gource/wiki/Video

# Conversion Jar vers .Exec
Launch4j est une application qui permet de cr�er des "lanceurs" *(ex�cutables Windows classiques)* pour des applications d�velopp�es en Java. 
Se trouve : **http://launch4j.sourceforge.net/**  
Pour avoir une version standalone (Java bundled) il faut que Java soit install� dans un r�pertoire relatif.  
Je conseille d'utiliser JDK, et de le copier sous: ...\pac-tool\jarr

# Inno : Installateur Windows 
Inno permet de cr�er un installeur Windows : **http://www.jrsoftware.org/isinfo.php **
	
# wxMaxima
Outil de calcul symbolique.
Tr�s utile pour la simplification des expressions math�matiques : **http://andrejv.github.io/wxmaxima/index.html**

# Autre

## Conseils
Pour le test final, pr�f�rer la m�thode en ligne de command : **java -jar PAC-Tool.java*
	

## Introduction au Java
OpenClassRooms est une excellente introduction au Java : **https://openclassrooms.com/courses/apprenez-a-programmer-en-java **  
D'autres r�f�rences sont donn�es au niveau du r�pertoire: 
..\pac-tool\dev_help

## Youtube
La chaine de diffusion Youtube est sous le compte: christian.klugesherz@geosolterm.fr
