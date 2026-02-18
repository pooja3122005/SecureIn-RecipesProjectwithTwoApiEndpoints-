package com.recepieData.app.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginatedRecipeDTO {

    private int page;
    private int limit;
    private long total;
    private List<RecipeDTO> data;
}
