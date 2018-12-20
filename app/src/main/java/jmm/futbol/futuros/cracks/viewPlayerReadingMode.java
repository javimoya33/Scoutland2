package jmm.futbol.futuros.cracks;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.example.parallaxviewpage.ParallaxFragmentPagerAdapter;
import app.example.parallaxviewpage.ParallaxViewPagerBaseActivity;
import jmm.futbol.futuros.cracks.FragmentsContentPlayer.BioFragment;
import jmm.futbol.futuros.cracks.FragmentsContentPlayer.PlayFragment;
import jmm.futbol.futuros.cracks.FragmentsContentPlayer.StatFragment;
import jmm.futbol.futuros.cracks.cargas.cargaInfoJugador;
import jmm.futbol.futuros.cracks.slidingTab.SlidingTabLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * Created by Acer on 12/07/2017.
 * Clase correspondiente a la vista de un jugador en particular
 */
public class viewPlayerReadingMode extends ParallaxViewPagerBaseActivity {

    /**
     * Etiqueta de depuración
     */
    private static final String TAG = viewPlayerReadingMode.class.getSimpleName();

    // Variables de la cabecera de la vista
    private ImageView imgArrowLeft;
    private TextView nomJugadorCab;
    private TextView apeJugadorCab;
    private ImageView imgArrowRight;
    private LinearLayout linearHeader;
    private LinearLayout linearCabecera;

    // Variables correspondientes al enunciado de los datos del jugador de la vista
    private TextView txtFechaNacimiento;
    private TextView txtLugarNacimiento;
    private ImageView flagPaisNacimiento;
    private TextView txtAltura;
    private TextView txtPie;
    private View view1demarc;
    private View view2demarc;
    private View view3demarc;
    private View view4demarc;
    private View view5demarc;

    // Variables correspondientes a los datos del jugador de la vista.
    private  int idJugador;
    private ImageView imgJugador;
    private TextView nombreJugador;
    private TextView apellidoJugador;
    private TextView fechaNacJugador;
    private TextView lugarNacJugador;
    private TextView alturaJugador;
    private TextView pieJugador;
    private ImageView demarcacionJugador;
    private TextView demarcacion1TxtJugador;
    private TextView demarcacion2TxtJugador;
    private TextView demarcacion3TxtJugador;
    private TextView demarcacion4TxtJugador;
    private TextView posicionJugador;

    // Variables con los datos de tamaño, densidad y ancho de la pantalla que son recibidos al refrescar la página
    private int sizeScreen;
    private float densityScreen;
    private int widthScreen;

    // Variables con los datos de tamaño, densidad y ancho de la pantalla recibidos de MainActivity
    public static float densityScr;
    public static int sizeScr;
    public static int widthScr;

    // Contenedor principal de la vista del jugador, contenedores con la información del jugador, y
    // barra lateral con las diferentes pestañas.
    private CoordinatorLayout parentView;
    private LinearLayout headerIzq;
    private LinearLayout headerDer;
    private SlidingTabLayout mNavigBar;

    // Variable que determinan el idJugador y el número de jugadores que hay en la base de datos, necesarios
    // para poder pasar de la vista de un jugador a otro.
    public static int idJug = 0;
    public int numJugadoresDB = 0;

    // Variable que determinan el idJugador y el número de jugadores de una liga determinada, necesarios
    // para poder pasar de la vista de un jugador a otro.
    public static int posArrayJugLiga = 0;
    public int numJugLigaDB = 0;

    // Variable que determinan el idJugador y el número de jugadores de un equipo determinado, necesarios
    // para poder pasar de la vista de un jugador a otro.
    public static int posArrayJugEquipo = 0;
    public int numJugEquipoDB = 0;

    // Variable que determinan el idJugador y el número de jugadores de un país determinado, necesarios
    // para poder pasar de la vista de un jugador a otro.
    public static int posArrayJugNac = 0;
    public int numJugNacDB = 0;

    public ArrayList<Integer> idJugadores = new ArrayList<Integer>();

    // Variable que determina los milisegundos que tienen que pasar entre pase y pase de un jugador a otro.
    // Esto evita la sobrecarga de la app.
    private long time = 5000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(jmm.futbol.futuros.cracks.R.layout.activity_view_player);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();

