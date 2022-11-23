package co.edu.sena.service.impl;

import co.edu.sena.domain.Presentation;
import co.edu.sena.repository.PresentationRepository;
import co.edu.sena.service.PresentationService;
import co.edu.sena.service.dto.PresentationDTO;
import co.edu.sena.service.mapper.PresentationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Presentation}.
 */
@Service
@Transactional
public class PresentationServiceImpl implements PresentationService {

    private final Logger log = LoggerFactory.getLogger(PresentationServiceImpl.class);

    private final PresentationRepository presentationRepository;

    private final PresentationMapper presentationMapper;

    public PresentationServiceImpl(PresentationRepository presentationRepository, PresentationMapper presentationMapper) {
        this.presentationRepository = presentationRepository;
        this.presentationMapper = presentationMapper;
    }

    @Override
    public PresentationDTO save(PresentationDTO presentationDTO) {
        log.debug("Request to save Presentation : {}", presentationDTO);
        Presentation presentation = presentationMapper.toEntity(presentationDTO);
        presentation = presentationRepository.save(presentation);
        return presentationMapper.toDto(presentation);
    }

    @Override
    public PresentationDTO update(PresentationDTO presentationDTO) {
        log.debug("Request to save Presentation : {}", presentationDTO);
        Presentation presentation = presentationMapper.toEntity(presentationDTO);
        presentation = presentationRepository.save(presentation);
        return presentationMapper.toDto(presentation);
    }

    @Override
    public Optional<PresentationDTO> partialUpdate(PresentationDTO presentationDTO) {
        log.debug("Request to partially update Presentation : {}", presentationDTO);

        return presentationRepository
            .findById(presentationDTO.getId())
            .map(existingPresentation -> {
                presentationMapper.partialUpdate(existingPresentation, presentationDTO);

                return existingPresentation;
            })
            .map(presentationRepository::save)
            .map(presentationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PresentationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Presentations");
        return presentationRepository.findAll(pageable).map(presentationMapper::toDto);
    }

    public Page<PresentationDTO> findAllWithEagerRelationships(Pageable pageable) {
        return presentationRepository.findAllWithEagerRelationships(pageable).map(presentationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PresentationDTO> findOne(Long id) {
        log.debug("Request to get Presentation : {}", id);
        return presentationRepository.findOneWithEagerRelationships(id).map(presentationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Presentation : {}", id);
        presentationRepository.deleteById(id);
    }
}
