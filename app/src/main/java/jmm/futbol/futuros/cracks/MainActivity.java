package jmm.futbol.futuros.cracks;

import android.animation.Animator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewAnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import jmm.futbol.futuros.cracks.FragmentsPopup.ListLeagueFragment;
import jmm.futbol.futuros.cracks.FragmentsPopup.ListNacFragment;
import jmm.futbol.futuros.cracks.FragmentsPopup.ListPlayerFragment;
import jmm.futbol.futuros.cracks.FragmentsPopup.ListTeamFragment;
import jmm.futbol.futuros.cracks.tools.GlideApp;
import jmm.futbol.futuros.cracks.tools.rutaImgScoutland;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

/*** Clase principal de la aplicación ***/
public class MainActivity extends AppCompatActivity implements OnClickListener{

    private ImageButton img_logo_cuadrado;
    private ImageButton img_logo_hor;

    private ImageButton butMenu;
    private LinearLayout layoutMain;
    private RelativeLayout layoutContent;
    private ArrayList<ImageView> show_viewPlayer = new ArrayList<>();
    private LinearLayout linearRow5;

    private TabLayout tabs;
    private RelativeLayout layoutButtons;
    private Boolean isOpen = false;
    private ViewPager viewPager;
    private int[] tabIcons = {
            jmm.futbol.futuros.cracks.R.drawable.icon_player,
            jmm.futbol.futuros.cracks.R.drawable.icon_league,
            jmm.futbol.futuros.cracks.R.drawable.icon_team,
            jmm.futbol.futuros.cracks.R.drawable.icon_flag,
            jmm.futbol.futuros.cracks.R.drawable.icon_close
    };
    private ImageButton closePopup;

    private int sizeScreen;
    private float densityScreen;
    private int widthScreen;
    private String strImagen;

    private AdView adViewBanner1;
    private AdView adViewBanner2;
    private AdView adViewBannerInferior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(jmm.futbol.futuros.cracks.R.layout.activity_main);

        //Inicializamos MobileAds para cargar anuncios en la app.
        MobileAds.initialize(this, "ca-app-pub-6925377246649300~5335543545");

        adViewBanner1 = findViewById(jmm.futbol.futuros.cracks.R.id.main_banner1);
        adViewBanner2 = findViewById(jmm.futbol.futuros.cracks.R.id.main_banner2);
        adViewBannerInferior = findViewById(jmm.futbol.futuros.cracks.R.id.main_banner_inferior);

        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();

        adViewBanner1.loadAd(adRequest);
        adViewBanner2.loadAd(adRequest);
        adViewBannerInferior.loadAd(adRequest);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();

        // Variables que recoge el tipo de tamaño de la pantalla del móvil (small, normal, large o xlarge)
        // el tipo densidad, el ancho, la densityDpi, dpHeight y el dpWidth de la pantalla. Fundamentales para determinar las
        // dimensiones que tendrán cada elemento de la pantalla.
        sizeScreen = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        densityScreen =  metrics.density;
        widthScreen = metrics.widthPixels;

        // Asignamos cada imagen a cada ImageView de la actividad principal de la app
        show_viewPlayer.add(0, (ImageView) findViewById(jmm.futbol.futuros.cracks.R.id.imgRow1Col1));
        show_viewPlayer.get(0).setOnClickListener(this);
        show_viewPlayer.add(1, (ImageView) findViewById(jmm.futbol.futuros.cracks.R.id.imgRow2Col1));
        show_viewPlayer.get(1).setOnClickListener(this);
        show_viewPlayer.add(2, (ImageView) findViewById(jmm.futbol.futuros.cracks.R.id.imgRow2Col2));
        show_viewPlayer.get(2).setOnClickListener(this);
        show_viewPlayer.add(3, (ImageView) findViewById(jmm.futbol.futuros.cracks.R.id.imgRow3Col1));
        show_viewPlayer.get(3).setOnClickListener(this);
        show_viewPlayer.add(4, (ImageView) findViewById(jmm.futbol.futuros.cracks.R.id.imgRow4Col1));
        show_viewPlayer.get(4).setOnClickListener(this);
        show_viewPlayer.add(5, (ImageView) findViewById(jmm.futbol.futuros.cracks.R.id.imgRow4Col2));
        show_viewPlayer.get(5).setOnClickListener(this);
        show_viewPlayer.add(6, (ImageView) findViewById(jmm.futbol.futuros.cracks.R.id.imgRow5Col1));
        show_viewPlayer.get(6).setOnClickListener(this);
        show_viewPlayer.add(7, (ImageView) findViewById(jmm.futbol.futuros.cracks.R.id.imgRow5Col2));
        show_viewPlayer.get(7).setOnClickListener(this);
        show_viewPlayer.add(8, (ImageView) findViewById(jmm.futbol.futuros.cracks.R.id.imgRow5Col3));
        show_viewPlayer.get(8).setOnClickListener(this);

