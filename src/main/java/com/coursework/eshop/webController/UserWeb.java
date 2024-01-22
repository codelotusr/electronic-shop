package com.coursework.eshop.webController;

import com.coursework.eshop.hibernateController.CustomHibernate;
import com.coursework.eshop.model.Customer;
import com.coursework.eshop.model.Manager;
import com.coursework.eshop.model.User;
import com.coursework.eshop.webController.serializers.LocalDateGsonSerializer;
import com.coursework.eshop.webController.serializers.ManagerGsonSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Properties;

@Controller
public class UserWeb {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("coursework-eshop");
    CustomHibernate customHibernate = new CustomHibernate(entityManagerFactory);


    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/getUserById/{id}", method = RequestMethod.GET)
    public String readUserById(@PathVariable(name = "id") int id) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateGsonSerializer());
        builder.registerTypeAdapter(Manager.class, new ManagerGsonSerializer());
        Gson gson = builder.create();

        User user = customHibernate.readEntityById(User.class, id);

        return gson.toJson(user);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "getUserByCredentials", method = RequestMethod.POST)
    public String getUserByCredentials(@RequestBody String data) {
        Gson parser = new Gson();
        Properties properties = parser.fromJson(data, Properties.class);
        User user = customHibernate.getUserByCredentials(properties.getProperty("login"), properties.getProperty("password"));
        return user != null ? user.toString() : "null";
    }
}
