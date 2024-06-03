package lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.Service;

import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.Exception.ClientNotFoundException;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.Exception.DispatchNotFoundException;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.database.DispatchRepository;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model.Client;
import lt.viko.eif.m.trojanovskis.taksi.rest.taksirest.model.Dispatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class is responsible for getting data from DispatchRepository class and sending it to DispatchController class.
 * This class is used to get, update, delete and create new dispatchers.
 */
@Service
public class dispatchService {

    @Autowired
    private  DispatchRepository repository;

    /**
     * This method is used to get all dispatchers from repository.
     * @return List of all dispatchers.
     */
    public List<Dispatch> getAllDispatch() {
        return repository.findAll();
    }

    /**
     * This method is used to delete dispatcher by id.
     * @param id This is the id of the dispatcher that will be deleted.
     */
    public void deleteDispatchById(Long id) {
        repository.deleteById(id);
    }

    /**
     * This method is used to update dispatcher by id.
     * @param id This is the id of the dispatcher that will be updated.
     * @param workNumber This is the new work number of the dispatcher.
     * @param phoneNumber This is the new phone number of the dispatcher.
     * @param firstName This is the new first name of the dispatcher.
     * @param lastName This is the new last name of the dispatcher.
     * @return Returns updated dispatcher.
     */
    public Dispatch updateDispatch(Long id,String workNumber, String phoneNumber, String firstName, String lastName) {
        Dispatch dispatch = repository.findById(id)
                .orElseThrow(() -> new DispatchNotFoundException(id));
        if (workNumber != null) {
            dispatch.setWorkNumber(workNumber);
        }
        if (phoneNumber != null) {
            dispatch.setPhoneNumber(phoneNumber);
        }
        if (firstName != null) {
            dispatch.setFirstName(firstName);
        }
        if (lastName != null) {
            dispatch.setLastName(lastName);
        }
        repository.save(dispatch);
        return dispatch;

    }

    /**
     * This method is used to get dispatcher by id.
     * @param id This is the id of the dispatcher that will be returned.
     * @return Returns dispatcher by id.
     */
    public Dispatch getDispatchById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new DispatchNotFoundException(id));
    }

    /**
     * This method is used to create new dispatcher.
     * @param firstName This is the first name of the dispatcher.
     * @param lastName This is the last name of the dispatcher.
     * @param phoneNumber This is the phone number of the dispatcher.
     * @param workNumber This is the work number of the dispatcher.
     * @return Returns new dispatcher.
     */
    public Dispatch newDispatch(String firstName, String lastName, String phoneNumber, String workNumber) {
        Dispatch dispatch = new Dispatch();
        dispatch.setPhoneNumber(phoneNumber);
        dispatch.setFirstName(firstName);
        dispatch.setLastName(lastName);
        dispatch.setWorkNumber(workNumber);
        return repository.save(dispatch);
    }

    /**
     * This method is used to get dispatcher by phone number.
     * @param phoneNumber This is the phone number of the dispatcher that will be returned.
     * @return Returns dispatcher by phone number.
     */
    public List<Dispatch> findDispatchByPhoneNumber(String phoneNumber) {
        return repository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new DispatchNotFoundException(phoneNumber));
    }

    /**
     * This method is used to get dispatcher by first name, last name or both.
     * @param FirstName This is the first name of the dispatcher that will be returned.
     * @param LastName This is the last name of the dispatcher that will be returned.
     * @return Returns dispatcher by first name, last name or both.
     */
    public List<Dispatch> findByName(String FirstName, String LastName)
    {
        if(FirstName != null && LastName != null)
        {
            return repository.findByFirstNameAndLastName(FirstName,LastName).orElseThrow(() -> new DispatchNotFoundException(FirstName,LastName));
        }
        else if(FirstName != null)
        {
            return repository.findByFirstName(FirstName).orElseThrow(() -> new DispatchNotFoundException(FirstName));
        }
        else if(LastName != null)
        {
            return repository.findByLastName(LastName).orElseThrow(() -> new DispatchNotFoundException(LastName));
        }
        return null;
    }

    /**
     * This method is used to get dispatcher by work number.
     * @param workNumber This is the work number of the dispatcher that will be returned.
     * @return Returns dispatcher by work number.
     */
    public List<Dispatch> findByWorkNumber(String workNumber) {
        return repository.findByWorkNumber(workNumber).orElseThrow(() -> new DispatchNotFoundException(workNumber));
    }





}
