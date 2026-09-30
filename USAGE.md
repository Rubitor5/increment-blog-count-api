# Blog Statistics REST API Usage

This guide shows how to call the Blog Statistics REST API with `curl`. It
assumes Liferay is running and the API modules have been deployed. The default
local base URL is `http://localhost:8080`; change it to match your environment.

```bash
export BASE_URL="http://localhost:8080"
export LIFERAY_USER="test@liferay.com"
export LIFERAY_PASSWORD="test"
export API="$BASE_URL/o/blog-statistics"
```

The examples use Basic authentication. This application is configured to allow
guest access, but authentication behavior can also depend on the deployment's
Liferay configuration. Use an account authorized for the environment if the
request requires authentication.

## Build, initialize, and run locally

Run these commands from the workspace root. The project targets Liferay Portal
7.4 GA132 and the Gradle wrapper downloads the configured Gradle 8.9
distribution. Use Java 17 for this workspace and its tests.

### Select Java 17

On macOS, select an installed Java 17 runtime:

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
export PATH="$JAVA_HOME/bin:$PATH"
java -version
```

Confirm the output reports version `17`. On Linux, set `JAVA_HOME` to the
installed JDK 17 directory and put its `bin` directory first on `PATH`, for
example:

```bash
export JAVA_HOME="/path/to/jdk-17"
export PATH="$JAVA_HOME/bin:$PATH"
java -version
```

### Generate Service Builder code when needed

If you changed `service.xml` or a local-service method signature, regenerate
the Service Builder output before compiling:

```bash
./gradlew :modules:blog-statistics-service:blog-statistics-service-service:buildService
```

Do not hand-edit generated model, persistence, or local-service API classes.
The generated sources are part of the Service Builder workflow.

### Compile the modules

To compile and package the modules without running tests:

```bash
./gradlew clean assemble
```

The REST HTTP test class uses RestAssured against a running server, so avoid a
blanket `./gradlew test` before Liferay is deployed and available. To run the
server-independent focused unit tests after setting up Java 17:

```bash
./gradlew \
  :modules:blog-statistics-rest:test \
  --tests com.example.blog.statistics.rest.service.impl.BlogStatisticsResourceServiceImplTest \
  :modules:blog-statistics-service:blog-statistics-service-service:test
```

### Initialize, deploy, and start Liferay with Blade

Install/configure the Liferay Blade CLI first. From the workspace root,
initialize the bundle configured by this workspace, deploy the built modules,
and start the server:

```bash
blade gw initBundle
blade gw deploy
blade server run
```

Keep the server process running while using the cURL examples below. By
default, the local REST API is at `http://localhost:8080/o/blog-statistics`.
The same workspace tasks can also be run through `./gradlew` (`initBundle` and
`deploy`) when preferred; Blade is used above for the server lifecycle.

## Resource overview

| Method | Path | Purpose | Typical response |
|---|---|---|---|
| `GET` | `/` | List statistics with filters, pagination, and sorting | `200 OK` |
| `GET` | `/{blogEntryId}` | Get one blog entry's statistics | `200 OK`, or `404 Not Found` |
| `POST` | `/{blogEntryId}/increment` | Create a statistics record or increment its count | `200 OK` |
| `DELETE` | `/{blogEntryId}` | Delete one blog entry's statistics | `204 No Content`, or `404 Not Found` |
| `DELETE` | `/` | Delete all statistics records | `204 No Content` |

All routes are below `$API`, i.e. `/o/blog-statistics`. The single-record routes
accept an optional `companyId` query parameter. When it is omitted or is
non-positive, the current Liferay company is used.

## Increment a view count

An increment creates the `(companyId, blogEntryId)` statistics row with a count
of one when it does not exist; otherwise, it increments the existing count.
No request body is needed.

```bash
curl -i -u "$LIFERAY_USER:$LIFERAY_PASSWORD" \
  -X POST "$API/99001/increment"
```

Target a specific company:

```bash
curl -i -u "$LIFERAY_USER:$LIFERAY_PASSWORD" \
  -X POST "$API/99001/increment?companyId=20101"
```

The response is a `BlogStatisticsDTO`, for example:

```json
{
  "blogStatisticsId": 1,
  "companyId": 20101,
  "blogEntryId": 99001,
  "viewCount": 1,
  "createDate": "2026-09-30 12:00:00.0",
  "modifiedDate": "2026-09-30 12:00:00.0"
}
```

## Get one entry's statistics

```bash
curl -i -u "$LIFERAY_USER:$LIFERAY_PASSWORD" \
  "$API/99001"
```

