package ch.cpnv.timbreuse.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.cpnv.timbreuse.automation.EndOfDay;
import ch.cpnv.timbreuse.dao.DAOFactory;
import ch.cpnv.timbreuse.dao.DAOHolyday;
import ch.cpnv.timbreuse.dao.DAOLog;
import ch.cpnv.timbreuse.dao.DAOStudent;

public class EndDay extends HttpServlet {
	private DAOStudent daoStudent;
	private DAOLog daoLog;
	private DAOHolyday daoHolyday;
	EndOfDay eod;

	public void init() {
		this.daoStudent = ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoStudent();
		this.daoLog 	= ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoLog();
		this.daoHolyday = ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoHolyday();
		eod = new EndOfDay(daoStudent, daoLog, daoHolyday);
		eod.init();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	}
}
