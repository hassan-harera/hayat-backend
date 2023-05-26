package com.harera.hayat.needs.service;

import com.harera.hayat.needs.model.Need;
import com.harera.hayat.needs.repository.NeedRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NeedService {

    private final NeedRepository needRepository;

    public NeedService(NeedRepository needRepository) {
        this.needRepository = needRepository;
    }

    public List<Need> search(String query, int page) {
        return needRepository.search(query, page);
    }
}
