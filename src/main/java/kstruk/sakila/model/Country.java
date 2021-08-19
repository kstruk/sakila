package kstruk.sakila.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Country {

    private Integer id;

    private String country;

    private LocalDateTime lastUpdate;

}
