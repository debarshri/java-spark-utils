import com.google.common.collect.Maps;
import net.vidageek.mirror.dsl.Mirror;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

public class ClassUtils {
    public static <T> Map<String,Object> toMap(T aClass)
    {
        Field[] fields = aClass.getClass().getDeclaredFields();
        Map<String, Object> values = Maps.newHashMap();
        Mirror mirror = new Mirror();

        for(Field field:fields)
        {
            String name = field.getName();
            values.put(name, mirror.on(aClass).get().field(name));
        }

        return values;
    }

    public static <T> JSONObject toJson(T aClass)
    {
        Field[] fields = aClass.getClass().getDeclaredFields();
        Mirror mirror = new Mirror();
        JSONObject values = new JSONObject();

        for(Field field:fields)
        {
            String name = field.getName();
            try {
                values.put(name, mirror.on(aClass).get().field(name));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return values;
    }

    public static JSONObject mapToJson(Map<String, Object> data)
    {
        JSONObject values = new JSONObject();

        for(Map.Entry<String, Object> element : data.entrySet() )
        {
            try {
                values.put(element.getKey(),element.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return values;
    }

    public static Map<String, Object> jsonToMap(JSONObject data)
    {
        Map<String, Object> values = Maps.newHashMap();
        Iterator keys = data.keys();
        while(keys.hasNext())
        {
            try {
                Object next = keys.next();
                values.put(data.getString(next.toString()),data.get(next.toString()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return values;
    }
}
