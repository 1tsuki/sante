package com.astrider.sfc.src.helper;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import com.astrider.sfc.app.lib.helper.FlashMessage;
import com.astrider.sfc.app.lib.helper.StringUtils;
import com.astrider.sfc.src.model.vo.db.UserVo;

/**
 * jsp内で利用するヘルパークラス<br>
 *<br>
 * 機能<br>
 *  主要機能<br>
 *      ・getPath()          contextPathを自動的に取得、要求pathに付加<br>
 *      ・getCssPath()       ページ毎の固有cssファイルを自動挿入<br>
 *      ・getTitle();        FrontControllerにて設定されたタイトルを挿入<br>
 *      ・getFlashMessages() FlashMessageがあれば表示、一度表示したらsessionから削除<br>
 * @author Itsuki Sakitsu
 *
 */
public class ViewUtils {
    private PageContext context;
    private HttpSession session;
    private HttpServletRequest request;

    public ViewUtils(PageContext context, HttpSession session, HttpServletRequest request) {
        this.context = context;
        this.session = session;
        this.request = request;
    }

    /**
     * @return ログイン可否
     */
    public boolean isLoggedIn() {
        UserVo user = (UserVo) session.getAttribute("loginUser");
        return user != null;
    }

    /**
     * 相対パスを取得(contextPathを先頭に付加)
     * @param target
     */
    public void getPath(String target) {
        StringBuilder sb = new StringBuilder();
        sb.append(request.getContextPath());
        sb.append(target);
        print(sb.toString());
    }

    /**
     * urlに対応したcssをロード
     * frontControllerでinvalidがコールされていた場合はUnknown.cssをロード
     */
    public void getCssPath() {
        String fileName = getCssFileName();
        StringBuilder sb = new StringBuilder();
        sb.append("/asset/css");
        sb.append(fileName);

        getPath(sb.toString());
    }

    /**
     * Sessionに格納されたURL情報から対象のcssファイルの相対パスを取得
     * @return
     */
    private String getCssFileName() {
        String replaced = request.getServletPath().replace("/WEB-INF/template/view", "").replace(".jsp", "");
        String[] splitted = replaced.split("/");

        StringBuilder sb = new StringBuilder();
        for (int i=0; i < splitted.length; i++) {
            String item = splitted[i].toLowerCase();
            sb.append(item);
            if (i != splitted.length - 1) {
                sb.append("/");
            }
        }
        sb.append(".css");

        return sb.toString();
    }

    /**
     * sessionに格納されたページタイトル情報から動的にタイトルを出力
     */
    public void getTitle() {
        StringBuilder sb = new StringBuilder();
        sb.append(context.getServletContext().getServletContextName());
        String pageTitle = (String) session.getAttribute("pageTitle");
        if (StringUtils.isNotEmpty(pageTitle)) {
            sb.append(" | ");
            sb.append(pageTitle);
        }

        String title = sb.toString();
        print(title);
    }

    public void showBackgroundImage() {
        String replaced = request.getServletPath().replace("/WEB-INF/template/view/", "").replace(".jsp", "");
        if (replaced.equals("Index")) {
            print("<img alt=\"背景画像\" src=");
            getPath("/asset/img/topimage.jpg");
            print(" class=\"background-image\">");
        }
    }

    /**
     * sessionに格納されたerrorMessagesをflashMessageとして表示
     */
    public void getFlashMessages() {
        FlashMessage flashMessage = (FlashMessage) session.getAttribute("flashMessage");
        if (flashMessage != null && flashMessage.hasMessage()) {
            print("<div id='flash-messages' class='" + flashMessage.getMessageType().toString().toLowerCase() + "'>");
            print("<ul>");
            for (String message : flashMessage.getMessages()) {
                print("<li>" + message + "</li>");
            }
            print("</ul>");
            print("<a href='#' onClick='$(\"#flash-messages\").hide()'>x</a>");
            print("</div>");
            session.removeAttribute("flashMessage");
        }
    }

    /**
     * printを簡易化
     * @param arg
     */
    private void print(String arg) {
        try {
            context.getOut().print(arg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
