package com.alone.threemeals.mapper;

import java.util.List;
import java.util.Map;

import com.alone.threemeals.notice.vo.NecessityNotice;
import com.alone.threemeals.notice.vo.NecessityNoticeComment;

public interface NecessityNoticeMapper {
	public int insertNecessityNotice(NecessityNotice necessityNotice) throws Exception;
	public int getCount(Map<String, Object> findMap) throws Exception;
	public NecessityNotice selectOneNecessity(int n_Seq) throws Exception;
	public List<NecessityNotice> selectAllNecessity(Map<String, Object> findMap) throws Exception;
	public int updateNecessity(NecessityNotice necessityNotice) throws Exception;
	public int deleteNecessity(int n_Seq) throws Exception;
	public int updateGrok(Map<String, Object> grokMap) throws Exception;
	
	public int grokCount(int n_Seq) throws Exception;
	public int nongrokCount(int n_Seq) throws Exception;
	public int commentGetCount(int n_Seq) throws Exception;
	
	public int updateHit(int n_Seq) throws Exception;
	public int minusHit(int n_Seq) throws Exception;
	
	public int insertNecessityComment(NecessityNoticeComment necessityNoticeComment) throws Exception;
	public int deleteCommentByNotice(int n_Seq) throws Exception;
	public int deleteNecessityComment(int c_Seq) throws Exception;
	public int updateNecessityComment(NecessityNoticeComment necessityNoticeComment) throws Exception;
	public List<NecessityNoticeComment> selectAllNecessityComment(Map<String, Integer> pageMap) throws Exception;
	
}
