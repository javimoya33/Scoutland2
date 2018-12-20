package jmm.futbol.futuros.cracks.Modelo;

/**
 * Clase contenedora de los métodos para la carga de la información de las posiciones de un jugador.
 */
public class Posicion {

    /**
     * Atributos de la tabla Posicion
     */
    private int idPosicion;
    private String AbrevPosicion;
    private String NomPosicion;
    private int PosPrincipal;

    public Posicion(int idposicion, String abrevPosicion, String nomPosicion, int posPrincipal)
    {
        this.idPosicion = idposicion;
        this.AbrevPosicion = abrevPosicion;
        this.NomPosicion = nomPosicion;
        this.PosPrincipal = posPrincipal;
    }

    public int getIdPosicion() { return idPosicion; }

    public String getAbrevPosicion() {return AbrevPosicion; }

    public String getNomPosicion() { return NomPosicion; }

    public int getPosPrincipal() { return PosPrincipal; }
}
