package com.onlinestore.onlineStore;

import com.onlinestore.onlineStore.model.Product;
import com.onlinestore.onlineStore.model.Roles;
import com.onlinestore.onlineStore.model.User;
import com.onlinestore.onlineStore.repository.ProductRepository;
import com.onlinestore.onlineStore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class OnlineStoreApplication implements CommandLineRunner {
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ProductRepository productRepository;

	public static void main(String[] args) {
		SpringApplication.run(OnlineStoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user = new User("admin",passwordEncoder.encode("admin123"),"admin@yahoo.com","Tamara","Kitanovska",Roles.SUPER_ADMIN);
		userRepository.save(user);
		productRepository.save(new Product("FOSIL",150,2,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTfBA5JpzZvX5r0DMVwxc-XYmMuwEhqg2DV_hWGzkl0n7FKTav0"));
		productRepository.save(new Product("LORD",200,55,"https://cdn.shopify.com/s/files/1/0860/8486/products/ladies_lord_05_1000x1260_crop_center.png?v=1499172007"));
		productRepository.save(new Product("OLIVIA",1000,10,"https://images.asos-media.com/products/olivia-burton-grey-lilac-large-white-dial-leather-watch/7617369-1-greylilac?$XL$?$XXL$&wid=300&fmt=jpeg&qlt=80,0&op_sharpen=0&resMode=sharp2&op_usm=1,0.4,6,1&iccEmbed=0&printRes=72"));
		productRepository.save(new Product("SECONDA",190,4,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQLvky5WkqVtscq7c1cu-nYhQDPeMKKu-UfKiVfcjMXgEtGNoQbdA"));
		productRepository.save(new Product("SK",150,10,"https://ae01.alicdn.com/kf/HTB1cNacbU4WMKJjSspmq6AznpXaY/Shengke-Luxury-Women-Watch-Brands-Crystal-Sliver-Dial-Fashion-Design-Bracelet-Watches-Ladies-Womenwrist-Watches-Relogio.jpg_640x640.jpg"));
		productRepository.save(new Product("TIMEX",120,30,"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSMYIqvszUEgDBi0OHWIn8MlxseIxnVqptdbh_kg6TRK1xmkQdFBw"));
	}
}
