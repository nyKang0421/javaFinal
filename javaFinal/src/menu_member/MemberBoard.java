package menu_member;

import controller.MallController;
import dao.BoardDAO;
import mall.MenuCommand;
import util.Util;

public class MemberBoard implements MenuCommand {
	
	private MallController cont;
	private Util util;
	private BoardDAO bdao;

	@Override
	public void init() {
		cont = MallController.getInstance();
		util = Util.getInterface();
		bdao = BoardDAO.getInstance();
		
		System.out.println("=====[ 게시판 ]=====");
		System.out.println("[1] 게시글보기\n[2] 게시글추가\n[3] 내게시글(삭제)\n[4] 뒤로가기\n[0] 종료");
		System.out.println("=====================");
		
	}

	@Override
	public boolean update() {
		int sel = util.getIntValue("메뉴", 0, 4);
		if (sel == 0) {
			System.out.println("[ 프로그램 종료 ]");
			cont.setNext(null);
		}else if (sel == 1) {
			bdao.showBoard();
			cont.setNext("MemberBoard");
			
		}else if (sel == 2) {
			bdao.insertBoard();
			cont.setNext("MemberBoard");
			
		}else if (sel == 3) {
			bdao.deleteBoard();
			cont.setNext("MemberBoard");
			
		}else if (sel == 4) {
			util.printMsg("메뉴로 돌아갑니다");
			cont.setNext("MemberMain");
		}

		return false;
	}

}
