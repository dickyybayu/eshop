package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class CarRepositoryImpl implements CarRepository {
    private List<Car> carData = new ArrayList<>();

    @Override
    public Car create(Car car) {
        if (car.getCarId() == null) {
            car.setCarId(UUID.randomUUID().toString());
        }
        carData.add(car);
        return car;
    }

    @Override
    public List<Car> findAll() {
        return new ArrayList<>(carData);
    }

    @Override
    public Car findById(String carId) {
        for (Car car : carData) {
            if (car.getCarId().equals(carId)) {
                return car;
            }
        }
        return null;
    }

    @Override
    public Car update(String carId, Car updatedCar) {
        for (int i = 0; i < carData.size(); i++) {
            Car car = carData.get(i);
            if (car.getCarId().equals(carId)) {
                // Update the existing car with the new information
                car.setCarName(updatedCar.getCarName());
                car.setCarColor(updatedCar.getCarColor());
                car.setCarQuantity(updatedCar.getCarQuantity());
                return car;
            }
        }
        return null; // Handle the case where the car is not found
    }

    @Override
    public void delete(String carId) {
        carData.removeIf(car -> car.getCarId().equals(carId));
    }
}
