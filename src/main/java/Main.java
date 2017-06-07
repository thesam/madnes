import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
		CPU cpu = new CPU(new Memory());
		ExecutorService executorService = null;
		try {
			executorService = Executors.newFixedThreadPool(2);
			executorService.submit(() -> {
				while(true) {
					cpu.tick();
				}
			});
		} finally {
			if (executorService != null) {
				executorService.shutdown();
			}
		}
	}
}