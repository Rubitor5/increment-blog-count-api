package com.example.blogstatistics.internal.filter;

import com.example.blogstatistics.service.BlogStatisticsLocalService;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.PortletFilter;
import javax.portlet.filter.RenderFilter;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.io.IOException;

/**
 * Portlet Filter that intercepts rendering of the Liferay Blogs Portlet
 * and automatically increments the view count in BlogStatisticsLocalService.
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=com_liferay_blogs_web_portlet_BlogsPortlet",
		"javax.portlet.name=com_liferay_blogs_web_portlet_BlogsAggregatorPortlet"
	},
	service = PortletFilter.class
)
public class BlogViewCountPortletFilter implements RenderFilter {

	private static final Log _log = LogFactoryUtil.getLog(BlogViewCountPortletFilter.class);

	@Reference
	private BlogStatisticsLocalService _blogStatisticsLocalService;

	@Reference
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Override
	public void init(FilterConfig filterConfig) throws PortletException {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(
			RenderRequest request, RenderResponse response, FilterChain chain)
		throws IOException, PortletException {

		try {
			long blogEntryId = ParamUtil.getLong(request, "entryId");
			String urlTitle = ParamUtil.getString(request, "urlTitle");
			long companyId = PortalUtil.getCompanyId(request);

			if (blogEntryId <= 0 && Validator.isNotNull(urlTitle)) {
				long scopeGroupId = PortalUtil.getScopeGroupId(request);
				BlogsEntry entry = _blogsEntryLocalService.fetchEntry(scopeGroupId, urlTitle);
				if (entry != null) {
					blogEntryId = entry.getEntryId();
				}
			}

			if (blogEntryId > 0 && companyId > 0) {
				_blogStatisticsLocalService.incrementViewCount(companyId, blogEntryId);
				if (_log.isDebugEnabled()) {
					_log.debug("Auto-incremented view count for blogEntryId: " + blogEntryId);
				}
			}
		} catch (Exception e) {
			_log.error("Error auto-incrementing view count in BlogViewCountPortletFilter", e);
		}

		chain.doFilter(request, response);
	}
}
