package com.alone.threemeals.notice.contorllers;

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

import com.alone.threemeals.mapper.AnabadaNoticeMapper;
import com.alone.threemeals.notice.vo.AnabadaNotice;
import com.alone.threemeals.notice.vo.AnabadaNoticeComment;

@Controller
public class AnabadaNoticeController {
	@Autowired
	SqlSession sqlSession;
	@Autowired
	AnabadaNotice anabadaNotice;
	@Autowired
	AnabadaNoticeComment anabadaNoticeComment;
	
	/** 아나바다 게시판 글쓰기 화면 */
	@RequestMapping(value="/anabadaNoticeRegView", method=RequestMethod.POST)
	public String anabadaNoticeRegView(){
		return "anabadanotice/anabadaNoticeRegView";
	}
	
	/** 아나바다 게시판 글쓰기 기능 */
	@RequestMapping(value="/anabadaNoticeRegProcess", method=RequestMethod.POST)
	public String anabadaNoticeRegProcess(
			@RequestParam(value="n_Title") String n_Title,
			@RequestParam(value="n_Writer") String n_Writer,
			@RequestParam(value="n_Content") String n_Content,
			HttpServletRequest request,
			Model Model
			){
		anabadaNotice.setN_Title(n_Title);
		anabadaNotice.setN_Writer(n_Writer);
		anabadaNotice.setN_Content(n_Content);
		
		AnabadaNoticeMapper anabadaMap = sqlSession.getMapper(AnabadaNoticeMapper.class);
		
		try {
			anabadaMap.insertAnabadaNotice(anabadaNotice);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:anabadaNoticeList";
	}
	
	/** 아나바다 게시판 리스트 출력 */
	@RequestMapping(value="/anabadaNoticeList", method=RequestMethod.GET)
	public String anabadaNoticeList(
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

		int srow = 1+(tempPage-1)*12;
		int erow = 12+(tempPage-1)*12;

		Map<String, Object> findMap = new HashMap<String, Object>();
		findMap.put("find", find);
		findMap.put("qtext", qtext);
		findMap.put("srow", srow);
		findMap.put("erow", erow);

		AnabadaNoticeMapper anabadaMap = sqlSession.getMapper(AnabadaNoticeMapper.class);
		List<AnabadaNotice> anabadalist=null;

		try {
			anabadalist = anabadaMap.selectAllAnabada(findMap);
			totalPages = anabadaMap.getCount(findMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<String> dateList_ymd = new ArrayList<String>();
		SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");		
		for(int i=0;i<anabadalist.size();i++){			
			dateList_ymd.add(i, ymd.format(anabadalist.get(i).getN_RegDate()));
		};				
		List<String> dateList_hm = new ArrayList<String>();
		SimpleDateFormat hm = new SimpleDateFormat("HH:mm");
		for(int i=0;i<anabadalist.size();i++){			
			dateList_hm.add(i, hm.format(anabadalist.get(i).getN_RegDate()));
		};		
		Date date = new Date();
		String today = ymd.format(date);

		if(totalPages%12 != 0)
			totalPages = totalPages/12+1;
		else
			totalPages = totalPages/12;

		request.setAttribute("find", find);
		request.setAttribute("qtext", qtext);
		request.setAttribute("dateList_ymd", dateList_ymd);
		request.setAttribute("dateList_hm", dateList_hm);
		request.setAttribute("today", today);
		request.setAttribute("currPage", tempPage);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("anabadalist", anabadalist);		

		return "anabadanotice/anabadaNoticeList";
	}
	
	/** 아나바다 게시판 글 보기 */
	@RequestMapping(value="/anabadaNoticeDetail", method=RequestMethod.GET)
	public String anabadaNoticeDetail(
			@RequestParam(value="n_Seq") String seq, 
			String currPage,
			Model model){
		AnabadaNoticeMapper anabadaMap = sqlSession.getMapper(AnabadaNoticeMapper.class);
		List<AnabadaNoticeComment> anabadaCommentList = null;
		int n_Seq = Integer.parseInt(seq);
		int grok=0;
		int nongrok=0;
		
		int tempPage = 0;
		if(currPage == null || currPage.equals(""))
			tempPage = 1;
		else{
			tempPage = Integer.parseInt(currPage);
			try {
				anabadaMap.minusHit(n_Seq);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		int totalPages = 1;
		int srow = 1+(tempPage-1)*4;
		int erow = 4+(tempPage-1)*4;
		
		Map<String, Integer> pageMap = new HashMap<String, Integer>();
		
		pageMap.put("n_Seq",n_Seq);
		pageMap.put("srow",srow);
		pageMap.put("erow",erow);
		
		try {
			grok = anabadaMap.grokCount(n_Seq);
			nongrok= anabadaMap.nongrokCount(n_Seq);
			anabadaMap.updateHit(n_Seq);
			anabadaNotice = anabadaMap.selectOneAnabada(n_Seq);
			anabadaCommentList = anabadaMap.selectAllAnabadaComment(pageMap);
			totalPages = anabadaMap.commentGetCount(n_Seq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(totalPages%4 != 0)
			totalPages = totalPages/4+1;
		else
			totalPages = totalPages/4;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateview = sdf.format(anabadaNotice.getN_RegDate());
		
		List<String> dateList_ymd = new ArrayList<String>();
		SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");		
		for(int i=0;i<anabadaCommentList.size();i++){			
			dateList_ymd.add(i, ymd.format(anabadaCommentList.get(i).getC_RegDate()));
		};				
		List<String> dateList_hm = new ArrayList<String>();
		SimpleDateFormat hm = new SimpleDateFormat("HH:mm");
		for(int i=0;i<anabadaCommentList.size();i++){			
			dateList_hm.add(i, hm.format(anabadaCommentList.get(i).getC_RegDate()));
		};		
		Date date = new Date();
		String today = ymd.format(date);
		
		
		model.addAttribute("dateList_ymd", dateList_ymd);
		model.addAttribute("dateList_hm", dateList_hm);
		model.addAttribute("today", today);
		model.addAttribute("grok", grok);
		model.addAttribute("nongrok", nongrok);
		model.addAttribute("dateview", dateview);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("anabada", anabadaNotice);		
		model.addAttribute("anabadaCommentList", anabadaCommentList);
		return "anabadanotice/anabadaNoticeDetail";
	}
	
	
	/** 아나바다 게시판 수정 뷰 & 수정완료 뷰 */
	@RequestMapping(value="/anabadaNoticeUpdateView", method=RequestMethod.GET)
	public String anabadaNoticeUpdateView(@RequestParam(value="n_Seq") String seq, Model model){
		AnabadaNoticeMapper anabadaMap = sqlSession.getMapper(AnabadaNoticeMapper.class);
		int n_Seq = Integer.parseInt(seq);
		try {
			anabadaNotice = anabadaMap.selectOneAnabada(n_Seq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("anabada", anabadaNotice);		
		return "anabadanotice/anabadaNoticeUpdateView";
	}
	
	/** 아나바다 게시판 수정 기능 */
	@RequestMapping(value="/anabadaNoticeUpdateProcess", method=RequestMethod.POST)
	public String anabadaNoticeNoticeUpdateProcess(
			@RequestParam(value="n_Seq") String seq,
			@RequestParam(value="n_Title") String n_Title,
			@RequestParam(value="n_Content") String n_Content,
			Model model
			){
		AnabadaNoticeMapper anabadaMap = sqlSession.getMapper(AnabadaNoticeMapper.class);
		int n_Seq = Integer.parseInt(seq);
		
		anabadaNotice.setN_Seq(n_Seq);
		anabadaNotice.setN_Title(n_Title);
		anabadaNotice.setN_Content(n_Content);
		
		try {
			anabadaMap.updateAnabada(anabadaNotice);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:anabadaNoticeDetail?n_Seq="+n_Seq;
	}
	
	/** 아나바다 게시판 글 삭제 */
	@RequestMapping(value="/anabadaNoticeDelete", method=RequestMethod.GET)
	public String anabadaNoticeDelete(
			@RequestParam(value="n_Seq") String n_seq
			){
		int n_Seq = Integer.parseInt(n_seq);
		int cnt = 0;
		AnabadaNoticeMapper anabadaMap = sqlSession.getMapper(AnabadaNoticeMapper.class);
		
		try {
			cnt = anabadaMap.commentGetCount(n_Seq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(cnt != 0){
			try {
				anabadaMap.deleteCommentByNotice(n_Seq);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			anabadaMap.deleteAnabada(n_Seq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:anabadaNoticeList";
	}
	
	/** 댓글 추가 */
	   @RequestMapping(value="/anabadaCommentRegProcess", method=RequestMethod.POST)
	   public void anabadaCommentRegProcess(
	         @RequestParam(value="n_Seq") String n_seq,
	         @RequestParam(value="c_Writer") String c_Writer,
	         @RequestParam(value="c_Content") String c_Content,
	         @RequestParam(value="c_Grok") String c_Grok,
	         HttpServletResponse response
	         ){
		  PrintWriter pw =null;
		   
	      int n_Seq = Integer.parseInt(n_seq);
	      anabadaNoticeComment.setN_Seq(n_Seq);
	      anabadaNoticeComment.setC_Writer(c_Writer);
	      anabadaNoticeComment.setC_Content(c_Content);
	      anabadaNoticeComment.setC_Grok(c_Grok);

	      AnabadaNoticeMapper anabadaMap = sqlSession.getMapper(AnabadaNoticeMapper.class);
	      Map<String, Object> grokMap = new HashMap<String, Object>();
	      grokMap.put("n_Seq", n_Seq);
	      grokMap.put("c_Grok", c_Grok);
	      try {
	    	  anabadaMap.minusHit(n_Seq);
	    	  anabadaMap.insertAnabadaComment(anabadaNoticeComment);
	    	  anabadaMap.updateGrok(grokMap);
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
	   
	   /** 댓글 삭제 */
	   @RequestMapping(value="/anabadaCommentDelete", method=RequestMethod.POST)
	   public void anabadaCommentDelete(
			   @RequestParam(value="n_Seq") String n_seq,
			   @RequestParam(value="c_Seq") String c_seq,
			   HttpServletResponse response
	         ){
		  PrintWriter pw =null;
		   
		  int n_Seq = Integer.parseInt(n_seq);
	      int c_Seq = Integer.parseInt(c_seq);

	      AnabadaNoticeMapper anabadaMap = sqlSession.getMapper(AnabadaNoticeMapper.class);
	      
	      try {
	    	  anabadaMap.minusHit(n_Seq);
	    	  anabadaMap.deleteAnabadaComment(c_Seq);
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
	   @RequestMapping(value="/anabadaCommentUpdate", method=RequestMethod.POST)
	   public void anabadaCommentUpdate(		
			   @RequestParam(value="n_Seq") String n_seq,
			   @RequestParam(value="c_Seq") String c_seq,
		       @RequestParam(value="c_Content") String c_Content,
		       @RequestParam(value="c_Grok") String c_Grok,
		       HttpServletResponse response
	         ){
		  PrintWriter pw =null;
		   
		  int n_Seq = Integer.parseInt(n_seq);
	      int c_Seq = Integer.parseInt(c_seq);
	      anabadaNoticeComment.setC_Seq(c_Seq);
	      anabadaNoticeComment.setC_Content(c_Content);
	      anabadaNoticeComment.setC_Grok(c_Grok);

	      AnabadaNoticeMapper anabadaMap = sqlSession.getMapper(AnabadaNoticeMapper.class);

	      try {
	    	  anabadaMap.minusHit(n_Seq);
	    	  anabadaMap.updateAnabadaComment(anabadaNoticeComment);
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
