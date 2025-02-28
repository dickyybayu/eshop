package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;
    @BeforeEach
    void setUp() {
        // This setup method is intentionally left empty as no initialization is required currently.
    }
    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(savedProduct.getProductId(), product.getProductId());
        assertEquals(savedProduct.getProductName(), product.getProductName());
        assertEquals(savedProduct.getProductQuantity(), product.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
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

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    // Test for findById()
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

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
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

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals("Sampo Cap Bambang", savedProduct.getProductName());
        assertEquals(100, savedProduct.getProductQuantity());
    }


    @Test
    void testEmptyProduct() {
        Product updatedProduct = new Product();
        updatedProduct.setProductName("Sampo Cap Gomeh");
        updatedProduct.setProductQuantity(100);

        Product result = productRepository.update(updatedProduct);
        assertNull(result);
    }

    @Test
    void testDeleteProduct() {
        Product product = new Product();
        product.setProductName("Sampo Cap Dave");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());

        productRepository.delete(product);

        productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteNonExistentProduct() {
        Product existingProduct = new Product();
        existingProduct.setProductId("existing-product-id");
        existingProduct.setProductName("Sample Product");
        existingProduct.setProductQuantity(10);
        productRepository.create(existingProduct);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());

        Product nonExistentProduct = new Product();
        nonExistentProduct.setProductId("non-existent-id");
        nonExistentProduct.setProductName("Ghost Product");
        nonExistentProduct.setProductQuantity(0);

        productRepository.delete(nonExistentProduct);

        productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());

        assertEquals("existing-product-id", productIterator.next().getProductId());
    }

}