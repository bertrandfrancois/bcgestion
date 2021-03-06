package com.testbuilder;

import com.model.Address;
import com.model.Client;
import com.model.Period;
import com.model.Project;
import com.google.common.collect.Lists;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class ProjectTestBuilder {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    private Long id = null;
    private String description = "description";
    private Address address = new Address("street", "12345", "city");
    private LocalDate startDate = LocalDate.of(2017, 1, 1);
    private LocalDate endDate = LocalDate.of(2017, 12, 31);
    private Client client;

    public  ProjectTestBuilder() {
    }

    public static ProjectTestBuilder project(){
        return new ProjectTestBuilder();
    }

    public Project build() {
        return new Project(id,
                           Lists.newArrayList(),
                           description,
                           address,
                           new Period(startDate, endDate),
                           client);
    }

    public ProjectTestBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public ProjectTestBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public ProjectTestBuilder withAddress(Address address) {
        this.address = address;
        return this;
    }

    //    public ProjectTestBuilder withStartDate(String startDate) {
    //        this.startDate = date(startDate);
    //        return this;
    //    }
    //
    //    public ProjectTestBuilder withEndDate(String endDate) {
    //        this.endDate = date(endDate);
    //        return this;
    //    }

    public ProjectTestBuilder withClient(Client client) {
        this.client = client;
        return this;
    }
}
