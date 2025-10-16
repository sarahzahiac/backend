# Utilise une image Java legere
FROM openjdk:17-jdk-slim
 
# Definit le repertoire de travail dans le conteneur
WORKDIR /app
 
# Copie le fichier JAR compile dans le conteneur
COPY target/*.jar app.jar
 
# Copie aussi le dossier data (pour que people.csv et people.db soient dans l image)
COPY data /app/data
 
# Expose le port de l application Spring Boot
EXPOSE 8585
 
# Commande pour demarrer ton backend
ENTRYPOINT ["java", "-jar", "app.jar"]
