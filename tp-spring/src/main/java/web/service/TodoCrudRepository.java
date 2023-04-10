package web.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import web.model.Todo;

@Repository
public interface TodoCrudRepository extends CrudRepository<Todo, Long> {
}
