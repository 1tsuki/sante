package com.astrider.sfc.src.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.astrider.sfc.app.lib.AuthUtils;
import com.astrider.sfc.app.lib.FlashMessage.Type;
import com.astrider.sfc.app.lib.Mailer;
import com.astrider.sfc.app.lib.Mapper;
import com.astrider.sfc.app.lib.StringUtils;
import com.astrider.sfc.app.lib.Validator;
import com.astrider.sfc.app.model.BaseModel;
import com.astrider.sfc.src.model.dao.UserDao;
import com.astrider.sfc.src.model.vo.db.UserVo;
import com.astrider.sfc.src.model.vo.form.LoginFormVo;
import com.astrider.sfc.src.model.vo.form.ReissueFormVo;

public class AuthModel extends BaseModel {
	public boolean authLogin(HttpServletRequest request) {
		// 入力情報取得
		Mapper<LoginFormVo> mapper = new Mapper<LoginFormVo>();
		LoginFormVo loginForm = mapper.fromHttpRequest(request);
		Validator<LoginFormVo> validator = new Validator<LoginFormVo>(loginForm);
		if (!validator.valid()) {
			flashMessage.addMessage(validator.getFlashMessage());
			return false;
		}
		String password = loginForm.getPassword();

		// emailに対応するuserVoを取得
		UserDao dao = new UserDao();
		UserVo user = dao.selectByEmail(loginForm.getEmail());
		dao.close();
		boolean authSucceed = false;
		if (user != null) {
			String authToken = user.getAuthToken();
			authSucceed = AuthUtils.verify(password, authToken);
		}

		// 入力されたパスワードを確認
		if (!authSucceed) {
			flashMessage.addMessage("ユーザー認証に失敗しました");
			return false;
		}

		// isConfirmedを確認
		if (!user.isConfirmed()) {
			flashMessage.addMessage("このユーザーはメールアドレス確認が完了していません。仮登録メールから本登録を実行してください。");
			return false;
		}

		// isAvailable, isDeletedを確認
		if (!user.isAvailable() || user.isDeleted()) {
			flashMessage.addMessage("ユーザー認証に失敗しました");
			return false;
		}

		// ログイン成功
		HttpSession session = request.getSession();
		session.setAttribute("loginUser", user);
		return true;
	}

	public boolean logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return true;
	}

	public boolean reissue(HttpServletRequest request) {
		Mapper<ReissueFormVo> mapper = new Mapper<ReissueFormVo>();
		ReissueFormVo form = mapper.fromHttpRequest(request);
		// form validation
		Validator<ReissueFormVo> validator = new Validator<ReissueFormVo>(form);
		if (!validator.valid()) {
			flashMessage.addMessage(validator.getFlashMessage());
			flashMessage.setMessageType(Type.WARNING);
			return false;
		}

		// get userVo
		UserDao userDao = new UserDao();
		UserVo user = userDao.selectByEmail(form.getEmail());
		if (user == null) {
			flashMessage.addMessage("ご指定のメールアドレスで登録されたアカウントは存在しませんでした");
			flashMessage.setMessageType(Type.WARNING);
			userDao.close();
			return false;
		}

		// set new password
		String tmpPassword = StringUtils.getUniqueString().substring(0, 10);
		user.setAuthToken(AuthUtils.encrypt(tmpPassword));
		userDao.update(user, false);
		
		// send Email
		String subject = "【sante】パスワード再発行手続き";
		StringBuilder sb = new StringBuilder();
		sb.append(user.getUserName() + "様\n\n");
		sb.append("お客様のパスワードが再発行されました。仮パスワードは下記のとおりです。\n\n");
		sb.append(tmpPassword + "\n\n");
		sb.append("仮パスワードでログイン後、必ず設定画面からパスワードの再設定を行なって下さい。\n");
		sb.append("また、このメールに心当たりの無い方はsante運営事務局にお問い合わせ下さい。\n");
		String body = sb.toString();
		Mailer mailer = new Mailer(user.getEmail(), subject, body);
		if (!mailer.send()) {
			flashMessage.addMessage("メールの送信に失敗しました");
			flashMessage.setMessageType(Type.WARNING);
			userDao.close();
			return false;
		}

		userDao.commit();
		userDao.close();
		
		flashMessage.addMessage("ご登録のメールアドレスに仮パスワードを送付しました。仮パスワードでログイン後、設定画面からパスワードの再設定を行なって下さい。");
		flashMessage.setMessageType(Type.INFO);
		return true;
	}

}
