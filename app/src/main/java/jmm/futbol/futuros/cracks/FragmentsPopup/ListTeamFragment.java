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
import jmm.futbol.futuros.cracks.Modelo.Equipo;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Fragmento donde se cargarán los datos de cada equipo.
 */
public class ListTeamFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerPopup;

    private static int sizeScreen = 0;
    private static float densityScreen = 0;

    FirebaseRecyclerAdapter mAdapter;

    long numJugadoresByEquipo[] = new long[50];
    int idJugadorByEquipo[] = new int[50];

    ArrayList<Integer> posArrayIdEquipo = new ArrayList<>();
    ArrayList<Integer> posArrayidJugador = new ArrayList<>();

    private static final int LISTTEAM_VIEW_TYPE = 1;
    private static final int BANNER_VIEW_TYPE = 2;

    // Constructor de la clase.
    public ListTeamFragment(){
    }

    // Creamos una nueva instancia del fragmento donse se le pasará su id particular, además del tamaño
    // y la densidad de la pantalla.
    public static ListTeamFragment newInstance(int sectionNumber, int sizescreen, float densityscreen)
    {
        ListTeamFragment fragment = new ListTeamFragment();
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
        View rootView = inflater.inflate(jmm.futbol.futuros.cracks.R.layout.fragment_popup, container, false);

        // Creamos referencias a las tablas de las bases de datos de Firebase que vamos a necesitar.
        DatabaseReference dbEquipos = FirebaseDatabase.getInstance().getReference().child("equipo");
        final DatabaseReference dbJugadores = FirebaseDatabase.getInstance().getReference().child("jugador");

        // Relacionamos el layoutManager con el recyclerview creado en el fragmento.
        recyclerPopup = (RecyclerView) rootView.findViewById(jmm.futbol.futuros.cracks.R.id.recycler_popup);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerPopup.setLayoutManager(layoutManager);

        // Creamos un adaptador de Firebase al que le pasaremos los campos de la tabla Equipo, su referencia
        // a Firebase, la clase del adaptador y el layout donde se cargarán los datos de los campos.
        mAdapter = new FirebaseRecyclerAdapter<Equipo, RecTeamAdapter>(
                Equipo.class, jmm.futbol.futuros.cracks.R.layout.popup_league_item, RecTeamAdapter.class, dbEquipos) {

            @Override
            public void populateViewHolder(final RecTeamAdapter recTeamAdapter, final Equipo equipo, final int position) {

                if (position>1 && position % 4 == 0) {

                    recTeamAdapter.setCargarBanner();
                }

                // Dos tipos de fuentes utilizadas en los campos de texto.
                Typeface fuentePopup = Typeface.createFromAsset(getContext().getAssets(), "fonts/Silver_Age_Queens.ttf");
                final Typeface fuente1bold = Typeface.createFromAsset(getContext().getAssets(), "fonts/Champagne_&_Limousines_Bold.ttf");

                // Recogemos los datos de los campos de cada equipo.
                final int idEquipo = Integer.parseInt(equipo.getIdEquipo());
                String nombreEquipo = equipo.getNombreEquipo();
                final String strColorPrimario1 = equipo.getColorPrimario1();
                final String strColorPrimario2 = equipo.getColorPrimario2();
                final String strColorSecundario = equipo.getColorSecundario();

                // Cargamos el link recogido en el campo de la base de datos con el siguiente método.
                String nombreEquipoLink = nombreEquipo.replaceAll(" ", "");
                recTeamAdapter.setLinkImage(getContext(), sizeScreen, densityScreen, nombreEquipoLink.toLowerCase(), 2);

                // Cargamos los nombres de cada equipo, los colores del contenedor y su padding mediante
                // los siguiente métodos.
                recTeamAdapter.setNombreItem(nombreEquipo, fuentePopup, strColorSecundario);
                recTeamAdapter.setColorArrow(strColorSecundario);
                recTeamAdapter.setPaddingItem(getContext());

                // Realizamos una consulta en la que recogeremos los jugadores de cada equipo.
                dbJugadores.orderByChild("Jugador_idEquipo").equalTo(equipo.getIdEquipo())
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                // Una vez que tenemos los jugadores de cada equipo, los contamos para
                                // obtener cuantos jugadores hay en cada equipo y lo representamos en
                                // el layout mediante el siguiente método.
                                numJugadoresByEquipo[position] += dataSnapshot.getChildrenCount();
                                if (numJugadoresByEquipo[position] == 1)
                                {
                                    recTeamAdapter.setNumJugadores(numJugadoresByEquipo[position] + " jugador",
                                            strColorSecundario, fuente1bold);
                                }
                                else
                                {
                                    recTeamAdapter.setNumJugadores(numJugadoresByEquipo[position] + " jugadores",
                                            strColorSecundario, fuente1bold);
                                }

                                // Realizamos de nuevo una consulta para obtener los jugadores de cada equipo, en
                                // este caso para obtener el id de los jugadores que hay en ese equipo.
                                dbJugadores.orderByChild("Jugador_idEquipo").equalTo(equipo.getIdEquipo())
                                        .addChildEventListener(new ChildEventListener() {
                                            @Override
                                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                                // Recogemos la position de cada liga relacionada con cada equipo.
                                                posArrayIdEquipo.add(position);
                                                // Recogemos los id de los jugadores de cada equipo contenido en cada liga.
                                                posArrayidJugador.add(Integer.parseInt((String) dataSnapshot.child("idJugador").getValue()));

                                                // Definimos el color de fondo de cada etiqueta y los datos que se pasarán al clikar sobre ellos.
                                                recTeamAdapter.setColorHolder(strColorPrimario1, strColorPrimario2, getContext(),
                                                        3, posArrayidJugador, posArrayIdEquipo, idEquipo, 0, sizeScreen, densityScreen,
                                                        (int) numJugadoresByEquipo[position]);
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
        };

        // Cargamos el adaptador en el recyclerview.
        recyclerPopup.setAdapter(mAdapter);

        return rootView;
    }
}
