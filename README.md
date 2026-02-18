# SecureIn-RecipesProjectwithTwoApiEndpoints-

#  Recipe Data API (Spring Boot + PostgreSQL)

A **Spring Boot REST API project** that loads recipe data from a JSON file into a PostgreSQL database and provides APIs to retrieve recipes with pagination, sorting, and filtering(search).

This project demonstrates:
- Spring Boot REST API development
- Spring Data JPA + Hibernate integration
- PostgreSQL JSONB/TEXT storage for nutrients
- JSON file loading using CommandLineRunner
- Pagination + Sorting
- Dynamic search using Specification API



##  Tech Stack

- **Java 17**
- **Spring Boot 3.2.2**
- **Spring Web**
- **Spring Data JPA**
- **Hibernate**
- **PostgreSQL**
- **Jackson (ObjectMapper)**
- **Lombok**
- **Maven**
- **Postman** (for API testing)
- **pgAdmin** (optional, for DB GUI)

---

## 📂 Project Structure

src/main/java/com/recepieData/app
│
├── controller
│ └── RecipeController.java
│
├── services
│ └── RecipeService.java
│
├── Repo
│ └── RecipeRepo.java
│
├── entities
│ └── Recipe.java
│
├── dto
│ ├── RecipeDTO.java
│ ├── PaginatedRecipeDTO.java
│ └── SearchResponse.java
│
├── loader
│ └── RecipeJsonLoader.java
│
└── AppApplication.java

---

##  Required Installations (Before Running)


Download:
- OpenJDK 17 / Oracle JDK 17

Verify installation:
```powershell
java -version
mvn -version
psql --version
Install pgAdmin (Optional)

a)  Database Setup
Open pgAdmin-->
CREATE DATABASE recipedb;

b)Spring Boot Database Configuration
in application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/recipedb
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

server.port=8080

c)JSON File Setup
Ensure this file exists:

src/main/resources/recipes.json

This file contains recipe records.
When the project starts, it automatically loads JSON data into DB using:
CommandLineRunner (RecipeJsonLoader)

d)Build Project
mvn clean install
e)Run Spring Boot App
mvn spring-boot:run


final testing whether our API endpoints is working or not
to check that use these url
1)Get All Recipes (Pagination + Sorting)
http://localhost:8080/api/recipes?page=1&limit=10

2)Search Recipes (Filter API)
Search by Title----->http://localhost:8080/api/recipes/search?title=pie
Search by Cuisine---->http://localhost:8080/api/recipes/search?cuisine=Southern%20Recipes
 we can search accordingly....


here to insert Recipe Manually(sql)  in PostGresSQL to go to Query Tool run this 
INSERT INTO recipes
(title, cuisine, rating, prep_time, cook_time, total_time, description, nutrients, serves)
VALUES
(
  'Chicken Biryani',
  'Indian Recipes',
  4.6,
  20,
  40,
  60,
  'Spicy and flavorful chicken biryani made with basmati rice.',
  '{"calories":"550 kcal","proteinContent":"30 g","fatContent":"20 g"}',
  '4 servings'
);


output :
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/f77538f9-2f3b-4fb6-a283-ab9cd6dabac2" />
<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/6e098282-b912-481c-8ef5-468f08f349e8" />
<img width="1919" height="1021" alt="image" src="https://github.com/user-attachments/assets/c0388aee-a417-4622-a504-0250fc9234f3" />
<img width="1917" height="985" alt="image" src="https://github.com/user-attachments/assets/f0c55618-0632-4489-ab0f-fd8802162830" />



 
