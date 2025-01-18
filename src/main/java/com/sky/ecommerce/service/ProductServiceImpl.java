package com.sky.ecommerce.service;

import com.sky.ecommerce.exception.ProductException;
import com.sky.ecommerce.model.Category;
import com.sky.ecommerce.model.Product;
import com.sky.ecommerce.repository.CategoryRepository;
import com.sky.ecommerce.repository.ProductRepository;
import com.sky.ecommerce.request.CreateProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl implements ProductService{

    private ProductRepository productRepository;
    private UserService userService;
    private CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, UserService userService, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.categoryRepository = categoryRepository;
    }
    public ProductServiceImpl(){}

    @Override
    public Product createProduct(CreateProductRequest req) {

        // Handle top-level category
        Category topLevel =categoryRepository.findByName(req.getTopLevelCategory());

        if (topLevel == null) {
            Category topLevelCategory = new Category();
            topLevelCategory.setName(req.getTopLevelCategory());
            topLevelCategory.setLevel(1);

            topLevel = categoryRepository.save(topLevelCategory);
        }

// Handle second-level category
        Category secondLevel = categoryRepository.findByNameAndParent(req.getSecondLevelCategory(),topLevel.getName());
        if (secondLevel == null) {
            Category secondLevelCategory = new Category();
            secondLevelCategory.setName(req.getSecondLevelCategory());
            secondLevelCategory.setLevel(2);
            secondLevelCategory.setParentCategory(topLevel); // Assuming a parent-child relationship

            secondLevel = categoryRepository.save(secondLevelCategory);
        }

// Handle third-level category
        Category thirdLevel = categoryRepository.findByNameAndParent(req.getThirdLevelCategory(),secondLevel.getName());
        if (thirdLevel == null) {
            Category thirdLevelCategory = new Category();
            thirdLevelCategory.setName(req.getThirdLevelCategory());
            thirdLevelCategory.setLevel(3);
            thirdLevelCategory.setParentCategory(secondLevel); // Assuming a parent-child relationship

            thirdLevel = categoryRepository.save(thirdLevelCategory);
        }

        Product product = new Product();

        product.setBrand(req.getBrand());
        product.setCategory(thirdLevel);
        product.setColour(req.getColour());
        product.setDescription(req.getDescription());
        product.setTitle(req.getTitle());
        product.setDiscountedPrice(req.getDiscountedPrice());
        product.setDiscountPercent(req.getDiscountPercentage());
        product.setImageUrl(req.getImageUrl());
        product.setPrice(req.getPrice());
        product.setSizes(req.getSize());
        product.setQuantity(req.getQuantity());
        product.setCreatedAt(LocalDateTime.now());

        Product saveProduct = productRepository.save(product);
        return saveProduct;
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
        Product product = findProductById(productId);
        product.getSizes().clear();
        productRepository.delete(product);
        return "Product deleted Successfully";
    }

    @Override
    public Product updateProduct(Long productId, Product prod1) throws ProductException {

        Product product = findProductById(productId);

        if(prod1.getQuantity()!=0){
            product.setQuantity(prod1.getQuantity());
        }
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long id) throws ProductException {
        Optional<Product> opt = productRepository.findById(id);

        if(opt.isPresent()){
            return opt.get();
        }
        throw new ProductException("Product not found with id - " +id);


    }

    @Override
    public List<Product> findProductByCategory(String category) {
        return List.of();
    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> colours, List<String> sizes, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        List<Product> products = productRepository.filterProducts(category,minPrice,maxPrice,minDiscount,sort);

        if(!colours.isEmpty()){
            products=products.stream().filter(p-> colours.stream().anyMatch(c->c.equalsIgnoreCase(p.getColour()))).collect(Collectors.toList());
        }

        if(stock!=null){
            if(stock.equals("in_stock")){
                products=products.stream().filter(p -> p.getQuantity()>0).collect(Collectors.toList());
            } else if (stock.equals("out_of_stock")) {
                products =products.stream().filter(p->p.getQuantity()<1).collect(Collectors.toList());
            }
        }

        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex+pageable.getPageSize(),products.size());

        List<Product> pageContent = products.subList(startIndex,endIndex);

        Page<Product> filteredProducts = new PageImpl<>(pageContent,pageable,products.size());

        return filteredProducts;
    }
}
