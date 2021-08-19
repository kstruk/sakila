package kstruk.sakila.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Address {

    private Integer id;

    private Integer cityId;

    private String address;

    private String address2;

    private String district;

    private String postalCode;

    private String phone;

    private LocalDateTime lastUpdate;

}
