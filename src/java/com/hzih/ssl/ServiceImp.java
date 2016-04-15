package com.hzih.ssl;


import com.inetec.common.security.DecryptClassLoader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by IntelliJ IDEA.
 * User: wxh
 * Date: 2005-11-9
 * Time: 0:09:50
 * To change this template use File | Settings | File Templates.
 */
public class ServiceImp extends HttpServlet {
    private static DecryptClassLoader m_classLoad = null;
    private Class m_service = null;
    private String m_path = null;



    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        try {
            m_service.getMethod("doGet", new Class[]{HttpServletRequest.class, HttpServletResponse.class}).invoke(m_service.newInstance(), new Object[]{request, response});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        try {
            m_service.getMethod("doPost", new Class[]{HttpServletRequest.class, HttpServletResponse.class}).invoke(m_service.newInstance(), new Object[]{request, response});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public void init() throws ServletException {
        try {
            m_service.getMethod("init", new Class[]{String.class, String.class}).invoke(m_service.newInstance(), new Object[]{m_path, ""});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
