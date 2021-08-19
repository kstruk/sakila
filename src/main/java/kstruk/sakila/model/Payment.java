package kstruk.sakila.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Payment {

    private Integer id;

    private Integer customerId;

    private Integer stuffId;

    private Integer rentalId;

    private BigDecimal amount;

    private LocalDateTime paymentDate;

    private LocalDateTime lastUpdate;

}
