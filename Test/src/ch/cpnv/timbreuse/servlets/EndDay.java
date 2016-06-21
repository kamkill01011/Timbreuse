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
import ch.cpnv.timbreuse.gui.CustomConsole;

/**
 * Servlet qui initialise les automatisme pour les fins de journées
 * @author Mathieu.JEE Kamil.AMRANI
 *
 */
public class EndDay extends HttpServlet {
	private DAOStudent daoStudent;
	private DAOLog daoLog;
	private DAOHolyday daoHolyday;
	EndOfDay eod;

	@Override
	public void init() {
		CustomConsole cc = new CustomConsole();
		this.daoStudent = ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoStudent();
		this.daoLog 	= ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoLog();
		this.daoHolyday = ((DAOFactory) getServletContext().getAttribute("daofactory")).getDaoHolyday();
		cc.init();//strart a console for System.out and System.err
		eod = new EndOfDay(daoStudent, daoLog, daoHolyday);
		eod.init();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	}
}
