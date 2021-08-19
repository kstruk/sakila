package kstruk.sakila.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Inventory {

    private Integer id;

    private Integer filmId;

    private Integer storeId;

    private LocalDateTime lastUpdate;

}
