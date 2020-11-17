import java.util.concurrent.ForkJoinPool;

public class ReverseStr {
    public static void main(final String[] args) {
        String myStr = "AdoptOpenJDK\\bin\\java";
        System.out.println(myStr);

        ReverseStrTask task = new ReverseStrTask(myStr);
        ForkJoinPool.commonPool().invoke(task);

        System.out.println(task.getResultStr());
    }
}
