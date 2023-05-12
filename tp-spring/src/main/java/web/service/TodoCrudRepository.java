package web.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import web.model.Todo;

import java.util.List;

@Repository
public interface TodoCrudRepository extends CrudRepository<Todo, Long> {
    List<Todo> findByTitleContaining(final String title);

    boolean existsByIdAndOwner(long id, String owner);

//    @Query("select t from Todo t where t.title like %?1%")
//    List<Todo> findByTitleTxt(final String titleTxt);
}
