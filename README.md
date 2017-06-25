# Speed Dating : programme d'aide à la décision


Pour pouvoir compiler / exécuter le programme il faut tout d'abord télécharer le solveur d'ibm :
https://www-01.ibm.com/software/websphere/products/optimization/cplex-studio-community-edition/


## 1. Création des configurations

Il va falloir créer et exécuter dans l'ordre 3 configurations maven:

### 1ère commande
install:install-file -DgroupId=com.ibm -DartifactId=opl -Dversion=12.6.3.0 -Dpackaging=jar -Dfile="chemin de votre lib oplall.jar"
Par exemple pour moi le chemin était E:\ilog\opl\lib\oplall.jar, (à écrire sans les guillemets dans la commande)


### 2ème commande
clean package

### 3ème commande
exec:java

Attention la 1ère commande n'est a exécuter qu'une seule fois à la première installation du programme.



Ensuite pour ceux qui sont sur windows, click droit sur poste de travail > propriétés > Paramètres système avancés > variables d'environement

La dans la liste des variables SYSTEMES (et non pas utilisateur) vous faite nouvelle vous l'appellez OPL_LIB_PATH
Comme chemin vous cherchez le chemin du fichier oplall.jar de votre installation cplex
(par exemple le mien était E:\ilog\opl\lib\oplall.jar)
