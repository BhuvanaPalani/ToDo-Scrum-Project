package se.jensenyh.todo_list.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.jensenyh.todo_list.todo.Todo;
import se.jensenyh.todo_list.todo.TodoRepository;

@Controller
// @RequestMapping("/todo")
public class ToDoController {

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping(path = "/checklist")
    public String getAllTodos(Model model, OAuth2AuthenticationToken auth) {

        try {
            var cognitoUserId = auth.getPrincipal().getAttribute("sub").toString();

            var todos = todoRepository.findByCognitoUserId(cognitoUserId);

            model.addAttribute("todos", todos);
        } catch (NullPointerException e) {
            // TODO: Handle error better

            return "redirect:/error";
        }

        return "checklist";
    }

    @PostMapping(path = "/create")
    public String create(@ModelAttribute("todo") Todo newTodo, String isDone, OAuth2AuthenticationToken auth) {

        try {

            var cognitoUserId = auth.getPrincipal().getAttribute("sub").toString();

            newTodo.setCognitoUserId(cognitoUserId);

            newTodo.setIsDone(isDone != null);

            todoRepository.save(newTodo);
        } catch (NullPointerException e) {
            // TODO: Handle error better

            return "redirect:/error";
        }

        return "redirect:/checklist";
    }


    @GetMapping(path = "/todo/{id}")
    public String getTodoById(@PathVariable int id, Model model) {
        var todo = todoRepository.findById(id).get();
        model.addAttribute("todo", todo);
        return "edit_form";
    }

    @PostMapping(path = "/todo/{id}/edit")
    public String edit(@PathVariable int id, @ModelAttribute("todo") Todo updatedTodo, String isDone, OAuth2AuthenticationToken auth) {

        try {

            var cognitoUserId = auth.getPrincipal().getAttribute("sub").toString();

            updatedTodo.setCognitoUserId(cognitoUserId);
            updatedTodo.setId(id);
            updatedTodo.setIsDone(isDone != null);

            todoRepository.save(updatedTodo);
        } catch (NullPointerException e) {
            // TODO: Handle error better

            return "redirect:/error";
        }

        return "redirect:/checklist";
    }

    @PostMapping(path = "/todo/{id}/delete")
    public String delete(@PathVariable int id) {

        todoRepository.deleteById(id);

        return "redirect:/checklist";
    }
}
