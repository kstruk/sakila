package kstruk.sakila.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Film {

    private Integer id;

    private Integer languageId;

    private Integer originalLanguageId;

    private Integer ratingId;

    private String title;

    private String description;

    private Integer releaseYear;

    private Integer length;

    private Integer rentalDuration;

    private BigDecimal rentalRate;

    private BigDecimal replacementCost;

    private LocalDateTime lastUpdate;

}
