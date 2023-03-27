package web.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import web.model.Category;
import web.model.Todo;

import java.util.List;

@RestController
@RequestMapping("api/v1/public/todo")
@CrossOrigin
public class TodoControllerV1 {

    public TodoControllerV1() {
    }

    @GetMapping(path = "hello", produces = MediaType.TEXT_PLAIN_VALUE)
    public String hello() {
        return "Hello";
    }

    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Todo get(@PathVariable String id) {
        return new Todo(1, "A title", "desc", List.of(Category.ENTERTAINMENT, Category.WORK), null, "foo");
    }

    @PostMapping()
    public void post(@RequestBody Todo todo) {
        System.out.println(todo);
    }
}
