package by.iaa.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import by.iaa.contacts.DB.DatabaseAdapter;
import by.iaa.contacts.DB.IRepository;
import by.iaa.contacts.Model.Contact;
import by.iaa.contacts.Model.Image;
import by.iaa.contacts.ViewModel.EditViewModel;
import by.iaa.contacts.ViewModel.MainViewModel;

public class ViewContactActivity extends AppCompatActivity {
    TextView name, description, date, link, firstPhone, secondPhone;
    ImageView imageView;
    EditViewModel contact;
    IRepository<Contact> db = new DatabaseAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);

        contact = new EditViewModel(this);

        int id = getIntent().getIntExtra("id", 0);
        contact = contact.getContactById(id);

        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        date = findViewById(R.id.date);
        imageView = findViewById(R.id.image);
        link = findViewById(R.id.link);
        firstPhone = findViewById(R.id.firstPhone);
        secondPhone = findViewById(R.id.secondPhone);

        name.setText(contact.name);
        description.setText(contact.description);
        date.setText(contact.getStringDate());
        link.setText(contact.socialLink);
        firstPhone.setText(contact.firstPhone);
        secondPhone.setText(contact.secondPhone);
        Image.getInstance().loadImageFromStorage(imageView, contact.pathImages);
    }

    public void ClickTelegram(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/" + contact.telegramLink));
        startActivity(intent);
    }

    public void ClickLocation(View view){
        String map = "http://maps.google.co.in/maps?q=" + contact.organization.toString();
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(map)));;
    }

    public void ClickPhone(View view){
        Intent intentph = new Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:" + contact.firstPhone));
        if (intentph.resolveActivity(getPackageManager()) != null) {
            startActivity(intentph);
        }
        startActivity(intentph);
    }
    public void ClickPhoneSecond(View view){
        Intent intentph = new Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:" + contact.secondPhone));
        if (intentph.resolveActivity(getPackageManager()) != null) {
            startActivity(intentph);
        }
        startActivity(intentph);
    }

    public void ClickSocial(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www." + contact.socialLink));
        startActivity(intent);
    }

    public  void Share(View v){

        String textToShare = "[Name] " + contact.name + "\n[First phone] "+ contact.firstPhone;
        textToShare += contact.secondPhone.length() == 0 ? "" : "\n[Second phone] " + contact.secondPhone;
        textToShare += contact.telegramLink.length() == 0? "" : "\n[Telegram name] @" + contact.telegramLink;

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, textToShare);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }
}