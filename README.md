# liferay-search-table

## About

New portlet with customization of Liferay seach portlet.
Portlet is avaiable under Tools > Search portlet in applications

## Preview

![Preview](https://raw.githubusercontent.com/mjanys/liferay-search-table/master/preview.png)

## Configuration
Under asset_entries configure data
* data
  * values # array of one mode, only one model is allowed
  * seachTable
    * columns # document fields to display
    * dateColumns # columns with date attributes
    
### Example
```javascript
{"facets": [
    {
        "displayStyle": "scopes",
        "fieldName": "groupId",
        "static": false,
        "data": {
            "maxTerms": 10,
            "showAssetCount": true,
            "frequencyThreshold": 1
        },
        "weight": 1.6,
        "className": "com.liferay.portal.kernel.search.facet.ScopeFacet",
        "label": "site",
        "order": "OrderHitsDesc"
    },
    {
        "displayStyle": "asset_entries",
        "fieldName": "entryClassName",
        "static": true,
        "data": {
            "frequencyThreshold": 1,
            "values": ["com.liferay.portlet.messageboards.model.MBMessage"],
            "searchTable": {
                "columns": [
                    "title",
                    "content",
                    "userName",
                    "createDate",
                    "publishDate",
                    "modified"
                ],
                "dateColumns": [
                    "publishDate",
                    "modified",
                    "createDate",
                    "expirationDate",
                    "publishDate"
                ]
            }
        },
        "weight": 1.5,
        "className": "com.liferay.portal.kernel.search.facet.AssetEntriesFacet",
        "label": "asset-type",
        "order": "OrderHitsDesc"
    },
    {
        "displayStyle": "asset_tags",
        "fieldName": "assetTagNames",
        "static": false,
        "data": {
            "displayStyle": "list",
            "maxTerms": 10,
            "showAssetCount": true,
            "frequencyThreshold": 1
        },
        "weight": 1.4,
        "className": "com.liferay.portal.kernel.search.facet.MultiValueFacet",
        "label": "tag",
        "order": "OrderHitsDesc"
    },
    {
        "displayStyle": "asset_categories",
        "fieldName": "assetCategoryIds",
        "static": false,
        "data": {
            "displayStyle": "list",
            "maxTerms": 10,
            "showAssetCount": true,
            "frequencyThreshold": 1
        },
        "weight": 1.3,
        "className": "com.liferay.portal.kernel.search.facet.MultiValueFacet",
        "label": "category",
        "order": "OrderHitsDesc"
    },
    {
        "displayStyle": "folders",
        "fieldName": "folderId",
        "static": false,
        "data": {
            "maxTerms": 10,
            "showAssetCount": true,
            "frequencyThreshold": 1
        },
        "weight": 1.2,
        "className": "com.liferay.portal.kernel.search.facet.MultiValueFacet",
        "label": "folder",
        "order": "OrderHitsDesc"
    },
    {
        "displayStyle": "users",
        "fieldName": "userName",
        "static": false,
        "data": {
            "maxTerms": 10,
            "showAssetCount": true,
            "frequencyThreshold": 1
        },
        "weight": 1.1,
        "className": "com.liferay.portal.kernel.search.facet.MultiValueFacet",
        "label": "user",
        "order": "OrderHitsDesc"
    },
    {
        "displayStyle": "modified",
        "fieldName": "modified",
        "static": false,
        "data": {
            "ranges": [
                {
                    "range": "[past-hour TO *]",
                    "label": "past-hour"
                },
                {
                    "range": "[past-24-hours TO *]",
                    "label": "past-24-hours"
                },
                {
                    "range": "[past-week TO *]",
                    "label": "past-week"
                },
                {
                    "range": "[past-month TO *]",
                    "label": "past-month"
                },
                {
                    "range": "[past-year TO *]",
                    "label": "past-year"
                }
            ],
            "frequencyThreshold": 0
        },
        "weight": 1,
        "className": "com.liferay.portal.kernel.search.facet.ModifiedFacet",
        "label": "modified",
        "order": "OrderHitsDesc"
    }
]}
```
