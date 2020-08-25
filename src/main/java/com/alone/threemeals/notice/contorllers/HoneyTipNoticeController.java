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

import com.alone.threemeals.mapper.HoneyTipNoticeMapper;
import com.alone.threemeals.notice.vo.HoneyTipNotice;
import com.alone.threemeals.notice.vo.HoneyTipNoticeComment;

@Controller
public class HoneyTipNoticeController {
	@Autowired
	SqlSession sqlSession;
	@Autowired
	HoneyTipNotice honeyTipNotice;
	@Autowired
	HoneyTipNoticeComment honeyTipNoticeComment;

	/** 기타 꿀팁 게시판 글쓰기 화면 */
	@RequestMapping(value="/honeyTipNoticeRegView", method=RequestMethod.POST)
	public String honeyTipNoticeRegView(){
		return "honeytipnotice/honeyTipNoticeRegView";
	}

	/** 기타 꿀팁 게시판 글쓰기 기능*/
	@RequestMapping(value="/honeyTipNoticeRegProcess",method=RequestMethod.POST)
	public String honeyTipNoticeRegProcess(
			@RequestParam(value="n_Title") String n_Title,
			@RequestParam(value="n_Writer") String n_Writer,
			@RequestParam(value="n_Content") String n_Content,
			HttpServletRequest request,
			Model Model
			){
		honeyTipNotice.setN_Title(n_Title);
		honeyTipNotice.setN_Writer(n_Writer);
		honeyTipNotice.setN_Content(n_Content);

		HoneyTipNoticeMapper honeyMap = sqlSession.getMapper(HoneyTipNoticeMapper.class);
		try {
			honeyMap.insertHoneyTipNotice(honeyTipNotice);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:honeyTipNoticeList";
	}

	/** 기타 꿀팁 게시판 글 리스트 출력 */
	@RequestMapping(value="/honeyTipNoticeList", method=RequestMethod.GET)
	public String honeyTipNoticeList(
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

		HoneyTipNoticeMapper honeyMap = sqlSession.getMapper(HoneyTipNoticeMapper.class);
		List<HoneyTipNotice> honeyTiplist=null;

		try {
			honeyTiplist = honeyMap.selectAllHoneyTip(findMap);
			totalPages = honeyMap.getCount(findMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<String> dateList_ymd = new ArrayList<String>();
		SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");		
		for(int i=0;i<honeyTiplist.size();i++){			
			dateList_ymd.add(i, ymd.format(honeyTiplist.get(i).getN_RegDate()));
		};				
		List<String> dateList_hm = new ArrayList<String>();
		SimpleDateFormat hm = new SimpleDateFormat("HH:mm");
		for(int i=0;i<honeyTiplist.size();i++){			
			dateList_hm.add(i, hm.format(honeyTiplist.get(i).getN_RegDate()));
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
		request.setAttribute("honeyTiplist", honeyTiplist);		

		return "honeytipnotice/honeyTipNoticeList";
	}

	/** 기타 꿀팁 게시판 글보기 */
	@RequestMapping(value="/honeyTipNoticeDetail", method=RequestMethod.GET)
	public String honeyTipNoticeDetail(
			@RequestParam(value="n_Seq") String seq, 
			String currPage,
			Model model
			){
		HoneyTipNoticeMapper honeyMap = sqlSession.getMapper(HoneyTipNoticeMapper.class);
		List<HoneyTipNoticeComment> honeyTipCommentList = null;
		int n_Seq = Integer.parseInt(seq);
		int grok=0;
		int nongrok=0;
		
		int tempPage = 0;
		if(currPage == null || currPage.equals(""))
			tempPage = 1;
		else{
			tempPage = Integer.parseInt(currPage);
			try {
				honeyMap.minusHit(n_Seq);
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
			grok = honeyMap.grokCount(n_Seq);
			nongrok= honeyMap.nongrokCount(n_Seq);
			honeyMap.updateHit(n_Seq);
			honeyTipNotice = honeyMap.selectOneHoneyTip(n_Seq);
			honeyTipCommentList = honeyMap.selectAllHoneyTipComment(pageMap);
			totalPages = honeyMap.commentGetCount(n_Seq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(totalPages%4 != 0)
			totalPages = totalPages/4+1;
		else
			totalPages = totalPages/4;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateview = sdf.format(honeyTipNotice.getN_RegDate());
		
		List<String> dateList_ymd = new ArrayList<String>();
		SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");		
		for(int i=0;i<honeyTipCommentList.size();i++){			
			dateList_ymd.add(i, ymd.format(honeyTipCommentList.get(i).getC_RegDate()));
		};				
		List<String> dateList_hm = new ArrayList<String>();
		SimpleDateFormat hm = new SimpleDateFormat("HH:mm");
		for(int i=0;i<honeyTipCommentList.size();i++){			
			dateList_hm.add(i, hm.format(honeyTipCommentList.get(i).getC_RegDate()));
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
		model.addAttribute("honeyTip", honeyTipNotice);		
		model.addAttribute("honeyTipCommentList", honeyTipCommentList);
		return "honeytipnotice/honeyTipNoticeDetail";
	}

	/** 기타 꿀팁 수정 뷰 & 수정 완료 뷰*/
	@RequestMapping(value="/honeyTipNoticeUpdateView", method=RequestMethod.GET)
	public String honeyTipNoticeUpdateView(@RequestParam(value="n_Seq") String seq, Model model){
		HoneyTipNoticeMapper honeyMap = sqlSession.getMapper(HoneyTipNoticeMapper.class);
		int n_Seq = Integer.parseInt(seq);
		try {
			honeyTipNotice = honeyMap.selectOneHoneyTip(n_Seq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("honeyTip", honeyTipNotice);		
		return "honeytipnotice/honeyTipNoticeUpdateView";
	}

	/** 기타 꿀팁 수정 기능 */
	@RequestMapping(value="/honeyTipNoticeUpdateProcess", method=RequestMethod.POST)
	public String honeyTipNoticeUpdateProcess(
			@RequestParam(value="n_Seq") String seq,
			@RequestParam(value="n_Title") String n_Title,
			@RequestParam(value="n_Content") String n_Content,
			Model model
			){
		HoneyTipNoticeMapper honeyMap = sqlSession.getMapper(HoneyTipNoticeMapper.class);

		int n_Seq = Integer.parseInt(seq);

		honeyTipNotice.setN_Seq(n_Seq);
		honeyTipNotice.setN_Title(n_Title);
		honeyTipNotice.setN_Content(n_Content);

		try {
			honeyMap.updateHoneyTip(honeyTipNotice);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:honeyTipNoticeDetail?n_Seq="+n_Seq;

	}
	
	/** 꿀팁 게시판 삭제 */
	@RequestMapping(value="/honeyTipNoticeDelete", method=RequestMethod.GET)
	public String honeyTipNoticeDelete(
			@RequestParam(value="n_Seq") String n_seq
			){
		int n_Seq = Integer.parseInt(n_seq);
		int cnt = 0;
		HoneyTipNoticeMapper honeyMap = sqlSession.getMapper(HoneyTipNoticeMapper.class);
		try {
			cnt = honeyMap.commentGetCount(n_Seq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(cnt != 0){
			try {
				honeyMap.deleteCommentByNotice(n_Seq);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			honeyMap.deleteHoneyTip(n_Seq);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:honeyTipNoticeList";
		
	}


	/** 댓글 추가 */
	@RequestMapping(value="/honeyTipCommentRegProcess", method=RequestMethod.POST)
	public void honeyTipCommentRegProcess(
			@RequestParam(value="n_Seq") String n_seq,
			@RequestParam(value="c_Writer") String c_Writer,
			@RequestParam(value="c_Content") String c_Content,
			@RequestParam(value="c_Grok") String c_Grok,
			HttpServletResponse response
			){
		PrintWriter pw =null;

		int n_Seq = Integer.parseInt(n_seq);
		honeyTipNoticeComment.setN_Seq(n_Seq);
		honeyTipNoticeComment.setC_Writer(c_Writer);
		honeyTipNoticeComment.setC_Content(c_Content);
		honeyTipNoticeComment.setC_Grok(c_Grok);

		HoneyTipNoticeMapper honeyMap = sqlSession.getMapper(HoneyTipNoticeMapper.class);
		Map<String, Object> grokMap = new HashMap<String, Object>();
	    grokMap.put("n_Seq", n_Seq);
	    grokMap.put("c_Grok", c_Grok);

		try {
			honeyMap.minusHit(n_Seq);
			honeyMap.insertHoneyTipComment(honeyTipNoticeComment);
			honeyMap.updateGrok(grokMap);
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
	@RequestMapping(value="/honeyTipCommentDelete", method=RequestMethod.POST)
	public void honeyTipCommentDelete(
			@RequestParam(value="n_Seq") String n_seq,
			@RequestParam(value="c_Seq") String c_seq,
			HttpServletResponse response
			){
		PrintWriter pw =null;

		int n_Seq = Integer.parseInt(n_seq);
		int c_Seq = Integer.parseInt(c_seq);

		HoneyTipNoticeMapper honeyMap = sqlSession.getMapper(HoneyTipNoticeMapper.class);

		try {
			honeyMap.minusHit(n_Seq);
			honeyMap.deleteHoneyTipComment(c_Seq);
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
	@RequestMapping(value="/honeyTipCommentUpdate", method=RequestMethod.POST)
	public void honeyTipCommentUpdate(		
			@RequestParam(value="n_Seq") String n_seq,
			@RequestParam(value="c_Seq") String c_seq,
			@RequestParam(value="c_Content") String c_Content,
			@RequestParam(value="c_Grok") String c_Grok,
			HttpServletResponse response
			){
		PrintWriter pw =null;

		int n_Seq = Integer.parseInt(n_seq);
		int c_Seq = Integer.parseInt(c_seq);
		honeyTipNoticeComment.setC_Seq(c_Seq);
		honeyTipNoticeComment.setC_Content(c_Content);
		honeyTipNoticeComment.setC_Grok(c_Grok);

		HoneyTipNoticeMapper honeyMap = sqlSession.getMapper(HoneyTipNoticeMapper.class);

		try {
			honeyMap.minusHit(n_Seq);
			honeyMap.updateHoneyTipComment(honeyTipNoticeComment);
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
