package com.example.quanlydanhba;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class AddContactActivity extends AppCompatActivity {
    private EditText etName, etPhone, etNote;
    private Spinner spGroup;
    private RadioGroup rgGender;
    private CheckBox cbFavorite;
    private Button btnSave;

    private ArrayList<Contact> existingContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etNote = findViewById(R.id.etNote);
        spGroup = findViewById(R.id.spGroup);
        rgGender = findViewById(R.id.rgGender);
        cbFavorite = findViewById(R.id.cbFavorite);
        btnSave = findViewById(R.id.btnSave);

        existingContacts = (ArrayList<Contact>) getIntent().getSerializableExtra("EXISTING_CONTACTS");

        String[] groups = {"Gia đình", "Bạn bè", "Công việc", "Khác"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, groups);
        spGroup.setAdapter(adapter);

        btnSave.setOnClickListener(v -> saveContact());
    }

    private void saveContact() {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String note = etNote.getText().toString().trim();

        if (name.isEmpty()) {
            etName.setError("Họ và tên không được để trống");
            return;
        }

        if (!phone.matches("^0\\d{9}$")) {
            etPhone.setError("Số điện thoại phải gồm 10 chữ số và bắt đầu bằng số 0");
            return;
        }

        if (existingContacts != null) {
            for (Contact c : existingContacts) {
                if (c.getPhone().equals(phone)) {
                    etPhone.setError("Số điện thoại này đã tồn tại trong danh bạ");
                    return;
                }
            }
        }

        String group = spGroup.getSelectedItem().toString();
        String gender = "Nam";
        int checkedGenderId = rgGender.getCheckedRadioButtonId();
        if (checkedGenderId == R.id.rbFemale) gender = "Nữ";
        else if (checkedGenderId == R.id.rbOther) gender = "Khác";

        boolean isFavorite = cbFavorite.isChecked();

        Contact newContact = new Contact(name, phone, group, gender, isFavorite, note);

        Intent resultIntent = new Intent();
        resultIntent.putExtra("NEW_CONTACT", newContact);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}