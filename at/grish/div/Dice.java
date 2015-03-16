package at.grish.div;

/**
 * Class emulating rolling of dice
 *
 * @author stef
 */
public class Dice {

    int nSides;

    public Dice(int nSides) {
        this.nSides = nSides;
    }

    public int roll() {
        return (int) (1 + (Math.random() * nSides));
    }

    public int[] roll(int times) {
        int[] ret = new int[times];
        for (int i = 0; i < times; i++) {
            ret[i] = roll();
        }
        return ret;
    }
}
