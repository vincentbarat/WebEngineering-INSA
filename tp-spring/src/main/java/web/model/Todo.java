package web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonSubTypes({
    @JsonSubTypes.Type(value = SpecificTodo.class, name = "spec"),
    @JsonSubTypes.Type(value = Todo.class, name = "todo")
})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
public class Todo {

    @Id
    @GeneratedValue
    protected long id;

    protected String title;
    protected String description;
    protected List<Category> categories;

    @JsonIgnore
    @ManyToOne
    protected TodoList list;

    protected String owner;

    @Override
    public String toString() {
        return "Todo [id=" + id + ", title=" + title
            + ", description=" + description + ", categories=" + categories + "]";
    }
}