        // Pedimos permisos para habilitar que la clase 'claseRutaImgScoutland' pueda recoger imágenes de sus repositorios
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Arrays de las terminaciones del nombre de cada imagen de los jugadores de la vista principal
        String[] nomPlayersMainView = {"ousmanedembele", "delealli", "hectorbellerin", "gianluigidonnarumma",
                "marcoasensio", "leroysane", "yerrymina", "gonçaloguedes", "kylianmbappe"};

        // Bucle en el que se asignará cada ruta de imagen a su ImageView correspondiente. Las imágenes
        // se cargan mediante la herramienta Glide
        rutaImgScoutland claseRutaImgScoutland = new rutaImgScoutland();
        for (int i = 0; i < show_viewPlayer.size(); i++)
        {
            strImagen = claseRutaImgScoutland.obtenerRutaImgScoutland(sizeScreen, densityScreen, "ImgMainView", "main_" + nomPlayersMainView[i]);
            StorageReference imgReference = storageReference.child(strImagen);

            GlideApp.with(this)
                    .load(imgReference)
                    .placeholder(jmm.futbol.futuros.cracks.R.drawable.loading2)
                    .error(jmm.futbol.futuros.cracks.R.drawable.icon_player)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .dontTransform()
                    .into(show_viewPlayer.get(i));
        }

        // Contenedor principal de la app, contenedor con las imágenes y contenedor del menu popup.
        layoutMain = (LinearLayout) findViewById(jmm.futbol.futuros.cracks.R.id.linearMain);
        layoutContent = (RelativeLayout) findViewById(jmm.futbol.futuros.cracks.R.id.relativeContent);
        layoutButtons = (RelativeLayout) findViewById(jmm.futbol.futuros.cracks.R.id.relativePopup);

