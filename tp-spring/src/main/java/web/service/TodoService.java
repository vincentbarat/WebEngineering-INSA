package web.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.Todo;

import java.util.Optional;

@Service
public class TodoService {

//    private long cpt;
//    private final Map<Long, Todo> todos = new HashMap<>();

    @Autowired
    private TodoCrudRepository repository;

    /**
     * Add a todo.
     *
     * @param todo Todo to add
     * @return the added todo
     */
    public Todo addTodo(final Todo todo) {
        repository.save(todo);
        return todo;
    }

    /**
     * Replace an existing todo by a new one.
     *
     * @param newTodo New todo to insert
     * @return true if a todo with the same key was already present
     */
    public boolean replaceTodo(final Todo newTodo) {
        if (!repository.existsById(newTodo.getId()))
            return false;
        repository.save(newTodo);
        return true;
    }

    /**
     * Remove a todo.
     *
     * @param id Id of the todo to remove
     * @return true if the todo was found and deleted
     */
    public boolean removeTodo(final long id) {
        if (!repository.existsById(id))
            return false;
        repository.deleteById(id);
        return true;
    }

    /**
     * Modify an existing todo.
     *
     * @param partialTodo A partial todo containing the identifier of the todo to modify and the attributes to replace
     * @return the modified todo or null if the todo to modify cannot be found
     */
    public Todo modifyTodo(final Todo partialTodo) {
        Optional<Todo> todo = repository.findById(partialTodo.getId());
        if (todo.isEmpty())
            return null;
        BeanUtils.copyProperties(partialTodo, todo.get(), Todo.class);
        return todo.get();
    }
}
