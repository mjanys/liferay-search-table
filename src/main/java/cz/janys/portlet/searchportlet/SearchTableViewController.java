package cz.janys.portlet.searchportlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.facet.AssetEntriesFacet;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;
import cz.janys.portlet.searchportlet.util.SearchHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import static cz.janys.portlet.searchportlet.SearchTableConstants.*;

/**
 * This class is base controller for VIEW mode.
 */
@Controller
@RequestMapping("VIEW")
public class SearchTableViewController {

    private static final Logger log = Logger.getLogger(SearchTableViewController.class);

    @Autowired
    private SearchHelper searchHelper;

    @RenderMapping
    public String defaultView(RenderRequest request,
                              RenderResponse response,
                              PortletPreferences portletPreferences,
                              @RequestParam(value = PARAM_KEYWORDS, required = false) String keyword) throws SystemException, PortalException {
        if (!isConfiguredCorrectly(portletPreferences)) {
            return errorMessage();
        }
        else {
            return searchResult(portletPreferences, request, keyword);
        }
    }

    private String errorMessage() {
        return VIEW_ERROR_MESSAGE;
    }

    private boolean isConfiguredCorrectly(PortletPreferences portletPreferences) {
        FacetConfiguration facetConfiguration = searchHelper.getFacetConfiguration(portletPreferences, AssetEntriesFacet.class);
        if (facetConfiguration == null)
            return false;
        JSONObject data = facetConfiguration.getData();
        if (data == null)
            return false;
        JSONArray jsonArray = data.getJSONArray(KEY_VALUES);
        if (jsonArray == null)
            return false;

        return jsonArray.length() == 1;
    }

    private String view(RenderRequest request) {
        return VIEW;
    }

    private String searchResult(PortletPreferences portletPreferences, RenderRequest request, String keyword) throws SystemException, PortalException {
        addSearchTableAttributes(request, portletPreferences);
        request.setAttribute(ATTR_KEYWORDS, resolveKeyword(request, keyword));

        return VIEW_SEARCH_RESULT;
    }

    private String resolveKeyword(RenderRequest request, String keyword) {
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            if (parameterName.startsWith("st-")) {
                String rawParameterName = parameterName.substring("st-".length());
                return  rawParameterName + ":" + request.getParameter(parameterName);
            }
        }
        return keyword;
    }

    private void addSearchTableAttributes(RenderRequest request, PortletPreferences portletPreferences) {
        FacetConfiguration facetConfiguration = searchHelper.getFacetConfiguration(portletPreferences, AssetEntriesFacet.class);
        if (facetConfiguration != null) {
            if (facetConfiguration.getData() != null && facetConfiguration.getData().getJSONObject(KEY_SEARCH_TABLE) != null) {
                JSONObject searchTableConfig = facetConfiguration.getData().getJSONObject(KEY_SEARCH_TABLE);

                request.setAttribute(ATTR_DATE_COLUMNS, asStringList(searchTableConfig.getJSONArray(KEY_DATE_COLUMNS)));
                request.setAttribute(ATTR_COLUMNS, asStringList(searchTableConfig.getJSONArray(KEY_COLUMNS)));}

        }
    }

    private List<String> asStringList(JSONArray jsonArray) {
        if (jsonArray == null) {
            return Collections.emptyList();
        }
        List<String> list = new ArrayList<String>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));
        }
        return list;
    }

}
