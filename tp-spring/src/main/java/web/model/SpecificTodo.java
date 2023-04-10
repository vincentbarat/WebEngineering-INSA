package web.model;

import jakarta.persistence.Entity;
import lombok.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@Entity
public class SpecificTodo extends Todo {
    private String specificAttr;
}
