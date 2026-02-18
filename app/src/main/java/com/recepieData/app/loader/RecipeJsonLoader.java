package com.recepieData.app.loader;

import com.recepieData.app.Repo.RecipeRepo;
import com.recepieData.app.entities.Recipe;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class RecipeJsonLoader implements CommandLineRunner {

    private final RecipeRepo recipeRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void run(String... args) throws Exception {

        if (recipeRepository.count() > 0) {
            System.out.println("Recipes already exist in DB. Skipping JSON load...");
            return;
        }

        InputStream inputStream = new ClassPathResource("recipes.json").getInputStream();
        JsonNode rootNode = objectMapper.readTree(inputStream);

        // Your JSON is single object (not array)
        Recipe recipe = Recipe.builder()
                .title(rootNode.path("title").asText(""))
                .cuisine(rootNode.path("cuisine").asText(""))
                .rating(parseFloat(rootNode.path("rating").asText(null)))
                .prepTime(parseInt(rootNode.path("prep_time").asText(null)))
                .cookTime(parseInt(rootNode.path("cook_time").asText(null)))
                .totalTime(parseInt(rootNode.path("total_time").asText(null)))
                .description(rootNode.path("description").asText(""))
                .serves(rootNode.path("serves").asText(""))
                .nutrients(rootNode.path("nutrients").toString())
                .build();

        recipeRepository.save(recipe);

        System.out.println("1 Recipe loaded successfully into DB!");
    }

    private Float parseFloat(String value) {
        try {
            if (value == null || value.equalsIgnoreCase("NaN") || value.isEmpty()) return null;
            return Float.parseFloat(value);
        } catch (Exception e) {
            return null;
        }
    }

    private Integer parseInt(String value) {
        try {
            if (value == null || value.equalsIgnoreCase("NaN") || value.isEmpty()) return null;
            return Integer.parseInt(value);
        } catch (Exception e) {
            return null;
        }
    }
}
