package br.senac.sp.epictask.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import br.senac.sp.epictask.model.Task;
import br.senac.sp.epictask.repository.TakeRepository;
import jakarta.validation.Valid;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("task")
public class TaskController {

    @Autowired
    TakeRepository repository;

    @GetMapping
    public String index(Model model){
        var list = repository.findAll();
        model.addAttribute("tasks", list);

        Map<String, Double> gastosPorPessoa = gastosPorPessoa(list);
        model.addAttribute("gastosPorPessoa", gastosPorPessoa);


        double totalGasto = gastosTotais(list);
        model.addAttribute("totalGasto", totalGasto);

        Map<String, Integer> quantidadeTarefas = contarTarefas(list);
        model.addAttribute("quantidadeTarefas", quantidadeTarefas);
        return "task/index";


    }

    private double gastosTotais(Iterable<Task> tasks) {
        double total = 0;
        for (Task task : tasks) {
            total += task.getPrice();
        }
        return total;
    }

    private Map<String, Double> gastosPorPessoa(Iterable<Task> tasks) {
        Map<String, Double> gastosPorPessoa = new HashMap<>();
        for (Task task : tasks) {
            String name = task.getName();
            double price = task.getPrice();
            gastosPorPessoa.put(name, gastosPorPessoa.getOrDefault(name, 0.0) + price);
        }
        return gastosPorPessoa;
    }

    private Map<String, Integer> contarTarefas(Iterable<Task> tasks) {
        Map<String, Integer> taskCounts = new HashMap<>();
        for (Task task : tasks) {
            String name = task.getName();
            taskCounts.put(name, taskCounts.getOrDefault(name, 0) + 1);
        }
        return taskCounts;
    }


    @GetMapping("new") //localhost:8080/new
    public String form(Task task){
        return "task/form";
    }

    @PostMapping("new")
    public String save(@Valid Task task, BindingResult result){
        if(result.hasErrors()) return "task/form";
        task.setName(task.getName().toUpperCase());
        repository.save(task);
        return "redirect:/task";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Long id){
        repository.deleteById(id);
        return "redirect:/task";


    }

}
