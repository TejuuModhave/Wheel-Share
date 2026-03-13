package com.business.repositories;

import org.springframework.data.repository.CrudRepository;

import com.business.entities.Product;

import java.util.List;
import com.business.entities.User;

public interface ProductRepository extends CrudRepository<Product, Integer> {
	public Product findByPname(String name);

	public List<Product> findByOwner(User owner);
}
