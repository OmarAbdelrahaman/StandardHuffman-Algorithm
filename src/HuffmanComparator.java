
import java.util.Comparator;

public class HuffmanComparator implements Comparator<Node> {
    public int compare(Node x, Node y)
    {
        return x.freq - y.freq;
    }
}
