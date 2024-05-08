package com.example.ProjectDemo.dao;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;
import org.neo4j.driver.Record;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class CustomPersonRepository {

    private final Driver driver;

    public CustomPersonRepository() {
        // 配置 Neo4j 连接信息
        String uri = "bolt://localhost:7687";
        String username = "neo4j";
        String password = "123456789";

        // 创建驱动器实例
        this.driver = GraphDatabase.driver(uri, AuthTokens.basic(username, password));
    }

    // 创建节点
    public int createPerson(String name, int age) {
        try (Session session = driver.session()) {
            String query = "CREATE (p:Person {name: $name, age: $age}) RETURN id(p)";
            Record record = session.run(query, Map.of("name", name, "age", age)).single();
            return record.get("id(p)").asInt();
        }
    }

    // 读取节点
    public List<Map<String, Object>> getPersons() {
        List<Map<String, Object>> persons = new ArrayList<>();
        try (Session session = driver.session()) {
            String query = "MATCH (p:Person) RETURN p";
            Result result = session.run(query);

            while (result.hasNext()) {
                Record record = result.next();
                persons.add(record.asMap());
            }
        }
        return persons;
    }

    // 更新节点
    public void updatePersonAge(int id, int newAge) {
        try (Session session = driver.session()) {
            String query = "MATCH (p:Person) WHERE ID(p) = $id SET p.age = $age";
            session.run(query, Map.of("id", id, "age", newAge));
        }
    }

    // 删除节点
    public void deletePerson(int id) {
        try (Session session = driver.session()) {
            String query = "MATCH (p:Person) WHERE ID(p) = $id DETACH DELETE p";
            session.run(query, Map.of("id", id));
        }
    }

    // 删除所有 Person 节点
    public void deleteAllPersons() {
        try (Session session = driver.session()) {
            String query = "MATCH (p:Person) DETACH DELETE p";
            session.run(query);
        }
    }

    // 关闭驱动器
    public void close() {
        driver.close();
    }
}