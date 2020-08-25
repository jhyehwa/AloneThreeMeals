package com.alone.threemeals.mapper;

import java.util.List;
import java.util.Map;

import com.alone.threemeals.notice.vo.HoneyTipNotice;
import com.alone.threemeals.notice.vo.HoneyTipNoticeComment;

public interface HoneyTipNoticeMapper {
	public int insertHoneyTipNotice(HoneyTipNotice honeyTipNotice) throws Exception;
	public int getCount(Map<String, Object> findMap) throws Exception;
	public HoneyTipNotice selectOneHoneyTip(int n_Seq) throws Exception;
	public List<HoneyTipNotice> selectAllHoneyTip(Map<String, Object> findMap) throws Exception;
	public int updateHoneyTip(HoneyTipNotice honeyTipNotice) throws Exception;
	public int deleteHoneyTip(int n_Seq) throws Exception;
	public int updateGrok(Map<String, Object> grokMap) throws Exception;
	
	public int grokCount(int n_Seq) throws Exception;
	public int nongrokCount(int n_Seq) throws Exception;
	public int commentGetCount(int n_Seq) throws Exception;
	
	public int updateHit(int n_Seq) throws Exception;	
	public int minusHit(int n_Seq) throws Exception;	
	
	public int insertHoneyTipComment(HoneyTipNoticeComment honeyTipNoticeComment) throws Exception;
	public int deleteCommentByNotice(int n_Seq) throws Exception;
	public int deleteHoneyTipComment(int c_Seq) throws Exception;
	public int updateHoneyTipComment(HoneyTipNoticeComment honeyTipNoticeComment) throws Exception;
	public List<HoneyTipNoticeComment> selectAllHoneyTipComment(Map<String, Integer> pageMap) throws Exception;
}
