package jmm.futbol.futuros.cracks.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import jmm.futbol.futuros.cracks.tools.GlideApp;
import jmm.futbol.futuros.cracks.tools.rutaImgScoutland;
import jmm.futbol.futuros.cracks.viewPlayerReadingMode;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * Clase que contiene el adaptador con los métodos que cargarán los datos en el layout asociado.
 */
public class RecTeamAdapter extends RecyclerView.ViewHolder {

    private View mView;

    // Constructor de la clase.
    public RecTeamAdapter(View itemView) {
        super(itemView);
        mView = itemView;
    }

    // Método utilizado para definir el nombre principal de cada tarjeta, su fuente y su color de letra.
    public void setNombreItem(String nombreequipo, Typeface fuentePopup, String colorTxtEquipo)
    {
        TextView tvEquipo = (TextView) mView.findViewById(jmm.futbol.futuros.cracks.R.id.nombreLiga);
        tvEquipo.setText(nombreequipo);
        tvEquipo.setTypeface(fuentePopup);
        tvEquipo.setTextColor(Color.parseColor(colorTxtEquipo));
    }

    // Método utilizado para determinar el número de jugadores asociados con esa tarjeta, y donde además
    // se define el tipo de fuente y el color de la letra de su Textview asociado.
    public void setNumJugadores(String numJugadores, String colorNumJugadores, Typeface fuenteNumJugadores)
    {
        TextView tvNumJugadores = (TextView) mView.findViewById(jmm.futbol.futuros.cracks.R.id.numJugadores);
        tvNumJugadores.setText(numJugadores);
        tvNumJugadores.setTextColor(Color.parseColor(colorNumJugadores));
        tvNumJugadores.setTypeface(fuenteNumJugadores);
    }

