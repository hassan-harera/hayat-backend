package com.harera.hayat.framework.service;


import java.util.List;
import java.util.stream.Collectors;

import com.harera.hayat.framework.exception.EntityNotFoundException;
import com.harera.hayat.framework.model.city.State;
import com.harera.hayat.framework.model.city.StateResponse;
import com.harera.hayat.framework.repository.city.StateRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.harera.hayat.framework.util.ErrorCode.NOT_FOUND_STATE_ID;


@Service
public class StateService {

    private final StateRepository stateRepository;
    private final ModelMapper mapper;

    @Autowired
    public StateService(StateRepository stateRepository, ModelMapper mapper) {
        this.stateRepository = stateRepository;
        this.mapper = mapper;
    }

    public StateResponse get(long id) {
        State state = stateRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id),
                                        NOT_FOUND_STATE_ID));
        return mapper.map(state, StateResponse.class);
    }

    public List<StateResponse> list() {
        List<State> stateList = stateRepository.findAll();
        return stateList.stream().map(state -> mapper.map(state,
                StateResponse.class)).collect(Collectors.toList());
    }
}
