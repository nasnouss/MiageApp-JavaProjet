package RealProject;

public class Main {
	
	public static void main(String[] args) {
		Menu m = new Menu();
		try {
			m.Start();
		}catch (InterruptedException e){
			e.printStackTrace();
		}
	}
}

