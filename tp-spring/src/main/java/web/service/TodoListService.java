package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.TodoList;

@Service
public class TodoListService {

    @Autowired
    private TodoListCrudRepository repository;

    /**
     * Create a new todo list.
     *
     * @param name Namle of the Todo list to create
     * @return the added todo list
     */
    public TodoList createTodoList(final String name) {
        return repository.save(new TodoList(name));
    }
}
