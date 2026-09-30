package com.example.blog.statistics.rest.application;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BlogStatisticsRestApplicationIntegrationTest {

    private static final String BASE_URI = "http://localhost:8080";
    private static final String BASE_PATH = "/o/blog-statistics";
    private static final long TEST_BLOG_ENTRY_ID = 99001L;
    private static final long SORTED_BLOG_ENTRY_ID = 99002L;
    private static final long OTHER_SORTED_BLOG_ENTRY_ID = 99003L;
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

    @Test
    public void test04a_GetAllViewCounts_WithSort_ReturnsSortedItems() {
        Long companyId = incrementAndGetCompanyId(SORTED_BLOG_ENTRY_ID);
        incrementViewCount(SORTED_BLOG_ENTRY_ID);
        incrementViewCount(SORTED_BLOG_ENTRY_ID);
        incrementViewCount(OTHER_SORTED_BLOG_ENTRY_ID);

        List<Integer> defaultOrder = getViewCounts(companyId, null, null);
        assertViewCountsAreOrdered(defaultOrder, false);

        List<Integer> descendingOrder = getViewCounts(
                companyId, "VIEW_COUNT", "DESC");
        assertViewCountsAreOrdered(descendingOrder, false);

        List<Integer> ascendingOrder = getViewCounts(
                companyId, "VIEW_COUNT", "ASC");
        assertViewCountsAreOrdered(ascendingOrder, true);
    }

    @Test
    public void test04b_GetAllViewCounts_WithInvalidSort_Returns400() {
        RestAssured.given()
                .queryParam("sortAttribute", "unknownField")
                .queryParam("sortDirection", "DESC")
                .when()
                .get("/")
                .then()
                .statusCode(400);

        RestAssured.given()
                .queryParam("sortAttribute", "VIEW_COUNT")
                .when()
                .get("/")
                .then()
                .statusCode(400);

        RestAssured.given()
                .queryParam("sortAttribute", "VIEW_COUNT")
                .queryParam("sortDirection", "SIDEWAYS")
                .when()
                .get("/")
                .then()
                .statusCode(400);
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

    private static void assertViewCountsAreOrdered(
            List<Integer> viewCounts, boolean ascending) {

        assertTrue("Expected at least two rows to verify ordering", viewCounts.size() >= 2);

        for (int index = 1; index < viewCounts.size(); index++) {
            if (ascending) {
                assertTrue(
                        viewCounts.get(index - 1) <= viewCounts.get(index));
            } else {
                assertTrue(
                        viewCounts.get(index - 1) >= viewCounts.get(index));
            }
        }
    }

    private static List<Integer> getViewCounts(
            Long companyId, String sortAttribute, String sortDirection) {

        var request = RestAssured.given()
                .accept(ContentType.JSON)
                .queryParam("companyId", companyId)
                .queryParam("pageSize", 100);

        if (sortAttribute != null) {
            request.queryParam("sortAttribute", sortAttribute);
            request.queryParam("sortDirection", sortDirection);
        }

        return request.when()
                .get("/")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("items.viewCount", Integer.class);
    }

    private static Long incrementAndGetCompanyId(long blogEntryId) {
        return RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .pathParam("blogEntryId", blogEntryId)
                .when()
                .post("/{blogEntryId}/increment")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getLong("companyId");
    }

    private static void incrementViewCount(long blogEntryId) {
        RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .pathParam("blogEntryId", blogEntryId)
                .when()
                .post("/{blogEntryId}/increment")
                .then()
                .statusCode(200);
    }
}
