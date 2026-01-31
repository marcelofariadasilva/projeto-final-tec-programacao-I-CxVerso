import br.com.marcelofaria.tarefas.app.ConsoleView;
import br.com.marcelofaria.tarefas.domain.Status;
import br.com.marcelofaria.tarefas.domain.Task;
import br.com.marcelofaria.tarefas.service.TaskService;

import java.time.LocalDate;

public class Main {

    // Ponto de entrada da aplicação: cria dados de exemplo e executa as operações do sistema
    public static void main(String[] args) {

        // Inicializa o serviço responsável por cadastrar, listar, filtrar e ordenar tarefas
        TaskService service = new TaskService();

        // Cria tarefas de exemplo para simular o uso do sistema
        Task task1 = new Task(
                "Estudar Java",
                "Revisar conceitos básicos",
                LocalDate.now().plusDays(2),
                Status.PENDENTE
        );

        Task task2 = new Task(
                "Projeto Final",
                "Avançar no projeto de programação",
                LocalDate.now().plusDays(5),
                Status.EM_ANDAMENTO
        );

        // Adiciona as tarefas ao serviço (com validação)
        service.add(task1);
        service.add(task2);

        // Exibe todas as tarefas cadastradas
        ConsoleView.section("TODAS");
        ConsoleView.printTasks(service.listAll());

        // Exibe apenas as tarefas com status PENDENTE (versão imperativa)
        ConsoleView.section("PENDENTES");
        ConsoleView.printTasks(service.filterByStatus(Status.PENDENTE));

        // Exibe apenas as tarefas com status PENDENTE (versão com Streams)
        ConsoleView.section("PENDENTES (STREAM)");
        ConsoleView.printTasks(service.filterByStatusStream(Status.PENDENTE));

        // Exibe as tarefas ordenadas por data limite (deadline)
        ConsoleView.section("ORDENADAS POR DEADLINE");
        ConsoleView.printTasks(service.listOrderedByDeadline());

        // Executa a verificação assíncrona de tarefas com prazo próximo (até 3 dias)
        ConsoleView.section("NOTIFICAÇÕES (até 3 dias)");
        service.findUpcomingDeadlinesAsync(3)
                .thenAccept(list -> {
                    if (list.isEmpty()) {
                        System.out.println("Nenhuma tarefa com prazo próximo.");
                        return;
                    }
                    list.forEach(ConsoleView::warning);
                })
                .join(); // Aguarda as notificações serem processadas antes de encerrar
    }
}
