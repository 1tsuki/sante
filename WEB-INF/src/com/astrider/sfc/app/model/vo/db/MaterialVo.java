package com.astrider.sfc.app.model.vo.db;

import com.astrider.sfc.lib.model.vo.BaseVo;
import com.astrider.sfc.lib.helper.annotation.*;

@Table("materials")
public class MaterialVo extends BaseVo {
    private static final long serialVersionUID = -4318950366299339701L;
    @Column(physic="material_id", logic="素材ID", isPrimaryKey=true)
    @Valid(isNotNull=true, isMin=true, min=0)
    private int materialId;
    @Column(physic="material_name", logic="素材名")
    @Valid(isNotNull=true, isNotBlank=true)
    private String materialName;
    @Column(physic="gram_per_quantity", logic="単位あたりグラム数")
    @Valid(isNotNull=true, isMin=true, min=0)
    private int gramPerQuantity;
    @Column(physic="prefix", logic="接頭辞")
    private String prefix;
    @Column(physic="postfix", logic="接尾辞")
    private String postfix;
    @Column(physic="nutrient_id", logic="栄養素ID")
    private int nutrientId;

    public int getMaterialId() {
        return materialId;
    }
    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }
    public String getMaterialName() {
        return materialName;
    }
    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }
    public int getGramPerQuantity() {
        return gramPerQuantity;
    }
    public void setGramPerQuantity(int gramPerQuantity) {
        this.gramPerQuantity = gramPerQuantity;
    }
    public String getPrefix() {
        return prefix;
    }
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    public String getPostfix() {
        return postfix;
    }
    public void setPostfix(String postfix) {
        this.postfix = postfix;
    }
    public int getNutrientId() {
        return nutrientId;
    }
    public void setNutrientId(int nutrientId) {
        this.nutrientId = nutrientId;
    }
}
