package com.onlinestore.onlineStore.controller;

import com.onlinestore.onlineStore.model.Product;
import com.onlinestore.onlineStore.model.User;
import com.onlinestore.onlineStore.service.ProductService;
import com.onlinestore.onlineStore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @RequestMapping("/products")
    public String getProducts(Model model){
        model.addAttribute("products", productService.findAll());
        return "product/productList";
    }

    @RequestMapping(value = "/user/mycard", method = RequestMethod.GET)
    public String openMyCard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findByUsername(authentication.getName());
        Iterable<Product> products = userService.findByUsername(loggedUser.getUsername()).getProducts();
        model.addAttribute("products", products);
        model.addAttribute("sumProducts",userService.findByUsername(loggedUser.getUsername()).getProducts().stream()
                .mapToInt(p->p.getPrice())
                .sum());
        return "product/mycart";
    }

    // Remove product from cart
    @RequestMapping(value = "/user/removeProduct/{productId}", method = RequestMethod.GET)
    public String removeFromMyCard(@PathVariable(value = "productId") Long productId, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findByUsername(authentication.getName());
        Product product = productService.findById(productId).orElse(null);
        product.removeUserFromProduct(loggedUser);
        product.setQuantity(product.getQuantity()+1);
        productService.save(product);
        Iterable<Product> products = userService.findByUsername(loggedUser.getUsername()).getProducts();
        model.addAttribute("products", products);
        model.addAttribute("sumProducts",userService.findByUsername(loggedUser.getUsername()).getProducts().stream()
                .mapToInt(p->p.getPrice())
                .sum());
        return "product/mycart";
    }

    // Add product to cart
    @RequestMapping(value = "/product/addToCart/{productId}", method = RequestMethod.GET)
    public String addToCart(@PathVariable(value = "productId") Long productId) {
        Product product = productService.findById(productId).orElse(null);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = userService.findByUsername(authentication.getName());
        if(product.getQuantity()>0) {
            product.setQuantity(product.getQuantity()-1);
            product.addUserToProduct(loggedUser);
            productService.save(product);
        }
        return "redirect:/products";
    }

    @GetMapping("/admin/product/create")
    public String createProduct(Model model){
        model.addAttribute("product", new Product());
        return "product/create";
    }

    @RequestMapping(value = "/admin/product/save", method = RequestMethod.POST)
    public String save(@Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "product/create";
        }
        productService.save(product);
        return "redirect:/products";
    }

    @RequestMapping("/admin/product/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model){
        model.addAttribute("product", productService.get(id));
        return "product/edit";
    }

    @RequestMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes){
        productService.delete(id);
        redirectAttributes.addFlashAttribute("message", "Product was deleted");
        return "redirect:/products";
    }

}
