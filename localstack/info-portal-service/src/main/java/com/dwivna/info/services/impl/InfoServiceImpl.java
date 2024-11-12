package com.dwivna.info.services.impl;

import com.dwivna.info.models.Info;
import com.dwivna.info.repositories.InfoRepository;
import com.dwivna.info.services.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InfoServiceImpl implements InfoService {

    private final InfoRepository infoRepository;

    @Autowired
    public InfoServiceImpl(InfoRepository infoRepository) {
        this.infoRepository = infoRepository;
    }

    @Override
    public Info saveInfo(Info info) {
        return infoRepository.save(info);
    }

    @Override
    public Info getInfoByIdAndService(String id, String service) {
        return infoRepository.findByIdAndSortKey(id, service);
    }
}
