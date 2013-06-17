package com.astrider.sfc.lib;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.astrider.sfc.lib.helper.FlashMessage;

/**
 * Front Command<br>
 *<br>
 * 概要<br>
 *  スクラッチのservletに相当。doGet,doPostメソッドに加えて、redirectやforwardを簡易化するメソッド等を用意。<br>
 *  flashMessageクラスを保持し、中身が存在した場合は自動的にflashmessageがページに表示される。<br>
 *<br>
 * 機能<br>
 *  主要機能<br>
 *      ・init()     通常のServlet処理に必要な各種変数を代入、その他初期化処理<br>
 *      ・doGet()    標準ではUnknown.jspにforwardする。Overrideすべし。<br>
 *      ・doPost()   標準ではUnknown.jspにforwardする。Overrideすべし。<br>
 *<br>
 *  副次機能<br>
 *      ・render()       jspを描画、引数なしで自動的に名前で対応するjspを呼び出し<br>
 *      ・redirect()     requestDispatcherを実行<br>
 * @author Itsuki Sakitsu
 *
 */
public abstract class Command {
    protected static final String VIEW_BASEPATH = "/WEB-INF/template/view";
    protected HttpServletRequest  request;
    protected HttpServletResponse response;
    protected ServletContext      context;
    protected FlashMessage        flashMessage;

    /**
     * 初期化メソッド
     * @param request
     * @param response
     * @param context
     * @throws UnsupportedEncodingException
     */
    public void init(HttpServletRequest request, HttpServletResponse response,
            ServletContext context) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        this.request = request;
        this.response = response;
        this.context = context;

        initFlashMessage();
    }

    /**
     * getリクエスト時
     * @throws ServletException
     * @throws IOException
     */
    public void doGet() throws ServletException, IOException {
        redirect("/Unknown");
    }

    /**
     * postリクエスト時
     * @throws ServletException
     * @throws IOException
     */
    public void doPost() throws ServletException, IOException {
        redirect("/Unknown");
    }

    /**
     * forward実行用ラッパーメソッド
     * @param target
     * @throws ServletException
     * @throws IOException
     */
    protected void forward(String target) throws ServletException, IOException {
        registerFlashMessage();
        RequestDispatcher rd = context.getRequestDispatcher(target);
        rd.forward(request, response);
    }

    /**
     * servletに対応するjspに自動forward
     * @param target
     * @throws ServletException
     * @throws IOException
     */
    protected void render(String target) throws ServletException, IOException {
        registerFlashMessage();
        RequestDispatcher rd = context.getRequestDispatcher(VIEW_BASEPATH + target);
        rd.forward(request, response);
    }

    /**
     * 指定した名前のjspに自動forward
     * @throws ServletException
     * @throws IOException
     */
    protected void render() throws ServletException, IOException {
        render(request.getServletPath() + ".jsp");
    }

    /**
     * @param target
     * @throws IOException
     */
    protected void redirect(String target) throws IOException {
        registerFlashMessage();
        String paramString = request.getContextPath() + target;
        response.sendRedirect(paramString);
    }

    /**
     * redirectされてきたflashMessagesの内容を配列に格納
     */
    private void initFlashMessage() {
        flashMessage = new FlashMessage();

        HttpSession session = request.getSession();
        FlashMessage passedMessages = (FlashMessage) session.getAttribute("flashMessage");
        if (passedMessages != null) {
            flashMessage.addMessage(passedMessages);
        }
    }

    /**
     * sessionにflashMessage配列を登録
     */
    private void registerFlashMessage() throws IOException {
        HttpSession session = request.getSession();
        session.setAttribute("flashMessage", flashMessage);
    }
}
