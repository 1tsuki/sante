package com.astrider.sfc.app.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.astrider.sfc.app.model.vo.db.MaterialQuantityVo;
import com.astrider.sfc.app.model.vo.db.RecipeVo;
import com.astrider.sfc.app.model.vo.db.StepVo;
import com.astrider.sfc.lib.helper.Mapper;
import com.astrider.sfc.lib.model.dao.BaseDao;

public class RecipeDao extends BaseDao {
    public RecipeDao() {
        super();
    }

    public RecipeDao(Connection con) {
        super(con);
    }

    public ArrayList<MaterialQuantityVo> selectMaterialQuantitiesByRecipeId(int recipeId) {
        ArrayList<MaterialQuantityVo> materialQuantities = new ArrayList<MaterialQuantityVo>();
        PreparedStatement pstmt = null;

        try {
            String sql =  "SELECT * FROM RECIPE_MATERIAL_QUANTITIES WHERE recipe_id = ? ORDER BY nutrient_id";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, recipeId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Mapper<MaterialQuantityVo> mapper = new Mapper<MaterialQuantityVo>();
                MaterialQuantityVo materialQuantity = mapper.fromResultSet(rs);
                materialQuantities.add(materialQuantity);
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
        return materialQuantities;
    }

    public ArrayList<StepVo> selectStepsByRecipeId(int recipeId) {
        ArrayList<StepVo> steps = new ArrayList<StepVo>();
        PreparedStatement pstmt = null;

        try {
            String sql = "SELECT * FROM steps WHERE recipe_id = ? ORDER BY STEP";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, recipeId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Mapper<StepVo> mapper = new Mapper<StepVo>();
                StepVo step = mapper.fromResultSet(rs);
                steps.add(step);
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
        return steps;
    }

    public RecipeVo selectByRecipeId(int recipeId) {
        RecipeVo recipe = null;
        PreparedStatement pstmt = null;

        try {
            String sql = "SELECT * FROM recipes WHERE recipe_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, recipeId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Mapper<RecipeVo> mapper = new Mapper<RecipeVo>();
                recipe = mapper.fromResultSet(rs);
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

        return recipe;
    }

    public ArrayList<RecipeVo> selectByMaterialName(String materialName) {
        ArrayList<RecipeVo> recipes = new ArrayList<RecipeVo>();
        PreparedStatement pstmt = null;

        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM (recipes INNER JOIN recipe_materials ON ");
            sb.append("(recipes.recipe_id = recipe_materials.recipe_id))");
            sb.append("INNER JOIN materials ON (recipe_materials.material_id = materials.material_id) ");
            sb.append("WHERE materials.material_name = ?");
            String sql = sb.toString();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, materialName);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Mapper<RecipeVo> mapper = new Mapper<RecipeVo>();
                RecipeVo recipe = mapper.fromResultSet(rs);
                recipes.add(recipe);
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

        return recipes;
    }

    public ArrayList<Integer> selectRecipeIdByNutrientId(int nutrientId) {
        ArrayList<Integer> recipes = new ArrayList<Integer>();
        PreparedStatement pstmt = null;

        try {
            String sql = "SELECT DISTINCT recipe_id FROM RECIPE_MATERIAL_QUANTITIES WHERE nutrient_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, nutrientId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                recipes.add(rs.getInt(1));
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
        return recipes;
    }

    public ArrayList<MaterialQuantityVo> selectMaterialQuantitiesByNutrientId(int nutrientId) {
        ArrayList<MaterialQuantityVo> materials = new ArrayList<MaterialQuantityVo>();
        PreparedStatement pstmt = null;

        try {
            String sql = "SELECT * FROM RECIPE_MATERIAL_QUANTITIES WHERE nutrient_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, nutrientId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Mapper<MaterialQuantityVo> mapper = new Mapper<MaterialQuantityVo>();
                MaterialQuantityVo material = mapper.fromResultSet(rs);
                materials.add(material);
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
        return materials;
    }

    public ArrayList<RecipeVo> selectAll() {
        ArrayList<RecipeVo> recipes = new ArrayList<RecipeVo>();
        PreparedStatement pstmt = null;

        try {
            String sql = "SELECT * FROM recipes";
            pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Mapper<RecipeVo> mapper = new Mapper<RecipeVo>();
                recipes.add(mapper.fromResultSet(rs));
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

        return recipes;
    }
}
