package sia.tacocloud.controllers;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import sia.tacocloud.domainEntities.Ingredient;
import sia.tacocloud.domainEntities.Taco;
import sia.tacocloud.domainEntities.TacoOrder;
import sia.tacocloud.repositories.IngredientRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController{


    private final IngredientRepository ingredientRepo;
    private final TaskExecutionProperties taskExecutionProperties;


    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepo, TaskExecutionProperties taskExecutionProperties) {
        this.ingredientRepo = ingredientRepo;
        this.taskExecutionProperties = taskExecutionProperties;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {

        Iterable<Ingredient> ingredients = ingredientRepo.findAll();
        List<Ingredient> ingredientList = new ArrayList<>();

        Iterator<Ingredient> i = ingredients.iterator();
        while(i.hasNext()) {
            Ingredient cur = i.next();
            ingredientList.add(cur);
        }


        Ingredient.Type[] types = Ingredient.Type.values();
        for(Ingredient.Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredientList, type));
        }
    }

    @PostMapping
    public String processTaco(@Valid Taco taco, Errors errors, @ModelAttribute TacoOrder tacoOrder) {
        if(errors.hasErrors()) {return "design";}
        tacoOrder.addTaco(taco);
        log.info("Processing taco: {}", taco);
        return "redirect:/orders/current";
    }

    @ModelAttribute(name = "tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }

    @ModelAttribute
    public Taco taco() {
        return new Taco();
    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }


    private Iterable<Ingredient> filterByType(
            List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
