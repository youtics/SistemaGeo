package com.youtics.menuoverflow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class frmRegistro extends AppCompatActivity{

    //Campos de Registrar
    EditText userId, password;
    String statusLogin;
    Spinner sp_loginStatus;
    Date registerDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_registro);

        userId = (EditText) findViewById(R.id.tvUser);
        password = (EditText) findViewById(R.id.tvPassword);

        //Gestión spinner
        sp_loginStatus = (Spinner) findViewById(R.id.sp_TipoUser);
        String[] tipoUser = {"Usuario", "Administrador"};
        ArrayAdapter<String> adapterTipoUser = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, tipoUser);
        sp_loginStatus.setAdapter(adapterTipoUser);
    }

    //metodo para ocultar y mostrar menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }

    //metodo para asiganr las funciones correspondientes a las opciones
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent sig;

        if (id == R.id.item1) {
            sig = new Intent(this, MainActivity.class);
            startActivity(sig);
            Toast.makeText(this, "Opciones 1", Toast.LENGTH_LONG).show();
        } else if (id == R.id.item2) {
            sig = new Intent(this, frmRegistro.class);
            startActivity(sig);
            Toast.makeText(this, "Opciones 2", Toast.LENGTH_LONG).show();
        } else if (id == R.id.item3) {
            sig = new Intent(this, MainActivity.class);
            startActivity(sig);
            Toast.makeText(this, "Opciones 3", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    //Metodo para dar de alta a los usuarios
    public void Registrar(View view) throws ParseException {
        AdminSqliteOpenHelper admin = new AdminSqliteOpenHelper(this, "Users", null, 1);
        SQLiteDatabase BD = admin.getWritableDatabase();

        Usuario user = new Usuario();
        user.setUserId(userId.getText().toString());
        user.setPassword(password.getText().toString());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        registerDate = new Date();
        String fechaCadena = sdf.format(registerDate);
        Date FechaFormateada = sdf.parse(fechaCadena);
        user.setRegisterDate(FechaFormateada);

        statusLogin = sp_loginStatus.getSelectedItem().toString();
        user.setLoginStatus(statusLogin);

        //Toast.makeText(this, user.toString(), Toast.LENGTH_LONG).show();

        if(BuscarUsuario(user.getUserId())==false) {
            if (!user.getUserId().isEmpty() && !user.getLoginStatus().isEmpty() &&
                    !user.getPassword().isEmpty() && !user.getRegisterDate().toString().isEmpty()) {
                ContentValues registro = new ContentValues();
                registro.put("userId", user.getUserId());
                registro.put("pass", user.getPassword());
                registro.put("statusLogin", user.getLoginStatus());
                registro.put("registerDate", fechaCadena);

                BD.insert("usuarios", null, registro);
                BD.close();
                //limpiar los campos;
                LimpiarCampos();
                Toast.makeText(this, "Los datos fueron registrado con Éxito...", Toast.LENGTH_LONG).show();
                Intent inicio = new Intent(this, MainActivity.class);
                startActivity(inicio);
                finish();
            } else {

                Toast.makeText(this, "Error, posibles campos vacios...", Toast.LENGTH_LONG).show();
            }
        }
    }
    public boolean BuscarUsuario(String userName) {
        AdminSqliteOpenHelper admin = new AdminSqliteOpenHelper(this, "Users", null, 1);
        SQLiteDatabase BD = admin.getWritableDatabase();

        userName = userId.getText().toString();
        boolean encontrado=false;

        if (!userName.isEmpty()) {
            Cursor fila = BD.rawQuery("SELECT * FROM usuarios WHERE userId ='" + userName + "'" , null);
            if (fila.moveToFirst()) {
                encontrado=true;
                Toast.makeText(this, "El usuario YA EXISTE...", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Error, Nombre de Usuario Vacio...COMPLETAR", Toast.LENGTH_LONG).show();
        }
        BD.close();
        return encontrado;
    }
    public void LimpiarCampos() {
        //Campos de Registrar
        userId.setText("");
        password.setText("");
    }

    //metodo para consultar un usuario

    public void Buscar(View view) {
        AdminSqliteOpenHelper admin = new AdminSqliteOpenHelper(this, "Users", null, 1);
        SQLiteDatabase BD = admin.getWritableDatabase();

        String codigo = userId.getText().toString();

        if (!codigo.isEmpty()) {
            Cursor fila = BD.rawQuery("select * From usuarios where userId =" + codigo, null);
            if (fila.moveToFirst()) {
                userId.setText(fila.getString(0));
                BD.close();
                //faltan campos
            } else {
                Toast.makeText(this, "El usuario NO EXISTE...", Toast.LENGTH_LONG).show();
                BD.close();
            }
        } else {
            Toast.makeText(this, "Error, Nombre de Usuario Vacio...COMPLETAR", Toast.LENGTH_LONG).show();
        }
    }

    public void Eliminar(View view) {
        AdminSqliteOpenHelper admin = new AdminSqliteOpenHelper(this, "Users", null, 1);
        SQLiteDatabase BD = admin.getWritableDatabase();

        String codigo = userId.getText().toString();

        if (!codigo.isEmpty()) {
            //Nos retorna la cantidad de elementos eliminados
            int cantidad = BD.delete("usuarios", "codigo=" + codigo, null);
            BD.close();
            LimpiarCampos();

            if (cantidad == 1) {
                Toast.makeText(this, "Eliminado exitosamente", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "El Usuario NO existe", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Debes introducir el NOMBRE DE USUARIO", Toast.LENGTH_LONG).show();
        }
    }

    public void Modificar(View view)
    {
        AdminSqliteOpenHelper admin = new AdminSqliteOpenHelper(this, "Users", null, 1);
        SQLiteDatabase BD = admin.getWritableDatabase();

        String codigo = userId.getText().toString();
        String pass = password.getText().toString();
        String status = sp_loginStatus.getSelectedItem().toString();

        if (!codigo.isEmpty() && pass.isEmpty() && status.isEmpty()) {
            ContentValues registro = new ContentValues();
            registro.put("userId", codigo);
            registro.put("pass", pass);
            registro.put("statusLogin", status);

            int cantidad = BD.update("usuarios", registro, "userId="+codigo, null);
            BD.close();
            LimpiarCampos();
            if (cantidad == 1) {
                Toast.makeText(this, "Modificado exitosamente", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "El Usuario NO existe", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Debes introducir el NOMBRE DE USUARIO", Toast.LENGTH_LONG).show();
        }
    }
}