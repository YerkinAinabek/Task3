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
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "players", value = "/")
public class PlayersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DaoPlayers playersDao = DaoPlayers.getInstance();
    private static final Logger LOGGER = Logger.getLogger(PlayersServlet.class.getName());

    @Override
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
                case "/update":
                    updatePlayers(req, resp);
                break;
                default:
                    listPlayers(req, resp);
                    break;
            }
        }catch (SQLException e) {
            //For simplicity just Log the exceptions
            LOGGER.log(Level.SEVERE, "SQL ERROR", e);
        }

    }

    private void updatePlayers(HttpServletRequest req, HttpServletResponse resp)
        throws SQLException, IOException, ServletException{
        int player_id = Integer.parseInt(req.getParameter("player_id"));
        String nickname = req.getParameter("nickname");
        String biography = req.getParameter("biography");
        int player_lvl = Integer.parseInt(req.getParameter("player_lvl"));

        Players player = new Players(player_id, nickname, player_lvl, biography);
        playersDao.update(player);
        resp.sendRedirect("list");
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp)
        throws SQLException, IOException, ServletException {
        String player_id = (req.getParameter("player_id"));
        Optional<Players> existingPlayer = playersDao.find(player_id);
        RequestDispatcher dispatcher = req.getRequestDispatcher("players-form.jsp");
        existingPlayer.ifPresent(s->req.setAttribute("players", s));
        dispatcher.forward(req, resp);
    }

    private void deletePlayers(HttpServletRequest req, HttpServletResponse resp)
        throws SQLException, IOException, ServletException {
        int player_id = Integer.parseInt(req.getParameter("player_id"));

        Players player = new Players(player_id);
        playersDao.delete(player);
        resp.sendRedirect("list");
    }

    private void insertPlayers(HttpServletRequest req, HttpServletResponse resp)
        throws SQLException, IOException, ServletException {
        String nickname = req.getParameter("nickname");
        int player_lvl = Integer.parseInt(req.getParameter("player_lvl"));
        String biography = req.getParameter("biography");

        Players newPlayer = new Players(nickname, player_lvl, biography);
        playersDao.save(newPlayer);
        resp.sendRedirect("list");
    }

    private void showNewForm (HttpServletRequest req, HttpServletResponse resp)
        throws SQLException, IOException, ServletException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("players-form.jsp");
        dispatcher.forward(req, resp);
    }

    private void listPlayers (HttpServletRequest req, HttpServletResponse resp)
        throws SQLException, IOException, ServletException {
        List<Players> listPlayers = playersDao.findAll();
        req.setAttribute("listPlayers", listPlayers);
        RequestDispatcher dispatcher = req.getRequestDispatcher("players-list.jsp");
        dispatcher.forward(req, resp);
    }


}
