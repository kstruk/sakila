package kstruk.sakila.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class FilmText {

    private Integer id;

    private String title;

    private String description;

    private LocalDateTime lastUpdate;

}
