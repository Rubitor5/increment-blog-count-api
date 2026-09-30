package com.example.blogstatistics.internal.events;

import com.example.blogstatistics.service.BlogStatisticsLocalService;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.LifecycleEvent;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        immediate = true,
        property = "key=application.startup.events",
        service = LifecycleAction.class
)
public class DataInitializerLifecycleAction implements LifecycleAction {

    private static final Log _log = LogFactoryUtil.getLog(DataInitializerLifecycleAction.class);

    @Reference
    private BlogStatisticsLocalService _blogStatisticsLocalService;

    @Activate
    public void activate() {
        initData();
    }

    @Override
    public void processLifecycleEvent(LifecycleEvent lifecycleEvent) {
        initData();
    }

    private void initData() {
        try {
            long companyId = PortalUtil.getDefaultCompanyId();

            // Check if DB already has records to prevent duplicate seeding
            int existingCount = _blogStatisticsLocalService.getBlogStatisticsesCount();

            if (existingCount > 0) {
                _log.info("Database already contains " + existingCount + " blog statistics records. Skipping fixture initialization.");
                return;
            }

            _log.info("Initializing sample fixture data for Blog Statistics...");

            // Create sample entries
            seedRecord(companyId, 1001L, 15);
            seedRecord(companyId, 1002L, 42);
            seedRecord(companyId, 1003L, 128);
            seedRecord(companyId, 1004L, 5);
            seedRecord(companyId, 1005L, 310);

            _log.info("Successfully seeded 5 initial blog statistics records!");

        } catch (Exception e) {
            _log.error("Failed to seed database statistics fixture", e);
        }
    }

    private void seedRecord(long companyId, long blogEntryId, int initialViews) {
        for (int i = 0; i < initialViews; i++) {
            _blogStatisticsLocalService.incrementViewCount(companyId, blogEntryId);
        }
    }
}
