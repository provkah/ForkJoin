import java.util.concurrent.RecursiveAction;
import java.util.logging.Logger;

public class ReverseStrTask extends RecursiveAction {
    private static final int THRESHOLD = 10;

    private final String str;

    private String resultStr;

    private final int lo;
    private final int hi;

    private static final Logger logger = Logger.getLogger(ReverseStrTask.class.getName());

    public ReverseStrTask(final String str) {
        this.str = str;

        this.lo = 0;
        this.hi = str.length();
    }

    public ReverseStrTask(final String str, final int lo, final int hi) {
        this.str = str;

        this.lo = lo;
        this.hi = hi;
    }

    public String getResultStr() {
        return resultStr;
    }

    protected void compute() {
        resultStr = hi - lo < THRESHOLD ? reverseString(str, lo, hi) : reverseStringForkJoin(str, lo, hi);
    }

    private static String reverseStringForkJoin(final String str, final int lo, final int hi) {
        int mid = (lo + hi) / 2;
        logger.info(String.format("Split string, lo: %d, mid: %d, hi: %d, thread: %s", lo, mid, hi, Thread.currentThread().getName()));

        ReverseStrTask taskLoMid = new ReverseStrTask(str, lo, mid);
        ReverseStrTask taskMidHi = new ReverseStrTask(str, mid, hi);
        invokeAll(taskLoMid, taskMidHi);

        String result = taskMidHi.resultStr + taskLoMid.resultStr;
        logger.info(String.format("Result string, lo: %d, mid: %d, hi: %d, thread: %s, resultStr: %s", lo, mid, hi, Thread.currentThread().getName(), result));
        return result;
    }

    private static String reverseString(final String str, final int lo, final int hi) {
        String result = (new StringBuilder(str.substring(lo, hi))).reverse().toString();
        logger.info(String.format("Result string, lo: %d, hi: %d, thread: %s, resultStr: %s", lo, hi, Thread.currentThread().getName(), result));
        return result;
    }
}
