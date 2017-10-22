/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.si;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.json.JSONObject;

/**
 *
 * @author Kasper
 */
public class sendXML {

    private static final String EXCHANGE_NAME = "cphbusiness.bankXML";

    public static void main(String[] argv) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("datdb.cphbusiness.dk");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        JSONObject json = new JSONObject("{\"ssn\": 1160578787,\"creditScore\": 598,\"loanAmount\": 10.0,\"loanDuration\": 360}");
        
        convertToXML ctx = new convertToXML();
        String message = ctx.convertFromJSON(json);
        
        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties().builder();
        
        builder.replyTo("kkc-receiver");
        
        AMQP.BasicProperties prop = builder.build();

        channel.basicPublish(EXCHANGE_NAME, "", prop, message.getBytes());
        System.out.println(" [x] Sent '" + message.toString() + "'");

        channel.close();
        connection.close();
    }
}
