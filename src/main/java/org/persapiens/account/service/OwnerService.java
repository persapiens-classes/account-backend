package org.persapiens.account.service;

import org.persapiens.account.domain.Owner;
import org.persapiens.account.persistence.OwnerRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OwnerService extends CrudService<Owner, Long> {
    @Autowired
    private OwnerRepository ownerRepository;
    
    public Optional<Owner> findByName(String name) {
        return ownerRepository.findByName(name);
    }
    
    @Transactional
    public void deleteByName(String name) {
        ownerRepository.deleteByName(name);
    }
}
