package web.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.Todo;

import java.util.List;
import java.util.Map;

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
    public Todo add(final Todo todo) {
        return repository.save(todo);
    }

    /**
     * Replace an existing todo by a new one.
     *
     * @param newTodo New todo to insert
     * @return true if a todo with the same key was already present
     */
    public boolean replace(final Todo newTodo) {
        if (!repository.existsById(newTodo.getId()))
            return false;
        repository.save(newTodo);
        return true;
    }

    /**
     * Delete a todo.
     *
     * @param id Id of the todo to remove
     * @return true if the todo was found and deleted
     */
    public boolean delete(final long id) {
        if (!repository.existsById(id))
            return false;
        repository.deleteById(id);
        return true;
    }

    /**
     * Modify an existing todo.
     *
     * @param partialTodo A map containing the set of attributes to replace
     * @param id          Id of the todo to modify
     * @return the modified todo or null if the todo to modify cannot be found
     */
    public Todo modify(final long id, final Map<String, Object> partialTodo) {
        Todo todo = repository.findById(id).orElse(null);
        if (todo == null)
            return null;
        try {
            new ObjectMapper().updateValue(todo, partialTodo);
            return repository.save(todo);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Find all todos whose title contains a given text.
     *
     * @param txt The text to search for
     * @return the list of todos found
     */
    public List<Todo> findByTitleContaining(final String txt) {
        return repository.findByTitleContaining(txt);
    }
}
