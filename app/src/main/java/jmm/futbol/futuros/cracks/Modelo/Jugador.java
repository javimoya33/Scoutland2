package jmm.futbol.futuros.cracks.Modelo;

//import com.google.gson.Gson;

/**
 * Clase contenedora de los métodos para la carga de la información de un jugador.
 */
public class Jugador {

    /**
     * Atributos de la tabla Jugador
     */
    private String idJugador;
    private String Nombre;
    private String Apellido;
    private String Equipo;
    private String Dorsal;
    private String Fecha_nac;
    private String Edad;
    private String Lugar_nac;
    private String Pais_nac;
    private String idPaisNac;
    private String Altura;
    private String Posicion;
    private String Abrev_Posicion;
    private String Pie;
    private String Fecha_debut;
    private String Debut_seleccion;
    private String Imagen;
    private String Biografia;
    private String Como_juega;
    private String ColorPrimario1;
    private String ColorPrimario2;
    private String ColorSecundario;
    private int TotalJugadores;
    private int TotalJugadoresByEquipo;
    private int TotalJugadoresByLiga;
    private int TotalJugadoresByNac;

    public Jugador(){}

    public Jugador(String idjugador, String nombre, String apellido, String equipo, String dorsal,
                   String fecha_nac, String edad, String lugar_nac, String pais_nac, String idpaisnac, String altura,
                   String posicion, String abrevPosicion, String pie, String fecha_debut,
                   String debut_seleccion, String imagen, String biografia, String como_juega,
                   String colorPrimario1, String colorPrimario2, String colorSecundario,
                   int totalJugadores, int totalJugadoresByEquipo, int totalJugadoresByLiga, int totalJugadoresByNac)
    {
        this.idJugador = idjugador;
        this.Nombre = nombre;
        this.Apellido = apellido;
        this.Equipo = equipo;
        this.Dorsal = dorsal;
        this.Fecha_nac = fecha_nac;
        this.Edad = edad;
        this.Lugar_nac = lugar_nac;
        this.Pais_nac = pais_nac;
        this.idPaisNac = idpaisnac;
        this.Altura = altura;
        this.Posicion = posicion;
        this.Abrev_Posicion = abrevPosicion;
        this.Pie = pie;
        this.Fecha_debut = fecha_debut;
        this.Debut_seleccion = debut_seleccion;
        this.Imagen = imagen;
        this.Biografia = biografia;
        this.Como_juega = como_juega;
        this.ColorPrimario1 = colorPrimario1;
        this.ColorPrimario2 = colorPrimario2;
        this.ColorSecundario = colorSecundario;
        this.TotalJugadores = totalJugadores;
        this.TotalJugadoresByEquipo = totalJugadoresByEquipo;
        this.TotalJugadoresByLiga = totalJugadoresByLiga;
        this.TotalJugadoresByNac = totalJugadoresByNac;
    }

    public String getIdJugador() { return idJugador; }

    public String getNombre() { return Nombre; }

    public String getApellido() { return Apellido; }

    public String getEquipo() { return Equipo; }

    public String getDorsal() { return Dorsal; }

    public String getFecha_nac() { return Fecha_nac; }

    public String getEdad() { return Edad; }

    public String getLugar_nac() { return Lugar_nac; }

    public String getPais_nac() { return Pais_nac; }

    public String getIdPaisNac() { return idPaisNac; }

    public String getAltura() { return Altura; }

    public String getPosicion() { return Posicion; }

    public String getAbrevPosicion() { return Abrev_Posicion; }

    public String getPie() { return Pie; }

    public String getFecha_debut() { return Fecha_debut; }

    public String getDebut_seleccion() { return Debut_seleccion; }

    public String getImagen() { return Imagen; }

    public String getBiografia() { return Biografia; }

    public String getComo_juega() { return Como_juega; }

    public String getColorPrimario1() { return ColorPrimario1; }

    public String getColorPrimario2() { return ColorPrimario2; }

    public String getColorSecundario() { return ColorSecundario; }

    public int getTotalJugadores() { return TotalJugadores; }

    public int getTotalJugadoresByEquipo() { return TotalJugadoresByEquipo; }

    public int getTotalJugadoresByLiga() { return TotalJugadoresByLiga; }

    public int getTotalJugadoresByNac() { return TotalJugadoresByNac; }
}
