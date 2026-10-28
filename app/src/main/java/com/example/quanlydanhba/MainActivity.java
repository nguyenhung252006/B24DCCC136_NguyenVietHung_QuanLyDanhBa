package com.example.quanlydanhba;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView tvTotalCount, tvFavoriteCount;
    private ListView lvContacts;
    private Button btnAddContact;

    private ArrayList<Contact> contactList;
    private ContactAdapter adapter;

    private ActivityResultLauncher<Intent> addContactLauncher;
    private ActivityResultLauncher<Intent> detailContactLauncher;
    private int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTotalCount = findViewById(R.id.tvTotalCount);
        tvFavoriteCount = findViewById(R.id.tvFavoriteCount);
        lvContacts = findViewById(R.id.lvContacts);
        btnAddContact = findViewById(R.id.btnAddContact);

        initData();

        adapter = new ContactAdapter(this, contactList);
        lvContacts.setAdapter(adapter);

        updateStats();

        addContactLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Contact newContact = (Contact) result.getData().getSerializableExtra("NEW_CONTACT");
                        if (newContact != null) {
                            contactList.add(newContact);
                            adapter.notifyDataSetChanged();
                            updateStats();
                        }
                    }
                }
        );

        detailContactLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String action = result.getData().getStringExtra("ACTION");
                        if ("DELETE".equals(action)) {
                            if (selectedPosition != -1 && selectedPosition < contactList.size()) {
                                contactList.remove(selectedPosition);
                            }
                        } else if ("UPDATE".equals(action)) {
                            Contact updatedContact = (Contact) result.getData().getSerializableExtra("UPDATED_CONTACT");
                            if (updatedContact != null && selectedPosition != -1) {
                                contactList.set(selectedPosition, updatedContact);
                            }
                        }
                        adapter.notifyDataSetChanged();
                        updateStats();
                    }
                }
        );

        btnAddContact.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
            intent.putExtra("EXISTING_CONTACTS", contactList);
            addContactLauncher.launch(intent);
        });

        lvContacts.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            Intent intent = new Intent(MainActivity.this, ContactDetailActivity.class);
            intent.putExtra("CONTACT", contactList.get(position));
            detailContactLauncher.launch(intent);
        });
    }

    private void initData() {
        contactList = new ArrayList<>();
        contactList.add(new Contact("Nguyễn Văn An", "0901234567", "Bạn bè", "Nam", false, "Bạn phổ thông"));
        contactList.add(new Contact("Trần Thị Bình", "0912345678", "Gia đình", "Nữ", true, "Chị gái, hay gọi buổi tối"));
        contactList.add(new Contact("Lê Minh Châu", "0987654321", "Công việc", "Khác", false, "Đồng nghiệp dự án"));
    }

    private void updateStats() {
        int total = contactList.size();
        int favorites = 0;
        for (Contact c : contactList) {
            if (c.isFavorite()) favorites++;
        }
        tvTotalCount.setText(String.valueOf(total));
        tvFavoriteCount.setText(String.valueOf(favorites));
    }
}