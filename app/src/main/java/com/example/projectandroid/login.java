package com.example.projectandroid;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.edtusername);
        passwordEditText = findViewById(R.id.edtpass);
        loginButton = findViewById(R.id.btnlogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Contoh sederhana pemeriksaan login
                if (username.equals("admin") && password.equals("admin123")) {
                    // Jika login berhasil, buka MainActivity
                    Intent intent = new Intent(login.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Tutup aktivitas login agar tidak bisa kembali dengan tombol back

                    // Tambahkan pesan login berhasil
                    Toast.makeText(login.this, "Selamat Datang Admin!", Toast.LENGTH_SHORT).show();
                } else {
                    // Jika login gagal, tampilkan pesan kesalahan
                    Toast.makeText(login.this, "Masukan akun admin!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
