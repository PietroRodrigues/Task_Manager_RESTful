package com.project.task_manager.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.task_manager.Model.Tarefa;
import com.project.task_manager.repository.TarefaRepository;

@RestController
@RequestMapping("/tarefas") // Endpoint base para: /tarefas
public class TarefaController {

    @Autowired
    private TarefaRepository tarefaRepository;

    //Criar nova tarefa
    @PostMapping // POST /tarefas (criar)
    public Tarefa criarTarefa(@RequestBody Tarefa tarefa) {
        return tarefaRepository.save(tarefa); // Tarefa criada com sucesso 201
    }

    //Criar tarefas em lote
    @PostMapping("/lote") // POST /tarefas/lote (criar em lote)
    public ResponseEntity<List<Tarefa>> criarTarefasEmLote(@RequestBody List<Tarefa> tarefas) {
        List<Tarefa> tarefasSalvas = tarefaRepository.saveAll(tarefas); // Salva tarefas em lote
        return ResponseEntity.status(HttpStatus.CREATED).body(tarefasSalvas); // Tarefas criadas com sucesso 201
    }

    //Listar todas tarefas
    @GetMapping // GET /tarefas (listar)
    public List<Tarefa> listarTarefas() {
        return tarefaRepository.findAll(); // Listagem de todas as tarefas
    }

    //Buscar tarefa por ID
    @GetMapping("/{id}") // GET /tarefas/{id} (buscar por ID)
    public ResponseEntity<Tarefa> buscarPorId(@PathVariable Long id) {
        return tarefaRepository.findById(id)
            .map(tarefa -> ResponseEntity.ok(tarefa)) // Tarefa encontrada com sucesso 200
            .orElse(ResponseEntity.notFound().build()); // Tarefa não encontrada 404
    }

    //Atualizar tarefa
    @PutMapping("/{id}") // PUT /tarefas/{id} (atualizar)
    public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa tarefaAtualizada){
        if (!tarefaRepository.existsById(id)) {
            return ResponseEntity.notFound().build(); // Tarefa não encontrada 404
        }
        tarefaAtualizada.setId(id);
        return ResponseEntity.ok(tarefaRepository.save(tarefaAtualizada)); // Tarefa atualizada com sucesso 200
    }

    //Deletar tarefa
    @DeleteMapping("/{id}") // DELETE /tarefas/{id} (deletar)
    public ResponseEntity<Void> deletarTarefa(@PathVariable Long id){
        if (!tarefaRepository.existsById(id)) {
            return ResponseEntity.notFound().build(); // Tarefa não encontrada 404
        }
        tarefaRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // Tarefa deletada com sucesso 204
    }

}
