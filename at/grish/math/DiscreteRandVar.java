package at.grish.math;


/**
 *
 * @author stef
 */
public class DiscreteRandVar {

    public double[][] probabilityTable;
    public int nValues;

    public DiscreteRandVar(int nValues) {
        this.nValues = nValues;
        probabilityTable = new double[2][nValues];
        for (int i = 0; i < nValues; i++) {
            probabilityTable[0][i] = Double.MAX_VALUE;
        }
    }

    public void addValue(double val, double prob) {
        for (int i = 0; i < nValues; i++) {
            if (probabilityTable[0][i] == Double.MAX_VALUE) {
                probabilityTable[0][i] = val;
                probabilityTable[1][i] = prob;
                break;
            }
        }
        sort();
    }

    private void sort() {
        double[][] a = new double[2][nValues];



        int tmp;

        for (int i = 0; i < nValues; i++) {
            tmp = 0;
            for (int j = 0; j < nValues; j++) {
                if (probabilityTable[0][j] < probabilityTable[0][tmp]) {
                    tmp = j;
                }
            }

            a[0][i] = probabilityTable[0][tmp];
            a[1][i] = probabilityTable[1][tmp];

            probabilityTable[0][tmp] = Double.MAX_VALUE;


        }
        probabilityTable = a;
    }

    @Override
    public String toString() {
        String s = "----------\n";
        for (int i = 0; i < nValues; i++) {
            s += probabilityTable[0][i] + ", " + probabilityTable[1][i] + "\n";
        }
        return s + "\n-----length " + nValues + "---\n";
    }

    public double calcExpectedValue() {
        double ev = 0;
        for (int i = 0; i < nValues; i++) {
            ev += probabilityTable[0][i] * probabilityTable[1][i];
        }
        return ev;
    }

    public double probSmallerEq(double val) {
        double ev = 0;
        for (int i = 0; i < nValues; i++) {
            if (probabilityTable[0][i] > val) {
                break;
            }
            ev += probabilityTable[1][i];

        }
        return ev;
    }
}
