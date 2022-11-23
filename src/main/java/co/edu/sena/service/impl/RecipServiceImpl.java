package co.edu.sena.service.impl;

import co.edu.sena.domain.Recip;
import co.edu.sena.repository.RecipRepository;
import co.edu.sena.service.RecipService;
import co.edu.sena.service.dto.RecipDTO;
import co.edu.sena.service.mapper.RecipMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Recip}.
 */
@Service
@Transactional
public class RecipServiceImpl implements RecipService {

    private final Logger log = LoggerFactory.getLogger(RecipServiceImpl.class);

    private final RecipRepository recipRepository;

    private final RecipMapper recipMapper;

    public RecipServiceImpl(RecipRepository recipRepository, RecipMapper recipMapper) {
        this.recipRepository = recipRepository;
        this.recipMapper = recipMapper;
    }

    @Override
    public RecipDTO save(RecipDTO recipDTO) {
        log.debug("Request to save Recip : {}", recipDTO);
        Recip recip = recipMapper.toEntity(recipDTO);
        recip = recipRepository.save(recip);
        return recipMapper.toDto(recip);
    }

    @Override
    public RecipDTO update(RecipDTO recipDTO) {
        log.debug("Request to save Recip : {}", recipDTO);
        Recip recip = recipMapper.toEntity(recipDTO);
        recip = recipRepository.save(recip);
        return recipMapper.toDto(recip);
    }

    @Override
    public Optional<RecipDTO> partialUpdate(RecipDTO recipDTO) {
        log.debug("Request to partially update Recip : {}", recipDTO);

        return recipRepository
            .findById(recipDTO.getId())
            .map(existingRecip -> {
                recipMapper.partialUpdate(existingRecip, recipDTO);

                return existingRecip;
            })
            .map(recipRepository::save)
            .map(recipMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RecipDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Recips");
        return recipRepository.findAll(pageable).map(recipMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RecipDTO> findOne(Long id) {
        log.debug("Request to get Recip : {}", id);
        return recipRepository.findById(id).map(recipMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Recip : {}", id);
        recipRepository.deleteById(id);
    }
}
