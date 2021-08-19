package kstruk.sakila.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Stuff {

    private Integer id;

    private Integer addressId;

    private Integer storeId;

    private String firstName;

    private String lastName;

    private byte[] picture;

    private String email;

    private Boolean active;

    private String username;

    private String password;

    private LocalDateTime lastUpdate;

}
