package kstruk.sakila.model;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.Set;
import lombok.Data;

@Data
public class Film {

    private Integer id;

    private Integer languageId;

    private Integer originalLanguageId;

    private String title;

    private String description;

    private Year releaseYear;

    private Integer rentalDuration;

    private Double rentalRate;

    private Integer length;

    private Double replacementCost;

    private Integer ratingId;

    private Set<Feature> specialFeatures;

    private LocalDateTime lastUpdate;

}
