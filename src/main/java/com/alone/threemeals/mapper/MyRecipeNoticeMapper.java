package com.alone.threemeals.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.alone.threemeals.notice.vo.MyRecipeNotice;
import com.alone.threemeals.notice.vo.MyRecipeNoticeComment;

public interface MyRecipeNoticeMapper {
	public int insertMyRecipeNotice(MyRecipeNotice myRecipeNotice) throws Exception;
	public List<MyRecipeNotice> selectAllMyRecipe(Map<String, Object> findMap) throws Exception;
	public int getCount() throws SQLException;
	public MyRecipeNotice selectOneMyRecipe(int n_Seq) throws Exception;
	public int updateMyRecipe(MyRecipeNotice myRecipeNotice) throws Exception;
	public int deleteMyRecipe(int n_Seq) throws Exception;
	
	public int grokCount(int n_Seq) throws Exception;
	public int nongrokCount(int n_Seq) throws Exception;
	public int commentGetCount(int n_Seq) throws Exception;
	
	public int updateHit(int n_Seq) throws Exception;
	
	public int minusHit(int n_Seq) throws Exception;	
	public int insertMyRecipeComment(MyRecipeNoticeComment myRecipeNoticeComment) throws Exception;
	public int deleteCommentByNotice(int n_Seq) throws Exception;
	public int deleteMyRecipeComment(int c_Seq) throws Exception;
	public int updateMyRecipeComment(MyRecipeNoticeComment myRecipeNoticeComment) throws Exception;
	public List<MyRecipeNoticeComment> selectAllMyRecipeComment(Map<String, Integer> pageMap) throws Exception;
}
