package com.recepieData.app.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeDTO {

    private Long id;
    private String title;
    private String cuisine;
    private Float rating;

    private Integer prep_time;
    private Integer cook_time;
    private Integer total_time;

    private String description;
    private Object nutrients;
    private String serves;
}
