package jsontocsv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jackson.node.ValueNode;
import org.junit.Test;

/**
 * The Class JsonToCsvJacksonTest.
 * 
 * @author Andrea Battaglia (Red Hat)
 */
public class JsonToCsvJacksonTest {

    /**
     * Test creating flat key values.
     *
     * @throws JsonParseException
     *             the json parse exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void testCreatingFlatKeyValues() throws JsonParseException,
            IOException {
        JsonFactory f = new MappingJsonFactory();
        JsonParser jp = f.createJsonParser(new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream(
                        "cli-result.json"))));

        Map<String, String> map = new LinkedHashMap<String, String>();
        try {
            JsonNode readTree = new ObjectMapper().readTree(jp);
            addKeys("", readTree, map);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(map);
        for (Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());

        }
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
            // object -> recursive
            ObjectNode objectNode = (ObjectNode) jsonNode;
            Iterator<Map.Entry<String, JsonNode>> iter = objectNode.getFields();
            String pathPrefix = currentPath.isEmpty() ? "" : currentPath + ".";

            while (iter.hasNext()) {
                Map.Entry<String, JsonNode> entry = iter.next();
                addKeys(pathPrefix + entry.getKey(), entry.getValue(), map);
            }
        } else if (jsonNode.isArray()) {
            // array -> recursive adding a child key per array element name!
            ArrayNode arrayNode = (ArrayNode) jsonNode;
            for (int i = 0; i < arrayNode.size(); i++) {
                addKeys(currentPath + "[" + i + "]", arrayNode.get(i), map);
            }
        } else if (jsonNode.isValueNode()) {
            // value -> print
            ValueNode valueNode = (ValueNode) jsonNode;
            map.put(currentPath, valueNode.asText());
        }
    }

}
