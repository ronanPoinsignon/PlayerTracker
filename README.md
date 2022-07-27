# PlayerTracker

Application java permettant d'être averti lorsqu'un joueur commence une partie de League of Legends.

## Utilisation

Entrez simplement le pseudo de la personne que vous voulez suivre. Vous serez notifié lorsque celle-ci commencera une partie.

Vous pouvez accompagner le pseudo d'un nom pour ne pas oublier qui est la personne que vous suivez dans le cas où cette dernière change de pseudo.

## Main

La classe principale de l'application est dans appli.App.java .

## Installation

Pour installer l'application, vous devez être sur la version 11.0.14 de java (jdk comme jre).

> La bonne version de java est accessible via [ce lien](https://www.oracle.com/fr/java/technologies/javase/jdk11-archive-downloads.html).

Faites simplement "mvn install" dans un invite de commande à la racine du projet pour build les fichiers finaux, qui sont placés dans le dossier target.

> Si la commande renvoie "command not found" comme résultat, vous pouvez télécharger maven [ici](https://maven.apache.org/download.cgi). **Téléchargez le fichier bin.zip.**

> Pour pouvoir utiliser mvn dans n'importe quel dossier de votre pc, vous devez l'ajouter en variable d'environnement dans le PATH. Un tuto est disponible [ici](https://www.architectryan.com/2018/03/17/add-to-the-path-on-windows-10/).

Les fichiers à utiliser sont ceux-ci :
* PlayerTracker-jar-with-dependencies.jar
* PlayerTracker.exe