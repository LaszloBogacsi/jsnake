public class Map {
    private final int w;
    private final int h;
    private final int gw;
    private final int gh;



    public Map(int w, int h, int gw, int gh) {
        this.w = w;
        this.h = h;
        this.gw = gw;
        this.gh = gh;
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    public int getGridWidth() {
        return gw;
    }

    public int getGridHeight() {
        return gh;
    }
}
