package com.youtics.menuoverflow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    //Campos de login
    private  EditText user, pass;
    Button btnRegistrar, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = (EditText) findViewById(R.id.tvUser);
        pass = (EditText) findViewById(R.id.tvPassword);
    }

    //metodo para ocultar y mostrar menu
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }

    //metodo para asiganr las funciones correspondientes a las opciones
    public  boolean onOptionsItemSelected(MenuItem item)
    {
        int id=item.getItemId();
        Intent sig;

        if(id == R.id.item1) {
            sig = new Intent(this, MainActivity.class);
            startActivity(sig);
            Toast.makeText(this, "Opciones 1", Toast.LENGTH_LONG).show();
        }else if (id == R.id.item2){
            sig = new Intent(this, frmRegistro.class);
            startActivity(sig);
            Toast.makeText(this, "Opciones 2", Toast.LENGTH_LONG).show();
        }else if (id == R.id.item3){
            sig = new Intent(this, frmRegistro.class);
            startActivity(sig);
            Toast.makeText(this, "Opciones 3", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public  void Login (View view)
    {
        AdminSqliteOpenHelper admin = new AdminSqliteOpenHelper(this, "Users", null, 1);
        SQLiteDatabase BD = admin.getWritableDatabase();
        String statusUser = BuscarYRetornarTipoDeUsuario(user.getText().toString(), pass.getText().toString());
        if(statusUser.equals("Administrador"))
        {
            //deberia construir el usuario entero y tenerlo aca
            Toast.makeText(this, "Entre como ADMINISTRADOR", Toast.LENGTH_LONG).show();
        }else if (statusUser.equals("Usuario"))
        {
            Toast.makeText(this, "Entre como USUARIO", Toast.LENGTH_LONG).show();
        }else if(statusUser.equals(""))
        {
            Toast.makeText(this, "EL USUARIO NO EXISTE", Toast.LENGTH_LONG).show();
        }
    }
    public String BuscarYRetornarTipoDeUsuario(String u, String p) {
        AdminSqliteOpenHelper admin = new AdminSqliteOpenHelper(this, "Users", null, 1);
        SQLiteDatabase BD = admin.getWritableDatabase();

        String status = "";

        if (!u.isEmpty()) {
            String sql = "SELECT * FROM usuarios WHERE userId ='" + u + "'" + "AND pass ='" + p + "'";
            //String sql= "SELECT * FROM usuarios WHERE userId ='" + u + "'";
            //Toast.makeText(this, sql, Toast.LENGTH_LONG).show();
            Cursor fila = BD.rawQuery(sql , null);
            if (fila.moveToFirst()) {//&& fila.getString(1).equals(p)
                status = fila.getString(2);
                Toast.makeText(this, "Login "+fila.getString(0)+" con EXITO........", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Error, Nombre de Usuario Vacio...COMPLETAR", Toast.LENGTH_LONG).show();
        }
        BD.close();
        return status;
    }

}