package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.Todo;
import web.model.TodoList;

@Service
public class TodoListService {

    @Autowired
    private TodoListCrudRepository todoListRepository;

    @Autowired
    private TodoCrudRepository todoRepository;

    /**
     * Create a new todo list.
     *
     * @param name Name of the Todo list to create
     * @return the added todo list
     */
    public TodoList create(final String name) {
        return todoListRepository.save(new TodoList(name));
    }

    /**
     * Add a todo to a todo list.
     *
     * @param id     Id of the todo list
     * @param todoId Id of the todo to add to the todo list
     * @return the todo list on success, null otherwise
     */
    public TodoList addTodo(final long id, final long todoId) {
        TodoList todoList = todoListRepository.findById(id).orElse(null);
        if (todoList == null)
            return null;
        Todo todo = todoRepository.findById(todoId).orElse(null);
        if (todo == null)
            return null;
        todo.setList(todoList);
        todoRepository.save(todo);
        todoList.getTodos().add(todo);
        return todoListRepository.save(todoList);
    }
}
