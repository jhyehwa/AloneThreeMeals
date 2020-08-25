package com.alone.threemeals.mapper;

import java.util.List;
import java.util.Map;

import com.alone.threemeals.notice.vo.MyRoomNotice;
import com.alone.threemeals.notice.vo.MyRoomNoticeComment;

public interface MyRoomNoticeMapper {
	public int insertMyRoomNotice(MyRoomNotice myRoomNotice) throws Exception;
	public int getCount(Map<String, Object> findMap) throws Exception;
	public MyRoomNotice selectOneMyRoom(int n_Seq) throws Exception;
	public List<MyRoomNotice> selectAllMyRoom(Map<String, Object> findMap) throws Exception;
	public int updateMyRoom(MyRoomNotice myRoomNotice) throws Exception;
	public int deleteMyRoom(int n_Seq) throws Exception;
	public int updateGrok(Map<String, Object> grokMap) throws Exception;
	
	public int grokCount(int n_Seq) throws Exception;
	public int nongrokCount(int n_Seq) throws Exception;
	public int commentGetCount(int n_Seq) throws Exception;
	
	public int updateHit(int n_Seq) throws Exception;
	public int minusHit(int n_Seq) throws Exception;	
	
	public int insertMyRoomComment(MyRoomNoticeComment myRoomNoticeComment) throws Exception;
	public int deleteCommentByNotice(int n_Seq) throws Exception;
	public int deleteMyRoomComment(int c_Seq) throws Exception;
	public int updateMyRoomComment(MyRoomNoticeComment myRoomNoticeComment) throws Exception;
	public List<MyRoomNoticeComment> selectAllMyRoomComment(Map<String, Integer> pageMap) throws Exception;
	
}
