package by.iaa.contacts.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.Collections;
import java.util.Comparator;

import by.iaa.contacts.Adapter.ContactAdapter;
import by.iaa.contacts.ChangeContactActivity;
import by.iaa.contacts.DB.DatabaseAdapter;
import by.iaa.contacts.DB.IRepository;
import by.iaa.contacts.Model.Contact;
import by.iaa.contacts.Model.ContactsList;
import by.iaa.contacts.R;


public class ListContactFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    ListView contacts;
    ContactsList contactsList;
    Contact selectedItem;
    ContactAdapter adapter;
    IRepository<Contact> db;
    final int DIALOG_DELETE = 1;
    ArrayAdapter<CharSequence> spinnerAdapter;
    EditText editText;
    View view;

    Spinner category;
    Integer SelectedCategoryTypeId = 0;
    String searchText;
    public ListContactFragment() {
        super(R.layout.list_contacts_fragment);
    }

    public interface OnFragmentSendDataListener {
        void onSendData(Contact data);
    }

    private OnFragmentSendDataListener fragmentSendDataListener;

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        try {
            fragmentSendDataListener = (OnFragmentSendDataListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс OnFragmentInteractionListener");
        }
        db = new DatabaseAdapter(context);


        spinnerAdapter = ArrayAdapter.createFromResource
                (context, R.array.categoryTypesForSearch, android.R.layout.simple_spinner_item);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        SelectedCategoryTypeId = i;
        setListContacts();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState) {

        view = inflater.inflate(R.layout.list_contacts_fragment, container, false);
        contacts = view.findViewById(R.id.contacts);

        category = view.findViewById(R.id.category);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(spinnerAdapter);

        category.setOnItemSelectedListener(this);

        editText = view.findViewById(R.id.searchObject);

        editText.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchText = String.valueOf(s);
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        return view;
    }

    public void setListContacts() {
        db.open();
        adapter = new ContactAdapter(getContext(), R.layout.list_item, db.getAllByType(SelectedCategoryTypeId));
        db.close();
        contacts.setAdapter(adapter);
        registerForContextMenu(contacts);
        contacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // по позиции получаем выбранный элемент
                Contact selectedItem = (Contact) contacts.getItemAtPosition(position);
                fragmentSendDataListener.onSendData(selectedItem);
            }
        });
    }

    public static final int IDM_OPEN = 101;
    public static final int IDM_REMOVE = 102;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(Menu.NONE, IDM_OPEN, Menu.NONE, "Изменить");
        menu.add(Menu.NONE, IDM_REMOVE, Menu.NONE, "Удалить");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        selectedItem = (Contact) contacts.getItemAtPosition(info.position);
        switch (item.getItemId()) {
            case IDM_OPEN:
                InfoEvent();
                return true;
            case IDM_REMOVE:
                showDialog();
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void InfoEvent() {
        Intent intent = new Intent(getContext(), ChangeContactActivity.class);
        intent.putExtra("contact", selectedItem);
        startActivity(intent);
    }

    protected void showDialog() {
        AlertDialog.Builder al = new AlertDialog.Builder(getContext());
        al.setTitle("Удаление");
        al.setMessage("Вы действительно хотите удалить мероприятие?");
        al.setPositiveButton("Да", clickListener);
        al.setNegativeButton("Нет", clickListener);
        al.show();
    }

    DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            switch (i) {
                case DialogInterface.BUTTON_POSITIVE:
                    db.open();
                    db.remove(selectedItem.id);
                    db.close();
                    setListContacts();
                    return;
                case DialogInterface.BUTTON_NEGATIVE:
                    return;

            }
        }
    };

    public void sortedEventsInAsc() {
        db.open();
        Collections.sort(db.getAll(), new Comparator<Contact>() {
            @Override
            public int compare(Contact contact, Contact t1) {
                return contact.calendar.compareTo(t1.calendar);
            }
        });
        db.close();
        adapter.notifyDataSetChanged();
    }

    public void sortedEventsInDesc() {
        db.open();
        Collections.sort(db.getAll(), new Comparator<Contact>() {
            @Override
            public int compare(Contact contact, Contact t1) {
                return t1.calendar.compareTo(contact.calendar);
            }
        });
        db.close();
        adapter.notifyDataSetChanged();
    }

    public void GetByName(){
        db.open();
        adapter = new ContactAdapter(getContext(), R.layout.list_item, db.getAllByName(searchText));
        db.close();
        contacts.setAdapter(adapter);
    }

}
