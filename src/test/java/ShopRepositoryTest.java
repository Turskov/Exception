import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.services.AlreadyExistsException;
import ru.netology.services.NotFoundException;
import ru.netology.services.Product;
import ru.netology.services.ShopRepository;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ShopRepositoryTest {

    private ShopRepository repo;
    private Product product1;
    private Product product2;
    private Product product3;

    @ParameterizedTest
    @MethodSource("equalsTestData")
    public void testEqualsMethod(Product product1, Product product2, boolean expectedResult) {
        assertEquals(expectedResult, product1.equals(product2));
    }

    private static Stream<Object[]> equalsTestData() {
        return Stream.of(
                new Object[]{new Product(1, "Товар", 100), new Product(1, "Товар", 100), true},
                new Object[]{new Product(1, "Товар", 100), new Product(2, "Товар", 100), false},
                new Object[]{new Product(1, "Товар", 100), new Product(1, "Другой товар", 100), false},
                new Object[]{new Product(1, "Товар", 100), new Product(1, "Товар", 200), false}
        );
    }

    @Test
    public void testEqualsWithNull() {
        Product product = new Product(1, "Товар", 100);
        Assertions.assertFalse(product.equals(null));
    }

    @Test
    public void testEqualsReflexivity() {
        Product product = new Product(1, "Товар", 100);
        assertEquals(product, product);
    }

    @Test
    public void testEqualsSymmetry() {
        Product product1 = new Product(1, "Товар", 100);
        Product product2 = new Product(1, "Товар", 100);
        assertEquals(product1, product2);
        assertEquals(product2, product1);
    }

    @Test
    public void testEqualsTransitivity() {
        Product product1 = new Product(1, "Товар", 100);
        Product product2 = new Product(1, "Товар", 100);
        Product product3 = new Product(1, "Товар", 100);
        assertEquals(product1, product2);
        assertEquals(product2, product3);
        assertEquals(product1, product3);
    }

    @Test
    public void testEqualsWithDifferentClass() {
        Product product = new Product(1, "Товар", 100);
        String str = "Товар";
        Assertions.assertNotEquals(product, str);
    }

    @Test
    public void testEqualsWithDifferentFields() {
        Product product1 = new Product(1, "Товар", 100);
        Product product2 = new Product(2, "Товар", 100);
        Product product3 = new Product(1, "Другой товар", 100);
        Product product4 = new Product(1, "Товар", 200);

        Assertions.assertNotEquals(product1, product2);
        Assertions.assertNotEquals(product1, product3);
        Assertions.assertNotEquals(product1, product4);
    }
    @BeforeEach
    public void setUp() {
        repo = new ShopRepository();
        product1 = new Product(1, "Носки", 80);
        product2 = new Product(2, "Куртка", 2000);
        product3 = new Product(3, "Книга", 250);
    }

    @Test
    public void addProduct_Successfully() {
        repo.add(product1);
        repo.add(product2);
        repo.add(product3);

        Product[] expected = {product1, product2, product3};
        Product[] actual = repo.findAll();

        assertArrayEquals(expected, actual);
    }

    @Test
    public void addProduct_WithDuplicateId_ThrowsAlreadyExistsException() {
        repo.add(product1);
        repo.add(product2);

        assertThrows(AlreadyExistsException.class, () -> {
            repo.add(new Product(2, "Телефон", 10000));
        });
    }

    @Test
    public void removeExistingProduct_Successfully() {
        repo.add(product1);
        repo.add(product2);
        repo.add(product3);
        repo.removeById(2);

        Product[] expected = {product1, product3};
        Product[] actual = repo.findAll();

        assertArrayEquals(expected, actual);
    }

    @Test
    public void removeNonExistingProduct_ThrowsNotFoundException() {
        repo.add(product1);
        repo.add(product2);
        repo.add(product3);

        assertThrows(NotFoundException.class, () -> {
            repo.removeById(4);
        });
    }

    @Test
    public void findById_ReturnsExistingProduct() {
        repo.add(product1);
        repo.add(product2);

        Product actual = repo.findById(2);

        assertEquals(product2, actual);
    }

    @Test
    public void findById_ReturnsNullForNonExistingProduct() {
        repo.add(product1);
        repo.add(product2);

        Product actual = repo.findById(3);

        assertNull(actual);
    }

    @Test
    public void findAll_ReturnsEmptyArrayForEmptyRepository() {
        Product[] actual = repo.findAll();

        assertArrayEquals(new Product[0], actual);
    }
    @Test
    public void getPrice_ReturnsCorrectPrice() {
        // Arrange
        int expectedPrice = 100;
        Product product = new Product(1, "Товар", expectedPrice);

        // Act
        int actualPrice = product.getPrice();

        // Assert
        Assertions.assertEquals(expectedPrice, actualPrice);
    }

    @Test
    public void setPrice_SetsCorrectPrice() {
        // Arrange
        int initialPrice = 100;
        Product product = new Product(1, "Товар", initialPrice);
        int newPrice = 150;

        // Act
        product.setPrice(newPrice);
        int updatedPrice = product.getPrice();

        // Assert
        Assertions.assertEquals(newPrice, updatedPrice);
    }

    @Test
    public void getTitle_ReturnsCorrectTitle() {
        // Arrange
        String expectedTitle = "Товар";
        Product product = new Product(1, expectedTitle, 100);

        // Act
        String actualTitle = product.getTitle();

        // Assert
        Assertions.assertEquals(expectedTitle, actualTitle);
    }

    @Test
    public void setTitle_SetsCorrectTitle() {
        // Arrange
        String initialTitle = "Товар";
        Product product = new Product(1, initialTitle, 100);
        String newTitle = "Новый товар";

        // Act
        product.setTitle(newTitle);
        String updatedTitle = product.getTitle();

        // Assert
        Assertions.assertEquals(newTitle, updatedTitle);
    }

    @Test
    public void matches_ReturnsFalse() {
        // Arrange
        String query = "Товар";
        Product product = new Product(1, "Товар", 100);

        // Act
        boolean result = product.matches(query);

        // Assert
        Assertions.assertFalse(result);
    }
}
