package com.example.admin.Model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplyVO {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="강연신청 idx")
	private String applyIdx;	//강연신청 idx
	@ApiModelProperty(value="강연 idx")
	private String ltIdx;		//강연 idx
	@ApiModelProperty(value="사번")
	private String empNo;		//사번
	@ApiModelProperty(value="신청 등록일")
	private String instDate;	//신청 등록일
}
