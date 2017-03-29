import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		BlenderParser p = new BlenderParser("star.obj");
		
		try {
			p.parseObjectFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		p.printArrs();
	}
}
