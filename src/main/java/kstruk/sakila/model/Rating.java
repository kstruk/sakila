package kstruk.sakila.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Rating {

    private Integer id;

    private String name;

    private LocalDateTime lastUpdate;

}
