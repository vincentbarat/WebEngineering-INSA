Quelques remarques sur le TP :

TP1 / TP2:
- le type de la variable `cpt` devrait être `long` pour matcher le `uint64` du Swagger et éviter les pb de cast dans le code   
- GET devrait prendre un paramètre {id} dès la première implémentation pour éviter la confusion et correspondre a un cas classique
- PUT devrait aussi prendre un paramètre {id} (pour éviter des erreurs du client, et bien différentier le POST et le PUT)
- NOT_FOUND devrait être retourné plutôt que BAD_REQUEST par GET, PUT et DELETE quand l'objet n'est pâs trouvé
- Dans le corrigé, le POST ne fonctionne pas, visiblement a cause des annotations suivantes :

```
@JsonSubTypes({
  @JsonSubTypes.Type(value = SpecificTodo.class, name = "spec"),
  @JsonSubTypes.Type(value = Todo.class, name = "todo")
})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
```

- Dans le corrigé, le PATCH ne fonctionne pas avec :
```BeanUtils.copyProperties(todo, todoFound, Object.class);```
il faut remplacer Object.class par Todo.class.
