package tools.openApiTest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import tools.openApiTest.entity.Order;

import java.io.IOException;

/**
 * Created by xuchun on 15/9/10.
 */
public class testPackage {
    public static void main(String[] args) {
                ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
        String str = "{\n" +
                "  \"return_order\": \"123\",\n" +
                "  \"packageDetails\": [\n" +
                "    {\n" +
                "      \"packageDetail\": {\n" +
                "        \"ship_date\": \"\",\n" +
                "        \"weight\": \"33\",\n" +
                "        \"out_sid\": \"1\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"packageDetail\": {\n" +
                "        \"ship_date\": \"\",\n" +
                "        \"weight\": \"33\",\n" +
                "        \"out_sid\": \"2\"\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        try {
            Order test = mapper.readValue(str, Order.class);
            System.out.println(mapper.writeValueAsString(test));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
