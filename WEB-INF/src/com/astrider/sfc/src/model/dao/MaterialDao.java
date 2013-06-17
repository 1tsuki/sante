package com.astrider.sfc.src.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.astrider.sfc.app.lib.helper.Mapper;
import com.astrider.sfc.app.lib.model.dao.BaseDao;
import com.astrider.sfc.src.model.vo.db.MaterialVo;

public class MaterialDao extends BaseDao {
    public MaterialVo selectByNameAndPrePostfix(String name, String prefix, String postfix) {
        MaterialVo material = null;
        PreparedStatement pstmt = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM materials WHERE material_name = ? AND ");
            if (prefix.equals("")) {
                sb.append(" prefix IS NULL AND ");
            } else {
                sb.append(" prefix = ? AND ");
            }
            if (postfix.equals("")) {
                sb.append(" postfix IS NULL");
            } else {
                sb.append(" postfix = ?");
            }
            pstmt = con.prepareStatement(sb.toString());
            pstmt.setString(1, name);

            int i = 2;
            if (prefix.equals("")) {
                pstmt.setString(i++, prefix);
            }
            if (postfix.equals("")) {
                pstmt.setString(i++, postfix);
            }
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Mapper<MaterialVo> mapper = new Mapper<MaterialVo>();
                material = mapper.fromResultSet(rs);
                System.out.println(material.getMaterialId());
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
