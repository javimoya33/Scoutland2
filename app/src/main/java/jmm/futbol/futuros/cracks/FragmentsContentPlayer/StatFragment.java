package jmm.futbol.futuros.cracks.FragmentsContentPlayer;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import jmm.futbol.futuros.cracks.Adaptadores.RecStatsAdapter;
import jmm.futbol.futuros.cracks.Modelo.ParrafosAnalisis;
import jmm.futbol.futuros.cracks.tools.GlideApp;
import jmm.futbol.futuros.cracks.tools.rutaImgScoutland;
import app.example.parallaxviewpage.NotifyingScrollView;
import app.example.parallaxviewpage.ScrollViewFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * Fragmento que engloba al contenido de las Estadísticas de un jugador
 */
public class StatFragment extends ScrollViewFragment {

    public static int idJugador;
    public static int tipoTexto = 2;

    private static int sizeScreen = 0;
    private static float densityScreen = 0;

    private HorizontalScrollView horizontalScrollView;
    private TableLayout tableStats;

    private String strImagen;
    private int numFilas = 0;
    private int posArrayStats = 0;

    private RecyclerView recyclerContent;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseRecyclerAdapter adapterParrafosStats;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();

    // Creamos una nueva instancia del fragmento donse se le pasará su id particular, además del tamaño
    // y la densidad de la pantalla.
    public static Fragment newInstance(int pos, int idjugador, int sizescreen, float densityscreen)
    {
        StatFragment fragment = new StatFragment();
        Bundle args = new Bundle();
        idJugador = idjugador;
        sizeScreen = sizescreen;
        densityScreen = densityscreen;
        args.putInt(ARG_POSITION, pos);
        args.putInt(ARG_POSITION, idjugador);
        args.putFloat(ARG_POSITION, densityscreen);
        fragment.setArguments(args);
        return fragment;
    }

    // Constructor de la clase.
    public StatFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mPosition = getArguments().getInt(ARG_POSITION);

        // Inflamos un layout en el contenedor del fragmento.
        View view = inflater.inflate(jmm.futbol.futuros.cracks.R.layout.fragment_stats, container, false);

        // Permitidos el desplazamiento vertical táctil en el contenedor.
        mScrollView = (NotifyingScrollView) view.findViewById(jmm.futbol.futuros.cracks.R.id.scrollView);
        setScrollViewOnScrollListener();

        // Creamos referencias a las tablas de las bases de datos de Firebase que vamos a necesitar.
        final DatabaseReference dbEstadisticas = FirebaseDatabase.getInstance().getReference().child("estadisticas");
        final Query dbParrafosAnalisis = FirebaseDatabase.getInstance().getReference().child("parrafosanalisis")
                .orderByChild("tipoParrafo").equalTo("2_" + idJugador);

        // Relacionamos el layoutManager con el recyclerview creado en el fragmento.
        recyclerContent = (RecyclerView) view.findViewById(jmm.futbol.futuros.cracks.R.id.recycler_stats);
        layoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerContent.setLayoutManager(layoutManager);
        recyclerContent.setNestedScrollingEnabled(false);

        horizontalScrollView = (HorizontalScrollView) view.findViewById(jmm.futbol.futuros.cracks.R.id.scrollStats);
        tableStats = (TableLayout) view.findViewById(jmm.futbol.futuros.cracks.R.id.tableStats);

        // Lanzamos una consulta donde obentendremos las estadísticas de un id particular.
        dbEstadisticas.orderByChild("estadisticas_idJugador").equalTo(String.valueOf(idJugador))
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        ArrayList<String> arrStats = new ArrayList<>();
                        arrStats.add((String) dataSnapshot.child("Temporada").getValue());
                        arrStats.add((String) dataSnapshot.child("Partidos").getValue());
                        arrStats.add((String) dataSnapshot.child("Minutos").getValue());
                        arrStats.add((String) dataSnapshot.child("TarjAmarillas").getValue());
                        arrStats.add((String) dataSnapshot.child("TarjRojas").getValue());
                        arrStats.add((String) dataSnapshot.child("Goles").getValue());
                        arrStats.add((String) dataSnapshot.child("Asistencias").getValue());
                        arrStats.add((String) dataSnapshot.child("Disparos").getValue());
                        arrStats.add((String) dataSnapshot.child("Regates").getValue());
                        arrStats.add((String) dataSnapshot.child("Pases").getValue());
                        arrStats.add((String) dataSnapshot.child("Centros").getValue());
                        arrStats.add((String) dataSnapshot.child("Faltas").getValue());
                        arrStats.add((String) dataSnapshot.child("Robos").getValue());
                        arrStats.add((String) dataSnapshot.child("Paradas").getValue());
                        numFilas += 1;

