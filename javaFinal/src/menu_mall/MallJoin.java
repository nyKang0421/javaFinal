package menu_mall;

import controller.MallController;
import dao.MemberDAO;
import mall.MenuCommand;
import util.Util;

public class MallJoin implements MenuCommand {
	private MallController cont;
	Util util = Util.getInterface();

	@Override
	public void init() {
		cont = MallController.getInstance();
		
	}

	@Override
	public boolean update() {
		System.out.println("=====[ 회원가입 ]=====");
		MemberDAO dao = MemberDAO.getInstance();
		
		String id = util.getStrValue("아이디 ");
		if(id == null) return false;
		
		if(id.equals("admin")||id.contains("admin")) {
			util.printErr("admin 관련 id는 사용할 수 없습니다");
			return false;
		}
		
		if(dao.getMemberById(id)!= null) {
			util.printErr("이미 사용하는 아이디");
			return false;
		}
		
		String pw = util.getStrValue("비밀번호 ");
		if(pw == null) return false;
		String name = util.getStrValue("이름 ");
		if(name == null) return false;
		
		if(dao.insertMember(id , pw , name)) {
			System.out.println("[ 회원 추가 완료 ]");
		}else {
			System.out.println("[ 회원 추가 실패]");
		}
		cont.setNext("MallMain");
		return false;
	}

}
 