package jmm.futbol.futuros.cracks.Modelo;

/**
 * Clase contenedora de los métodos de la carga de la información de las estadísticas de un jugador.
 */
public class Estadistica {

    /**
     * Atributos de la tabla Estadistica
     */
    private int idEstaditica;
    private String Temporada;
    private int Partidos;
    private int Minutos;
    private int TarjAmarillas;
    private int TarjRojas;
    private int Goles;
    private int PosGoles;
    private int Asistencias;
    private int PosAsistencias;
    private float Disparos;
    private int PosDisparos;
    private float Pases;
    private int PosPases;
    private float Regates;
    private int PosRegates;
    private float Robos;
    private int PosRobos;
    private float Faltas;
    private int PosFaltas;
    private float Paradas;
    private int PosParadas;
    private float Centros;
    private int PosCentros;
    private int estadisticas_idJugador;

    public Estadistica(int idestaditica, String temporada, int partidos, int minutos, int tarjAmarillas, int tarjRojas,
                       int goles, int posGoles, int asistencias, int posAsistencias, float disparos, int posDisparos,
                       float pases, int posPases, float regates, int posRegates, float robos, int posRobos, float faltas,
                       int posFaltas, float paradas, int posParadas, float centros, int posCentros, int estadisticas_idjugador)
    {
        this.idEstaditica = idestaditica;
        this.Temporada = temporada;
        this.Partidos = partidos;
        this.Minutos = minutos;
        this.TarjAmarillas = tarjAmarillas;
        this.TarjRojas = tarjRojas;
        this.Goles = goles;
        this.PosGoles = posGoles;
        this.Asistencias = asistencias;
        this.PosAsistencias = posAsistencias;
        this.Disparos = disparos;
        this.PosDisparos = posDisparos;
        this.Pases = pases;
        this.PosPases = posPases;
        this.Regates = regates;
        this.PosRegates = posRegates;
        this.Robos = robos;
        this.PosRobos = posRobos;
        this.Faltas = faltas;
        this.PosFaltas = posFaltas;
        this.Paradas = paradas;
        this.PosParadas = posParadas;
        this.Centros = centros;
        this.PosCentros = posCentros;
        this.estadisticas_idJugador = estadisticas_idjugador;
    }

    public int getIdEstaditica() { return idEstaditica; }

    public String getTemporada() { return Temporada; }

    public int getPartidos() { return Partidos; }

    public int getMinutos() { return Minutos; }

    public int getTarjAmarillas() { return TarjAmarillas; }

    public int getTarjRojas() { return TarjRojas; }

    public int getGoles() { return Goles; }

    public int getPosGoles() { return PosGoles; }

    public int getAsistencias() { return Asistencias; }

    public int getPosAsistencias() { return PosAsistencias; }

    public float getDisparos() { return Disparos; }

    public int getPosDisparos() { return PosDisparos; }

    public float getPases() { return Pases; }

    public int getPosPases() { return PosPases; }

    public float getRegates() { return Regates; }

    public int getPosRegates() { return PosRegates; }

    public float getRobos() { return Robos; }

    public int getPosRobos() { return PosRobos; }

    public float getFaltas() { return Faltas; }

    public int getPosFaltas() { return PosFaltas; }

    public float getParadas() { return Paradas; }

    public int getPosParadas() { return PosParadas; }

    public float getCentros() { return Centros; }

    public int getPosCentros() { return PosCentros; }

    public int getEstadisticas_idJugador() { return estadisticas_idJugador; }
}
