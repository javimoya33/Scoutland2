package jmm.futbol.futuros.cracks.Modelo;

/**
 * Clase contenedora de los métodos de la carga de la información de un párrafo de un análisis.
 */
public class ParrafosAnalisis {

    /**
     * Atributos de la tabla ParrafosAnalisis
     */
    private String idParrafo;
    private String textoParrafo;
    private String valoresAnalisis;
    private String tipoParrafo;
    private String titulo;
    private String iconoStat;
    private String autorFoto;
    private String enlaceAutor;
    private String parrafo_idJugador;

    public ParrafosAnalisis(){}

    public ParrafosAnalisis(String idparrafo, String textoparrafo, String valoresanalisis, String titulo, String iconostat, String tipoparrafo,
                            String autorfoto, String enlaceautor, String parrafo_idjugador)
    {
        this.idParrafo = idparrafo;
        this.textoParrafo = textoparrafo;
        this.valoresAnalisis = valoresanalisis;
        this.titulo = titulo;
        this.iconoStat = iconostat;
        this.tipoParrafo = tipoparrafo;
        this.autorFoto = autorfoto;
        this.enlaceAutor = enlaceautor;
        this.parrafo_idJugador = parrafo_idjugador;
    }

    public String getIdParrafo() { return idParrafo; }

    public String getTextoParrafo() { return textoParrafo; }

    public String getValoresAnalisis() { return valoresAnalisis; }

    public String getTitulo() { return titulo; }

    public String getIconoStat() { return iconoStat; }

    public String getTipoParrafo() { return tipoParrafo; }

    public String getAutorFoto() { return autorFoto; }

    public String getEnlaceAutor() { return enlaceAutor; }

    public String getParrafo_idJugador() { return parrafo_idJugador; }
}
