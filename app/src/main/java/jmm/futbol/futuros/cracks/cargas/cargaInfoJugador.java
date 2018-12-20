package jmm.futbol.futuros.cracks.cargas;

import android.content.Context;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import jmm.futbol.futuros.cracks.slidingTab.SlidingTabLayout;
import jmm.futbol.futuros.cracks.tools.GlideApp;
import jmm.futbol.futuros.cracks.tools.rutaImgScoutland;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

/**
 * Clase que contiene la carga de la información de cada jugador.
 */
public class cargaInfoJugador {

    private String strImagen = "";
    private String strImgDemarcacion = "";
    private String strImgFlagJugador = "";

    private cargaPosiciones cargarPosiciones = new cargaPosiciones();

    // Método que carga la información de cada jugador.
    public void cargarInfoJugador(final Context context, final String TAG, final int idJugador, final TextView nomJugadorCab,
                                  final TextView apeJugadorCab, final ImageView imgJugador, final TextView nomJugador,
                                  final TextView apeJugador, final TextView fechaNacJugador, final TextView lugarNacJugador,
                                  final ImageView flagPaisJugador, final TextView altJugador, final TextView pieJugador,
                                  final TextView posJugador, final LinearLayout cabecera, final LinearLayout linHeader,
                                  final TextView txtFechaNacimiento, final TextView txtLugarNacimiento, final TextView txtAltura,
                                  final TextView txtPie, final ImageView imgDemarcacion, final TextView txtDemarcacion1,
                                  final TextView txtDemarcacion2, final TextView txtDemarcacion3, final TextView txtDemarcacion4,
                                  final View view1demarc, final View view2demarc, final View view3demarc, final View view4demarc,
                                  final View view5demarc, final SlidingTabLayout navigBar, final ViewPager viewPager,
                                  final ImageView arrowLeft, final ImageView arrowRight, final int sizeScreen,
                                  final float densityScreen, final StorageReference storageReference, DatabaseReference dbJugador)
    {
        // Lanzamos la consulta con la información del jugador cuyo id es igual al pasado por parámetro en el método.
        dbJugador.orderByChild("idJugador").equalTo("" + idJugador + "")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(final DataSnapshot dataSnapshot, String s) {

                        // Definimos el color de fondo de la cabecera.
                        linHeader.setBackgroundColor(Color.parseColor(dataSnapshot.child("ColorPrimario1").getValue().toString()));
                        cabecera.setBackgroundColor(Color.parseColor(dataSnapshot.child("ColorPrimario2").getValue().toString()));

                        // Pedimos permiso para acceder a las rutas.
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);

                        // Recogemos la ruta de cada imagen de la vista según el tamaño y la densidad de la pantalla que la ejecuta.
                        rutaImgScoutland claseRutaImgScoutland = new rutaImgScoutland();
                        strImagen = claseRutaImgScoutland.obtenerRutaImgScoutland(
                                sizeScreen, densityScreen, "Perfiles", dataSnapshot.child("Imagen").getValue().toString());
                        strImgDemarcacion = claseRutaImgScoutland.obtenerRutaImgScoutland(sizeScreen, densityScreen,
                                "Posiciones", "position_" + dataSnapshot.child("Imagen").getValue().toString().substring(8));
                        strImgFlagJugador = claseRutaImgScoutland.obtenerRutaImgScoutland(sizeScreen, densityScreen,
                                "Paises", "flag_" + dataSnapshot.child("Pais_nac").getValue().toString());

                        // Creamos las referencias al Storage del Firebase y la asociamos a las rutas creadas.
                        StorageReference imgProfileReference = storageReference.child(strImagen);
                        StorageReference imgDemarcacionReference = storageReference.child(strImgDemarcacion);
                        StorageReference imgFlagJugador = storageReference.child(strImgFlagJugador);

                        // Cargamos las rutas de las imágentes mediante GlideApp.
                        GlideApp.with(context)
                                .load(imgProfileReference)
                                .placeholder(jmm.futbol.futuros.cracks.R.drawable.loading2)
                                .error(jmm.futbol.futuros.cracks.R.drawable.icon_player)
                                .skipMemoryCache(true)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .dontTransform()
                                .into(imgJugador);

                        GlideApp.with(context)
                                .load(imgDemarcacionReference)
                                .placeholder(jmm.futbol.futuros.cracks.R.drawable.loading2)
                                .error(jmm.futbol.futuros.cracks.R.drawable.position_gianluigi_donnarumma)
                                .skipMemoryCache(true)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .dontTransform()
                                .into(imgDemarcacion);

                        GlideApp.with(context)
                                .load(imgFlagJugador)
                                .placeholder(jmm.futbol.futuros.cracks.R.drawable.loading2)
                                .error(jmm.futbol.futuros.cracks.R.drawable.position_gianluigi_donnarumma)
                                .skipMemoryCache(true)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .dontTransform()
                                .into(flagPaisJugador);

                        // Mostramos el contenido del nombre y apellido del jugador con su color de letra correspondiente
                        // en la cabecera.
                        nomJugadorCab.setText(dataSnapshot.child("Nombre").getValue().toString());
                        nomJugadorCab.setTextColor(Color.parseColor(dataSnapshot.child("ColorSecundario").getValue().toString()));
                        apeJugadorCab.setText(dataSnapshot.child("Apellido").getValue().toString());
                        apeJugadorCab.setTextColor(Color.parseColor(dataSnapshot.child("ColorSecundario").getValue().toString()));

                        // Mostramos el contenido del nombre, apellido, fecha y lugar de nacamiento, altura y pie del jugador
                        // con su color de letra correspondiente en la cabecera.
                        nomJugador.setText(dataSnapshot.child("Nombre").getValue().toString());
                        nomJugador.setTextColor(Color.parseColor(dataSnapshot.child("ColorSecundario").getValue().toString()));
                        apeJugador.setText(dataSnapshot.child("Apellido").getValue().toString());
                        apeJugador.setTextColor(Color.parseColor(dataSnapshot.child("ColorSecundario").getValue().toString()));
                        fechaNacJugador.setText(dataSnapshot.child("Fecha_nac").getValue().toString() + "(" +
                                dataSnapshot.child("Edad").getValue().toString() + ")");
                        fechaNacJugador.setTextColor(Color.parseColor(dataSnapshot.child("ColorSecundario").getValue().toString()));
                        lugarNacJugador.setText(dataSnapshot.child("Lugar_nac").getValue().toString() + ", " +
                                dataSnapshot.child("Pais_nac").getValue().toString());
                        lugarNacJugador.setTextColor(Color.parseColor(dataSnapshot.child("ColorSecundario").getValue().toString()));
                        altJugador.setText(dataSnapshot.child("Altura").getValue().toString());
                        altJugador.setTextColor(Color.parseColor(dataSnapshot.child("ColorSecundario").getValue().toString()));
                        pieJugador.setText(dataSnapshot.child("Pie").getValue().toString());
                        pieJugador.setTextColor(Color.parseColor(dataSnapshot.child("ColorSecundario").getValue().toString()));

                        // Mostramos el contenido de las demarcaciones del jugador que se está mostrando.
                        txtDemarcacion1.setTextColor(Color.parseColor(dataSnapshot.child("ColorSecundario").getValue().toString()));
                        txtDemarcacion2.setTextColor(Color.parseColor(dataSnapshot.child("ColorSecundario").getValue().toString()));
                        txtDemarcacion3.setTextColor(Color.parseColor(dataSnapshot.child("ColorSecundario").getValue().toString()));
                        txtDemarcacion4.setTextColor(Color.parseColor(dataSnapshot.child("ColorSecundario").getValue().toString()));

                        // Definimos el color de las líneas de separación de las demarcaciones.
                        view1demarc.setBackgroundColor(Color.parseColor(dataSnapshot.child("ColorPrimario2").getValue().toString()));
                        view2demarc.setBackgroundColor(Color.parseColor(dataSnapshot.child("ColorPrimario2").getValue().toString()));
                        view3demarc.setBackgroundColor(Color.parseColor(dataSnapshot.child("ColorPrimario2").getValue().toString()));
                        view4demarc.setBackgroundColor(Color.parseColor(dataSnapshot.child("ColorPrimario2").getValue().toString()));
                        view5demarc.setBackgroundColor(Color.parseColor(dataSnapshot.child("ColorPrimario2").getValue().toString()));

                        // Definimos los colores de las pestañas de la vista del InfoJugador.
                        navigBar.setBackgroundColor(Color.parseColor(dataSnapshot.child("ColorPrimario2").getValue().toString()));
                        navigBar.createDefaultTabView(context, dataSnapshot.child("ColorSecundario").getValue().toString());
                        navigBar.setSelectedIndicatorColors(Color.parseColor(dataSnapshot.child("ColorSecundario").getValue().toString()));
                        navigBar.setViewPager(viewPager, dataSnapshot.child("ColorSecundario").getValue().toString());

                        // Definimos el color de texto de la información del jugador.
                        txtFechaNacimiento.setTextColor(Color.parseColor(dataSnapshot.child("ColorSecundario").getValue().toString()));
                        txtLugarNacimiento.setTextColor(Color.parseColor(dataSnapshot.child("ColorSecundario").getValue().toString()));
                        txtAltura.setTextColor(Color.parseColor(dataSnapshot.child("ColorSecundario").getValue().toString()));
                        txtPie.setTextColor(Color.parseColor(dataSnapshot.child("ColorSecundario").getValue().toString()));

                        // Definimos los colores de los iconos de las flechas de navegación de la vista.
                        arrowLeft.setColorFilter(Color.parseColor(dataSnapshot.child("ColorSecundario").getValue().toString()));
                        arrowRight.setColorFilter(Color.parseColor(dataSnapshot.child("ColorSecundario").getValue().toString()));

                        // Creamos la referencia a la base de datos de la tabla posiciones.
                        final DatabaseReference dbPosiciones = FirebaseDatabase.getInstance().getReference().child("posicion");

                        // Lanzamos la consulta que devolverá las posiciones asociadas al id de un jugador particular.
                        dbPosiciones.orderByChild("Posicion_idJugador").equalTo(dataSnapshot.child("idJugador").getValue().toString())
                                .addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                        // Recogemos la abreviatura de la posición del jugador.
                                        String posicionJugador = dataSnapshot.child("AbrevPosicion").getValue().toString();

                                        // Lanzamos el método que devolverá los datos con las posiciones de cada jugador.
                                        cargarPosiciones.setDemarcacionJugador(txtDemarcacion1, txtDemarcacion2, txtDemarcacion3,
                                                txtDemarcacion4, posicionJugador);
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
}
