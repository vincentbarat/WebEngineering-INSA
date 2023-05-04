package web.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import web.model.Category;
import web.model.Todo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestTodoService {

    @MockBean
    //@Autowired
    private TodoCrudRepository repository;

    @Autowired
    private TodoService todoService;

    @Test()
    public void saveCalledWhenAddingATodo() {
        Todo todo = new Todo(0L, "title 1", "bar", List.of(Category.LOW_PRIORITY), null, "foo");
        Todo todo2 = new Todo(1L, "title 1", "bar", List.of(Category.LOW_PRIORITY), null, "foo");

        // Configuring the mock so that a call to 'save' with 'todo' will return the same todo
        Mockito.when(repository.save(todo)).thenReturn(todo2);

        Todo res = todoService.add(todo);

        // Checking that the save method has been called one time with 'todo'
        Mockito.verify(repository, Mockito.times(1)).save(todo);

        // Checking that the method returns the result of 'save'.
        assertEquals(todo2, res);
    }
}
