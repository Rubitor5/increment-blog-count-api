# Solution decisions

I built this as a Liferay 7.4 GA132 workspace. I used Service Builder for the
statistics table and local service because it generates the model and
persistence code in the way Liferay expects. Each record is identified by a
company and blog entry, so the same entry can be tracked separately within
Liferay's company scope.

I split the work into a service API, its implementation, and a REST module.
The REST module handles HTTP parameters and responses; the local service owns
the statistics operations and database queries. The REST code calls service
interfaces through OSGi rather than depending on implementation classes. That
keeps the modules separate and lets me test the REST mapping without starting
the whole portal.

For the list endpoint, I added filters, pagination, and sorting. The REST
query uses enum values for sort fields and direction, so callers can choose
only supported fields rather than pass arbitrary query properties. The
resource-service maps those parameters into `Filter` and `PageRequest`; the
local service applies the DynamicQuery ordering. If no sort is given, results
are ordered by view count descending.

I also added a render filter for Liferay's Blogs portlets. It gets the blog
entry ID from the render request, or resolves it from the URL title, then asks
the local service to increment the count. This counts renders, not unique
visitors, and the increment logic would need more work to guarantee accuracy
under heavy concurrent traffic.

I added a small data initializer to seed the statistics table when it is empty. 
It creates sample counts for a few blog entry IDs, so I can test the API’s listing,
filtering, pagination, and sorting directly without needing to create blog entries 
or use the UI. These records are only fixtures for development and testing.

I wrote unit tests for the local-service behavior, REST-to-service mapping,
and render filter. There is also a RestAssured test for the HTTP endpoints. I
did not make starting and managing the portal part of the normal test run; I
used focused unit tests for quick feedback instead.

In the beginning I had a big build issue. The autogeneration of the workspace used 
plugin  `17.1.5` which was not compatible with the ServiceBuilder version, driving compilation
to fail. So after some hours and a lot of research identified this issue and downgraded
to `15.0.0`. 

The workspace targets Portal 7.4 GA132, uses the
Gradle 8.9 wrapper, and I used Java 17 to run the tests.

Before production use, I would review access
controls, since the sample REST application allows guest access and disables
Liferay access control, and decide how to handle bots, duplicate views, and
concurrent increments.
