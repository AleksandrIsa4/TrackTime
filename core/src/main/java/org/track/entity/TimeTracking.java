package org.track.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.track.model.Status;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@Entity
public class TimeTracking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Schema(description = "Название класса")
    String nameClass;

    @Schema(description = "Название метода")
    String nameMethod;

    @Schema(description = "Время выполнения метода, мс")
    Long time;

    @Schema(description = "Операция выполнена или была ошибка")
    @Enumerated(EnumType.STRING)
    Status status;

    @Schema(description = "Время начала выполнения метода")
    LocalDateTime dateTime;
}
