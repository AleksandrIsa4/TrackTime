package org.track.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.track.model.Status;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class TimeTrackingResponse {

    @Schema(description = "Название класса")
    String nameClass;

    @Schema(description = "Название метода")
    String nameMethod;

    @Schema(description = "время выполнения метода, мс")
    Long time;

    @Schema(description = "Статус выполнения метода")
    Status status;

    @Schema(description = "Время начала выполнения метода")
    LocalDateTime dateTime;
}
