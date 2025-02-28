package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryImplTest {

    @InjectMocks
    ProductRepositoryImpl productRepository;

    @BeforeEach
    void setUp() {
        // Initialize repository before each test
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        List<Product> productList = productRepository.findAll();
        assertFalse(productList.isEmpty(), "Product list should not be empty");
        Product savedProduct = productList.get(0);
        assertEquals(savedProduct.getProductId(), product.getProductId());
        assertEquals(savedProduct.getProductName(), product.getProductName());
        assertEquals(savedProduct.getProductQuantity(), product.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        List<Product> productList = productRepository.findAll();
        assertTrue(productList.isEmpty(), "Product list should be empty");
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de45-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        List<Product> productList = productRepository.findAll();
        assertFalse(productList.isEmpty(), "Product list should not be empty");
        assertEquals(2, productList.size(), "There should be two products in the list");

        assertEquals(product1.getProductId(), productList.get(0).getProductId());
        assertEquals(product2.getProductId(), productList.get(1).getProductId());
    }

    @Test
    void testFindById() {
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Product A");
        product.setProductQuantity(100);

        productRepository.create(product);

        Product foundProduct = productRepository.findById("1");

        assertNotNull(foundProduct);
        assertEquals("Product A", foundProduct.getProductName());
        assertEquals(100, foundProduct.getProductQuantity());
    }

    @Test
    void testFindByIdNotEquals() {
        Product product = new Product();
        product.setProductId("5");
        product.setProductName("Product A");
        product.setProductQuantity(100);

        productRepository.create(product);

        Product foundProduct = productRepository.findById("2");

        assertNull(foundProduct);
    }

    @Test
    void testFindByIdNotFound() {
        String nonExistentProductId = "non-existent-id";

        Product foundProduct = productRepository.findById(nonExistentProductId);

        assertNull(foundProduct);
    }

    @Test
    void testEditProduct() {
        Product product = new Product();
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId(product.getProductId());
        updatedProduct.setProductName("Sampo Cap Fam");
        updatedProduct.setProductQuantity(200);

        Product result = productRepository.update(updatedProduct);

        assertNotNull(result);
        assertEquals(updatedProduct.getProductName(), result.getProductName());
        assertEquals(updatedProduct.getProductQuantity(), result.getProductQuantity());

        List<Product> productList = productRepository.findAll();
        assertFalse(productList.isEmpty(), "The product should still be present after update");
        Product savedProduct = productList.get(0);
        assertEquals(updatedProduct.getProductName(), savedProduct.getProductName());
        assertEquals(updatedProduct.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testEditProductIdNotEquals() {
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("2");
        updatedProduct.setProductName("Sampo Cap Fam");
        updatedProduct.setProductQuantity(200);

        Product result = productRepository.update(updatedProduct);

        assertNull(result);

        List<Product> productList = productRepository.findAll();
        assertFalse(productList.isEmpty(), "There should still be the original product in the repository");
        Product savedProduct = productList.get(0);
        assertEquals("Sampo Cap Bambang", savedProduct.getProductName());
        assertEquals(100, savedProduct.getProductQuantity());
    }

    @Test
    void testDeleteProduct() {
        Product product = new Product();
        product.setProductName("Sampo Cap Dave");
        product.setProductQuantity(100);
        productRepository.create(product);

        List<Product> productList = productRepository.findAll();
        assertFalse(productList.isEmpty(), "Product should be in the repository");

        productRepository.delete(product);

        productList = productRepository.findAll();
        assertTrue(productList.isEmpty(), "Product should be deleted from the repository");
    }

    @Test
    void testDeleteNonExistentProduct() {
        Product existingProduct = new Product();
        existingProduct.setProductId("existing-product-id");
        existingProduct.setProductName("Sample Product");
        existingProduct.setProductQuantity(10);
        productRepository.create(existingProduct);

        List<Product> productListBefore = productRepository.findAll();
        assertFalse(productListBefore.isEmpty(), "There should be at least one product in the repository");

        Product nonExistentProduct = new Product();
        nonExistentProduct.setProductId("non-existent-id");
        nonExistentProduct.setProductName("Ghost Product");
        nonExistentProduct.setProductQuantity(0);

        productRepository.delete(nonExistentProduct);

        List<Product> productListAfter = productRepository.findAll();
        assertFalse(productListAfter.isEmpty(), "Product list should remain unchanged after attempting to delete a non-existent product");
        assertEquals("existing-product-id", productListAfter.get(0).getProductId(), "The existing product should remain in the repository");
    }
}