                        // Cargamos los datos de cada fila con las estadísticas de temporada.
                        setFilasTablaStats(getContext(), horizontalScrollView, tableStats, arrStats, numFilas);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        // Dejamos pasar un intervalo de 1000 milisegundos para que la consulta rea realizada antes
        // de que comience la siguiente.
        try
        {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Cargamos la fila superior, donde se muestra la información representada en cada columna.
        setColumnasTablaStats(getContext());

        // Creamos un adaptador de Firebase al que le pasaremos los campos de la tabla ParrafosAnalisis,
        // su referencia a Firebase, la clase del adaptador y el layout donde se cargarán los datos de los campos.
        adapterParrafosStats = new FirebaseRecyclerAdapter<ParrafosAnalisis, RecStatsAdapter>(
                ParrafosAnalisis.class, jmm.futbol.futuros.cracks.R.layout.recycler_stats, RecStatsAdapter.class, dbParrafosAnalisis)
        {
            @Override
            protected void populateViewHolder(final RecStatsAdapter recStatsAdapter, final ParrafosAnalisis parrafosAnalisis, final int position) {

                // Recogemos los datos de los campos de cada párrafo.
                String nombreIconStats = parrafosAnalisis.getIconoStat();
                String nombreTituloStat = parrafosAnalisis.getTitulo();
                String textoParrafo = parrafosAnalisis.getTextoParrafo();

                // Cargamos el contenido de cada párrafo con el icono y título representativo.
                recStatsAdapter.setStatsParrafo(getContext(), sizeScreen, densityScreen,
                        nombreIconStats, nombreTituloStat, textoParrafo, position);
            }
        };

        // Cargamos el adaptador en el recyclerview.
        recyclerContent.setAdapter(adapterParrafosStats);

        return view;
    }

    // Método donde se carga la información de cada fila correspondiente a las estadísticas de cada jugador.
    public void setFilasTablaStats(Context context, HorizontalScrollView scrollStats, TableLayout tableStats,
                                   ArrayList<String> arrStats, int numFila)
    {
        // Fuentes utilizada en los campos de texto.
        Typeface fuente2 = Typeface.createFromAsset(context.getAssets(), "fonts/Sumptuous_Light.otf");

        scrollStats.setVisibility(View.VISIBLE);

        TextView tvTemporada;
        TextView tvPartidos;
        TextView tvMinutos;
        TextView tvTarjAmar;
        TextView tvTarjRojas;
        TextView tvGoles;
        TextView tvAsist;
        TextView tvDisparos;
        TextView tvRegates;
        TextView tvPases;
        TextView tvCentros;
        TextView tvFaltas;
        TextView tvRobos;
        TextView tvParadas;

        posArrayStats = 0;

        // Creamos una fila para la tabla de estadísticas.
        TableRow row = new TableRow(context);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,0,0,0);
        row.setLayoutParams(lp);

        // Se cambia el fondo de cada fila según sea par o impar.
        if (multiplode2(numFila) == 0)
        {
            row.setBackgroundColor(context.getResources().getColor(jmm.futbol.futuros.cracks.R.color.colorPrueba12));
        }

        // Cargamos los datos correspondientes a los valores de cada estadística.

        tvTemporada = new TextView(context);
        cargarFilasTablaStats(tvTemporada, context, fuente2, lp, row);
        tvTemporada.setText(arrStats.get(posArrayStats));
        posArrayStats += 1;

        tvPartidos = new TextView(context);
        cargarFilasTablaStats(tvPartidos, context, fuente2, lp, row);
        tvPartidos.setText(String.valueOf(arrStats.get(posArrayStats)));
        posArrayStats += 1;

        tvMinutos = new TextView(context);
        cargarFilasTablaStats(tvMinutos, context, fuente2, lp, row);
        tvMinutos.setText(String.valueOf(arrStats.get(posArrayStats)));
        posArrayStats += 1;

        tvTarjAmar = new TextView(context);
        cargarFilasTablaStats(tvTarjAmar, context, fuente2, lp, row);
        tvTarjAmar.setText(String.valueOf(arrStats.get(posArrayStats)));
        posArrayStats += 1;

