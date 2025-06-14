import java.util.Random;

public class TesteStress {

    public static void main(String[] args) {
        // Cria e inicia o pico de estresse em uma nova thread
        Thread stressThread = new Thread(new PicoDeEstresse());
        stressThread.setDaemon(false); // A thread de pico de estresse continuará mesmo após o término do main
        stressThread.start();

        // Cria e inicia as tarefas pesadas
        for (int i = 0; i < 10; i++) {
            final int n = i;
            Thread tarefaPesada = new Thread(() -> tarefaPesada(n));
            tarefaPesada.setDaemon(true); // As tarefas pesadas rodarão em threads separadas
            tarefaPesada.start();
        }

        // Cria e inicia as tarefas leves
        for (int i = 0; i < 6; i++) {
            final int n = i;
            Thread tarefaLeve = new Thread(() -> tarefaLeve(n));
            tarefaLeve.setDaemon(true); // As tarefas leves também em threads separadas
            tarefaLeve.start();
        }

        // Loop infinito no thread principal para manter o programa em execução
        while (true) {
            try {
                Thread.sleep(1000); // Apenas para evitar o fechamento imediato do main
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Função para simular um pico de estresse de CPU
    public static class PicoDeEstresse implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(5000); // Espera 5 segundos
                    System.out.println("Pico de estresse iniciado!");

                    // Cria um "pico de estresse" com duração aleatória entre 0.5 e 2 segundos
                    Random rand = new Random();
                    double duracaoPico = 0.5 + (1.5 * rand.nextDouble());
                    System.out.printf("Pico de estresse durando %.2f segundos%n", duracaoPico);

                    // Simula a carga de CPU para o tempo do pico de estresse
                    long startTime = System.currentTimeMillis();
                    while (System.currentTimeMillis() - startTime < duracaoPico * 1000) {
                        // Busy-wait para sobrecarregar a CPU
                    }

                    System.out.println("Pico de estresse finalizado!");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    // Função que simula uma tarefa pesada
    public static void tarefaPesada(int n) {
        while (true) {
            try {
                System.out.printf("Iniciando tarefa pesada %d%n", n);
                Thread.sleep(1000 + new Random().nextInt(2000)); // Simula uma tarefa pesada
                System.out.printf("Finalizando tarefa pesada %d%n", n);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Função que simula uma tarefa leve
    public static void tarefaLeve(int n) {
        while (true) {
            try {
                System.out.printf("Iniciando tarefa leve %d%n", n);
                Thread.sleep(100 + new Random().nextInt(400)); // Simula uma tarefa leve
                System.out.printf("Finalizando tarefa leve %d%n", n);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
