package by.iaa.contacts.Model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Contact implements Serializable {
    public int id;
    public String name;
    public String description;
    public Calendar calendar;
    public String pathImages;
    public String firstPhone;
    public String secondPhone;
    public String telegramLink;
    public Category category;
    public String organization;
    public String socialLink;

    public Contact(int id, String name, String description, Calendar calendar, String firstPhone, String secondPhone,
                   String telegram, int category,String socialLink, String organization , String pathImages) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.calendar = calendar;
        this.pathImages = pathImages;
        this.firstPhone = firstPhone;
        this.secondPhone = secondPhone;
        this.telegramLink = telegram;
        this.category = Category.valueOf(category);
        this.organization = organization;
        this.socialLink = socialLink;
    }

    public Contact(String name, String description, Calendar calendar,
                   String firstPhone, String secondPhone, String telegram, int category, String socialLink, String organization,
                   String pathImages) {
        this.name = name;
        this.description = description;
        this.calendar = calendar;
        this.firstPhone = firstPhone;
        this.secondPhone = secondPhone;
        this.telegramLink = telegram;
        this.category = Category.valueOf(category);
        this.organization = organization;
        this.socialLink = socialLink;
        this.pathImages = pathImages;
    }

    public Contact(int id, String name, String description, Calendar calendar) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.calendar = calendar;
    }

    public Contact(String name, String description, Calendar calendar, String pathImages) {
        this.name = name;
        this.description = description;
        this.calendar = calendar;
        this.pathImages = pathImages;
    }

    public Contact(String name, String description, Calendar calendar,
                   String firstPhone, String secondPhone, String telegram, int category, String socialLink, String organization) {
        this.name = name;
        this.description = description;
        this.calendar = calendar;
        this.firstPhone = firstPhone;
        this.secondPhone = secondPhone;
        this.telegramLink = telegram;
        this.category = Category.valueOf(category);
        this.organization = organization;
        this.socialLink = socialLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact event = (Contact) o;
        return name.equals(event.name) && Objects.equals(description, event.description) &&
                Objects.equals(calendar, event.calendar) && pathImages.equals(event.pathImages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, calendar, pathImages);
    }
}
