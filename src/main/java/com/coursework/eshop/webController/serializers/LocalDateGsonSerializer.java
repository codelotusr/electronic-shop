package com.coursework.eshop.webController.serializers;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateGsonSerializer implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
    @Override
    public LocalDate deserialize(com.google.gson.JsonElement jsonElement, java.lang.reflect.Type type, com.google.gson.JsonDeserializationContext jsonDeserializationContext) throws com.google.gson.JsonParseException {
        String ldtString = jsonElement.getAsString();
        return LocalDate.parse(ldtString, DateTimeFormatter.ISO_DATE);
    }

    @Override
    public com.google.gson.JsonElement serialize(LocalDate localDate, java.lang.reflect.Type type, com.google.gson.JsonSerializationContext jsonSerializationContext) {
        return new com.google.gson.JsonPrimitive(localDate.format(DateTimeFormatter.ISO_DATE));
    }
}
