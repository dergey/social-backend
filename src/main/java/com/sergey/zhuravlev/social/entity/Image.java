package com.sergey.zhuravlev.social.entity;

import com.sergey.zhuravlev.social.converter.DataSizeConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    @Column(name = "mime_type", length = 128, nullable = false, updatable = false)
    private String mimeType;

    @Column(name = "height", nullable = false, updatable = false)
    private Integer height;

    @Column(name = "width", nullable = false, updatable = false)
    private Integer width;

    @Convert(converter = DataSizeConverter.class)
    @Column(name = "data_size", nullable = false, updatable = false)
    private DataSize dataSize;

    @Column(name = "storage_id", length = 80, nullable = false, updatable = false)
    private String storageId;

    @Column(name = "create_at", nullable = false, updatable = false)
    private LocalDateTime createAt;

}
