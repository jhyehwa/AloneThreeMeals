package com.alone.threemeals.notice.contorllers;

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

import com.alone.threemeals.mapper.MyRecipeNoticeMapper;
import com.alone.threemeals.notice.vo.MyRecipeNotice;
import com.alone.threemeals.notice.vo.MyRecipeNoticeComment;

@Controller
public class MyRecipeNoticeController {
	@Autowired
	SqlSession sqlSession;
	@Autowired
	MyRecipeNotice myRecipeNotice;
	@Autowired
	MyRecipeNoticeComment myRecipeNoticeComment;

	/** 나만의 레시피 게시판 글쓰기 화면 */
	@RequestMapping(value="/myRecipeNoticeRegView", method=RequestMethod.POST)
	public String myRecipeNoticeRegView(){
		return "myrecipenotice/myRecipeNoticeRegView";
	}

	/** 나만의 레시피 게시판 글쓰기 기능 */
	@RequestMapping(value="/myRecipeNoticeRegProcess", method=RequestMethod.POST)
	public String myRecipeNoticeRegProcess(
			@RequestParam(value="n_Title") String n_Title,
			@RequestParam(value="n_Writer") String n_Writer,
			@RequestParam(value="n_File") CommonsMultipartFile n_File,
			@RequestParam(value="star") String star,
			@RequestParam(value="n_Content") String n_Content,
			HttpServletRequest request,
			Model Model
			){
		int n_Star = Integer.parseInt(star);
		myRecipeNotice.setN_Title(n_Title);
		myRecipeNotice.setN_Writer(n_Writer);
		myRecipeNotice.setN_File(n_File);
		myRecipeNotice.setN_Star(n_Star);
		myRecipeNotice.setN_Content(n_Content);
		String fileName = myRecipeNotice.getN_File().getOriginalFilename();
		long time = System.currentTimeMillis();
		String fileOld[] = fileName.split("\\.");
		fileName = fileOld[0]+"_"+time+"."+fileOld[1];
		myRecipeNotice.setN_Picture(fileName);
		String realPath = request.getServletContext().getRealPath("/upload");
		String path = realPath+"\\"+fileName;

		FileOutputStream fs = null;
		try{
			fs = new FileOutputStream(path);
			fs.write(myRecipeNotice.getN_File().getBytes());
			if(fs != null) fs.close();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		MyRecipeNoticeMapper myRecipeMap = sqlSession.getMapper(MyRecipeNoticeMapper.class);

		try {
			myRecipeMap.insertMyRecipeNotice(myRecipeNotice);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:myRecipeNoticeList";
	}

	/** 나만의 레시피 리스트 뽑기 */
	@RequestMapping(value="/myRecipeNoticeList", method=RequestMethod.GET)
	public String myRecipeNoticeList(
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

		MyRecipeNoticeMapper myRecipeMap = sqlSession.getMapper(MyRecipeNoticeMapper.class);
		List<MyRecipeNotice> myRecipelist = null;

		try {
			myRecipelist=myRecipeMap.selectAllMyRecipe(findMap);
			totalPages = myRecipeMap.getCount();
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
		request.setAttribute("myRecipelist", myRecipelist);

		return "myrecipenotice/myRecipeNoticeList";
	}

	/** 나만의 레시피 글보기 */
	@RequestMapping(value="/myRecipeNoticeDetail", method=RequestMethod.GET)
	public String myRecipeNoticeDetail(@RequestParam(value="n_Seq") String seq,
			String currPage,
			Model model){
		MyRecipeNoticeMapper myRecipeMap = sqlSession.getMapper(MyRecipeNoticeMapper.class);
		List<MyRecipeNoticeComment> myRecipeCommentList=null;
		int n_Seq = Integer.parseInt(seq);
		int grok=0;
		int nongrok=0;
		
		int tempPage = 0;
		if(currPage == null || currPage.equals(""))
			tempPage = 1;
		else{
			tempPage = Integer.parseInt(currPage);
		}
		
		int totalPages = 1;
		int srow = 1+(tempPage-1)*4;
		int erow = 4+(tempPage-1)*4;
		
		Map<String, Integer> pageMap = new HashMap<String, Integer>();
		
		pageMap.put("n_Seq",n_Seq);
		pageMap.put("srow",srow);
		pageMap.put("erow",erow);
		
		try {
			grok = myRecipeMap.grokCount(n_Seq);
			nongrok= myRecipeMap.nongrokCount(n_Seq);
			myRecipeMap.updateHit(n_Seq);
			myRecipeNotice=myRecipeMap.selectOneMyRecipe(n_Seq);
			myRecipeCommentList = myRecipeMap.selectAllMyRecipeComment(pageMap);
			totalPages = myRecipeMap.commentGetCount(n_Seq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(totalPages%4 != 0)
			totalPages = totalPages/4+1;
		else
			totalPages = totalPages/4;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateview = sdf.format(myRecipeNotice.getN_RegDate());
		
		List<String> dateList_ymd = new ArrayList<String>();
		SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");		
		for(int i=0;i<myRecipeCommentList.size();i++){			
			dateList_ymd.add(i, ymd.format(myRecipeCommentList.get(i).getC_RegDate()));
		};				
		List<String> dateList_hm = new ArrayList<String>();
		SimpleDateFormat hm = new SimpleDateFormat("HH:mm");
		for(int i=0;i<myRecipeCommentList.size();i++){			
			dateList_hm.add(i, hm.format(myRecipeCommentList.get(i).getC_RegDate()));
		};		
		Date date = new Date();
		String today = ymd.format(date);
		
		model.addAttribute("dateList_ymd", dateList_ymd);
		model.addAttribute("dateList_hm", dateList_hm);
		model.addAttribute("today", today);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("grok", grok);
		model.addAttribute("nongrok", nongrok);
		model.addAttribute("dateview", dateview);
		model.addAttribute("myRecipe", myRecipeNotice);
		model.addAttribute("myRecipeCommentList", myRecipeCommentList);
		return "myrecipenotice/myRecipeNoticeDetail";
	}

	/** 나만의 레시피 글 수정 뷰 */
	@RequestMapping(value="/myRecipeNoticeUpdateView", method=RequestMethod.GET)
	public String myRecipeNoticeUpdateView(@RequestParam(value="n_Seq") String seq, Model model){
		MyRecipeNoticeMapper myRecipeMap = sqlSession.getMapper(MyRecipeNoticeMapper.class);
		int n_Seq = Integer.parseInt(seq);
		try {
			myRecipeNotice = myRecipeMap.selectOneMyRecipe(n_Seq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("myRecipe",myRecipeNotice);

		return "myrecipenotice/myRecipeNoticeUpdateView";

	}

	/** 나만의 레시피 글 수정 기능 */
	@RequestMapping(value="/myRecipeNoticeUpdateProcess", method=RequestMethod.POST)
	public String myRecipeNoticeUpdateProcess(
			@RequestParam(value="n_Seq") String seq,
			@RequestParam(value="n_Title") String n_Title,
			@RequestParam(value="n_File") CommonsMultipartFile n_File,
			@RequestParam(value="star") String star,
			@RequestParam(value="n_Content") String n_Content,
			HttpServletRequest request,
			Model model
			){
		MyRecipeNoticeMapper myRecipeMap = sqlSession.getMapper(MyRecipeNoticeMapper.class);
		MyRecipeNotice myRecipeChoice = null;
		int n_Seq = Integer.parseInt(seq);
		int n_Star = Integer.parseInt(star);

		try {
			myRecipeChoice = myRecipeMap.selectOneMyRecipe(n_Seq);
		} catch (Exception e) {
			e.printStackTrace();
		} 

		myRecipeNotice.setN_Seq(n_Seq);
		myRecipeNotice.setN_Title(n_Title);
		myRecipeNotice.setN_Star(n_Star);
		myRecipeNotice.setN_Content(n_Content);

		myRecipeNotice.setN_File(n_File);
		String fileName = myRecipeNotice.getN_File().getOriginalFilename();

		if(fileName.equals("")){
			myRecipeNotice.setN_Picture(myRecipeChoice.getN_Picture());
		}
		else{
			long time = System.currentTimeMillis();
			String fileOld[] = fileName.split("\\.");
			fileName = fileOld[0]+"_"+time+"."+fileOld[1];
			myRecipeNotice.setN_Picture(fileName);
			String realPath = request.getServletContext().getRealPath("/upload");
			String path = realPath+"\\"+fileName;
			String delPath = realPath+"\\"+myRecipeChoice.getN_Picture();
			FileOutputStream fs = null;
			File file = new File(delPath);
			file.delete();

			try{
				fs = new FileOutputStream(path);
				fs.write(myRecipeNotice.getN_File().getBytes());
				if(fs != null) fs.close();
			}catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}

		try {
			myRecipeMap.updateMyRecipe(myRecipeNotice);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:myRecipeNoticeDetail?n_Seq="+n_Seq;

	}
	
	
	/** 댓글 추가 */
	   @RequestMapping(value="/myRecipeCommentRegProcess", method=RequestMethod.POST)
	   public void myRecipeCommentRegProcess(
	         @RequestParam(value="n_Seq") String n_seq,
	         @RequestParam(value="c_Writer") String c_Writer,
	         @RequestParam(value="c_Content") String c_Content,
	         @RequestParam(value="c_Grok") String c_Grok,
	         HttpServletResponse response
	         ){
		  PrintWriter pw =null;
		   
	      int n_Seq = Integer.parseInt(n_seq);
	      myRecipeNoticeComment.setN_Seq(n_Seq);
	      myRecipeNoticeComment.setC_Writer(c_Writer);
	      myRecipeNoticeComment.setC_Content(c_Content);
	      myRecipeNoticeComment.setC_Grok(c_Grok);

	      MyRecipeNoticeMapper myRecipeMap = sqlSession.getMapper(MyRecipeNoticeMapper.class);
	      
	      try {
	    	  myRecipeMap.minusHit(n_Seq);
	    	  myRecipeMap.insertMyRecipeComment(myRecipeNoticeComment);
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	     
	      try {
	          pw = response.getWriter();
	       } catch (IOException e) {
	          e.printStackTrace();
	       }
	      pw.print("success");
	   }
	   
	   
	   /** 나만의 레시피 글 삭제 */
	   @RequestMapping(value="/myRecipeNoticeDelete", method=RequestMethod.GET)
	   public String myRecipeNoticeDelete(
			   @RequestParam(value="n_Seq") String n_seq
			   ){
		   int n_Seq = Integer.parseInt(n_seq);
		   MyRecipeNoticeMapper myRecipeMap = sqlSession.getMapper(MyRecipeNoticeMapper.class);
		   int cnt =0;
		   try {
			   cnt = myRecipeMap.commentGetCount(n_Seq);
		   } catch (Exception e) {
			e.printStackTrace();
		   }
		   
		   if(cnt != 0){
			   try {
				myRecipeMap.deleteCommentByNotice(n_Seq);
			} catch (Exception e) {
				e.printStackTrace();
			}
		   }
		   try {
			myRecipeMap.deleteMyRecipe(n_Seq);
		   } catch (Exception e) {
			e.printStackTrace();
		   }
		   
		   return "redirect:myRecipeNoticeList";
	   }
	   
	   /** 댓글 삭제 */
	   @RequestMapping(value="/myRecipeCommentDelete", method=RequestMethod.POST)
	   public void myRecipeCommentDelete(
			   @RequestParam(value="n_Seq") String n_seq,
			   @RequestParam(value="c_Seq") String c_seq,
			   HttpServletResponse response
	         ){
		  PrintWriter pw =null;
		   
		  int n_Seq = Integer.parseInt(n_seq);
	      int c_Seq = Integer.parseInt(c_seq);

	      MyRecipeNoticeMapper myRecipeMap = sqlSession.getMapper(MyRecipeNoticeMapper.class);

	      try {
	    	  myRecipeMap.minusHit(n_Seq);
	    	  myRecipeMap.deleteMyRecipeComment(c_Seq);
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	     
	      try {
	          pw = response.getWriter();
	       } catch (IOException e) {
	          e.printStackTrace();
	       }
	      pw.print("success");
	   }
	   
	   /** 댓글 수정 */
	   @RequestMapping(value="/myRecipeCommentUpdate", method=RequestMethod.POST)
	   public void myRecipeCommentUpdate(		
			   @RequestParam(value="n_Seq") String n_seq,
			   @RequestParam(value="c_Seq") String c_seq,
		       @RequestParam(value="c_Content") String c_Content,
		       @RequestParam(value="c_Grok") String c_Grok,
		       HttpServletResponse response
	         ){
		  PrintWriter pw =null;
		   
		  int n_Seq = Integer.parseInt(n_seq);
	      int c_Seq = Integer.parseInt(c_seq);
	      myRecipeNoticeComment.setC_Seq(c_Seq);
	      myRecipeNoticeComment.setC_Content(c_Content);
	      myRecipeNoticeComment.setC_Grok(c_Grok);

	      MyRecipeNoticeMapper myRecipeMap = sqlSession.getMapper(MyRecipeNoticeMapper.class);

	      try {
	    	  myRecipeMap.minusHit(n_Seq);
	    	  myRecipeMap.updateMyRecipeComment(myRecipeNoticeComment);
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	     
	      try {
	          pw = response.getWriter();
	       } catch (IOException e) {
	          e.printStackTrace();
	       }
	      pw.print("success");
	   }
}
