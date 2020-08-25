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

import com.alone.threemeals.mapper.MyRoomNoticeMapper;
import com.alone.threemeals.notice.vo.MyRoomNotice;
import com.alone.threemeals.notice.vo.MyRoomNoticeComment;

@Controller
public class MyRoomNoticeController {
	@Autowired
	SqlSession sqlSession;
	@Autowired
	MyRoomNotice myRoomNotice;
	@Autowired
	MyRoomNoticeComment myRoomNoticeComment;

	/** 청소&인테리어 글쓰기 화면 */
	@RequestMapping(value="/myRoomNoticeRegView", method=RequestMethod.POST)
	public String myRoomNoticeRegView(){
		return "myroomnotice/myRoomNoticeRegView";
	}

	/** 청소&인테리어 글쓰기 기능 */
	@RequestMapping(value="/myRoomNoticeRegProcess", method=RequestMethod.POST)
	public String myRoomNoticeRegProcess(
			@RequestParam(value="n_Title") String n_Title,
			@RequestParam(value="n_Writer") String n_Writer,
			@RequestParam(value="n_Content") String n_Content,
			HttpServletRequest request,
			Model model
			){
		myRoomNotice.setN_Title(n_Title);
		myRoomNotice.setN_Writer(n_Writer);
		myRoomNotice.setN_Content(n_Content);

		MyRoomNoticeMapper myRoomMap = sqlSession.getMapper(MyRoomNoticeMapper.class);

		try {
			myRoomMap.insertMyRoomNotice(myRoomNotice);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "redirect:myRoomNoticeList";
	}

	/** 청소&인테리어 리스트 출력  */
	@RequestMapping(value="/myRoomNoticeList", method=RequestMethod.GET)
	public String myRoomNoticeList(
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

		MyRoomNoticeMapper myRoomMap = sqlSession.getMapper(MyRoomNoticeMapper.class);
		List<MyRoomNotice> myRoomlist=null;

		try {
			myRoomlist = myRoomMap.selectAllMyRoom(findMap);
			totalPages = myRoomMap.getCount(findMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<String> dateList_ymd = new ArrayList<String>();
		SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");		
		for(int i=0;i<myRoomlist.size();i++){			
			dateList_ymd.add(i, ymd.format(myRoomlist.get(i).getN_RegDate()));
		};				
		List<String> dateList_hm = new ArrayList<String>();
		SimpleDateFormat hm = new SimpleDateFormat("HH:mm");
		for(int i=0;i<myRoomlist.size();i++){			
			dateList_hm.add(i, hm.format(myRoomlist.get(i).getN_RegDate()));
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
		request.setAttribute("myRoomlist", myRoomlist);

		System.out.println(myRoomlist.size());

		return "myroomnotice/myRoomNoticeList";
	}

	/** 청소&인테리어  글 보기*/
	@RequestMapping(value="/myRoomNoticeDetail", method=RequestMethod.GET)
	public String myRoomNoticeDetail(
			@RequestParam(value="n_Seq") String seq, 
			String currPage,
			Model model){
		MyRoomNoticeMapper myRoomMap = sqlSession.getMapper(MyRoomNoticeMapper.class);
		List<MyRoomNoticeComment> myRoomCommentList = null;
		int n_Seq = Integer.parseInt(seq);
		int grok = 0;
		int nongrok = 0;
		
		int tempPage = 0;
		if(currPage == null || currPage.equals(""))
			tempPage = 1;
		else{
			tempPage = Integer.parseInt(currPage);
			try {
				myRoomMap.minusHit(n_Seq);
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
			grok = myRoomMap.grokCount(n_Seq);
			nongrok= myRoomMap.nongrokCount(n_Seq);
			myRoomMap.updateHit(n_Seq);
			myRoomNotice = myRoomMap.selectOneMyRoom(n_Seq);
			myRoomCommentList = myRoomMap.selectAllMyRoomComment(pageMap);
			totalPages = myRoomMap.commentGetCount(n_Seq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(totalPages%4 != 0)
			totalPages = totalPages/4+1;
		else
			totalPages = totalPages/4;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateview = sdf.format(myRoomNotice.getN_RegDate());
		
		List<String> dateList_ymd = new ArrayList<String>();
		SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");		
		for(int i=0;i<myRoomCommentList.size();i++){			
			dateList_ymd.add(i, ymd.format(myRoomCommentList.get(i).getC_RegDate()));
		};				
		List<String> dateList_hm = new ArrayList<String>();
		SimpleDateFormat hm = new SimpleDateFormat("HH:mm");
		for(int i=0;i<myRoomCommentList.size();i++){			
			dateList_hm.add(i, hm.format(myRoomCommentList.get(i).getC_RegDate()));
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
		model.addAttribute("myRoom", myRoomNotice);
		model.addAttribute("myRoomCommentList", myRoomCommentList);
		return "myroomnotice/myRoomNoticeDetail";
	}

	/** 청소&인테리어 글 수정 뷰 & 수정완료 뷰*/
	@RequestMapping(value="/myRoomNoticeUpdateView", method=RequestMethod.GET)
	public String myRoomNoticeUpdateView(@RequestParam(value="n_Seq") String seq, Model model){
		MyRoomNoticeMapper myRoomMap = sqlSession.getMapper(MyRoomNoticeMapper.class);
		int n_Seq = Integer.parseInt(seq);
		try {
			myRoomNotice = myRoomMap.selectOneMyRoom(n_Seq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("myRoom", myRoomNotice);		
		return "myroomnotice/myRoomNoticeUpdateView";
	}

	/** 청소&인테리어 글 수정 기능*/
	@RequestMapping(value="/myRoomNoticeUpdateProcess", method=RequestMethod.POST)
	public String myRoomNoticeUpdateProcess(
			@RequestParam(value="n_Seq") String seq,
			@RequestParam(value="n_Title") String n_Title,
			@RequestParam(value="n_Content") String n_Content,
			Model model
			){
		MyRoomNoticeMapper myRoomMap = sqlSession.getMapper(MyRoomNoticeMapper.class);
		int n_Seq = Integer.parseInt(seq);

		myRoomNotice.setN_Seq(n_Seq);
		myRoomNotice.setN_Title(n_Title);
		myRoomNotice.setN_Content(n_Content);

		try {
			myRoomMap.updateMyRoom(myRoomNotice);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:myRoomNoticeDetail?n_Seq="+n_Seq;
	}

	/** 청소&인테리어 게시판 글 삭제 */
	@RequestMapping(value="/myRoomNoticeDelete", method=RequestMethod.GET)
	public String myRoomNoticeDelete(
			@RequestParam(value="n_Seq") String n_seq
			){
		int n_Seq = Integer.parseInt(n_seq);
		int cnt = 0;
		MyRoomNoticeMapper myRoomMap = sqlSession.getMapper(MyRoomNoticeMapper.class);
		
		try {
			cnt = myRoomMap.commentGetCount(n_Seq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(cnt != 0){
			try {
				myRoomMap.deleteCommentByNotice(n_Seq);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		try {
			myRoomMap.deleteMyRoom(n_Seq);
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return "redirect:myRoomNoticeList";
	
	}


	/** 댓글 추가 */
	@RequestMapping(value="/myRoomCommentRegProcess", method=RequestMethod.POST)
	public void myRoomCommentRegProcess(
			@RequestParam(value="n_Seq") String n_seq,
			@RequestParam(value="c_Writer") String c_Writer,
			@RequestParam(value="c_Content") String c_Content,
			@RequestParam(value="c_Grok") String c_Grok,
			HttpServletResponse response
			){
		PrintWriter pw =null;

		int n_Seq = Integer.parseInt(n_seq);
		myRoomNoticeComment.setN_Seq(n_Seq);
		myRoomNoticeComment.setC_Writer(c_Writer);
		myRoomNoticeComment.setC_Content(c_Content);
		myRoomNoticeComment.setC_Grok(c_Grok);

		MyRoomNoticeMapper myRoomMap = sqlSession.getMapper(MyRoomNoticeMapper.class);
		Map<String, Object> grokMap = new HashMap<String, Object>();
	    grokMap.put("n_Seq", n_Seq);
	    grokMap.put("c_Grok", c_Grok);
	      
	      
		try {
			myRoomMap.minusHit(n_Seq);
			myRoomMap.insertMyRoomComment(myRoomNoticeComment);
			myRoomMap.updateGrok(grokMap);
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
	@RequestMapping(value="/myRoomCommentDelete", method=RequestMethod.POST)
	public void myRoomCommentDelete(
			@RequestParam(value="n_Seq") String n_seq,
			@RequestParam(value="c_Seq") String c_seq,
			HttpServletResponse response
			){
		PrintWriter pw =null;

		int n_Seq = Integer.parseInt(n_seq);
		int c_Seq = Integer.parseInt(c_seq);

		MyRoomNoticeMapper myRoomMap = sqlSession.getMapper(MyRoomNoticeMapper.class);

		try {
			myRoomMap.minusHit(n_Seq);
			myRoomMap.deleteMyRoomComment(c_Seq);
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
	@RequestMapping(value="/myRoomCommentUpdate", method=RequestMethod.POST)
	public void myRoomCommentUpdate(		
			@RequestParam(value="n_Seq") String n_seq,
			@RequestParam(value="c_Seq") String c_seq,
			@RequestParam(value="c_Content") String c_Content,
			@RequestParam(value="c_Grok") String c_Grok,
			HttpServletResponse response
			){
		PrintWriter pw =null;

		int n_Seq = Integer.parseInt(n_seq);
		int c_Seq = Integer.parseInt(c_seq);
		myRoomNoticeComment.setC_Seq(c_Seq);
		myRoomNoticeComment.setC_Content(c_Content);
		myRoomNoticeComment.setC_Grok(c_Grok);

		MyRoomNoticeMapper myRoomMap = sqlSession.getMapper(MyRoomNoticeMapper.class);

		try {
			myRoomMap.minusHit(n_Seq);
			myRoomMap.updateMyRoomComment(myRoomNoticeComment);
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
