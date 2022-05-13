package servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/FormHandler")
public class FormHandler extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("result", Arrays.stream(request.getParameter("data").split("\\s?,\\s?"))
                    .map(e -> Double.parseDouble(e)).sorted().collect(Collectors.toList()));
            getServletContext().getRequestDispatcher("/WEB-INF/views/result.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("result", request.getParameter("data"));
            request.setAttribute("error", "Only comma separated numbers are allowed");
            getServletContext().getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
