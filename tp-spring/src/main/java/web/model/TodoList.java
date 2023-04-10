package web.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class TodoList {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "list")
    private List<Todo> todos;

    private String owner;

    public TodoList(final String name) {
        super();
        this.name = name;
        todos = new ArrayList<>();
    }
}
