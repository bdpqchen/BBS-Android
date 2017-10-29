package com.twtstudio.bbs.bdpqchen.bbs.commons.rx;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.OBJECT_NAME_DATA;
import static com.twtstudio.bbs.bdpqchen.bbs.commons.support.Constants.OBJECT_NAME_ERR;

/**
 * Created by bdpqchen on 17-10-28.
 */

class ErrorJsonAdapter implements JsonDeserializer<BaseResponse<?>> {

    @Override
    public BaseResponse<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        BaseResponse<?> response = new BaseResponse<>();
        if (json.isJsonObject()) {
            JsonObject jsonObject = json.getAsJsonObject();
            int code = jsonObject.get(OBJECT_NAME_ERR).getAsInt();
            response.setErr(code);
            if (code == 0) {
                Type itemType = ((ParameterizedType) typeOfT).getActualTypeArguments()[0];
                response.setData(context.deserialize(jsonObject.get(OBJECT_NAME_DATA), itemType));
            } else {
                String errMsg = jsonObject.get(OBJECT_NAME_DATA).getAsString();
                response.setMessage(errMsg);
            }
        }
        return response;
    }
}
