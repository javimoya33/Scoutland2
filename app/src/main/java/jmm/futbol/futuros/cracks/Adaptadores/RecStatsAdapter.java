package jmm.futbol.futuros.cracks.Adaptadores;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.StrictMode;
import android.support.v7.widget.RecyclerView;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bluejamesbond.text.DocumentView;
import com.bluejamesbond.text.style.TextAlignment;
import com.bluejamesbond.text.util.ArticleBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import jmm.futbol.futuros.cracks.R;
import jmm.futbol.futuros.cracks.tools.GlideApp;
import jmm.futbol.futuros.cracks.tools.rutaImgScoutland;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by Acer on 17/04/2018.
 */
public class RecStatsAdapter extends RecyclerView.ViewHolder{

    private View mView;
    private String strImagen;
    DocumentView[] dvStats = new DocumentView[100];

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();

    public RecStatsAdapter(View itemView) {
        super(itemView);
        mView = itemView;
    }

    public void setStatsParrafo(Context context, int sizeScreen, float densityScreen, String nombreIconStats,
                                String nombreTituloStat, String textoParrafo, int position)
    {
        RelativeLayout relativeContent = (RelativeLayout) mView.findViewById(R.id.relativeContent);
        ImageView ivIconStat = (ImageView) mView.findViewById(R.id.imgIconStatTxt);
        TextView txtTitle = (TextView) mView.findViewById(R.id.txtTitle);

        Typeface fuente2 = Typeface.createFromAsset(context.getAssets(), "fonts/Sumptuous_Light.otf");
        Typeface fuente1bold = Typeface.createFromAsset(context.getAssets(), "fonts/Champagne_&_Limousines_Bold.ttf");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        rutaImgScoutland claseRutaImgScoutland = new rutaImgScoutland();
        strImagen = claseRutaImgScoutland.obtenerRutaImgScoutland(sizeScreen, densityScreen, "IconsStats",
                "icon_txt_" + nombreIconStats);

        StorageReference imgStatReference = storageReference.child(strImagen);

        GlideApp.with(context)
                .load(imgStatReference)
                .error(R.drawable.icon_league)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .dontAnimate()
                .into(ivIconStat);

        txtTitle.setText(nombreTituloStat);
        txtTitle.setTypeface(fuente1bold);
        txtTitle.setPaintFlags(txtTitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        final StyleSpan bss = new StyleSpan(Typeface.NORMAL); // Span to make text bold

        ArticleBuilder articleBuilder = new ArticleBuilder();
        articleBuilder.append(textoParrafo,
                true, new RelativeSizeSpan(1f), bss);

        dvStats[position] = new DocumentView(context, DocumentView.FORMATTED_TEXT);
        dvStats[position].setText(articleBuilder);
        dvStats[position].getDocumentLayoutParams().setTextAlignment(TextAlignment.JUSTIFIED);
        dvStats[position].getDocumentLayoutParams().setHyphenated(true);
        dvStats[position].getDocumentLayoutParams().setTextTypeface(fuente2);
        dvStats[position].getDocumentLayoutParams().setTextSize(TypedValue.COMPLEX_UNIT_SP, context.getResources().getDimensionPixelSize(R.dimen.size_letter_text_scroll_bioplaystats));
        dvStats[position].getDocumentLayoutParams().setInsetPaddingLeft(context.getResources().getDimensionPixelSize(R.dimen.size_letter_text_scroll_bioplaystats));
        dvStats[position].getDocumentLayoutParams().setInsetPaddingRight(context.getResources().getDimensionPixelSize(R.dimen.size_letter_text_scroll_bioplaystats));
        dvStats[position].destroyCache();
        relativeContent.addView(dvStats[position]);
    }
}
