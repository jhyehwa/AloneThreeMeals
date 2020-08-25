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

import com.alone.threemeals.mapper.NecessityNoticeMapper;
import com.alone.threemeals.notice.vo.NecessityNotice;
import com.alone.threemeals.notice.vo.NecessityNoticeComment;

@Controller
public class NecessityNoticeController {
	@Autowired
	SqlSession sqlSession;
	@Autowired
	NecessityNotice necessityNotice;
	@Autowired
	NecessityNoticeComment necessityNoticeComment;
	
	/** 생활 필수품 글쓰기 화면 */
	@RequestMapping(value="/necessityNoticeRegView", method=RequestMethod.POST)
	public String necessityNoticeRegView(){
		return "necessitynotice/necessityNoticeRegView";
	}
	
	/** 생활 필수품 글쓰기 기능 */
	@RequestMapping(value="/necessityNoticeRegProcess", method=RequestMethod.POST)
	public String necessityNoticeRegProcess(
			@RequestParam(value="n_Title") String n_Title,
			@RequestParam(value="n_Writer") String n_Writer,
			@RequestParam(value="n_Content") String n_Content,
			HttpServletRequest request,
			Model model
			){
		necessityNotice.setN_Title(n_Title);
		necessityNotice.setN_Writer(n_Writer);
		necessityNotice.setN_Content(n_Content);
		
		NecessityNoticeMapper necessityMap = sqlSession.getMapper(NecessityNoticeMapper.class);
		
		try {
			necessityMap.insertNecessityNotice(necessityNotice);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:necessityNoticeList";
	}
	
	/** 생활 필수품 게시판 리스트 */
	@RequestMapping(value="/necessityNoticeList", method=RequestMethod.GET)
	public String necessityNoticeList(
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

		NecessityNoticeMapper necessityMap = sqlSession.getMapper(NecessityNoticeMapper.class);
		List<NecessityNotice> necessitylist=null;

		try {
			necessitylist = necessityMap.selectAllNecessity(findMap);
			totalPages = necessityMap.getCount(findMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<String> dateList_ymd = new ArrayList<String>();
		SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");		
		for(int i=0;i<necessitylist.size();i++){			
			dateList_ymd.add(i, ymd.format(necessitylist.get(i).getN_RegDate()));
		};				
		List<String> dateList_hm = new ArrayList<String>();
		SimpleDateFormat hm = new SimpleDateFormat("HH:mm");
		for(int i=0;i<necessitylist.size();i++){			
			dateList_hm.add(i, hm.format(necessitylist.get(i).getN_RegDate()));
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
		request.setAttribute("necessitylist", necessitylist);

		System.out.println(necessitylist.size());

		return "necessitynotice/necessityNoticeList";
	}
	
	/** 생활 필수품 게시판 글 보기*/
	@RequestMapping(value="/necessityNoticeDetail", method=RequestMethod.GET)
	public String necessityNoticeDetail(
			@RequestParam(value="n_Seq") String seq, 
			String currPage,
			Model model){
		NecessityNoticeMapper necessityMap = sqlSession.getMapper(NecessityNoticeMapper.class);
		List<NecessityNoticeComment> necessityCommentList = null;
		int n_Seq = Integer.parseInt(seq);
		int grok=0;
		int nongrok=0;
		
		int tempPage = 0;
		if(currPage == null || currPage.equals(""))
			tempPage = 1;
		else{
			tempPage = Integer.parseInt(currPage);
			try {
				necessityMap.minusHit(n_Seq);
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
			grok = necessityMap.grokCount(n_Seq);
			nongrok= necessityMap.nongrokCount(n_Seq);
			necessityMap.updateHit(n_Seq);
			necessityNotice = necessityMap.selectOneNecessity(n_Seq);
			necessityCommentList = necessityMap.selectAllNecessityComment(pageMap);
			totalPages = necessityMap.commentGetCount(n_Seq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(totalPages%4 != 0)
			totalPages = totalPages/4+1;
		else
			totalPages = totalPages/4;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateview = sdf.format(necessityNotice.getN_RegDate());
		
		List<String> dateList_ymd = new ArrayList<String>();
		SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");		
		for(int i=0;i<necessityCommentList.size();i++){			
			dateList_ymd.add(i, ymd.format(necessityCommentList.get(i).getC_RegDate()));
		};				
		List<String> dateList_hm = new ArrayList<String>();
		SimpleDateFormat hm = new SimpleDateFormat("HH:mm");
		for(int i=0;i<necessityCommentList.size();i++){			
			dateList_hm.add(i, hm.format(necessityCommentList.get(i).getC_RegDate()));
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
		model.addAttribute("necessity", necessityNotice);
		model.addAttribute("necessityCommentList", necessityCommentList);
		return "necessitynotice/necessityNoticeDetail";
	}
	
	/** 생활 필수품 게시판 글 수정 뷰 & 수정 완료 뷰*/
	@RequestMapping(value="/necessityNoticeUpdateView", method=RequestMethod.GET)
	public String necessityNoticeUpdateView(@RequestParam(value="n_Seq") String seq, Model model){
		NecessityNoticeMapper necessityMap = sqlSession.getMapper(NecessityNoticeMapper.class);
		int n_Seq = Integer.parseInt(seq);
		try {
			necessityNotice = necessityMap.selectOneNecessity(n_Seq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("necessity", necessityNotice);		
		return "necessitynotice/necessityNoticeUpdateView";
	}
	
	/** 생활 필수품 게시판 수정 기능*/
	@RequestMapping(value="/necessityNoticeUpdateProcess", method=RequestMethod.POST)
	public String necessityNoticeUpdateProcess(
			@RequestParam(value="n_Seq") String seq,
			@RequestParam(value="n_Title") String n_Title,
			@RequestParam(value="n_Content") String n_Content,
			Model model
			){
		NecessityNoticeMapper necessityMap = sqlSession.getMapper(NecessityNoticeMapper.class);
		int n_Seq = Integer.parseInt(seq);
		necessityNotice.setN_Seq(n_Seq);
		necessityNotice.setN_Title(n_Title);
		necessityNotice.setN_Content(n_Content);
		try {
			necessityMap.updateNecessity(necessityNotice);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:necessityNoticeDetail?n_Seq="+n_Seq;	
		
	}
	
	/** 생활 필수품 게시글 삭제 */
	@RequestMapping(value="/necessityNoticeDelete", method=RequestMethod.GET)
	public String necessityNoticeDelete(
			@RequestParam(value="n_Seq") String n_seq
			){
		int n_Seq = Integer.parseInt(n_seq);
		int cnt = 0;
		NecessityNoticeMapper necessityMap = sqlSession.getMapper(NecessityNoticeMapper.class);
		
		try {
			cnt = necessityMap.commentGetCount(n_Seq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(cnt != 0){
			try {
				necessityMap.deleteCommentByNotice(n_Seq);
			} catch (Exception e) {
				e.printStackTrace();
			}		
			
		}
		
		try {
			necessityMap.deleteNecessity(n_Seq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:necessityNoticeList";
	}
		
	
	
	
	/** 댓글 추가 */
	   @RequestMapping(value="/necessityCommentRegProcess", method=RequestMethod.POST)
	   public void necessityCommentRegProcess(
	         @RequestParam(value="n_Seq") String n_seq,
	         @RequestParam(value="c_Writer") String c_Writer,
	         @RequestParam(value="c_Content") String c_Content,
	         @RequestParam(value="c_Grok") String c_Grok,
	         HttpServletResponse response
	         ){
		  PrintWriter pw =null;
		   
	      int n_Seq = Integer.parseInt(n_seq);
	      necessityNoticeComment.setN_Seq(n_Seq);
	      necessityNoticeComment.setC_Writer(c_Writer);
	      necessityNoticeComment.setC_Content(c_Content);
	      necessityNoticeComment.setC_Grok(c_Grok);

	      NecessityNoticeMapper necessityMap = sqlSession.getMapper(NecessityNoticeMapper.class);
	      Map<String, Object> grokMap = new HashMap<String, Object>();
	      grokMap.put("n_Seq", n_Seq);
	      grokMap.put("c_Grok", c_Grok);
	      
	      try {
	    	  necessityMap.minusHit(n_Seq);
	    	  necessityMap.insertNecessityComment(necessityNoticeComment);
	    	  necessityMap.updateGrok(grokMap);
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
	   @RequestMapping(value="/necessityCommentDelete", method=RequestMethod.POST)
	   public void necessityCommentDelete(
			   @RequestParam(value="n_Seq") String n_seq,
			   @RequestParam(value="c_Seq") String c_seq,
			   HttpServletResponse response
	         ){
		  PrintWriter pw =null;
		   
		  int n_Seq = Integer.parseInt(n_seq);
	      int c_Seq = Integer.parseInt(c_seq);

	      NecessityNoticeMapper necessityMap = sqlSession.getMapper(NecessityNoticeMapper.class);

	      try {
	    	  necessityMap.minusHit(n_Seq);
	    	  necessityMap.deleteNecessityComment(c_Seq);
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
	   @RequestMapping(value="/necessityCommentUpdate", method=RequestMethod.POST)
	   public void necessityCommentUpdate(		
			   @RequestParam(value="n_Seq") String n_seq,
			   @RequestParam(value="c_Seq") String c_seq,
		       @RequestParam(value="c_Content") String c_Content,
		       @RequestParam(value="c_Grok") String c_Grok,
		       HttpServletResponse response
	         ){
		  PrintWriter pw =null;
		   
		  int n_Seq = Integer.parseInt(n_seq);
	      int c_Seq = Integer.parseInt(c_seq);
	      necessityNoticeComment.setC_Seq(c_Seq);
	      necessityNoticeComment.setC_Content(c_Content);
	      necessityNoticeComment.setC_Grok(c_Grok);

	      NecessityNoticeMapper necessityMap = sqlSession.getMapper(NecessityNoticeMapper.class);

	      try {
	    	  necessityMap.minusHit(n_Seq);
	    	  necessityMap.updateNecessityComment(necessityNoticeComment);
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
