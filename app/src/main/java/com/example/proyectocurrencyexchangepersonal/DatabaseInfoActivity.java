package com.example.proyectocurrencyexchangepersonal;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.proyectocurrencyexchangepersonal.database.AppDatabase;
import com.example.proyectocurrencyexchangepersonal.daos.UsuarioDao;
import com.example.proyectocurrencyexchangepersonal.models.Usuario;


public class DatabaseInfoActivity extends AppCompatActivity {

    private TextView txtUsuarioCount, txtTransaccionMonedaCount, txtMonedaCount, txtUserInfo;
    private EditText edtUserId;
    private Button btnSearchUser, btnDeleteUser;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_info);

        txtUsuarioCount = findViewById(R.id.txtUsuarioCount);
        txtTransaccionMonedaCount = findViewById(R.id.txtTransaccionMonedaCount);
        txtMonedaCount = findViewById(R.id.txtMonedaCount);
        txtUserInfo = findViewById(R.id.txtUserInfo);
        edtUserId = findViewById(R.id.edtUserId);
        btnSearchUser = findViewById(R.id.btnSearchUser);
        btnDeleteUser = findViewById(R.id.btnDeleteUser);

        db = AppDatabase.getInstance(this);

        loadDatabaseInfo();

        btnSearchUser.setOnClickListener(v -> {
            searchUser();
        });

        btnDeleteUser.setOnClickListener(v -> {
            deleteUser();
        });
    }

    private void loadDatabaseInfo() {
        new Thread(() -> {
            int usuarioCount = db.usuarioDao().countUsuarios();
            int monedaCount = db.monedaDao().countMonedas();
            int transaccionMonedaCount = db.transaccionMonedaDao().countTransaccionesMoneda();

            runOnUiThread(() -> {
                txtUsuarioCount.setText("Usuario: " + usuarioCount);
                txtMonedaCount.setText("Moneda: " + monedaCount);
                txtTransaccionMonedaCount.setText("TransaccionMoneda: " + transaccionMonedaCount);
            });
        }).start();
    }

    private void searchUser() {
        String userIdString = edtUserId.getText().toString();
        if (userIdString.isEmpty()) {
            txtUserInfo.setText("Please enter a User ID");
            return;
        }
        int userId = Integer.parseInt(userIdString);

        new Thread(() -> {
            Usuario user = db.usuarioDao().getUsuarioByIdSync(userId);

            runOnUiThread(() -> {
                if (user != null) {
                    txtUserInfo.setText("User found: " + user.getNombre());
                    btnDeleteUser.setEnabled(true);
                } else {
                    txtUserInfo.setText("User not found");
                    btnDeleteUser.setEnabled(false);
                }
            });
        }).start();
    }

    private void deleteUser() {
        String userIdString = edtUserId.getText().toString();
        if (userIdString.isEmpty()) {
            txtUserInfo.setText("Please enter a User ID to delete");
            return;
        }
        int userId = Integer.parseInt(userIdString);

        new Thread(() -> {
            Usuario userToDelete = db.usuarioDao().getUsuarioByIdSync(userId);
            if (userToDelete != null) {
                db.usuarioDao().delete(userToDelete);
                runOnUiThread(() -> {
                    txtUserInfo.setText("User deleted");
                    loadDatabaseInfo();
                    btnDeleteUser.setEnabled(false);
                });
            } else {
                runOnUiThread(() -> {
                    txtUserInfo.setText("User not found for deletion");
                });
            }
        }).start();
    }
} 