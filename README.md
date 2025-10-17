# 🎬 Series - Backend

## 🚀 Description du projet
Ce projet représente la partie **backend** du système **Médias Series**, développé avec **Spring Boot**.  
Il permet la gestion des séries, épisodes, utilisateurs, notes (ratings) et historiques de visionnage.

Le backend expose plusieurs **API REST** pour interagir avec les données, et il s’intègre au **frontend Vite + React**.

---

## 🧩 Technologies utilisées
| Composant | Technologie          |
|------------|----------------------|
| Langage | Java 17              |
| Framework | Spring Boot 3        |
| Build Tool | Maven                |
| Sécurité | JWT (Json Web Token) |
| Base de données | SQLite               |
| Conteneurisation | Docker               |
| Intégration continue | Jenkins              |

---

## ⚙️ Installation et exécution du projet

### 1️⃣ Cloner le projet
Ouvre ton terminal et exécute :
```bash
git clone https://github.com/sarahzahiac/backend.git
cd backend
```

### 2️⃣ Lancer le projet avec Maven
```bash
mvn spring-boot:run
```

### ➡️ Le serveur démarre par défaut sur :
``` bash
http://localhost:8585
```

### ➡️ Container utilisé pour le déploiement :
Container d'Ikram Saidi
``` bash
ip 10.10.2.134
pass : 6250772
```

## 🧾 Commandes utiles Maven
| Action               | Commande            |
|----------------------|---------------------|
| Lancer le projet     | mvn spring-boot:run |
| Nettoyer le projet   | mvn clean           |
| Vérifier et compiler | mvn verify          |


---

### 📘 Documentation JavaDoc
Pour générer la documentation JavaDoc:
```bash
mvn javadoc:javadoc
```

### ➡️ Puis ouvre le fichier :
```bash
target/reports/apidocs/index.html
```
### 🐳 Docker
- Arrêter/Supprimer les conteneurs
```bash
docker compose down -v
```
- Reconstruire les images Docker
```bash
docker compose build
```
- Lancer tous les services
```bash
docker compose up -d
```

### Liens
- 📚 [Documentation JavaDoc](target/reports/apidocs/index.html)
`target/reports/apidocs/index.html`
- ⚙️ Documentation du Stack : STACK_DOC.md

## 👥 Équipe de développement
| Nom               | Rôle                                      |
|-------------------|-------------------------------------------|
| Rachel Silencieux | Développement backend + documentation     |
| Sarah Charef      | Développement backend                     |
| Ikram Saidi       | Développement backend + tests unitaires                    |
| Aya Issa          | Développement frontend + authentification |

---

Projet d'équipe - Cégep Marie-Victorin

Cours : Maintenance de logiciel
