package by.iaa.contacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import by.iaa.contacts.Fragment.*;
import by.iaa.contacts.Model.Contact;

public class MainActivity extends AppCompatActivity implements ListContactFragment.OnFragmentSendDataListener {
    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager.beginTransaction().
                setReorderingAllowed(true).
                replace(R.id.fragment_container_view, ListContactFragment.class, savedInstanceState).
                commit();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fragmentManager.beginTransaction().setReorderingAllowed(true).
                    replace(R.id.fragment_item_contact, DetailFragment.class, savedInstanceState).
                    commit();
            fragmentManager.popBackStack();
        }
    }

    @Override
    public void onSendData(Contact data) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Intent intent = new Intent(this, ViewContactActivity.class);
            intent.putExtra("contact", data);
            startActivity(intent);
        } else {
            DetailFragment fragment = (DetailFragment) getSupportFragmentManager().
                    findFragmentById(R.id.fragment_item_contact);
            fragment.setSelectedItem(data);
        }
    }

    public void AddContact(View v) {
        Intent intent = new Intent(this, AddContactActivity.class);
        startActivity(intent);
    }

    public void GetByName(View view) {
            ListContactFragment fragment = (ListContactFragment) getSupportFragmentManager().
                    findFragmentById(R.id.fragment_container_view);
            fragment.GetByName();
    }
}