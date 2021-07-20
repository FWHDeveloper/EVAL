package com.fatma.eval.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fatma.eval.dto.MailObject;
import com.fatma.eval.service.EmailServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


@Controller
@RequestMapping("/mail")
public class MailController {

    private Logger logger = LogManager.getLogger(MailController.class);

    @Autowired
    public EmailServiceImpl emailService;

    @Value("${attachment.invoice}")
    private String attachmentPath;

    @Autowired
    public SimpleMailMessage template;

    private static final Map<String, Map<String, String>> labels;

    static {
        labels = new HashMap<>();

        //Simple email
        Map<String, String> props = new HashMap<>();
        props.put("headerText", "Send Notification Email");
        props.put("messageLabel", "Message");
        props.put("additionalInfo", "");
        props.put("action", "send");
        labels.put("send", props);

    }

    @GetMapping(value = {"/send"})
    public String createMail(Model model,
                             HttpServletRequest request) {
        String action = request.getRequestURL().substring(
                request.getRequestURL().lastIndexOf("/") + 1
        );
        Map<String, String> props = labels.get(action);
        Set<String> keys = props.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            model.addAttribute(key, props.get(key));
        }

        model.addAttribute("mailObject", new MailObject());
        model.addAttribute("labels", labels);
        logger.info("props : {}" + props);
        logger.info("labels : {}" + labels);
        return "mail/send";
    }

    @PostMapping("/send")
    public String createMail(Model model,
                             @ModelAttribute("mailObject") @Valid MailObject mailObject,
                             Errors errors) {
        logger.info("================= send notidication Email ===============");
        if (errors.hasErrors()) {
            logger.info("mailObject : {}" + mailObject.toString());
            logger.info("Error: " + errors.getAllErrors());
            return "mail/send";
        }
        emailService.sendSimpleMessage(mailObject.getTo(),
                mailObject.getSubject(), mailObject.getText());

        logger.info("mailObject : {}" + mailObject.toString());

        return "redirect:/home";

    }


    
}
