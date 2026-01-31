package br.com.marcelofaria.tarefas.app;

import br.com.marcelofaria.tarefas.domain.Task;

import java.util.List;

public class ConsoleView {

    // Imprime um título de seção para organizar a saída no console
    public static void section(String title) {
        System.out.println();
        System.out.println("==== " + title + " ====");
    }

    // Imprime uma lista de tarefas em formato de lista numerada
    public static void printTasks(List<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada.");
            return;
        }

        int index = 1;
        for (Task task : tasks) {
            System.out.printf(
                    "%d) %s | prazo: %s | status: %s%n",
                    index++,
                    task.getTitle(),
                    task.getDeadline(),
                    task.getStatus()
            );
        }
    }

    // Exibe um aviso de prazo próximo para uma tarefa específica
    public static void warning(Task task) {
        System.out.println("⚠️ Prazo próximo: " + task.getTitle()
                + " (até " + task.getDeadline() + ")");
    }
}
