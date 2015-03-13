//package jsontocsv;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.util.Iterator;
//import java.util.LinkedHashMap;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import org.junit.Test;
//
//import com.fasterxml.jackson.core.JsonFactory;
//import com.fasterxml.jackson.core.JsonParseException;
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.MappingJsonFactory;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.ArrayNode;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//import com.fasterxml.jackson.databind.node.ValueNode;
//
//public class JsonToCsvFasterTest {
//
//    @Test
//    public void testCreatingFlatKeyValues() throws JsonParseException,
//            IOException {
//        JsonFactory f = new MappingJsonFactory();
//        JsonParser jp = f.createParser(new BufferedReader(
//                new InputStreamReader(getClass().getResourceAsStream(
//                        "cli-result.json"))));
//
//        Map<String, String> map = new LinkedHashMap<String, String>();
//        try {
//            JsonNode readTree = new ObjectMapper().readTree(jp);
//            addKeys("", readTree, map);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println(map);
//        for (Entry<String, String> entry : map.entrySet()) {
//            System.out.println(entry.getKey() + " = " + entry.getValue());
//
//        }
//    }
//
//    private void addKeys(String currentPath, JsonNode jsonNode,
//            Map<String, String> map) {
//        if (jsonNode.isObject()) {
//            ObjectNode objectNode = (ObjectNode) jsonNode;
//            Iterator<Map.Entry<String, JsonNode>> iter = objectNode.fields();
//            String pathPrefix = currentPath.isEmpty() ? "" : currentPath + ".";
//
//            while (iter.hasNext()) {
//                Map.Entry<String, JsonNode> entry = iter.next();
//                addKeys(pathPrefix + entry.getKey(), entry.getValue(), map);
//            }
//        } else if (jsonNode.isArray()) {
//            ArrayNode arrayNode = (ArrayNode) jsonNode;
//            for (int i = 0; i < arrayNode.size(); i++) {
//                addKeys(currentPath + "[" + i + "]", arrayNode.get(i), map);
//            }
//        } else if (jsonNode.isValueNode()) {
//            ValueNode valueNode = (ValueNode) jsonNode;
//            map.put(currentPath, valueNode.asText());
//        }
//    }
//
//}
