package jmm.futbol.futuros.cracks.FragmentsContentPlayer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jmm.futbol.futuros.cracks.Adaptadores.RecParrafosAdapter;
import jmm.futbol.futuros.cracks.Modelo.ParrafosAnalisis;
import app.example.parallaxviewpage.NotifyingScrollView;
import app.example.parallaxviewpage.ScrollViewFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

/**
 * Fragmento que engloba al contenido de la Biografía de un jugador
 */
public class BioFragment extends ScrollViewFragment {

    public static int idJugador;
    public static int tipoTexto = 0;

    private static int sizeScreen = 0;
    private static float densityScreen = 0;

    private RecyclerView recyclerContent;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseRecyclerAdapter adapterParrafos;

    private ArrayList<Integer> posIdParrafo = new ArrayList<>();

    public String nombreJugador;

    // Creamos una nueva instancia del fragmento donse se le pasará su id particular, además del tamaño
    // y la densidad de la pantalla.
    public static Fragment newInstance(int position, int idjugador, int sizescreen, float densityscreen)
    {
        BioFragment fragment = new BioFragment();
        Bundle args = new Bundle();
        idJugador = idjugador;
        sizeScreen = sizescreen;
        densityScreen = densityscreen;
        args.putInt(ARG_POSITION, position);
        args.putInt(ARG_POSITION, idjugador);
        args.putFloat(ARG_POSITION, densityscreen);
        fragment.setArguments(args);
        return fragment;
    }

    // Constructor de la clase.
    public BioFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState)
    {
        mPosition = getArguments().getInt(ARG_POSITION);

        // Inflamos un layout en el contenedor del fragmento.
        View view = inflater.inflate(jmm.futbol.futuros.cracks.R.layout.fragment_main, container, false);

        // Permitidos el desplazamiento vertical táctil en el contenedor.
        mScrollView = (NotifyingScrollView) view.findViewById(jmm.futbol.futuros.cracks.R.id.scrollView);
        setScrollViewOnScrollListener();

        // Creamos referencias a las tablas de las bases de datos de Firebase que vamos a necesitar.
        final DatabaseReference dbJugadores = FirebaseDatabase.getInstance().getReference().child("jugador");
        final Query dbParrafos = FirebaseDatabase.getInstance().getReference().child("parrafosanalisis")
                .orderByChild("tipoParrafo").equalTo("0_" + idJugador);

        // Relacionamos el layoutManager con el recyclerview creado en el fragmento.
        recyclerContent = (RecyclerView) view.findViewById(jmm.futbol.futuros.cracks.R.id.recycler_content);
        layoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerContent.setLayoutManager(layoutManager);
        recyclerContent.setNestedScrollingEnabled(false);

        // Lanzamos una consulta donde obentendremos los datos del jugador con un id particular.
        dbJugadores.orderByChild("idJugador").equalTo(String.valueOf(idJugador))
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        // Obtenemos de ese jugador su nombre completo, el cual necesitaremos para
                        // construir los enlaces de las ilustraciones del contenido.
                        nombreJugador = (String) dataSnapshot.child("Nombre").getValue() +
                                dataSnapshot.child("Apellido").getValue();
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

        // Creamos un adaptador de Firebase al que le pasaremos los campos de la tabla ParrafosAnalisis,
        // su referencia a Firebase, la clase del adaptador y el layout donde se cargarán los datos de los campos.
        adapterParrafos = new FirebaseRecyclerAdapter<ParrafosAnalisis, RecParrafosAdapter>(
                ParrafosAnalisis.class, jmm.futbol.futuros.cracks.R.layout.recycler_bioplay, RecParrafosAdapter.class, dbParrafos)
        {
            @Override
            protected void populateViewHolder(final RecParrafosAdapter recParrafosAdapter, final ParrafosAnalisis parrafo, final int position) {

                // Recogemos los datos de los campos de cada párrafo.
                int idParrafo = Integer.parseInt(parrafo.getIdParrafo());
                int idJugadorByParrafo = Integer.parseInt(parrafo.getParrafo_idJugador());
                String textoParrafo = parrafo.getTextoParrafo();
                String autorFoto = parrafo.getAutorFoto();

                // Pasaremos al adapatador de la clase sólo los párrafos que pertenecen al jugador en cuestión.
                if (idJugadorByParrafo == idJugador)
                {
                    recParrafosAdapter.setCargarBanner();
                    // Hacemos visible el contenedor del párrafo.
                    recParrafosAdapter.setHolderVisibility();
                    // Visibilizamos el espacio inicial necesario para que el contenido no se pise con
                    // el contenedor superior de la información del jugador.
                    recParrafosAdapter.setEspacioInicial(position);
                    // Mostramos el contenido de cada párrafo.
                    recParrafosAdapter.setContenidoParrafo(position, tipoTexto, "", textoParrafo, getContext());
                    // Mostramos las ilustraciones del contenido a partir de los enlaces pasados, y el
                    // tamaño y densidad de la pantalla donde se ejecuta.
                    recParrafosAdapter.setLinkImgParrafo(position, tipoTexto, sizeScreen, densityScreen, getContext(),
                            nombreJugador, posIdParrafo.size());
                    // Se muestra un enlace con el autor de la ilustración en caso de que tenga.
                    if (autorFoto != null)
                    {
                        recParrafosAdapter.setAutorImg(autorFoto);
                    }

                    // Añadimos el id del párrafo a un array que será el que cuente por que número
                    // de ilustración vamos
                    posIdParrafo.add(idParrafo);
                }
            }
        };

        // Cargamos el adaptador en el recyclerview.
        recyclerContent.setAdapter(adapterParrafos);

        return view;
    }
}
