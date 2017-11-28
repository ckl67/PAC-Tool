# PAC-Tool
Outil pour Pompe à Chaleur

# Définition 
Outil qui permet de vérifier les données contructeur (COP) et de calculer le COP de votre pompe à chaleur à partir de vos mesures.  
*(Programme écrit en Java)*

# Chaine de Développement
La chaine d'outil pour le développement est la suivante:  
*Je donne ci-dessous l'ordre dans lequel je conseille de procéder à l'installation de la chaîne de développement.*

# GitHub
Service Web d'hébergement et de gestion de développement de logiciels : **https://github.com/ckl67/PAC-Tool**
Projet sous: PAC-Tool

## Création Compte Github
La première chose va consister à créer un compte sur : **https://github.com/** afin que je puisse vous ajouter au projet.  
Une fois cette opération faite, il faudra me contacter afin que je vous autorise à participer au projet.

# Java
Installation de Java : **http://www.oracle.com/technetwork/java/javase/downloads/index.html**  
Choisir:   
* Java Platform, Standard Edition
* Version minimale: jre8u111
* Utiliser JDK

# SourceTree
Client graphique clair et structuré, compatible avec Git : **https://www.sourcetreeapp.com/**  
Lors de l'installation de SourceTree, il faudra  
* Créer un compte Altassian, ou utiliser un compte Google
* Choisir GitHub (L'association devrait se faire automatiquement)
* L'outil va 
* télécharger automatiquement la version embarquée de Git
* télécharger la version embarquée de Mercudial
* Au niveau du clone *(cad version locale)*, je vous invite à choisir un répertoire sous workspace : \Users\<votre compte windows>\workspace
	
# Eclipse: Environnement de développement intégré
Eclipse IDE *(Integrated Development Environment)* for Java Developers : **https://www.eclipse.org/downloads/eclipse-packages/**
* Version minimale: Oxygene Release
* Choisir: Eclipse Installer
* Lors de l'installation choisir: Eclipse IDE for Java Developers
* Une fois installer, faire pointer sur le répertoire : \Users\<nom>\workspace

## Add-ons intégrés à Eclipse (IDE)
### WindowBuilder
Permet de créer des interface graphique  
A travers Marketplace dans Eclipse : **WindowBuilder**   

### Editeur HTML
Editeur HTML, *(non wysiwyg)*  
A travers Marketplace dans Eclipse : **HTML Editor WPT** 
	
### Langage de modélisation unifié (UML)
Unified Modeling Language (UML) intégré à Eclipse, permet de voir l'arborescence des classes  
ObjectAid
* Installer uniquement les modules ne nécessitant pas de licences.
* Au niveau Eclipse dans Install New Software
* Work with: **http://www.objectaid.com/update/current**
* Name: ObjectAid UML Explorer

### log4j Logger
Log4j est un Plugin pour le logging 
Il n'y a rien à faire, car les jar files font déjà partis du projets.
C'est uniquement dans le cas de mise à jour qu'il faudra copier les fichiers au niveau du répertoirelib du projet    
Principe  
Doit être téléchargé sur le site officiel : **https://logging.apache.org/log4j/2.x/download.html** 
* Apache "Log4j 2 binary (zip)" --> choisir : log4j-api-2.x.y.jar et log4j-core-2.x.y.jar)
* Utilisation: http://logging.apache.org/log4j/2.x/manual/api.html

### JSON
Afin de pouvoir travailler avec du JSON, nous utilisons le fichier jar: json-simple  
Ici encore une fois, cette opération n'est nécessaire que dans le cas d'une mise à jour de json-simple  
Pour plus d'explication voir : **https://www.tutorialspoint.com/json/json_java_example.htm**	

# Fuite mémoiree
Afin de trouver les fuites mémoires, il est conseillé d'activer les paramètres de vérification code au niveau Eclipse  
Par la suite, un outil très intéressant est: visualv : **https://visualvm.github.io/download.html**  
Introduction : https://developers.redhat.com/blog/2014/08/14/find-fix-memory-leaks-java-application/

# Gestion de versions Git
Git est intégré à Eclipse, mais je préfère utiliser SourceTree.
Git est donc intégré à SourceTree, mais afin de pouvoir créer une animation montrant l'évolution du développement
Git est nécessaire en local sous windows
https://git-scm.com/download/win

# ffmpeg
converteur vidéoo
Après installation modifier variable d'environnement pour pointer sur ffmpeg : 	**https://ffmpeg.zeranoe.com/builds**/

# Gource
Outil d'animation du projet : **http://gource.io/**
Generation video: (durant la génération la fenêtre doit resté ouverte) 
https://github.com/acaudwell/Gource/wiki/Video

# Conversion Jar vers .Exec
Launch4j est une application qui permet de créer des "lanceurs" *(exécutables Windows classiques)* pour des applications développées en Java. 
Se trouve : **http://launch4j.sourceforge.net/**  
Pour avoir une version standalone (Java bundled) il faut que Java soit installé dans un répertoire relatif.  
Je conseille d'utiliser JDK, et de le copier sous: ...\pac-tool\jarr

# Inno : Installateur Windows 
Inno permet de créer un installeur Windows : **http://www.jrsoftware.org/isinfo.php **
	
# wxMaxima
Outil de calcul symbolique.
Très utile pour la simplification des expressions mathématiques : **http://andrejv.github.io/wxmaxima/index.html**

# Autre

## Conseils
Pour le test final, préférer la méthode en ligne de command : **java -jar PAC-Tool.java*
	

## Introduction au Java
OpenClassRooms est une excellente introduction au Java : **https://openclassrooms.com/courses/apprenez-a-programmer-en-java **  
D'autres références sont données au niveau du répertoire: 
..\pac-tool\dev_help

## Youtube
La chaine de diffusion Youtube est sous le compte: christian.klugesherz@geosolterm.fr
