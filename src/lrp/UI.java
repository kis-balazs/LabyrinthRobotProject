package lrp;

public class UI{
	
	static final int totalLevels = 2;
	static int level = 0;
	static int totalScore = 0;

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Body b = new Body(level);
	}
	public static void stop() {
		System.exit(0);
		}
	}
