package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        product = new Product();
        product.setProductId("1");
        product.setProductName("Product A");
        product.setProductQuantity(100);
    }

    // Test for create(Product product)
    @Test
    void testCreate() {
        when(productRepository.create(product)).thenReturn(product);

        Product createdProduct = productService.create(product);

        assertNotNull(createdProduct);
        assertEquals("Product A", createdProduct.getProductName());
        assertEquals(100, createdProduct.getProductQuantity());
        verify(productRepository).create(product);
    }

    // Test for findAll()
    @Test
    void testFindAll() {
        List<Product> products = new ArrayList<>();
        products.add(product);

        Iterator<Product> productIterator = products.iterator();
        when(productRepository.findAll()).thenReturn(productIterator);

        List<Product> allProducts = productService.findAll();

        assertEquals(1, allProducts.size());
        assertEquals("Product A", allProducts.get(0).getProductName());
        verify(productRepository).findAll();
    }

    // Test for findById(String productId)
    @Test
    void testFindById() {
        when(productRepository.findById("1")).thenReturn(product);

        Product foundProduct = productService.findById("1");

        assertNotNull(foundProduct);
        assertEquals("Product A", foundProduct.getProductName());
        verify(productRepository).findById("1");
    }

    // Test for update(Product product)
    @Test
    void testUpdate() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("1");
        updatedProduct.setProductName("Updated Product");
        updatedProduct.setProductQuantity(150);

        when(productRepository.update(updatedProduct)).thenReturn(updatedProduct);

        Product result = productService.update(updatedProduct);

        assertNotNull(result);
        assertEquals("Updated Product", result.getProductName());
        assertEquals(150, result.getProductQuantity());
        verify(productRepository).update(updatedProduct);
    }

    // Test for delete(Product product)
    @Test
    void testDelete() {
        when(productRepository.findById("1")).thenReturn(product);

        productService.delete(product);

        verify(productRepository).delete(product);
    }
}
