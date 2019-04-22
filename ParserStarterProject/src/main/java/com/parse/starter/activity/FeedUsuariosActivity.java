package com.parse.starter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.R;
import com.parse.starter.adapter.HomeAdapter;

import java.util.ArrayList;
import java.util.List;

public class FeedUsuariosActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;
    private String username;
    private String ownerId;
    private HomeAdapter adapter;
    private List<ParseObject> postagens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_usuarios);

        postagens = new ArrayList<>();

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        ownerId = intent.getStringExtra("ownerId");

        toolbar = (Toolbar) findViewById(R.id.toolbar_feed_usuario);
        toolbar.setTitle("Posts de "+username);
        toolbar.setTitleTextColor(R.color.black);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left);
        setSupportActionBar(toolbar);

        //Configurar listview e adapter
        listView = (ListView) findViewById(R.id.list_feed_usuario);
        adapter = new HomeAdapter(getApplicationContext(), postagens);
        listView.setAdapter(adapter);

        getPostagens();
    }

    private void getPostagens() {
        ParseQuery<ParseObject> consulta = ParseQuery.getQuery("Imagem");
        consulta.whereEqualTo("ownerId", this.ownerId);
        consulta.orderByDescending("createdAt");

        consulta.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null) {
                    if(objects.size() > 0) {
                        postagens.clear();
                        for(ParseObject parseObject : objects) {
                            postagens.add(parseObject);
                        }
                        adapter.notifyDataSetChanged(); //"avisa" ao adapter para se atualizar
                    }
                } else {
                    Toast.makeText(FeedUsuariosActivity.this, "Erro ao recuperar feed: "+e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
