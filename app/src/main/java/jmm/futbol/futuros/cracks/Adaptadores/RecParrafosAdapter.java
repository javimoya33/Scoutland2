package jmm.futbol.futuros.cracks.Adaptadores;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bluejamesbond.text.DocumentView;
import com.bluejamesbond.text.style.TextAlignment;
import com.bluejamesbond.text.util.ArticleBuilder;

import jmm.futbol.futuros.cracks.tools.GlideApp;
import jmm.futbol.futuros.cracks.tools.rutaImgScoutland;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.regex.Pattern;

import pl.droidsonroids.gif.GifImageView;

/**
 * Created by Acer on 11/04/2018.
 */
public class RecParrafosAdapter extends RecyclerView.ViewHolder {

    private View mView;
    private DocumentView[] dvContent = new DocumentView[20];

    private int numTextoParrafo = 0;

    public RecParrafosAdapter(View itemView) {
        super(itemView);
        mView = itemView;
    }

    public void setHolderVisibility()
    {
        RelativeLayout linearContentBioPlay = (RelativeLayout) mView.findViewById(jmm.futbol.futuros.cracks.R.id.linearContentBioPlay);
        linearContentBioPlay.setVisibility(View.VISIBLE);
    }

    public void setEspacioInicial(int position)
    {
        View espacioInicial = (View) mView.findViewById(jmm.futbol.futuros.cracks.R.id.espacioInicial);
        RecyclerView recyclerBioPlay = (RecyclerView) mView.findViewById(jmm.futbol.futuros.cracks.R.id.recycler_content);

        if (position == 0)
        {
            espacioInicial.setVisibility(View.VISIBLE);
        }
    }