        // Método en el que se recogen variables necesarias para realizar el efecto al deslizar la pantalla
        // hacia abajo.
        initValues();

        // Método en la que se determina los milisegundos que tienen que pasar entre pase y pase de un jugador
        // a otro. Esto evita la sobrecarga de la app.
        intervaloOnClick();


        imgArrowLeft = (ImageView) findViewById(jmm.futbol.futuros.cracks.R.id.arrow_navig_left);
        nomJugadorCab = (TextView) findViewById(jmm.futbol.futuros.cracks.R.id.nomJugadorCab);
        apeJugadorCab = (TextView) findViewById(jmm.futbol.futuros.cracks.R.id.apeJugadorCab);
        imgArrowRight = (ImageView) findViewById(jmm.futbol.futuros.cracks.R.id.arrow_navig_right);
        linearCabecera = (LinearLayout) findViewById(jmm.futbol.futuros.cracks.R.id.cabecera);
        linearHeader = (LinearLayout) findViewById(jmm.futbol.futuros.cracks.R.id.linearHeader);

        imgJugador = (ImageView) findViewById(jmm.futbol.futuros.cracks.R.id.imgJugador);
        txtFechaNacimiento = (TextView) findViewById(jmm.futbol.futuros.cracks.R.id.txtFechaNac);
        txtLugarNacimiento = (TextView) findViewById(jmm.futbol.futuros.cracks.R.id.txtLugarNac);
        flagPaisNacimiento = (ImageView) findViewById(jmm.futbol.futuros.cracks.R.id.flagPaisNac);
        txtAltura = (TextView) findViewById(jmm.futbol.futuros.cracks.R.id.txtAltura);
        txtPie = (TextView) findViewById(jmm.futbol.futuros.cracks.R.id.txtPie);

        nombreJugador = (TextView) findViewById(jmm.futbol.futuros.cracks.R.id.nomJugador);
        apellidoJugador = (TextView) findViewById(jmm.futbol.futuros.cracks.R.id.apeJugador);
        fechaNacJugador = (TextView) findViewById(jmm.futbol.futuros.cracks.R.id.fechaNacJugador);
        lugarNacJugador = (TextView) findViewById(jmm.futbol.futuros.cracks.R.id.lugarNacJugador);
        alturaJugador = (TextView) findViewById(jmm.futbol.futuros.cracks.R.id.altJugador);
        pieJugador = (TextView) findViewById(jmm.futbol.futuros.cracks.R.id.pieJugador);
        demarcacionJugador = (ImageView) findViewById(jmm.futbol.futuros.cracks.R.id.demarcacionJugador);
        demarcacion1TxtJugador = (TextView) findViewById(jmm.futbol.futuros.cracks.R.id.dem1TxtJugador);
        demarcacion2TxtJugador = (TextView) findViewById(jmm.futbol.futuros.cracks.R.id.dem2TxtJugador);
        demarcacion3TxtJugador = (TextView) findViewById(jmm.futbol.futuros.cracks.R.id.dem3TxtJugador);
        demarcacion4TxtJugador = (TextView) findViewById(jmm.futbol.futuros.cracks.R.id.dem4TxtJugador);
        view1demarc = (View) findViewById(jmm.futbol.futuros.cracks.R.id.view1Demarc);
        view2demarc = (View) findViewById(jmm.futbol.futuros.cracks.R.id.view2Demarc);
        view3demarc = (View) findViewById(jmm.futbol.futuros.cracks.R.id.view3Demarc);
        view4demarc = (View) findViewById(jmm.futbol.futuros.cracks.R.id.view4Demarc);
        view5demarc = (View) findViewById(jmm.futbol.futuros.cracks.R.id.view5Demarc);

        final DatabaseReference dbJugadores = FirebaseDatabase.getInstance().getReference().child("jugador");

        // Fuentes utilizas en el mostrado en la vista.
        Typeface fuente1 = Typeface.createFromAsset(this.getAssets(), "fonts/Champagne_&_Limousines.ttf");
        Typeface fuente1bold = Typeface.createFromAsset(this.getAssets(), "fonts/Champagne_&_Limousines_Bold.ttf");
        Typeface fuente2 = Typeface.createFromAsset(this.getAssets(), "fonts/Silver_Age_Queens.ttf");

