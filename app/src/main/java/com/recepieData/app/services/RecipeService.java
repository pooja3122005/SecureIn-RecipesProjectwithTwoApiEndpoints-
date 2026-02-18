package com.recepieData.app.services;

import com.recepieData.app.Repo.RecipeRepo;
import com.recepieData.app.dto.PaginatedRecipeDTO;
import com.recepieData.app.dto.RecipeDTO;
import com.recepieData.app.dto.SearchResponse;
import com.recepieData.app.entities.Recipe;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepo recipeRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Convert Entity -> DTO
    private RecipeDTO convertToDTO(Recipe recipe) {

        Object nutrientsObj = null;

        try {
            if (recipe.getNutrients() != null) {
                nutrientsObj = objectMapper.readValue(recipe.getNutrients(), Object.class);
            }
        } catch (Exception e) {
            // If nutrients JSON parsing fails, return nutrients as String
            nutrientsObj = recipe.getNutrients();
        }

        return RecipeDTO.builder()
                .id(recipe.getId())
                .title(recipe.getTitle())
                .cuisine(recipe.getCuisine())
                .rating(recipe.getRating())
                .prep_time(recipe.getPrepTime())
                .cook_time(recipe.getCookTime())
                .total_time(recipe.getTotalTime())
                .description(recipe.getDescription())
                .nutrients(nutrientsObj)
                .serves(recipe.getServes())
                .build();
    }

    // API 1: GET ALL recipes with pagination + sort rating DESC
    public PaginatedRecipeDTO getAllRecipes(int page, int limit) {

        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("rating").descending());
        Page<Recipe> recipes = recipeRepository.findAll(pageable);

        List<RecipeDTO> recipeList = recipes.getContent()
                .stream()
                .map(this::convertToDTO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return PaginatedRecipeDTO.builder()
                .page(page)
                .limit(limit)
                .total(recipes.getTotalElements())
                .data(recipeList)
                .build();
    }

    // API 2: SEARCH recipes with filters
    public SearchResponse searchRecipes(
            String title,
            String cuisine,
            Integer totalTime,
            Float rating,
            Integer calories
    ) {

        Specification<Recipe> spec = (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (title != null && !title.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
            }

            if (cuisine != null && !cuisine.isEmpty()) {
                predicates.add(cb.equal(cb.lower(root.get("cuisine")), cuisine.toLowerCase()));
            }

            if (totalTime != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("totalTime"), totalTime));
            }

            if (rating != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("rating"), rating));
            }

            // calories inside nutrients string
            if (calories != null) {
                predicates.add(cb.like(root.get("nutrients"), "%" + calories + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        List<RecipeDTO> recipes = recipeRepository.findAll(spec)
                .stream()
                .map(this::convertToDTO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return SearchResponse.builder()
                .data(recipes)
                .build();
    }
}
