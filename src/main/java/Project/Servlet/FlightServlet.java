package Project.Servlet;

import Project.DAO.FlightDAO;
import Project.Model.Flight;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.DateFormatter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/flight")
public class FlightServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String type = req.getParameter("type");

        if (type.equals("delete")){
            FlightDAO.removeById(id);
            resp.sendRedirect("flightlist.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        int idToAdd = 0;

        if(id != null){
            idToAdd = Integer.parseInt(id);
        }
        String departureDate = req.getParameter("departureDate");
        String arrivalDate = req.getParameter("arrivalDate");
        String from = req.getParameter("from");
        String to = req.getParameter("to");
        double price = Double.parseDouble(req.getParameter("price"));
        int seats = Integer.parseInt(req.getParameter("seats"));
        String type = req.getParameter("type");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");

            try {
                Date departure = simpleDateFormat.parse(departureDate);
                Date arrival = simpleDateFormat.parse(arrivalDate);

                Flight flight = new Flight(idToAdd, departure, arrival, from, to, price, seats);

                if (type.equals("add")) {
                    FlightDAO.addFlight(flight);
                    resp.sendRedirect("flightlist.jsp");
                } else if (type.equals("update")) {
                    FlightDAO.updateFlight(flight);
                    resp.sendRedirect("flightlist.jsp");

                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
    }
}
