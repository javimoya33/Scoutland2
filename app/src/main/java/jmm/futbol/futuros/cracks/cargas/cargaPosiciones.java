package jmm.futbol.futuros.cracks.cargas;

import android.widget.TextView;

/**
 * Clase que contiene la carga de posiciones de cada jugador.
 */
public class cargaPosiciones {

    int posDemarcJugador = 0;

    // Método que carga en su TextView correspondiente el valor de las posiciones que desempeña cada jugador.
    // Hay un máximo de 4 posiciones por jugador.
    public void setDemarcacionJugador(TextView txtDemarc1, TextView txtDemarc2, TextView txtDemarc3,
                                      TextView txtDemarc4, String demarcacionJugador)
    {
        posDemarcJugador += 1;

        switch (posDemarcJugador)
        {
            case 1:
                txtDemarc1.setText(demarcacionJugador);
                break;
            case 2:
                txtDemarc2.setText(demarcacionJugador);
                break;
            case 3:
                txtDemarc3.setText(demarcacionJugador);
                break;
            case 4:
                txtDemarc4.setText(demarcacionJugador);
                break;
        }
    }
}
