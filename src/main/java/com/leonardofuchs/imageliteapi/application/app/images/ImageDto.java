package com.leonardofuchs.imageliteapi.application.app.images;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class ImageDto {
    private String url;
    private String name;
    private String extension;
    private String tags;
    private Long size;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime uploadDate;
}
