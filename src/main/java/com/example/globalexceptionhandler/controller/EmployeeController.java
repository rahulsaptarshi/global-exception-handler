package com.example.globalexceptionhandler.controller;

import com.example.globalexceptionhandler.exception.BadRequestException;
import com.example.globalexceptionhandler.model.Employee;
import com.example.globalexceptionhandler.model.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EmployeeController {

    Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    @GetMapping("/getAllEmployee/{caseNumber}")
    public ResponseModel getAllEmployee(@PathVariable int caseNumber) throws BadRequestException {

        LOGGER.info("Request received to fetch all employees");
        List<Employee> employeeList = getEmployees(caseNumber);
        if (CollectionUtils.isEmpty(employeeList)) {
            throw new BadRequestException("Employee Not found");
        }
        return new ResponseModel(employeeList, HttpStatus.OK.value());
    }

    /**
     * Below is the normal way to add the BadRequest exception handling  for the controller
     * The drawback here is we need to write the same code for the other controller or service which is repetition of same code
     * and this is not readable as well.
     *
     * @return
     */
    @GetMapping("/getAllEmployee/normalHandler")
    public ResponseEntity<ResponseModel> getAllEmployeeNormal() {

        LOGGER.info("Request received to fetch all employees");
        List<Employee> employeeList = getEmployees(2);
        try {
            if (CollectionUtils.isEmpty(employeeList)) {
                throw new BadRequestException("Employee Not found");
            }
            return new ResponseEntity<>(new ResponseModel(employeeList, HttpStatus.OK.value()), HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(new ResponseModel("Employee Not found", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
    }

    private List<Employee> getEmployees(int caseNumber) {
        if (caseNumber == 1) {
            return new ArrayList<>() {{
                add(new Employee("john", "street", 29));
                add(new Employee("rock", "street1", 55));
                add(new Employee("james", "street2", 24));
            }};
        } else {
            return new ArrayList<>();
        }
    }
}
