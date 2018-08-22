package com.example.postgresdemo.controller;

import com.example.postgresdemo.exception.ResourceNotFoundException;
import com.example.postgresdemo.model.Todo;
import com.example.postgresdemo.repository.TodoRepository;
import com.example.postgresdemo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/members/{memberId}/todos")
    public List<Todo> gettodosByid(@PathVariable Long memberId) {
        return todoRepository.findByid(memberId);
    }

    @PostMapping("/members/{memberId}/todos")
    public Todo addtodo(@PathVariable Long memberId,
                          @Valid @RequestBody Todo todo) {
        return memberRepository.findById(memberId)
                .map(member -> {
                    todo.setMember(member);
                    return todoRepository.save(todo);
                }).orElseThrow(() -> new ResourceNotFoundException("member not found with id " + memberId));
    }

    @PutMapping("/members/{memberId}/todos/{id}")
    public Todo updatetodo(@PathVariable Long memberId,
                             @PathVariable Long id,
                             @Valid @RequestBody Todo todoRequest) {
        if(!memberRepository.existsById(memberId)) {
            throw new ResourceNotFoundException("member not found with id " + memberId);
        }

        return todoRepository.findById(id)
                .map(todo -> {
                    todo.setTodo(todoRequest.getTodo());
                    return todoRepository.save(todo);
                }).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id " + id));
    }

    @DeleteMapping("/members/{memberId}/todos/{id}")
    public ResponseEntity<?> deletetodo(@PathVariable Long memberId,
                                          @PathVariable Long id) {
        if(!memberRepository.existsById(memberId)) {
            throw new ResourceNotFoundException("member not found with id " + memberId);
        }

        return todoRepository.findById(id)
                .map(todo -> {
                    todoRepository.delete(todo);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id " + id));

    }
}
