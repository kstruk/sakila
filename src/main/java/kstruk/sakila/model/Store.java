package kstruk.sakila.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Store {

    private Integer id;

    private Integer addressId;

    private Integer managerId;

    private LocalDateTime lastUpdate;

}
