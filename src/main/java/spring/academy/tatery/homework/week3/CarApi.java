package spring.academy.tatery.homework.week3;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cars")
public class CarApi {

    private List<Car> cars;

    public CarApi() {
        cars = new ArrayList<Car>();

        Car car1 = new Car(0, "Opel", "Vectra", "blue");
        Car car2 = new Car(1, "VW", "Passat", "black");
        Car car3 = new Car(2, "Ford", "Mustang", "red");

        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
    }

    // pobieranie wszystkich pozycji -> bez podawania parametru "color": localhost:8080/cars
    // pobieranie elementów w określonym kolorze -> poprzez parametr "color", np: localhost:8080/cars?color=red
    @GetMapping
    public ResponseEntity<List<Car>> getCarsByColor(@RequestParam(required = false, defaultValue = "") String color) {
        if (color == null || color.length() == 0) {
            return new ResponseEntity<>(cars, HttpStatus.OK);
        }

        List<Car> carList = cars.stream().filter(c -> c.getColor().toLowerCase().matches(color)).collect(Collectors.toList());

        if (carList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(carList, HttpStatus.OK);
        }
    }

    // pobieranie elementu po jego id, np: localhost:8080/cars/0
    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable int id) {
        Optional<Car> car = cars.stream().filter(c -> c.getId() == id).findAny();
        if (car.isPresent()) {
            return new ResponseEntity<>(car.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // dodawanie pozycji
    @PostMapping
    public ResponseEntity<Object> addCar(@RequestBody Car car) {
        if (cars.add(car)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // modyfikowanie pozycji
    @PutMapping
    public ResponseEntity<Object> modifyCar(@RequestBody Car newCar) {
        Optional<Car> car = cars.stream().filter(c -> c.getId() == newCar.getId()).findAny();
        if (car.isPresent()) {
            cars.remove(car.get());
            cars.add(newCar);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // modyfikowanie jednego z pól pozycji, wybor samochodu po id: localhost:8080/cars/2
    @PatchMapping("/{id}")
    public ResponseEntity<Object> patchCar(@PathVariable int id, @RequestBody Car newCar) {
        Optional<Car> car = cars.stream().filter(c -> c.getId() == id).findAny();
        if (car.isPresent()) {
            Car carTmp = car.get();
            if (newCar.getColor() != null) {
                carTmp.setColor(newCar.getColor());
            }
            if (newCar.getMark() != null) {
                carTmp.setMark(newCar.getMark());
            }
            if (newCar.getModel() != null) {
                carTmp.setModel(newCar.getModel());
            }
            // cars.add(newCar);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // usuwanie jednej pozycji
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removeCar(@PathVariable int id) {
        Optional<Car> car = cars.stream().filter(c -> c.getId() == id).findAny();
        if (car.isPresent()) {
            cars.remove(car.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
