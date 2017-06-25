# Speed Dating : programme d'aide à la décision

## 1. Téléchargement du solveur
__
Pour pouvoir compiler / exécuter le programme il faut tout d'abord télécharer le solveur d'ibm :__
https://www-01.ibm.com/software/websphere/products/optimization/cplex-studio-community-edition/__


## 2. Création des configurations
__

Il va falloir créer et exécuter dans l'ordre 3 configurations maven:__

### 1ère commande
__
install:install-file -DgroupId=com.ibm -DartifactId=opl -Dversion=12.6.3.0 -Dpackaging=jar -Dfile="chemin de votre lib oplall.jar"__
Par exemple pour moi le chemin était E:\ilog\opl\lib\oplall.jar, (à écrire sans les guillemets dans la commande)__


### 2ème commande
__
clean package

### 3ème commande
__
exec:java

Attention la 1ère commande n'est a exécuter qu'une seule fois à la première installation du programme.



Ensuite pour ceux qui sont sur windows, click droit sur poste de travail > propriétés > Paramètres système avancés > variables d'environement

La dans la liste des variables SYSTEMES (et non pas utilisateur) vous faite nouvelle vous l'appellez OPL_LIB_PATH
Comme chemin vous cherchez le chemin du fichier oplall.jar de votre installation cplex
(par exemple le mien était E:\ilog\opl\lib\oplall.jar)
