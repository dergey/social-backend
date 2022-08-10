package com.sergey.zhuravlev.social.entity.education;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HigherEducation extends Education {

    @Column(name = "faculty", length = 160)
    private String faculty;

    @Column(name = "education_form", length = 160)
    private String educationForm;

    @Column(name = "status", length = 80)
    private String status;

    private LocalDate releaseAt;

}
