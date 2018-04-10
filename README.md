# PAC-Tool
Outil pour Pompe à Chaleur

# Définition 
Outil qui permet de vérifier les données constructeur (COP) et de calculer le COP de votre pompe à chaleur à partir de vos mesures.  
*(Programme écrit en Java)*

# Chaîne de Développement
La chaîne d'outil pour le développement est la suivante:  
*Je donne ci-dessous l'ordre dans lequel je vous conseille de procéder à l'installation de la chaîne de développement.*

# GitHub
Service Web d'hébergement et de gestion de développement de logiciels se trouve [ici](https://github.com/ckl67/PAC-Tool)  
Projet sous: PAC-Tool

## Création Compte GitHub
Si vous souhaitez participer au développement du projet  
La première chose va consister à créer un compte sur [GitHub](https://github.com/) afin que je puisse vous ajouter au projet.    
Une fois cette opération faite, il faudra me contacter à travers <christian.klugesherz@gmail.com> afin que je vous autorise à participer au projet.

# Java
Installation de [Java](http://www.oracle.com/technetwork/java/javase/downloads/index.html)  
Choisir:   
* Java Platform, Standard Edition
* Version minimale: Java SE 8u161/ 8u162
* Utiliser JDK

# SourceTree
[SourceTree](https://www.sourcetreeapp.com/) est un client graphique clair et structuré, compatible avec Git : 
Lors de l'installation de SourceTree, il faudra  
* Créer un compte Altassian, ou utiliser un compte Google
* Choisir GitHub 

Par la suite, L'outil va
* télécharger automatiquement la version embarquée de Git
* télécharger la version embarquée de Mercudial

Afin de créer l'espace de travail sur votre PC, et obtenir une copie du dépôt (PAC-TOOL)  *(= Clone)*  
* Pour la configuration du **clone**, il faudra choisir le répertoire : <u>\Users\<votre compte windows>\workspace</u>

# Eclipse: Environnement de développement intégré
[Eclipse IDE](https://www.eclipse.org/downloads/eclipse-packages/) *(Integrated Development Environment)* for Java Developers
* Version minimale: Oxygene Release
* Choisir: Eclipse Installer
* Lors de l'installation choisir: "Eclipse IDE for Java EE Developers"
* Une fois installer, faire pointer sur le répertoire : <u>\Users\<votre compte windows>\workspace</u>

## Attention !
Dans le cas ou Java est mis à jour, il est possible que Eclipse ne démarre plus. En fait le chemin de Java JRE a changé, et Eclipse ne pas peut pas connaître le nouveau répertoire.  
Pour corriger cela, il suffit d'éditer le fichier "eclipse.ini"  
Ce fichier se trouve sous: ....\eclipse\java-oxygen\eclipse

## Add-ons intégrés à Eclipse (IDE)
### WindowBuilder
Permet de créer des interface graphique  
Sous Eclipse, à travers Help/Marketplace installer : **WindowBuilder**   

### Editeur HTML
Editeur HTML, *(non wysiwyg)* 
Cette opération est uniquement nécessaire dans le cas ou la version Eclipse installée est:  "Eclipse IDE for Java Developers"
Sous Eclipse, à travers Help/Marketplace installer : **HTML Editor WPT**  
A l'installation, il pourrait y avoir un conflit avec XML, dans ce cas je vous conseille de choisir : "Keep My installation the same"  
*(Pour l'instant je n'ai pas réussi à trouver un éditeur HTML wysiwyg)*

### Langage de modélisation unifié (UML)
Unified Modeling Language (UML) permet de voir l'arborescence des classes  
Utilisation de ObjectAid
```txt
Installer uniquement les modules ne nécessitant pas de licences.
Au niveau Eclipse dans Install New Software
Work with: "http://www.objectaid.com/update/current"
Name: ObjectAid UML Explorer
```

### log4j Logger
Log4j est un Plugin pour permettre le logging 
Il n'y a rien à faire ici. Les .jar files font déjà partis du projets.
C'est uniquement dans le cas de mise à jour qu'il faudra copier les fichiers au niveau du répertoirelib du projet    
Principe  
log4j doit être téléchargé sur le site [officiel](https://logging.apache.org/log4j/2.x/download.html)
* Apache "Log4j 2 binary (zip)" --> choisir : log4j-api-2.x.y.jar et log4j-core-2.x.y.jar)
* [Utilisation](http://logging.apache.org/log4j/2.x/manual/api.html)

### JSON
Afin de pouvoir travailler avec du JSON, nous utilisons le fichier jar: json-simple  
Il n'y a rien à faire ici. Les .jar files font déjà partis du projets.
Cette opération est nécessaire que dans le cas d'une mise à jour de json-simple  
Pour plus d'explication voir [ici](https://www.tutorialspoint.com/json/json_java_example.htm)

# Éditeur HTML WYSIWYG
Pas de solution satisfaisante trouvée. Je reste sur la solution éditeur de texte.

# Éditeur Markdown
Markdown est un langage de balisage. Son but est d'offrir une syntaxe facile à lire et à écrire. Il y a un éditeur qui est intégrée à Eclipse, mais je préfère l'édituer [Markdown Monster](https://markdownmonster.west-wind.com/) 

# Fuite mémoire
Afin de trouver les fuites mémoires, il est conseillé d'activer les paramètres de vérification code au niveau Eclipse [voir ici](https://developers.redhat.com/blog/2014/08/14/find-fix-memory-leaks-java-application/)
*(par défaut, il y a des warning, que l'on peut passer en Error au niveau de la configuration du projet)*  

Un outil très intéressant est: [visualvm](https://visualvm.github.io/download.html)

Une introduction à l'outil peut se trouver [ici](https://developers.redhat.com/blog/2014/08/14/find-fix-memory-leaks-java-application/)

# Gestion de versions Git - Facultatif  
[Git](https://git-scm.com/download/win) est intégré à Eclipse, mais je préfère utiliser SourceTree.  
Git est donc intégré à SourceTree.
Mais afin de pouvoir créer une animation montrant l'évolution du développement, Git est nécessaire en local sous Windows

# ffmpeg - Facultatif  
[ffmpeg](https://ffmpeg.zeranoe.com/builds) est convertisseur vidéo gratuit, et est nécessaire pour Gource  
Après installation modifier variable d'environnement pour pointer sur ffmpeg

# Gource - Facultatif  
[Gource](http://gource.io/) Outil d'animation du projet :  
Attention, durant la génération de la vidéo, la fenêtre doit restée ouverte *([voir ici](https://github.com/acaudwell/Gource/wiki/Video))*

# Conversion Jar vers .Exec
[Launch4j](http://launch4j.sourceforge.net/) est une application qui permet de créer des "lanceurs" *(exécutables Windows classiques)* pour des applications développées en Java.  
Il n'y a rien à faire ici. Les fichiers font déjà partis du projets.

Pour avoir une version standalone *(Java bundled)* il faut que Java soit installé dans un répertoire relatif.  
Je conseille d'utiliser JDK, et de le copier sous: \workspace\pac-tool\inno\jar  
Pour ce faire, et comme Oracle ne fournit plus des versions standalone, il faut procéder comme [suite](https://bgasparotto.com/convert-jdk-exe-zip/)

Récupérer la version java [ici](http://www.oracle.com/technetwork/java/javase/downloads/index.html)   


```txt
   Run 7-Zip, .exe

Open the prompt, go to the directory where the above content were extract and 
run the commands below, to extract once again the content we need:

   cmd
   cd jdk-8u..-windows-x64\.rsrc\1033\JAVA_CAB10
   extrac32 111

A file named tools.zip is going to show up inside this directory, 
extract its contents with 7-Zip to get a tools folder:
Copy this files in : 
   \workspace\pac-tool\inno\jrex64 
  or 
   \workspace\pac-tool\inno\jrex86
```


Utilisation de launch4j   
Charger la configuration qui se trouve sous: ..\workspace\pac-tool\inno

# Inno : Installateur Windows 
[Inno](http://www.jrsoftware.org/isinfo.php) permet de créer un installeur Windows afin de distribuer et installer PAC-TOOL: 
	
# wxMaxima - Facultatif  
[wxMaxima](http://andrejv.github.io/wxmaxima/index.html) est un outil de calcul symbolique.  
Très utile pour la simplification des expressions mathématiques.  
Télécharcher un Tutoriel sur Maxima
[ici](https://raw.githubusercontent.com/ckl67/PAC-Tool/master/blob/img/Maxima/tutoriel_maxima.pdf) 

# Autres

## Conseils
Pour le test final, préférer la méthode en ligne de commande :

```txt
java -jar PAC-Tool.java
```

## Introduction au Java
[OpenClassRooms](https://openclassrooms.com/courses/apprenez-a-programmer-en-java) est une excellente introduction au Java   

## Youtube
La chaîne de diffusion Youtube est sous le compte: christian.klugesherz@geosolterm.fr
