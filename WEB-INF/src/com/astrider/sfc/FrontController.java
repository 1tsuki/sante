package com.astrider.sfc;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.astrider.sfc.app.annotation.Title;
import com.astrider.sfc.app.lib.Command;
import com.astrider.sfc.app.lib.StringUtils;
import com.astrider.sfc.src.command.UnknownCommand;

/**
 * FrontController.
 * 
 * @author astrider
 *         <p>
 *         全Commandに対するアクセスは全てこのクラスから振り分けられる<br>
 *         同時にCommandクラスにTitleアノテーションが用意されていた場合、自動的にページタイトルを設定する。<br>
 *         </p>
 * 
 */
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = -7412673150826768532L;

	/**
	 * doGet.
	 * <p>
	 * URLに対応したCommandクラスの呼び出し、及びページタイトル、servletPathの設定を行う。
	 * </p>
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Command command = getCommand(request);
		setPageTitle(request, command);
		setServletPath(request);
		ServletContext context = getServletContext();
		command.init(request, response, context);
		command.doGet();
	}

	/**
	 * doPost.
	 * <p>
	 * URLに対応したCommandクラスの呼び出し、及びページタイトル、servletPathの設定を行う。
	 * </p>
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Command command = getCommand(request);
		setPageTitle(request, command);
		setServletPath(request);
		ServletContext context = getServletContext();
		command.init(request, response, context);
		command.doPost();
	}

	/**
	 * CommandクラスにTitleアノテーションが付加されていた場合、sessionにタイトル情報を保存.
	 */
	private void setPageTitle(HttpServletRequest request, Command command) {
		Title title = command.getClass().getAnnotation(Title.class);
		if (title != null && StringUtils.isNotEmpty(title.value())) {
			request.setAttribute("pageTitle", title.value());
		}
	}

	/**
	 * sessionにservletPathを追加。jspにforwardされてからのViewHelper用.
	 * 
	 * @param request
	 */
	private void setServletPath(HttpServletRequest request) {
		request.setAttribute("servletPath", request.getServletPath());
	}

	/**
	 * Commandクラスのインスタンスを生成 失敗した場合はUnknownにredirect
	 * 
	 * @param request
	 * @return
	 */
	public Command getCommand(HttpServletRequest request) {
		String commandName = getCommandClassName(request);
		Class<? extends Command> commandClass;
		Command command = null;

		boolean isInvalidPath = false;
		try {
			commandClass = Class.forName(commandName).asSubclass(Command.class);
			command = commandClass.newInstance();
		} catch (Exception e) {
			command = new UnknownCommand();
			isInvalidPath = true;
		} catch (NoClassDefFoundError e) {
			command = new UnknownCommand();
			isInvalidPath = true;
		}

		request.setAttribute("invalidPath", isInvalidPath);
		return command;
	}

	/**
	 * URLをpackage.name.classnameCommand に変換
	 * 
	 * @param request
	 * @return
	 */
	protected String getCommandClassName(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getPackage().getName());
		sb.append(".src.command");
		String[] extracted = request.getServletPath().split("/");
		for (int i = 0; i < extracted.length; i++) {
			String item = extracted[i];
			if (StringUtils.isNotEmpty(item)) {
				sb.append(".");
				sb.append(item);
			}
		}
		sb.append("Command");
		return sb.toString();
	}
}
