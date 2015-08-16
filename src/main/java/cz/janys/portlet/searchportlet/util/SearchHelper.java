package cz.janys.portlet.searchportlet.util;

import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.facet.AssetEntriesFacet;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.ScopeFacet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import com.liferay.portal.kernel.search.facet.config.FacetConfigurationUtil;
import com.liferay.portal.kernel.search.facet.util.FacetFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortalUtil;
import org.springframework.stereotype.Component;

import javax.portlet.PortletPreferences;
import javax.portlet.ResourceRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cz.janys.portlet.searchportlet.SearchTableConstants.SEARCH_CONFIGURATION;

/**
 * @author Martin Janys
 */
@Component
public class SearchHelper {

    public final Map<String, String> defaultPreferences = new HashMap<String, String>() {{
        put("collatedSpellCheckResultEnabled", PropsUtil.get(PropsKeys.INDEX_SEARCH_COLLATED_SPELL_CHECK_RESULT_ENABLED));
        put("collatedSpellCheckResultDisplayThreshold", PropsUtil.get(PropsKeys.INDEX_SEARCH_COLLATED_SPELL_CHECK_RESULT_SCORES_THRESHOLD));
        put("queryIndexingEnabled", PropsUtil.get(PropsKeys.INDEX_SEARCH_QUERY_INDEXING_ENABLED));
        put("queryIndexingThreshold", PropsUtil.get(PropsKeys.INDEX_SEARCH_QUERY_INDEXING_THRESHOLD));
        put("querySuggestionsEnabled", PropsUtil.get(PropsKeys.INDEX_SEARCH_QUERY_SUGGESTION_ENABLED));
        put("querySuggestionsDisplayThreshold", PropsUtil.get(PropsKeys.INDEX_SEARCH_QUERY_SUGGESTION_SCORES_THRESHOLD));
        put("querySuggestionsMax", PropsUtil.get(PropsKeys.INDEX_SEARCH_QUERY_SUGGESTION_MAX));
    }};

    /**
     * @see /portal-web/docroot/html/portlet/search/main_search.jspf
     * @param request
     * @param portletPreferences
     * @return
     */
    public SearchContext createSearchContext(ResourceRequest request, PortletPreferences portletPreferences, int start, int end) throws Exception {
        SearchContext searchContext = SearchContextFactory.getInstance(PortalUtil.getHttpServletRequest(request));
        searchContext.setQueryConfig(createQueryConfig(portletPreferences));

        searchContext.setAttribute("paginationType", "more");
        searchContext.setStart(start);
        searchContext.setEnd(end);

        searchContext.addFacet(createAssetEntriesFacet(searchContext));
        searchContext.addFacet(createScopeFacet(searchContext));

        addConfiguredFacets(searchContext, portletPreferences);

        return searchContext;
    }

    private void addConfiguredFacets(SearchContext searchContext, PortletPreferences portletPreferences) throws Exception {
        List<FacetConfiguration> facetConfigurations = getFacetConfigurations(portletPreferences);
        for (FacetConfiguration facetConfiguration : facetConfigurations) {
            Facet facet = FacetFactoryUtil.create(searchContext, facetConfiguration);
            searchContext.addFacet(facet);
        }
    }

    public List<FacetConfiguration> getFacetConfigurations(PortletPreferences portletPreferences) {
        String searchConfiguration = portletPreferences.getValue(SEARCH_CONFIGURATION, StringPool.BLANK);

        return FacetConfigurationUtil.load(searchConfiguration);
    }

    public FacetConfiguration getFacetConfiguration(PortletPreferences portletPreferences, Class<?> facet) {
        List<FacetConfiguration> facetConfigurations = getFacetConfigurations(portletPreferences);
        for (FacetConfiguration facetConfiguration : facetConfigurations) {
            if (facetConfiguration.getClassName().equals(facet.getName())) {
                return facetConfiguration;
            }
        }
        return null;
    }

