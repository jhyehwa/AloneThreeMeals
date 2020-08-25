package com.alone.threemeals.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.alone.threemeals.notice.vo.StoreNotice;
import com.alone.threemeals.notice.vo.StoreNoticeComment;

public interface StoreNoticeMapper {
	public int insertStoreNotice(StoreNotice storeNotice) throws Exception;
	public List<StoreNotice> selectAllStore(Map<String, Object> findMap) throws Exception;
	public int getCount(Map<String, Object> findMap) throws SQLException;
	public StoreNotice selectOneStore(int n_Seq) throws Exception;
	public int updateStore(StoreNotice storeNotice) throws Exception;
	public int deleteStore(int n_Seq) throws Exception;
	public int insertStoreComment(StoreNoticeComment storeNoticeComment) throws Exception;
	public List<StoreNoticeComment> selectAllStoreComment(Map<String, Integer> pageMap) throws Exception;
	
	public int grokCount(int n_Seq) throws Exception;
	public int nongrokCount(int n_Seq) throws Exception;

	public int commentGetCount(int n_Seq) throws Exception;
	
	public int updateHit(int n_Seq) throws Exception;
	public int minusHit(int n_Seq) throws Exception;
	
	public int deleteCommentByNotice(int n_Seq) throws Exception;
	public int deleteStoreComment(int c_Seq) throws Exception;
	public int updateStoreComment(StoreNoticeComment storeNoticeComment) throws Exception;
}
