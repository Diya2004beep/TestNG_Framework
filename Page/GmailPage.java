package pages;

import jakarta.mail.*;
import jakarta.mail.search.FlagTerm;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GmailPage {

    public String getOTP(String email, String password) {

        try {
            System.out.println("Fetching OTP for: " + email);

            Properties props = new Properties();
            props.put("mail.store.protocol", "imaps");

            Session session = Session.getDefaultInstance(props, null);

            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", email, password);

            System.out.println("Connected to Gmail");

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);

            // search unseen emails
            FlagTerm unseenFlagTerm = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
            Message[] messages = inbox.search(unseenFlagTerm);

            if (messages.length == 0) {
                throw new RuntimeException("No unread emails found");
            }

            Message latestMessage = messages[messages.length - 1];

            String body = latestMessage.getContent().toString();

            Pattern pattern = Pattern.compile("\\b\\d{6}\\b");
            Matcher matcher = pattern.matcher(body);

            if (!matcher.find()) {
                throw new RuntimeException("OTP not found in email");
            }

            String otp = matcher.group();

            System.out.println("OTP fetched: " + otp);

            inbox.close(false);
            store.close();

            return otp;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching OTP");
        }
    }
}
