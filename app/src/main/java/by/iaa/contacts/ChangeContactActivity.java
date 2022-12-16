package by.iaa.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

import by.iaa.contacts.DB.DatabaseAdapter;
import by.iaa.contacts.DB.IRepository;
import by.iaa.contacts.Model.Category;
import by.iaa.contacts.Model.Contact;
import by.iaa.contacts.Model.Image;
import by.iaa.contacts.ViewModel.EditViewModel;

public class ChangeContactActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    DatePicker date;
    ImageView image;
    EditViewModel contact;
    EditText name, description, link, firstPhone, secondPhone, telegram, organization;
    IRepository<Contact> db = new DatabaseAdapter(this);

    Spinner category;
    Integer SelectedCategoryTypeId;
    private final int Pick_image = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_contact);
        contact = new EditViewModel(this);

        image = findViewById(R.id.image);
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        date = findViewById(R.id.calendar);
        firstPhone = findViewById(R.id.firstPhone);
        secondPhone = findViewById(R.id.secondPhone);
        telegram = findViewById(R.id.telegram);
        category = findViewById(R.id.category);
        organization = findViewById(R.id.location);
        link = findViewById(R.id.link);

        int id = getIntent().getIntExtra("id", 0);
        contact = contact.getContactById(id);

        Image.getInstance().loadImageFromStorage(image, contact.pathImages);
        name.setText(contact.name);
        description.setText(contact.description);
        date.updateDate(contact.calendar.get(Calendar.YEAR),
                contact.calendar.get(Calendar.MONTH),
                contact.calendar.get(Calendar.DATE));

        firstPhone.setText(contact.firstPhone);
        secondPhone.setText(contact.secondPhone);
        telegram.setText(contact.telegramLink);
        organization.setText(contact.organization);
        link.setText(contact.socialLink);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categoryTypes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);

        category.setOnItemSelectedListener(this);
        category.setSelection(contact.category.getValue());
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        SelectedCategoryTypeId = i;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.change_event_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                changeEvent();
                goToMainActivity();
                return true;
            case R.id.cancel:
                goToMainActivity();
                return true;
            default:
                return true;
        }
    }

    public void setImage(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, Pick_image);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        try {
            final Uri imageUri = imageReturnedIntent.getData();
            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
            Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            image.setImageBitmap(selectedImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void changeEvent() {
        contact.name = name.getText().toString();
        contact.description = description.getText().toString();
        contact.calendar = new GregorianCalendar(date.getYear(), date.getMonth(), date.getDayOfMonth());
        contact.firstPhone = firstPhone.getText().toString();
        contact.secondPhone = secondPhone.getText().toString();
        contact.telegramLink = telegram.getText().toString();
        contact.category = Category.valueOf(SelectedCategoryTypeId);
        contact.socialLink = link.getText().toString();
        contact.organization = organization.getText().toString();

        try {
            contact.pathImages = Image.getInstance().saveToInternalStorage(
                    ((BitmapDrawable) image.getDrawable()).getBitmap(), this, contact.getNameImage());
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        contact.UpdateContact(contact);

    }
}