        tvTarjRojas = new TextView(context);
        cargarFilasTablaStats(tvTarjRojas, context, fuente2, lp, row);
        tvTarjRojas.setText(String.valueOf(arrStats.get(posArrayStats)));
        posArrayStats += 1;

        tvGoles = new TextView(context);
        cargarFilasTablaStats(tvGoles, context, fuente2, lp, row);
        tvGoles.setText(String.valueOf(arrStats.get(posArrayStats)));
        posArrayStats += 1;

        tvAsist = new TextView(context);
        cargarFilasTablaStats(tvAsist, context, fuente2, lp, row);
        tvAsist.setText(String.valueOf(arrStats.get(posArrayStats)));
        posArrayStats += 1;

        tvDisparos = new TextView(context);
        cargarFilasTablaStats(tvDisparos, context, fuente2, lp, row);
        tvDisparos.setText(String.valueOf(arrStats.get(posArrayStats)));
        posArrayStats += 1;

        tvRegates = new TextView(context);
        cargarFilasTablaStats(tvRegates, context, fuente2, lp, row);
        tvRegates.setText(String.valueOf(arrStats.get(posArrayStats)));
        posArrayStats += 1;

        tvPases = new TextView(context);
        cargarFilasTablaStats(tvPases, context, fuente2, lp, row);
        tvPases.setText(String.valueOf(arrStats.get(posArrayStats)));
        posArrayStats += 1;

        tvCentros = new TextView(context);
        cargarFilasTablaStats(tvCentros, context, fuente2, lp, row);
        tvCentros.setText(String.valueOf(arrStats.get(posArrayStats)));
        posArrayStats += 1;

        tvFaltas = new TextView(context);
        cargarFilasTablaStats(tvFaltas, context, fuente2, lp, row);
        tvFaltas.setText(String.valueOf(arrStats.get(posArrayStats)));
        posArrayStats += 1;

        tvRobos = new TextView(context);
        cargarFilasTablaStats(tvRobos, context, fuente2, lp, row);
        tvRobos.setText(String.valueOf(arrStats.get(posArrayStats)));
        posArrayStats += 1;

        tvParadas = new TextView(context);
        cargarFilasTablaStats(tvParadas, context, fuente2, lp, row);
        tvParadas.setText(String.valueOf(arrStats.get(posArrayStats)));
        posArrayStats += 1;

