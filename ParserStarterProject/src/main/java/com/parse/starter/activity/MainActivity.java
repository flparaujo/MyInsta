/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.starter.R;
import com.parse.starter.adapter.TabsAdapter;
import com.parse.starter.fragments.HomeFragment;
import com.parse.starter.util.SlidingTabLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

  private Toolbar toolbarPrincipal;
  private SlidingTabLayout slidingTabLayout;
  private ViewPager viewPager;
  private AlertDialog opcoesTirarFoto;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
      
    toolbarPrincipal = (Toolbar) findViewById(R.id.toolbar_principal);
    toolbarPrincipal.setLogo(R.drawable.logo2);

    setSupportActionBar(toolbarPrincipal);

    slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tab_main);
    viewPager = (ViewPager) findViewById(R.id.view_pager_main);

    TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager(), this);
    viewPager.setAdapter(tabsAdapter);
    slidingTabLayout.setCustomTabView(R.layout.tab_view, R.id.text_item_tab);
    slidingTabLayout.setDistributeEvenly(true);
    slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.darkGrey));
    slidingTabLayout.setViewPager(viewPager);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_sair:
        deslogarUsuario();
        return true;
      case R.id.action_configuracoes:
        return true;
      case R.id.action_compartilhar:
        compartilharFoto();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void compartilharFoto() {

    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    startActivityForResult(intent, 1);

  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if((requestCode == 1) && resultCode == RESULT_OK && data != null) { // se selecionou uma foto
      Uri localImagem = data.getData();
      try {
        Bitmap imagem = MediaStore.Images.Media.getBitmap(getContentResolver(), localImagem);
        //comprimir no formato PNG
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imagem.compress(Bitmap.CompressFormat.JPEG, 70, stream);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddmmaaaahhmmss");
        String nomeImagem = simpleDateFormat.format(new Date());
        //criar stream com formato do parse
        ParseFile arquivoParse = new ParseFile(nomeImagem+"imagem.png", stream.toByteArray());

        ParseObject parseObject = new ParseObject("Imagem"); //cria uma classe 'Imagem'
        parseObject.put("ownerId", ParseUser.getCurrentUser().getObjectId());
        parseObject.put("imagem", arquivoParse);

        parseObject.saveInBackground(new SaveCallback() {
          @Override
          public void done(ParseException e) {
            if(e == null) {
              Toast.makeText(MainActivity.this, "POSTOU!!!!", Toast.LENGTH_LONG).show();
              TabsAdapter adapterNovo = (TabsAdapter) viewPager.getAdapter();
              HomeFragment homeFragmentNovo = (HomeFragment) adapterNovo.getFragment(0);
              homeFragmentNovo.atualizaPostagens();
            } else {
              Toast.makeText(MainActivity.this, "Erro ao postar imagem!", Toast.LENGTH_LONG).show();
            }
          }
        });

      } catch (IOException e) {
        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
      }
    }
  }


  private void deslogarUsuario() {
    ParseUser.logOut();
    Intent intent  = new Intent(this, LoginActivity.class);
    startActivity(intent);
  }
}
