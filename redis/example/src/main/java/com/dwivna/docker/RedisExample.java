package com.dwivna.docker;

import redis.clients.jedis.Jedis;

public class RedisExample {

    public static void main(String[] args) {
        // Configure Redis host and port
        String redisHost = "localhost";
        int redisPort = 6379;

        // Connect to Redis
        try (Jedis jedis = new Jedis(redisHost, redisPort)) {
            // Check connection
            System.out.println("Connected to Redis: " + jedis.ping());

            // Put a key-value pair
            String key = "myKey";
            String value = "myValue";
            jedis.set(key, value);
            System.out.println("Set key-value: " + key + " -> " + value);

            // Get the value for the key
            String retrievedValue = jedis.get(key);
            System.out.println("Retrieved value for " + key + ": " + retrievedValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}