    public void setContenidoParrafo(int position, int tipoTexto, String textoValores, String textoParrafo, Context context)
    {
        HorizontalScrollView scrollValoresJugadores = (HorizontalScrollView) mView.findViewById(jmm.futbol.futuros.cracks.R.id.scrollValoresJugadores);
        LinearLayout llFortJugador = (LinearLayout) mView.findViewById(jmm.futbol.futuros.cracks.R.id.linearFortJugadores);
        LinearLayout llDebJugador = (LinearLayout) mView.findViewById(jmm.futbol.futuros.cracks.R.id.linearDebJugadores);
        TextView tvFortalezas = (TextView) mView.findViewById(jmm.futbol.futuros.cracks.R.id.tvFortalezas);
        TextView tvDebilidades = (TextView) mView.findViewById(jmm.futbol.futuros.cracks.R.id.tvDebilidades);
        RelativeLayout relativeContent = (RelativeLayout) mView.findViewById(jmm.futbol.futuros.cracks.R.id.relativeContent);
        ImageView imgGifPlayer = (ImageView) mView.findViewById(jmm.futbol.futuros.cracks.R.id.imgGifPlayer);

        Typeface fuente2 = Typeface.createFromAsset(context.getAssets(), "fonts/Sumptuous_Light.otf");

        System.out.println("Raices -> " + tipoTexto + " - " + position);

        if ((tipoTexto == 1) && (position == 0))
        {
            imgGifPlayer.setVisibility(View.GONE);
            scrollValoresJugadores.setVisibility(View.VISIBLE);

            tvFortalezas.setTypeface(fuente2, Typeface.BOLD);
            tvFortalezas.setPaintFlags(tvFortalezas.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            tvDebilidades.setTypeface(fuente2, Typeface.BOLD);
            tvDebilidades.setPaintFlags(tvDebilidades.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

            String separador = Pattern.quote("\n");
            String[] valoresJugador = textoValores.split ("\\. ");

            for (int i = 0; i < valoresJugador.length; i++)
            {
                if (i < 4)
                {

                    LinearLayout linearValorJugador = new LinearLayout(context);
                    linearValorJugador.setOrientation(LinearLayout.HORIZONTAL);

                    LinearLayout.LayoutParams lpValorJugador = new LinearLayout.LayoutParams(
                            context.getResources().getInteger(jmm.futbol.futuros.cracks.R.integer.size_icon_fortdeb),
                            context.getResources().getInteger(jmm.futbol.futuros.cracks.R.integer.size_icon_fortdeb));
                    lpValorJugador.setMargins(0, 0, 10, 0);
                    ImageView ivValorJugador = new ImageView(context);
                    ivValorJugador.setImageResource(jmm.futbol.futuros.cracks.R.drawable.icon_checkok);
                    ivValorJugador.setLayoutParams(lpValorJugador);

                    valoresJugador[i].replace("\n", "");
                    String valorJugadorSinEspacio = valoresJugador[i].replace("\\n", "");

                    TextView tvValoresJugador = new TextView(context);
                    tvValoresJugador.setText(valorJugadorSinEspacio);
                    tvValoresJugador.setTextColor(Color.BLACK);
                    tvValoresJugador.setTypeface(fuente2);
                    tvValoresJugador.setMaxLines(1);
                    tvValoresJugador.setTextSize(context.getResources().getDimensionPixelSize(jmm.futbol.futuros.cracks.R.dimen.size_letter_values_players));

                    linearValorJugador.addView(ivValorJugador);
                    linearValorJugador.addView(tvValoresJugador);
                    llFortJugador.addView(linearValorJugador);
                }
                else
                {
                    LinearLayout linearValorJugador = new LinearLayout(context);
                    linearValorJugador.setOrientation(LinearLayout.HORIZONTAL);

                    LinearLayout.LayoutParams lpValorJugador = new LinearLayout.LayoutParams(
                            context.getResources().getInteger(jmm.futbol.futuros.cracks.R.integer.size_icon_fortdeb),
                            context.getResources().getInteger(jmm.futbol.futuros.cracks.R.integer.size_icon_fortdeb));
                    lpValorJugador.setMargins(0, 0, 15, 0);
                    ImageView ivValorJugador = new ImageView(context);
                    ivValorJugador.setImageResource(jmm.futbol.futuros.cracks.R.drawable.icon_checkno);
                    ivValorJugador.setLayoutParams(lpValorJugador);

                    valoresJugador[i].replace("\n", "");
                    String valorJugadorSinEspacio = valoresJugador[i].replace("\\n", "");

                    TextView tvValoresJugador = new TextView(context);
                    tvValoresJugador.setText(valorJugadorSinEspacio);
                    tvValoresJugador.setTextColor(Color.BLACK);
                    tvValoresJugador.setTypeface(fuente2);
                    tvValoresJugador.setMaxLines(1);
                    tvValoresJugador.setTextSize(context.getResources().getDimensionPixelSize(jmm.futbol.futuros.cracks.R.dimen.size_letter_values_players));

                    linearValorJugador.addView(ivValorJugador);
                    linearValorJugador.addView(tvValoresJugador);
                    llDebJugador.addView(linearValorJugador);
                }
            }
        }

            final StyleSpan bss = new StyleSpan(Typeface.NORMAL);

            ArticleBuilder articleBuilder = new ArticleBuilder();
            articleBuilder.append(textoParrafo, true, new RelativeSizeSpan(1f), bss);

            dvContent[numTextoParrafo] = new DocumentView(context, DocumentView.FORMATTED_TEXT);
            dvContent[numTextoParrafo].setText(articleBuilder);
            dvContent[numTextoParrafo].getDocumentLayoutParams().setTextAlignment(TextAlignment.JUSTIFIED);
            dvContent[numTextoParrafo].getDocumentLayoutParams().setHyphenated(true);
            dvContent[numTextoParrafo].getDocumentLayoutParams().setTextTypeface(fuente2);
            dvContent[numTextoParrafo].getDocumentLayoutParams().setTextSize(TypedValue.COMPLEX_UNIT_SP, context.getResources().getDimension(jmm.futbol.futuros.cracks.R.dimen.size_letter_text_scroll_bioplaystats));
            dvContent[numTextoParrafo].setCacheConfig(DocumentView.CacheConfig.NO_CACHE);
            relativeContent.addView(dvContent[numTextoParrafo]);

        numTextoParrafo += 1;
    }

    public void setLinkImgParrafo(int position, int numPestana, int sizeScreen, float densityScreen, final Context context, String nombreJugador, int numImgParrafo)
    {

        final ImageView ivImgParrafo = (ImageView) mView.findViewById(jmm.futbol.futuros.cracks.R.id.imgGifPlayer);
        final ImageView clickImgPlayer = (ImageView) mView.findViewById(jmm.futbol.futuros.cracks.R.id.clickImgGifPlayer);
        final GifImageView ivGifParrafo = (GifImageView) mView.findViewById(jmm.futbol.futuros.cracks.R.id.gifPlayerr);

        rutaImgScoutland claseRutaImgScoutland = new rutaImgScoutland();
        String strImagen = "";
        String strGif = "";

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();

        switch (numPestana)
        {
            case 0:
                if (position == 4)
                {
                    ivImgParrafo.setVisibility(View.GONE);
                }
                else
                {
                    ivGifParrafo.setVisibility(View.GONE);

                    strImagen = claseRutaImgScoutland.obtenerRutaImgScoutland(sizeScreen, densityScreen, "ImgBiografia",
                            "bio" + nombreJugador + numImgParrafo);

                    StorageReference imgBioReference = storageReference.child(strImagen);

                    GlideApp.with(context)
                            .load(imgBioReference)
                            .error(jmm.futbol.futuros.cracks.R.drawable.icon_landscape)
                            .into(ivImgParrafo);
                }
                break;

            case 1:
                if (position == 4)
                {
                    ivImgParrafo.setVisibility(View.GONE);
                    clickImgPlayer.setVisibility(View.GONE);
                }
                else
                {
                    strImagen = claseRutaImgScoutland.obtenerRutaImgScoutland(sizeScreen, densityScreen, "ImgAnalisis",
                            "ana" + nombreJugador + position);
                    strGif = claseRutaImgScoutland.obtenerRutaImgScoutland(sizeScreen, densityScreen, "GifAnalisis",
                            "gif" + nombreJugador + position);

                    ivImgParrafo.setVisibility(View.VISIBLE);
                    clickImgPlayer.setVisibility(View.VISIBLE);

                    final StorageReference imgPlayReference = storageReference.child(strImagen);

                    GlideApp.with(context)
                            .load(imgPlayReference)
                            .placeholder(jmm.futbol.futuros.cracks.R.drawable.loading2)
                            .error(jmm.futbol.futuros.cracks.R.drawable.icon_landscape)
                            .into(ivImgParrafo);

                    final StorageReference gifPlayReference = storageReference.child(strGif);
                    final String finalStrGif = strGif;
                    ivImgParrafo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            ivImgParrafo.setVisibility(View.GONE);
                            clickImgPlayer.setVisibility(View.GONE);
                            ivGifParrafo.setVisibility(View.VISIBLE);

                            GlideApp.with(context)
                                    .load(gifPlayReference)
                                    .error(jmm.futbol.futuros.cracks.R.drawable.icon_landscape)
                                    .placeholder(jmm.futbol.futuros.cracks.R.drawable.loading2)
                                    .into(ivGifParrafo);
                        }
                    });

                    ivGifParrafo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            ivImgParrafo.setVisibility(View.VISIBLE);
                            clickImgPlayer.setVisibility(View.VISIBLE);
                            ivGifParrafo.setVisibility(View.GONE);

                            GlideApp.with(context)
                                    .load(imgPlayReference)
                                    .error(jmm.futbol.futuros.cracks.R.drawable.icon_landscape)
                                    .into(ivImgParrafo);
                        }
                    });
                    break;
                }
        }

    }

    public void setAutorImg(String strAutorFoto)
    {
        TextView tvAutorFoto = (TextView) mView.findViewById(jmm.futbol.futuros.cracks.R.id.tvAutorFoto);
        tvAutorFoto.setVisibility(View.VISIBLE);
        tvAutorFoto.setText(
                Html.fromHtml("<a href=\"" + strAutorFoto + "\">"
                        + "@" + strAutorFoto + "</a>"));
        tvAutorFoto.setClickable(true);
    }

    public void setCargarBanner()
    {
        LinearLayout linearBioPlayBanner = (LinearLayout) mView.findViewById(jmm.futbol.futuros.cracks.R.id.linear_bioplay_banner);
        AdView adViewBannerBioPlay = (AdView) mView.findViewById(jmm.futbol.futuros.cracks.R.id.bioplay_banner);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adViewBannerBioPlay.loadAd(adRequest);
    }
}
