package kstruk.sakila.model;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Rental {

    private Integer id;

    private Integer stuffId;

    private Integer customerId;

    private Integer inventoryId;

    private LocalDateTime rentalDate;

    private LocalDateTime returnDate;

    private LocalDateTime lastUpdate;

}
