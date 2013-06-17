package com.astrider.sfc.src.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.astrider.sfc.app.lib.helper.AuthUtils;
import com.astrider.sfc.app.lib.helper.Mapper;
import com.astrider.sfc.app.lib.model.BaseModel;
import com.astrider.sfc.src.model.dao.UserDao;
import com.astrider.sfc.src.model.vo.db.UserVo;
import com.astrider.sfc.src.model.vo.form.LoginFormVo;

public class AuthModel extends BaseModel {
    public boolean authLogin(HttpServletRequest request) {
        // 入力情報取得
        Mapper<LoginFormVo> mapper = new Mapper<LoginFormVo>();
        LoginFormVo loginForm = mapper.fromHttpRequest(request);
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

}
