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

@WebServlet(urlPatterns = "/files/*", initParams = @WebInitParam(name="path", value="/files/"))
public class Download extends HttpServlet {
	public static final int BUFFER_SIZE = 10240; // 10ko

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

		//Lecture du paramètre 'path' passé à  la servlet via la déclaration dans le web.xml
		String path = this.getServletConfig().getInitParameter("path");
		//Récupération du chemin du fichier demandé au sein de l'url de la requête
		String requiredFile = request.getPathInfo();

		//Vérifie qu'un fichier a bien été fourni
		if (requiredFile == null) {
			//Si non, alors on envoie une erreur 404
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		//Décode le nom de fichier récupéré, au cas où il contient des caractères spéciaux ou espaces
		requiredFile = URLDecoder.decode(requiredFile, "UTF-8");
		//Prépare l'objet file
		File file = new File(path, requiredFile);
		//Vörifie que le fichier existe
		if (!file.exists()) {
			// Si non, alors on envoie une erreur 404
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		//Récupère le type du fichier
		String type = getServletContext().getMimeType(file.getName());

		//Si le type de fichier est inconnu, alors on initialise un type par défaut
		if (type == null) {
			type = "application/octet-stream";
		}

		//Initialise la réponse HTTP
		response.reset(); //efface l'intégralité du contenu de la réponse
		response.setBufferSize(BUFFER_SIZE); //Obligatoire après un reset
		response.setContentType(type);
		//création de l'en-tête http
		response.setHeader("Content-Length", String.valueOf(file.length())); 
		response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

		//Prépare les flux 
		BufferedInputStream input = null;
		BufferedOutputStream output = null;
		try {
			//Ouvre les flux 
			input = new BufferedInputStream(new FileInputStream(file), BUFFER_SIZE);
			output = new BufferedOutputStream(response.getOutputStream(), BUFFER_SIZE);

			//Lit le fichier et écrit son contenu dans la réponse HTTP
			byte[] buff = new byte[BUFFER_SIZE];
			int longueur;
			while ((longueur = input.read(buff) ) > 0) {
				output.write(buff, 0, longueur);
			}
		} finally {
			output.close();
			input.close();
		}
	}
}