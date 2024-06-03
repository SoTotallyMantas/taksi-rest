package lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.Service;

import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.Exception.DriverNotFoundException;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.database.DriverRepository;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class is responsible for getting data from DriverRepository class and sending it to DriverController class.
 * This class is used to get, update, delete and create new drivers.
 */
@Service
public class driverService {

    @Autowired
    private DriverRepository repository;

    /**
     * This method is used to get all drivers from repository.
     * @return List of all drivers.
     */
    public List<Driver> getAllDrivers() {
        return repository.findAll();
    }
    /**
     * This method is used to delete driver by id.
     * @param id This is the id of the driver that will be deleted.
     */
    public void deleteDriverById(Long id) {
        repository.deleteById(id);
    }
    /**
     * This method is used to update driver by id.
     * @param id This is the id of the driver that will be updated.
     * @param phoneNumber This is the new phone number of the driver.
     * @param firstName This is the new first name of the driver.
     * @param lastName This is the new last name of the driver.
     * @param licensePlate This is the new license plate of the driver.
     * @return Returns updated driver.
     */
    public Driver updateDriver(Long id, String phoneNumber, String firstName, String lastName, String licensePlate) {
        Driver driver = repository.findById(id)
                .orElseThrow(() -> new DriverNotFoundException(id));
        if (phoneNumber != null) {
            driver.setPhoneNumber(phoneNumber);
        }
        if (firstName != null) {
            driver.setFirstName(firstName);
        }
        if (lastName != null) {
            driver.setLastName(lastName);
        }
        if (licensePlate != null) {
            driver.setLicensePlate(licensePlate);
        }
        repository.save(driver);
        return driver;

    }
    /**
     * This method is used to get driver by id.
     * @param id This is the id of the driver that will be returned.
     * @return Returns driver by id.
     */
    public Driver getDriverById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new DriverNotFoundException(id));
    }
    /**
     * This method is used to create new driver.
     * @param firstName This is the first name of the driver.
     * @param lastName This is the last name of the driver.
     * @param phoneNumber This is the phone number of the driver.
     * @param licensePlate This is the license plate of the driver.
     * @return Returns new driver.
     */
    public Driver newDriver(String firstName, String lastName, String phoneNumber, String licensePlate) {
        Driver driver = new Driver();
        driver.setPhoneNumber(phoneNumber);
        driver.setFirstName(firstName);
        driver.setLastName(lastName);
        driver.setLicensePlate(licensePlate);
        return repository.save(driver);
    }
    /**
     * This method is used to get driver by phone number.
     * @param phoneNumber This is the phone number of the driver that will be returned.
     * @return Returns driver by phone number.
     */
    public Driver findDriverByPhoneNumber(String phoneNumber) {
        return repository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new DriverNotFoundException(phoneNumber));
    }
    /**
     * This method is used to get driver by first name, last name or both.
     * @param FirstName This is the first name of the driver that will be returned.
     * @param LastName This is the last name of the driver that will be returned.
     * @return Returns driver by first name, last name or both.
     */
    public List<Driver> findByName(String FirstName, String LastName) {
      if(FirstName != null && LastName != null)
      {
          return repository.findByFirstNameAndLastName(FirstName,LastName).orElseThrow(() -> new DriverNotFoundException(FirstName,LastName));
      }
      else if(FirstName != null)
      {
          return repository.findByFirstName(FirstName).orElseThrow(() -> new DriverNotFoundException(FirstName));
      }
      else
      {
          return repository.findByLastName(LastName).orElseThrow(() -> new DriverNotFoundException(LastName));
      }


    }
    /**
     * This method is used to get driver by license plate.
     * @param licensePlate This is the license plate of the driver that will be returned.
     * @return Returns driver by license plate.
     */
    public List<Driver> findByLicensePlate(String licensePlate) {
        return repository.findByLicensePlate(licensePlate).orElseThrow(() -> new DriverNotFoundException(licensePlate));
    }


}
