package com.example.blog.statistics.rest.application;


import com.example.blog.statistics.rest.dto.BlogStatisticsDTO;
import com.example.blog.statistics.rest.dto.PagedResponseDTO;
import com.example.blog.statistics.rest.service.BlogStatisticsResourceService;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Collections;
import java.util.Set;

@Component(
		property = {
				"osgi.jaxrs.application.base=/blog-statistics",
				"osgi.jaxrs.name=BlogStatistics.Rest",
				"auth.verifier.guest.allowed=true",
				"liferay.access.control.disable=true"
		},
		service = Application.class
)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BlogStatisticsRestApplication extends Application {

	@Reference
	private BlogStatisticsResourceService resourceService;

	@Override
	public Set<Object> getSingletons() {
		return Collections.singleton(this);
	}

	/**
	 * 1. Obtain a list with all the view counts (Supports pagination & filtering)
	 * GET /o/blog-statistics/
	 */
	@GET
	@Path("/")
	public Response getAllViewCounts(
			@QueryParam("companyId") Long companyId,
			@QueryParam("blogEntryId") Long blogEntryId,
			@QueryParam("minViews") Long minViews,
			@QueryParam("maxViews") Long maxViews,
			@QueryParam("page") @DefaultValue("1") int page,
			@QueryParam("pageSize") @DefaultValue("20") int pageSize) {

		PagedResponseDTO response = resourceService.getPagedStats(
				companyId, blogEntryId, minViews, maxViews, page, pageSize);

		return Response.ok(response).build();
	}

	/**
	 * 2. Obtain the view count of a specific blog entry
	 * GET /o/blog-statistics/{blogEntryId}
	 */
	@GET
	@Path("/{blogEntryId}")
	public Response getViewCountByBlogEntryId(
			@PathParam("blogEntryId") long blogEntryId,
			@QueryParam("companyId") Long companyId) {

		BlogStatisticsDTO dto = resourceService.getViewCount(blogEntryId, companyId);

		if (dto == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.ok(dto).build();
	}

	/**
	 * 3. Increase the view count of a specific blog entry
	 * POST /o/blog-statistics/{blogEntryId}/increment
	 */
	@POST
	@Path("/{blogEntryId}/increment")
	public Response incrementViewCount(
			@PathParam("blogEntryId") long blogEntryId,
			@QueryParam("companyId") Long companyId) {

		BlogStatisticsDTO dto = resourceService.incrementViewCount(blogEntryId, companyId);

		return Response.ok(dto).build();
	}

	/**
	 * 4. Delete a specific blog entry
	 * DELETE /o/blog-statistics/{blogEntryId}
	 */
	@DELETE
	@Path("/{blogEntryId}")
	public Response deleteByBlogEntryId(
			@PathParam("blogEntryId") long blogEntryId,
			@QueryParam("companyId") Long companyId) {

		try {
			resourceService.deleteViewCount(blogEntryId, companyId);
			return Response.noContent().build();
		} catch (Exception e) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	/**
	 * 5. Delete all the statistics
	 * DELETE /o/blog-statistics/
	 */
	@DELETE
	@Path("/")
	public Response deleteAllStatistics() {
		resourceService.deleteAll();
		return Response.noContent().build();
	}
}