package com.example.blog.statistics.rest.application;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BlogStatisticsRestApplicationIntegrationTest {

    private static final String BASE_URI = "http://localhost:8080";
    private static final String BASE_PATH = "/o/blog-statistics";
    private static final long TEST_BLOG_ENTRY_ID = 99001L;
    private static final long NON_EXISTENT_BLOG_ENTRY_ID = 999999999L;

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.basePath = BASE_PATH;
        RestAssured.authentication = RestAssured.basic("test@liferay.com", "test");
    }

    // =========================================================================
    // 1. POST /{blogEntryId}/increment
    // =========================================================================

    @Test
    public void test01_IncrementViewCount_Returns200AndUpdatedDto() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .pathParam("blogEntryId", TEST_BLOG_ENTRY_ID)
                .when()
                .post("/{blogEntryId}/increment")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("blogEntryId", equalTo((int) TEST_BLOG_ENTRY_ID))
                .body("viewCount", greaterThanOrEqualTo(1));
    }

    // =========================================================================
    // 2. GET /{blogEntryId}
    // =========================================================================

    @Test
    public void test02_GetViewCountByBlogEntryId_WhenRecordExists_Returns200() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .pathParam("blogEntryId", TEST_BLOG_ENTRY_ID)
                .when()
                .get("/{blogEntryId}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("blogEntryId", equalTo((int) TEST_BLOG_ENTRY_ID))
                .body("viewCount", notNullValue());
    }

    @Test
    public void test03_GetViewCountByBlogEntryId_WhenNotFound_Returns404() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .pathParam("blogEntryId", NON_EXISTENT_BLOG_ENTRY_ID)
                .when()
                .get("/{blogEntryId}")
                .then()
                .statusCode(404);
    }

    // =========================================================================
    // 3. GET / (getAllViewCounts)
    // =========================================================================

    @Test
    public void test04_GetAllViewCounts_WithPagination_Returns200AndPagedResponse() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .queryParam("page", 1)
                .queryParam("pageSize", 10)
                .queryParam("blogEntryId", TEST_BLOG_ENTRY_ID)
                .when()
                .get("/")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("page", equalTo(1))
                .body("pageSize", equalTo(10))
                .body("items", notNullValue());
    }

    // =========================================================================
    // 4. DELETE /{blogEntryId}
    // =========================================================================

    @Test
    public void test05_DeleteByBlogEntryId_WhenExists_Returns204NoContent() {
        RestAssured.given()
                .pathParam("blogEntryId", TEST_BLOG_ENTRY_ID)
                .when()
                .delete("/{blogEntryId}")
                .then()
                .statusCode(204);
    }

    @Test
    public void test06_DeleteByBlogEntryId_WhenNotFound_Returns404() {
        RestAssured.given()
                .pathParam("blogEntryId", NON_EXISTENT_BLOG_ENTRY_ID)
                .when()
                .delete("/{blogEntryId}")
                .then()
                .statusCode(404);
    }

    // =========================================================================
    // 5. DELETE / (deleteAllStatistics)
    // =========================================================================

    @Test
    public void test07_DeleteAllStatistics_Returns204NoContent() {
        RestAssured.given()
                .when()
                .delete("/")
                .then()
                .statusCode(204);
    }
}
