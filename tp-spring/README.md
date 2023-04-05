
# Préambule

Le sujet est tout neuf et contient certainement des erreurs. N'hésitez pas à me les indiquer. **Vous devez terminer le TP précédent avant chaque nouvelle séance**.

N'hésitez pas à nous demander des explications sur des concepts Java/POO que vous ne comprenez pas.


# Compétences visées

- Créer un back-end Java avec Spring

- Créer une API REST en Spring

- Marshaller et démarshaller des données de manière adéquate (DTO)

- Tester une API REST de manière manuelle et automatique (JUnit, Postman, curl)

- Mettre en place une authentification

- Se prémunir de certaines attaques informatiques

- Écrire et traiter des requêtes REST en JavaScript


# Sujet

Le sujet des TP concerne la création d'un back-end pour réaliser des opérations CRUD pour des todos.

```mermaid
classDiagram
    class TodoList {
        - id: long
        - name: string
        - owner: string
    }
    class Todo {
        - id: long
        - title: string
        - owner: string
        - description: string
    }
    class Category {
        <<enumeration>>

        HIGH_PRIORITY
        LOW_PRIORITY
        WORK
        ENTERTAINMENT
    }
    Todo "* todos" --* "1 list" TodoList
    Category "* categories" <-- Todo
```

Les attributs `id` sont les identifiants uniques des objets.


# Prérequis logiciels

- Les vrais sont sous Linux. En ce qui concerne les autres, vous pouvez toujours vous y mettre.

- Vérifier sa version de (Java 17) :
`java -version`

- Vérifier que Maven est installé (Maven 3) : `mvn -v`

- Vous devriez pouvoir utiliser l'instance de Swagger Editor en ligne : https://editor.swagger.io<br/>
Vous pouvez aussi installer Swagger Editor sur votre machine : https://github.com/swagger-api/swagger-editor<br/>
Pour cela, le plus simple est d'installer docker et de lancer les commandes suivantes (mettre un `sudo` devant chaque commande au besoin) :
```
docker pull swaggerapi/swagger-editor
docker run -d -p 1024:8080 swaggerapi/swagger-editor
```
et dans votre navigateur aller sur la page http://localhost:1024.
À tout moment vous pouvez retrouver votre instance docker Swagger avec `docker ps -a`. La première colonne affichée vous indique l'ID de l'instance. Vous pouvez la stopper ou la redémarrer (à chaque début de TP) avec `docker start <id>` et `docker stop <id>`


- Avoir IntelliJ ou VisualCode<br/>

- Cloner our télécharger le dépôt du cours : https://github.com/arnobl/WebEngineering-INSA/<br/>
L'exemple du cours se trouve dans : `rest/springboot2`<br/>
Le projet à utiliser pour le TP se trouve dans : `tp-spring`

- Avec IntelliJ, pour charger le projet du TP : `open` → aller chercher le fichier `pom.xml` du projet → *Open as project* → *Trust Project*. <br/>
Avec VSCode, faites *Ouvrir un dossier*.


# TP 1


## Q1.1

Lancer le back-end en allant dans `Application.java` et en lançant le `main`.

