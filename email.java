/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package project.mailsend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
//qxit dexe mqmu rhob


public class SimpleService {
	
	public static void main(String[] a) {
		 // Recipient's email ID needs to be mentioned.
        String to = "";

        // Sender's email ID needs to be mentioned
        String from = "chethancm2001kadur@gmail.com";
        final String username = ""; // your Gmail username
        final String password = ""; // your Gmail password

        // Assuming you are sending email through smtp.gmail.com
        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Get the Session object.
        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });

        try {
            // Create a default MimeMessage object.
        	  Message message = new MimeMessage(session);
        	  
            // Set From: header field of the header.
        	  message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(to));

            // Set Subject: header field
          
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            ///home/chethan/eclipse-workspace/Learn/mailsend/src/main/java/project
            // Send message
            List<HashMap<String, String>> orderDetailsList = new ArrayList<HashMap<String, String>>();

            // Add sample order details to the list
            
            String currentWorkingDirectory = System.getProperty("user.dir") + "/src/main/java/project/mailsend/photo.png";
            
            System.out.println(currentWorkingDirectory);
            
            HashMap<String, String> order1 = new HashMap<String, String>();
            order1.put("Orders", "12312");
            order1.put("message", "Order message 1");	
            order1.put("imagePath", currentWorkingDirectory);

            HashMap<String, String> order2 = new HashMap<String, String>();
            order2.put("Orders", "67890");
            order2.put("message", "Order message 2");
            order2.put("imagePath", currentWorkingDirectory);
            
            HashMap<String, String> order3 = new HashMap<String, String>();
            order3.put("Orders", "11223");
            order3.put("message", "Order message 3");
            order3.put("imagePath", currentWorkingDirectory);
            
            orderDetailsList.add(order1);
            orderDetailsList.add(order2);
            orderDetailsList.add(order3);

            	 	
            
            
            StringBuilder htmlTable = new StringBuilder();
            htmlTable.append("<h1>Order Details</h1>")
                    .append("<table border='1' cellpadding='5' cellspacing='0' style='width:100%;'>")
                    .append("<tr><th>Order Number</th><th>Message</th><th>Image</th></tr>");

            for (int i = 0; i < orderDetailsList.size(); i++) {
                HashMap<String, String> orderDetails = orderDetailsList.get(i);
                String imagePath = orderDetails.get("imagePath");
                String cid = "image" + i;

                htmlTable.append("<tr>")
                        .append("<td>").append(orderDetails.get("Orders")).append("</td>")
                        .append("<td>").append(orderDetails.get("message")).append("</td>")
                        .append("<td><img src='cid:").append(cid).append("' width='100%' height='100'></td>")
                        .append("</tr>");
            }
            htmlTable.append("</table>");
            
                messageBodyPart.setContent(htmlTable.toString(), "text/html");

                // Create a multipart message
                MimeMultipart multipart = new MimeMultipart();

                // Set HTML message part
                multipart.addBodyPart(messageBodyPart);
                
                for (int i = 0; i < orderDetailsList.size(); i++) {
                    HashMap<String, String> orderDetails = orderDetailsList.get(i);
                    String imagePath = orderDetails.get("imagePath");
                    String cid = "image" + i;

                    if (imagePath != null && !imagePath.isEmpty()) {
                        MimeBodyPart imageBodyPart = new MimeBodyPart();
                        FileDataSource fds = new FileDataSource(imagePath);
                        imageBodyPart.setDataHandler(new DataHandler(fds));
                        imageBodyPart.setHeader("Content-ID", "<" + cid + ">");
                        imageBodyPart.setDisposition(MimeBodyPart.INLINE);

                        // Add the image part to the multipart
                        multipart.addBodyPart(imageBodyPart);
                    }
                }

                // Send the complete message parts
                message.setContent(multipart);
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
	}

}
