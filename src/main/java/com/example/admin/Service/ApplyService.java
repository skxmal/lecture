package com.example.admin.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.example.admin.Enum.AjaxResultType;
import com.example.admin.Mapper.ApplyMapper;
import com.example.admin.Model.ApplyVO;
import com.example.admin.Model.LectureVO;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class ApplyService {


	@Autowired
	ApplyMapper applyMapper;

	/**
	 * 강연 목록 조회
	 * @return
	 */
	public Map<String,Object> selectFrontLectureList() {
		Map<String,Object> map = new HashMap<String,Object>();

		List<LectureVO> list = applyMapper.selectFrontLectureList();
		map.put("list", list);

		return map;
	}

	/**
	 * 강연 상세 조회
	 * @param applyVO
	 * @return
	 */
	public LectureVO selectFrontLecture(ApplyVO applyVO) {
		return applyMapper.selectFrontLecture(applyVO);
	}

	/**
	 * 강연 신청 처리
	 * @param applyVO
	 * @return
	 */
	@Transactional
	public Map<String, Object> applyLecture(ApplyVO applyVO) {
		Map<String,Object> map = new HashMap<String,Object>();
		String result = AjaxResultType.SUCCESS.getValue();
		String msg = "신청 하였습니다.";

		int isResult = 0;

		if(!applyValidate(applyVO)) {
        	result = AjaxResultType.FAIL.getValue();
			msg = "올바른 사번, 강연idx가 아닙니다.";
		} else {

			//강연정보 있는지 조회
			LectureVO view = applyMapper.selectFrontLecture(applyVO);

			//강연 값이 있는경우에만 실행
			if(view == null) {
	        	result = AjaxResultType.FAIL.getValue();
				msg = "강연 정보가 없습니다.";
			} else {
				//신청정보 있는지 조회
				int applyCnt = applyMapper.selectApplyLectureCnt(applyVO);
				if(applyCnt > 0) {
		        	result = AjaxResultType.FAIL.getValue();
		    		msg = "이미 신청한 강의입니다.";
				} else {

					//강연 신청 가능여부(신청인원 초과로 불가시 N리턴, 아니면 Y리턴)
					String applyYn = applyMapper.selectLectureYn(applyVO);

					if("N".equals(applyYn)) {
			        	result = AjaxResultType.FAIL.getValue();
						msg = "신청 가능 인원이 초과되었습니다.";
					} else {
						isResult = applyMapper.applyLecture(applyVO);

				        if(isResult == 0) {
				        	result = AjaxResultType.FAIL.getValue();
				    		msg = "신청에 실패하었습니다.";
				        } else {
			        		String checkMaxYn = applyMapper.checkMaxReqYn(applyVO);

				        	if("N".equals(checkMaxYn)) {
				        		//신청가능인원이 초과되었으며 insert한 applyIdx가 max값인경우 롤백처리
				        		result = AjaxResultType.FAIL.getValue();
					    		msg = "신청 가능 인원이 초과되었습니다.";
					    		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				        	}
				        }
					}
				}
			}
		}

		map.put("result", result);
		map.put("msg", msg);

		return map;
	}

	/**
	 * 신청한 강연 목록 조회
	 * @param applyVO
	 * @return
	 */
	public Map<String,Object> selectFrontApplyList(ApplyVO applyVO) {
		Map<String,Object> map = new HashMap<String,Object>();
		String result = AjaxResultType.SUCCESS.getValue();
		String msg = "조회 성공하였습니다.";

		if(applyVO.getEmpNo() != null && !"".equals(applyVO.getEmpNo())) {
			List<LectureVO> list = applyMapper.selectFrontApplyList(applyVO);
			map.put("list", list);

			if(list.size() < 1) {
	    		msg = "신청한 강연이 없습니다.";
			}
		} else {
        	result = AjaxResultType.FAIL.getValue();
    		msg = "사번을 입력해주세요.";
		}

		map.put("result", result);
		map.put("msg", msg);

		return map;
	}

	/**
	 * 강연 신청 취소 처리
	 * @param applyVO
	 * @return
	 */
	public Map<String, Object> applyLectureCancel(ApplyVO applyVO) {
		Map<String,Object> map = new HashMap<String,Object>();
		String result = AjaxResultType.SUCCESS.getValue();
		String msg = "신청 취소 하였습니다.";

		int isResult = 0;

		if(!applyValidate(applyVO)) {
        	result = AjaxResultType.FAIL.getValue();
			msg = "올바른 사번, 강연idx가 아닙니다.";
		} else {

			//강연정보 있는지 조회
			LectureVO view = applyMapper.selectFrontLecture(applyVO);

			//강연 값이 있는경우에만 삭제 실행
			if(view == null) {
	        	result = AjaxResultType.FAIL.getValue();
				msg = "강연 정보가 없습니다.";
			} else {
				//신청정보 있는지 조회
				int applyCnt = applyMapper.selectApplyLectureCnt(applyVO);

				if(applyCnt < 1) {
		        	result = AjaxResultType.FAIL.getValue();
		    		msg = "신청하지 않은 강의입니다.";
				} else {
					isResult = applyMapper.applyLectureCancel(applyVO);

			        if(isResult == 0) {
			        	result = AjaxResultType.FAIL.getValue();
			    		msg = "신청 취소를 실패하었습니다.";
			        }
				}
			}
		}

		map.put("result", result);
		map.put("msg", msg);

		return map;
	}

	/**
	 * 강연 신청하기 값 체크
	 * @param applyVO
	 * @return
	 */
	public boolean applyValidate(ApplyVO applyVO) {
		boolean result = true;

		if(applyVO.getEmpNo() == null || "".equals(applyVO.getEmpNo()) || applyVO.getEmpNo().length() != 5) {
			result = false;
		}else if(applyVO.getLtIdx() == null || "".equals(applyVO.getLtIdx())) {
			result = false;
		}

		return result;
	}

	/**
	 * 실시간 인기 강연 목록(최근 3일간)
	 * @return
	 */
	public Map<String,Object> selectPopularLectureList() {
		Map<String,Object> map = new HashMap<String,Object>();

		List<LectureVO> list = applyMapper.selectPopularLectureList();
		map.put("list", list);

		return map;
	}
}