        // Inflado de la vista con la edición del texto en cada pestaña.
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(jmm.futbol.futuros.cracks.R.layout.text_tab_view_player, null);
        TextView tabNavigBar = (TextView) view.findViewById(jmm.futbol.futuros.cracks.R.id.item);
        tabNavigBar.setTypeface(fuente1);

        // Enlaza cada TextView con su fuente correspondiente.
        txtFechaNacimiento.setTypeface(fuente1);
        txtLugarNacimiento.setTypeface(fuente1);
        txtAltura.setTypeface(fuente1);
        txtPie.setTypeface(fuente1);
        nomJugadorCab.setTypeface(fuente1bold);
        apeJugadorCab.setTypeface(fuente2);
        nombreJugador.setTypeface(fuente1bold);
        apellidoJugador.setTypeface(fuente2);
        fechaNacJugador.setTypeface(fuente1bold);
        lugarNacJugador.setTypeface(fuente1bold);
        alturaJugador.setTypeface(fuente1bold);
        pieJugador.setTypeface(fuente1bold);
        demarcacion1TxtJugador.setTypeface(fuente1bold);
        demarcacion2TxtJugador.setTypeface(fuente1);
        demarcacion3TxtJugador.setTypeface(fuente1);
        demarcacion4TxtJugador.setTypeface(fuente1);

        // Contenedor principal de la vista ActivityPlayer.
        parentView = (CoordinatorLayout) findViewById(jmm.futbol.futuros.cracks.R.id.parent_view);
        // Variable que enlaza con la librería 'slidingTab' encargada de gestionar el proceso de las pestañas
        // y su contendio adyacente.
        mViewPager = (ViewPager) findViewById(jmm.futbol.futuros.cracks.R.id.view_pager);
        // Franja que contiene a las pestañas de la vista.
        mNavigBar = (SlidingTabLayout) findViewById(jmm.futbol.futuros.cracks.R.id.navig_tab);

        // Contenedores concernientes a la franja superior de la vista, donde se incluya la información
        // explícita de un jugador.
        mHeader = findViewById(jmm.futbol.futuros.cracks.R.id.header);
        headerIzq = (LinearLayout) findViewById(jmm.futbol.futuros.cracks.R.id.header_izq);
        headerDer = (LinearLayout) findViewById(jmm.futbol.futuros.cracks.R.id.header_der);

        // Efecto que reduce la vista de las imágenes y contenedores superior al deslizar la vista hacia abajo.
        if (savedInstanceState != null)
        {
            headerIzq.setTranslationY(savedInstanceState.getFloat(IMAGE_TRANSLATION_Y));
            headerDer.setTranslationY(savedInstanceState.getFloat(IMAGE_TRANSLATION_Y));
            mHeader.setTranslationY(savedInstanceState.getFloat(HEADER_TRANSLATION_Y));
        }

        // Recibe los datos del identificador de la vista (envío), el tamaño, la densidad y el ancho de la pantalla
        // provenientes de la clase que ha llamado al ActivityPlayer
        int envio = getIntent().getIntExtra("envio", 1);
        sizeScreen = getIntent().getIntExtra("sizeScreen", 1);
        densityScreen = getIntent().getFloatExtra("densityScreen", 1);
        widthScreen = getIntent().getIntExtra("widthScreen", 1);

