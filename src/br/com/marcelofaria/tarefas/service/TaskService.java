package br.com.marcelofaria.tarefas.service;

import br.com.marcelofaria.tarefas.domain.Status;
import br.com.marcelofaria.tarefas.domain.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TaskService {

    // Armazena as tarefas em memória durante a execução do programa
    private final List<Task> tasks = new ArrayList<>();

    // Valida e adiciona uma nova tarefa à lista
    public void add(Task task) {
        validate(task);
        tasks.add(task);
    }

    // Retorna uma cópia da lista com todas as tarefas cadastradas
    public List<Task> listAll() {
        return new ArrayList<>(tasks);
    }

    // Retorna as tarefas filtradas por status (versão imperativa)
    public List<Task> filterByStatus(Status status) {
        if (status == null) {
            throw new IllegalArgumentException("Status para filtro não pode ser nulo.");
        }

        List<Task> result = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getStatus() == status) {
                result.add(task);
            }
        }
        return result;
    }

    // Retorna as tarefas filtradas por status usando Streams (versão funcional)
    public List<Task> filterByStatusStream(Status status) {
        if (status == null) {
            throw new IllegalArgumentException("Status para filtro não pode ser nulo.");
        }

        return tasks.stream()
                .filter(task -> task.getStatus() == status)
                .toList();
    }

    // Retorna uma lista de tarefas ordenada pela data limite (deadline)
    public List<Task> listOrderedByDeadline() {
        List<Task> result = new ArrayList<>(tasks);
        result.sort((a, b) -> a.getDeadline().compareTo(b.getDeadline()));
        return result;
    }

    // Valida regras obrigatórias antes de salvar uma tarefa
    private void validate(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Tarefa não pode ser nula.");
        }

        if (task.getTitle() == null || task.getTitle().trim().length() < 5) {
            throw new IllegalArgumentException("Título deve ter no mínimo 5 caracteres.");
        }

        if (task.getDeadline() == null) {
            throw new IllegalArgumentException("Deadline é obrigatória.");
        }

        if (task.getDeadline().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Deadline não pode estar no passado.");
        }
    }

    // Busca de forma assíncrona tarefas com prazo próximo dentro de X dias à frente
    public CompletableFuture<List<Task>> findUpcomingDeadlinesAsync(int daysAhead) {
        if (daysAhead < 0) {
            throw new IllegalArgumentException("daysAhead não pode ser negativo.");
        }

        return CompletableFuture.supplyAsync(() -> {
            LocalDate today = LocalDate.now();
            LocalDate limit = today.plusDays(daysAhead);

            return tasks.stream()
                    .filter(t -> !t.getDeadline().isBefore(today) && !t.getDeadline().isAfter(limit))
                    .filter(t -> t.getStatus() != Status.CONCLUIDO)
                    .toList();
        });
    }
}
