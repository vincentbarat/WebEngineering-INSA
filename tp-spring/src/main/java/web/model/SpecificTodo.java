package web.model;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecificTodo extends Todo {
    private String specificAttr;
}
