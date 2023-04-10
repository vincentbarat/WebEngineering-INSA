package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import web.model.Category;
import web.model.Todo;
import web.service.TodoService;

import java.util.List;

@RestController
@RequestMapping("api/v2/public/todo")
@CrossOrigin
public class TodoControllerV2 {

    @Autowired
    TodoService todoService;

    @GetMapping(path = "hello", produces = MediaType.TEXT_PLAIN_VALUE)
    public String hello() {
        return "Hello";
    }

    @GetMapping(path = "todo", produces = MediaType.APPLICATION_JSON_VALUE)
    public Todo get() {
        return new Todo(1, "A title", "desc", List.of(Category.ENTERTAINMENT, Category.WORK), null, "foo");
    }

    @PostMapping(path = "todo", produces = MediaType.APPLICATION_JSON_VALUE)
    public Todo post(@RequestBody final Todo todo) {
        todoService.addTodo(todo);
        return todo;
    }

    @PutMapping(path = "todo", produces = MediaType.APPLICATION_JSON_VALUE)
    public Todo put(@RequestBody final Todo todo) {
        if (!todoService.replaceTodo(todo))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return todo;
    }

    @PatchMapping(path = "bof/todo", produces = MediaType.APPLICATION_JSON_VALUE)
    public Todo patch(@RequestBody final Todo patch) {
        Todo modifiedTodo = todoService.modifyTodo(patch);
        if (modifiedTodo == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return modifiedTodo;
    }

    @DeleteMapping(path = "todo/{id}")
    public void deleteTodo(@PathVariable("id") final long id) {
        if (!todoService.removeTodo(id))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
}
