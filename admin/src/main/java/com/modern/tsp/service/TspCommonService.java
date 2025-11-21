package com.modern.tsp.service;

import com.modern.tsp.mapper.TspCommonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p> TODO <p>
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TspCommonService {

    private final TspCommonMapper tspCommonMapper;

    public String pasToVin(String search) {
        search = "%" + search + "%";
        return tspCommonMapper.pasToVin(search);
    }

}
