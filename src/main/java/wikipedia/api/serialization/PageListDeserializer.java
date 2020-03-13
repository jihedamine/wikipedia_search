package wikipedia.api.serialization;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import wikipedia.api.serialization.types.Page;
import wikipedia.api.serialization.types.PageList;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Custom deserializer for a json pages query result from WikiMedia's API
 * We need a custom deserializer for the pages because their keys are the pages ids
 * which are unknown at compile time and hence cannot be modeled as a field in a GSon type
 */
public class PageListDeserializer implements JsonDeserializer<PageList> {

    /**
     * {@inheritDoc}
     * Custom deserializer for a json pages query result from WikiMedia's API
     */
    @Override
    public PageList deserialize(JsonElement pageListElement, Type type,
                                                 JsonDeserializationContext context) throws JsonParseException {
        JsonObject pageListObject = pageListElement.getAsJsonObject();
        List<Page> pageList = new ArrayList<>();
        for (Map.Entry<String, JsonElement> pageListEntry : pageListObject.entrySet()) {
            // For individual Page entry values, we can use default deserialization
            Page page = context.deserialize(pageListEntry.getValue(), Page.class);
            pageList.add(page);
        }
        return new PageList(pageList);
    }

}
