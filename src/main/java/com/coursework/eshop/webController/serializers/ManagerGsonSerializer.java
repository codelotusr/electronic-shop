package com.coursework.eshop.webController.serializers;

import com.coursework.eshop.model.Manager;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class ManagerGsonSerializer implements JsonSerializer<Manager> {
    @Override
    public JsonElement serialize(Manager manager, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", manager.getUsername());
        jsonObject.addProperty("password", manager.getPassword());
        jsonObject.addProperty("birthDate", manager.getBirthDate().toString());
        jsonObject.addProperty("firstName", manager.getFirstName());
        jsonObject.addProperty("lastName", manager.getLastName());
        jsonObject.addProperty("employeeId", manager.getEmployeeId());
        jsonObject.addProperty("medicalCertification", manager.getMedicalCertification());
        jsonObject.addProperty("employmentDate", manager.getEmploymentDate().toString());
        jsonObject.addProperty("isAdministrator", manager.isAdministrator());
        return jsonObject;
    }
}
