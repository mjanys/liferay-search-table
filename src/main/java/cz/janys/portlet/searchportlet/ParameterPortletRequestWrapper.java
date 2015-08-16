package cz.janys.portlet.searchportlet;

import javax.portlet.PortletRequest;
import javax.portlet.filter.PortletRequestWrapper;
import java.util.*;

/**
 * @author Martin Janys
 */
public class ParameterPortletRequestWrapper extends PortletRequestWrapper {

    private final Map<String, String[]> additionalParameters;

    /**
     * Creates an <code>PortletRequest</code> adaptor
     * wrapping the given request object.
     *
     * @param request the portlet request to wrap
     * @throws IllegalArgumentException if the request is <code>null</code>
     */
    public ParameterPortletRequestWrapper(PortletRequest request) {
        super(request);
        this.additionalParameters = new HashMap<String, String[]>();
    }

    @Override
    public String getParameter(String name) {
        String[] parameterValues = getParameterValues(name);
        return parameterValues.length > 0 ? parameterValues[0] : null;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return new HashMap<String, String[]>(additionalParameters) {{
            putAll(ParameterPortletRequestWrapper.super.getParameterMap());
        }};
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(new HashSet<String>(additionalParameters.keySet()){{
            addAll(Collections.list(ParameterPortletRequestWrapper.super.getParameterNames()));
        }});
    }

    @Override
    public String[] getParameterValues(String name) {
        return additionalParameters.containsKey(name) ? additionalParameters.get(name) : super.getParameterValues(name);
    }

    public String[] put(String key, String[] value) {
        return additionalParameters.put(key, value);
    }

    public String[] put(String key, String value) {
        return additionalParameters.put(key, new String[]{value});
    }
}
