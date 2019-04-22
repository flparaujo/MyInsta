package com.parse.starter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.parse.ParseObject;
import com.parse.starter.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeAdapter extends ArrayAdapter<ParseObject>{

    private final Context context;
    private List<ParseObject> postagens;

    public HomeAdapter(Context context, List<ParseObject> objects) {
        super(context, 0, objects);
        this.context = context;
        this.postagens = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView; //view que fica armazenada em cache no android

        /* verifica se o view não existe */
        if(view == null) {
            //inicializa objeto para montagem do layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.lista_postagem, parent, false);
        }

        if(postagens.size() > 0) {
            ImageView imagemPostada = (ImageView) view.findViewById(R.id.imagem_lista_postagem);

            ParseObject parseObject = postagens.get(position);

            Picasso.with(context)
                    .load(parseObject.getParseFile("imagem").getUrl())
                    .fit()
                    .into(imagemPostada);
        }

        return view;
    }
}