package jmm.futbol.futuros.cracks.tools;

/**
 * Clase en la que se determina la ruta de la imagen a cargar en función del tamaño y la densidad de pantalla
 * del dispositivo donde se ejecuta la aplicación.
 */
public class rutaImgScoutland {

    public String obtenerRutaImgScoutland(int sizeScreen, float densityscreen, String carpeta, String nomImg)
    {
        String rutaImg = "";
        String nuevorutaImg = "";

        /**
         * Tamaños de pantalla
         * --------------------
         * #4 - xlarge
         * #3 - large
         * #2 - normal
         *
         * * Densidades de pantalla
         * --------------------
         * #3.0 - xxhdpi
         * #2.0 - xhdpi
         * #1.5 - hdpi
         * #1.3 - tvdpi
         * #1.5 - mdpi
         */
        if (sizeScreen == 4)
        {
            if (densityscreen >= 3.0)
            {
                rutaImg = "xlarge/xxhdpi/" + carpeta + "/" + nomImg + ".png";
            }
            else if (densityscreen >= 2.0)
            {
                rutaImg = "xlarge/xhdpi/" + carpeta + "/" + nomImg + ".png";
            }
            else if (densityscreen >= 1.5)
            {
                rutaImg = "xlarge/hdpi/" + carpeta + "/" + nomImg + ".png";
            }
            else if (densityscreen >= 1.3)
            {
                rutaImg = "xlarge/tvdpi/" + carpeta + "/" + nomImg + ".png";
            }
            else
            {
                rutaImg = "http://192.168.1.108/Scoutland/xlarge/mdpi/" + carpeta + "/" + nomImg + ".png";
            }
        }
        else if (sizeScreen == 3)
        {
            if (densityscreen >= 3.0)
            {
                rutaImg = "large/xxhdpi/" + carpeta + "/" + nomImg + ".png";
            }
            else if (densityscreen >= 2.0)
            {
                rutaImg = "large/xhdpi/" + carpeta + "/" + nomImg + ".png";
            }
            else if (densityscreen >= 1.5)
            {
                rutaImg = "large/hdpi/" + carpeta + "/" + nomImg + ".png";
            }
            else if (densityscreen >= 1.3)
            {
                rutaImg = "large/tvdpi/" + carpeta + "/" + nomImg + ".png";
            }
            else
            {
                rutaImg = "large/mdpi/" + carpeta + "/" + nomImg + ".png";
            }
        }
        else
        {
            if (densityscreen >= 3.0)
            {
                rutaImg = "normal/xxhdpi/" + carpeta + "/" + nomImg + ".png";
            }
            else if (densityscreen >= 2.0)
            {
                rutaImg = "normal/xhdpi/" + carpeta + "/" + nomImg + ".png";
            }
            else if (densityscreen >= 1.5)
            {
                rutaImg = "normal/hdpi/" + carpeta + "/" + nomImg + ".png";
            }
            else
            {
                rutaImg = "normal/mdpi/" + carpeta + "/" + nomImg + ".png";
            }
        }

        // Reemplazamos la ruta de una imagen por la ruta de un gif cuando sea necesario
        if (carpeta == "GifAnalisis")
        {
            nuevorutaImg = rutaImg.replaceAll(".png", ".gif");
        }
        else
        {
            nuevorutaImg = rutaImg;
        }

        return nuevorutaImg;
    }
}
