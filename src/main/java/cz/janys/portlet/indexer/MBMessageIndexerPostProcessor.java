package cz.janys.portlet.indexer;

import com.liferay.portal.kernel.search.BaseIndexerPostProcessor;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Summary;

import javax.portlet.PortletURL;
import java.util.Locale;

/**
 * @author Martin Janys
 */
public class MBMessageIndexerPostProcessor extends BaseIndexerPostProcessor {

    @Override
    public void postProcessSummary(Summary summary, Document document, Locale locale, String snippet, PortletURL portletURL) {
        summary.setLocale(locale);
    }
}
