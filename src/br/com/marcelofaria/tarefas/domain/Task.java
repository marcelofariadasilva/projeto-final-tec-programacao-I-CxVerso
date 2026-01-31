package br.com.marcelofaria.tarefas.domain;

import java.time.LocalDate;

// Representa uma tarefa do sistema com seus dados principais
public class Task {

    // Título da tarefa
    private String title;

    // Descrição detalhada da tarefa
    private String description;

    // Data limite para conclusão da tarefa
    private LocalDate deadline;

    // Status atual da tarefa
    private Status status;

    // Construtor responsável por inicializar os dados da tarefa
    public Task(String title, String description, LocalDate deadline, Status status) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.status = status;
    }

    // Retorna o título da tarefa
    public String getTitle() {
        return title;
    }

    // Retorna a descrição da tarefa
    public String getDescription() {
        return description;
    }

    // Retorna a data limite da tarefa
    public LocalDate getDeadline() {
        return deadline;
    }

    // Retorna o status atual da tarefa
    public Status getStatus() {
        return status;
    }

    // Retorna uma representação textual da tarefa para exibição e debug
    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", deadline=" + deadline +
                ", status=" + status +
                '}';
    }
}
