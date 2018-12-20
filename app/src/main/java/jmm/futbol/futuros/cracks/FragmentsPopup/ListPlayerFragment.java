package jmm.futbol.futuros.cracks.FragmentsPopup;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jmm.futbol.futuros.cracks.Adaptadores.RecTeamAdapter;
import jmm.futbol.futuros.cracks.Modelo.Jugador;
import jmm.futbol.futuros.cracks.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Fragmento donde se cargarán los datos de cada jugador.
 */
public class ListPlayerFragment extends Fragment{

    private static final String ARG_SECTION_NUMBER = "section_number";
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerPopup;

    private static int sizeScreen = 0;
    private static float densityScreen = 0;

    FirebaseRecyclerAdapter mAdapter;

    // Constructor de la clase.
    public ListPlayerFragment() {
    }

    // Creamos una nueva instancia del fragmento donse se le pasará su id particular, además del tamaño
    // y la densidad de la pantalla.
    public static ListPlayerFragment newInstance(int sectionNumber, int sizescreen, float densityscreen)
    {
        ListPlayerFragment fragment = new ListPlayerFragment();
        Bundle args = new Bundle();
        sizeScreen = sizescreen;
        densityScreen = densityscreen;
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putFloat(ARG_SECTION_NUMBER, densityscreen);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflamos un layout en el contenedor del fragmento.
        View rootView = inflater.inflate(R.layout.fragment_popup, container, false);

        // Creamos referencias a las tablas de las bases de datos de Firebase que vamos a necesitar.
        final DatabaseReference dbJugadores = FirebaseDatabase.getInstance().getReference().child("jugador");
        final DatabaseReference dbPosiciones = FirebaseDatabase.getInstance().getReference().child("posicion");

        // Relacionamos el layoutManager con el recyclerview creado en el fragmento.
        recyclerPopup = (RecyclerView) rootView.findViewById(R.id.recycler_popup);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerPopup.setLayoutManager(layoutManager);

        // Creamos un adaptador de Firebase al que le pasaremos los campos de la tabla Jugador, su referencia
        // a Firebase, la clase del adaptador y el layout donde se cargarán los datos de los campos.
        mAdapter = new FirebaseRecyclerAdapter<Jugador, RecTeamAdapter>(
                Jugador.class, R.layout.popup_player_item, RecTeamAdapter.class, dbJugadores) {

            @Override
            public void populateViewHolder(final RecTeamAdapter recTeamAdapter, Jugador jugador, int position) {

                if (position>1 && position % 4 == 0) {

                    recTeamAdapter.setCargarBanner();
                }

                // Dos tipos de fuentes utilizadas en los campos de texto.
                Typeface fuentePopup = Typeface.createFromAsset(getContext().getAssets(), "fonts/Silver_Age_Queens.ttf");
                final Typeface fuente1bold = Typeface.createFromAsset(getContext().getAssets(), "fonts/Champagne_&_Limousines_Bold.ttf");

                // Recogemos los datos de los campos de cada jugador.
                String imagenJugador = jugador.getImagen();
                final int idJugador = Integer.parseInt(jugador.getIdJugador());
                String nombreJugador = jugador.getNombre();
                String apellidoJugador = jugador.getApellido();
                final String edadJugador = jugador.getEdad();
                final String equipoJugador = jugador.getEquipo();
                final String strColorPrimario1 = jugador.getColorPrimario1();
                final String strColorPrimario2 = jugador.getColorPrimario2();
                final String strColorSecundario = jugador.getColorSecundario();

                // Realizamos una consulta en la que recogeremos a todos los jugadores de la base de datos.
                dbJugadores
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                // Contamos la cantidad de jugadores que hay en la base de datos, necesario
                                // para luego podemos movernos entre un jugador y otro.
                                int numJugadores = (int) dataSnapshot.getChildrenCount();
                                // Definimos el color de fondo de cada etiqueta y los datos que se pasarán al clikar sobre ellos.
                                recTeamAdapter.setColorHolderPlayer(strColorPrimario1, strColorPrimario2,
                                        getContext(), 1, idJugador, 0, sizeScreen, densityScreen, numJugadores);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                                recTeamAdapter.setColorHolderPlayer(strColorPrimario1, strColorPrimario2,
                                        getContext(), 1, idJugador, 0, sizeScreen, densityScreen, 0);
                            }
                        });

                // Cargamos el enlace en el contenedor de cada imagen a mostrar.
                recTeamAdapter.setLinkImage(getContext(), sizeScreen, densityScreen, imagenJugador, 1);

                // Cargamos el nombre del jugador en cada etiqueta.
                recTeamAdapter.setNombreJugador(nombreJugador, apellidoJugador, fuentePopup, strColorSecundario);
                // Cargamos el color del icono de la flecha que hay en cada etiqueta.
                recTeamAdapter.setColorArrow(strColorSecundario);
                // Definimos el padding de cada etiqueta.
                recTeamAdapter.setPaddingItemPlayer(getContext());

                // Ejecutamos una consulta en la que cargaremos las posiciones asociadas al id de un
                // jugador determinado.
                dbPosiciones.orderByChild("Posicion_idJugador").equalTo(jugador.getIdJugador())
                        .addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                // Recogemos la posición del jugador en una variable y cargamos el resto de datos
                                // mediante su método correspondiente.
                                String posicionJugador = dataSnapshot.child("AbrevPosicion").getValue().toString();
                                recTeamAdapter.setInfoJugador(edadJugador, posicionJugador, equipoJugador, fuente1bold, strColorSecundario);
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

            }
        };

        // Cargamos el adaptador en el recyclerview.
        recyclerPopup.setAdapter(mAdapter);

        return rootView;
    }
}
