package com.example.admin.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.admin.Model.ApplyVO;
import com.example.admin.Model.LectureVO;

@Repository
@Mapper
public interface LectureMapper {

	public LectureVO selectLecture(LectureVO lectureVO);

	public int insertLecture(LectureVO lectureVO);

	public int updateLecture(LectureVO lectureVO);

	public List<LectureVO> selectLectureList();

	public int deleteLecture(LectureVO lectureVO);

	public List<ApplyVO> selectLectureApplyList(LectureVO lectureVO);
}
