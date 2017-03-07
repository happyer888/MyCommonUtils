import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通过Gson解析Json数据工具类
 * 注:使用需要添加Gson依赖库!
 */
public class GsonUtils {

    /**
     * 将Map集合转变为Json字符串
     *
     * @param map 传入Map集合
     * @return 返回String值
     */
    public static String parseMapToJson(Map<?, ?> map) {
        try {
            Gson gson = new Gson();
            return gson.toJson(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将Json字符串转变为JavaBean对象
     *
     * @param json  传入Json数据
     * @param clazz 对象Bean
     * @return 返回类型Bean
     */
    public static <T> T parseJsonToBean(String json, Class<T> clazz) {
        Gson gson = new Gson();
        T t = null;
        try {
            t = gson.fromJson(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 将Json字符串转变为Map集合
     *
     * @param json 传入Json数据
     * @return 返回Map结合
     */
    public static HashMap<String, Object> parseJsonToMap(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, Object>>() {
        }.getType();
        HashMap<String, Object> map = null;
        try {
            map = gson.fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 将Json字符串转变为List集合
     * params: new TypeToken<List<yourbean>>(){}.getType(),
     *
     * @param json 传入Json字符串
     * @param type new TypeToken<List<yourbean>>(){}.getType()
     * @return 返回List<T>集合
     */
    public static <T> List<T> parseJsonToList(String json, Type type) {
        Gson gson = new Gson();
        List<T> list = gson.fromJson(json, type);
        return list;
    }

    /**
     * 获取Json字符串中某个字段(Key)的值(Value)
     * 注意，只能获取同一层级的value!
     *
     * @param json 传入Json数据
     * @param key  传入Key字段
     * @return 返回字符串的Value
     */
    public static String getFieldValue(String json, String key) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        if (!json.contains(key)) {
            return "";
        }
        JSONObject jsonObject = null;
        String value = null;
        try {
            jsonObject = new JSONObject(json);
            value = jsonObject.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }
}
