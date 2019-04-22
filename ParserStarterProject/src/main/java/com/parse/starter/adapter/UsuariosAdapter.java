package com.parse.starter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseUser;
import com.parse.starter.R;

import java.util.List;

public class UsuariosAdapter extends ArrayAdapter<ParseUser>{

    private final Context context;
    private final List<ParseUser> usuarios;

    public UsuariosAdapter(Context context, List<ParseUser> objects) {
        super(context, 0, objects);
        this.context = context;
        this.usuarios = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView; //view que fica armazenada em cache no android

        /* verifica se o view não existe */
        if(view == null) {
            //inicializa objeto para montagem do layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.lista_usuario, parent, false);
        }

        TextView username = (TextView) view.findViewById(R.id.text_username);

        ParseUser parseUser = usuarios.get(position);
        username.setText(parseUser.getUsername());

        return view;
    }
}