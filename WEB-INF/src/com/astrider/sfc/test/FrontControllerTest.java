package com.astrider.sfc.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;

import com.astrider.sfc.FrontController;
import com.astrider.sfc.test.app.command.test.FooCommand;
import com.astrider.sfc.lib.Command;

public class FrontControllerTest {
    @Test
    public void 存在するパスを要求() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getServletPath()).thenReturn("/test/Foo");
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        FrontController frontController = new FrontController();
        Command command = frontController.getCommand(request);
        assertEquals(command.getClass(), FooCommand.class);
    }

    @Test
    public void 存在しないパスを要求() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getServletPath()).thenReturn("/test/FooBar");
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        FrontController frontController = new FrontController();
        Command command = frontController.getCommand(request);
        assertEquals(command.getClass(), com.astrider.sfc.app.command.UnknownCommand.class);
    }

    @Test
    public void 非CaseSensitiveなパスを要求() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getServletPath()).thenReturn("/Test/Foo");
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        FrontController frontController = new FrontController();
        Command command = frontController.getCommand(request);
        assertEquals(command.getClass(), com.astrider.sfc.app.command.UnknownCommand.class);
    }

    @Test
    public void タイトル情報をsessionに保存() {
        HttpServletRequest requestMock = mock(HttpServletRequest.class);
        HttpServletResponse responseMock = mock(HttpServletResponse.class);
        ServletContext contextMock = mock(ServletContext.class);
        HttpSession sessionMock = mock(HttpSession.class);
        FrontController frontControllerMock = mock(FrontController.class);
        FooCommand commandMock = mock(FooCommand.class);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(frontControllerMock.getServletContext()).thenReturn(contextMock);
        when(frontControllerMock.getCommand(requestMock)).thenReturn(commandMock);
        try {
            doNothing().when(commandMock).init(
                    any(HttpServletRequest.class),
                    any(HttpServletResponse.class),
                    any(ServletContext.class));
            doNothing().when(commandMock).doGet();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            frontControllerMock.doGet(requestMock, responseMock);
            System.out.println((String) sessionMock.getAttribute("pageTitle"));
        } catch (ServletException e) {
            fail();
            e.printStackTrace();
        } catch (IOException e) {
            fail();
            e.printStackTrace();
        }
    }
}
