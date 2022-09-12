package com.sergey.zhuravlev.socail.kafka;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({KafkaGenerateConfiguration.class})
@Documented
public @interface GenerateTopics {
}
