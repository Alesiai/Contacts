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

    public Contact(int id, String name, String description, Calendar calendar, String pathImages,
                   String firstPhone, String secondPhone, String telegram, int category, String socialLink, String organization) {
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

    public String getNameImage() {
        return name + new Date();
    }

    public String getStringDateForListView() {
        return getStringDate() + "\n" + getStringTime();
    }

    public String getStringDate() {
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        return (day < 10 ? "0" + day : day) + "." +
                (month < 10 ? "0" + month : month) + "." +
                calendar.get(Calendar.YEAR);
    }

    public String getStringTime() {
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        return (hour < 10 ? "0" + hour : hour) + ":" +
                (minute < 10 ? "0" + minute : minute);
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
