# ⚙️ Documentation du Stack Technique – Series Backend

## 🧩 Versions et outils utilisés
| Composant | Version |
|------------|----------|
| JDK | 17.0.2 |
| Maven | 3.9.x |
| Spring Boot | 3.5.5 |
| SQLite | 3.45.1.0 |
| Docker | 25.x |
| Jenkins | 2.x |

---

## 🧱 Structure du projet

Voici les principaux dossiers du projet et leur rôle :

| Dossier | Contenu |
|----------|----------|
| `controllers/` | Contient les API REST du backend (`PersonController`, `SeriesController`, `RatingsController`, etc.) |
| `services/` | Logique métier (authentification, recommandations, calculs des moyennes, etc.) |
| `repositories/` | Interfaces qui communiquent avec la base de données via Spring Data JPA |
| `models/` | Entités JPA représentant les tables de la base (`Person`, `Series`, `Episodes`, `Ratings`, `VuesHistory`) |
| `config/` | Contient les classes “Seeder” pour insérer des données initiales |
| `Securiter/` | Configuration de la sécurité Spring Boot (JWT, filtres, accès) |
| `utils/` | Classes utilitaires (génération et validation des tokens JWT) |
| `test/` | Tests unitaires et d’intégration du backend |
---

## 🧰 Commandes Maven et Docker

### 📦 Maven
| Action | Commande |
|--------|-----------|
| Lancer le projet | `mvn spring-boot:run` |
| Nettoyer le projet | `mvn clean` |
| Compiler et tester | `mvn verify` |
| Générer la JavaDoc | `mvn javadoc:javadoc` |

### 🐳 Docker
| Action | Commande  |
|--------|-----------|
| Construire l’image Docker | `docker build -t maintenance-env:base` |
| Lancer le conteneur | `docker run -it maintenance-env:base bash` |

---

## 💾 Base de données
Le projet utilise **SQLite** comme base de données embarquée.

- Le fichier de base de données est créé automatiquement dans le répertoire du projet lors du premier lancement.
- La configuration manuelle est optionelle.
- Les données initiales sont insérées via les classes `Seeder` (par ex. `RatingsSeeder`, `EpisodeSeeder`, etc.).

---

## ⚙️ Stack complet

- **Backend :** Spring Boot 3 (Java)
- **Frontend :** React + Vite
- **Base de données :** SQLite
- **Sécurité :** JWT (Json Web Token)
- **Build Tool :** Maven
- **Conteneurisation :** Docker
- **Intégration continue :** Jenkins

---

### 🧰 Makefile

| Action | Commande                        |
|--------|---------------------------------|
| `make build` | Compile le projet avec Maven    |
| `make test` | Lance les tests unitaires       |
| `make run` | Exécute `mvn spring-boot:run`   |
| `make doc` | Génère la documentation JavaDoc |

---

## 💡 Bonnes pratiques d’usage

- ✅ **Convention de noms** :
    - Conteneur Docker : ``
    - Image Docker : ``
- ✅ **Sécurité** :
    - Mots de passe hachés avec BCrypt avant sauvegarde
    - Authentification via tokens JWT
- ✅ **Git workflow** :
    - `main` → version stable
    - `dev` → version de développement
    - `feature/*` → nouvelles fonctionnalités
- ✅ **Documentation** :
    - Chaque classe publique doit avoir un commentaire JavaDoc
    - Documentation générée dans `/target/site/apidocs`

---

## 🔗 Liens utiles
- 📚 [Documentation JavaDoc](target/reports/apidocs/index.html)
  `target/reports/apidocs/index.html`
- 📘 [README principal](README.md)

---

## 👥 Équipe de développement
| Nom               | Rôle                                      |
|-------------------|-------------------------------------------|
| Rachel Silencieux | Développement backend + documentation     |
| Sarah Charef      | Développement backend                     |
| Ikram Saidi       | Développement backend + tests unitaires                     |
| Aya Issa          | Développement frontend + authentification |

---

## 🧾 Informations
Projet d'équipe - Cégep Marie-Victorin

Cours : Maintenance de logiciel 
