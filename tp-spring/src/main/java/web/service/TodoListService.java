package web.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import web.model.Todo;

import java.util.HashMap;
import java.util.Map;

@Service
public class TodoListService {

    private long cpt;
    private final Map<Long, Todo> todos = new HashMap<>();

    /**
     * Add a todo.
     *
     * @param todo Todo to add
     * @return the added todo
     */
    public Todo addTodo(final Todo todo) {
        todo.setId(cpt);
        todos.put(cpt, todo);
        cpt++;
        System.out.println(todos);
        return todo;
    }

    /**
     * Replace an existing todo by a new one.
     *
     * @param newTodo New todo to insert
     * @return true if a todo with the same key was already present
     */
    public boolean replaceTodo(final Todo newTodo) {
        if (todos.get(newTodo.getId()) == null)
            return false;
        todos.put(newTodo.getId(), newTodo);
        System.out.println(todos);
        return true;
    }

    /**
     * Remove a todo.
     *
     * @param id Id of the todo to remove
     * @return true if the todo was found and deleted
     */
    public boolean removeTodo(final long id) {
        if (todos.get(id) == null)
            return false;
        todos.remove(id);
        System.out.println(todos);
        return true;
    }

    /**
     * Modify an existing todo.
     *
     * @param partialTodo A partial todo containing the identifier of the todo to modify and the attributes to replace
     * @return the modified todo or null if the todo to modify cannot be found
     */
    public Todo modifyTodo(final Todo partialTodo) {
        Todo todo = todos.get(partialTodo.getId());
        if (todo == null)
            return null;
        BeanUtils.copyProperties(partialTodo, todo, Todo.class);
        System.out.println(todos);
        return todo;
    }
}