- Dans Swagger Editor (https://editor.swagger.io ou `http://localhost:1024` si vous utilisez une version sur votre machine), supprimer le contenu afficher et ajouter simplement :
```yaml
openapi: 3.0.3
info:
  title: TP Web INSA Rennes
  description: |-
    Intro au dev d'un back-end REST en Java avec Spring et OpenAPI
  version: 1.0.0
servers:
  - url: http://localhost:8080/api/v1

tags: # Some annotations used to document the route descriptions (optional)
  - name: todo
    description: Les todos
paths:
    /public/todo/hello:
        get:
            tags:
                - todo
            responses:
              '200':
                description: c'est bon
```

- Exécutez cette commande REST avec `Try it out` -> `Execute`.

- Cette route est déjà codée dans le contrôleur REST `TodoControllerV1` (package `web.controller`). Regardez cette classe.

## Q1.2

- Dans votre navigateur, entrez l'URL `http://localhost:8080/api/v1/public/todo/hello`<br/>
Pourquoi la barre d'adresse de votre navigateur sait-elle gérer une requête REST GET ? Est-elle aussi capable de gérer un POST ?


- Affichez la console de développement de votre navigateur. Allez dans l'onglet réseau et rafraîchissez la page. Vous devriez pouvoir observer la requête et ses détails.


## Q1.3 Get OpenAPI

- Dans votre Swagger Editor, ajouter dans le contrôleur `todo` (tag `todo`) une route REST `todo` (`GET`) qui retourna au format JSON une instance de la classe `Todo`. Inspirez vous de l'exemple OpenAPI du cours : https://github.com/arnobl/WebEngineering-INSA/blob/master/rest/openapi.yaml
Notamment, vous aurez besoin de définir et d'utiliser le schéma de l'objet retourné (le `Todo`). En grand prince je vous le donne pour cette fois :
```yaml
components:
  schemas:
    Todo:
      type: object
      properties:
        id:
          type: integer
          format: int64
          example: 10
        title:
          type: string
        description:
          type: string
        categories:
          type: array
          items:
            type: string
```
Tester que la commande ne fonctionne pas.

## Q1.4 Get Spring

- Codez cette requête dans votre contrôleur REST (il faudra redémarrer le back-end). L'instance retournée sera `new Todo(1, "A title", "desc", List.of(Category.ENTERTAINMENT, Category.WORK), null, "foo")`
- Tester à nouveau dans Swagger Editor. Vous pouvez voir que le format du JSON reçu ne correspond pas à celui attendu (celui défini dans Swagger Editor). Nous verrons cela plus tard avec les DTO.
- Tester dans le navigateur


## Q1.5 Post v1

- Créer une route REST `POST` `todo` (dans Swagger Editor puis dans votre projet Spring) qui recevra un objet `Todo` (en JSON, `consumes`) avec les données que vous voulez. Le type de retour de la route sera `void` (code 200).
- La route affichera pour l'instant juste cet objet (`System.out.println(...)`).
**Attention :** la sortie de `println` sera visible dans la console d'IntelliJ (et non dans votre navigateur).
- Tester avec Swagger Editor


## Terminer le TP pour la séance d'après

**Et sauvegarder votre openAPI de Swagger Editor !**


# TP 2

## Q2.1 Post v2

Dans les questions précédentes, nous ne sauvegardions pas les todos crée par la commande `POST`, et ne gérions pas l'identifiant unique.

- Dans le contrôleur REST, ajoutez un attribut `cpt` (type `int`) qui sera incrémenté à chaque nouveau todo et utilisé comme identifiant du nouveau todo. Modifiez la route `POST` en conséquence. Cette pratique n'est pas propre du tout. Nous verrons plus tard comment faire cela de manière correcte.

- Étant donné que les objets todo à stocker ont une clé unique et que nous voudrions certainement chercher en fonction de cet id, quel serait la structure de donnée adequate à utiliser ici ? Toujours dans le contrôleur, ajoutez un attribut `todos` dont le type sera la structure identifiée.
La route `POST` ajoutera le todo crée dans cette structure et retournera le todo crée. Modifier le Swagger Editor en conséquence. Modifiez le `println` pour qu'il affiche la liste des todos.


Donc, dans cette structure tous les todos doivent avoir un id différent.

## Q2.2 Delete

- Ajouter (dans Swagger Editor et votre code Spring) une route `DELETE` `todo/{id}` qui supprimera le todo dont l'id est celui donné en paramètre de l'URI. Cette route devra alors chercher dans la structure le todo dont l'id est égal à celui du todo passé en paramètre. Si la recherche échoue, alors retourner un code `400` (cf l'exemple *openapi.yaml*). Si elle réussit, vous supprimerez l'objet de la liste des todos.

- Testez avec Swagger Editor.


## Q2.3 Put

- Le `Put` remplace un objet par un autre. C'est une manière de modifier complètement un objet.
Ajoutez une route (dans Swagger Editor et votre code Spring) `PUT` `todo` qui fera cette opération sur un todo. Pour cela vous pouvez copier-coller-adapter la route `POST` car assez proche.

- Testez avec Swagger Editor.



## Q2.3 Patch pas terrible

