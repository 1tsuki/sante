package com.astrider.sfc.src.command.internal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.ServletException;

import com.astrider.sfc.app.lib.Command;
import com.astrider.sfc.app.lib.helper.AuthUtils;
import com.astrider.sfc.src.model.dao.MaterialDao;
import com.astrider.sfc.src.model.vo.db.MaterialQuantityVo;
import com.astrider.sfc.src.model.vo.db.MaterialVo;
import com.astrider.sfc.src.model.vo.db.RecipeMaterialVo;

public class InsertRecipeMaterialsCommand extends Command {
    private final String authToken = "cc1f93c9fa2628492fc79a266abb2a40c61c4a91b6c824bf4d62f49d7b9f20b7efd"
            + "186854c4eda0363640318986b8e3683585bff769babd182560b52d1c7d1c898ffdb838a";

    @Override
    public void doGet() throws ServletException, IOException {
        System.out.println("access");
        String id = request.getParameter("id");
        String password = request.getParameter("password");

        if (id.equals("admin") && AuthUtils.verify(password, authToken)) {
            System.out.println("authenticated");
            insertMaterials();
        }
    }

    private void insertMaterials() {
        try {
            MaterialDao materialDao = new MaterialDao();
            File csv = new File("D:\\Projects\\workspace\\sante\\WEB-INF\\test-data\\materials.csv");
            BufferedReader br = new BufferedReader(new FileReader(csv));
            String line = "";
            int recipeId = -1;
            String materialName = "";
            String prefix = "";
            float quantity = -1;
            String postfix = "";

            while ((line = br.readLine()) != null) {
                String[] splitted = line.split(",");
                recipeId = Integer.parseInt(splitted[0]);
                materialName = splitted[1];
                prefix = splitted[2];
                quantity = Float.parseFloat(splitted[3]);
                postfix = splitted[4];

                MaterialVo material = materialDao.selectByNameAndPrePostfix(materialName, prefix, postfix);
                if (material == null) {
                    System.out.println("inserting item " + materialName);
                    material = new MaterialVo();
                    material.setGramPerQuantity(1);
                    material.setPrefix(prefix);
                    material.setMaterialName(materialName);
                    material.setPostfix(postfix);
                    material.setNutrientId(12);
                    materialDao.insert(material);
                    material = materialDao.selectByNameAndPrePostfix(materialName, prefix, postfix);
                }
                RecipeMaterialVo recipeMaterialVo = new RecipeMaterialVo();
                recipeMaterialVo.setRecipeId(recipeId);
                recipeMaterialVo.setQuantity(quantity);
                recipeMaterialVo.setMaterialId(material.getMaterialId());
                materialDao.insert(recipeMaterialVo);
            }
            materialDao.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
