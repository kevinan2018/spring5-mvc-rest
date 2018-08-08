package guru.springframework.controllers.v1;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractRestControllerTest {
    public static String asJsonString(final Object obj) {
        try {
            String jstr = new ObjectMapper().writeValueAsString(obj);
            return jstr;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
