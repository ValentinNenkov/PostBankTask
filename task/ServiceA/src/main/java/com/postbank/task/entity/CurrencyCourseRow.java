package com.postbank.task.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "currency_course_row")
public class CurrencyCourseRow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer gold;
    private String name;
    private String code;
    private Integer ratio;
    private BigDecimal reverseRate;
    private BigDecimal rate;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate currDate;
    private Integer fStar;
    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private CurrencyCourse currencyCourse;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CurrencyCourseRow that = (CurrencyCourseRow) o;

        if (!id.equals(that.id)) return false;
        if (!currDate.equals(that.currDate)) return false;
        return rate.equals(that.rate);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + currDate.hashCode();
        result = 31 * result + rate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CurrencyCourseRow{" +
                "id=" + id +
                ", currDate=" + currDate +
                ", rate=" + rate +
                '}';
    }

}

