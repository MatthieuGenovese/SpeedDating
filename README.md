# TER-M1-Genovese-Lefevre-Ditmann-Curti

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
dans 'command line' écrivez : exec:java
Apply puis ok

Lancez la configuration clean package, quand elle est terminer lancez la config exec:java
Normalement le hello world s'execute :)

Si quand vous pullez vous n'avez pas la lib opl-12.6.3.0.jar, enlevez les .jar de votre gitignore ;)