        // Dependiendo del identificador de la vista que reciba
        switch (envio)
        {
            // Si el valor del envío es 1 se ha pulsado a un jugador de la pantalla principal, o del menu
            // de todos los jugadores del popup.
            case 1:
                idJugador = getIntent().getIntExtra("idJugador", 1);
                idJug = idJugador;

                // Se llama al método que cargará los datos del jugador procedentes de la base de datos
                cargaInfoJugador cargaInfoJugador = new cargaInfoJugador();
                cargaInfoJugador.cargarInfoJugador(getApplicationContext(), TAG, idJugador, nomJugadorCab, apeJugadorCab,
                        imgJugador, nombreJugador, apellidoJugador, fechaNacJugador, lugarNacJugador, flagPaisNacimiento,
                        alturaJugador, pieJugador, posicionJugador, linearCabecera, linearHeader, txtFechaNacimiento,
                        txtLugarNacimiento, txtAltura, txtPie, demarcacionJugador, demarcacion1TxtJugador,
                        demarcacion2TxtJugador, demarcacion3TxtJugador, demarcacion4TxtJugador, view1demarc, view2demarc,
                        view3demarc, view4demarc, view5demarc, mNavigBar, mViewPager, imgArrowLeft, imgArrowRight, sizeScreen,
                        densityScreen, storageReference, dbJugadores);

                // Se recibe el dato referente a la lista de números jugadores totales que hay en la base de datos.
                // Esto permitirá pasar de un jugador a otro pulsando el botón correspondiente.
                int numJugDB = getIntent().getIntExtra("numJugadoresDB", 1);
                numJugadoresDB = numJugDB;

                // Si se llega al id menor (en nuestra BD es 2) se deshabilitará el botón 'Anterior'
                if (idJug <= 2)
                {
                    imgArrowLeft.setOnClickListener(null);
                }
                else
                {
                    imgArrowLeft.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            // En caso contrario de no haber llegado al id menor se podrá pasar a la vista
                            // del jugador con el id -1. Se dejará un intervalo de 3000 milisegundos entre
                            // pulsado y pulsado para evitar sobrecarga en la app.
                            if (time >= 3000)
                            {
                                Intent refreshViewPlayer = new Intent(getApplicationContext(), viewPlayerReadingMode.class);
                                refreshViewPlayer.putExtra("envio", 1);
                                refreshViewPlayer.putExtra("idJugador", idJug - 1);
                                refreshViewPlayer.putExtra("sizeScreen", sizeScreen);
                                refreshViewPlayer.putExtra("densityScreen", densityScreen);
                                refreshViewPlayer.putExtra("numJugadoresDB", numJugadoresDB);
                                startActivity(refreshViewPlayer);
                                finish();

                                time = 0;
                            }
                        }
                    });
                }

                // Si se llega al id mayor se deshabilitará el botón 'Siguiente'. Para esto es necesario saber
                // el número de jugadores que pertenecen a una liga particular ('numJugDB').
                if (idJug >= 1 + numJugDB)
                {
                    imgArrowRight.setOnClickListener(null);
                }
                else
                {
                    imgArrowRight.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (time >= 3000)
                            {
                                Intent refreshViewPlayer = new Intent(getApplicationContext(), viewPlayerReadingMode.class);
                                refreshViewPlayer.putExtra("envio", 1);
                                refreshViewPlayer.putExtra("idJugador", idJug + 1);
                                refreshViewPlayer.putExtra("sizeScreen", sizeScreen);
                                refreshViewPlayer.putExtra("densityScreen", densityScreen);
                                refreshViewPlayer.putExtra("numJugadoresDB", numJugadoresDB);
                                startActivity(refreshViewPlayer);
                                finish();

                                time = 0;
                            }
                        }
                    });
                }
                break;

            case 2:
                // Si el valor del envío es 2 se ha pulsado a una liga del menu popup.
                final int idLiga = getIntent().getIntExtra("idLiga", 1);
                idJugadores = getIntent().getIntegerArrayListExtra("idJugador");
                posArrayJugLiga = getIntent().getIntExtra("posArrayJugLiga", 1);
                final int numJugLiga = getIntent().getIntExtra("numJugLigaDB", 1);

                idJug = idJugadores.get(posArrayJugLiga);
                numJugLigaDB = numJugLiga;

                // Se llama al método que cargará los datos procedentes de la base de datos del jugador
                // de esa liga pulsada.
                cargaInfoJugador cargaIdJugadoresByLiga = new cargaInfoJugador();
                cargaIdJugadoresByLiga.cargarInfoJugador(getApplicationContext(), TAG, idJug, nomJugadorCab, apeJugadorCab,
                        imgJugador, nombreJugador, apellidoJugador, fechaNacJugador, lugarNacJugador, flagPaisNacimiento,
                        alturaJugador, pieJugador, posicionJugador, linearCabecera, linearHeader, txtFechaNacimiento,
                        txtLugarNacimiento, txtAltura, txtPie, demarcacionJugador, demarcacion1TxtJugador,
                        demarcacion2TxtJugador, demarcacion3TxtJugador, demarcacion4TxtJugador, view1demarc, view2demarc,
                        view3demarc, view4demarc, view5demarc, mNavigBar, mViewPager, imgArrowLeft, imgArrowRight, sizeScreen,
                        densityScreen, storageReference, dbJugadores);

                // Si se llega al id menor de entre las ligas se deshabilitará el botón 'Anterior'.
                if (posArrayJugLiga <= 0)
                {
                    imgArrowLeft.setOnClickListener(null);
                }
                else
                {
                    imgArrowLeft.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (time >= 3000)
                            {
                                Intent refreshViewPlayer = new Intent(getApplicationContext(), viewPlayerReadingMode.class);
                                refreshViewPlayer.putExtra("envio", 2);
                                refreshViewPlayer.putExtra("idLiga", idLiga);
                                refreshViewPlayer.putExtra("idJugador", idJugadores);
                                refreshViewPlayer.putExtra("posArrayJugLiga", posArrayJugLiga - 1);
                                refreshViewPlayer.putExtra("numJugLigaDB", numJugLigaDB);
                                refreshViewPlayer.putExtra("sizeScreen", sizeScreen);
                                refreshViewPlayer.putExtra("densityScreen", densityScreen);
                                startActivity(refreshViewPlayer);
                                finish();

                                time = 0;
                            }
                        }
                    });
                }

                // Si se llega al id de liga mayor se deshabilitará el botón de 'Siguiente'
                if (posArrayJugLiga >= numJugLigaDB - 1)
                {
                    imgArrowRight.setOnClickListener(null);
                }
                else
                {
                    imgArrowRight.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (time >= 3000)
                            {
                                Intent refreshViewPlayer = new Intent(getApplicationContext(), viewPlayerReadingMode.class);
                                refreshViewPlayer.putExtra("envio", 2);
                                refreshViewPlayer.putExtra("idLiga", idLiga);
                                refreshViewPlayer.putExtra("idJugador", idJugadores);
                                refreshViewPlayer.putExtra("posArrayJugLiga", posArrayJugLiga + 1);
                                refreshViewPlayer.putExtra("numJugLigaDB", numJugLigaDB);
                                refreshViewPlayer.putExtra("sizeScreen", sizeScreen);
                                refreshViewPlayer.putExtra("densityScreen", densityScreen);
                                startActivity(refreshViewPlayer);
                                finish();

                                time = 0;
                            }
                        }
                    });
                }
                break;

            case 3:
                // Si el valor del envío es 3 se ha pulsado a un equipo del menu popup.
                final int idEquipo = getIntent().getIntExtra("idEquipo", 1);
                idJugadores = getIntent().getIntegerArrayListExtra("idJugador");
                posArrayJugEquipo = getIntent().getIntExtra("posArrayJugEquipo", 1);
                final int numJugEquipo = getIntent().getIntExtra("numJugEquipoDB", 1);

                idJug = idJugadores.get(posArrayJugEquipo);
                numJugEquipoDB = numJugEquipo;

                // Se llama al método que cargará los datos procedentes de la base de datos del jugador
                // de ese equipo pulsado.
                cargaInfoJugador cargaIdJugadoresByEquipo = new cargaInfoJugador();
                cargaIdJugadoresByEquipo.cargarInfoJugador(getApplicationContext(), TAG, idJug, nomJugadorCab, apeJugadorCab,
                        imgJugador, nombreJugador, apellidoJugador, fechaNacJugador, lugarNacJugador, flagPaisNacimiento,
                        alturaJugador, pieJugador, posicionJugador, linearHeader, linearCabecera, txtFechaNacimiento,
                        txtLugarNacimiento, txtAltura, txtPie, demarcacionJugador, demarcacion1TxtJugador,
                        demarcacion2TxtJugador, demarcacion3TxtJugador, demarcacion4TxtJugador, view1demarc, view2demarc,
                        view3demarc, view4demarc, view5demarc, mNavigBar, mViewPager, imgArrowLeft, imgArrowRight, sizeScreen,
                        densityScreen, storageReference, dbJugadores);

                if (posArrayJugEquipo <= 0)
                {
                    imgArrowLeft.setOnClickListener(null);
                }
                else
                {
                    imgArrowLeft.setOnClickListener(new View.OnClickListener() {


                        @Override
                        public void onClick(View v) {

                            if (time >= 3000)
                            {
                                Intent refreshViewPlayer = new Intent(getApplicationContext(), viewPlayerReadingMode.class);
                                refreshViewPlayer.putExtra("envio", 3);
                                refreshViewPlayer.putExtra("idEquipo", idEquipo);
                                refreshViewPlayer.putExtra("idJugador", idJugadores);
                                refreshViewPlayer.putExtra("posArrayJugEquipo", posArrayJugEquipo - 1);
                                refreshViewPlayer.putExtra("numJugEquipoDB", numJugEquipoDB);
                                refreshViewPlayer.putExtra("sizeScreen", sizeScreen);
                                refreshViewPlayer.putExtra("densityScreen", densityScreen);
                                startActivity(refreshViewPlayer);
                                finish();

                                time = 0;
                            }
                        }
                    });
                }

                if (posArrayJugEquipo >= numJugEquipoDB - 1)
                {
                    imgArrowRight.setOnClickListener(null);
                }
                else
                {
                    imgArrowRight.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (time >= 3000)
                            {
                                Intent refreshViewPlayer = new Intent(getApplicationContext(), viewPlayerReadingMode.class);
                                refreshViewPlayer.putExtra("envio", 3);
                                refreshViewPlayer.putExtra("idEquipo", idEquipo);
                                refreshViewPlayer.putExtra("idJugador", idJugadores);
                                refreshViewPlayer.putExtra("posArrayJugEquipo", posArrayJugEquipo + 1);
                                refreshViewPlayer.putExtra("numJugEquipoDB", numJugEquipoDB);
                                refreshViewPlayer.putExtra("sizeScreen", sizeScreen);
                                refreshViewPlayer.putExtra("densityScreen", densityScreen);
                                startActivity(refreshViewPlayer);
                                finish();

                                time = 0;
                            }
                        }
                    });
                }
                break;

            case 4:
                // Si el valor del envío es 4 se ha pulsado a un país del menu popup.
                final String idNac = getIntent().getStringExtra("idNac");
                idJugadores = getIntent().getIntegerArrayListExtra("idJugador");
                posArrayJugNac = getIntent().getIntExtra("posArrayJugNac", 1);
                final int numJugNac = getIntent().getIntExtra("numJugNacDB", 1);

                idJug = idJugadores.get(posArrayJugNac);
                numJugNacDB = numJugNac;

                // Se llama al método que cargará los datos procedentes de la base de datos del jugador
                // de ese país pulsado.
                cargaInfoJugador cargaIdJugadoresByNac = new cargaInfoJugador();
                cargaIdJugadoresByNac.cargarInfoJugador(getApplicationContext(), TAG, idJug, nomJugadorCab, apeJugadorCab,
                        imgJugador, nombreJugador, apellidoJugador, fechaNacJugador, lugarNacJugador, flagPaisNacimiento,
                        alturaJugador, pieJugador, posicionJugador, linearHeader, linearCabecera, txtFechaNacimiento,
                        txtLugarNacimiento, txtAltura, txtPie, demarcacionJugador, demarcacion1TxtJugador,
                        demarcacion2TxtJugador, demarcacion3TxtJugador, demarcacion4TxtJugador, view1demarc, view2demarc,
                        view3demarc, view4demarc, view5demarc, mNavigBar, mViewPager, imgArrowLeft, imgArrowRight, sizeScreen,
                        densityScreen, storageReference, dbJugadores);

                if (posArrayJugNac <= 0)
                {
                    imgArrowLeft.setOnClickListener(null);
                }
                else
                {
                    imgArrowLeft.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (time >= 3000)
                            {
                                Intent refreshViewPlayer = new Intent(getApplicationContext(), viewPlayerReadingMode.class);
                                refreshViewPlayer.putExtra("envio", 4);
                                refreshViewPlayer.putExtra("idNac", idNac);
                                refreshViewPlayer.putExtra("idJugador", idJugadores);
                                refreshViewPlayer.putExtra("posArrayJugNac", posArrayJugNac - 1);
                                refreshViewPlayer.putExtra("numJugNacDB", numJugNacDB);
                                refreshViewPlayer.putExtra("sizeScreen", sizeScreen);
                                refreshViewPlayer.putExtra("densityScreen", densityScreen);
                                startActivity(refreshViewPlayer);
                                finish();

                                time = 0;
                            }
                        }
                    });
                }

                if (posArrayJugNac >= numJugNacDB - 1)
                {
                    imgArrowRight.setOnClickListener(null);
                }
                else
                {
                    imgArrowRight.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (time >= 3000)
                            {
                                Intent refreshViewPlayer = new Intent(getApplicationContext(), viewPlayerReadingMode.class);
                                refreshViewPlayer.putExtra("envio", 4);
                                refreshViewPlayer.putExtra("idNac", idNac);
                                refreshViewPlayer.putExtra("idJugador", idJugadores);
                                refreshViewPlayer.putExtra("posArrayJugNac", posArrayJugNac + 1);
                                refreshViewPlayer.putExtra("numJugNacDB", numJugNacDB);
                                refreshViewPlayer.putExtra("sizeScreen", sizeScreen);
                                refreshViewPlayer.putExtra("densityScreen", densityScreen);
                                startActivity(refreshViewPlayer);
                                finish();

                                time = 0;
                            }
                        }
                    });
                }
                break;
        }

        sizeScr = sizeScreen;
        densityScr = densityScreen;
        widthScr = widthScreen;

        setupAdapter(idJugador, densityScreen);
    }

    @Override
    protected void initValues() {

        int tabHeight = getResources().getDimensionPixelSize(jmm.futbol.futuros.cracks.R.dimen.tab_height);
        mMinHeaderHeight = getResources().getDimensionPixelSize(jmm.futbol.futuros.cracks.R.dimen.min_header_height);
        mHeaderHeight = getResources().getDimensionPixelSize(jmm.futbol.futuros.cracks.R.dimen.header_height);
        mMinHeaderTranslation = -mMinHeaderHeight + tabHeight + this.getResources().getInteger(jmm.futbol.futuros.cracks.R.integer.height_translation);

        mNumFragments = 3;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putFloat(IMAGE_TRANSLATION_Y, headerIzq.getTranslationY());
        outState.putFloat(IMAGE_TRANSLATION_Y, headerDer.getTranslationY());
        outState.putFloat(HEADER_TRANSLATION_Y, mHeader.getTranslationY());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void setupAdapter(int idjugador, float densityScreen) {
        if (mAdapter == null) {
            mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mNumFragments, idjugador, densityScreen);
        }

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(mNumFragments);
        mNavigBar.setOnPageChangeListener(getViewPagerChangeListener());
    }

    @Override
    protected void scrollHeader(int scrollY) {
        float translationY = Math.max(-scrollY, mMinHeaderTranslation);
        mHeader.setTranslationY(translationY);
        headerIzq.setTranslationY(-translationY / 3);
        headerDer.setTranslationY(-translationY / 3);

        if (mHeader.getTranslationY() == -300.0)
        {
            parentView.setBackgroundColor(0x00FF00);
        }
    }

    public static class ViewPagerAdapter extends ParallaxFragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm, int numFragments, int idjug, float densityScreen) {
            super(fm, numFragments);
        }

        // Método encargado de cargar los fragmentos 
        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            switch (position) {
                case 0:
                    fragment = BioFragment.newInstance(0, idJug, sizeScr, densityScr);
                    break;

                case 1:
                    fragment = PlayFragment.newInstance(0, idJug, sizeScr, densityScr);
                    break;

                case 2:
                    fragment = StatFragment.newInstance(0, idJug, sizeScr, densityScr);
                    break;

                default:
                    throw new IllegalArgumentException("Wrong page given " + position);
            }
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "Orígenes";

                case 1:
                    return "Cómo juega";

                case 2:
                    return "Estadísticas";

                default:
                    throw new IllegalArgumentException("wrong position for the fragment in vehicle page");
            }
        }
    }

    public void intervaloOnClick()
    {
        time = 0;
        final Handler h = new Handler();
        h.postDelayed(new Runnable() {



            @Override
            public void run() {

                time += 3000;
                h.postDelayed(this, 3000);
            }
        }, 3000);
    }
}
