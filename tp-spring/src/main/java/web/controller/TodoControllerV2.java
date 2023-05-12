package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import web.model.Category;
import web.model.Todo;
import web.service.TodoService;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v2/private/todo")
@CrossOrigin
public class TodoControllerV2 {

    @Autowired
    TodoService todoService;

    @GetMapping(path = "hello", produces = MediaType.TEXT_PLAIN_VALUE)
    public String hello() {
        return "Hello";
    }

    @GetMapping(path = "todo", produces = MediaType.APPLICATION_JSON_VALUE)
    public Todo get(Principal user) {
        return new Todo(1, "A title", "desc", List.of(Category.ENTERTAINMENT, Category.WORK), null, user.getName());
    }

    @PostMapping(path = "todo",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public Todo post(final Principal user, @RequestBody final Todo todo) {
        todo.setId(0); // ensure not to overwrite an existing todo
        todo.setOwner(user.getName()); // ensure the owner is the logged user
        return todoService.add(todo);
    }

    @PutMapping(path = "todo",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public Todo put(final Principal user, @RequestBody final Todo todo) {
        // Ensure the todo exists and is owned by the logger user
        if (!todoService.existByIdAndOwner(todo.getId(), user.getName()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        todo.setOwner(user.getName()); // ensure the owner is not changed
        if (!todoService.replace(todo))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return todo;
    }

    @PatchMapping(path = "todo/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public Todo patch(final Principal user, @PathVariable final long id,
                      @RequestBody final Map<String, Object> partialTodo) {
        // Ensure the todo exists and is owned by the logger user
        if (!todoService.existByIdAndOwner(id, user.getName()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        partialTodo.remove("id"); // ensure the id is not modified
        partialTodo.remove("owner"); // ensure the owner is not modified
        Todo modifiedTodo = todoService.modify(id, partialTodo);
        if (modifiedTodo == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return modifiedTodo;
    }

    @DeleteMapping(path = "todo/{id}")
    public void delete(final Principal user, @PathVariable final long id) {
        // Ensure the todo exists and is owned by the logger user
        if (!todoService.existByIdAndOwner(id, user.getName()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if (!todoService.delete(id))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "title", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Todo> findByTitleContaining(@RequestParam("txt") final String txt) {
        return todoService.findByTitleContaining(txt);
    }
}
