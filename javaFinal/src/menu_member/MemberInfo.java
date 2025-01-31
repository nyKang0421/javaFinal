package menu_member;

import controller.MallController;
import dao.MemberDAO;
import mall.MenuCommand;
import util.Util;

public class MemberInfo implements MenuCommand {
	private MallController cont;
	Util util = Util.getInterface();
	MemberDAO mdao = MemberDAO.getInstance();

	@Override
	public void init() {
		cont = MallController.getInstance();
		System.out.println("=====[ 내정보 ]=====");
		System.out.println("[1] 비밀번호변경\n[2] 뒤로가기\n[0] 종료");
		System.out.println("=====================");
		
	}

	@Override
	public boolean update() {
		int sel = util.getIntValue("메뉴", 0, 2);
		if (sel == 0) {
			System.out.println("[ 프로그램 종료 ]");
			cont.setNext(null);
		}else if (sel == 1) {
			mdao.ModifyPw(cont.getLoginId());
			cont.setNext("MemberInfo");

		}else if (sel == 2) {
			util.printMsg("메뉴로 돌아갑니다");
			cont.setNext("MemberMain");
		}
		
		return false;
	}

}
