package com.example.admin.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.admin.Enum.AjaxResultType;
import com.example.admin.Mapper.LectureMapper;
import com.example.admin.Model.ApplyVO;
import com.example.admin.Model.LectureVO;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class LectureService {


	@Autowired
	LectureMapper lectureMapper;

	/**
	 * 강연 정보 상세 조회
	 * @param lectureVO
	 * @return
	 */
	public LectureVO selectLecture(LectureVO lectureVO) {
		return lectureMapper.selectLecture(lectureVO);
	}

	/**
	 * 강연 등록/수정 처리
	 * @param lectureVO
	 * @return
	 */
	public Map<String, Object> saveLecture(LectureVO lectureVO) {
		Map<String,Object> map = new HashMap<String,Object>();
		String result = AjaxResultType.SUCCESS.getValue();
		String msg = "등록 하였습니다.";

		int isResult = 0;

		if(!lectureValidate(lectureVO)) {
        	result = AjaxResultType.FAIL.getValue();
			msg = "올바른 등록 정보가 아닙니다.";
		} else {
			//강연정보 있는지 조회
			LectureVO view = lectureMapper.selectLecture(lectureVO);

			//강연 값이 없는경우에만 저장 실행
			if(view == null) {
		        isResult = lectureMapper.insertLecture(lectureVO);

		        if(isResult == 0) {
		        	result = AjaxResultType.FAIL.getValue();
		    		msg = "등록 실패하었습니다.";
		        }
			} else {
				msg = "수정 하였습니다.";
				isResult = lectureMapper.updateLecture(lectureVO);

		        if(isResult == 0) {
		        	result = AjaxResultType.FAIL.getValue();
		    		msg = "수정에 실패하었습니다.";
		        }
			}
		}

		map.put("result", result);
		map.put("msg", msg);

		return map;
	}

	/**
	 * 강연 목록
	 * @param lectureVO
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> selectLectureList() throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();
		List<LectureVO> list = lectureMapper.selectLectureList();

		map.put("list", list);

		return map;
	}

	/**
	 * 강연 삭제
	 * @param lectureVO
	 * @return
	 */
	public Map<String, Object> deleteLecture(LectureVO lectureVO) {
		Map<String,Object> map = new HashMap<String,Object>();
		String result = AjaxResultType.SUCCESS.getValue();
		String msg = "삭제 하였습니다.";

		int isResult = 0;

		//강연정보 있는지 조회
		LectureVO view = lectureMapper.selectLecture(lectureVO);

		//강연 값이 있는경우에만 삭제 실행
		if(view == null) {
			msg = "강연 정보가 없습니다.";
		} else {
			isResult = lectureMapper.deleteLecture(lectureVO);

	        if(isResult == 0) {
	        	result = AjaxResultType.FAIL.getValue();
	    		msg = "삭제에 실패하었습니다.";
	        }
		}

		map.put("result", result);
		map.put("msg", msg);

		return map;
	}

	/**
	 * 강연 신청자 목록 조회
	 * @param lectureVO
	 * @return
	 */
	public Map<String, Object> selectLectureApplyList(LectureVO lectureVO) {
		Map<String,Object> map = new HashMap<String,Object>();
		String result = AjaxResultType.SUCCESS.getValue();
		String msg = "조회 성공하였습니다.";

		if(lectureVO.getLtIdx() != null && !"".equals(lectureVO.getLtIdx())) {
			//강연정보 있는지 조회
			LectureVO view = lectureMapper.selectLecture(lectureVO);

			//강연 값이 있는경우에만 실행
			if(view == null) {
				msg = "강연 정보가 없습니다.";
			} else {
				List<ApplyVO> list = lectureMapper.selectLectureApplyList(lectureVO);
				map.put("list", list);

				if(list.size() < 1) {
		    		msg = "강연 신청자가 없습니다.";
				}
			}

		} else {
        	result = AjaxResultType.FAIL.getValue();
    		msg = "강연idx를 입력해주세요.";
		}

		map.put("result", result);
		map.put("msg", msg);

		return map;
	}

	/**
	 * 강연 등록하기 값 체크
	 * @param lectureVO
	 * @return
	 */
	public boolean lectureValidate(LectureVO lectureVO) {
		boolean result = true;

		if(lectureVO.getLtNm() == null || "".equals(lectureVO.getLtNm())) {
			result = false;
		}else if(lectureVO.getLtPlace() == null || "".equals(lectureVO.getLtPlace())) {
			result = false;
		}else if(lectureVO.getStartDate() == null || "".equals(lectureVO.getStartDate())) {
			result = false;
		}else if(lectureVO.getMaxReq() == null || "".equals(lectureVO.getMaxReq())) {
			result = false;
		}else if(lectureVO.getLtCnn() == null || "".equals(lectureVO.getLtCnn())) {
			result = false;
		}

		return result;
	}
}
