package com.testbuilder;

import com.beans.Address;
import com.beans.Client;
import com.beans.Project;
import com.google.common.collect.Lists;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.testbuilder.DateTestBuilder.date;

public class ProjectTestBuilder {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    private Long id = null;
    private String description = "description";
    private Address address = new Address("street", "12345", "city");
    private Date startDate = date("2017-01-01");
    private Date endDate = date("2017-12-31");
    private Client client;

    public ProjectTestBuilder() throws ParseException {
    }

    public static ProjectTestBuilder project() throws ParseException {
        return new ProjectTestBuilder();
    }

    public Project build() {
        return new Project(id,
                description,
                address,
                startDate,
                endDate,
                Lists.newArrayList(),
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

    public ProjectTestBuilder withStartDate(String startDate) {
        this.startDate = date(startDate);
        return this;
    }

    public ProjectTestBuilder withEndDate(String endDate) {
        this.endDate = date(endDate);
        return this;
    }

    public ProjectTestBuilder withClient(Client client) {
        this.client = client;
        return this;
    }
}