To query a specific company:

```bash
curl -i -u "$LIFERAY_USER:$LIFERAY_PASSWORD" \
  "$API/99001?companyId=20101"
```

The response has the same shape as the increment response. If no matching
record exists in the selected company, the endpoint responds with `404`.

## List, filter, paginate, and sort

The list endpoint returns a paged response. With no query parameters, it uses
page `1`, page size `20`, and sorts by `VIEW_COUNT DESC`.

| Query parameter | Description | Behavior |
|---|---|---|
| `companyId` | Company scope | Current Liferay company if omitted or non-positive |
| `blogEntryId` | Exact blog entry filter | No entry filter if omitted or non-positive |
| `minViews` | Inclusive lower view-count bound | Ignored when omitted or negative |
| `maxViews` | Inclusive upper view-count bound | Ignored when omitted or negative |
| `page` | One-based page number | Defaults to `1`; values below `1` are normalized to `1` |
| `pageSize` | Items per page | Defaults to `20`; values below `1` are normalized to `1` |
| `sortAttribute` | Sort field enum key | Defaults to `VIEW_COUNT` when sorting is omitted |
| `sortDirection` | Sort direction enum key | Defaults to `DESC` when sorting is omitted |

Supported `sortAttribute` values are `BLOG_STATISTICS_ID`, `COMPANY_ID`,
`BLOG_ENTRY_ID`, `VIEW_COUNT`, `CREATE_DATE`, and `MODIFIED_DATE`. Supported
`sortDirection` values are `ASC` and `DESC`. These are enum keys and are
case-sensitive. If supplying a sort, pass both parameters; supplying only one
returns `400 Bad Request`.

### Basic list and filter examples

```bash
# All records in the current company, default ordering.
curl -i -u "$LIFERAY_USER:$LIFERAY_PASSWORD" "$API/"

# An explicit company and a blog entry.
curl -i -u "$LIFERAY_USER:$LIFERAY_PASSWORD" \
  "$API/?companyId=20101&blogEntryId=99001"

# Inclusive range of view counts.
curl -i -u "$LIFERAY_USER:$LIFERAY_PASSWORD" \
  "$API/?minViews=10&maxViews=100"
```

### Pagination and sorting examples

```bash
# Page two, 25 records per page.
curl -i -u "$LIFERAY_USER:$LIFERAY_PASSWORD" \
  "$API/?page=2&pageSize=25"

# Lowest view counts first.
curl -i -u "$LIFERAY_USER:$LIFERAY_PASSWORD" \
  "$API/?sortAttribute=VIEW_COUNT&sortDirection=ASC"

# Most recently modified first.
curl -i -u "$LIFERAY_USER:$LIFERAY_PASSWORD" \
  "$API/?sortAttribute=MODIFIED_DATE&sortDirection=DESC"

# Combine filters, paging, and ordering.
curl -i -u "$LIFERAY_USER:$LIFERAY_PASSWORD" \
  "$API/?companyId=20101&minViews=10&maxViews=500&page=1&pageSize=50&sortAttribute=VIEW_COUNT&sortDirection=DESC"
```

The paged response contains `items`, `page`, `pageSize`, `totalCount`, and
`totalPages`, e.g.:

```json
{
  "items": [
    {
      "blogStatisticsId": 1,
      "companyId": 20101,
      "blogEntryId": 99001,
      "viewCount": 10,
      "createDate": "2026-09-30 12:00:00.0",
      "modifiedDate": "2026-09-30 12:00:00.0"
    }
  ],
  "page": 1,
  "pageSize": 20,
  "totalCount": 1,
  "totalPages": 1
}
```

Invalid enum values, or a partial sort pair, are expected to return `400`.

## Delete one entry

The optional `companyId` works the same way as it does for the GET and POST
single-entry endpoints.

```bash
curl -i -u "$LIFERAY_USER:$LIFERAY_PASSWORD" \
  -X DELETE "$API/99001"

# Specify a company explicitly.
curl -i -u "$LIFERAY_USER:$LIFERAY_PASSWORD" \
  -X DELETE "$API/99001?companyId=20101"
```

On success the response is `204 No Content`. A missing record is reported as
`404 Not Found`.

## Delete all entries

```bash
curl -i -u "$LIFERAY_USER:$LIFERAY_PASSWORD" \
  -X DELETE "$API/"
```

This removes **every** Blog Statistics record; it is not scoped to the current
company. Use it only when that is intended, preferably in a disposable test
environment. Success returns `204 No Content`.
