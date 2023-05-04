package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import web.dto.NamedDTO;
import web.dto.TodoListIdDTO;
import web.model.TodoList;
import web.service.TodoListService;

@RestController
@RequestMapping("api/v2/public/todolist")
@CrossOrigin
public class TodoListController {

    @Autowired
    TodoListService todoListService;

    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public TodoList create(@RequestBody final NamedDTO namedDTO) {
        return todoListService.create(namedDTO.getName());
    }

    @PatchMapping(path = "todo", produces = MediaType.APPLICATION_JSON_VALUE)
    public TodoList addTodo(@RequestBody final TodoListIdDTO todoListIdDTO) {
        final TodoList todoList = todoListService.addTodo(todoListIdDTO.getTodoListId(), todoListIdDTO.getTodoId());
        if (todoList == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return todoList;
    }
}
