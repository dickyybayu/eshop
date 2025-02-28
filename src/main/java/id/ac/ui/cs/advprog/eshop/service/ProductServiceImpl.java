package id.ac.ui.cs.advprog.eshop.service;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepositoryImpl;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepositoryImpl productRepository;

    public ProductServiceImpl(ProductRepositoryImpl productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product create(Product product) {
       return productRepository.create(product);
    }
    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(String productId) {
        return productRepository.findById(productId);
    }

    @Override
    public Product update(Product product) {
        return productRepository.update(product);
    }

    @Override
    public void delete(Product product) {
        productRepository.delete(product);
    }
}