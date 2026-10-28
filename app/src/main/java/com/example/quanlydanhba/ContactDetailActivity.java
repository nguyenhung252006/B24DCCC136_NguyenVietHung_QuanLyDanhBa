package com.example.quanlydanhba;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ContactDetailActivity extends AppCompatActivity {
    private TextView tvAvatar, tvName, tvPhone, tvGroupGender, tvNote;
    private CheckBox cbFavorite;
    private Button btnCall, btnBack, btnDelete;

    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        tvAvatar = findViewById(R.id.tvDetailAvatar);
        tvName = findViewById(R.id.tvDetailName);
        tvPhone = findViewById(R.id.tvDetailPhone);
        tvGroupGender = findViewById(R.id.tvDetailGroupGender);
        tvNote = findViewById(R.id.tvDetailNote);
        cbFavorite = findViewById(R.id.cbDetailFavorite);
        btnCall = findViewById(R.id.btnCall);
        btnBack = findViewById(R.id.btnBack);
        btnDelete = findViewById(R.id.btnDelete);

        contact = (Contact) getIntent().getSerializableExtra("CONTACT");

        if (contact != null) {
            tvAvatar.setText(contact.getAvatarText());
            tvName.setText(contact.getName());
            tvPhone.setText(contact.getPhone());
            tvGroupGender.setText("Nhóm: " + contact.getGroup() + "   |   Giới tính: " + contact.getGender());
            tvNote.setText("Ghi chú: " + (contact.getNote().isEmpty() ? "Không có" : contact.getNote()));
            cbFavorite.setChecked(contact.isFavorite());
        }

        btnCall.setOnClickListener(v -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + contact.getPhone()));
            startActivity(callIntent);
        });

        cbFavorite.setOnCheckedChangeListener((buttonView, isChecked) -> {
            contact.setFavorite(isChecked);
        });

        btnBack.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("ACTION", "UPDATE");
            resultIntent.putExtra("UPDATED_CONTACT", contact);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        btnDelete.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("ACTION", "DELETE");
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("ACTION", "UPDATE");
        resultIntent.putExtra("UPDATED_CONTACT", contact);
        setResult(RESULT_OK, resultIntent);
        super.onBackPressed();
    }
}