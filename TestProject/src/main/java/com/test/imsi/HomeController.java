package com.test.imsi;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home1(ModelAndView mv) {
		System.out.println("16");
		
		List <ProductVO> list = ProductDAO.mainProductList("상의");
		
		mv.addObject("mlist", list);
		mv.setViewName("User_Main");
		
		
		return mv;
	}
	
	@RequestMapping(value = "/goaboutcompany" , method = RequestMethod.GET)
	public ModelAndView goaboutcompany(ModelAndView mv,HttpServletRequest req)
	{
		mv.setViewName("about_company");
		return mv;
	}
	//회사 소개
	
	@RequestMapping(value = "/gohome" , method = RequestMethod.GET)
	   public String gohome(ModelAndView mv,HttpServletRequest req)
	   {   
	      String user = req.getParameter("user");
	      HttpSession hs = req.getSession();
	      
	      if(user!=null)
	         hs.setAttribute("user", user);
	      
	      return "redirect:/";
	   }   
	   
	   @RequestMapping(value = "/logout" , method = RequestMethod.GET)
	   public String logout(Locale locale, HttpServletRequest req)
	   {   
	      HttpSession hs = req.getSession();
	      hs.invalidate();
	      
	      return "redirect:/";
	   }
	   
	   @RequestMapping(value = "/mypage", method = RequestMethod.GET)
	   public String mypage(Locale locale, HttpServletRequest req) {
	      
	      String user = req.getParameter("user");
	      HttpSession hs = req.getSession();
	      
	      if(user!=null)
	         hs.setAttribute("user", user);
	      
	      return "mypage";
	   }
	   
	   @RequestMapping(value = "/myPoint", method = RequestMethod.GET)
	   public String myPoint(Locale locale, HttpServletRequest req) {
	      
	      String user = req.getParameter("user");
	      HttpSession hs = req.getSession();
	      
	      if(user!=null)
	         hs.setAttribute("user", user);
	      
	      return "myPoint";
	   }
	   
	   @RequestMapping(value = "/myQnA", method = RequestMethod.GET)
	   public String myQnA(Locale locale, HttpServletRequest req) {
	      
	      String user = req.getParameter("user");
	      HttpSession hs = req.getSession();
	      
	      if(user!=null)
	         hs.setAttribute("user", user);
	      
	      return "myQnA";
	   }
	   
	   @RequestMapping(value = "/myOrder", method = RequestMethod.GET)
	   public String myOrder(Locale locale, HttpServletRequest req) {
	      
	      String user = req.getParameter("user");
	      HttpSession hs = req.getSession();
	      
	      if(user!=null)
	         hs.setAttribute("user", user);
	      
	      return "myOrder";
	   }
	   
	   @RequestMapping(value = "/myreview", method = RequestMethod.GET)
	   public String myreview(Locale locale, HttpServletRequest req) {
	      
	      String user = req.getParameter("user");
	      HttpSession hs = req.getSession();
	      
	      if(user!=null)
	         hs.setAttribute("user", user);
	      
	      return "myreview";
	   }
	
	//-----------------------------------장바구니---------------------------------------
    @RequestMapping(value = "/cartinsert" , produces = "application/json")
    public @ResponseBody Map<String,Object> cartinsert(@RequestBody Map<String, Object> params , HttpServletRequest req)
    {            
       String productsubject = (String) params.get("productsubject");
       String wansize = (String) params.get("wansize");
       String wancolor = (String) params.get("wancolor");
       int amount = (int) params.get("amount");
       
       ProductVO pvo = new ProductVO();
       pvo.setProductsubject(productsubject);
       pvo.setProductsize(wansize);
       pvo.setProductcolor(wancolor);
       
       String productcode = ProductDAO.productMapping(pvo);
       
       int temp = CartDAO.cartMaxNum();
       int maxnum = temp+1;
       
       HttpSession hs = req.getSession();   
       String user = (String) hs.getAttribute("loginid");
       
       CartVO cvo = new CartVO(maxnum,user,productcode,amount);
       
       CartDAO.cartInsert(cvo);
       
       Map<String , Object> map1 = new HashMap<String , Object>();
       
     return map1;
    }
	
	
	//--------------------------------------------관리자 게시판--------------------------------------------------------
	
	@RequestMapping(value = "/goadminpage" , method = RequestMethod.GET)
	public ModelAndView goadminpage(ModelAndView mv,HttpServletRequest req)
	{
		mv.setViewName("admin_Main");
		return mv;
	}
	//(임시)관리자 페이지
	
	
	//--------------------------------------로그인/회원가입-----------------------------------

	@RequestMapping(value = "/check_member" , method = RequestMethod.GET)
	public ModelAndView checkmemeber(ModelAndView mv,HttpServletRequest req)
	{
		mv.setViewName("check_member");
		return mv;
	}
	//회원 확인 창으로
	
	@RequestMapping(value = "/loginwai", method = RequestMethod.GET)
	public String home(Model model) {
		
		MemberDAO dao = new MemberDAO();
		String aa = dao.hello();
		
		model.addAttribute("maa", aa);
		
		return "login";	
	}
	
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String hello(Model model) {
		
		return "login";	
	}
	
	
	
	 @RequestMapping(value = "/memberAllData", method = RequestMethod.GET)
	public ModelAndView home(ModelAndView mv) {
		
		MemberDAO dao = new MemberDAO();
		List <MemberVO> list = dao.memberAllDate();
		
		mv.addObject("mlist", list);
		mv.setViewName("memberlist");
		
		return mv;	
	} 
	 
	 
	 @RequestMapping(value = "/login", method = RequestMethod.POST)
		public ModelAndView login(HttpServletRequest req, ModelAndView mv) {
			
			String id = req.getParameter("id");
			String pw = req.getParameter("pw");
			
			System.out.println(id);
			System.out.println(pw);
			
			MemberVO vo = new MemberVO();
			vo.setId(id);
			vo.setPw(pw);	
			
			 if (MemberDAO.login(vo)!=null) 
		      {      
		         vo = MemberDAO.login(vo);
		         mv.addObject("mvo", vo);
		         HttpSession Session = req.getSession(true);
		         Session.setAttribute("loginid",id);
		         
		         if (vo.getAdmin().equals("0")) 
		         {   
		            mv.setViewName("loginview");
		            Session.setAttribute("admin", 0);
		         } 
		         else 
		         {
		            mv.setViewName("hello");
		            Session.setAttribute("admin", 1);
		         }   
		      }
		      else 
		      {
		         mv.setViewName("login");
		      }
			
			return mv;
			
		}

	 
	 
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
		public String logout(HttpServletRequest req) {
			
			 HttpSession Session = req.getSession(true);
			 Session.setAttribute("login", "");
			
			return "login";
	}
	 
	 
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(HttpServletRequest req, ModelAndView mv) throws UnsupportedEncodingException {

			String id = req.getParameter("id");
			String pw = req.getParameter("pw");
			String hp1 = req.getParameter("hp1");
			String hp2 = req.getParameter("hp2");
			String hp3 = req.getParameter("hp3");
			String ad1 = req.getParameter("ad1");
			String ad2 = req.getParameter("ad2");
			String ad3 = req.getParameter("ad3");
			String email = req.getParameter("email");
		
		      ad2 = new String(ad2.getBytes("8859_1"),"utf-8");
		      ad3 = new String(ad3.getBytes("8859_1"),"utf-8");
			

		System.out.println(pw);
		
		MemberVO vo = new MemberVO();
		vo.setId(id);
		vo.setPw(pw);
		vo.setHp1(hp1);
		vo.setHp2(hp2);
		vo.setHp3(hp3);
		vo.setAd1(ad1);
		vo.setAd2(ad2);
		vo.setAd3(ad3);
		vo.setEmail(email);
		
		MemberDAO.update(vo);			
		mv.setViewName("login");
				
		return mv;
		
	} 

	 
	 
	 
	 @RequestMapping(value = "/idchk" , method = RequestMethod.POST , produces="application/json")
	    public @ResponseBody Map<String,Object> idchk(@RequestBody Map<String, Object> params) {
		 
		 String id = (String) params.get("id");

		 System.out.println(id);
		 
		 Map <String,Object> map1 = new HashMap <String,Object>();
		 int rs = MemberDAO.idchk(id);
		 
		 map1.put("rs", rs);
		 
	     return  map1;
	    }
	 

	 @RequestMapping(value = "/admin" , method = RequestMethod.POST, produces="application/json")
	    public 	@ResponseBody Map<String,Object> admin(@RequestBody Map<String, Object> params) {

		 String id = (String) params.get("userid");
		 String admin = (String) params.get("useradmin");
		 
		 System.out.println(id);
		 System.out.println(admin);
		 
		 Map <String,Object> map1 = new HashMap <String,Object>();
		 
		 if(id.equals("adm")) {
			 int rs = 2;
			 map1.put("rs", rs);
		 } else if(admin.equals("0")) {
			 int rs = MemberDAO.adminset(id);
			 map1.put("rs", rs);
		 } else if (admin.equals("1")){
			 int rs = MemberDAO.admindel(id);
			 map1.put("rs", rs);
		 }
			return map1;
		 
	    }
	 
	 
		@RequestMapping(value = "/chkmem", method = RequestMethod.POST)
		public String chkmem() {
			
			return "check_member";
		}

			
	 
		 @RequestMapping(value = "/chkrnum", method = RequestMethod.POST, produces="application/json")
			public @ResponseBody Map<String,Object> chkrnum(@RequestBody Map<String, Object> params) {
				
			 String name = (String) params.get("name");
			 String r_num1 = (String) params.get("rnum1");
			 String r_num2 = (String) params.get("rnum2");
			 
				System.out.println("주민번호 확인");
				 	
				System.out.println(r_num1);
				System.out.println(r_num2);
				
				MemberVO vo = new MemberVO();
				vo.setName(name);
				vo.setR_num1(r_num1);
				vo.setR_num2(r_num2);			
				
				Map <String,Object> map1 = new HashMap <String,Object>();
				 
				if (MemberDAO.chkrnum(vo)!=null) {
					 
				
					int rs = 1;
					map1.put("rs", rs);
					
						
				} else {
					
					int rs = 2;
					map1.put("rs", rs);

				
				 }
				
				return map1;
				
			}
	 
		 
			@RequestMapping(value = "/gologin", method = RequestMethod.GET)
			public String gologin() {
				
				return "login";
			}
			
			
			@RequestMapping(value = "/goshop", method = RequestMethod.GET)
			public String goshop() {
				
				return "User_Main";
			}
			//내 정보 보기에서 쇼핑하러 가기
			 
			 
			 

			 @RequestMapping(value = "/gosignup", method = RequestMethod.POST)
			public ModelAndView gosignup(HttpServletRequest req, ModelAndView mv) throws UnsupportedEncodingException {
						
						String name = req.getParameter("name");
						String r_num1 = req.getParameter("rnum1");
						String r_num2 = req.getParameter("rnum2");
							
						  name = new String(name.getBytes("8859_1"),"utf-8");
						 	
							System.out.println(r_num1);
							System.out.println(r_num2);
						
							mv.addObject("name",  name);
							mv.addObject("r_num1",  r_num1);
							mv.addObject("r_num2",  r_num2);
							 
							 SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy/MM/dd");
							 Date date = new Date();
							 String time = format1.format(date);
							 
							 mv.addObject("m_date", time);
							
							 
							 if(req.getParameter("rnum2").substring(0,1).equals("1") || req.getParameter("rnum2").substring(0,1).equals("3")) {
								 
								 mv.addObject("gender", "male");
								 
							 } else {mv.addObject("gender", "female");}
							 
							mv.setViewName("signup");		
						
						return mv;
						
					} 
			 
			 @RequestMapping(value = "/signup", method = RequestMethod.POST)
			   public ModelAndView member(HttpServletRequest req, ModelAndView mv) throws UnsupportedEncodingException {
			      
			      String name = req.getParameter("name");
			      String id = req.getParameter("id");
			      String pw = req.getParameter("pw");
			      String hp1 = req.getParameter("hp1");
			      String hp2 = req.getParameter("hp2");
			      String hp3 = req.getParameter("hp3");
			      String ad1 = req.getParameter("ad1");
			      String ad2 = req.getParameter("ad2");
			      String ad3 = req.getParameter("ad3");   
			      System.out.println("ad3이 뭐냐1"+ad3);
			      String email = req.getParameter("email");
			      String r_num1 = req.getParameter("r_num1");
			      String r_num2 = req.getParameter("r_num2");
			      String s_date = req.getParameter("m_date");
			      
			      int year = Integer.parseInt(s_date.substring(0,4));
			      int month = Integer.parseInt(s_date.substring(5,7));
			      int day = Integer.parseInt(s_date.substring(8,10));
			      
			      Date m_date = new Date();
			      m_date.setYear(year);
			      m_date.setMonth(month);
			      m_date.setDate(day);
			      
			      String gender = req.getParameter("gender");
			      String admin = req.getParameter("admin");
			      String grade = req.getParameter("grade");
			      
			      name = new String(name.getBytes("8859_1"),"utf-8");
			      ad2 = new String(ad2.getBytes("8859_1"),"utf-8");
			      ad3 = new String(ad3.getBytes("8859_1"),"utf-8");
			      System.out.println("ad3이 뭐냐2"+ad3);
			      gender = new String(gender.getBytes("8859_1"),"utf-8");
			      grade = new String(grade.getBytes("8859_1"),"utf-8");
			      
			      System.out.println(id);
			      System.out.println(pw);
			      
			      MemberVO vo = new MemberVO();
			      vo.setName(name);
			      vo.setId(id);
			      vo.setPw(pw);
			      vo.setHp1(hp1);
			      vo.setHp2(hp2);
			      vo.setHp3(hp3);
			      vo.setAd1(ad1);
			      vo.setAd2(ad2);
			      vo.setAd3(ad3);
			      System.out.println("ad3이 뭐냐3"+vo.getAd3());
			      vo.setEmail(email);
			      vo.setR_num1(r_num1);
			      vo.setR_num2(r_num2);
			      vo.setM_date(m_date);
			      vo.setGender(gender);
			      vo.setAdmin(admin);
			      vo.setGrade(grade);
			      
			      MemberDAO.signup(vo);
			       
			      mv.setViewName("login");
			      
			      return mv;
			      
			   }
	//----------------------------Q&A게시판-------------------------------------
	 @RequestMapping(value = "/goqnaboard" , method = RequestMethod.GET)
		public ModelAndView qnapaging(ModelAndView mv , HttpServletRequest req)
		{
			int page = 1;
			
			String productcode = req.getParameter("productcode");
			
			if(req.getParameter("page")!=null)
			{
				page = Integer.parseInt(req.getParameter("page"));
			}
			
			int totalnum = QnaboardDAO.qnaboardNum(productcode);
			
			int totalnum2 = ((totalnum-1)/10)+1;
			
			Map <String,Object> map1 = new HashMap <String,Object>();
			map1.put("page", page);
			map1.put("totalnum", totalnum);
			map1.put("productcode", productcode);
			
			List <QnaboardVO> list1 = QnaboardDAO.qnaboardPaging(map1);
			
			System.out.println("아니면 여긴가");
			
			mv.addObject("list1",list1);
			mv.setViewName("qnaboard");
			mv.addObject("totalnum2" , totalnum2);
			mv.addObject("productcode",productcode);
			
			mv.addObject("page",page);
			
			String user = req.getParameter("user");
			
			HttpSession hs = req.getSession();
			
			if(user!=null)
				hs.setAttribute("user", user);
			
			return mv;
		}
		
		@RequestMapping(value = "/goqnaboard1" , method = RequestMethod.GET)
		public ModelAndView qnapaging1(ModelAndView mv , HttpServletRequest req)
		{
			int page = 1;
			
			if(req.getParameter("page")!=null)
			{
				page = Integer.parseInt(req.getParameter("page"));
			}
			
			int totalnum = QnaboardDAO.qnaboardNum1();
			
			int totalnum2 = ((totalnum-1)/10)+1;
			
			Map <String,Object> map1 = new HashMap <String,Object>();
			map1.put("page", page);
			map1.put("totalnum", totalnum);
			
			List <QnaboardVO> list1 = QnaboardDAO.qnaboardPaging1(map1);
			
			System.out.println("아니면 여긴가");
			
			mv.addObject("list1",list1);
			mv.setViewName("qnaboard1");
			mv.addObject("totalnum2" , totalnum2);
			
			mv.addObject("page",page);
			
			String user = req.getParameter("user");
			
			HttpSession hs = req.getSession();
			
			if(user!=null)
				hs.setAttribute("user", user);
			
			return mv;
		}
		
		@RequestMapping(value = "/qnapage" , produces = "application/json")
		public @ResponseBody Map<String,Object> qnapage(@RequestBody Map<String, Object> params , HttpServletRequest req)
		{
			int page =(Integer) params.get("page");
			String productcode = (String) params.get("productcode");
			
			int totalnum = QnaboardDAO.qnaboardNum(productcode);
			
			Map <String,Object> map2 = new HashMap <String,Object>();
			map2.put("page", page);
			map2.put("totalnum", totalnum);
			map2.put("productcode", productcode);
			
			List <QnaboardVO> list1 = QnaboardDAO.qnaboardPaging(map2);
			
			Map <String,Object> map1 = new HashMap <String,Object>();
			
			map1.put("list2" , list1);
			
			map1.put("page", page);
			
			map1.put("productcode", productcode);
			
			System.out.println("--------------");

			return map1;
		}
		
		@RequestMapping(value = "/qnapage1" , produces = "application/json")
		public @ResponseBody Map<String,Object> qnapage1(@RequestBody Map<String, Object> params , HttpServletRequest req)
		{
			int page =(Integer) params.get("page");
			
			int totalnum = QnaboardDAO.qnaboardNum1();
			
			Map <String,Object> map2 = new HashMap <String,Object>();
			map2.put("page", page);
			map2.put("totalnum", totalnum);
			
			List <QnaboardVO> list1 = QnaboardDAO.qnaboardPaging1(map2);
			
			Map <String,Object> map1 = new HashMap <String,Object>();
			
			map1.put("list2" , list1);
			
			map1.put("page", page);
			
			System.out.println("--------------");

			return map1;
		}
		
		@RequestMapping(value = "/contents1" , produces = "application/json")
		public @ResponseBody Map<String,Object> contents(@RequestBody Map<String, Object> params , HttpServletRequest req)
		{
			int number =(Integer) params.get("number");
			int page =(Integer) params.get("page");
			String productcode = (String) params.get("productcode");
			
			System.out.println("상품 코드는 : " + productcode);
			
			QnaboardVO qvo = QnaboardDAO.qnaboardSearchData(number);
			
			int totalnum = QnaboardDAO.qnaboardNum(productcode);
			
			Map <String,Object> map2 = new HashMap <String,Object>();
			map2.put("page", page);
			map2.put("totalnum", totalnum);
			map2.put("productcode", productcode);
			
			List <QnaboardVO> list1 = QnaboardDAO.qnaboardPaging(map2);
			
			Map <String,Object> map1 = new HashMap <String,Object>();
			
			map1.put("number1", number);
			
			map1.put("content1", qvo.getQnacontents());
			
			map1.put("list2" , list1);
			
			map1.put("procode", qvo.getProductcode());
			
			map1.put("qnaanswercontent", qvo.getQnaanswercontents());
			
			map1.put("page", page);
			
			map1.put("productcode", productcode);
			
			System.out.println("--------------");

			return map1;
		}
		
		@RequestMapping(value = "/contents2" , produces = "application/json")
		public @ResponseBody Map<String,Object> contents2(@RequestBody Map<String, Object> params , HttpServletRequest req)
		{
			int number =(Integer) params.get("number");
			int page =(Integer) params.get("page");
			
			QnaboardVO qvo = QnaboardDAO.qnaboardSearchData(number);
			
			int totalnum = QnaboardDAO.qnaboardNum1();
			
			Map <String,Object> map2 = new HashMap <String,Object>();
			map2.put("page", page);
			map2.put("totalnum", totalnum);
			
			List <QnaboardVO> list1 = QnaboardDAO.qnaboardPaging1(map2);
			
			Map <String,Object> map1 = new HashMap <String,Object>();
			
			map1.put("number1", number);
			
			map1.put("content1", qvo.getQnacontents());
			
			map1.put("list2" , list1);
			
			map1.put("procode", qvo.getProductcode());
			
			map1.put("qnaanswercontent", qvo.getQnaanswercontents());
			
			map1.put("page", page);
			
			System.out.println("--------------");

			return map1;
		}
		
		@RequestMapping(value = "/goqnainsert" , method = RequestMethod.GET)
		public ModelAndView goqnainsert(ModelAndView mv,HttpServletRequest req)
		{
			String productcode = req.getParameter("productcode");
			mv.addObject("productcode",productcode);
			mv.setViewName("qnainsert");
			return mv;
		}
		
		@RequestMapping(value = "/qnains" , method = RequestMethod.GET)
		public String qnainsert(HttpServletRequest req)
		{
			String qnakind = req.getParameter("kind");
			String qnasubject = req.getParameter("qnasubject");
			String qnacontents = req.getParameter("qnacontents");
			String productcode = req.getParameter("productcode");
			
			int qnalock;
			
			if(req.getParameter("qnalock") == null)
				qnalock = 0;
			else
				qnalock = 1;
			
			System.out.println("사이즈 알기 전");
			
			int temp;
			temp = QnaboardDAO.qnaboardMaxNum();
			
			System.out.println("사이즈 안 후");
			int maxnum = temp+1;
			
			Date date = new Date();
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
			String todate = transFormat.format(date);
			
			HttpSession hs = req.getSession();	
			String user = (String) hs.getAttribute("user");
			
			QnaboardVO qvo = new QnaboardVO(maxnum,qnakind,qnasubject,qnacontents,todate,qnalock,0,user,productcode,"");
			
			System.out.println("인서트전");
			
			QnaboardDAO.qnaboardInsert(qvo);
			
			System.out.println("인서트후");
			
			return "redirect:/goproductdetail?productcode="+productcode;
		}
		
		@RequestMapping(value = "/qnadel"  ,method = RequestMethod.GET)
		public String qnadelete(HttpServletRequest req)
		{
			int number = Integer.parseInt(req.getParameter("number1"));
			
			QnaboardDAO.qnaboardDelete(number);
			
			return "redirect:/goqnaboard1";	
		}
		
		@RequestMapping(value = "/goqnaans" , method = RequestMethod.GET)
		public ModelAndView goqnaans(ModelAndView mv,HttpServletRequest req)
		{
			int number = Integer.parseInt(req.getParameter("number1"));
			
			mv.addObject("number",number);
			mv.setViewName("popup");
			
			return mv;
		}
		

		@RequestMapping(value = "/qnaanscon"  ,method = RequestMethod.POST)
		public String qnaasncont(@RequestBody Map<String, Object> params)
		{
			int number = Integer.parseInt((String)params.get("qnano"));
			String qnaanswercontents = (String) params.get("qnaanswercontents");
			
			System.out.println("글번호 : " + number);
			System.out.println("답변 내용 : " + qnaanswercontents);
			
			QnaboardVO qvo = new QnaboardVO();
			
			qvo.setQnano(number);
			qvo.setQnaanswercontents(qnaanswercontents);
			
			System.out.println(qvo.getQnano());
			System.out.println(qvo.getQnaanswercontents());
			
			QnaboardDAO.qnaboardUpdate(qvo);
			
			String a = "redirect:/goqnaans?number1="+number;
			
			return a;
		}
	 
		
	//--------------------------------------------공지사항게시판--------------------------------------------------------
	
		@RequestMapping(value = "/gonoticeboard" , method = RequestMethod.GET)
		public ModelAndView noticepaging(ModelAndView mv , HttpServletRequest req)
		{
			int page = Integer.parseInt(req.getParameter("page"));
			
			int totalnum = NoticeboardDAO.noticeboardNum();
			
			int totalnum2 = ((totalnum-1)/10)+1;
			
			Map <String,Object> map1 = new HashMap <String,Object>();
			map1.put("page", page);
			map1.put("totalnum", totalnum);
			
			System.out.println(totalnum);
			
			List <NoticeboardVO> list1 = NoticeboardDAO.noticeboardPaging(map1);
			
			mv.addObject("list1",list1);
			mv.setViewName("noticeboard");
			mv.addObject("totalnum2" , totalnum2);
			
			String user = req.getParameter("user");
			
			HttpSession hs = req.getSession();
			
			if(user!=null)
				hs.setAttribute("user", user);
			
			return mv;
		}
		
		@RequestMapping(value = "/noticeview" , method = RequestMethod.GET)
		public ModelAndView noticeview(ModelAndView mv,HttpServletRequest req)
		{
			int noticeno = Integer.parseInt(req.getParameter("noticeno"));
			
			System.out.println("글번호 : " + noticeno);
			
			NoticeboardVO nvo = NoticeboardDAO.noticeboardViewData(noticeno);
			
			mv.addObject("nvo",nvo);
			mv.setViewName("noticeview");
			
			return mv;
		}
		
		@RequestMapping(value = "/gonoticeinsert" , method = RequestMethod.GET)
		public ModelAndView gonoticeinsert(ModelAndView mv,HttpServletRequest req)
		{
			if(req.getParameter("edit")!=null)
			{
				String noticesubject = req.getParameter("noticesubject");
				String noticecontents = req.getParameter("noticecontents");
				int noticeno = Integer.parseInt(req.getParameter("noticeno"));
				String noticedate = req.getParameter("noticedate");
				
				mv.addObject("noticesubject",noticesubject);
				mv.addObject("noticecontents",noticecontents);
				mv.addObject("noticeno",noticeno);
				mv.addObject("noticedate",noticedate);
			}	
			mv.setViewName("noticeinsert");
			return mv;
		}
		
		@RequestMapping(value = "/noticeboarddelete" , method = RequestMethod.GET)
		public String noticeboarddelete(HttpServletRequest req)
		{
			int noticeno = Integer.parseInt(req.getParameter("noticeno"));
			
			NoticeboardDAO.noticeboardDeleteData(noticeno);
			
			return "redirect:/gonoticeboardpage=1";
		}
		
		@RequestMapping(value = "/noticeboardinsert" , method = RequestMethod.GET)
		public String noticeinsert(HttpServletRequest req)
		{	
			String noticesubject = req.getParameter("noticesubject");
			String noticecontents = req.getParameter("noticecontents");
			
			if(req.getParameter("edit")!=null)
			{
				int maxnum = Integer.parseInt(req.getParameter("noticeno"));
				
				NoticeboardVO nvo = new NoticeboardVO();
				nvo.setNoticesubject(noticesubject);
				nvo.setNoticecontents(noticecontents);
				nvo.setNoticeno(maxnum);
				
				NoticeboardDAO.noticeboardUpdate(nvo);
			}
			else
			{
				int temp;
				temp = NoticeboardDAO.noticeboardMaxNum();
				
				System.out.println("사이즈 안 후");
				int maxnum = temp+1;
				
				Date date = new Date();
				SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
				String todate = transFormat.format(date);
				
				NoticeboardVO nvo = new NoticeboardVO(maxnum,noticesubject,noticecontents,todate);
				
				NoticeboardDAO.noticeboardInsert(nvo);
			}	
			return "redirect:/gonoticeboard?page=1";
		}
	
		
		//-------------------------------상품리스트관련----------------------------------------
		
		@RequestMapping(value = "/goproductinsert" , method = RequestMethod.GET)
		public ModelAndView goproductinsert(ModelAndView mv,HttpServletRequest req)
		{	
			if(req.getParameter("productcode") != null)
			{
				String productcode = req.getParameter("productcode");
				ProductVO pvo = ProductDAO.productViewData(productcode);
				mv.addObject("pvo",pvo);
			}
				
			mv.setViewName("productinsert");
			
			return mv;
		}
		
		public static String getUuid() { 
			return UUID.randomUUID().toString().replaceAll("-", ""); 
		}
		
		@RequestMapping(value = "/goproductlistpage" ,method = RequestMethod.GET)
		public ModelAndView productlistpage(ModelAndView mv , HttpServletRequest req)
		{     
			int page = Integer.parseInt(req.getParameter("page"));
	         
			int totalnum = ProductDAO.productNum();
	         
			int totalnum2 = ((totalnum-1)/10)+1;
	         
			Map <String,Object> map2 = new HashMap <String,Object>();
			map2.put("page", page);
			map2.put("totalnum", totalnum);
	         
			List <ProductVO> list1 = ProductDAO.productPaging(map2);
	         
			mv.addObject("list1",list1);
	         
			mv.setViewName("productlist");
			mv.addObject("totalnum2",totalnum2);

			return mv;
		}
		
		@RequestMapping(value = "/goproductlist" , method = RequestMethod.GET)
		public ModelAndView goproductlist(ModelAndView mv,HttpServletRequest req)
		{	
			List <ProductVO> list1 = ProductDAO.productAllData();
			
			mv.addObject("list1", list1);
			mv.setViewName("productlist");
			
			String user = req.getParameter("user");
			
			HttpSession hs = req.getSession();
			
			if(user!=null)
				hs.setAttribute("user", user);
			
			
			return mv;
		}
		
		   @RequestMapping(value = "/productins" , method = RequestMethod.POST)
		   public String productinsert(HttpServletRequest req,MultipartHttpServletRequest mre) throws UnsupportedEncodingException
		   {         
		      String productsubject = mre.getParameter("productsubject");
		      productsubject = new String(productsubject.getBytes("8859_1"),"utf-8");
		      
		      String productcontents = mre.getParameter("productcontents");
		      productcontents = new String(productcontents.getBytes("8859_1"),"utf-8");
		      
		      String productgender = mre.getParameter("gender");
		      productgender = new String(productgender.getBytes("8859_1"),"utf-8");
		      
		      int productstock = Integer.parseInt(mre.getParameter("productstock"));
		      int productprice = Integer.parseInt(mre.getParameter("productprice"));
		      
		      if(mre.getParameter("edit") != null)
		      {
		         String productcode = req.getParameter("productcode");
		         
		         ProductVO pvo = new ProductVO();
		         
		         pvo.setProductsubject(productsubject);
		         pvo.setProductcontents(productcontents);
		         pvo.setProductstock(productstock);
		         pvo.setProductprice(productprice);
		         pvo.setProductgender(productgender);
		         pvo.setProductcode(productcode);

		         ProductDAO.productUpdate(pvo);
		      }
		      else
		      {
		         Date date = new Date();
		         SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
		         String todate = transFormat.format(date);
		         
		         String productsize = mre.getParameter("productsize");
		         String productcolor = mre.getParameter("productcolor");
		         
		         String productcategory = mre.getParameter("category");
		         productcategory = new String(productcategory.getBytes("8859_1"),"utf-8");

		         int temp;
		         temp = ProductDAO.productMaxNum();
		         int maxnum = temp+1;
		         
		         String productcode = "";
		         
		         switch(productcategory)
		         {
		         case "상의" : productcode += "T"; break;
		         case "하의" : productcode += "P"; break;
		         case "가방" : productcode += "B"; break;
		         case "신발" : productcode += "S"; break;
		         case "액세서리" : productcode += "A"; break;
		         }
		         
		         switch(productcolor)
		         {
		         case "RED" : productcode += "R"; break;
		         case "ORANGE" : productcode += "O"; break;
		         case "YELLOW" : productcode += "Y"; break;
		         case "GREEN" : productcode += "G"; break;
		         case "BLUE" : productcode += "B"; break;
		         }
		         
		         switch(productsize)
		         {
		         case "S" : productcode += "S"; break;
		         case "M" : productcode += "M"; break;
		         case "L" : productcode += "L"; break;
		         case "XL" : productcode += "X"; break;
		         case "210" : productcode += "21"; break;
		         case "220" : productcode += "22"; break;
		         case "230" : productcode += "23"; break;
		         case "240" : productcode += "24"; break;
		         case "250" : productcode += "25"; break;
		         case "260" : productcode += "26"; break;
		         case "270" : productcode += "27"; break;
		         case "280" : productcode += "28"; break;
		         }
		         
		         if(maxnum<10)
		            productcode += ("00"+maxnum);
		         else if(maxnum<100)
		            productcode += ("0"+maxnum);
		         else
		            productcode += maxnum;

		         
		         double productstar = 0.0;
		         
		         ProductVO pvo = new ProductVO(maxnum,productcode,productcolor,productsize,productstock,productcategory,productgender,productsubject,productcontents,"noFile","noFile",todate,productstar,productprice);
		         
		         /*MultipartFile mf = mre.getFile("file"); // jsp file name mapping*/
		         List<MultipartFile> mf2 = mre.getFiles("file");
		         String uploadPath = "";
		         
		         if(!mf2.isEmpty()) 
		         {
		            
		            String path = "E:/"+"upload/"+"test/"; // 파일 업로드 경로
		            System.out.println("1");
		            if(mre.getFileNames()==null) 
		            {
		               System.out.println("2");
		            }
		            else System.out.println("3");
		            
		            
		            for(MultipartFile mf : mf2)
		            {
		               String original = mf.getOriginalFilename();
		               int size = (int) mf.getSize();
		               String ext = original.substring(original.lastIndexOf('.'));
		               String saveFileName = getUuid() + ext;
		               uploadPath = path+File.separator+saveFileName;
		               try {
		                  mf.transferTo(new File(uploadPath));
		               } catch (IllegalStateException e) {
		                  // TODO Auto-generated catch block
		                  e.printStackTrace();
		               } catch (IOException e) {
		                  // TODO Auto-generated catch block
		                  e.printStackTrace();
		               }
		               
		               int temp2;
		               temp2 = UploadDAO.uploadMaxNum();
		               int maxnum2 = temp2+1;
		               
		               UploadVO uvo = new UploadVO(maxnum2,productcode,original,saveFileName);
		               UploadDAO.uploadInsert(uvo);
		               
		               if(pvo.getOriginal_fname().equals("noFile"))
		               {
		                  pvo.setOriginal_fname(original);
		                  pvo.setStored_fname(saveFileName);
		               }
		            }
		            /*String original = mf.getOriginalFilename(); // 업로드하는 파일 name
		            System.out.println(original);
		            int size = (int) mf.getSize();
		            String ext = original.substring(original.lastIndexOf('.')); // 확장자 
		            String saveFileName = getUuid() + ext;
		               
		            System.out.println("!!!!!!!!!!"+original);   // file original name
		            System.out.println("!!!!!!!!!!"+size);// file size
		            System.out.println("!!!!!!!!!!"+saveFileName);// fileName암호화   
		            
		            uploadPath = path+File.separator+saveFileName; // 파일 업로드 경로 + 파일 이름
		            
		            
		            try {
		               mf.transferTo(new File(uploadPath)); // 파일을 위에 지정 경로로 업로드
		            } catch (IllegalStateException e) {
		               // TODO Auto-generated catch block
		               e.printStackTrace();
		            } catch (IOException e) {
		               // TODO Auto-generated catch block
		               e.printStackTrace();
		            }
		            System.out.println("업로드완료");
		            pvo.setOriginal_fname(original);
		            pvo.setStored_fname(saveFileName);*/
		         }
		            
		         System.out.println("인서트전");
		            
		         ProductDAO.productInsert(pvo);
		            
		         System.out.println("인서트후");
		      }
		      


		      return "redirect:/goproductlist";
		   }
		@RequestMapping(value = "/productdelete" , method = RequestMethod.GET)
		public String productelete(HttpServletRequest req)
		{
			String productcode = req.getParameter("productcode");
			
			ProductDAO.productDelete(productcode);
			
			return "redirect:/goproductlist";
		}
		
        @RequestMapping(value = "/goproductdetail" , method = RequestMethod.GET)
        public ModelAndView goproductdetail(ModelAndView mv,HttpServletRequest req)
        {   
           String productcode = req.getParameter("productcode");
           
           System.out.println(productcode);
           
           ProductVO pvo = ProductDAO.productViewData(productcode);
           mv.addObject("pvo",pvo);
           
           String productsubject = pvo.getProductsubject();
           
           List <ProductVO> list1 = ProductDAO.productSameData(productsubject);
           String sizetemp ="";
           List <String> size = new ArrayList<String>();
           
           for(ProductVO list2 : list1)
           {
              if(sizetemp.equals(""))
              {   
                 sizetemp = list2.getProductsize();
                 size.add(sizetemp);
              }
              else if(!sizetemp.equals(list2.getProductsize()))
              {   
                 sizetemp = list2.getProductsize();
                 size.add(sizetemp);
              }
           }
           
           String [] aa = new String [size.size()];
           sizetemp = "";
           int i=0;
           
           for(ProductVO list2 : list1)
           {
              if(sizetemp.equals(""))
              {         
                 aa[i] = "\""+list2.getProductcolor()+"\"";
                 sizetemp = list2.getProductsize();
              }
              else if(sizetemp.equals(list2.getProductsize()))
              {
                 aa[i] += ","+"\""+list2.getProductcolor()+"\"";
              }
              else if(!sizetemp.equals(list2.getProductsize()))
              {   
                 mv.addObject("color"+sizetemp,aa[i]);
                 i++;
                 aa[i] = "\""+list2.getProductcolor()+"\"";
                 sizetemp = list2.getProductsize();
              }
           }
        
           mv.addObject("size",size);
           mv.addObject("color"+sizetemp,aa[i]);
     
           mv.setViewName("productdetail");
           
           System.out.println(mv.getModel());
           
           return mv;
        }
		
		//-------------------------------리뷰관련----------------------------------------
				
		@RequestMapping(value = "/goreviewinsert" , method = RequestMethod.GET)
		public ModelAndView goreviewinsert(ModelAndView mv,HttpServletRequest req)
		{		
			String productcode = req.getParameter("productcode");
		    mv.addObject("productcode",productcode);
		    mv.setViewName("reviewinsert");
			
			return mv;
		}
		
		@RequestMapping(value = "/reviewboard" , method = RequestMethod.GET)
		public ModelAndView reviewboard(ModelAndView mv,HttpServletRequest req)
		{
			int page = 1;
			int choice = 0;
			String productcode = req.getParameter("productcode");
			
			if(req.getParameter("page") != null) {
				page = Integer.parseInt(req.getParameter("page"));
			}
			
			int totalnum = ReviewDAO.reviewNum(productcode);
			int totalnum2 = ((totalnum-1)/10)+1;
			
			Map <String,Object> map1 = new HashMap <String,Object>();
			map1.put("page", page);
			map1.put("totalnum", totalnum);
			map1.put("productcode", productcode);
			
			String user = "id123";//사용자아이디 productdetail.jsp에서 받아올지 세션으로 받아올지 미정
			ReviewDAO.setZero();
			ReviewDAO.setCheck(user); 		

			List <ReviewVO> reviewList = ReviewDAO.reviewPaging1(map1);
			
			List<ReviewVO> total_list = ReviewDAO.getTotal(productcode);
			int five = 0;
			int four = 0;
			int three = 0;
			int two = 0;
			int one = 0;

			int total = total_list.size();
			
			for(int i=0;i<total;i++) {
				ReviewVO vo = total_list.get(i);
				if(vo.getR_star() ==1) one++;
				else if(vo.getR_star() ==2) two++;
				else if(vo.getR_star() ==3) three++;
				else if(vo.getR_star() ==4) four++;
				else five++;
			}
			
			double avg = ((one*1+two*2+three*3+four*4+five*5)*1.0)/total;
			
			
			mv.addObject("r_list", reviewList);
			mv.addObject("totalnum2",totalnum2);
			mv.addObject("productcode",productcode);
			mv.addObject("choice",choice);
			mv.addObject("page",page);
			mv.addObject("user",user);
			
			mv.addObject("one",one);
			mv.addObject("two",two);
			mv.addObject("three",three);
			mv.addObject("four",four);
			mv.addObject("five",five);
			mv.addObject("total",total);
			mv.addObject("avg",avg);
			
			System.out.println("1");
			mv.setViewName("reviewboard");
			return mv;
		}
		@RequestMapping(value = "/reviewpage" , produces = "application/json")
		public @ResponseBody Map<String,Object> reviewpage(@RequestBody Map<String, Object> params , HttpServletRequest req)
		{
			int choice = (Integer)params.get("choice");
			int page =(Integer) params.get("page");
			String productcode = (String) params.get("productcode");
			String user = (String) params.get("user");
			System.out.println(choice);
			int totalnum = ReviewDAO.reviewNum(productcode);
			int totalnum2 = ((totalnum-1)/10)+1;
			
			Map <String,Object> map2 = new HashMap <String,Object>();
			map2.put("page", page);
			map2.put("totalnum", totalnum);
			map2.put("productcode", productcode);
			
			ReviewDAO.setZero();
			ReviewDAO.setCheck(user);
			List <ReviewVO> list1 = null;
			
			if(choice ==0) {
				list1 = ReviewDAO.reviewPaging1(map2);
			}else if(choice ==1) {
				list1 = ReviewDAO.reviewPaging2(map2);
			}else if(choice ==2) {
				list1 = ReviewDAO.reviewPaging3(map2);
			}else if(choice ==3) {
				list1 = ReviewDAO.reviewPaging4(map2);
			}
			
			Map <String,Object> map1 = new HashMap <String,Object>();
			
			map1.put("r_list" , list1);
			
			map1.put("choice", choice);
			System.out.println(choice);
			map1.put("page", page);
			
			map1.put("productcode", productcode);
			
			map1.put("user", user);
			
			map1.put("totalnum2",totalnum2);
			
			System.out.println("--------------");

			return map1;
		}
	
	
		
		@RequestMapping("/goreview")
		public String uploadTest(ModelAndView mv,MultipartHttpServletRequest mre) throws UnsupportedEncodingException{
			
			
			ReviewVO reviewvo = new ReviewVO();
			
			int temp = ReviewDAO.reviewMaxNum();
			int maxnum = temp+1;
			
			String sangpum = mre.getParameter("r_sangpum");
			sangpum = new String(sangpum.getBytes("8859_1"),"utf-8");
			
			String title = mre.getParameter("title");
			title = new String(title.getBytes("8859_1"),"utf-8");
			
			String naeyong = mre.getParameter("naeyong");
			naeyong = new String(naeyong.getBytes("8859_1"),"utf-8");
			
			String name = mre.getParameter("name");
			name = new String(name.getBytes("8859_1"),"utf-8");///
			
			String grade = "gold";////
			
			String r_size = "M";////

			
			Date date = new Date();
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
			String todate = transFormat.format(date);
			
			double r_star = Double.parseDouble(mre.getParameter("star1"));
			
			reviewvo.setR_num(maxnum);
			reviewvo.setR_grade(grade);
			reviewvo.setR_sangpum(sangpum);
			reviewvo.setR_size(r_size);
			reviewvo.setR_title(title);
			reviewvo.setR_text(naeyong);
			reviewvo.setR_writer(name);
			reviewvo.setR_date(todate);
			reviewvo.setR_star(r_star);
			
			reviewvo.setOriginal_fname("noFile");
			reviewvo.setStored_fname("noFile");
			
			MultipartFile mf = mre.getFile("file"); // jsp file name mapping
			String uploadPath = "";
			
			if(!mf.isEmpty()) {
				
				String path = "E:/"+"upload/"+"test/"; // 파일 업로드 경로
				System.out.println(1);
				if(mre.getFileNames()==null) {
					System.out.println(2);
				}
				else System.out.println(3);
				String original = mf.getOriginalFilename(); // 업로드하는 파일 name
				System.out.println(original);
				int size = (int) mf.getSize();
				String ext = original.substring(original.lastIndexOf('.')); // 확장자 
				String saveFileName = getUuid() + ext;
					
				System.out.println("!!!!!!!!!!"+original);	// file original name
				System.out.println("!!!!!!!!!!"+size);// file size
				System.out.println("!!!!!!!!!!"+saveFileName);// fileName암호화	
				
				uploadPath = path+File.separator+saveFileName; // 파일 업로드 경로 + 파일 이름
				
				
				try {
					mf.transferTo(new File(uploadPath)); // 파일을 위에 지정 경로로 업로드
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("업로드완료");
				reviewvo.setOriginal_fname(original);
				reviewvo.setStored_fname(saveFileName);
			}
			
			ReviewDAO.insertReview(reviewvo);
			
			return "redirect:/goproductdetail?productcode="+sangpum;
		}
		
		  @RequestMapping(value="/check_like", method=RequestMethod.POST, produces="application/json")
		  public @ResponseBody Map<String,Object> like(@RequestBody Map<String, Object> params, HttpSession session){
			
			Map <String,Object> map1 = new HashMap <String,Object>();

			int r_num = (Integer) params.get("number");
			
			ReviewDAO reviewdao = new ReviewDAO();

			/*String id1 = session.getAttribute("id1");*///////////////////////////////////////////
			String id1 = "id123";
			
			HashMap <String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("id", id1);
			hashMap.put("r_num", r_num);
			
			int like_check = reviewdao.check(hashMap);
			String msg="";
			
		    if(like_check == 0) {
		    	msg="좋아요!";
		    	reviewdao.like_inc(r_num);
		    	reviewdao.like_insert(id1, r_num);
		    }else if(like_check==1) {
		    	msg="좋아요 취소";
		    	reviewdao.like_dec(r_num);
		    	reviewdao.like_delete(hashMap);
		    }else {
		    	msg="이미 싫어요를 누르셨습니다.";
		    }

		    int check = reviewdao.check(hashMap);
		    int like_cnt = reviewdao.like_cnt(r_num);
		    
		    map1.put("like_check", check);
		    map1.put("like_cnt", like_cnt);
		    map1.put("msg", msg);
		    
		    return map1;
		  }
		  
		  @RequestMapping(value="/check_dislike", method=RequestMethod.POST, produces="application/json")
		  public @ResponseBody Map<String,Object> dislike(@RequestBody Map<String, Object> params, HttpSession session){
			
			Map <String,Object> map1 = new HashMap <String,Object>();
			
			int r_num = (Integer) params.get("number");

			ReviewDAO reviewdao = new ReviewDAO();
			
			/*String id1 = session.getAttribute("id1");*///////////////////////////////////////////
			String id1 = "id123";
			
			HashMap <String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("id", id1);
			hashMap.put("r_num", r_num);
			
			int like_check = reviewdao.check(hashMap);
			String msg="";
			
		    if(like_check == 0) {
		    	msg="싫어요..";
		    	reviewdao.dislike_inc(r_num);
		    	reviewdao.dislike_insert(id1, r_num);
		    }else if(like_check==-1) {
		    	msg="싫어요 취소";
		    	reviewdao.dislike_dec(r_num);
		    	reviewdao.like_delete(hashMap);
		    }else {
		    	msg="이미 좋아요를 누르셨습니다.";
		    }
		    
		    int check = reviewdao.check(hashMap);
		    int dislike_cnt = reviewdao.dislike_cnt(r_num);
		    
		    map1.put("like_check", check);
		    map1.put("dislike_cnt", dislike_cnt);
		    map1.put("msg", msg);
		    
		    return map1;
		  }
	
}
