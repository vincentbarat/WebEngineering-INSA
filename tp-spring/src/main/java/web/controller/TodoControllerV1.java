package web.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
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

    private long cpt;
    private final Map<Long, Todo> todos = new HashMap<>();

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
    public Todo post(@RequestBody final Todo todo) {
        todo.setId(cpt);
        todos.put(cpt, todo);
        cpt++;
        System.out.println(todos);
        return todo;
    }

    @PutMapping(path = "todo", produces = MediaType.APPLICATION_JSON_VALUE)
    public Todo put(@RequestBody final Todo todo) {
        if (todos.get(todo.getId()) == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        todos.put(todo.getId(), todo);
        System.out.println(todos);
        return todo;
    }

    @PatchMapping(path = "bof/todo", produces = MediaType.APPLICATION_JSON_VALUE)
    public Todo patch(@RequestBody final Todo patch) {
        Todo todo = todos.get(patch.getId());
        if (todo == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

//        if (patch.getList() != null)
//            todo.setList(patch.getList());
//        if (patch.getTitle() != null)
//            todo.setTitle(patch.getTitle());
//        if (patch.getOwner() != null)
//            todo.setOwner(patch.getOwner());
//        if (patch.getDescription() != null)
//            todo.setDescription(patch.getDescription());
//        if (patch.getCategories() != null)
//            todo.setCategories(patch.getCategories());
        try {
            BeanUtils.copyProperties(patch, todo, Todo.class);
        }
        catch (final BeansException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not possible", ex);
        }

        System.out.println(todos);
        return todo;
    }

    @DeleteMapping(path = "todo/{id}")
    public void deleteTodo(@PathVariable("id") final long id) {
        if (todos.get(id) == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        todos.remove(id);
        System.out.println(todos);
    }
}
