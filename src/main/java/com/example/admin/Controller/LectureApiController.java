package com.example.admin.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.admin.Model.LectureVO;
import com.example.admin.Service.LectureService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api("LectureApiController API")
@RestController
@RequestMapping("/api/admin")
public class LectureApiController {

	@Autowired
	LectureService lectureService;

	/**
	 * 강연 목록
	 * @param lectureVO
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value="강연 목록(어드민)")
	@GetMapping("/lecture/lectureList.do")
	public Map<String,Object> lectureList() throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();

		map = lectureService.selectLectureList();

		return map;
	}

	/**
	 * 강연 등록/수정 처리
	 * @param lectureVO
	 * @return
	 */
	@ApiOperation(value="강연 등록/수정 처리")
	@ApiImplicitParams({
		@ApiImplicitParam(name="ltIdx",value="강연idx(수정시 필요)",required = false)
		,@ApiImplicitParam(name="ltNm",value="강연자명",required = true)
		,@ApiImplicitParam(name="ltPlace",value="강연장",required = true)
		,@ApiImplicitParam(name="startDate",value="강연시간(ex:2023-09-14 10:10)",required = true)
		,@ApiImplicitParam(name="maxReq",value="신청인원",required = true)
		,@ApiImplicitParam(name="ltCnn",value="강연내용",required = true)
	})
	@GetMapping("/lecture/saveLecture.do")
	public Map<String,Object> insertLecture(LectureVO lectureVO) {
		Map<String,Object> map = new HashMap<String,Object>();

		map = lectureService.saveLecture(lectureVO);

		return map;
	}

	/**
	 * 강연 삭제 처리
	 * @param lectureVO
	 * @return
	 */
	@ApiOperation(value="강연 삭제 처리")
	@ApiImplicitParam(name="ltIdx",value="강연idx",required = true)
	@GetMapping("/lecture/deleteLecture.do")
	public Map<String,Object> deleteLecture(LectureVO lectureVO) {
		Map<String,Object> map = new HashMap<String,Object>();

		map = lectureService.deleteLecture(lectureVO);

		return map;
	}

	/**
	 * 강연 신청자 목록 조회
	 * @param lectureVO
	 * @return
	 */
	@ApiOperation(value="강연 신청자 목록 조회")
	@ApiImplicitParam(name="ltIdx",value="강연idx",required = true)
	@GetMapping("/lecture/lectureApplyList.do")
	public Map<String,Object> lectureApplyList(LectureVO lectureVO) {
		Map<String,Object> map = new HashMap<String,Object>();

		map = lectureService.selectLectureApplyList(lectureVO);

		return map;
	}

}
