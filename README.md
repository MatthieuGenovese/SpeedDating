# TER-M1-Genovese-Lefevre-Ditmann-Curti


*** TEMPORAIRE ***

Si vous voulez quand même exécuter correctement le projet en attendant que le maven fonctionne :
Créer une nouvelle configuration d'éxecution, choisissez "Application" au lieu de "Maven", "Main class" vous mettez sample.Main
et Working directory le repertoire de votre dossier TER
Normalement avec cette configuration, le programme s'exécute normalement (modèle OPL + javaFX)

**********************



Pour initialiser vos environements correctement :

New froject from VCS : vous coller le lien du git

Ensuite si vous etes sous intellij :
Click droit sur le projet Add Framework support... >>> Maven >>> OK

La ou vous avez la configuration (en haut a droite juste a coté de la fleche verte d'execution)

Vous éditez une nouvelle configuration
cliquez sur le '+' vert
Sélectionnez Maven
dans 'command line' écrivez : clean package
Apply puis ok

Recréez encore une configuration
Sélectionnez Maven
dans 'command line' écrivez : compile exec:java
Apply puis ok

Ensuite pour ceux qui sont sur windows, click droit sur poste de travail > propriétés > Paramètres système avancés > variables d'environement

La dans la liste des variables SYSTEMES (et non pas utilisateur) vous faite nouvelle vous l'appellez OPL_LIB_PATH
Comme chemin vous cherchez le chemin du fichier oplall.jar de votre installation cplex
(par exemple le mien était E:\ilog\opl\lib\oplall.jar)


Lancez la configuration clean package, quand elle est terminer lancez la config exec:java
Normalement le hello world s'execute :)
