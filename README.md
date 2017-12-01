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
Si vous souhaitez participer au d�veloppement du projet  
La premi�re chose va consister � cr�er un compte sur : **https://github.com/** afin que je puisse vous ajouter au projet.    
Une fois cette op�ration faite, il faudra me contacter afin que je vous autorise � participer au projet.

# Java
Installation de Java : **http://www.oracle.com/technetwork/java/javase/downloads/index.html**  
Choisir:   
* Java Platform, Standard Edition
* Version minimale: jre8u111
* Utiliser JDK

# SourceTree
SourceTree est un client graphique clair et structur�, compatible avec Git : **https://www.sourcetreeapp.com/**  
Lors de l'installation de SourceTree, il faudra  
* Cr�er un compte Altassian, ou utiliser un compte Google
* Choisir GitHub 
 
Par la suite, L'outil va
* t�l�charger automatiquement la version embarqu�e de Git
* t�l�charger la version embarqu�e de Mercudial

Afin de cr�er l'espace de travail sur votre PC, et obtenir une copie du d�p�t (PAC-TOOL) Git existant *(= Clone)*  
* Pour la configuration du **clone**, il faudra choisir le r�pertoire : <u>\Users\<votre compte windows>\workspace</u>
	
# Eclipse: Environnement de d�veloppement int�gr�
Eclipse IDE *(Integrated Development Environment)* for Java Developers : **https://www.eclipse.org/downloads/eclipse-packages/**
* Version minimale: Oxygene Release
* Choisir: Eclipse Installer
* Lors de l'installation choisir: Eclipse IDE for Java Developers
* Une fois installer, faire pointer sur le r�pertoire : <u>\Users\<votre compte windows>\workspace</u>

## Add-ons int�gr�s � Eclipse (IDE)
### WindowBuilder
Permet de cr�er des interface graphique  
Sous Eclipse, � travers Help/Marketplace installer : **WindowBuilder**   

### Editeur HTML
Editeur HTML, *(non wysiwyg)*  
Sous Eclipse, � travers Help/Marketplace installer : **HTML Editor WPT**  
A l'installation, il pourrait y avoir un conflit avec XML, dans ce cas je vous conseille de choisir : "Keep My installation the same"  
*(Pour l'instant je n'ai pas r�ussi � trouver un �diteur HTML wysiwyg)*

### Langage de mod�lisation unifi� (UML)
Unified Modeling Language (UML) permet de voir l'arborescence des classes  
ObjectAid
* Installer uniquement les modules ne n�cessitant pas de licences.
* Au niveau Eclipse dans Install New Software
* Work with: **http://www.objectaid.com/update/current**
* Name: ObjectAid UML Explorer

### log4j Logger
Log4j est un Plugin pour permettre le logging 
Il n'y a rien � faire ici. Les .jar files font d�j� partis du projets.
C'est uniquement dans le cas de mise � jour qu'il faudra copier les fichiers au niveau du r�pertoirelib du projet    
Principe  
Doit �tre t�l�charg� sur le site officiel : **https://logging.apache.org/log4j/2.x/download.html** 
* Apache "Log4j 2 binary (zip)" --> choisir : log4j-api-2.x.y.jar et log4j-core-2.x.y.jar)
* Utilisation: http://logging.apache.org/log4j/2.x/manual/api.html

### JSON
Afin de pouvoir travailler avec du JSON, nous utilisons le fichier jar: json-simple  
Ici encore une fois, cette op�ration est n�cessaire que dans le cas d'une mise � jour de json-simple  
Pour plus d'explication voir : **https://www.tutorialspoint.com/json/json_java_example.htm**	

# Fuite m�moire
Afin de trouver les fuites m�moires, il est conseill� d'activer les param�tres de v�rification code au niveau Eclipse    
*(par d�faut, il y a des warning, que l'on peut passer en Error au niveau de la configuration du projet)*  
voir: https://developers.redhat.com/blog/2014/08/14/find-fix-memory-leaks-java-application/  

Un outil tr�s int�ressant est: visualvm : **https://visualvm.github.io/download.html**  
Introduction : https://developers.redhat.com/blog/2014/08/14/find-fix-memory-leaks-java-application/

# Gestion de versions Git - Facultatif  
Git est int�gr� � Eclipse, mais je pr�f�re utiliser SourceTree.
Git est donc int�gr� �SourceTree, mais afin de pouvoir cr�er une animation montrant l'�volution du d�veloppement
Git est n�cessaire en local sous windows
https://git-scm.com/download/win

# ffmpeg - Facultatif  
ffmpeg est convertisseur vid�o gratuit, et est n�cessaire pour Gource
Apr�s installation modifier variable d'environnement pour pointer sur ffmpeg : 	**https://ffmpeg.zeranoe.com/builds**/

# Gource - Facultatif  
Outil d'animation du projet : **http://gource.io/**
Attention, durant la g�n�ration de la vid�o, la fen�tre doit rest�e ouverte : **https://github.com/acaudwell/Gource/wiki/Video**

# Conversion Jar vers .Exec
Launch4j est une application qui permet de cr�er des "lanceurs" *(ex�cutables Windows classiques)* pour des applications d�velopp�es en Java.  
Se trouve : **http://launch4j.sourceforge.net/**  
Pour avoir une version standalone (Java bundled) il faut que Java soit install� dans un r�pertoire relatif.  
Je conseille d'utiliser JDK, et de le copier sous: \workspace\pac-tool\inno\jar  
Pour ce faire, et comme Oracle ne fournit plus des versions standalone, il faut proc�der comme suite  
R�cup�rer la version java sous : http://www.oracle.com/technetwork/java/javase/downloads/index.html  
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
Inno permet de cr�er un installeur Windows afin de distribuer et installer PAC-TOOL: **http://www.jrsoftware.org/isinfo.php **
	
# wxMaxima - Facultatif  
Outil de calcul symbolique.  
Tr�s utile pour la simplification des expressions math�matiques : **http://andrejv.github.io/wxmaxima/index.html**

# Autres

## Conseils
Pour le test final, pr�f�rer la m�thode en ligne de command : **java -jar PAC-Tool.java*

## Introduction au Java
OpenClassRooms est une excellente introduction au Java : **https://openclassrooms.com/courses/apprenez-a-programmer-en-java **  
D'autres r�f�rences sont donn�es au niveau du r�pertoire: 
..\pac-tool\dev_help

## Youtube
La chaine de diffusion Youtube est sous le compte: christian.klugesherz@geosolterm.fr
