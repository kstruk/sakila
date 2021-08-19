package kstruk.sakila.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Actor {

    private Integer id;

    private String firstName;

    private String lastName;

    private LocalDateTime lastUpdate;

}
