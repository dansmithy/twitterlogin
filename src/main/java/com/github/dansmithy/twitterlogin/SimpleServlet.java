package com.github.dansmithy.twitterlogin;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

public class SimpleServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Method is: [%s]", request.getMethod())).append("\n");
        builder.append(String.format("Full path is: [%s]", request.getRequestURI())).append("\n");
        builder.append(String.format("Query string is: [%s]", request.getQueryString())).append("\n");
        builder.append(String.format("Content is: [%s]", IOUtils.toString(request.getInputStream()))).append("\n");
        builder.append(String.format("Headers are:")).append("\n");
        @SuppressWarnings("unchecked")
        Enumeration<String> headerNamesEnumeration = request.getHeaderNames();
        while (headerNamesEnumeration.hasMoreElements()) {
            String headerName = headerNamesEnumeration.nextElement();
            builder.append(String.format("Key: [%s].", headerName)).append("  ");
            String delim = "";
            builder.append(String.format("Value(s): ["));
            @SuppressWarnings("unchecked")
            Enumeration<String> headerValuesEnumeration = request.getHeaders(headerName);
            while (headerValuesEnumeration.hasMoreElements()) {
                String value = headerValuesEnumeration.nextElement();
                builder.append(delim).append(value);
                delim = ", ";
            }
            builder.append("]\n");
        }
        response.getWriter().write(builder.toString());
        System.out.println(builder.toString());
    }

}