    // Método donde se define el color de fondo de cada tarjeta de cada jugador, y su enlace asociado al clickar sobre ella.
    public void setColorHolderPlayer(String colorHolderPrimario, String colorHolderSecundario, final Context context,
                                     final int envio, final int idJugador, final int idHolder,
                                     final int sizescreen, final float densityscreen, final int numJugadoresDB )
    {
        RelativeLayout rlCardPlayer = (RelativeLayout) mView.findViewById(jmm.futbol.futuros.cracks.R.id.card_player);

        // Se aplica el degradado con sus dos colores correspondientes al fondo de la tarjeta.
        int colors[] = { Color.parseColor(colorHolderPrimario), Color.parseColor(colorHolderSecundario)};
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
        rlCardPlayer.setBackground(gradientDrawable);

        // Al clickar sobre el enlace se pasarán los correspondientes al id de la pestaña,
        // el id del primer jugador asoaciado con esa etiqueta, el tamaño y densidad de la pantalla y el número
        // de jugadores asociados a esa etiqueta.
        rlCardPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentViewPlayer = new Intent(context, viewPlayerReadingMode.class);
                intentViewPlayer.putExtra("envio", 1);
                intentViewPlayer.putExtra("idJugador", idJugador);
                intentViewPlayer.putExtra("sizeScreen", sizescreen);
                intentViewPlayer.putExtra("densityScreen", densityscreen);
                intentViewPlayer.putExtra("numJugadoresDB", numJugadoresDB);
                context.startActivity(intentViewPlayer);
            }
        });
    }

    // Método donde se define el color de fondo de cada tarjeta de cada equipo, liga o país, y su enlace asociado al clickar sobre ella.
    public void setColorHolder(String colorHolderPrimario, String colorHolderSecundario, final Context context,
                               final int envio, final ArrayList<Integer> posArrayIdJugador, final ArrayList<Integer> posArrayIdHolder,
                               final int idHolder, final int resto, final int sizescreen, final float densityscreen, final int numJugadoresDB)
    {
        RelativeLayout rlCardTeam = (RelativeLayout) mView.findViewById(jmm.futbol.futuros.cracks.R.id.card_league);

        // Se aplica el degradado con sus dos colores correspondientes al fondo de la tarjeta.
        int colors[] = { Color.parseColor(colorHolderPrimario), Color.parseColor(colorHolderSecundario)};
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
        rlCardTeam.setBackground(gradientDrawable);

        rlCardTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<Integer> posArrayIdJugadorByIdHolder = new ArrayList<>();

                // Bucle donde se cargarán los id de cada jugador asociados a la etiqueta pulsada.
                for (int i = 0; i < posArrayIdHolder.size(); i++)
                {
                    if ((idHolder - resto) == posArrayIdHolder.get(i))
                    {
                        posArrayIdJugadorByIdHolder.add(posArrayIdJugador.get(i));
                    }
                }

                // Dependiendo de si la etiqueta corresponde a un equipo, liga o país, se cargarán el id de la etiqueta,
                // el id de la etiqueta, el id del primer jugador asociado a esa etiqueta, la posición del array que
                // contiene los id de los jugadores asociados a cada etiqueta, el tamaño y densidad de pantalla y los
                // números de jugadores asociados a la etiqueta pulsada.
                switch (envio)
                {
                    case 2:
                        Intent intentViewLeague = new Intent(context, viewPlayerReadingMode.class);
                        intentViewLeague.putExtra("envio", 2);
                        intentViewLeague.putExtra("idLiga", idHolder - resto);
                        intentViewLeague.putExtra("idJugador", posArrayIdJugadorByIdHolder);
                        intentViewLeague.putExtra("posArrayJugLiga", 0);
                        intentViewLeague.putExtra("sizeScreen", sizescreen);
                        intentViewLeague.putExtra("densityScreen", densityscreen);
                        intentViewLeague.putExtra("numJugLigaDB", numJugadoresDB);
                        context.startActivity(intentViewLeague);
                        break;

                    case 3:
                        Intent intentViewTeam = new Intent(context, viewPlayerReadingMode.class);
                        intentViewTeam.putExtra("envio", 3);
                        intentViewTeam.putExtra("idEquipo", idHolder - resto);
                        intentViewTeam.putExtra("idJugador", posArrayIdJugadorByIdHolder);
                        intentViewTeam.putExtra("posArrayJugEquipo", 0);
                        intentViewTeam.putExtra("sizeScreen", sizescreen);
                        intentViewTeam.putExtra("densityScreen", densityscreen);
                        intentViewTeam.putExtra("numJugEquipoDB", numJugadoresDB);
                        context.startActivity(intentViewTeam);
                        break;

                    case 4:

                        ArrayList<Integer> posArrayIdJugadorByIdHolder2 = new ArrayList<>();

                        // Bucle donde se cargarán los id de cada jugador asociado a un país determinado.
                        // En el caso de los países sigue un proceso algo diferente al de los equipos y ligas.
                        for (int i = 0; i < posArrayIdHolder.size(); i++)
                        {
                            if ((idHolder - resto) == posArrayIdHolder.get(i))
                            {
                                posArrayIdJugadorByIdHolder2.add(i+2);
                            }
                        }


                        Intent intentViewNac = new Intent(context, viewPlayerReadingMode.class);
                        intentViewNac.putExtra("envio", 4);
                        intentViewNac.putExtra("idNac", idHolder);
                        intentViewNac.putExtra("idJugador", posArrayIdJugadorByIdHolder2);
                        intentViewNac.putExtra("posArrayJugNac", 0);
                        intentViewNac.putExtra("sizeScreen", sizescreen);
                        intentViewNac.putExtra("densityScreen", densityscreen);
                        intentViewNac.putExtra("numJugNacDB", numJugadoresDB);
                        context.startActivity(intentViewNac);
                        break;
                }
            }
        });
    }

    // Método encargado de definir el color del icono de la flecha que hay en cada etiqueta.
    public void setColorArrow(String colorArrow)
    {
        ImageView imgArrowNavig = (ImageView) mView.findViewById(jmm.futbol.futuros.cracks.R.id.arrow_navig);
        imgArrowNavig.setColorFilter(Color.parseColor(colorArrow));
    }

    // Método encargado de definir el padding de las etiquetas de los jugadores.
    public void setPaddingItemPlayer(Context context)
    {
        LinearLayout leagueItem = (LinearLayout) mView.findViewById(jmm.futbol.futuros.cracks.R.id.player_item);
        leagueItem.setPadding((int) context.getResources().getDimension(jmm.futbol.futuros.cracks.R.dimen.padding_item),
                (int) context.getResources().getDimension(jmm.futbol.futuros.cracks.R.dimen.padding_item),
                (int) context.getResources().getDimension(jmm.futbol.futuros.cracks.R.dimen.padding_item),
                (int) context.getResources().getDimension(jmm.futbol.futuros.cracks.R.dimen.padding_item));
    }

    // Método encargado de definir el padding de las etiquetas de los equipos, ligas o países.
    public void setPaddingItem(Context context)
    {
        LinearLayout leagueItem = (LinearLayout) mView.findViewById(jmm.futbol.futuros.cracks.R.id.league_item);
        leagueItem.setPadding((int) context.getResources().getDimension(jmm.futbol.futuros.cracks.R.dimen.padding_item),
                (int) context.getResources().getDimension(jmm.futbol.futuros.cracks.R.dimen.padding_item),
                (int) context.getResources().getDimension(jmm.futbol.futuros.cracks.R.dimen.padding_item),
                (int) context.getResources().getDimension(jmm.futbol.futuros.cracks.R.dimen.padding_item));
    }

    // Método que vuelve invisible una etiqueta en caso de que esté repetida. Ocurre sólo en el caso
    // de los paises.
    public void setInvisibilityHolder()
    {
        LinearLayout leagueItem = (LinearLayout) mView.findViewById(jmm.futbol.futuros.cracks.R.id.league_item);
        RelativeLayout rlCardTeam = (RelativeLayout) mView.findViewById(jmm.futbol.futuros.cracks.R.id.card_league);
        leagueItem.setPadding(0, 0, 0, 0);
        leagueItem.setVisibility(View.GONE);
        rlCardTeam.setVisibility(View.GONE);
    }

    // Método que recoge los enlaces de Firebase de cada imagen a cargar en su método correspondiente.
    public void setLinkImage(Context context, int sizescreen, float densityscreen, String nombreEquipo, int numTab)
    {
        rutaImgScoutland claseRutaImgScoutland = new rutaImgScoutland();
        String strImagen = "";

        switch (numTab)
        {
            case 1:
                strImagen = claseRutaImgScoutland.obtenerRutaImgScoutland(sizescreen, densityscreen, "Perfiles", nombreEquipo);
                break;
            case 2:
                strImagen = claseRutaImgScoutland.obtenerRutaImgScoutland(sizescreen, densityscreen, "Clubes", nombreEquipo);
                break;
            case 3:
                strImagen = claseRutaImgScoutland.obtenerRutaImgScoutland(sizescreen, densityscreen, "Ligas", nombreEquipo);
                break;
            case 4:
                strImagen = claseRutaImgScoutland.obtenerRutaImgScoutland(sizescreen, densityscreen, "Paises", nombreEquipo);
                break;
        }

        // Dependiendo de si el fragmento es el de los jugadores o es otro diferente, el Imageview se asociará
        // con un id u otro.
        ImageView ivImgHolder = null;
        if (numTab == 1)
        {
            ivImgHolder = (ImageView) mView.findViewById(jmm.futbol.futuros.cracks.R.id.imagenJugador);
        }
        else
        {
            ivImgHolder = (ImageView) mView.findViewById(jmm.futbol.futuros.cracks.R.id.imagenLiga);
        }

        // Creamos una referencia al repositorio de Firebase (StorageReference) y le cargamos el enlace
        // de cada imagen a mostrar.
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference imgReference = storageReference.child(strImagen);

        GlideApp.with(context)
                .load(imgReference)
                .error(jmm.futbol.futuros.cracks.R.drawable.icon_team)
                .into(ivImgHolder);
    }

    // Método encargado de mostrar el texto de cada jugador, con su fuente y color correspondiente.
    public void setNombreJugador(String nombreJugador, String apellidoJugador, Typeface fuenteTexto, String colorTexto)
    {
        TextView tvNombreJugador = (TextView) mView.findViewById(jmm.futbol.futuros.cracks.R.id.nombreJugador);
        TextView tvApellidoJugador = (TextView) mView.findViewById(jmm.futbol.futuros.cracks.R.id.apellidoJugador);

        tvNombreJugador.setText(nombreJugador);
        tvApellidoJugador.setText(apellidoJugador);

        tvNombreJugador.setTypeface(fuenteTexto);
        tvApellidoJugador.setTypeface(fuenteTexto);

        tvNombreJugador.setTextColor(Color.parseColor(colorTexto));
        tvApellidoJugador.setTextColor(Color.parseColor(colorTexto));
    }

    // Método encargado de mostrar en cada etiqueta el resto de información de cada jugador, con su
    // fuente y color de ltra correspondiente.
    public void setInfoJugador(String edadJugador, String posJugador, String equipoJugador,
                               Typeface fuenteTexto, String colorTexto)
    {
        TextView tvEdadPosJugador = (TextView) mView.findViewById(jmm.futbol.futuros.cracks.R.id.edadNacJugador);
        TextView tvEquipoJugador = (TextView) mView.findViewById(jmm.futbol.futuros.cracks.R.id.equipoJugador);

        tvEdadPosJugador.setText(edadJugador + " años, " + posJugador);
        tvEquipoJugador.setText(equipoJugador);

        tvEdadPosJugador.setTypeface(fuenteTexto);
        tvEquipoJugador.setTypeface(fuenteTexto);

        tvEdadPosJugador.setTextColor(Color.parseColor(colorTexto));
        tvEquipoJugador.setTextColor(Color.parseColor(colorTexto));
    }

    public void setCargarBanner()
    {
        LinearLayout linearPopupBanner = (LinearLayout) mView.findViewById(jmm.futbol.futuros.cracks.R.id.linear_popup_banner);
        linearPopupBanner.setVisibility(View.VISIBLE);

        AdView adViewBannerPopup = (AdView) mView.findViewById(jmm.futbol.futuros.cracks.R.id.popup_banner);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adViewBannerPopup.loadAd(adRequest);
    }
}
