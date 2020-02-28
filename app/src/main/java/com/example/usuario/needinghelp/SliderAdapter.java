package com.example.usuario.needinghelp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SliderAdapter extends PagerAdapter
{

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context = context;
    }

    public int[] slide_images = {
            R.drawable.mapa_icono,
            R.drawable.hands_icono,
            R.drawable.noticia_icono
    };

    public String[] slide_headings = {
            "MIRA LO QUE SUCEDE A TU ALREDEDOR",
            "AYUDA EN EVENTOS O RECIBE AYUDA",
            "ENTÉRATE DE LO QUE SUCEDE"
    };

    public String[] slide_desc = {
            "Mira lo que necesitan personas cercanas a ti, mediante el mapa descubre lo que sucede y las personas que te necesitan, toma tareas y dirigete al lugar indicado para cumplir con las tareas solicitadas",
            "Publica tu situación y lo que necesitas, y mira lo que necesitan los demás, aceptando tareas para ayudar a los otros o publicando las tuyas según una serie de categorías específicas para detallar más tu situación",
            "Mira los eventos que ocurren cerca a ti, los accidentes o momentos que necesiten de tu conocimiento o auxilio. Publica tragedias cercanas o urgencias que requieren del conocimiento emergente de las personas cercanas a ti"
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (RelativeLayout) o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);
        TextView slideHeading = (TextView) view.findViewById(R.id.slide_head);
        TextView slideDesc = (TextView) view.findViewById(R.id.slide_desc);
        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDesc.setText(slide_desc[position]);
        container.addView(view);

        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
