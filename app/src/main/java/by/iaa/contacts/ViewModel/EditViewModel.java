package by.iaa.contacts.ViewModel;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;

import by.iaa.contacts.DB.DatabaseAdapter;
import by.iaa.contacts.DB.IRepository;
import by.iaa.contacts.Model.Category;
import by.iaa.contacts.Model.Contact;

public class EditViewModel {
    public static IRepository<Contact> db;
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

    public EditViewModel(Context context){
        this.db = new DatabaseAdapter(context);
    }

    public EditViewModel(int id, String name, String description, Calendar calendar, String pathImages,
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

    public EditViewModel(Contact contact){
        this.id = contact.id;
        this.name = contact.name;
        this.description = contact.description;
        this.calendar =  contact.calendar;
        this.pathImages =  contact.pathImages;
        this.firstPhone =  contact.firstPhone;
        this.secondPhone =  contact.secondPhone;
        this.telegramLink =  contact.telegramLink;
        this.category =  contact.category;
        this.organization =  contact.organization;
        this.socialLink =  contact.socialLink;
    }

    public EditViewModel getContactById (int id){
        db.open();
        Contact contact = db.get(id);
        db.close();

        return new EditViewModel(contact);
    }

    public void UpdateContact(EditViewModel editViewModel){
       Contact contact = new Contact ( editViewModel.id,
               editViewModel.name, editViewModel.description, editViewModel.calendar,
                editViewModel.firstPhone, editViewModel.secondPhone, editViewModel.telegramLink,
                editViewModel.category.getValue(),
                editViewModel.socialLink, editViewModel.organization,
                editViewModel.pathImages);

        db.open();
        db.update(contact);
        db.close();
    }

    public String getNameImage() {
        return name + new Date();
    }

    public String getStringDate() {
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        return (day < 10 ? "0" + day : day) + "." +
                (month < 10 ? "0" + month : month) + "." +
                calendar.get(Calendar.YEAR);
    }
}
