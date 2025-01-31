package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Util {
	
	private Util(){}
	private static Util instance = new Util();
	public static Util getInterface() {
		return instance;
	}
	
	private static final String CUR_PATH = System.getProperty("user.dir")+"\\src\\files\\";
	
	static Scanner sc = new Scanner(System.in);

	public String getStrValue(String string) {
		System.out.printf("%s 입력 >> ",string);
		String input = sc.nextLine();
		if(isCheckString(input)) return null;
		return input;
	}
	
	

	public boolean isCheckString(String input) {
		if(input.isBlank()||input.isEmpty()) {
			printErr("입력된 문자가 없습니다");
			return true;
		}
		return false;
	}
	
	public void printMsg(String s) {
		System.out.println("[ "+s+" ]");
	}
	public void printErr(String s) {
		System.err.println("[ "+s+" ]");
	}



	public int getIntValue(String string, int start, int end) {
		System.out.printf("%s[%d-%d] 입력 >> ",string,start,end);
		int sel =-1;
		try {
			sel = sc.nextInt();
			sc.nextLine();
			
		}catch(Exception e) {
			sc.nextLine();
			printErr("숫자만 입력하세요");
			//e.printStackTrace();
		}
		if(isCheckRange(sel,start,end)) return -1;
		return sel;
	}



	private boolean isCheckRange(int sel, int start, int end) {
		if(sel<start || sel > end) {
			String s = String.format("[%d-%d] 중 선택 가능합니다",start,end);
			printErr(s);
			return true;
		}
		return false;
	}



	public void fileUpdate(String name,String data) {
		String filePath = CUR_PATH +name;
		try(FileWriter fw = new FileWriter(filePath)){
			fw.write(data);
			printMsg("파일저장 완료");
		} catch (IOException e) {
			printErr("파일저장 실패");
			//e.printStackTrace();
		}
		
	}



	public String fileLoad(String name) {
		String s = null;
		String filePath = CUR_PATH +name;
		try(FileReader fr = new FileReader(filePath);
				BufferedReader br = new BufferedReader(fr)){
			s = "";
			while(true) {
				String line = br.readLine();
				if(line == null) break;
				s += line+"\n";
			}
		} catch (FileNotFoundException e) {
			printErr("파일찾기 실패");
			//e.printStackTrace();
		} catch (IOException e) {
			printErr("파일불러오기 실패");
			//e.printStackTrace();
		}
		return s;
	}



	public String getCategories() {
		String s = null;
		String filePath = CUR_PATH +"categories.txt";
		try(FileReader fr = new FileReader(filePath);
				BufferedReader br = new BufferedReader(fr)){
			s = "";
			while(true) {
				String line = br.readLine();
				if(line == null) break;
				s += line+"\n";
			}
		} catch (FileNotFoundException e) {
			printErr("카테고리 파일찾기 실패");
			printErr("기본 카테고리로 대체합니다");
			//e.printStackTrace();
		} catch (IOException e) {
			printErr("카테고리 파일불러오기 실패");
			//e.printStackTrace();
		}
		return s;
	}



	public void setCategories(String cData) {
		String filePath = CUR_PATH +"categories.txt";
		try(FileWriter fw = new FileWriter(filePath)){
			fw.write(cData);
			printMsg("카테고리 기본 파일저장 완료");
		} catch (IOException e) {
			printErr("파일저장 실패");
			//e.printStackTrace();
		}
		
	}

}
