package com.fbm.mgmt.supervisor;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Parasu17
 *
 */
public class SimpleServlet extends HttpServlet {	

	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(SimpleServlet.class);

	private void info(String str) {
		System.out.println(str);
	}

    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	log.info("Inside the doPost method");
    	info("hello");
    	Map<String, String[]> paramMap = req.getParameterMap();
    	if(paramMap != null) {
    		for(Map.Entry<String, String[]> ent : paramMap.entrySet()) {
    			info("param: key=" + ent.getKey() + "; value=" + Arrays.toString(ent.getValue()));
    		}
    	}
    	info(paramMap == null ? "null" : paramMap.toString());
    }

}
