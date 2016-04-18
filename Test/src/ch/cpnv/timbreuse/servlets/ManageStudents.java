package ch.cpnv.timbreuse.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;

import ch.cpnv.timbreuse.beans.Student;


public class ManageStudents extends HttpServlet {
	public static final String VIEW = "/WEB-INF/manageStudents.jsp";
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Student researchStudent = new Student();
		researchStudent.setName("Amrani");
		researchStudent.setDiff(3);
		
		request.setAttribute("researchStudent", researchStudent);
		
        this.getServletContext().getRequestDispatcher(VIEW).forward(request, response);
    }
}