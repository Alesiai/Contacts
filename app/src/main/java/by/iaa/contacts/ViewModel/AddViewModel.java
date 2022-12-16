package by.iaa.contacts.ViewModel;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;

import by.iaa.contacts.DB.DatabaseAdapter;
import by.iaa.contacts.DB.IRepository;
import by.iaa.contacts.Model.Category;
import by.iaa.contacts.Model.Contact;

public class AddViewModel {
    public static IRepository<Contact> db;
    public String name;
    public String description;
    public Calendar calendar;
    public String pathImages;
    public String firstPhone;
    public String secondPhone;
    public String telegramLink;
    public int categoryTypeId;
    public String organization;
    public String socialLink;

    public AddViewModel(Context context){
        this.db = new DatabaseAdapter(context);
    }

    public AddViewModel(String name, String description, Calendar calendar,
                   String firstPhone, String secondPhone, String telegram, int category, String socialLink, String organization) {
        this.name = name;
        this.description = description;
        this.calendar = calendar;
        this.firstPhone = firstPhone;
        this.secondPhone = secondPhone;
        this.telegramLink = telegram;
        this.categoryTypeId = category;
        this.organization = organization;
        this.socialLink = socialLink;
    }

    public void AddContact(AddViewModel addViewModel){
        db.open();
        db.insert(new Contact(
                addViewModel.name, addViewModel.description, addViewModel.calendar,
                addViewModel.firstPhone, addViewModel.secondPhone, addViewModel.telegramLink, addViewModel.categoryTypeId,
                addViewModel.socialLink, addViewModel.organization,
                addViewModel.pathImages
        ));
        db.close();
    }

    public String getNameImage() {
        return name + new Date();
    }

}
