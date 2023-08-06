package ru.netology.services;

import java.util.ArrayList;
import java.util.List;

public class ShopRepository {
    private List<Product> products = new ArrayList<>();

    public Product findById(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    /**
     * Метод добавления товара в репозиторий
     * @param product — добавляемый товар
     */
    public void add(Product product) {
        if (findById(product.getId()) != null) {
            throw new AlreadyExistsException("Element with id: " + product.getId() + " already exists");
        }
        products.add(product);
    }

    public Product[] findAll() {
        return products.toArray(new Product[0]);
    }

    // Этот способ мы рассматривали в теории в теме про композицию - удаление товара
    // Добавляем проверку по Id
    public void removeById(int id) {
        if (findById(id) == null) {
            throw new NotFoundException("Element with id: " + id + " not found");
        }
        products.removeIf(product -> product.getId() == id);
    }
}