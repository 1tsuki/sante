package com.astrider.sfc.app.lib;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.astrider.sfc.ApplicationContext.*;

/**
 * Command.
 * 
 * @author astrider
 *         <p>
 *         FrontControllerから呼び出されるクラス。通常のServletに相当。<br>
 *         redirectやforwardを簡易化するメソッドの他、flashMessageの出力機能を持つ。
 *         </p>
 */
public abstract class Command {
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected ServletContext context;
	protected FlashMessage flashMessage;

	/**
	 * 初期化メソッド.
	 * 
	 * @param request
	 * @param response
	 * @param context
	 * @throws UnsupportedEncodingException
	 */
	public void init(HttpServletRequest request, HttpServletResponse response, ServletContext context)
			throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		this.request = request;
		this.response = response;
		this.context = context;

		initFlashMessage();
	}

	/**
	 * getリクエスト時.
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doGet() throws ServletException, IOException {
		redirect("/Unknown");
	}

	/**
	 * postリクエスト時.
	 * 
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doPost() throws ServletException, IOException {
		redirect("/Unknown");
	}

	/**
	 * forward.
	 * 
	 * @param target
	 * @throws ServletException
	 * @throws IOException
	 *             <p>
	 *             forwardの実行に加えて、flashMessageをセッションに格納する。
	 *             </p>
	 */
	protected void forward(String target) throws ServletException, IOException {
		registerFlashMessage();
		RequestDispatcher rd = context.getRequestDispatcher(target);
		rd.forward(request, response);
	}

	/**
	 * 簡易forward.
	 * 
	 * @param target
	 * @throws ServletException
	 * @throws IOException
	 *             <p>
	 *             /WEB-INF/等の接頭辞情報自動的に付与してforwardを実行。
	 *             forwardの実行に加えて、flashMessageをセッションに格納する。
	 *             </p>
	 */
	protected void render(String target) throws ServletException, IOException {
		registerFlashMessage();
		RequestDispatcher rd = context.getRequestDispatcher(VIEW_BASEPATH + target);
		rd.forward(request, response);
	}

	/**
	 * 簡易forward.
	 * 
	 * @param target
	 * @throws ServletException
	 * @throws IOException
	 *             <p>
	 *             要求されたURIに対応するjspをに対して自動的にforwardを実行。
	 *             forwardの実行に加えて、flashMessageをセッションに格納する。
	 *             </p>
	 */
	protected void render() throws ServletException, IOException {
		render(request.getServletPath() + ".jsp");
	}

	/**
	 * 簡易redirect.
	 * 
	 * @param target
	 * @throws IOException
	 */
	protected void redirect(String target) throws IOException {
		registerFlashMessage();
		String paramString = request.getContextPath() + target;
		response.sendRedirect(paramString);
	}

	/**
	 * redirectされてきたFlashMessagesの内容を配列に格納.
	 */
	private void initFlashMessage() {
		flashMessage = new FlashMessage();

		HttpSession session = request.getSession();
		FlashMessage passedMessages = (FlashMessage) session.getAttribute(SESSION_FLASH_MESSAGE);
		if (passedMessages != null) {
			flashMessage.addMessage(passedMessages);
			flashMessage.setMessageType(passedMessages.getMessageType());
		}
	}

	/**
	 * sessionにFlashMessage配列を登録
	 */
	private void registerFlashMessage() throws IOException {
		HttpSession session = request.getSession();
		session.setAttribute(SESSION_FLASH_MESSAGE, flashMessage);
	}
}