- Ajoutez une route `PATCH` `bof/todo` (bof, car cette version n'est pas terrible) qui modifiera un todo. Pour cela copier-coller-modifier la route `POST` `todo` car cette première version du patch est assez similaire. Cette route devra alors chercher dans la liste le todo dont l'id est égal à celui du todo passé en paramètre. Si la recherche échoue, alors retourner un code `400` (cf. l'exemple *openapi.yaml*). Si elle réussit, alors vous utiliserez les setters de `Todo`, par exemple :
```java
  if(todo.getPublicDescription() != null) {
    todoFound.setPublicDescription(todo.getPublicDescription());
  }
  //etc.
```

Cette manière de faire le patch souffre de plusieurs défauts importants. Lesquels selon vous ?
Lecture intéressante : https://stackoverflow.com/a/19111046/9649530



## Bilan TP2

Nous avons vu les bases pour coder des routes REST réalisant des opérations CRUD sur un type d'objets (le `Todo`).

Pour l'instant le code de notre back-end a plusieurs défauts majeurs :
- Stockage des objets dans le contrôleur. Ça n'est pas une bonne pratique car comment partager les données entre plusieurs contrôleurs ? Et est-ce le rôle d'un contrôleur de stocker ? Nous utiliserons plus tard un *service*.
- Gestion à la main de l'unicité des objets et stockage peu efficaces des données. Nous utiliserons une base de données et son lien avec le back-end (JPA) plus tard.
- Nous (de-)marshallons directement les objets `Todo` alors que nous voulons que quelques attributs dans certains cas. Nous utiliserons des DTO plus tard.
- Pas de test unitaire (TU) écrit pour l'instant.
- Pas de sécurité : tout le monde pour faire du CRUD sur les objets todo.


## Terminer le TP pour la séance d'après

**Et sauvegarder votre openAPI de Swagger Editor !**



# TP3

## Q3.1 contrôleur V2

- Copiez-collez le contrôleur `TodoControllerV1.java` pour avoir un `TodoControllerV2.java` dont le `RequestMapping` indique `api/v2/public/todo`.
Changez également l'adresse du serveur dans Swagger Editor en conséquence.
Nous travaillerons sur ce nouveau contrôleur avec cette nouvelle URI.


## Q3.2 Service

- Créez un service `TodoListService` et ajoutez un attribut de ce type dans votre nouveau contrôleur avec `@autowired`. Que fait cette annotation ?

- Déplacez les attributs `cpt` et `todos` dans ce service. Cela va vous demandez de modifiez la plupart des routes de votre contrôleur délègue au service toute la logique CRUD des opérations.
Votre service devrait donc avoir les méthodes suivantes :
```java
	public Todo addTodo(final Todo todo) {
	}
  // true if newTodo corresponds to an existing todo
	public boolean replaceTodo(final Todo newTodo) {
	}
// true if id corresponds to an existing todo
	public boolean removeTodo(final int id) {
	}


	public Todo modifyTodo(final Todo partialTodo) {
	}


	private Todo findTodo(final int id) {
  }
```

- Que se passe-t-il si je mets un attribut `@autowired TodoListService...` dans un autre contrôleur ?


Quel sont les avantages d'un service par rapport à nos 2 TP précédents ?
Nous creuserons en 4INFO ce concept d'injection de dépendances (le `@autowired`).


## Q3.3 Repository

Coder un service comme nous l'avons fait dans la question précédente est un peu laborieux (gestion à la main du `cpt`, structure de stockage) : les opérations CRUD sur un objet, c'est du grand classique et Spring fournit un mécanisme pour simplifier cela : les `repository`.
Les `repository` sont injectables tout comme les services. La différence est que ces premiers ont pour but de stocker des données et faciliter leur accès. Les services offrent des méthodes pour réaliser des opérations, des calculs.

- Dans le package `web.service` créez un repository CRUD pour les todo :

```java
@Repository
public interface TodoCrudRepository extends CrudRepository<Todo, Long> {
}
```
Pour rappel, le générique `Long` correspond au type de la clé primaire de `Todo` (l'attribut `id`).

- Dans `TodoService`, mettez en commentaire les attributs `cpt` et `todos` et ajoutez à la place votre nouveau repository :
```java
@Autowired
private TodoCrudRepository repository;
```

- Modifiez le code du service pour qu'il utilise désormais le repository pour stocker les objets `todo`. Vous noterez que la méthode `save` du repository ne demande pas l'id unique de l'objet. Pourquoi ? (il manque quelque chose dans la classe `Todo` que nous allons ajouter).


- Ajoutez les annotations nécessaires dans la classe `Todo` pour pallier le problème précédent.


- Testez votre nouveau contrôleur avec Swagger Editor.


## Bilan TP3

Nous avons vu comment mieux gérer les données manipulées dans un back-end à l'aide des services et des repositories.


Cependant, le notre back-end a encore des défauts :
- Nous utilisons un repository CRUD et non une véritable base de données, ce qui nous empêche d'écrire des requêtes SQL.
- Nous (de-)marshallons directement les objets `Todo` alors que nous voulons que quelques attributs dans certains cas. Nous utiliserons des DTO plus tard.
- Pas de test unitaire (TU) écrit pour l'instant.
- Pas de sécurité : tout le monde pour faire du CRUD sur les objets todo.



# TP4

## Q4.1 Retour des routes REST

Étant donné le code ci-dessous, qu'est-ce qui est retourné au client qui a envoyé la requête REST ? Un objet Todo ?

```java
	@GetMapping(path = "todo", produces = MediaType.APPLICATION_JSON_VALUE)
	public Todo todo() {
		return new Todo(1, "A title", "desc", List.of(Category.ENTERTAINMENT, Category.WORK), "foo");
	}
```

Et maintenant avec cette méthode qui retourne void ?

```java
	@DeleteMapping(path = "todo/{id}")
	public void deleteTodo(@PathVariable("id") final int id) {
		if(!todoListService.removeTodo(id)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not possible");
		}
	}
```

## Q4.2

Du coup, quelle différence avec le code suivant ? Que permet le code suivant ?


```java
@PutMapping(path = "user", consumes = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<String> replaceUser(@RequestBody final User patchedUser) {
  if(patchedUser.getId().equals(dataService.getUser().getId())) {
    dataService.setUser(patchedUser);
    return ResponseEntity.ok().build();
  }
  throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The ID is not the same");
}
```


## Q4.3 Les exceptions

Toujours avec le code suivant, qu'est-ce qui est retourné au client lorsqu'une exception est levée ?

```java
	@DeleteMapping(path = "todo/{id}")
	public void deleteTodo(@PathVariable("id") final int id) {
		if(!todoListService.removeTodo(id)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not possible");
		}
	}
```

## Q4.4 Marshalling avec héritage

La classe `SpecificTodo` est une sous-classe de `Todo`.
Modifiez la route `GET` `todo/todo` pour quelle retourne un objet `SpecificTodo`. Relancez le serveur et testez cette route. Utilisez le résultat retourné pour l'envoyer via la route `POST`. Pourquoi cette dernière ne crée finalement pas un `SpecificTodo` mais un `Todo` ?

Ajoutez les annotations nécessaires pour que cela fonctionne. Cf slide 47. Il vous faudra aussi ajouter l'annotation `@Entity`.




## Q4.5

Créez un nouveau contrôleur (URI `api/v2/public/todolist`), un nouveau service et un nouveau repository pour les `TodoList`.

## Q4.6

Ajoutez dans Swagger Editor et dans votre nouveau contrôleur les routes REST suivantes :
- une route pour ajouter une todolist vide. Vous devrez ajouter des annotations à `TodoList` à l'instar de `Todo`. Vous devrez également ajouter des annotations JPA pour identifier les clés étrangères de TodoList et Todo : puisque que TodoList à une liste de Todo, dans la base de données il faut expliciter comment cette référence Java va se transformer en schéma relationnel. Regardez les annotations `@OneToMany` et `@ManyToOne` slides 76. Sans ces annotations, le back-end crashera et vous expliquant qu'il ne sait pas gérer dans la base de données la relation entre ces deux classes.


## Terminer le TP pour la séance d'après

**Et sauvegarder votre openAPI de Swagger Editor !**


# TP5


## 5.1 DTO

La route pour ajouter une todolist vide n'est pas optimale : pourquoi envoyer un objet todolist alors que nous n'avons besoin que de son nom ?
Plusieurs solutions : mettre le nom dans l'URI de la requête ou embarquer un DTO contenant que le nom dans le body de la requête. Nous allons utiliser cette dernière solution.

- Créez un DTO `namedDTO` contenant juste un attribut correspond à un nom. Pensez à l'annotation `@Data` de *lombok* pour générer les getters et setters.

- Ajoutez ce DTO dans la définition de votre Swagger Editor et modifiez la route concernée.


## 5.2

- Ajoutez une route pour ajouter un todo à une todo list (un todo pour être dans plusieurs list pour l'instant). Attention, vous aurez donc besoin de l'id du todo à ajouter et de l'id de la todo list concernée. Donc votre `TodoListService` aura les deux repositories.


## 5.3 Patch Todo

Nous allons modifier la requête patch todo pour la rendre de meilleure qualité.
- Inspirez-vous du slide 31 pour modifier la requête et le service pour patch correctement le todo.
- Modifiez le Swagger Editor et testez


## 5.4 Query

- Ajoutez une query dans le repository des Todo pour retourner la liste des Todo dont le titre contient le texte donné en paramètre (cf la partie Query dans le cours).

- Ajoutez la requête REST associée dans le contrôleur Todo v2 et testez avec Swagger Editor





# TP6 Test

Le sujet de ce TP est simple.
Développer une suite de tests qui teste la dernière version de votre contrôleur, votre service, et repository avec une couverture de branche de 100%.
En test unitaire (TU) nous testons chaque classe séparément, donc le service puis le contrôleur (le repository n'a pas de code étant géré par Spring).

- Complétez la classe de tests `TestTodoService`

- Complétez la classe de tests `TestTodoControllerV2`



# TP7 Test

À l'instar du TP6, faites de même pour `TodoList`. Il vous faudra créer les classes de tests.
Au bout d'une heure de TP, commencez le TP8.


# TP8 Sécurité


Nous allons voir comment créer des routes REST publiques et d'autres privées : pour des questions de sécurités des données il est obligatoire ede réfléchir à ce que peuvent faire les utilisateurs. Nous n’utiliserons pas les todo et todo list au début de ce TP, juste des utilisateurs.



## 8.1


- Regardez le code de la classe `SecurityConfig` : que fait la ligne `new AntPathRequestMatcher("/api/v*/public/**")).permitAll()` selon vous ?


- Dans `TodoControllerV2`, remplacez `@RequestMapping("api/v2/public/todo")` par `@RequestMapping("api/v2/private/todo")`, modifiez votre Swagger Editor. Faites de même pour `TodoListController`. Testez : que se-passe-t-il désormais ?


## 8.2


- Créez un contrôleur Spring: `PublicUserController` (URI : `api/v2/public/user`).

- Utilisez le code fournit dans la classe `PublicUserController` du projet exemple (le projet montré en cours) pour ajouter une route pour créer un nouvel utilisateur et un autre pour s'identifier.
https://github.com/arnobl/WebEngineering-INSA/blob/master/rest/springboot2/src/main/java/fr/insarennes/demo/restcontroller/PublicUserController.java


- Ajoutez ces deux routes dans Swagger Editor et testez. Après avoir utiliser la route pour s'identifier, regardez la console d'IntelliJ/VSCode. Que voyez-vous de spéciale concernant l'authentification par cookie ?

## 8.3


- Créez un contrôleur Spring: `PrivateUserController` (URI : `api/v2/private/user`).


- Utilisez le code fournit dans la classe `PrivateUserController` du projet exemple pour ajouter la route :
```java
	@GetMapping()
	public String hello(final Principal user) {
		return user.getName();
	}
```

Cette route retourne donc le login de l'utilisateur authentifié.

https://github.com/arnobl/WebEngineering-INSA/blob/master/rest/springboot2/src/main/java/fr/insarennes/demo/restcontroller/PrivateUserController.java


- Pour tester cette route, il faut passer dans le cookie de la requête le paramètre `JSESSIONID`. Il n'est pas possible de faire cela via Swagger Editor (https://github.com/swagger-api/swagger-editor/issues/1951#issuecomment-466399821), donc utilisez *curl* de la manière suivante :
`curl -X 'GET' 'http://localhost:8080/api/v2/private/user' --cookie 'JSESSIONID=BA0A4E9FA9D6FF97753D8FA7361C5C'`


## 8.4

Il faut maintenant refaire fonctionner les routes de `TodoControllerV2`.

- Une fois authentifié (il faut créer un utilisateur après chaque redémarrage du back-end), vous pouvez tester avec curl la route 'hello' en utilisant le cookie de session retourné lors de l'authentification :
`curl -X 'GET' 'http://localhost:8080/api/v2/private/todo/hello'  --cookie 'JSESSIONID=...'`


- Pour la route 'todo', nous voulons que le `owner` du `todo` créé soit le `login` de l'utilisateur authentifié. Pour cela, dans toutes les requêtes qui nécessiteront cette information vous devrez ajouter en paramètre de la méthode Java de la route : `Principal principal` et utilisez `principal.getName()` pour obtenir le login et l'utiliser pour le paramètre `owner` du todo. Testez avec curl :
`curl -X 'GET' 'http://localhost:8080/api/v2/private/todo/todo'  --cookie 'JSESSIONID=...'`


## 8.5

Faites de même pour toutes les autres routes REST du contrôleur `TodoControllerV2`.
Attention : pour les routes *put*, *delete* et *patch* il faut vérifier que le login de l'utilisateur soit bien le `owner` des todos concernés.
Cela vous demandera de modifier votre service `TodoService` pour ajouter à différentes méthodes le login en paramètre.


## 8.6

Faites de même pour le contrôleur `TodoListController`.

