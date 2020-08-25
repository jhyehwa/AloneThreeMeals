package com.alone.threemeals.mapper;

import java.util.List;
import java.util.Map;

import com.alone.threemeals.notice.vo.AnabadaNotice;
import com.alone.threemeals.notice.vo.AnabadaNoticeComment;

public interface AnabadaNoticeMapper {
	public int insertAnabadaNotice(AnabadaNotice anabadaNotice) throws Exception;
	public int getCount(Map<String, Object> findMap) throws Exception;
	public AnabadaNotice selectOneAnabada(int n_Seq) throws Exception;
	public List<AnabadaNotice> selectAllAnabada(Map<String, Object> findMap) throws Exception;
	public int updateAnabada(AnabadaNotice anabadaNotice) throws Exception;
	public int deleteAnabada(int n_Seq) throws Exception;
	public int updateGrok(Map<String, Object> grokMap) throws Exception;
	
	public int grokCount(int n_Seq) throws Exception;
	public int nongrokCount(int n_Seq) throws Exception;
	public int commentGetCount(int n_Seq) throws Exception;
	
	public int updateHit(int n_Seq) throws Exception;
	public int minusHit(int n_Seq) throws Exception;	
	
	public int insertAnabadaComment(AnabadaNoticeComment anabadaNoticeComment) throws Exception;
	public int deleteCommentByNotice(int n_Seq) throws Exception;
	public int deleteAnabadaComment(int c_Seq) throws Exception;
	public int updateAnabadaComment(AnabadaNoticeComment anabadaNoticeComment) throws Exception;
	public List<AnabadaNoticeComment> selectAllAnabadaComment(Map<String, Integer> pageMap) throws Exception;
}
