package dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import util.Util;

public class FileDAO {
	
	Util util = Util.getInterface();
	private MemberDAO mdao = MemberDAO.getInstance();
	private CartDAO cdao = CartDAO.getInstance();
	private BoardDAO bdao = BoardDAO.getInstance();
	private ItemDAO idao = ItemDAO.getInstance();
	
	enum FileName {
		BOARD("board.txt"), MEMBER("member.txt"), ITEM("item.txt"), CART("cart.txt");
		private String name;
		FileName(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
	}

	private FileDAO() {
		init();
	}

	private static FileDAO instance = new FileDAO();

	static public FileDAO getInstance() {
		return instance;
	}
	
	private void createFile(FileName name) {
		Path path = Paths.get(System.getProperty("user.dir")+"\\src\\files\\" + name.getName());
		try {
			Files.createFile(path);
			System.out.println(path + "파일 생성 완료");
		} catch (IOException e) {
			
			util.printErr(path +"파일이 이미 존재합니다");
			//System.out.println("파일이 이미 있음");
			//e.printStackTrace();
		}
	}

	private void init() {
		createFile(FileName.BOARD);
		createFile(FileName.MEMBER);
		createFile(FileName.ITEM);
		createFile(FileName.CART);
	}

	public void boardControll(int num,String data) {
		FileName f;
		if(num == 1) {
			f = FileName.BOARD;
		}else if(num == 2) {
			f = FileName.MEMBER;
		}else if(num == 3) {
			f = FileName.ITEM;
		}else if(num == 4) {
			f = FileName.CART;
		}else {
			util.printErr("오류 발생");
			return;
		}
		printMenu();
		int sel = util.getIntValue("메뉴", 1, 3);
		if (sel == 3) {
			util.printMsg("관리자 메뉴로 돌아갑니다");
			return;
		}else if (sel == 1) {
			util.fileUpdate(f.getName(),data);
		}else if (sel == 2) {
			String newData = util.fileLoad(f.getName());
			if(num == 1) {
				bdao.upLoadData(newData);
			}else if(num == 2) {
				mdao.upLoadData(newData);
			}else if(num == 3) {
				idao.upLoadData(newData);
			}else if(num == 4) {
				cdao.upLoadData(newData);
			}
		}
	}

	private void printMenu() {
		System.out.println("=====[ 관리자 파일관리 ]=====");
		System.out.println("[1] 파일저장\n[2] 파일불러오기\n[3] 뒤로가기");
		System.out.println("=====================");
		
	}

}
