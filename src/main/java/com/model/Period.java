package com.model;

import com.validation.ClassCheck;
import com.validation.ValidPeriod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@ValidPeriod(groups =  ClassCheck.class)
@GroupSequence({Period.class, ClassCheck.class})
public class Period {

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "START_DATE")
    private LocalDate startDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "END_DATE")
    private LocalDate endDate;

}
