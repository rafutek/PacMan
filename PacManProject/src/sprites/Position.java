package sprites;

public class Position {
	
	// no need to synchronize when getting and setting these values
	public ThreadLocal<Integer> x = new ThreadLocal<Integer>();
	public ThreadLocal<Integer> y = new ThreadLocal<Integer>();
	
	public Position(int x, int y) {
		this.x.set(x);
		this.y.set(y);
	}
}
