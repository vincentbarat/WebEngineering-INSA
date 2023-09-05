package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import web.dto.IdsDTO;
import web.dto.NamedDTO;
import web.model.TodoList;
import web.service.TodoListService;

@RestController
@RequestMapping("api/v2/private/todolist")
@CrossOrigin
public class TodoListController {

    @Autowired
    TodoListService todoListService;

    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public TodoList create(@RequestBody final NamedDTO namedDTO) {
        return todoListService.create(namedDTO.getName());
    }

    @PatchMapping(path = "addtodo", produces = MediaType.APPLICATION_JSON_VALUE)
    public TodoList addTodoUsingDTO(@RequestBody final IdsDTO todoListIdDTO) {
        final TodoList todoList = todoListService.addTodo(todoListIdDTO.getTodoListId(), todoListIdDTO.getTodoId());
        if (todoList == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return todoList;
    }

    @PatchMapping(path = "{id}/addtodo/{todoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TodoList addTodoUsingPathVariables(@PathVariable final long id, @PathVariable final long todoId) {
        final TodoList todoList = todoListService.addTodo(id, todoId);
        if (todoList == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        return todoList;
    }
}
