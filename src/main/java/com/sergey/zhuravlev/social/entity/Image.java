package com.sergey.zhuravlev.social.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.springframework.util.unit.DataSize;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    // todo do render in all ImageSize
    // This preview will always be stored in JPEG format, with dimensions of 512x512
    @Lob
    @Fetch(FetchMode.SELECT)
    @Type(type="org.hibernate.type.MaterializedBlobType")
    @Column(name = "preview", nullable = false, updatable = false)
    private byte[] preview;

    @Column (name = "mime_type", nullable = false, updatable = false)
    private String mimeType;

    @Column (name = "height", nullable = false, updatable = false)
    private Integer height;

    @Column (name = "width", nullable = false, updatable = false)
    private Integer width;

    @Column (name = "data_size", nullable = false, updatable = false)
    private DataSize dataSize;

    @Lob
    @Fetch(FetchMode.SELECT)
    @Type(type="org.hibernate.type.MaterializedBlobType")
    @Column(name = "data", nullable = false, updatable = false)
    private byte[] data;

    @Column(name = "create_at", nullable = false, updatable = false)
    private LocalDateTime createAt;

}
