package me.cullycross.weather.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import me.cullycross.weather.pojos.Day;

/**
 * Created by: Anton Shkurenko (cullycross)
 * Project: KPImapsTest
 * Date: 11/3/15
 * Code style: SquareAndroid (https://github.com/square/java-code-styles)
 * Follow me: @tonyshkurenko
 */
public class DayDeserializer implements JsonDeserializer<List<Day>> {

  @Override
  public List<Day> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {

    final JsonObject object = (JsonObject) json;
    final JsonObject daily = object.getAsJsonObject("daily");
    final JsonArray data = daily.getAsJsonArray("data");

    List<Day> days = new ArrayList<>();
    for(JsonElement o : data) {
      days.add(context.deserialize(o, Day.class));
    }
    return days;
  }
}
