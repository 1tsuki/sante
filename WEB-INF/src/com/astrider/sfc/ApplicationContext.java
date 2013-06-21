package com.astrider.sfc;

public final class ApplicationContext {
	// フロントコントローラ周辺
	public static final String BASE_COMMAND_PATH = "com.astrider.sfc.src.command";
	public static final String FALLBACK_COMMAND = "UnknownCommand";
	public static final String COMMAND_POSTFIX = "Command";
	
	// 各種Path
	public static final String VIEW_BASEPATH = "/WEB-INF/template/view";
	public static final String CSS_BASEPATH = "/asset/css";
	public static final String JS_BASEPATH = "/asset/js";
	public static final String IMG_BASEPATH = "/asset/img";
	
	// AuthUtil関連
	public static final int SALT_LENGTH = 10;
	public static final int STRETCH_COUNT = 100;
	public static final int[] SALT_INSERT_POSITIONS = { 2, 14, 52, 63, 78, 81, 93, 101, 103, 120 };
	
	// 内部ツール関連
	public static final String ADMIN_ID = "admin";
	public static final String ADMIN_PASS = "cc1f93c9fa2628492fc79a266abb2a40c61c4a91b6c824bf4d62f49d7b9f20b7efd186854c4eda0363640318986b8e3683585bff769babd182560b52d1c7d1c898ffdb838a";
	
	// セッション名
	public static final String SESSION_USER = "sfc-loginUser";
	public static final String SESSION_FLASH_MESSAGE = "sfc-flashMessage";
	public static final String SESSION_SERVLET_PATH = "sfc-servletPath";
	public static final String SESSION_PAGE_TITLE = "sfc-pageTitle";
	public static final String SESSION_INVALID_PATH = "sfc-invalidPath";
	public static final String SESSION_REGISTER_FORM = "sfc-registerForm";

	// ページ名
	public static final String PAGE_INDEX = "/Index";
	public static final String PAGE_UNKNOWN = "/Unknown";
	public static final String PAGE_AUTH_REISSUE = "/auth/Reissue";
	public static final String PAGE_AUTH_LOGIN = "/auth/Login";
	public static final String PAGE_AUTH_LOGOUT = "/auth/Logout";
	public static final String PAGE_KNOWLEDGE_INDEX = "/knowledge/Index";
	public static final String PAGE_REGISTER_INPUT = "/register/Input";
	public static final String PAGE_REGISTER_SENDMAIL = "/register/SendMail";
	public static final String PAGE_REGISTER_CONFIRMEMAIL = "/register/ConfirmEmail";
	public static final String PAGE_REGISTER_SUCCESS = "/register/Success";
	public static final String PAGE_REGISTER_FAIL = "/register/Fail";
	public static final String PAGE_USER_INDEX = "/user/Index";
	public static final String PAGE_USER_RECIPE_DETAIL = "/user/recipe/Detail";
	public static final String PAGE_USER_RECIPE_SEARCH = "/user/recipe/Search";
	public static final String PAGE_USER_LOG_COOKCOMPLETE = "/user/log/CookComplete";
	public static final String PAGE_USER_LOG_DAILY = "/user/log/Daily";
	public static final String PAGE_USER_LOG_MONTHLY = "/user/log/Monthly";
	public static final String PAGE_USER_LOG_MODIFY = "/user/log/Modify";
	public static final String PAGE_USER_PROFILE_CHANGEPASSWORD = "/user/profile/ChangePassword";
	
	// DB周辺
	public static final String DB_LOCALNAME = "java:comp/env/jdbc/sante";
	
	// メーラー周辺
	public static final String MAIL_FROM = "noreply@sante.com";
	public static final String MAIL_HOST = "localhost";
	public static final String MAIL_PORT = "25"; 
	
	// 固有設定
	public static final int SANTE_NUTRIENT_COUNT = 11;
	public static final String SANTE_MATERIALS_CSV = "/Users/astrider/Documents/workspace/recruit/sante/WEB-INF/test-data/materials.csv";
}
