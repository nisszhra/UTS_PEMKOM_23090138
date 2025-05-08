/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.p_uts_23090138_c_2025;
import com.mongodb.client.*;
import org.bson.Document;

/**
 *
 * @author ZAHRA
 */
public class MongoConnection {
    public static MongoClient getClient() {
        return MongoClients.create("mongodb://localhost:27017");
    }

    public static MongoDatabase getDatabase() {
        return getClient().getDatabase("uts_23090138_C_2025");
    }

    public static MongoCollection<Document> getCustomerCollection() {
        return getDatabase().getCollection("coll_23090138_C_2025");
    }
}
