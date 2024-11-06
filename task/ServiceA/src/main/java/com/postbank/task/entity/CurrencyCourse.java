package com.postbank.task.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "currency_course")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrencyCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate date;
    @OneToMany(mappedBy = "currencyCourse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CurrencyCourseRow> rows;
    private Boolean active;


}
