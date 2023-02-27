package lab;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class WhereAmI
 */
@WebServlet("/")
public class WhereAmI extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WhereAmI() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	PrintWriter out = res.getWriter ();
    	res.setContentType("text/html");
    	out.println("<HTML><HEAD><TITLE>Where am I running?</TITLE></HEAD><BODY BGCOLOR=\"#FFFFEE\">");
    	out.println("<h1>Where Am I Running?</h1>");

    	String name = null;
    	MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

    	ObjectName serverMBean;
    	try {
    		serverMBean = new ObjectName(
    				"WebSphere:feature=kernel,name=ServerInfo");
    		if (mbs.isRegistered(serverMBean)) {
    			name = (String) mbs.getAttribute(serverMBean, "Name");
    			if (name.equals("green"))
    				out.println("<h2><font color = \"green\">The green server</h2>");
    			else if (name.equals("blue"))
    				out.println("<h2><font color = \"blue\">The blue server</h2>");
    			else if (name.equals("red"))
    				out.println("<h2><font color = \"red\">The red server</h2>");
    			else if (name.equals("purple"))
    				out.println("<h2><font color = \"purple\">The purple server</h2>");
    			else
    				out.println("<h2><font color = \"black\">Server name: "+name+"</h2>");

    			String libertyVersion = (String) mbs.getAttribute(serverMBean, "LibertyVersion");	
    			out.println("Liberty Version is: "+ libertyVersion);
    		}else{
    			System.out.println("server mbean doesn't seem to exist");
    		}			
    		
    		
    		ObjectName endPointInfoMBean = new ObjectName("WebSphere:feature=channelfw,type=endpoint,*");
    		Set<ObjectInstance> endpoints = mbs.queryMBeans(endPointInfoMBean, null);
    		boolean found = false;
    		for (ObjectInstance endpoint : endpoints) {
    			ObjectName endpointMBean = endpoint.getObjectName();
    			String host = (String) mbs.getAttribute(endpointMBean, "Host");
    			int port = (Integer) mbs.getAttribute(endpointMBean, "Port");
    			//out.println("<br>Host: " + host);
    			out.println("<br>Host: " + InetAddress.getLocalHost().getHostName());
    			out.println("<br>Port: " + port);
    			found = true;
    		}
    		if (!found) {
    			System.out.println("<br>EndPointInfoMBean doesn't seem to exist");
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    	}

    	//out.println("<br>Host: " + req.getRemoteHost());
    	//out.println("<br>Port: " + req.getServerPort());	

    	// Display information for existing session but don't create one
    	HttpSession mySession = req.getSession(false);
    	if (mySession == null){
    		out.println("<br>HTTPSession is null");	
    	} else {
    		long creation = mySession.getCreationTime();
        	Date d = new Date(TimeUnit.SECONDS.toMillis(creation));
        	out.println("<br>Session started: "+d);
    	}
    	
    	// Display the cloneid in the Cookie
    	Cookie[] cookies = req.getCookies();
    	if (cookies != null){
	    	for (Cookie cookie:cookies)
	    	{
	    		
	    		if (cookie.getName().equals("JSESSIONID")){
	    			int lastIndex = cookie.getValue().lastIndexOf(":");
	    		    String cloneid = cookie.getValue().substring(lastIndex + 1).trim();
	    		    out.println("<br>CloneID in cookie is: "+cloneid); 
	    		}
	    	}
    	}
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
