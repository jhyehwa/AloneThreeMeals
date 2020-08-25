package com.alone.threemeals.cook.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alone.threemeals.mapper.CookMapper;
import com.alone.threemeals.member.vo.Cook;
import com.alone.threemeals.member.vo.Likecheck;
import com.alone.threemeals.notice.vo.MyRecipeNotice;
import com.alone.threemeals.notice.vo.StoreNotice;

@Controller
public class CookController {
   @Autowired
   SqlSession sqlSession;
   @Autowired
   Cook cook;
   @Autowired
   Likecheck likecheck;
   
   
   /** 레시피 글쓰기 뷰 */
   @RequestMapping(value="/cookRegView", method=RequestMethod.POST)
   public String cookRegView(){
      return "cook/cookRegView";
   }

   /** 레시피 글쓰기 기능 */
   @RequestMapping(value="/cookRegProcess", method=RequestMethod.POST)
   public String cookRegProcess(
         @RequestParam(value="c_Name") String c_Name,
         @RequestParam(value="c_Recipe") String c_Recipe,
         @RequestParam(value="c_File") CommonsMultipartFile c_File,
         @RequestParam(value="main_Ingredient") String main_Ingredient,
         HttpServletRequest request,
         Model model){
      cook.setC_Name(c_Name);
      cook.setC_Like(0);
      cook.setC_Recipe(c_Recipe);
      cook.setMain_Ingredient(main_Ingredient);
      cook.setC_File(c_File);
      String fileName = cook.getC_File().getOriginalFilename();
      long time = System.currentTimeMillis();
      String fileOld[] = fileName.split("\\.");
      fileName = fileOld[0]+"_"+time+"."+fileOld[1];
      cook.setC_Picture(fileName);
      String realPath = request.getServletContext().getRealPath("/upload");
      String path = realPath+"\\"+fileName;

      FileOutputStream fs = null;
      try {
         fs = new FileOutputStream(path);
         fs.write(cook.getC_File().getBytes());
         if(fs != null)fs.close();
      } catch (FileNotFoundException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      CookMapper cookMap = sqlSession.getMapper(CookMapper.class);
      try {
         cookMap.insertCook(cook);
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      model.addAttribute("cook", cook);
      return "redirect:cookList";
   }

   /** 레시피 리스트 뽑기 */
   @RequestMapping(value="/cookList", method=RequestMethod.GET)
   public String cookList(
         String find, String qtext, String currPage, HttpServletRequest request
         ){
      if(find == null) find="n_Writer";
      if(qtext == null) qtext="";

      int tempPage = 0;

      if(currPage == null || currPage.equals(""))
         tempPage = 1;
      else 
         tempPage = Integer.parseInt(currPage);

      int totalPages = 1;

      int srow = 1+(tempPage-1)*6;
      int erow = 6+(tempPage-1)*6;

      Date date = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      String today = sdf.format(date);

      Map<String, Object> findMap = new HashMap<String, Object>();
      findMap.put("find", find);
      findMap.put("qtext", qtext);
      findMap.put("srow", srow);
      findMap.put("erow", erow);


      CookMapper cookMap = sqlSession.getMapper(CookMapper.class);
      List<Cook> cooklist=null;
      try {
         cooklist = cookMap.selectAllCook(findMap);
         totalPages = cookMap.getCount();
      } catch (Exception e) {
         e.printStackTrace();
      }

      if(totalPages%6 != 0)
         totalPages = totalPages/6+1;
      else
         totalPages = totalPages/6;

      request.setAttribute("find", find);
      request.setAttribute("qtext", qtext);
      request.setAttribute("today", today);
      request.setAttribute("currPage", tempPage);
      request.setAttribute("totalPages", totalPages);
      request.setAttribute("cooklist", cooklist);
      System.out.println(cooklist);
      return "cook/cookList";
   }

   /** 레시피 보기 */
   @RequestMapping(value="/cookDetail", method=RequestMethod.GET)
   public String cookDetail(@RequestParam(value="c_Id") String id,
         Model model){
      int c_Id = Integer.parseInt(id);
      CookMapper cookMap = sqlSession.getMapper(CookMapper.class);
      try {
         cook = cookMap.selectOneCook(c_Id);
      } catch (Exception e) {
         e.printStackTrace();
      }
      model.addAttribute("cook", cook);
      return "cook/cookDetail";
   }
   
   /** 추천 여부 */
   @RequestMapping(value="/likeCheck", method=RequestMethod.POST)
   public void likeCheck(
         @RequestParam(value="c_Id") String id,
         @RequestParam(value="m_Id") String m_Id,
         HttpServletResponse response
         ){
      PrintWriter pw = null;
      try {
         pw = response.getWriter();
      } catch (IOException e1) {
         e1.printStackTrace();
      }
            
      int c_Id = Integer.parseInt(id);
      Likecheck likeChoice = null;
      CookMapper cookMap = sqlSession.getMapper(CookMapper.class);
      
      Map<String, Object> likeCkMap = new HashMap<String, Object>();
      Map<String, Object> clikeMap = new HashMap<String, Object>();
      likeCkMap.put("c_Id", c_Id);
      likeCkMap.put("m_Id", m_Id);
      
      try {
         likeChoice = cookMap.selectOneLikeCheck(likeCkMap);
      } catch (Exception e) {
         e.printStackTrace();
      }
      
      likecheck.setC_Id(c_Id);
      likecheck.setM_Id(m_Id);
            
      clikeMap.put("c_Id", c_Id);
      
      if(likeChoice == null){
         clikeMap.put("l_Ck", "yes");
         try {
            cookMap.insertLikeCheck(likecheck);
            cookMap.updateCLike(clikeMap);
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
      else{
         likecheck.setL_Ck(likeChoice.getL_Ck());
         if(likeChoice.getL_Ck().equals("yes"))
            clikeMap.put("l_Ck", "no");
         else
            clikeMap.put("l_Ck", "yes");
         
         try {
            System.out.println("///////"+likecheck);
            cookMap.updateLikecheck(likecheck);
            cookMap.updateCLike(clikeMap);
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
      
      pw.print("success");
      
   }
   
   
   /** 레시피 수정 뷰 */
   @RequestMapping(value="cookUpdateView", method=RequestMethod.GET)
   public String cookUpdateView(@RequestParam(value="c_Id") String id,
         Model model){
      int c_Id = Integer.parseInt(id);
      CookMapper cookMap = sqlSession.getMapper(CookMapper.class);
      try {
         cook = cookMap.selectOneCook(c_Id);
      } catch (Exception e) {
         e.printStackTrace();
      }
      model.addAttribute("cook",cook);
      return "cook/cookUpdateView";
   }

   /** 레시피 수정 기능 */
   @RequestMapping(value="cookUpdateProcess", method=RequestMethod.POST)
   public String cookUpdateProcess(
         @RequestParam(value="c_Id") String id,
         @RequestParam(value="c_Name") String c_Name,
         @RequestParam(value="c_Recipe") String c_Recipe,
         @RequestParam(value="c_File") CommonsMultipartFile c_File,
         @RequestParam(value="main_Ingredient") String main_Ingredient,
         HttpServletRequest request,
         Model model
         ){
      int c_Id = Integer.parseInt(id);
      CookMapper cookMap = sqlSession.getMapper(CookMapper.class);
      Cook cookChoice = null;
      try {
         cookChoice = cookMap.selectOneCook(c_Id);
      } catch (Exception e) {
         e.printStackTrace();
      }
      cook.setC_Id(c_Id);
      cook.setC_Name(c_Name);
      cook.setMain_Ingredient(main_Ingredient);
      cook.setC_Recipe(c_Recipe);
      cook.setC_File(c_File);
      String fileName = cook.getC_File().getOriginalFilename();
      if(fileName.equals(""))
         cook.setC_Picture(cookChoice.getC_Picture());
      else{
         long time = System.currentTimeMillis();
         String fileOld[] = fileName.split("\\.");
         fileName = fileOld[0]+"_"+time+"."+fileOld[1];
         cook.setC_Picture(fileName);
         String realPath = request.getServletContext().getRealPath("/upload");
         String path = realPath+"\\"+fileName;
         String delPath = realPath+"\\"+cookChoice.getC_Picture();
         FileOutputStream fs = null;
         File file = new File(delPath);
         file.delete();
         
         try{
            fs = new FileOutputStream(path);
            fs.write(cook.getC_File().getBytes());
            if(fs != null) fs.close();
         }catch (FileNotFoundException e) {
            e.printStackTrace();
         } catch (IOException e) {
            e.printStackTrace();
         } 
               
      }
      
      try {
         cookMap.updateCook(cook);
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      
      
      return "redirect:cookUpdateComplete?c_Id="+c_Id;
   }
   
   /** 수정완료 화면 */
   @RequestMapping(value="/cookUpdateComplete", method=RequestMethod.GET)
   public String cookUpdateComplete(@RequestParam(value="c_Id") String id,
         Model model){
      int c_Id = Integer.parseInt(id);
      CookMapper cookMap = sqlSession.getMapper(CookMapper.class);
      try {
         cook = cookMap.selectOneCook(c_Id);
      } catch (Exception e) {
         e.printStackTrace();
      }
      model.addAttribute("cook", cook);
      return "cook/cookUpdateComplete";
   }
   
   /** 글 삭제 */
   @RequestMapping(value="/cookDeleteProcess", method=RequestMethod.GET)
   public String cookDeleteProcess(@RequestParam(value="c_Id") String id,
         Model model){
      int c_Id = Integer.parseInt(id);
      CookMapper cookMap = sqlSession.getMapper(CookMapper.class);
      try {
    	 cookMap.deleteLike(c_Id);
         cookMap.deleteCook(c_Id);
      } catch (Exception e) {
         e.printStackTrace();
      }
      return "redirect:cookList";
   }

   /** 인기메뉴 */
   @RequestMapping(value="/bestMenuRegView", method=RequestMethod.GET)
   public String bestMenuRegView(Model model){
	   CookMapper cookMap = sqlSession.getMapper(CookMapper.class);
	   List<Cook> cookList = null;
	   List<MyRecipeNotice> myRecipeList = null;
	   List<StoreNotice> storeList = null;
	   try {
		   cookList = cookMap.cookLikeBest();
		   myRecipeList = cookMap.myRecipeLikeBest();
		   storeList = cookMap.storeLikeBest();
	   } catch (Exception e) {
		   e.printStackTrace();
	   }
	   model.addAttribute("cookList", cookList);
	   model.addAttribute("myRecipeList", myRecipeList);
	   model.addAttribute("storeList", storeList);
	   return "cook/bestMenu";
   }
   
   /** 오늘 뭐 먹지? */
   @RequestMapping(value="/todayChoice", method=RequestMethod.GET)
   public String todayChoice(Model model){
	   CookMapper cookMap = sqlSession.getMapper(CookMapper.class);
	   List<Cook> cooklist = new ArrayList<Cook>();
	   try {
		cooklist = cookMap.selectCookList();
	   } catch (Exception e) {
		e.printStackTrace();
	   }
	   System.out.println("==========="+cooklist.size());
	   model.addAttribute("cooklist", cooklist);
	   return "cook/todayChoice";
   }

}