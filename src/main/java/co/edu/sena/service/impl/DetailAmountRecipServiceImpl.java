package co.edu.sena.service.impl;

import co.edu.sena.domain.DetailAmountRecip;
import co.edu.sena.repository.DetailAmountRecipRepository;
import co.edu.sena.service.DetailAmountRecipService;
import co.edu.sena.service.dto.DetailAmountRecipDTO;
import co.edu.sena.service.mapper.DetailAmountRecipMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DetailAmountRecip}.
 */
@Service
@Transactional
public class DetailAmountRecipServiceImpl implements DetailAmountRecipService {

    private final Logger log = LoggerFactory.getLogger(DetailAmountRecipServiceImpl.class);

    private final DetailAmountRecipRepository detailAmountRecipRepository;

    private final DetailAmountRecipMapper detailAmountRecipMapper;

    public DetailAmountRecipServiceImpl(
        DetailAmountRecipRepository detailAmountRecipRepository,
        DetailAmountRecipMapper detailAmountRecipMapper
    ) {
        this.detailAmountRecipRepository = detailAmountRecipRepository;
        this.detailAmountRecipMapper = detailAmountRecipMapper;
    }

    @Override
    public DetailAmountRecipDTO save(DetailAmountRecipDTO detailAmountRecipDTO) {
        log.debug("Request to save DetailAmountRecip : {}", detailAmountRecipDTO);
        DetailAmountRecip detailAmountRecip = detailAmountRecipMapper.toEntity(detailAmountRecipDTO);
        detailAmountRecip = detailAmountRecipRepository.save(detailAmountRecip);
        return detailAmountRecipMapper.toDto(detailAmountRecip);
    }

    @Override
    public DetailAmountRecipDTO update(DetailAmountRecipDTO detailAmountRecipDTO) {
        log.debug("Request to save DetailAmountRecip : {}", detailAmountRecipDTO);
        DetailAmountRecip detailAmountRecip = detailAmountRecipMapper.toEntity(detailAmountRecipDTO);
        detailAmountRecip = detailAmountRecipRepository.save(detailAmountRecip);
        return detailAmountRecipMapper.toDto(detailAmountRecip);
    }

    @Override
    public Optional<DetailAmountRecipDTO> partialUpdate(DetailAmountRecipDTO detailAmountRecipDTO) {
        log.debug("Request to partially update DetailAmountRecip : {}", detailAmountRecipDTO);

        return detailAmountRecipRepository
            .findById(detailAmountRecipDTO.getId())
            .map(existingDetailAmountRecip -> {
                detailAmountRecipMapper.partialUpdate(existingDetailAmountRecip, detailAmountRecipDTO);

                return existingDetailAmountRecip;
            })
            .map(detailAmountRecipRepository::save)
            .map(detailAmountRecipMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DetailAmountRecipDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DetailAmountRecips");
        return detailAmountRecipRepository.findAll(pageable).map(detailAmountRecipMapper::toDto);
    }

    public Page<DetailAmountRecipDTO> findAllWithEagerRelationships(Pageable pageable) {
        return detailAmountRecipRepository.findAllWithEagerRelationships(pageable).map(detailAmountRecipMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DetailAmountRecipDTO> findOne(Long id) {
        log.debug("Request to get DetailAmountRecip : {}", id);
        return detailAmountRecipRepository.findOneWithEagerRelationships(id).map(detailAmountRecipMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DetailAmountRecip : {}", id);
        detailAmountRecipRepository.deleteById(id);
    }
}
