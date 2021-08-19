package kstruk.sakila.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class City {

    private Integer id;

    private Integer countryId;

    private String city;

    private LocalDateTime lastUpdate;

}
