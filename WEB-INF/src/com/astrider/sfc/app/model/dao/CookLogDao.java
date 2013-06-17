package com.astrider.sfc.app.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.astrider.sfc.app.model.vo.db.CookLogVo;
import com.astrider.sfc.lib.helper.Mapper;
import com.astrider.sfc.lib.model.dao.BaseDao;

public class CookLogDao extends BaseDao {
    public CookLogDao() {
        super();
    }

    public CookLogDao(Connection con) {
        super(con);
    }

    public ArrayList<CookLogVo> selectCookedAtYesterday(int userId) {
        ArrayList<CookLogVo> cookLogs = new ArrayList<CookLogVo>();
        PreparedStatement pstmt = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM cook_logs WHERE user_id = ? ");
            sb.append("AND cooked_at BETWEEN TRUNC(SYSDATE - 1) AND TRUNC(SYSDATE) - 1/86400");
            pstmt = con.prepareStatement(sb.toString());
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Mapper<CookLogVo> mapper = new Mapper<CookLogVo>();
                CookLogVo cookLog = mapper.fromResultSet(rs);
                cookLogs.add(cookLog);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cookLogs;
    }

}
