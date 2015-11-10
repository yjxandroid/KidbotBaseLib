package com.kidbot.library.Log;


import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;


 class LogUtils {
    /**
     * @return StackTraceElement对象
     */
    @CheckResult
    public static StackTraceElement getStackTrace() {
        return Thread.currentThread().getStackTrace()[4];
    }
    // 基本数据类型
    private final static String[] types = {"int", "java.lang.String", "boolean", "char",
            "float", "double", "long", "short", "byte"};


    /**
     * 将对象转化为String
     *
     * @param object
     * @return String 对象
     */
    public static <T> String objectToString(@NonNull T object) {
        if (object == null) {
            return "Object{object is null}";
        }
        if (object.toString().startsWith(object.getClass().getName() + "@")) {
            StringBuilder builder = new StringBuilder(object.getClass().getSimpleName() + "{");
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                boolean flag = false;
                for (String type : types) {
                    if (field.getType().getName().equalsIgnoreCase(type)) {
                        flag = true;
                        Object value = null;
                        try {
                            value = field.get(object);
                        } catch (IllegalAccessException e) {
                            value = e;
                        } finally {
                            builder.append(String.format("%s=%s, ", field.getName(),
                                    value == null ? "null" : value.toString()));
                            break;
                        }
                    }
                }
                if (!flag) {
                    builder.append(String.format("%s=%s, ", field.getName(), "Object"));
                }
            }
            return builder.replace(builder.length() - 2, builder.length() - 1, "}").toString();
        } else {
            return object.toString();
        }
    }

    /**
     * @param json josn格式的数据
     * @return josn 转 string
     */
    public static String jsonToString(@NonNull String json){
        try {
            if (json.startsWith("{")){
                JSONObject jsonObject=new JSONObject(json);
                return String.format(jsonObject.toString(4));
            }else if (json.startsWith("[")){
                JSONArray jsonArray = new JSONArray(json);
               return jsonArray.toString(4);
            }
        }catch (JSONException e){
            e.printStackTrace();
            return "JSON{json is null}";
        }
        return "JSON{json is null}";


    }

}
