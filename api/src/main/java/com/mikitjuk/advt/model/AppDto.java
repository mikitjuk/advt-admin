package com.mikitjuk.advt.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mikitjuk.advt.entity.types.AppType;
import com.mikitjuk.advt.entity.types.ContentType;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class AppDto {
    private Integer id;
    private String name;
    private AppType type;
    @JsonProperty("content_types")
    private List<ContentType> contentTypes;
    @JsonProperty("user_id")
    private Integer userId;
}
