package cz.zoubelu.service.impl;

import cz.zoubelu.utils.ConversionError;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.util.List;

/**
 * Created by zoubas on 11.3.17.
 */
public class EmailSender {

    public void sendEmail(List<ConversionError> errors) {
        HtmlEmail email = new HtmlEmail();
        try {
            email.setHostName("-------");
            email.setAuthentication("-----------", "------------");
            email.addTo("--------------");
            email.setFrom("-------------");
            email.setSubject("--------------");

            email.setHtmlMsg(createEmailBody(errors));
            email.setTextMsg("Your email client does not support HTML messages");
            email.send();
        } catch (EmailException e) {
            System.out.println("FAAAAAAAAIIIIIILLLLLL!!!!!");
            e.printStackTrace();
        }
    }

    private String createEmailBody(List<ConversionError> errors) {
        String headline = "<h1>Following errors occur after conversion.</h1>";
        String body = "<ul>";

        for (ConversionError error : errors) {
            body += "<li>" + error.getMessage() + "</li>";
        }

        body += "</ul>";
        return "<html lang=\"cz\"></head><body>" + body + "</body></html>";
    }
}
