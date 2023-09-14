package com.example.admin.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.admin.Model.ApplyVO;
import com.example.admin.Model.LectureVO;

@Repository
@Mapper
public interface ApplyMapper {

	public int applyLecture(ApplyVO applyVO);

	public int selectApplyLectureCnt(ApplyVO applyVO);

	public String selectLectureYn(ApplyVO applyVO);

	public List<LectureVO> selectFrontLectureList();

	public LectureVO selectFrontLecture(ApplyVO applyVO);

	public List<LectureVO> selectFrontApplyList(ApplyVO applyVO);

	public int applyLectureCancel(ApplyVO applyVO);

	public List<LectureVO> selectPopularLectureList();

	public String checkMaxReqYn(ApplyVO applyVO);
}
