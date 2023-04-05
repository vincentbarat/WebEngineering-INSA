package web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import web.model.Category;
import web.model.Todo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/public/todo")
@CrossOrigin
public class TodoControllerV1 {

    private int cpt;
    private Map<Integer, Todo> todos = new HashMap<>();

    public TodoControllerV1() {
    }

    @GetMapping(path = "hello", produces = MediaType.TEXT_PLAIN_VALUE)
    public String hello() {
        return "Hello";
    }

    @GetMapping(path = "todo", produces = MediaType.APPLICATION_JSON_VALUE)
    public Todo get() {
        return new Todo(1, "A title", "desc", List.of(Category.ENTERTAINMENT, Category.WORK), null, "foo");
    }

    @PostMapping(path = "todo", produces = MediaType.APPLICATION_JSON_VALUE)
    public Todo post(@RequestBody Todo todo) {
        todo.setId(cpt);
        todos.put(cpt, todo);
        cpt++;
        System.out.println(todos);
        return todo;
    }

    @DeleteMapping(path = "todo/{id}")
    public void deleteTodo(@PathVariable("id") Integer id) {
        if (todos.get(id) == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        todos.remove(id);
    }
}
