package com.chi.makers.api.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chi.makers.api.vo.ImgVO;
import com.chi.makers.api.vo.InfoVO;
import com.chi.makers.api.vo.MakersBestVO;
import com.chi.makers.api.vo.MakersVO;
import com.chi.makers.api.vo.OptionVO;
import com.chi.makers.api.vo.ScheduleVO;

@Repository //Data Access Object - DB 접속하는 저장소이니까
public class MakersDAO {
	
	@Autowired // 이거 안해주면 null 밑에서 포인트 익셉션 뜬다. -조심 
	private SqlSession sqlSession;

	public List<MakersVO> getMakers() {		
		return sqlSession.selectList("makers.selectMakers");
	}

	public List<ImgVO> getImg(int id) {
		//mybatis는 item_id(mysql) -> itemID(java)로 자동으로 mapping 함 - mybatis-config.xml에 설정할 수 있음
		return sqlSession.selectList("makers.selectImg", id);
	}

	public MakersVO getMakers(int id) {
		return sqlSession.selectOne("makers.selectSubItem", id);
	}

	public List<ImgVO> getImgs(int id) {
		return sqlSession.selectList("makers.selectImgs", id);
	}

	public List<InfoVO> getInfos(int id) {
		return sqlSession.selectList("makers.selectInfos", id);
	}

	public List<ScheduleVO> getSchedules(int id) {
		return sqlSession.selectList("makers.selectSchedules", id);
	}

	public List<MakersBestVO> selectBestMakers() {
		return sqlSession.selectList("makers.selectBestOrders");
	}

	public List<OptionVO> getOptions(int id) {
		return sqlSession.selectList("makers.selectOptions", id);
	}

}