    /**
     * @see /portal-web/docroot/html/portlet/search/main_search.jspf
     * @param searchContext
     * @return
     */
    private Facet createAssetEntriesFacet(SearchContext searchContext) {
        Facet assetEntriesFacet = new AssetEntriesFacet(searchContext);
        assetEntriesFacet.setStatic(true);
        return assetEntriesFacet;
    }

    /**
     * @see /portal-web/docroot/html/portlet/search/main_search.jspf
     * @param searchContext
     * @return
     */
    private Facet createScopeFacet(SearchContext searchContext) {
        Facet scopeFacet = new ScopeFacet(searchContext);
        scopeFacet.setStatic(true);
        return scopeFacet;
    }

    /**
     * @see /portal-web/docroot/html/portlet/search/init.jsp
     * @param preferences
     * @return
     */
    private QueryConfig createQueryConfig(PortletPreferences preferences) {
        QueryConfig queryConfig = new QueryConfig();

        boolean collatedSpellCheckResultEnabled = GetterUtil.getBoolean(preferences.getValue("collatedSpellCheckResultEnabled", null), GetterUtil.getBoolean(defaultPreferences.get("collatedSpellCheckResultEnabled")));
        int collatedSpellCheckResultDisplayThreshold = GetterUtil.getInteger(preferences.getValue("collatedSpellCheckResultDisplayThreshold", null), GetterUtil.getInteger("collatedSpellCheckResultDisplayThreshold"));
        if (collatedSpellCheckResultDisplayThreshold < 0) {
            collatedSpellCheckResultDisplayThreshold =  GetterUtil.getInteger("collatedSpellCheckResultDisplayThreshold");
        }
        boolean queryIndexingEnabled = GetterUtil.getBoolean(preferences.getValue("queryIndexingEnabled", null), GetterUtil.getBoolean(defaultPreferences.get("queryIndexingEnabled")));
        int queryIndexingThreshold = GetterUtil.getInteger(preferences.getValue("queryIndexingThreshold", null), GetterUtil.getInteger(defaultPreferences.get("queryIndexingThreshold")));
        if (queryIndexingThreshold < 0) {
            queryIndexingThreshold = GetterUtil.getInteger(defaultPreferences.get("queryIndexingThreshold"));
        }
        boolean querySuggestionsEnabled = GetterUtil.getBoolean(preferences.getValue("querySuggestionsEnabled", null), GetterUtil.getBoolean(defaultPreferences.get("querySuggestionsEnabled")));
        int querySuggestionsDisplayThreshold = GetterUtil.getInteger(preferences.getValue("querySuggestionsDisplayThreshold", null), GetterUtil.getInteger(defaultPreferences.get("querySuggestionsDisplayThreshold")));

        if (querySuggestionsDisplayThreshold < 0) {
            querySuggestionsDisplayThreshold = GetterUtil.getInteger(defaultPreferences.get("querySuggestionsDisplayThreshold"));
        }
        int querySuggestionsMax = GetterUtil.getInteger(preferences.getValue("querySuggestionsMax", null), GetterUtil.getInteger(defaultPreferences.get("querySuggestionsMax")));

        if (querySuggestionsMax <= 0) {
            querySuggestionsMax = GetterUtil.getInteger(defaultPreferences.get("querySuggestionsMax"));
        }

        queryConfig.setCollatedSpellCheckResultEnabled(collatedSpellCheckResultEnabled);
        queryConfig.setCollatedSpellCheckResultScoresThreshold(collatedSpellCheckResultDisplayThreshold);
        queryConfig.setHighlightEnabled(true);
        queryConfig.setQueryIndexingEnabled(queryIndexingEnabled);
        queryConfig.setQueryIndexingThreshold(queryIndexingThreshold);
        queryConfig.setQuerySuggestionEnabled(querySuggestionsEnabled);
        queryConfig.setQuerySuggestionScoresThreshold(querySuggestionsDisplayThreshold);
        queryConfig.setQuerySuggestionsMax(querySuggestionsMax);

        return queryConfig;
    }

}
