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

import java.util.ArrayList;

/**
 * Fragmento donde se cargarán los datos de cada país.
 */
public class ListNacFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerPopup;

    private static int sizeScreen = 0;
    private static float densityScreen = 0;

    FirebaseRecyclerAdapter mAdapter;

    long arrJugadoresByNac[] = new long[50];
    String arrPaisNacByJug[] = new String[50];
    boolean repetido = false;
    int numJugadoresByNac;

    ArrayList<Integer> posArrayIdNac = new ArrayList<>();
    ArrayList<Integer> posArrayIdJugador = new ArrayList<>();

    // Constructor de la clase.
    public ListNacFragment(){
    }

    // Creamos una nueva instancia del fragmento donse se le pasará su id particular, además del tamaño
    // y la densidad de la pantalla.
    public static ListNacFragment newInstance(int sectionNumber, int sizescreen, float densityscreen)
    {
        ListNacFragment fragment = new ListNacFragment();
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

        // Relacionamos el layoutManager con el recyclerview creado en el fragmento.
        recyclerPopup = (RecyclerView) rootView.findViewById(R.id.recycler_popup);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerPopup.setLayoutManager(layoutManager);

        // Creamos un adaptador de Firebase al que le pasaremos los campos de la tabla Jugador, su referencia
        // a Firebase, la clase del adaptador y el layout donde se cargarán los datos de los campos.
        mAdapter = new FirebaseRecyclerAdapter<Jugador, RecTeamAdapter>(
                Jugador.class, R.layout.popup_league_item, RecTeamAdapter.class, dbJugadores) {

            @Override
            public void populateViewHolder(final RecTeamAdapter recTeamAdapter, Jugador jugador, final int position) {

                if (position>1 && position % 4 == 0) {

                    recTeamAdapter.setCargarBanner();
                }

                // Dos tipos de fuentes utilizadas en los campos de texto.
                Typeface fuentePopup = Typeface.createFromAsset(getContext().getAssets(), "fonts/Silver_Age_Queens.ttf");
                final Typeface fuente1bold = Typeface.createFromAsset(getContext().getAssets(), "fonts/Champagne_&_Limousines_Bold.ttf");

                // Recogemos los datos de los campos de cada País.
                final int idJugador = Integer.parseInt(jugador.getIdJugador());
                final String nombrePais = jugador.getPais_nac();
                final int idPaisNac = Integer.parseInt(jugador.getIdPaisNac());
                final String strColorPrimario1 = jugador.getColorPrimario1();
                final String strColorPrimario2 = jugador.getColorPrimario2();
                final String strColorSecundario = jugador.getColorSecundario();

                // Recogimos en un array el nombre e id de cada país al que pertenece cada jugador.
                arrPaisNacByJug[position] = nombrePais;
                posArrayIdNac.add(idPaisNac);

                // Determinamos en un bucle si el nombre de país recien cargado esta repetido o no.
                // Si está repetido escondemos el contenedor de esa etiqueta.
                repetido = false;
                numJugadoresByNac = 0;
                for (int i = 0; i < position; i++)
                {
                    if (arrPaisNacByJug[i].equals(nombrePais))
                    {
                        repetido = true;
                        recTeamAdapter.setInvisibilityHolder();
                    }
                }

                // Si el nombre de país no está repetido lo mostramos.
                if (repetido == false)
                {
                    // Cargamos el método que mostrará el icono del país y su nombre correspondiente.
                    recTeamAdapter.setLinkImage(getContext(), sizeScreen, densityScreen, "flag_" + nombrePais, 4);
                    recTeamAdapter.setNombreItem(nombrePais, fuentePopup, strColorSecundario);

                    // Realizamos una consulta que devuelvan los jugadores que pertenecen a un determinado país.
                    dbJugadores.orderByChild("Pais_nac").equalTo(nombrePais)
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    // Una vez que tenemos los jugadores de cada país, los contamos para
                                    // obtener cuantos jugadores hay en cada país y lo representamos en
                                    // el layout mediante el siguiente método.
                                    arrJugadoresByNac[position] += dataSnapshot.getChildrenCount();
                                    if (arrJugadoresByNac[position] == 1)
                                    {
                                        recTeamAdapter.setNumJugadores(arrJugadoresByNac[position] + " jugador",
                                                strColorSecundario, fuente1bold);
                                    }
                                    else
                                    {
                                        recTeamAdapter.setNumJugadores(arrJugadoresByNac[position] + " jugadores",
                                                strColorSecundario, fuente1bold);
                                    }

                                    // Realizamos de nuevo una consulta para obtener los jugadores de cada país, en
                                    // este caso para obtener el id de los jugadores que hay en ese país.
                                    dbJugadores.orderByChild("Pais_nac").equalTo(nombrePais)
                                            .addChildEventListener(new ChildEventListener() {
                                                @Override
                                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                                    // Recogemos los id de los jugadores de cada equipo contenido en cada liga.
                                                    posArrayIdJugador.add(Integer.parseInt((String) dataSnapshot.child("idJugador").getValue()));

                                                    // Definimos el color de fondo de cada etiqueta y los datos que se pasarán al clikar sobre ellos.
                                                    recTeamAdapter.setColorHolder(strColorPrimario1, strColorPrimario2,
                                                            getContext(), 4, posArrayIdJugador, posArrayIdNac, idPaisNac, 0, sizeScreen, densityScreen,
                                                            (int) arrJugadoresByNac[position]);
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

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                    recTeamAdapter.setNumJugadores("0 jugadores", strColorSecundario, fuente1bold);
                                }
                            });
                }

                // Definimos el color del icono de la flecha de cada etiqueta.
                recTeamAdapter.setColorArrow(strColorSecundario);
                // Definimos el padding de cada etiqueta.
                recTeamAdapter.setPaddingItem(getContext());

            }
        };

        // Cargamos el adaptador en el recyclerview.
        recyclerPopup.setAdapter(mAdapter);

        return rootView;
    }
}
