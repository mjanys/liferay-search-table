package cz.janys.portlet.searchportlet.config;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

/**
 * @author Alexander Chow
 */
public class ConfigurationActionImpl extends DefaultConfigurationAction {

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		boolean advancedConfiguration = GetterUtil.getBoolean(
				getParameter(actionRequest, "advancedConfiguration"));

		if (!advancedConfiguration) {
			updateBasicConfiguration(
				portletConfig, actionRequest, actionResponse);
		}

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	protected void updateBasicConfiguration(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		boolean displayScopeFacet = GetterUtil.getBoolean(
				getParameter(actionRequest, "displayScopeFacet"));
		boolean displayAssetCategoriesFacet = GetterUtil.getBoolean(
				getParameter(actionRequest, "displayAssetCategoriesFacet"));
		boolean displayAssetTagsFacet = GetterUtil.getBoolean(
				getParameter(actionRequest, "displayAssetTagsFacet"));
		boolean displayAssetTypeFacet = GetterUtil.getBoolean(
				getParameter(actionRequest, "displayAssetTypeFacet"));
		boolean displayFolderFacet = GetterUtil.getBoolean(
				getParameter(actionRequest, "displayFolderFacet"));
		boolean displayUserFacet = GetterUtil.getBoolean(
				getParameter(actionRequest, "displayUserFacet"));
		boolean displayModifiedRangeFacet = GetterUtil.getBoolean(
				getParameter(actionRequest, "displayModifiedRangeFacet"));

		String searchConfiguration = PropsUtil.get(PropsKeys.SEARCH_FACET_CONFIGURATION);

		JSONObject configurationJSONObject = JSONFactoryUtil.createJSONObject(
				searchConfiguration);

		JSONArray oldFacetsJSONArray = configurationJSONObject.getJSONArray(
			"facets");

		if (oldFacetsJSONArray == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"The resource " + PropsUtil.get(PropsKeys.SEARCH_FACET_CONFIGURATION) +
						" is missing a valid facets JSON array");
			}
		}

		JSONArray newFacetsJSONArray = JSONFactoryUtil.createJSONArray();

		for (int i = 0; i < oldFacetsJSONArray.length(); i++) {
			JSONObject oldFacetJSONObject = oldFacetsJSONArray.getJSONObject(i);

			String fieldName = oldFacetJSONObject.getString("fieldName");

			if ((displayScopeFacet && fieldName.equals("groupId")) ||
				(displayAssetCategoriesFacet &&
				 fieldName.equals("assetCategoryIds")) ||
				(displayAssetTagsFacet && fieldName.equals("assetTagNames")) ||
				(displayAssetTypeFacet && fieldName.equals("entryClassName")) ||
				(displayFolderFacet && fieldName.equals("folderId")) ||
				(displayUserFacet && fieldName.equals("userId")) ||
				(displayModifiedRangeFacet && fieldName.equals("modified"))) {

				newFacetsJSONArray.put(oldFacetJSONObject);
			}
		}

		configurationJSONObject.put("facets", newFacetsJSONArray);

		searchConfiguration = configurationJSONObject.toString();

		setPreference(
			actionRequest, "searchConfiguration", searchConfiguration);
	}

	private static Log _log = LogFactoryUtil.getLog(
			ConfigurationActionImpl.class);

}