#!/usr/bin/env bash

# Descartamos cualquier cambio hecho en git
git add -A
git reset --hard

# Actualizamos en proyecto
git pull origin master

# Copiamos el fichero de configuración de servidor como si fuese el local
cp /home/dsa3/exam-project/service/src/main/resources/beeter.properties.prod /home/dsa3/exam-project/service/src/main/resources/beeter.properties

# Compilamos el proyecto
mvn clean compile assembly:single

# Ahora sólo falta reiniciar el servidor
runuser -l root -c 'service exam-service restart'
