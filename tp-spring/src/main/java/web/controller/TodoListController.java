package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import web.dto.NamedDTO;
import web.model.TodoList;
import web.service.TodoListService;

@RestController
@RequestMapping("api/v2/public/todolist")
@CrossOrigin
public class TodoListController {

    @Autowired
    TodoListService todoListService;

    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public TodoList post(@RequestBody final NamedDTO namedDTO) {
        return todoListService.createTodoList(namedDTO.getName());
    }
}
