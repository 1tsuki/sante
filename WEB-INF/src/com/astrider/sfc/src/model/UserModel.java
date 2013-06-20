package com.astrider.sfc.src.model;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.astrider.sfc.app.lib.BaseModel;
import com.astrider.sfc.app.lib.Mailer;
import com.astrider.sfc.app.lib.Mapper;
import com.astrider.sfc.app.lib.Validator;
import com.astrider.sfc.app.lib.FlashMessage.Type;
import com.astrider.sfc.app.lib.util.AuthUtils;
import com.astrider.sfc.src.helper.SanteUtils;
import com.astrider.sfc.src.helper.SanteUtils.InsufficientNutrients;
import com.astrider.sfc.src.model.dao.UserDao;
import com.astrider.sfc.src.model.vo.db.RecipeVo;
import com.astrider.sfc.src.model.vo.db.UserVo;
import com.astrider.sfc.src.model.vo.form.ChangePasswordFormVo;

/**
 * ユーザー関連Model.
 * 
 * @author astrider
 * 
 */
public class UserModel extends BaseModel {

	/**
	 * マイページ用.
	 * 
	 * @param request
	 * @return
	 */
	public boolean getRecommendedRecipes(HttpServletRequest request) {
		UserVo user = SanteUtils.getLoginUser(request);
		if (user == null) {
			return false;
		}

		InsufficientNutrients nutrients = SanteUtils.getInsufficientNutrients(user.getUserId());
		request.setAttribute("nutrients", nutrients);

		ArrayList<RecipeVo> recipes = SanteUtils.getRecommendedRecipes(user.getUserId(), 4);
		request.setAttribute("recommendedRecipes", recipes);
		return true;
	}

	public boolean changePassword(HttpServletRequest request) {
		Mapper<ChangePasswordFormVo> mapper = new Mapper<ChangePasswordFormVo>();
		ChangePasswordFormVo form = mapper.fromHttpRequest(request);
		Validator<ChangePasswordFormVo> validator = new Validator<ChangePasswordFormVo>(form);
		if (!validator.valid()) {
			flashMessage.addMessage(validator.getFlashMessage());
			return false;
		}
		
		// 入力パスワード不一致
		if (!form.getNewPassword().equals(form.getNewPasswordConfirm())) {
			flashMessage.addMessage("新しいパスワードが一致しませんでした");
			return false;
		}

		// ログイン不具合
		UserVo loginUser = SanteUtils.getLoginUser(request);
		if (loginUser == null) {
			flashMessage.addMessage("不正なアクセス");
			return false;
		}

		// 認証パスワード不一致
		if (!AuthUtils.verify(form.getCurrentPassword(), loginUser.getAuthToken())) {
			flashMessage.addMessage("入力されたパスワードが間違っています");
			return false;
		}

		// 変更処理
		loginUser.setAuthToken(AuthUtils.encrypt(form.getNewPassword()));
		UserDao userDao = new UserDao();
		userDao.update(loginUser, false);
		
		// メール送信
		String subject = "【sante】パスワード変更手続き";
		StringBuilder sb = new StringBuilder();
		sb.append(loginUser.getUserName() + "様\n\n");
		sb.append("お客様のパスワードが変更されました。");
		sb.append("このメールに心当たりの無い方はsante運営事務局にお問い合わせ下さい。\n");
		String body = sb.toString();
		Mailer mailer = new Mailer(loginUser.getEmail(), subject, body);
		if (!mailer.send()) {
			flashMessage.addMessage("メールの送信に失敗しました");
			userDao.close();
			return false;
		}

		userDao.commit();
		userDao.close();
		
		flashMessage.addMessage("パスワードが正常に変更されました");
		flashMessage.setMessageType(Type.INFO);
		return true;
	}
}
