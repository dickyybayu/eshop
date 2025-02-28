package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import java.util.List;

public interface CarRepository {
    Car create(Car car);
    List<Car> findAll();
    Car findById(String carId);
    Car update(String carId, Car car);
    void delete(String carId);
}