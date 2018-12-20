package jmm.futbol.futuros.cracks.Modelo;

/**
 * Clase contenedora de los métodos de la carga de la información de un equipo.
 */
public class Equipo {



    /**
     * Atributos de la tabla Equipo
     */
    private String idEquipo;
    private String NombreEquipo;
    private String DirDeportivo;
    private String ColorPrimario1;
    private String ColorPrimario2;
    private String ColorSecundario;
    private int TotalJugadores;

    public Equipo(){}

    public Equipo(String idequipo, String nombre, String dirDeportivo, String colorPrimario1, String colorPrimario2,
                  String colorSecundario, int totalJugadores)
    {
        this.idEquipo = idequipo;
        this.NombreEquipo = nombre;
        this.DirDeportivo = dirDeportivo;
        this.ColorPrimario1 = colorPrimario1;
        this.ColorPrimario2 = colorPrimario2;
        this.ColorSecundario = colorSecundario;
        this.TotalJugadores = totalJugadores;
    }

    public String getIdEquipo() { return idEquipo; }

    public String getNombreEquipo() { return NombreEquipo; }

    public String getDirDeportivo() { return DirDeportivo; }

    public String getColorPrimario1() { return ColorPrimario1; }

    public String getColorPrimario2() { return ColorPrimario2; }

    public String getColorSecundario() { return ColorSecundario; }

    public int getTotalJugadores() { return TotalJugadores; }
}
