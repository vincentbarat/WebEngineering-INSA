package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import web.model.SpecificTodo;
import web.model.Todo;
import web.service.TodoService;

import java.util.List;
import java.util.Map;

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
    public SpecificTodo get() {
        return new SpecificTodo("test");
    }

    @PostMapping(path = "todo",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Todo post(@RequestBody final Todo todo) {
        return todoService.addTodo(todo);
    }

    @PutMapping(path = "todo",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Todo put(@RequestBody final Todo todo) {
        if (!todoService.replaceTodo(todo))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return todo;
    }

    @PatchMapping(path = "todo/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Todo patch(@PathVariable final long id,
                      @RequestBody final Map<String, Object> partialTodo) {
        Todo modifiedTodo = todoService.modifyTodo(id, partialTodo);
        if (modifiedTodo == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return modifiedTodo;
    }

    @DeleteMapping(path = "todo/{id}")
    public void delete(@PathVariable("id") final long id) {
        if (!todoService.removeTodo(id))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "title/{txt}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Todo> findByTitleContaining(@PathVariable("txt") final String txt) {
        return todoService.findTodoByTitleContaining(txt);
    }
}
