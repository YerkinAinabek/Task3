package com.example.WebApplication.Servlets;

import com.example.WebApplication.DAO.DaoPlayers;
import com.example.WebApplication.DAO.DaoPlayersTestData;
import com.example.WebApplication.DAO.PlayersDao;
import com.example.WebApplication.Models.Players;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import static com.example.WebApplication.DAO.DaoPlayersTestData.*;
import static org.mockito.Mockito.*;


class PlayersServletTest {
    private static final String EMPTY_STRING = "";
    private static PlayersDao dao;
    private static Players player;
    private static HttpServletRequest request;
    private static HttpServletResponse response;
    private static RequestDispatcher dispatcher;
    private static PlayersServlet controller;
    private static Players updatedPlayer;

    // Collection to store HttpServletRequest keys/values attributes
    private static final Map<String, Object> attributes = new HashMap<>();


    @BeforeAll
    public static void setup() throws SQLException {
        //setup DaoPlayers
        dao = mock(DaoPlayers.class);
        when(dao.findAll()).thenReturn(players);
        when(dao.find(PLAYER_1_ID)).thenReturn(player1);
        when(dao.delete(anyInt())).thenReturn(true);
        when(dao.find(4)).thenReturn(getNew());
        //setup servlets objects
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
        //setup controller
        controller = new PlayersServlet();

        //https://stackoverflow.com/a/30726246/16047333
        // Mock setAttribute
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                String key = invocation.getArgument(0);
                Object value = invocation.getArgument(1);
                attributes.put(key, value);
                return null;
            }
        }).when(request).setAttribute(Mockito.anyString(), Mockito.any());

        // Mock getAttribute
        Mockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = invocation.getArgument(0);
                return attributes.get(key);
            }
        }).when(request).getAttribute(Mockito.anyString());
    }

    @AfterEach
    void resetAttributeStorage() {
        attributes.clear();
    }

    @Test
    void doGet_ActionDefault_ListOfPlayers() throws Exception {
        //reset only the number of invocations
        clearInvocations(dispatcher);
        //define mocks behavior for our case
        when(request.getServletPath()).thenReturn("");
        when(request.getRequestDispatcher("WEB-INF/jsp/players-list.jsp")).thenReturn(dispatcher);
        controller.doGet(request, response);

        //check request attributes
        Assertions.assertEquals(players, request.getAttribute("listPlayers"));
        // verify called methods
        verify(dispatcher, times(1)).forward(request, response);
    }

    @Test
    void doGet_Action_Delete() throws Exception {
        clearInvocations(response);

        when(request.getServletPath()).thenReturn("/delete");
        when(request.getParameter("id")).thenReturn(String.valueOf(PLAYER_1_ID));
        when(request.getRequestDispatcher("WEB-INF/jsp/players-list.jsp")).thenReturn(dispatcher);

        controller.doGet(request, response);

        verify(response, times(1)).sendRedirect("list");
    }

    @Test
    void doGet_Action_NewForm() throws Exception {
        clearInvocations(dispatcher);

        when(request.getServletPath()).thenReturn("/new");
        when(request.getRequestDispatcher("WEB-INF/jsp/players-form.jsp")).thenReturn(dispatcher);

        controller.doGet(request, response);

        verify(dispatcher, times(1)).forward(request, response);
    }

    @Test
    void doGet_Action_Update() throws Exception {
        clearInvocations(dispatcher);

        when(request.getServletPath()).thenReturn("/update");
        when(request.getParameter("id")).thenReturn(String.valueOf(PLAYER_1_ID));
        when(request.getRequestDispatcher("WEB-INF/jsp/players-form.jsp")).thenReturn(dispatcher);

        controller.doGet(request, response);

        Assertions.assertEquals(player1, request.getAttribute("player"));
        verify(dispatcher, times(1)).forward(request, response);
    }
}