        // Botón del toolbar que hace aparecer o desaperecer de la pantalla al menu popup.
        butMenu = (ImageButton) findViewById(jmm.futbol.futuros.cracks.R.id.but_menu);
        butMenu.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {

                viewMenu();
            }

        });

        // Contenedor de la información de las diferentes pestañas del menú popup.
        viewPager = (ViewPager) findViewById(jmm.futbol.futuros.cracks.R.id.pager);
        setupViewPager(viewPager);

        // Pestañas del menú popup al que se cargan sus correspondientes iconos.
        tabs = (TabLayout) findViewById(jmm.futbol.futuros.cracks.R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        setupTabIcons();

        // Botón del logo de la app situado en el toolbar que hará desaparecer al menú popup y
        // hará volver al ususario al menú principal.
        img_logo_cuadrado = (ImageButton) findViewById(jmm.futbol.futuros.cracks.R.id.img_logo_cuadrado);
        img_logo_cuadrado.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                int x = layoutContent.getRight();
                int y = layoutContent.getBottom();

                int startRadius = Math.max(layoutContent.getWidth(), layoutContent.getHeight());
                int endRadius = Math.max(0, 0);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                {
                    butMenu.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), jmm.futbol.futuros.cracks.R.color.colorAccent, null)));
                    butMenu.setImageResource(jmm.futbol.futuros.cracks.R.drawable.icon_menu4);

                    Animator anim = ViewAnimationUtils.createCircularReveal(layoutButtons, x, y, startRadius, endRadius);
                    anim.addListener(new Animator.AnimatorListener(){

                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {

                            layoutButtons.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    anim.start();
                }
                layoutButtons.setVisibility(View.GONE);
                isOpen = false;
            }
        });

        // Botón del logo de la app situado en el toolbar que hará desaparecer al menú popup y
        // hará volver al ususario al menú principal.
        img_logo_hor = (ImageButton) findViewById(jmm.futbol.futuros.cracks.R.id.img_logo_hor);
        img_logo_hor.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                int x = layoutContent.getRight();
                int y = layoutContent.getBottom();

                int startRadius = Math.max(layoutContent.getWidth(), layoutContent.getHeight());
                int endRadius = Math.max(0, 0);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                {
                    butMenu.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), jmm.futbol.futuros.cracks.R.color.colorAccent, null)));
                    butMenu.setImageResource(jmm.futbol.futuros.cracks.R.drawable.icon_menu4);

                    Animator anim = ViewAnimationUtils.createCircularReveal(layoutButtons, x, y, startRadius, endRadius);
                    anim.addListener(new Animator.AnimatorListener(){

                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {

                            layoutButtons.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    anim.start();
                }
                layoutButtons.setVisibility(View.GONE);
                isOpen = false;
            }
        });

        // Botón de la pestaña del menú opopup que hara desaparecer el menú popup cuando se pulse.
        closePopup = (ImageButton) findViewById(jmm.futbol.futuros.cracks.R.id.close_popup);
        closePopup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                int x = layoutContent.getRight();
                int y = layoutContent.getBottom();

                int startRadius = Math.max(layoutContent.getWidth(), layoutContent.getHeight());
                int endRadius = Math.max(0, 0);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                {
                    butMenu.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), jmm.futbol.futuros.cracks.R.color.colorAccent, null)));
                    butMenu.setImageResource(jmm.futbol.futuros.cracks.R.drawable.icon_menu4);

                    Animator anim = ViewAnimationUtils.createCircularReveal(layoutButtons, x, y, startRadius, endRadius);
                    anim.addListener(new Animator.AnimatorListener(){

                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {

                            layoutButtons.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    anim.start();
                }
                layoutButtons.setVisibility(View.GONE);
                isOpen = false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adViewBanner1 != null) {
            adViewBanner1.resume();
        }
        if (adViewBanner2 != null) {
            adViewBanner2.resume();
        }
        if (adViewBannerInferior != null) {
            adViewBannerInferior.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (adViewBanner1 != null) {
            adViewBanner1.pause();
        }
        if (adViewBanner2 != null) {
            adViewBanner2.pause();
        }
        if (adViewBannerInferior != null) {
            adViewBannerInferior.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adViewBanner1 != null) {
            adViewBanner1.destroy();
        }
        if (adViewBanner2 != null) {
            adViewBanner2.destroy();
        }
        if (adViewBannerInferior != null) {
            adViewBannerInferior.destroy();
        }
    }

    // Instrucciones que harán aparecer o desaparecer al menú popup en caso de que esté abierto o no.
    private void viewMenu()
    {
        if (!isOpen)
        {
            // Variables que dan valor a la franja derecha y superior de la pantalla.
            int x = layoutContent.getRight();
            int y = layoutContent.getTop();

            // Variables que servirán para iniciar un efecto fluido de aparición y desaprición del menú popup.
            int startRadius = 0;
            int endRadius = (int) Math.hypot(layoutMain.getWidth(), layoutMain.getHeight());

            // El contenedor del menú popup se hará visible en caso de que no haber estado abierto previamente.
            layoutButtons.setVisibility(View.VISIBLE);

            // El aspecto del botón que abré el menu cambiará.
            butMenu.setImageResource(jmm.futbol.futuros.cracks.R.drawable.icon_menu4_selected);

            // La animación a la hora de aparecer o desaparecer el menu popup se iniciará en caso de que
            // el sdk del móvil tenga la versión mínima que lo permita.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                Animator anim = ViewAnimationUtils.createCircularReveal(layoutButtons, x, y, startRadius, endRadius);
                anim.start();
            }

            // Variable que determinará si el menu opopup está abierto o no.
            isOpen = true;
        }
        else
        {
            int x = layoutContent.getRight();
            int y = layoutContent.getTop();

            int startRadius = Math.max(layoutContent.getWidth(), layoutContent.getHeight());
            int endRadius = Math.max(0, 0);

            //butMenu.setBackgroundTintList(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null)));
            butMenu.setImageResource(jmm.futbol.futuros.cracks.R.drawable.icon_menu4);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {

                Animator anim = ViewAnimationUtils.createCircularReveal(layoutButtons, x, y, startRadius, endRadius);
                anim.addListener(new Animator.AnimatorListener(){

                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        layoutButtons.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

                anim.start();
            }
            else
            {
                layoutButtons.setVisibility(View.GONE);
            }
            isOpen = false;
        }
    }

    @Override
    public void onClick(View v) {

        // Switch encargado de asignar las rutas que seguirá la aplicación dependiendo del personaje
        // del menú principal que se pulse.
        switch (v.getId())
        {
            case jmm.futbol.futuros.cracks.R.id.imgRow1Col1:
                Intent intentViewPlayer = new Intent(getApplicationContext(), viewPlayerReadingMode.class);
                intentViewPlayer.putExtra("envio", 1);
                intentViewPlayer.putExtra("idJugador", 2);
                intentViewPlayer.putExtra("sizeScreen", sizeScreen);
                intentViewPlayer.putExtra("densityScreen", densityScreen);
                intentViewPlayer.putExtra("widthScreen", widthScreen);
                intentViewPlayer.putExtra("numJugadoresDB", 12);
                this.startActivity(intentViewPlayer);
                break;
            case jmm.futbol.futuros.cracks.R.id.imgRow2Col1:
                Intent intentViewPlayer2 = new Intent(getApplicationContext(), viewPlayerReadingMode.class);
                intentViewPlayer2.putExtra("envio", 1);
                intentViewPlayer2.putExtra("idJugador", 4);
                intentViewPlayer2.putExtra("sizeScreen", sizeScreen);
                intentViewPlayer2.putExtra("densityScreen", densityScreen);
                intentViewPlayer2.putExtra("widthScreen", widthScreen);
                intentViewPlayer2.putExtra("numJugadoresDB", 12);
                this.startActivity(intentViewPlayer2);
                break;
            case jmm.futbol.futuros.cracks.R.id.imgRow2Col2:
                Intent intentViewPlayer3 = new Intent(getApplicationContext(), viewPlayerReadingMode.class);
                intentViewPlayer3.putExtra("envio", 1);
                intentViewPlayer3.putExtra("idJugador", 6);
                intentViewPlayer3.putExtra("sizeScreen", sizeScreen);
                intentViewPlayer3.putExtra("densityScreen", densityScreen);
                intentViewPlayer3.putExtra("widthScreen", widthScreen);
                intentViewPlayer3.putExtra("numJugadoresDB", 12);
                this.startActivity(intentViewPlayer3);
                break;
            case jmm.futbol.futuros.cracks.R.id.imgRow3Col1:
                Intent intentViewPlayer4 = new Intent(getApplicationContext(), viewPlayerReadingMode.class);
                intentViewPlayer4.putExtra("envio", 1);
                intentViewPlayer4.putExtra("idJugador", 9);
                intentViewPlayer4.putExtra("sizeScreen", sizeScreen);
                intentViewPlayer4.putExtra("densityScreen", densityScreen);
                intentViewPlayer4.putExtra("widthScreen", widthScreen);
                intentViewPlayer4.putExtra("numJugadoresDB", 12);
                this.startActivity(intentViewPlayer4);
                break;
            case jmm.futbol.futuros.cracks.R.id.imgRow4Col1:
                Intent intentViewPlayer5 = new Intent(getApplicationContext(), viewPlayerReadingMode.class);
                intentViewPlayer5.putExtra("envio", 1);
                intentViewPlayer5.putExtra("idJugador", 8);
                intentViewPlayer5.putExtra("sizeScreen", sizeScreen);
                intentViewPlayer5.putExtra("densityScreen", densityScreen);
                intentViewPlayer5.putExtra("widthScreen", widthScreen);
                intentViewPlayer5.putExtra("numJugadoresDB", 12);
                this.startActivity(intentViewPlayer5);
                break;
            case jmm.futbol.futuros.cracks.R.id.imgRow4Col2:
                Intent intentViewPlayer6 = new Intent(getApplicationContext(), viewPlayerReadingMode.class);
                intentViewPlayer6.putExtra("envio", 1);
                intentViewPlayer6.putExtra("idJugador", 3);
                intentViewPlayer6.putExtra("sizeScreen", sizeScreen);
                intentViewPlayer6.putExtra("densityScreen", densityScreen);
                intentViewPlayer6.putExtra("widthScreen", widthScreen);
                intentViewPlayer6.putExtra("numJugadoresDB", 12);
                this.startActivity(intentViewPlayer6);
                break;
            case jmm.futbol.futuros.cracks.R.id.imgRow5Col1:
                Toast.makeText(getApplicationContext(), "Yerry Mina, muy pronto en nuestra app.",
                        Toast.LENGTH_SHORT).show();
                break;
            case jmm.futbol.futuros.cracks.R.id.imgRow5Col2:
                Toast.makeText(getApplicationContext(), "Gonçalo Guedes, muy pronto en nuestra app.",
                        Toast.LENGTH_SHORT).show();
                break;
            case jmm.futbol.futuros.cracks.R.id.imgRow5Col3:
                Toast.makeText(getApplicationContext(), "Kylian Mbappé, muy pronto en nuestra app.",
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }

    // Método encargado de asignar los iconos a cada pestaña correspondiente.
    private void setupTabIcons()
    {
        tabs.getTabAt(0).setIcon(tabIcons[0]).setCustomView(jmm.futbol.futuros.cracks.R.layout.tab_popup_main);
        tabs.getTabAt(1).setIcon(tabIcons[1]).setCustomView(jmm.futbol.futuros.cracks.R.layout.tab_popup_main);
        tabs.getTabAt(2).setIcon(tabIcons[2]).setCustomView(jmm.futbol.futuros.cracks.R.layout.tab_popup_main);
        tabs.getTabAt(3).setIcon(tabIcons[3]).setCustomView(jmm.futbol.futuros.cracks.R.layout.tab_popup_main);
    }

    // Método encargado de crear los fragmentos del contenedor del menu popup.
    private void setupViewPager(ViewPager viewPager)
    {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(ListPlayerFragment.newInstance(1, sizeScreen, densityScreen), "");
        adapter.addFragment(ListLeagueFragment.newInstance(2, sizeScreen, densityScreen), "");
        adapter.addFragment(ListTeamFragment.newInstance(3, sizeScreen, densityScreen), "");
        adapter.addFragment(ListNacFragment.newInstance(4, sizeScreen, densityScreen), "");
        viewPager.setAdapter(adapter);
    }

    // Adaptador encargado de crear los fragmentos con sus títulos correspondientes.
    public class SectionsPageAdapter extends FragmentPagerAdapter
    {
        private final List<Fragment> fragments = new ArrayList<>();
        private final List<String> fragmentsTitles = new ArrayList<>();

        public SectionsPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return fragments.get(position);
        }

        @Override
        public int getCount() {

            return fragments.size();
        }

        public void addFragment(Fragment fragment, String title)
        {
            fragments.add(fragment);
            fragmentsTitles.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return fragmentsTitles.get(position);
        }
    }
}
