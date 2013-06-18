package com.astrider.sfc.src.model.vo.form;

import com.astrider.sfc.app.lib.helper.annotation.Column;
import com.astrider.sfc.app.lib.model.vo.BaseVo;

public class SearchQueryVo extends BaseVo {
	private static final long serialVersionUID = 5770282629300515850L;
	@Column(physic="material_name", logic="素材名")
	private String materialName;
	@Column(physic="material_id", logic="素材ID")
	private int materialId;
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public int getMaterialId() {
		return materialId;
	}
	public void setMaterialId(int materialId) {
		this.materialId = materialId;
	}
}
