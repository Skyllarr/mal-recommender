package cz.muni.fi.pv254.init;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by suomiy on 12/2/15.
 */

@Provider
public class JacksonJSONProvider implements ContextResolver<ObjectMapper> {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.setSerializationInclusion(Include.NON_EMPTY);
    }

    public JacksonJSONProvider() {
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return MAPPER;
    }
}