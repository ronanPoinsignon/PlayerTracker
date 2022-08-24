# PlayerTracker

Application Windows en java permettant d'être averti lorsqu'un joueur commence une partie de League of Legends.

## Utilisation

Entrez simplement le pseudo de la personne que vous voulez suivre. Vous serez notifié lorsque celle-ci commencera une partie.

Vous pouvez accompagner le pseudo d'un nom pour ne pas oublier qui est la personne que vous suivez dans le cas où cette dernière change de pseudo.

## Main

La classe principale de l'application est dans appli.App.java .

## Release

Un dossier zippé `apps.zip` est fourni dans chaque release à partir de la 1.7.0. Il contient une fois dézippé une application java sous forme de JAR, un exécutable et un dossier JRE. Il est important de laisser l'exécutable dans le même dossier que le dossier jre.

 Ce dossier apps peut être créé en suivant les étapes décrites dans la partie Installation ci-dessous.

## Installation

Pour installer l'application, vous devez être sur une version de java supérieure ou égale à java 11.

> Vous pouvez vous rendre sur [ce lien](https://www.oracle.com/fr/java/technologies/javase/jdk11-archive-downloads.html) pour télécharger la version 11. Prenez la version `jdk-11.X.Y_windows-x64_bin.exe` et lancez l'exécutable pour installer java sur votre ordinateur.

Ensuite, faites simplement "mvn install" dans un invite de commande à la racine du projet pour build les fichiers finaux, qui sont placés dans le dossier target/apps.

> Si la commande renvoie "command not found" comme résultat, vous pouvez télécharger maven [ici](https://maven.apache.org/download.cgi). **Téléchargez le fichier bin.zip.**

> Pour utiliser mvn dans n'importe quel dossier de votre pc, vous pouvez l'ajouter en variable d'environnement dans le PATH. Un tuto est disponible [ici](https://www.architectryan.com/2018/03/17/add-to-the-path-on-windows-10/). Dans le cas contraire, vous devrez donner le chemin absolu (ex : `C:\Program Files\apache-maven-3.8.4\bin\mvn install`).

Les fichiers à utiliser sont ceux-ci :
* PlayerTracker-jar-with-dependencies.jar
* PlayerTracker.exe