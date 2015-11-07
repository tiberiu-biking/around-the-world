package master.pam.crosscutting.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.GsonBuilder;
import master.pam.crosscutting.date.DateFormatUtil;

public class GsonHelper {

    private static GsonBuilder gsonBuilder = new GsonBuilder().
            // setPrettyPrinting().
                    setDateFormat(DateFormatUtil.DATE_PATTERN_MASTER_PAM);

    public static String toGson(Object aObject) {
        return gsonBuilder.create().toJson(aObject);
    }

    public static <T> T fromGson(String aGson, Class<T> aClass) {
        return gsonBuilder.create().fromJson(aGson, aClass);
    }

    public static String toGson(Object aObject, ExclusionStrategy... aExclusionStrategys) {
        for (ExclusionStrategy exclusionStrategy : aExclusionStrategys)
            gsonBuilder.addSerializationExclusionStrategy(exclusionStrategy);

        return gsonBuilder.create().toJson(aObject);
    }
}
