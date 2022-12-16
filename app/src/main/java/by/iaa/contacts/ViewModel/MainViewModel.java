package by.iaa.contacts.ViewModel;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import by.iaa.contacts.DB.DatabaseAdapter;
import by.iaa.contacts.DB.IRepository;
import by.iaa.contacts.Model.Category;
import by.iaa.contacts.Model.Contact;

public class MainViewModel {
    public int id;
    public String name;
    public String pathImages;
    public String firstPhone;

    public static IRepository<Contact> db;
    public static Context context;

    public MainViewModel(Context context){
        this.db = new DatabaseAdapter(context);
    }

    public MainViewModel(int id, String name, String pathImages, String firstPhone) {
        this.id = id;
        this.name = name;
        this.firstPhone = firstPhone;
        this.pathImages = pathImages;
    }

    public List<MainViewModel> getContactsByType(int SelectedCategoryTypeId){
        this.db.open();
        List<Contact> contacts = db.getAllByType(SelectedCategoryTypeId);
        this.db.close();

      List<MainViewModel> mainViewModels = new ArrayList<>();
      if(!contacts.isEmpty()){
          for (Contact contact : contacts)
          {
              mainViewModels.add(
                      new MainViewModel(
                              contact.id,
                              contact.name,
                              contact.firstPhone,
                              contact.pathImages)
              );
          }
      }
        return  mainViewModels;
    }

    public List<MainViewModel> getContactsByName(String searchText){
        this.db.open();
        List<Contact> contacts = db.getAllByName(searchText);
        this.db.close();

        List<MainViewModel> mainViewModels = new ArrayList<>();
        if(!contacts.isEmpty()){
            for (Contact contact : contacts)
            {
                mainViewModels.add(
                        new MainViewModel(
                                contact.id,
                                contact.name,
                                contact.firstPhone,
                                contact.pathImages)
                );
            }
        }
        return  mainViewModels;
    }

    public void DeleteContact(int id){
        db.open();
        db.remove(id);
        db.close();
    }
}
