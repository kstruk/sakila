package kstruk.sakila.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Customer {

    private Integer id;

    private Integer addressId;

    private Integer storeId;

    private String firstName;

    private String lastName;

    private String email;

    private boolean active;

    private LocalDateTime createDate;

    private LocalDateTime lastUpdate;

}
