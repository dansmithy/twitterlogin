package com.github.dansmithy.twitterlogin;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Debug class used for running jetty within Eclipse.
 */
public class TwitterLoginStart {

    private static final int DEFAULT_PORT = 8086;

    /**
     * Main function, starts the jetty server. Puts data into
     * the db.
     * @param args  contextPath, war
     * @throws Exception
     */
    public static void main(String[] args) throws Exception  {

        Server server = new Server(getPort());
        WebAppContext context = new WebAppContext();
        context.setContextPath("/");
        server.setHandler(context);
        context.addServlet(VerifyUserServlet.class, "/loginDetails/*");
        context.addServlet(TwitterCallback.class, "/twitterCallback/*");
        context.addServlet(LogoutServlet.class, "/j_spring_security_logout");
        context.addServlet(SimpleServlet.class, "/simple/*");
        context.setWar("src/main/webapp");
        server.start();
        server.join();
    }

    private static Integer getPort() {
        String portVar = System.getenv("PORT");
        if (portVar != null) {
            return Integer.valueOf(portVar);
        } else {
            return DEFAULT_PORT;
        }
    }


}
