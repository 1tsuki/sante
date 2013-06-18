package com.astrider.sfc.src.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.astrider.sfc.app.lib.helper.Mapper;
import com.astrider.sfc.app.lib.model.dao.BaseDao;
import com.astrider.sfc.src.model.vo.db.MaterialVo;

public class MaterialDao extends BaseDao {
	public MaterialDao() {
		super();
	}

	public MaterialDao(Connection con) {
		super(con);
	}

	public MaterialVo selectByNameAndPrePostfix(MaterialVo arg) {
		MaterialVo material = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "SELECT * FROM materials WHERE material_name = ? AND prefix = ? AND postfix = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, arg.getMaterialName());
			pstmt.setString(2, arg.getPrefix());
			pstmt.setString(3, arg.getPostfix());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Mapper<MaterialVo> mapper = new Mapper<MaterialVo>();
				material = mapper.fromResultSet(rs);
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
		return material;
	}
}
