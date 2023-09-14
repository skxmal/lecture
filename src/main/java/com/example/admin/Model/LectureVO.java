package com.example.admin.Model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LectureVO {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="강연idx")
	private String ltIdx;		//강연 idx
	@ApiModelProperty(value="강연자명")
	private String ltNm;		//강연자명
	@ApiModelProperty(value="강연장")
	private String ltPlace;		//강연장
	@ApiModelProperty(value="신청인원")
	private String maxReq;		//신청인원
	@ApiModelProperty(value="강연시간(ex:2023-09-14 10:10")
	private String startDate;	//강연시간
	@ApiModelProperty(value="강연내용")
	private String ltCnn;		//강연내용
}
