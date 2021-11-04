package com.example.WebApplication.Servlets;

import com.example.WebApplication.DAO.DaoPlayers;
import com.example.WebApplication.Models.Players;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import static com.example.WebApplication.util.DataSourceFactory.getConnection;

@WebServlet("/")
public class PlayersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final DaoPlayers daoPlayers;

    public PlayersServlet() {
        daoPlayers = new DaoPlayers(getConnection());
    }
    public PlayersServlet(DaoPlayers dao) {
        daoPlayers = dao;
    }


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String action = req.getServletPath();

        try {
            switch (action) {
                case "/new":
                    showNewForm(req, resp);
                    break;
                case "/insert":
                    insertPlayers(req, resp);
                    break;
                case "/delete":
                    deletePlayers(req, resp);
                    break;
                case "/edit":
                    showEditForm(req, resp);
                    break;
                case "/update":
                    updatePlayers(req, resp);
                    break;
                default:
                    listPlayers(req, resp);
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void updatePlayers(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException{

        int id = Integer.parseInt(req.getParameter("id"));
        String nickname = req.getParameter("nickname");
        String biography = req.getParameter("biography");
        int lvl = Integer.parseInt(req.getParameter("lvl"));

        Players player = new Players(id, nickname, lvl, biography);
        daoPlayers.update(player);
        resp.sendRedirect("list");
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException {

        int id = Integer.parseInt(req.getParameter("id"));
        Players existingPlayer = daoPlayers.find(id);
        RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/jsp/players-form.jsp");
        req.setAttribute("player", existingPlayer);
        dispatcher.forward(req, resp);
    }

    private void deletePlayers(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException {

        int id = Integer.parseInt(req.getParameter("id"));
        daoPlayers.delete(id);
        resp.sendRedirect("list");
    }

    private void insertPlayers(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException {

        String nickname = req.getParameter("nickname");
        int lvl = Integer.parseInt(req.getParameter("lvl"));
        String biography = req.getParameter("biography");

        Players newPlayer = new Players(nickname, lvl, biography);
        daoPlayers.insert(newPlayer);
        resp.sendRedirect("list");
    }

    private void showNewForm (HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException {

        RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/jsp/players-form.jsp");
        dispatcher.forward(req, resp);
    }

    private void listPlayers (HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException {

        List < Players > listPlayers = daoPlayers.findAll();
        req.setAttribute("listPlayers", listPlayers);
        RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/jsp/players-list.jsp");
        dispatcher.forward(req, resp);
    }


}
