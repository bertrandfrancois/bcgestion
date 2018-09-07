package com.service;

import com.beans.Client;
import com.beans.Project;
import com.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Service
@Transactional
public class ProjectService implements BaseService<Project>{

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<Project> findAll() {
        return newArrayList(projectRepository.findAll());
    }

    @Override
    public Project save(Project param) {
        return projectRepository.save(param);
    }

    @Override
    public Project find(long id) {
        return projectRepository.findById(id).get();
    }

    @Override
    public void delete(long id) {

    }
}
