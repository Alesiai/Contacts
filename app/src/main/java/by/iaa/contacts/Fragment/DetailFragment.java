package by.iaa.contacts.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import by.iaa.contacts.Model.Contact;
import by.iaa.contacts.Model.Image;
import by.iaa.contacts.R;

public class DetailFragment extends Fragment {
    ImageView image;
    TextView name, description, date, link, firstPhone, secondPhone;
    Contact contact;
    View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.detail_fragment, container, false);

        name = view.findViewById(R.id.name);
        description = view.findViewById(R.id.description);
        date = view.findViewById(R.id.date);
        link = view.findViewById(R.id.link);
        firstPhone = view.findViewById(R.id.firstPhone);
        secondPhone = view.findViewById(R.id.secondPhone);
        image=view.findViewById(R.id.image);

        return view;
    }

    public void setSelectedItem(Contact contact) {
        this.contact = contact;
        name.setText(contact.name);
        description.setText(contact.description);
        date.setText(contact.getStringDate());
        link.setText(contact.socialLink);
        firstPhone.setText(contact.firstPhone);
        secondPhone.setText(contact.secondPhone);
        Image.getInstance().loadImageFromStorage(image,contact.pathImages);
    }
}
