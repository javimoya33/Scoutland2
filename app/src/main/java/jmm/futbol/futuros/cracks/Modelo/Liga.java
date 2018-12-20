package jmm.futbol.futuros.cracks.Modelo;

/**
 * Clase contenedora de los métodos de la carga de la información de una liga.
 */
public class Liga {

    /**
     * Atributos de la tabla Liga
     */
    private String idLiga;
    private String NombreLiga;
    private String Nacionalidad;
    private String ColorPrimario1;
    private String ColorPrimario2;
    private String ColorSecundario;
    private int NumJugadores;

    public Liga(){}

    public Liga(String idliga, String nombre, String nacionalidad, String colorPrimario1, String colorPrimario2,
                String colorSecundario, int numJugadores)
    {
        this.idLiga = idliga;
        this.NombreLiga = nombre;
        this.Nacionalidad = nacionalidad;
        this.ColorPrimario1 = colorPrimario1;
        this.ColorPrimario2 = colorPrimario2;
        this.ColorSecundario = colorSecundario;
        this.NumJugadores = numJugadores;
    }

    public String getIdLiga() { return idLiga; }

    public String getNombreLiga() { return NombreLiga; }

    public String getNacionalidad() { return Nacionalidad; }

    public String getColorPrimario1() { return ColorPrimario1; }

    public String getColorPrimario2() { return ColorPrimario2; }

    public String getColorSecundario() { return ColorSecundario; }

    public int getNumJugadores() { return NumJugadores; }
}
