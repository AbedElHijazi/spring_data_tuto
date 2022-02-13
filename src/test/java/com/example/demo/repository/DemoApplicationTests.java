package com.example.demo.repository;

import com.example.demo.model.Client;
import com.example.demo.model.Compte;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.demo.model.Transaction;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    ClientDAO clientDao;
    
    private CompteDAO compteDAO;
    
    @Test
    @Ignore
    public void createAndGet() {
        Client c = new Client("Pascal", "Fares", "pascal.fares@lecnam.net");
        Compte cpt = new Compte(c, 0);
        c.setCompte(cpt);
        clientDao.save(c);
        Client c1 = clientDao.findByEmail("pascal.fares@lecnam.net");
        assertThat(c1.getNom()).isEqualTo(c.getNom());

    }

    @Test
    public void createAndAddFriend() {
        Client c = new Client("Pascal1", "Fares", "pascal1.fares@lecnam.net");
        Compte cpt = new Compte(c, 0);
        c.setCompte(cpt);
        clientDao.save(c);
        Client c1 = new Client("Pascal2", "Fares", "pascal2.fares@lecnam.net");
        Compte cpt1 = new Compte(c1, 0);
        c1.setCompte(cpt1);
        
        
        c1.getFriends().add(c);
        clientDao.save(c1);
        Client c2 = clientDao.findByEmail("pascal2.fares@lecnam.net");
        assertThat(c2.getFriends().size()).isEqualTo(c1.getFriends().size());
    }
    
    @Test
    public void createVirement() {
        Client c1 = clientDao.findByEmail("abedalkarim.hijazi@isae.edu.lb");
        Client c2 = clientDao.findByEmail("MonAmis@isae.edu.lb");
        
        if (c2==null)
        {
            Client c = new Client("MonAmis", "MonAmis", "MonAmis@isae.edu.lb");
            Compte cpt1 = new Compte(c, 0);
            c.setCompte(cpt1);
            
            c1.getFriends().add(c);
            clientDao.save(c);
            c2=c;
        }
        
        Compte compteC2 = compteDAO.findByProprietaire(c2);
        Transaction trans1 = new Transaction(5000,c1,compteC2);
        assertThat(trans1);
    }
}
