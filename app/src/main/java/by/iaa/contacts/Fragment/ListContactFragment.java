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
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import by.iaa.contacts.Adapter.ContactAdapter;
import by.iaa.contacts.ChangeContactActivity;
import by.iaa.contacts.DB.DatabaseAdapter;
import by.iaa.contacts.DB.IRepository;
import by.iaa.contacts.Model.Contact;
import by.iaa.contacts.R;
import by.iaa.contacts.ViewModel.MainViewModel;


public class ListContactFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    ListView contacts;
    MainViewModel selectedItem;
    ContactAdapter adapter;
    ArrayAdapter<CharSequence> spinnerAdapter;
    EditText editText;
    View view;

    Spinner category;
    Integer SelectedCategoryTypeId = 0;
    String searchText;

    MainViewModel mainViewModel;
    public ListContactFragment() {
        super(R.layout.list_contacts_fragment);
    }

    public interface OnFragmentSendDataListener {
        void onSendData(MainViewModel data);
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
        mainViewModel = new MainViewModel(context);

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

        try{
            List<MainViewModel> mainViewModels = mainViewModel.getContactsByType(SelectedCategoryTypeId);
            adapter = new ContactAdapter(getContext(), R.layout.list_item, mainViewModels);
            contacts.setAdapter(adapter);
        }
        catch (Exception e){
        }


        registerForContextMenu(contacts);
        contacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
         public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            MainViewModel selectedItem = (MainViewModel) contacts.getItemAtPosition(position);
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
        selectedItem = (MainViewModel) contacts.getItemAtPosition(info.position);
       switch (item.getItemId()) {
           case IDM_OPEN:
               InfoEvent();
               return true; case IDM_REMOVE:
                showDialog();
            default:
               return super.onContextItemSelected(item);
        }
    }

    public void InfoEvent() {
        Intent intent = new Intent(getContext(), ChangeContactActivity.class);
        intent.putExtra("id", selectedItem.id);
        startActivity(intent);
    }

    protected void showDialog() {
        AlertDialog.Builder al = new AlertDialog.Builder(getContext());
        al.setTitle("Удаление");
        al.setMessage("Вы действительно хотите удалить контакт?");
        al.setPositiveButton("Да", clickListener);
        al.setNegativeButton("Нет", clickListener);
        al.show();
    }

    DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            switch (i) {
                case DialogInterface.BUTTON_POSITIVE:
                    mainViewModel.DeleteContact(selectedItem.id);
                    setListContacts();
                    return;
                case DialogInterface.BUTTON_NEGATIVE:
                    return;

            }
        }
    };

    public void sortedContactsInAsc() {
       Collections.sort(mainViewModel.getContactsByType(SelectedCategoryTypeId), new Comparator<MainViewModel>() {
            @Override
            public int compare(MainViewModel contact, MainViewModel t1) {
              return contact.name.compareTo(t1.name);
           }
        });
       adapter.notifyDataSetChanged();
    }

    public void sortedContactsInDesc() {
        Collections.sort(mainViewModel.getContactsByType(SelectedCategoryTypeId), new Comparator<MainViewModel>() {
            @Override
            public int compare(MainViewModel contact, MainViewModel t1) {
               return t1.name.compareTo(contact.name);
            }
        });
       adapter.notifyDataSetChanged();
    }

    public void GetByName(){
        adapter = new ContactAdapter(getContext(), R.layout.list_item, mainViewModel.getContactsByName(searchText));
        contacts.setAdapter(adapter);
    }

}
