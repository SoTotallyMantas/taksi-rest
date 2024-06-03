package lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.Service;

import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.Exception.ClientNotFoundException;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.database.ClientRepository;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class is responsible for getting data from ClientRepository class and sending it to ClientController class.
 * This class is used to get, update, delete and create new clients.
 */
@Service
public class clientService {
    @Autowired
    private ClientRepository repository;

    /**
     * This method is used to get all clients from repository.
     * @return List of all clients.
     */
    public List<Client> getAllClients() {
        return repository.findAll();
    }

    /**
     * This method is used to delete client by id.
     * @param id This is the id of the client that will be deleted.
     */
    public void deleteClientById(Long id) {
        repository.deleteById(id);
    }

    /**
     * This method is used to update client by id.
     * @param id This is the id of the client that will be updated.
     * @param phoneNumber This is the new phone number of the client.
     * @param firstName This is the new first name of the client.
     * @param lastName This is the new last name of the client.
     * @return Returns updated client.
     */
    public Client updateClient(Long id, String phoneNumber, String firstName, String lastName) {
        Client client = repository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
        if (phoneNumber != null) {
            client.setPhoneNumber(phoneNumber);
        }
        if (firstName != null) {
            client.setFirstName(firstName);
        }
        if (lastName != null) {
            client.setLastName(lastName);
        }
        repository.save(client);
        return client;

    }

    /**
     * This method is used to get client by id.
     * @param id This is the id of the client that will be returned.
     * @return Returns client by id.
     */
    public Client getClientById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }

    /**
     * This method is used to create new client.
     * @param firstName This is the first name of the client.
     * @param lastName This is the last name of the client.
     * @param phoneNumber This is the phone number of the client.
     * @return Returns new client.
     */
    public Client newClient(String firstName, String lastName, String phoneNumber) {
        Client client = new Client();
        client.setPhoneNumber(phoneNumber);
        client.setFirstName(firstName);
        client.setLastName(lastName);
        return repository.save(client);
    }

    /**
     * This method is used to find client by phone number.
     * @param phoneNumber This is the phone number of the client that will be returned.
     * @return Returns client by phone number.
     */
    public Client findClientByPhoneNumber(String phoneNumber) {
        return repository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new ClientNotFoundException(phoneNumber));
    }

    /**
     * This method is used to find client by first name, last name or both.
     * @param FirstName This is the first name of the client that will be returned.
     * @param LastName This is the last name of the client that will be returned.
     * @return Returns client by first name, last name or both.
     */
    public List<Client> findByName(String FirstName, String LastName)
    {
      if(FirstName != null && LastName != null)
      {
          return repository.findByFirstNameAndLastName(FirstName,LastName).orElseThrow(() -> new ClientNotFoundException(FirstName,LastName));
      }
      else if(FirstName != null)
      {
          return repository.findByFirstName(FirstName).orElseThrow(() -> new ClientNotFoundException(FirstName));
      }
      else
      {
          return repository.findByLastName(LastName).orElseThrow(() -> new ClientNotFoundException(LastName));
      }


    }


}
