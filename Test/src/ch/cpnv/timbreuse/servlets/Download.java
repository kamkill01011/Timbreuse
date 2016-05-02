package ch.cpnv.timbreuse.servlets;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns ="/files/*", initParams = @WebInitParam(name="path", value="/files/"))
public class Download extends HttpServlet {
    public static final int BUFFER_SIZE = 10240; // 10ko

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
         * Lecture du paramètre 'chemin' passé à  la servlet via la declaration
         * dans le web.xml
         */
        String path = this.getServletConfig().getInitParameter("path");

        /*
         * Récupération du chemin du fichier demande au sein de l'URL de la
         * requête
         */
        String requestedFile = request.getPathInfo();

        /* Vérifie qu'un fichier a bien été fourni */
        if (requestedFile == null) {
            /*
             * Si non, alors on envoie une erreur 404, qui signifie que la
             * ressource demandée n'existe pas
             */
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        /*
         * Décode le nom de fichier récupéré, susceptible de contenir des
         * espaces et autres caractères spéciaux, et prépare l'objet File
         */
        requestedFile = URLDecoder.decode(requestedFile, "UTF-8");
        File file = new File(path, requestedFile);

        /* Vérifie que le fichier existe bien */
        if (!file.exists()) {
            /*
             * Si non, alors on envoie une erreur 404, qui signifie que la
             * ressource demandée n'existe pas
             */
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        /* Récupère le type du fichier */
        String type = getServletContext().getMimeType(file.getName());

        /*
         * Si le type de fichier est inconnu, alors on initialise un type par
         * défaut
         */
        if (type == null) {
            type = "application/octet-stream";
        }

        /* Initialise la réponse HTTP */
        response.reset();
        response.setBufferSize(BUFFER_SIZE);
        response.setContentType( type );
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

        /* Prépare les flux */
        BufferedInputStream input = null;
        BufferedOutputStream output = null;
        try {
            /* Ouvre les flux */
            input = new BufferedInputStream(new FileInputStream(file), BUFFER_SIZE);
            output = new BufferedOutputStream(response.getOutputStream(), BUFFER_SIZE);

            /* Lit le fichier et écrit son contenu dans la réponse HTTP */
            byte[] buffer = new byte[BUFFER_SIZE];
            int i;
            while ( ( i = input.read( buffer ) ) > 0 ) {
                output.write( buffer, 0, i );
            }
        } finally {
            output.close();
            input.close();
        }
    }
}