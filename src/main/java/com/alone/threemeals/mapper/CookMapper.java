package com.alone.threemeals.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.alone.threemeals.member.vo.Cook;
import com.alone.threemeals.member.vo.Likecheck;
import com.alone.threemeals.notice.vo.MyRecipeNotice;
import com.alone.threemeals.notice.vo.StoreNotice;

public interface CookMapper {
   public int insertCook(Cook cook) throws Exception;
   public List<Cook> selectAllCook(Map<String, Object> findMap) throws Exception;
   public List<Cook> selectCookList() throws Exception;
   public int getCount() throws SQLException;
   public Cook selectOneCook(int c_Id) throws Exception;
   public int insertLikeCheck(Likecheck likecheck) throws Exception;
   public Likecheck selectOneLikeCheck(Map<String, Object> likeCkMap) throws Exception;
   public int updateLikecheck(Likecheck likecheck) throws Exception;
   public int updateCLike(Map<String, Object> clikeMap) throws Exception;
   public int updateCook(Cook cook) throws Exception;
   public int deleteCook(int c_Id) throws Exception;
   public int deleteLike(int c_Id) throws Exception;
   public List<Cook> cookLikeBest() throws Exception;
   public List<MyRecipeNotice> myRecipeLikeBest() throws Exception;
   public List<StoreNotice> storeLikeBest() throws Exception;
   
   
}