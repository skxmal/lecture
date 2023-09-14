package com.example.admin.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.admin.Model.ApplyVO;
import com.example.admin.Service.ApplyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api("ApplyApiController API")
@RestController
@RequestMapping("/api")
public class ApplyApiController {

	@Autowired
	ApplyService applyService;

	/**
	 * 강연 목록 조회
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value="강연 목록(프론트)")
	@GetMapping(value = {"/apply/lectureList.do"})
	public Map<String,Object> lectureList() throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();

		map = applyService.selectFrontLectureList();

		return map;
	}

	/**
	 * 강연 신청 처리
	 * @param applyVO
	 * @return
	 */
	@ApiOperation(value="강연 신청 처리")
	@ApiImplicitParams({
		@ApiImplicitParam(name="ltIdx",value="강연idx",required = true)
		,@ApiImplicitParam(name="empNo",value="사번",required = true)
	})
	@GetMapping("/apply/applyLecture.do")
	public Map<String,Object> applyLecture(ApplyVO applyVO) {
		Map<String,Object> map = new HashMap<String,Object>();

		map = applyService.applyLecture(applyVO);

		return map;
	}

	/**
	 * 강연 신청 목록
	 * @param applyVO
	 * @return
	 */
	@ApiOperation(value="강연 신청 목록")
	@ApiImplicitParam(name="empNo",value="사번",required = true)
	@GetMapping("/apply/applyList.do")
	public Map<String,Object> applyList(ApplyVO applyVO) {
		Map<String,Object> map = new HashMap<String,Object>();

		map = applyService.selectFrontApplyList(applyVO);

		return map;
	}

	/**
	 * 강연 신청 취소 처리
	 * @param applyVO
	 * @return
	 */
	@ApiOperation(value="강연 신청 취소 처리")
	@ApiImplicitParams({
		@ApiImplicitParam(name="ltIdx",value="강연idx",required = true)
		,@ApiImplicitParam(name="empNo",value="사번",required = true)
	})
	@GetMapping("/apply/applyLectureCancel.do")
	public Map<String,Object> applyLectureCancel(ApplyVO applyVO) {
		Map<String,Object> map = new HashMap<String,Object>();

		map = applyService.applyLectureCancel(applyVO);

		return map;
	}

	/**
	 * 실시간 인기 강연 목록(최근 3일간)
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value="실시간 인기 강연 목록(최근 3일간)")
	@GetMapping(value = {"/apply/popularLectureList.do"})
	public Map<String,Object> popularLectureList() throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();

		map = applyService.selectPopularLectureList();

		return map;
	}


}
