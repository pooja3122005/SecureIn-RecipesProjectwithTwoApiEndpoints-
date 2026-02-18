package com.recepieData.app.controller;

import com.recepieData.app.dto.PaginatedRecipeDTO;
import com.recepieData.app.dto.SearchResponse;
import com.recepieData.app.services.RecipeService;
import lombok.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping
    public PaginatedRecipeDTO getAllRecipes(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        return recipeService.getAllRecipes(page, limit);
    }

    @GetMapping("/search")
    public SearchResponse searchRecipes(
            @RequestParam(required = false) Integer calories,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String cuisine,
            @RequestParam(required = false, name = "total_time") Integer totalTime,
            @RequestParam(required = false) Float rating
    ) {
        return recipeService.searchRecipes(title, cuisine, totalTime, rating, calories);
    }
}
