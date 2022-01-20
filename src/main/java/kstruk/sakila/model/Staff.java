package kstruk.sakila.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Staff {

    private Integer id;

    private Integer addressId;

    private Integer storeId;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private Boolean active;

    private LocalDateTime lastUpdate;

}
