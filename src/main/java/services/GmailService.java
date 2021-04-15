package services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import problemdomain.Account;

/**
 * Connects to the Gmail service and sends emails from the preschool email
 * parklandpreschoolteam@gmail.com
 *
 * @author Jean Lomibao
 */
public class GmailService {

    /**
     * Creates the email to be sent then sends the email.
     *
     * @param to address of the recipient
     * @param subject text for the subject of the email
     * @param template html template for the content of the email
     * @param tags values to be used for the template
     * @throws Exception thrown if there is an error with authentication values
     * provided or messaging and email address errors
     */
    public static void sendMail(String to, String subject, String template, HashMap<String, String> tags) throws Exception {
        String body = "";
        try {
            // read whole template into a single variable (body)
            BufferedReader br = new BufferedReader(new FileReader(new File(template)));

            String line = br.readLine();
            while (line != null) {
                body += line;
                line = br.readLine();
            }

            // replace all {{variable}} with the actual values
            for (String key : tags.keySet()) {
                body = body.replace("{{" + key + "}}", tags.get(key));
            }

        } catch (IOException e) {
            throw new IllegalStateException("Email template could not be found");
        }

        List<Address> toAddresses = new ArrayList<>();
        toAddresses.add(new InternetAddress(to));

        sendMail(toAddresses, subject, body, true);
    }

    /**
     * Authenticates and connects to the Gmail server with the right settings.
     * Creates the email and then sends the email.
     *
     * @param toAddresses list of addresses the message will be sent to
     * @param subject text for the subject of the email
     * @param body body of the email to be sent
     * @param bodyIsHTML true if the body is HTML
     * @throws MessagingException thrown if there is an error with address and
     * message creation
     * @throws Exception thrown if there is an error with authentication values
     * provided or messaging and email address errors
     */
    @SuppressWarnings("ConvertToTryWithResources")
    public static void sendMail(List<Address> toAddresses, String subject, String body, boolean bodyIsHTML) throws Exception {
        try {

            //grabs the email and password for authentication
            Context env = (Context) new InitialContext().lookup("java:comp/env");
            String username = (String) env.lookup("webmail-username");
            String password = (String) env.lookup("webmail-password");

            //setting for the gmail connection
            Properties props = new Properties();
            props.put("mail.transport.protocol", "smtps");
            props.put("mail.smtps.host", "smtp.gmail.com");
            props.put("mail.smtps.port", 465);
            props.put("mail.smtps.auth", "true");
            props.put("mail.smtps.quitwait", "false");
            Session session = Session.getDefaultInstance(props);
            session.setDebug(true);

            // create a message
            Message message = new MimeMessage(session);
            message.setSubject(subject);
            if (bodyIsHTML) {
                message.setContent(body, "text/html");
            } else {
                message.setText(body);
            }

            // creates the from address for the message
            Address fromAddress = new InternetAddress(username);
            message.setFrom(fromAddress);

            //sets the the recipient of the message
            for (Address to : toAddresses) {
                message.setRecipient(Message.RecipientType.TO, to);
            }

            // connect and send the message
            Transport transport = session.getTransport();
            transport.connect(username, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

        } catch (NamingException ex) {
            throw new IllegalStateException("Email or password could not be found");
        } catch (AddressException ex) {
            throw new IllegalStateException("Something went wrong with email address recipients");
        } catch (MessagingException ex) {
            throw new IllegalStateException("Somehting went wrong with sending the email");
        }
    }

    /**
     *
     * @param recipients list of accounts the message will be sent to
     * @param subject text for the subject of the email
     * @param template html template for the content of the email
     * @param tags values to be used for the template
     * @throws Exception thrown if there is an error with authentication values
     * provided or messaging and email address errors
     */
    public void sendMailToManyAccounts(List<Account> recipients, String subject, String template, HashMap<String, String> tags) throws Exception {
        String body = "";
        try {
            // read whole template into a single variable (body)
            BufferedReader br = new BufferedReader(new FileReader(new File(template)));

            String line = br.readLine();
            while (line != null) {
                body += line;
                line = br.readLine();
            }

            // replace all {{variable}} with the actual values
            for (String key : tags.keySet()) {
                body = body.replace("{{" + key + "}}", tags.get(key));
            }

        } catch (IOException e) {
            throw new IllegalStateException("Email template could not be found");
        }

        List<Address> toAddresses = new ArrayList<>();
        //prepare the email address of the recipients
        try {
            for (Account recipient : recipients) {
                toAddresses.add(new InternetAddress(recipient.getEmail()));
            }
        } catch (AddressException ex) {
            throw new IllegalStateException("Something went wrong with email address recipients");
        }

        sendMail(toAddresses, subject, body, true);
    }

    /**
     * Creates the instance for properties needed to connect to the Gmail server
     * then sets up the message and sends the email using the email and password
     * provided in the xml file
     * @param to email address of recipient
     * @param subject subject of the email
     * @param body body of the email
     * @param bodyIsHTML true if body is in html format
     * @throws MessagingException thrown when an error occurs while creating the message
     * @throws NamingException thrown when the email and password is not found
     */
    public static void sendMail(String to, String subject, String body, boolean bodyIsHTML) throws MessagingException, NamingException {
        Context env = (Context) new InitialContext().lookup("java:comp/env");
        String username = (String) env.lookup("webmail-username");
        String password = (String) env.lookup("webmail-password");

        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtps.host", "smtp.gmail.com");
        props.put("mail.smtps.port", 465);
        props.put("mail.smtps.auth", "true");
        props.put("mail.smtps.quitwait", "false");
        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);

        // create a message
        Message message = new MimeMessage(session);
        message.setSubject(subject);
        if (bodyIsHTML) {
            message.setContent(body, "text/html");
        } else {
            message.setText(body);
        }

        // address the message
        Address fromAddress = new InternetAddress(username);
        Address toAddress = new InternetAddress(to);
        message.setFrom(fromAddress);
        message.setRecipient(Message.RecipientType.TO, toAddress);

        // send the message
        Transport transport = session.getTransport();
        transport.connect(username, password);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
}
