package com.mikitjuk.advt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mikitjuk.advt.entity.types.AppType;
import com.mikitjuk.advt.entity.types.ContentType;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppDto {
    //read only
    private Integer id;
    private String name;
    private AppType type;
    private Integer userId;
    private List<ContentType> contentTypes;
}
