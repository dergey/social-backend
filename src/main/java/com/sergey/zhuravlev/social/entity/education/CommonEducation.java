package com.sergey.zhuravlev.social.entity.education;

import javax.persistence.Column;
import java.time.LocalDate;

public class CommonEducation extends Education {

    @Column(name = "start_at")
    private LocalDate startAt;

    @Column(name = "end_at")
    private LocalDate endAt;

    @Column(name = "release_at")
    private LocalDate releaseAt;

}
