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
Si vous souhaitez participer au développement du projet  
La première chose va consister à créer un compte sur : **https://github.com/** afin que je puisse vous ajouter au projet.    
Une fois cette opération faite, il faudra me contacter afin que je vous autorise à participer au projet.

# Java
Installation de Java : **http://www.oracle.com/technetwork/java/javase/downloads/index.html**  
Choisir:   
* Java Platform, Standard Edition
* Version minimale: jre8u111
* Utiliser JDK

# SourceTree
SourceTree est un client graphique clair et structuré, compatible avec Git : **https://www.sourcetreeapp.com/**  
Lors de l'installation de SourceTree, il faudra  
* Créer un compte Altassian, ou utiliser un compte Google
* Choisir GitHub 
 
Par la suite, L'outil va
* télécharger automatiquement la version embarquée de Git
* télécharger la version embarquée de Mercudial

Afin de créer l'espace de travail sur votre PC, et obtenir une copie du dépôt (PAC-TOOL) Git existant *(= Clone)*  
* Pour la configuration du **clone**, il faudra choisir le répertoire : <u>\Users\<votre compte windows>\workspace</u>
	
# Eclipse: Environnement de développement intégré
Eclipse IDE *(Integrated Development Environment)* for Java Developers : **https://www.eclipse.org/downloads/eclipse-packages/**
* Version minimale: Oxygene Release
* Choisir: Eclipse Installer
* Lors de l'installation choisir: Eclipse IDE for Java Developers
* Une fois installer, faire pointer sur le répertoire : <u>\Users\<votre compte windows>\workspace</u>

## Add-ons intégrés à Eclipse (IDE)
### WindowBuilder
Permet de créer des interface graphique  
Sous Eclipse, à travers Help/Marketplace installer : **WindowBuilder**   

### Editeur HTML
Editeur HTML, *(non wysiwyg)*  
Sous Eclipse, à travers Help/Marketplace installer : **HTML Editor WPT**  
A l'installation, il pourrait y avoir un conflit avec XML, dans ce cas je vous conseille de choisir : "Keep My installation the same"  
*(Pour l'instant je n'ai pas réussi à trouver un éditeur HTML wysiwyg)*

### Langage de modélisation unifié (UML)
Unified Modeling Language (UML) permet de voir l'arborescence des classes  
ObjectAid
* Installer uniquement les modules ne nécessitant pas de licences.
* Au niveau Eclipse dans Install New Software
* Work with: **http://www.objectaid.com/update/current**
* Name: ObjectAid UML Explorer

### log4j Logger
Log4j est un Plugin pour permettre le logging 
Il n'y a rien à faire ici. Les .jar files font déjà partis du projets.
C'est uniquement dans le cas de mise à jour qu'il faudra copier les fichiers au niveau du répertoirelib du projet    
Principe  
Doit être téléchargé sur le site officiel : **https://logging.apache.org/log4j/2.x/download.html** 
* Apache "Log4j 2 binary (zip)" --> choisir : log4j-api-2.x.y.jar et log4j-core-2.x.y.jar)
* Utilisation: http://logging.apache.org/log4j/2.x/manual/api.html

### JSON
Afin de pouvoir travailler avec du JSON, nous utilisons le fichier jar: json-simple  
Ici encore une fois, cette opération est nécessaire que dans le cas d'une mise à jour de json-simple  
Pour plus d'explication voir : **https://www.tutorialspoint.com/json/json_java_example.htm**	

# Fuite mémoire
Afin de trouver les fuites mémoires, il est conseillé d'activer les paramètres de vérification code au niveau Eclipse    
*(par défaut, il y a des warning, que l'on peut passer en Error au niveau de la configuration du projet)*  
voir: https://developers.redhat.com/blog/2014/08/14/find-fix-memory-leaks-java-application/  

Un outil très intéressant est: visualvm : **https://visualvm.github.io/download.html**  
Introduction : https://developers.redhat.com/blog/2014/08/14/find-fix-memory-leaks-java-application/

# Gestion de versions Git - Facultatif  
Git est intégré à Eclipse, mais je préfère utiliser SourceTree.
Git est donc intégré à SourceTree, mais afin de pouvoir créer une animation montrant l'évolution du développement
Git est nécessaire en local sous windows
https://git-scm.com/download/win

# ffmpeg - Facultatif  
ffmpeg est convertisseur vidéo gratuit, et est nécessaire pour Gource
Après installation modifier variable d'environnement pour pointer sur ffmpeg : 	**https://ffmpeg.zeranoe.com/builds**/

# Gource - Facultatif  
Outil d'animation du projet : **http://gource.io/**
Attention, durant la génération de la vidéo, la fenêtre doit restée ouverte : **https://github.com/acaudwell/Gource/wiki/Video**

# Conversion Jar vers .Exec
Launch4j est une application qui permet de créer des "lanceurs" *(exécutables Windows classiques)* pour des applications développées en Java.  
Se trouve : **http://launch4j.sourceforge.net/**  
Pour avoir une version standalone (Java bundled) il faut que Java soit installé dans un répertoire relatif.  
Je conseille d'utiliser JDK, et de le copier sous: \workspace\pac-tool\inno\jar  
Pour ce faire, et comme Oracle ne fournit plus des versions standalone, il faut procéder comme suite  
Récupérer la version java sous : http://www.oracle.com/technetwork/java/javase/downloads/index.html  
Ref: https://bgasparotto.com/convert-jdk-exe-zip/  

	Run 7-Zip, .exe
	Open the prompt, go to the directory where the above content were extract and 
	run the commands below, to extract once again the content we need:
	cmd
		cd jdk-8u..-windows-x64\.rsrc\1033\JAVA_CAB10
		extrac32 111
	A file named tools.zip is going to show up inside this directory, 
	extract its contents with 7-Zip to get a tools folder:
	Copy this files in : \workspace\pac-tool\inno\jrex64 or \workspace\pac-tool\inno\jrex86	

Utilisation de launch4j   
Charger la configuration qui se trouve sous: ..\workspace\pac-tool\inno

# Inno : Installateur Windows 
Inno permet de créer un installeur Windows afin de distribuer et installer PAC-TOOL: **http://www.jrsoftware.org/isinfo.php **
	
# wxMaxima - Facultatif  
Outil de calcul symbolique.  
Très utile pour la simplification des expressions mathématiques : **http://andrejv.github.io/wxmaxima/index.html**

# Autres

## Conseils
Pour le test final, préférer la méthode en ligne de command : **java -jar PAC-Tool.java*

## Introduction au Java
OpenClassRooms est une excellente introduction au Java : **https://openclassrooms.com/courses/apprenez-a-programmer-en-java **  
D'autres références sont données au niveau du répertoire: 
..\pac-tool\dev_help

## Youtube
La chaine de diffusion Youtube est sous le compte: christian.klugesherz@geosolterm.fr
