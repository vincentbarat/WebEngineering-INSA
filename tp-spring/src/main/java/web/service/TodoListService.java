package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.TodoList;

@Service
public class TodoListService {

    @Autowired
    private TodoListCrudRepository repository;

    /**
     * Add a todo list.
     *
     * @param todoList Todo list to add
     * @return the added todo list
     */
    public TodoList addTodo(final TodoList todoList) {
        return repository.save(todoList);
    }
}
