package by.iaa.contacts.Model;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ContactsList {
    private final String fileName = "contacts";
    public List<Contact> events;
    JSONHelper<Contact> jsonHelper;
    static Context context;

    public ContactsList(Context context) {
        this.context = context;
        jsonHelper = new JSONHelper<>();
        Type typeClass = new TypeToken<List<Contact>>() {
        }.getType();
        events = new ArrayList<>(jsonHelper.importFromJSON(this.context, fileName, typeClass));
        if (events == null)
            events = new ArrayList<>();

    }

    public void AddEvent(Contact newEvent) {
        events.add(newEvent);
        Type typeClass = new TypeToken<List<Contact>>() {
        }.getType();
        jsonHelper.exportToJSON(this.context, events, fileName, typeClass);
    }
    public void RemoveEvent(Contact event) {
        events.remove(event);
        Type typeClass = new TypeToken<List<Contact>>() {
        }.getType();
        jsonHelper.exportToJSON(this.context, events, fileName, typeClass);
    }
}
