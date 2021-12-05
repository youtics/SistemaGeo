package com.youtics.menuoverflow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class daoUsuario {

    Context c;
    Usuario u;
    ArrayList <Usuario> lista;
    SQLiteDatabase sql;
    String bd = "BDUsuario";
    String table = "create table if not exists usuario (id int primary key autoincrement, userId text, pass text, statusLogin text, registerDate text )";

    public daoUsuario(Context c) {
        this.c = c;
        sql = c.openOrCreateDatabase(bd, c.MODE_PRIVATE, null);
        sql.execSQL(table);
        u = new Usuario();
    }

    @Override
    public String toString() {
        return "daoUsuario{" +
                "c=" + c +
                ", u=" + u +
                ", lista=" + lista +
                ", sql=" + sql +
                ", bd='" + bd + '\'' +
                ", table='" + table + '\'' +
                '}';
    }

    public boolean insertUsuario(Usuario u) throws ParseException {
        if(buscar(u.getUserId()) == 0)
        {
            ContentValues cv = new ContentValues();
            cv.put("userId", u.getUserId());
            cv.put("pass", u.getPassword());
            cv.put("statusLogin", u.getLoginStatus());
            cv.put("registerDate", u.getRegisterDate().toString());
            return (sql.insert("usuario", null, cv)>0);
        }else
        {
            return false;
        }
    }

    public int buscar(String u) throws ParseException {
        int x=0;
        lista = selectUsuarios();
        for(Usuario user: lista) {
            if(user.getUserId().equals(u))
            {
                x++;
            }
        }
        return x;
    }

    public ArrayList<Usuario> selectUsuarios() throws ParseException {
        ArrayList <Usuario> listUser = new ArrayList<Usuario>();
        listUser.clear();
        Cursor cursor = sql.rawQuery("select * from usuario", null);
        if(cursor!=null && cursor.moveToFirst())
        {
            do{
                Usuario u = new Usuario();
                u.setUserId(cursor.getString(0));
                u.setPassword(cursor.getString(1));
                u.setLoginStatus(cursor.getString(2));
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date registerDate = sdf.parse(cursor.getString(3));
                u.setRegisterDate(registerDate);
                listUser.add(u);
            }while(cursor.moveToNext());
        }
    return listUser;
    }
}