        tableStats.addView(row, numFila);
    }

    // Método en el que se carga la fila superior que ilustra el contenido de cada columna.
    public void setColumnasTablaStats(Context context)
    {
        String[] nameStats = {"Partidos", "Minutos", "Tarjetas Amarillas", "Tarjetas Rojas", "Goles",
                "Asistencias", "Tiros", "Regates", "Pases", "Centros", "Faltas", "Robos", "Paradas"};

        String[] nameIconStats = {"matches", "minutes", "yellowcards", "redcards", "goals", "assists", "shoots",
                "dribbles", "passes", "crosses", "fouls", "steals", "saves"};

        TableRow rowColumn = new TableRow(context);
        rowColumn.setBackgroundColor(context.getResources().getColor(jmm.futbol.futuros.cracks.R.color.colorPrueba12));

        TableRow.LayoutParams lpRowColumn = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        rowColumn.setLayoutParams(lpRowColumn);

        TextView firstColumn = new TextView(context);
        rowColumn.addView(firstColumn);

        ImageView ivPartidos = new ImageView(context);
        cargarColumnasTablaStats(ivPartidos, rowColumn, context, nameStats[0], nameIconStats[0],
                sizeScreen, densityScreen);
        ImageView ivMinutos = new ImageView(context);
        cargarColumnasTablaStats(ivMinutos, rowColumn, context, nameStats[1], nameIconStats[1],
                sizeScreen, densityScreen);
        ImageView ivTarjAmar = new ImageView(context);
        cargarColumnasTablaStats(ivTarjAmar, rowColumn, context, nameStats[2], nameIconStats[2],
                sizeScreen, densityScreen);
        ImageView ivTarjRojas = new ImageView(context);
        cargarColumnasTablaStats(ivTarjRojas, rowColumn, context, nameStats[3], nameIconStats[3],
                sizeScreen, densityScreen);
        ImageView ivGoles = new ImageView(context);
        cargarColumnasTablaStats(ivGoles, rowColumn, context, nameStats[4], nameIconStats[4],
                sizeScreen, densityScreen);
        ImageView ivAsist = new ImageView(context);
        cargarColumnasTablaStats(ivAsist, rowColumn, context, nameStats[5], nameIconStats[5],
                sizeScreen, densityScreen);
        ImageView ivDisparos = new ImageView(context);
        cargarColumnasTablaStats(ivDisparos, rowColumn, context, nameStats[6], nameIconStats[6],
                sizeScreen, densityScreen);
        ImageView ivRegates = new ImageView(context);
        cargarColumnasTablaStats(ivRegates, rowColumn, context, nameStats[7], nameIconStats[7],
                sizeScreen, densityScreen);
        ImageView ivPases = new ImageView(context);
        cargarColumnasTablaStats(ivPases, rowColumn, context, nameStats[8], nameIconStats[8],
                sizeScreen, densityScreen);
        ImageView ivCentros = new ImageView(context);
        cargarColumnasTablaStats(ivCentros, rowColumn, context, nameStats[9], nameIconStats[9],
                sizeScreen, densityScreen);
        ImageView ivFaltas = new ImageView(context);
        cargarColumnasTablaStats(ivFaltas, rowColumn, context, nameStats[10], nameIconStats[10],
                sizeScreen, densityScreen);
        ImageView ivRobos = new ImageView(context);
        cargarColumnasTablaStats(ivRobos, rowColumn, context, nameStats[11], nameIconStats[11],
                sizeScreen, densityScreen);
        ImageView ivParadas = new ImageView(context);
        cargarColumnasTablaStats(ivParadas, rowColumn, context, nameStats[12], nameIconStats[12],
                sizeScreen, densityScreen);

        tableStats.addView(rowColumn, 0);
    }

    // Método en el que se carga la celda superior de cada columna.
    private void cargarColumnasTablaStats(ImageView imageView, TableRow row, final Context context,
                                          final String stat, final String nameIconStat, final int sizeScreen, final float densityScreen)
    {
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                context.getResources().getInteger(jmm.futbol.futuros.cracks.R.integer.size_icon_stats),
                context.getResources().getInteger(jmm.futbol.futuros.cracks.R.integer.size_icon_stats));
        layoutParams.setMargins(context.getResources().getInteger(jmm.futbol.futuros.cracks.R.integer.margin_lateral_title_stats),
                context.getResources().getInteger(jmm.futbol.futuros.cracks.R.integer.margin_superior_title_stats),
                context.getResources().getInteger(jmm.futbol.futuros.cracks.R.integer.margin_lateral_title_stats),
                context.getResources().getInteger(jmm.futbol.futuros.cracks.R.integer.margin_superior_title_stats));
        imageView.setLayoutParams(layoutParams);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, stat, Toast.LENGTH_SHORT).show();
            }
        });

        rutaImgScoutland claseRutaImgScoutland = new rutaImgScoutland();
        strImagen = claseRutaImgScoutland.obtenerRutaImgScoutland(
                sizeScreen, densityScreen, "IconsStats",
                "icon_txt_" + nameIconStat);

        StorageReference imgIconStats = storageReference.child(strImagen);

        GlideApp.with(context)
                .load(imgIconStats)
                .error(jmm.futbol.futuros.cracks.R.drawable.icon_close)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .dontAnimate()
                .into(imageView);

        row.addView(imageView);
    }

    // Método que carga los valores de cada fila.
    private void cargarFilasTablaStats(TextView textView, Context context, Typeface fuente,
                                       TableRow.LayoutParams layoutParams, TableRow row)
    {
        textView.setTypeface(fuente);
        textView.setTextSize(context.getResources().getDimension(jmm.futbol.futuros.cracks.R.dimen.size_letter_text_scroll_bioplaystats));
        textView.setLayoutParams(layoutParams);
        layoutParams.setMargins(context.getResources().getInteger(jmm.futbol.futuros.cracks.R.integer.margin_lateral_stats),
                context.getResources().getInteger(jmm.futbol.futuros.cracks.R.integer.margin_superior_stats),
                context.getResources().getInteger(jmm.futbol.futuros.cracks.R.integer.margin_lateral_stats),
                context.getResources().getInteger(jmm.futbol.futuros.cracks.R.integer.margin_superior_stats));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(context.getResources().getColor(jmm.futbol.futuros.cracks.R.color.colorPrueba7));
        row.addView(textView);
    }

    private int multiplode2(int numero)
    {
        int resto = numero%2;

        return resto;
    }
}
