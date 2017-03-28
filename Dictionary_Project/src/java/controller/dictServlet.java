package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import connection.DbConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author chetan
 */
public class dictServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String item = "";
        String key;
        String result = "";

        try {

            Connection conec = DbConnection.connect();
            key = request.getParameter("find");

            String sqlString = "SELECT * FROM entries WHERE word=?";
            ResultSet rs;
            try (PreparedStatement ps = conec.prepareStatement(sqlString)) {
                ps.setString(1, key);
                rs = ps.executeQuery();
                
                String eachitem;
                while (rs.next()) {
                    eachitem = "{\"word\":\"" + rs.getString("word")
                            + "\",\"type\":\"" + rs.getString("wordtype")
                            + "\",\"definition\":\"" + rs.getString("definition") + "\"}";

                    
                    item = item + eachitem + ",";
                }
                result = "{\"result\": [" + item.substring(0, item.length() - 1) + "]}";
            }

        } catch (SQLException ex) {
            Logger.getLogger(dictServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        response.setContentType("text/json;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            try {
                JSONObject json = (JSONObject) new JSONParser().parse(result);
                out.println(json.toJSONString());
            } catch (Exception ex) {
                //
            }
        }
        DbConnection.disconnect();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "AJAX Dictionary";
    }// </editor-fold>

}
