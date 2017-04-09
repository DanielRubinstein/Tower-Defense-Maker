package backEnd;
/**
 * Helper class for BFS in pathfinding algorithm
 * @author Christian Martindale (with online resource input)
 *
 */
public class Coord {
    Coord last;
	int xCoord;
    int yCoord;
    public Coord(int x, int y, Coord prev) {
        this.xCoord = x;
        this.yCoord = y;
        this.last = prev;
    }
    public Coord getLastNode() {
        return this.last;
    }
}