/**
 * 
 */
package com.redhat.it.customers.dmc.core.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jackson.node.ValueNode;
import org.jboss.dmr.ModelNode;

/**
 * The Class JsonFunctions.
 *
 * @author Andrea Battaglia (Red Hat)
 */
@Singleton
public final class JsonFunctions {
    private JsonFactory f = new MappingJsonFactory();

    /**
     * Creates the flat key values.
     *
     * @param response
     *            the response
     * @return the map
     * @throws JsonParseException
     *             the json parse exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public Map<String, String> createFlatKeyValues(ModelNode response)
            throws JsonParseException, IOException {
        return createFlatKeyValues(response.toJSONString(true));
    }

    /**
     * Test create flat key values.
     *
     * @throws JsonParseException
     *             the json parse exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public Map<String, String> createFlatKeyValues(String jsonString)
            throws JsonParseException, IOException {
        JsonParser jp = f.createJsonParser(jsonString);

        Map<String, String> map = new LinkedHashMap<String, String>();
        try {
            JsonNode readTree = new ObjectMapper().readTree(jp);
            addKeys("", readTree, map);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // System.out.println(map);
        // for (Entry<String, String> entry : map.entrySet()) {
        // System.out.println(entry.getKey() + " = " + entry.getValue());
        // }
        return map;
    }

    /**
     * Adds the keys.
     *
     * @param currentPath
     *            the current path
     * @param jsonNode
     *            the json node
     * @param map
     *            the map
     */
    private void addKeys(String currentPath, JsonNode jsonNode,
            Map<String, String> map) {
        if (jsonNode.isObject()) {
            ObjectNode objectNode = (ObjectNode) jsonNode;
            Iterator<Map.Entry<String, JsonNode>> iter = objectNode.getFields();
            String pathPrefix = currentPath.isEmpty() ? "" : currentPath + ".";

            while (iter.hasNext()) {
                Map.Entry<String, JsonNode> entry = iter.next();
                addKeys(pathPrefix + entry.getKey(), entry.getValue(), map);
            }
        } else if (jsonNode.isArray()) {
            ArrayNode arrayNode = (ArrayNode) jsonNode;
            for (int i = 0; i < arrayNode.size(); i++) {
                addKeys(currentPath + "[" + i + "]", arrayNode.get(i), map);
            }
        } else if (jsonNode.isValueNode()) {
            ValueNode valueNode = (ValueNode) jsonNode;
            map.put(currentPath, valueNode.asText());
        }
    }

}
