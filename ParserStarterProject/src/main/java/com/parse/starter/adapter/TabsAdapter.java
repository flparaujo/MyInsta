package com.parse.starter.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.ViewGroup;

import com.parse.starter.R;
import com.parse.starter.fragments.HomeFragment;
import com.parse.starter.fragments.UsuariosFragment;

import java.util.HashMap;
import java.util.Map;

public class TabsAdapter extends FragmentStatePagerAdapter{

    private Context context;
    private int[] icones = new int[]{R.drawable.ic_home, R.drawable.ic_people};
    private int tamanhoIcone;
    private Map<Integer,Fragment> fragmentosUtilizados;

    public TabsAdapter(FragmentManager fm, Context c) {
        super(fm);
        this.context = c;
        double escala = this.context.getResources().getDisplayMetrics().density;
        tamanhoIcone = (int) (36*escala);
        fragmentosUtilizados = new HashMap<>();
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                fragmentosUtilizados.put(position, fragment);
                break;
            case 1:
                fragment = new UsuariosFragment();
                //fragmentosUtilizados.put(position, fragment);
                break;
            default:
                break;

        }

        return fragment;
    }

    public Fragment getFragment(Integer id) {
        return fragmentosUtilizados.get(id);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container,position,object);
        fragmentosUtilizados.remove(position);
    }

    @Override
    public int getCount() {
        return icones.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        Drawable drawable = ContextCompat.getDrawable(this.context, icones[position]);
        drawable.setBounds(0, 0, tamanhoIcone, tamanhoIcone);
        // coloca a imagem dentro de um texto
        ImageSpan imageSpan = new ImageSpan(drawable);
        SpannableString spannableString = new SpannableString(" ");
        spannableString.setSpan(imageSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannableString;
    }
}