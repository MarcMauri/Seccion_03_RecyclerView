package app.android.mmauri.seccion_03_recyclerview.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.android.mmauri.seccion_03_recyclerview.adapters.MyAdapter;
import app.android.mmauri.seccion_03_recyclerview.R;
import app.android.mmauri.seccion_03_recyclerview.models.Movie;

public class MainActivity extends AppCompatActivity {

    private List<Movie> movies;

    private RecyclerView mRecyclerView;
    // Puede ser declarado como 'RecyclerView.Adapter' o como nuestra clase adaptador 'MyAdapter'
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.movies = getAllMovies();

        this.mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        this.mLayoutManager = new LinearLayoutManager(this);
        //this.mLayoutManager = new GridLayoutManager(this, 2); // context, # num. col
        //this.mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        // Implementamos nuestro OnItemClickListener propio, sobreescribiendo el metodo que nosotros
        // definimos en el adaptador, y recibiendo los parametros que necesitamos
        this.mAdapter = new MyAdapter(this.movies, R.layout.recycler_view_item, new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Movie movie, int position) {
                //Toast.makeText(MainActivity.this, name + " - " + position, Toast.LENGTH_SHORT).show();
                removeMovie(position);
            }
        });

        // Si sabemos que el size del layout del recyclerview no va a cambiar... (Mejora rendimiento)
        // NUNCA con StaggeredGridLayoutManager
        this.mRecyclerView.setHasFixedSize(true);
        // Le metemos animaciones por defecto
        this.mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        this.mRecyclerView.setLayoutManager(this.mLayoutManager);
        this.mRecyclerView.setAdapter(this.mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Nos ahorramos un paso metiendolo to-do en una linea
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_name:
                this.addMovie(0); // Se inserta en la parte superior -> pos: 0
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private List<Movie> getAllMovies() {
        return new ArrayList<Movie>() {{
            add(new Movie("Ben Hur", R.drawable.benhur));
            add(new Movie("DeadPool", R.drawable.deadpool));
            add(new Movie("Guardians of Galaxy", R.drawable.guardians));
            add(new Movie("Warcraft", R.drawable.warcraft));
        }};
    }

    private void addMovie(int position) {
        this.movies.add(position, new Movie("New Image #" + (++counter), R.drawable.bladerunner));
        // Mejora del mAdapter.notifyDataSetChanged()
        mAdapter.notifyItemInserted(position);
        // Y actualizamos la vista:
        mLayoutManager.scrollToPosition(0);
    }

    private void removeMovie(int position) {
        try {
            this.movies.remove(position);
            mAdapter.notifyItemRemoved(position);
        }
        catch (Exception e) {
            Toast.makeText(this, "Ep! No borres tan deprisa.\n" + e, Toast.LENGTH_LONG).show();
        }
    }
}
