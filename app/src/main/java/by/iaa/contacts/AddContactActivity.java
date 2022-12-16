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
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

import by.iaa.contacts.DB.*;
import by.iaa.contacts.Model.*;
import by.iaa.contacts.ViewModel.AddViewModel;

public class AddContactActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText name, description, link, firstPhone, secondPhone, telegram, organization;
    DatePicker date;
    ImageView imageView;
    Spinner category;
    Integer SelectedCategoryTypeId;
    AddViewModel addViewModel;

    private final int Pick_image = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        addViewModel = new AddViewModel(this);

        date = findViewById(R.id.calendar);
        imageView = findViewById(R.id.image);
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);

        link = findViewById(R.id.link);
        category = findViewById(R.id.category);
        firstPhone = findViewById(R.id.firstPhone);
        secondPhone = findViewById(R.id.secondPhone);
        telegram = findViewById(R.id.telegram);
        organization = findViewById(R.id.location);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.categoryTypes, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(spinnerAdapter);

        category.setOnItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addNew:
                addEvent();
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
            imageView.setImageBitmap(selectedImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addEvent() {
        Calendar calendar = new GregorianCalendar(date.getYear(), date.getMonth(), date.getDayOfMonth());
        Toast.makeText(this, String.valueOf(date.getYear()), Toast.LENGTH_LONG).show();
        AddViewModel addViewModel = new AddViewModel(
                name.getText().toString(),
                description.getText().toString(),
                calendar,
                firstPhone.getText().toString(),
                secondPhone.getText().toString(),
                telegram.getText().toString(),
                SelectedCategoryTypeId,
                link.getText().toString(),
                organization.getText().toString());
        try {
            addViewModel.pathImages = Image.getInstance().
                    saveToInternalStorage(((BitmapDrawable) imageView.getDrawable()).getBitmap(),
                            this, addViewModel.getNameImage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        addViewModel.AddContact(addViewModel);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}