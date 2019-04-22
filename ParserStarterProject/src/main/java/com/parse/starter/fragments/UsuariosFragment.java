package com.parse.starter.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.R;
import com.parse.starter.activity.FeedUsuariosActivity;
import com.parse.starter.adapter.UsuariosAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsuariosFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter<ParseUser> adapter;
    private List<ParseUser> usuarios;
    private ParseQuery<ParseUser> consulta;

    public UsuariosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_usuarios, container, false);

        listView = (ListView) view.findViewById(R.id.list_usuarios);
        usuarios = new ArrayList<>();
        adapter = new UsuariosAdapter(getActivity(), usuarios);
        listView.setAdapter(adapter);

        getUsuarios();

        //colocar evento click nos itens da lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ParseUser parseUser = usuarios.get(i);

                //etra no perfil
                Intent intent = new Intent(getActivity(), FeedUsuariosActivity.class);
                intent.putExtra("username", parseUser.getUsername());
                intent.putExtra("ownerId", parseUser.getObjectId());

                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    public void getUsuarios() {
        consulta = ParseUser.getQuery();
        consulta.whereNotEqualTo("objectId", ParseUser.getCurrentUser().getObjectId());
        consulta.orderByDescending("username");

        consulta.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e == null) {
                    if(objects.size() > 0) {
                        usuarios.clear();
                        for(ParseUser parseUser : objects) {
                            usuarios.add(parseUser);
                        }
                        adapter.notifyDataSetChanged();
                    }
                } else {

                }
            }
        });
    }
}
