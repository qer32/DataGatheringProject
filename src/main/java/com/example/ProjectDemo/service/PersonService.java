package com.example.ProjectDemo.service;

import com.example.ProjectDemo.dao.CustomPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PersonService {

    private final CustomPersonRepository customPersonRepository;

    @Autowired
    public PersonService(CustomPersonRepository customPersonRepository) {
        this.customPersonRepository = customPersonRepository;
    }

    public void performCrudOperations() {
        // 创建一个 Person 节点并获取返回的 ID
        int newPersonId = customPersonRepository.createPerson("MVP", 666);
        System.out.println("Created person with ID: " + newPersonId);

        // 读取所有 Person 节点
        List<Map<String, Object>> persons = customPersonRepository.getPersons();
        persons.forEach(person -> System.out.println(person));

        // 更新 Person 节点的年龄
        customPersonRepository.updatePersonAge(newPersonId, 31);

        // 再次读取以确认更新
        persons = customPersonRepository.getPersons();
        persons.forEach(person -> System.out.println(person));

        // 删除 Person 节点
        customPersonRepository.deletePerson(newPersonId);

        // 确认已被删除
        persons = customPersonRepository.getPersons();
        if (persons.isEmpty()) {
            System.out.println("All persons have been deleted.");
        }
    }

    public void DeleteAllPersonNodes() {
        customPersonRepository.deleteAllPersons();
    }
}
