package web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
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
