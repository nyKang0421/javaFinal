package menu_mall;

import controller.MallController;
import dao.MemberDAO;
import mall.MenuCommand;
import util.Util;

public class MallLogin implements MenuCommand {
	private MallController cont;

	@Override
	public void init() {
		System.out.println("=====[ 로그인 ]=====");
		cont = MallController.getInstance();
	}

	@Override
	public boolean update() {
		MemberDAO dao = MemberDAO.getInstance();
		Util util = Util.getInterface();
		if(dao.getListSize() <= 1) {
			util.printMsg("회원 목록이 존재하지 않습니다");
			util.printMsg("관리자만 로그인 가능합니다");
		}

		String id = util.getStrValue("아이디 ");
		if(id == null) return false;
		String pw = util.getStrValue("패스워드 ");
		if(id == null) return false;
	
		if (dao.isValidMember(id, pw)!=null) {
			if (id.equals("admin")) {
				cont.setLoginId("admin");
				cont.setNext("AdminMain");
			} else {
				cont.setLoginId(id);
				cont.setNext("MemberMain");
			}
			System.out.println("[ 로그인 성공 ]");
		} else {
			System.err.println("아이디 혹은 비밀번호를 확인해주세요");
			cont.setNext("MallMain");
		}
		return false;
	}

}
