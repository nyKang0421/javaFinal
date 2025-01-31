package menu_admin;

import controller.MallController;
import dao.BoardDAO;
import dao.CartDAO;
import dao.FileDAO;
import dao.ItemDAO;
import dao.MemberDAO;
import mall.MenuCommand;
import util.Util;

public class AdminFile implements MenuCommand {
	private MallController cont;
	private Util util;
	private FileDAO fdao;
	private MemberDAO mdao;
	private CartDAO cdao;
	private BoardDAO bdao;
	private ItemDAO idao;

	@Override
	public void init() {
		cont = MallController.getInstance();
		util = Util.getInterface();
		fdao = FileDAO.getInstance();
		mdao = MemberDAO.getInstance();
		cdao = CartDAO.getInstance();
		bdao = BoardDAO.getInstance();
		idao = ItemDAO.getInstance();
		System.out.println("=====[ 관리자 파일관리 ]=====");
		System.out.println("[1] 게시판\n[2] 회원\n[3] 아이템\n[4] 카트\n[5] 뒤로가기\n[0] 종료");
		System.out.println("=====================");
		
	}

	@Override
	public boolean update() {
		String data = "";
		int sel = util.getIntValue("메뉴", 0, 5);
		if (sel == 0) {
			System.out.println("[ 프로그램 종료 ]");
			cont.setNext(null);
		}else if (sel == 1) {
			data = bdao.getUpdateData();
			fdao.boardControll(sel,data);
			cont.setNext("AdminFile");
			
		}else if (sel == 2) {
			data = mdao.getUpdateData();
			fdao.boardControll(sel,data);
			cont.setNext("AdminFile");
			
		}else if (sel == 3) {
			data = idao.getUpdateData();
			fdao.boardControll(sel,data);
			cont.setNext("AdminFile");
			
		}else if (sel == 4) {
			data = cdao.getUpdateData();
			fdao.boardControll(sel,data);
			cont.setNext("AdminFile");
			
		}else if (sel == 5) {
			util.printMsg("관리자 메뉴로 돌아갑니다");
			cont.setNext("AdminMain");
			
		}
		
		return false;
	}

	

}
