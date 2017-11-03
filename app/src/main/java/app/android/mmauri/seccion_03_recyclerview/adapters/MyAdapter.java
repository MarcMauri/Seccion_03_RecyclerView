package app.android.mmauri.seccion_03_recyclerview.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import app.android.mmauri.seccion_03_recyclerview.R;
import app.android.mmauri.seccion_03_recyclerview.models.Movie;

/**
 * Created by marc on 10/11/17.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Movie> movies;
    private int layout;
    private OnItemClickListener itemClickListener;

    private Context context;


    // Constructor de la clase MyAdapter
    public MyAdapter(List<Movie> movies, int layout, OnItemClickListener listener) {
        this.movies = movies;
        this.layout = layout;
        this.itemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflamos el layout y se lo pasamos al constructor del ViewHolder, donde manejaremos
        // toda la logica como extraer los datos, referencias ...
        this.context = parent.getContext();
        View v = LayoutInflater.from(this.context).inflate(this.layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Llamamos al metodo Bind del ViewHolder pasandole objeto y listener
        holder.bind(this.movies.get(position), this.itemClickListener);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        // Elementos UI a rellenar
        public TextView textViewTitle;
        public ImageView imageViewPoster;

        public ViewHolder (View itemView) {
            // Recibe la View completa. La pasa al constructor padre y enlazamos referencias UI
            // con nuestras propiedades ViewHolder declaradas justo arriba
            super(itemView);
            this.textViewTitle = (TextView) itemView.findViewById(R.id.textViewTitle);
            this.imageViewPoster= (ImageView) itemView.findViewById(R.id.imageViewPoster);
        }

        public void bind(final Movie movie, final OnItemClickListener listener) {
            // Procesamos los datos a renderizar
            this.textViewTitle.setText(movie.getName());
            // Cargamos el atributo Poster en el Picasso de nuestro contexto, para meterlo dentro
            // del ImageView de la UI:
            Picasso.with(context).load(movie.getPoster()).fit().into(this.imageViewPoster);
            //this.imageViewPoster.setImageResource(movie.getPoster());

            // Definimos que por cada elemento de nuestro recycler view, tenemos un click listener,
            // que se comporta de la siguiente manera...
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(movie, getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Movie movie, int position);
    }
}
