package com.chi.makers.api.admin.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chi.makers.api.admin.vo.AdminMkVO;
import com.chi.makers.api.vo.ImgVO;
import com.chi.makers.api.vo.InfoVO;
import com.chi.makers.api.vo.MakersVO;
import com.chi.makers.api.vo.ScheduleVO;

@Repository
public class AdminMkDAO {

	@Autowired
	private SqlSession sqlSession;
	
	public List<AdminMkVO> selectListMakers() {
		return sqlSession.selectList("admin.selectListMakers");
	}

	public MakersVO selectMakers(int id) {
		return sqlSession.selectOne("admin.selectMakers", id);
	}

	public List<ImgVO> selectListImgs(int id) {
		return sqlSession.selectList("admin.selectImgs", id);
	}

	public List<InfoVO> selectListInfos(int id) {
		return sqlSession.selectList("admin.selectInfos", id);
	}

	public List<ScheduleVO> selectListSchedules(int id) {
		return sqlSession.selectList("admin.selectSchedules", id);
	}

	public int updateMakers(MakersVO makers) {
		return sqlSession.update("admin.updateMakers", makers);
		
	}

	public int deleteImgs(int id) {
		return sqlSession.delete("admin.deleteImgs", id);
	}

	public int insertImgs(MakersVO makers) {
		return sqlSession.insert("admin.insertImgs", makers);
		
	}

	public int deleteInfos(int id) {
		return sqlSession.delete("admin.deleteInfos", id);
	}

	public int insertInfos(MakersVO makers) {
		return sqlSession.insert("admin.insertInfos", makers);
	}

	public int deleteSchedules(int id) {
		return sqlSession.delete("admin.deleteSchedules", id);
	}

	public int insertSchedules(MakersVO makers) {
		return sqlSession.insert("admin.insertSchedules", makers);
	}
	
	public int insertMakers(MakersVO makers) {
		return sqlSession.insert("admin.insertMakers", makers);
	}

	public int generateId() {
		return sqlSession.selectOne("admin.generateId");
	}

	public int deleteMakers(int id) {
		return sqlSession.delete("admin.deleteMakers", id);
	}

}
