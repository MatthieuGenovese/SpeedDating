# Speed Dating : programme d'aide à la décision

## 1. Prérequis
  
Pour pouvoir compiler / exécuter le programme il faut tout d'abord télécharer le solveur d'ibm :  
https://www-01.ibm.com/software/websphere/products/optimization/cplex-studio-community-edition/__  

Ainsi qu'avoir le jdk java d'installé sur sa machine.  
  
  

## 2. Setup des variables d'environement

### 1. Sous Windows

Accèdez aux variables d'environement système et cherchez celle qui s'apelle LD_LIBRARY_PATH  
Ajouter lui le chemin suivant : "InstallationILOG"\opl\bin\x64_win64    
pour reprendre mon example précédent, le mien est localisé à : E:\ilog\opl\bin\x64_win64  

### 2. Sous Linux / MAC

ouvrez un terminal et tappez cette commande :  
export LD_LIBRARY_PATH="InstallationILOG"/opl/bin/x86-64_linux:$LD_LIBRARY_PATH  
pour encore donner un example, sur mon linux j'ai tappé :  
export LD_LIBRARY_PATH=/opt/ibm/ILOG/CPLEX_Studio_Community127/opl/bin/x86-64_linux:$LD_LIBRARY_PATH  
  
  
## 3. Création des configurations
  

Il va falloir créer et exécuter dans l'ordre 3 configurations maven:  

### 1ère commande
  
install:install-file -DgroupId=com.ibm -DartifactId=opl -Dversion=12.6.3.0 -Dpackaging=jar -Dfile="chemin de votre lib oplall.jar"  
Par exemple pour moi le chemin était E:\ilog\opl\lib\oplall.jar, (à écrire sans les guillemets dans la commande)  


### 2ème commande
  
clean package

### 3ème commande
  
exec:java  

Attention la 1ère commande n'est a exécuter qu'une seule fois à la première installation du programme.
Voila le programme est prêt à être exécuté.    

Si vous rencontrez l'erreur "cant find modele4.lp" sous linux / MAC ouvrez le fichier CalculMatrice.java, faites une recherche de "modele4.lp" et remplassez les "\" par des "/" dans le chemin du fichier  
