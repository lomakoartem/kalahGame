package ua.kalahgame.service.impl;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kalahgame.domain.Player;
import ua.kalahgame.infrastructure.passwordGenerateAndHash.PasswordGenerator;
import ua.kalahgame.infrastructure.passwordGenerateAndHash.PasswordHash;
import ua.kalahgame.repository.PlayerRepository;
import ua.kalahgame.service.PlayerService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Created by a.lomako on 1/30/2017.
 */
@Service
public class SimplePlayerService implements PlayerService {
    private PlayerRepository playerRepository;

    @Autowired

    public SimplePlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }



    private SimpleMailMessage templateMessage;

    private MailSender mailSender;

    private static final Logger LOG = LoggerFactory.getLogger(SimplePlayerService.class);

    @Autowired
    public void setTemplateMessage(SimpleMailMessage templateMessage) {
        this.templateMessage = templateMessage;
    }


    @Autowired
    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public Player findPlayerById(Long id) {
        Player player = playerRepository.findOne(id);

     /*   if (player == null) {
            throw new EntityNotFoundException(id);
        }*/

        return player;
    }

    @Override
    public Iterable<Player> findAll() {
        return playerRepository.findAll();
    }


    @Override
    public Optional<Player> getUser(String player_login) {
        return null;
    }

    @Override
    public Player addPlayer(Player player) {

        //     System.out.println("_-------__-_________--");
       /* Player domainUser = playerRepository.findOne(playerRepository.getUserByLogin(player.getPlayer_login()));
        System.out.println(domainUser);*/
        //  player.setPlayer_password(PasswordHash.hash(player.getPlayer_password().toCharArray()));
       /* player.setFirstName(player.getFirstName());
        player.setLastName(player.getLastName());
        player.setGender(player.getGender());
        player.setCity(player.getCity());*/
        return playerRepository.save(player);
    }

    @Override
    public boolean generatePasswordAndSendByMail(Long id) {

        char[] password = PasswordGenerator.generatePswd();
        if (sendPasswordByMail(id, password)) {
            savePlayerPassword(id, PasswordHash.hash(password));
            return true;
        } else {
            return false;
        }
    }


    @Override
    public char[] generatePasswordAndSaveInDatabase(Long id) {

        char[] password = PasswordGenerator.generatePswd();

        savePlayerPassword(id, PasswordHash.hash(password));

        return password;
    }

    @Override
    public boolean sendPasswordByMail(Long id, char[] password) {
        SimpleMailMessage msg = generateEmailMessage(id, password);
        return sendMail(msg);
    }

    private boolean sendMail(SimpleMailMessage msg) {
        try {
            this.mailSender.send(msg);
        } catch (MailException ex) {
            LOG.warn(ex.getMessage());
            return false;
        }

        return true;
    }

    private SimpleMailMessage generateEmailMessage(Long id, char[] password) {
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        Player player = findPlayerById(id);
        //  Vendor vendor = findById(id);
        msg.setTo(player.getEmail());
        msg.setText(String.format(msg.getText(), player.getEmail(), new String(password)));
        return msg;
    }
    private void savePlayerPassword(Long id, String password) {

        Player player = findPlayerById(id);
        //Vendor vendor = findById(id);
if(player != null) {
    player.setPlayer_password(password);
    playerRepository.save(player);
}
 else
    System.out.println("No such email in db");
           }
}
